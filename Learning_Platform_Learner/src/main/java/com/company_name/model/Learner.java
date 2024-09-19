package com.company_name.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Learner {
	
	@Id	
	private String LearnerEmail;
	
	private String LearnerPassword;
	
	private String LearnerName;
	
	private long LearnerMobile;
	
	public Learner() {
		super();
	}

	public Learner(String learnerEmail, String learnerPassword, String learnerName, long learnerMobile) {
		super();
		LearnerEmail = learnerEmail;
		LearnerPassword = learnerPassword;
		LearnerName = learnerName;
		LearnerMobile = learnerMobile;
	}

	public String getLearnerEmail() {
		return LearnerEmail;
	}

	public void setLearnerEmail(String learnerEmail) {
		LearnerEmail = learnerEmail;
	}

	public String getLearnerPassword() {
		return LearnerPassword;
	}

	public void setLearnerPassword(String learnerPassword) {
		LearnerPassword = learnerPassword;
	}

	public String getLearnerName() {
		return LearnerName;
	}

	public void setLearnerName(String learnerName) {
		LearnerName = learnerName;
	}

	public long getLearnerMobile() {
		return LearnerMobile;
	}

	public void setLearnerMobile(long learnerMobile) {
		LearnerMobile = learnerMobile;
	}

	
	
}
