package au.com.telstra.simcardactivator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class ActuatorService {
    
    private static final Logger logger = LoggerFactory.getLogger(ActuatorService.class);
    private static final String ACTUATOR_URL = "http://localhost:8444/actuate";
    
    private final RestTemplate restTemplate;
    
    public ActuatorService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    public ActuatorResponse activateSimCard(String iccid) throws RestClientException {
        ActuatorRequest request = new ActuatorRequest(iccid);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<ActuatorRequest> entity = new HttpEntity<>(request, headers);
        
        logger.info("Calling actuator service at: {}", ACTUATOR_URL);
        logger.debug("Request payload: {{\"iccid\": \"{}\"}}", iccid);
        
        try {
            ActuatorResponse response = restTemplate.postForObject(ACTUATOR_URL, entity, ActuatorResponse.class);
            logger.info("Actuator response: {{\"success\": {}}}", response != null ? response.isSuccess() : "null");
            return response;
        } catch (RestClientException e) {
            throw new RestClientException(
                String.format("Failed to activate SIM card with ICCID %s via actuator service at %s: %s", 
                    iccid, ACTUATOR_URL, e.getMessage()), e);
        }
    }
}

