package com.company_name.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Topics {
	
	private String topicName;
	
	int numberOfPages;

	public Topics() {
		
		super();
	
	}

	public Topics(String topicName, int numberOfPages) {
		
		super();
		
		this.topicName = topicName;
		
		this.numberOfPages = numberOfPages;
		
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public int getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}
	
	

}
