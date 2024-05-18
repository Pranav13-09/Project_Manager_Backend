package com.org.projectmanager.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.projectmanager.model.PlanType;
import com.org.projectmanager.response.PaymentLinkResponse;
import com.org.projectmanager.service.UserService;
import com.razorpay.RazorpayClient;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Value("${razorpay.key}")
    private String keyId;

    @Value("${razorpay.secret}")
    private String keySecret;
    @Autowired
    private UserService userService;

    @PostMapping("/{planType}")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(@PathVariable final PlanType planType,
            @RequestHeader("Authorization") final String jwt) throws Exception {
        final var user = userService.findUserByJwt(jwt);
        double amount = 799 * 100;
        if (PlanType.ANNUALY.equals(planType)) {
            amount = amount * 12 * 0.7;
        }
        try {
            final var razorpay = new RazorpayClient(keyId, keySecret);
            final var paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", amount);
            paymentLinkRequest.put("currency", "INR");
            final var customer = new JSONObject();
            customer.put("name", user.getFullName());
            customer.put("email", user.getEmail());
            paymentLinkRequest.put("customer", customer);
            final var notify = new JSONObject();
            notify.put("email", true);
            paymentLinkRequest.put("notify", notify);
            paymentLinkRequest.put("callback_url", "http://localhost:5173/upgrade_plan/success?planType=" + planType);
            final var payment = razorpay.paymentLink.create(paymentLinkRequest);
            final var paymentLinkId = payment.get("id");
            final var paymentLinkUrl = payment.get("short_url");
            final var plr = new PaymentLinkResponse();
            plr.setPayment_link_id((String) paymentLinkId);
            plr.setPayment_link_url((String) paymentLinkUrl);
            return new ResponseEntity<>(plr, HttpStatus.OK);
        } catch (final Exception e) {
            throw e;
        }
    }

}
