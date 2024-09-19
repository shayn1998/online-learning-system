package com.company_name.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

//DTO Request class

public class CreatePayment {

	@NotNull
	@Min(3)
	private Integer amount;
	
	@NotNull
    @Size(min = 5, max = 200)
    private String featureRequest;
	
	public CreatePayment(@NotNull @Min(3) Integer amount, @NotNull @Size(min = 5, max = 200) String featureRequest) {
		super();
		this.amount = amount;
		this.featureRequest = featureRequest;
	}

	public CreatePayment() {
		super();
	}

	public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getFeatureRequest() {
        return featureRequest;
    }

    public void setFeatureRequest(String featureRequest) {
        this.featureRequest = featureRequest;
    }
}
