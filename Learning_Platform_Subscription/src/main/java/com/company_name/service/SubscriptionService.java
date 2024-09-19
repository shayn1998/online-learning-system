package com.company_name.service;

import java.util.List;
import java.util.Optional;

import com.company_name.model.Subscription;

public interface SubscriptionService {
	
	
	Subscription save(Subscription subscription);
	
	boolean existsById(long subscriptionId);

	void deleteById(long subscriptionId);

	Optional<Subscription> findById(long object);

	List<Subscription> findAll();

}
