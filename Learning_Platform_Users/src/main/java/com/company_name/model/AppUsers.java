package com.company_name.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

//Model Class

@Entity
public class AppUsers {
	
	@Id	
	private String AppUsersEmail;
	
	private String AppUsersPassword;
	
	private String AppUsersName;
	
	private long AppUsersMobile;
	
	private long subscribedTo;
	
	private boolean bookedSubscription;
	
	public AppUsers() {
		super();
	}

	public AppUsers(String appUsersEmail, String appUsersPassword, String appUsersName, long appUsersMobile,
			long subscribedTo, boolean bookedSubscription) {
		super();
		AppUsersEmail = appUsersEmail;
		AppUsersPassword = appUsersPassword;
		AppUsersName = appUsersName;
		AppUsersMobile = appUsersMobile;
		this.subscribedTo = subscribedTo;
		this.bookedSubscription = bookedSubscription;
	}

	public String getAppUsersEmail() {
		return AppUsersEmail;
	}

	public void setAppUsersEmail(String appUsersEmail) {
		AppUsersEmail = appUsersEmail;
	}

	public String getAppUsersPassword() {
		return AppUsersPassword;
	}

	public void setAppUsersPassword(String appUsersPassword) {
		AppUsersPassword = appUsersPassword;
	}

	public String getAppUsersName() {
		return AppUsersName;
	}

	public void setAppUsersName(String appUsersName) {
		AppUsersName = appUsersName;
	}

	public long getAppUsersMobile() {
		return AppUsersMobile;
	}

	public void setAppUsersMobile(long appUsersMobile) {
		AppUsersMobile = appUsersMobile;
	}

	public long getSubscribedTo() {
		return subscribedTo;
	}

	public void setSubscribedTo(long subscribedTo) {
		this.subscribedTo = subscribedTo;
	}

	public boolean isBookedSubscription() {
		return bookedSubscription;
	}

	public void setBookedSubscription(boolean bookedSubscription) {
		this.bookedSubscription = bookedSubscription;
	}
	
}