package com.company_name.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//Author model Class


@Entity
public class Author{
	
	@Id
	private String authorEmailId;
	
	private String authorName;
	
	private String authorPassword;

	public Author() {
		super();
	}

	public Author(String authorEmailId, String authorName, String authorPassword) {
		super();
		this.authorEmailId = authorEmailId;
		this.authorName = authorName;
		this.authorPassword = authorPassword;
	}

	public String getAuthorEmailId() {
		return authorEmailId;
	}

	public void setAuthorEmailId(String authorEmailId) {
		this.authorEmailId = authorEmailId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getAuthorPassword() {
		return authorPassword;
	}

	public void setAuthorPassword(String authorPassword) {
		this.authorPassword = authorPassword;
	}

	

}