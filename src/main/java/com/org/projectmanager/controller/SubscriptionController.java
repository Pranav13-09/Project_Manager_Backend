package com.org.projectmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.org.projectmanager.model.PlanType;
import com.org.projectmanager.model.Subscription;
import com.org.projectmanager.model.User;
import com.org.projectmanager.service.SubscriptionService;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/create")
    public ResponseEntity<Subscription> createSubscription(@RequestBody final User user) {
        final var subscription = subscriptionService.createSubscription(user);
        return ResponseEntity.ok(subscription);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Subscription> getUsersSubscription(@PathVariable final Long userId) {
        final var subscription = subscriptionService.getUsersSubscription(userId);
        return ResponseEntity.ok(subscription);
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> isValid(@RequestBody final Subscription subscription) {
        final var valid = subscriptionService.isValid(subscription);
        return ResponseEntity.ok(valid);
    }

    @PostMapping("/upgrade/{userId}")
    public ResponseEntity<Subscription> upgradeSubscription(@PathVariable final Long userId,
            @RequestParam final PlanType planType) {
        final var subscription = subscriptionService.upgradeSubscription(userId, planType);
        return ResponseEntity.ok(subscription);
    }
}
