package au.com.telstra.simcardactivator;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ActuatorRequest {
    
    @JsonProperty("iccid")
    private String iccid;
    
    public ActuatorRequest() {
    }
    
    public ActuatorRequest(String iccid) {
        this.iccid = iccid;
    }
    
    public String getIccid() {
        return iccid;
    }
    
    public void setIccid(String iccid) {
        this.iccid = iccid;
    }
}

