package com.company_name.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company_name.model.Subscription;

//Repository

public interface SubscriptionRepository extends JpaRepository<Subscription, Long>{

}
