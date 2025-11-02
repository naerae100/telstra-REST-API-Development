package au.com.telstra.simcardactivator;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SimCardActivationResponse {
    
    @JsonProperty("iccid")
    private String iccid;
    
    @JsonProperty("customerEmail")
    private String customerEmail;
    
    @JsonProperty("active")
    private Boolean active;
    
    public SimCardActivationResponse() {
    }
    
    public SimCardActivationResponse(String iccid, String customerEmail, Boolean active) {
        this.iccid = iccid;
        this.customerEmail = customerEmail;
        this.active = active;
    }
    
    public String getIccid() {
        return iccid;
    }
    
    public void setIccid(String iccid) {
        this.iccid = iccid;
    }
    
    public String getCustomerEmail() {
        return customerEmail;
    }
    
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
    
    public Boolean getActive() {
        return active;
    }
    
    public void setActive(Boolean active) {
        this.active = active;
    }
}

