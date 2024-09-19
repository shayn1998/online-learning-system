package com.company_name.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//Model Class

@Entity
public class Subscription {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long subscriptionId;
	
	private double subscriptionPrice;
	
	private String subscriptionName;
	
	private int durationInMonths;
	
	public Subscription() {
		super();
	}

	public Subscription(long subscriptionId, double subscriptionPrice, String subscriptionName, int durationInMonths) {
		super();
		this.subscriptionId = subscriptionId;
		this.subscriptionPrice = subscriptionPrice;
		this.subscriptionName = subscriptionName;
		this.durationInMonths = durationInMonths;
	}

	public long getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public double getSubscriptionPrice() {
		return subscriptionPrice;
	}

	public void setSubscriptionPrice(double subscriptionPrice) {
		this.subscriptionPrice = subscriptionPrice;
	}

	public String getSubscriptionName() {
		return subscriptionName;
	}

	public void setSubscriptionName(String subscriptionName) {
		this.subscriptionName = subscriptionName;
	}

	public int getDurationInMonths() {
		return durationInMonths;
	}

	public void setDurationInMonths(int durationInMonths) {
		this.durationInMonths = durationInMonths;
	}

	@Override
	public String toString() {
		return "Subscription [subscriptionId=" + subscriptionId + ", subscriptionPrice=" + subscriptionPrice
				+ ", subscriptionName=" + subscriptionName + ", durationInMonths=" + durationInMonths + "]";
	}
	
	
	
}
