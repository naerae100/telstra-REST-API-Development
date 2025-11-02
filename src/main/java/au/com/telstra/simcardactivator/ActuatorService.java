package au.com.telstra.simcardactivator;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ActuatorService {
    
    private static final String ACTUATOR_URL = "http://localhost:8444/actuate";
    
    private final RestTemplate restTemplate;
    
    public ActuatorService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    public ActuatorResponse activateSimCard(String iccid) {
        ActuatorRequest request = new ActuatorRequest(iccid);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<ActuatorRequest> entity = new HttpEntity<>(request, headers);
        
        return restTemplate.postForObject(ACTUATOR_URL, entity, ActuatorResponse.class);
    }
}

