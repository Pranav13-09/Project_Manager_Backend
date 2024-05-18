package com.org.projectmanager.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.projectmanager.model.PlanType;
import com.org.projectmanager.model.Subscription;
import com.org.projectmanager.model.User;
import com.org.projectmanager.repository.SubscriptionRepository;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    UserService userService;
    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Override
    public Subscription createSubscription(final User user) {
        final var subscription = new Subscription();
        subscription.setUser(user);
        subscription.setStartDate(LocalDateTime.now());
        subscription.setEndDate(LocalDateTime.now().plusMonths(12));
        subscription.setValid(true);
        return subscriptionRepository.save(subscription);

    }

    @Override
    public Subscription getUsersSubscription(final Long userId) {

        final var subscription = subscriptionRepository.findByUserId(userId);
        if (!isValid(subscription)) {
            subscription.setPlanType(PlanType.FREE);
            subscription.setEndDate(LocalDateTime.now().plusMonths(12));
            subscription.setStartDate(LocalDateTime.now());
        }
        return subscriptionRepository.save(subscription);
    }

    @Override
    public boolean isValid(final Subscription subscription) {
        if (PlanType.FREE.equals(subscription.getPlanType())) {
            return true;
        }
        final var endDate = subscription.getEndDate();
        final var currentTime = LocalDateTime.now();
        return endDate.isAfter(currentTime) || endDate.isEqual(currentTime);
    }

    @Override
    public Subscription upgradeSubscription(final Long userId, final PlanType planType) {
        final var subscription = subscriptionRepository.findByUserId(userId);
        subscription.setPlanType(planType);
        subscription.setStartDate(LocalDateTime.now());
        if (PlanType.ANNUALY.equals(planType)) {
            subscription.setEndDate(LocalDateTime.now().plusMonths(12));
        } else {
            subscription.setEndDate(LocalDateTime.now().plusMonths(1));
        }
        return subscriptionRepository.save(subscription);
    }

}
