package com.org.projectmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.projectmanager.config.JwtProvider;
import com.org.projectmanager.model.User;
import com.org.projectmanager.repository.UserRepository;
import com.org.projectmanager.request.LoginRequest;
import com.org.projectmanager.response.AuthResponse;
import com.org.projectmanager.service.CustomerUserDetailsImpl;
import com.org.projectmanager.service.SubscriptionService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerUserDetailsImpl CustomUserDetails;

    @Autowired
    private SubscriptionService subscriptionService;

    private Authentication authenticate(final String username, final String password) {
        final var userDetails = CustomUserDetails.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("invalid username...");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("invalid password...");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUSerHandler(@RequestBody final User user) throws Exception {
        final var isUserExist = userRepository.findByEmail(user.getEmail());
        if (isUserExist != null) {
            throw new Exception("email already exists with another account");
        }
        final var createdUser = new User();
        createdUser.setEmail(user.getEmail());
        createdUser.setFullName(user.getFullName());
        createdUser.setPassword(passwordEncoder.encode(user.getPassword()));

        final var savedUser = userRepository.save(createdUser);
        final Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(),
                user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final var jwt = JwtProvider.generateToken(authentication);
        final var authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Signed Up Successfully");
        subscriptionService.createSubscription(savedUser);
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);

    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signIn(@RequestBody final LoginRequest loginRequest) {
        final var username = loginRequest.getEmail();
        final var password = loginRequest.getPassword();
        final var authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final var jwt = JwtProvider.generateToken(authentication);
        final var authResponse = new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setMessage("Signed In Successfully");
        return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }

}
