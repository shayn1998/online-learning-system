//CheckOutForm class for saving form payment details entered by the User in Index.html Page

package com.company_name.model;

import jakarta.validation.constraints.*;

public class CheckoutForm {

    @NotNull
    @Min(4)
    private Integer amount;

    @NotNull
    @Size(min = 5,max = 200)
    private String featureRequest;

    @Email
    private String email;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
