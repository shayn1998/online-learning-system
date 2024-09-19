package com.company_name.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company_name.model.Subscription;
import com.company_name.repository.SubscriptionRepository;

//Service Layer Implementation class

@Service
public class SubscriptionServiceImpl implements SubscriptionService{
	
	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Override
	public Subscription save(Subscription subscription) {
	
		return subscriptionRepository.save(subscription);
		
	}

	@Override
	public boolean existsById(long subscriptionId) {
		// TODO Auto-generated method stub
		return subscriptionRepository.existsById(subscriptionId);
		
	}

	@Override
	public void deleteById(long subscriptionId) {
		// TODO Auto-generated method stub
		subscriptionRepository.deleteById(subscriptionId);
		
	}

	@Override
	public Optional<Subscription> findById(long subscriptionId) {
		// TODO Auto-generated method stub
		return subscriptionRepository.findById(subscriptionId);
		
	}

	@Override
	public List<Subscription> findAll() {
		// TODO Auto-generated method stub
		return subscriptionRepository.findAll();
		
	}

}
