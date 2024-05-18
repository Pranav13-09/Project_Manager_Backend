package com.org.projectmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.projectmanager.model.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Subscription findByUserId(Long userId);
}
