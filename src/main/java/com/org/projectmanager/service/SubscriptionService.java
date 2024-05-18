package com.org.projectmanager.service;

import com.org.projectmanager.model.PlanType;
import com.org.projectmanager.model.Subscription;
import com.org.projectmanager.model.User;

public interface SubscriptionService {

    // Function to create a subscription taking a User object as a parameter
    Subscription createSubscription(User user);

    // Function to get a user's subscription using their userId
    Subscription getUsersSubscription(Long userId);

    // Function to validate a subscription taking a Subscription object as a
    // parameter
    boolean isValid(Subscription subscription);

    // Function to upgrade a subscription taking userId, PlanType enum, and boolean
    // isValid as parameters
    Subscription upgradeSubscription(Long userId, PlanType planType);
}
