package com.company_name.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


//DTO Response Class
public class CreatePaymentResponse {
	
	@NotNull
	@Min(3)
	private Integer amount;
	
	@NotNull
    @Size(min = 5, max = 200)
    private String featureRequest;
	
	
	public CreatePaymentResponse() {
		super();
	}

	public CreatePaymentResponse(@NotNull @Min(3) Integer amount, @NotNull @Size(min = 5, max = 200) String featureRequest) {
		super();
		this.amount = amount;
		this.featureRequest = featureRequest;
	}

	public CreatePaymentResponse(String featureRequest) {
		super();
		this.featureRequest = featureRequest;
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
