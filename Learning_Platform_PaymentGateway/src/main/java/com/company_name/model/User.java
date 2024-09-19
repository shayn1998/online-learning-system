//User class for saving the details of the User who is making the Payment

package com.company_name.model;

public class User {
	
	private String userId;
	
	private String userPassword;
	
	private String userName;
	
	private long userMobile;

	public User() {
		super();
	}

	public User(String userId, String userPassword, String userName, long userMobile) {
		super();
		this.userId = userId;
		this.userPassword = userPassword;
		this.userName = userName;
		this.userMobile = userMobile;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(long userMobile) {
		this.userMobile = userMobile;
	}
	
	
	
}
