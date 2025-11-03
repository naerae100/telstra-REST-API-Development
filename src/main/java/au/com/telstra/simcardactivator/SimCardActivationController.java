package au.com.telstra.simcardactivator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimCardActivationController {
    
    private static final Logger logger = LoggerFactory.getLogger(SimCardActivationController.class);
    
    private final ActuatorService actuatorService;
    private final SimCardActivationRepository repository;
    
    public SimCardActivationController(ActuatorService actuatorService, SimCardActivationRepository repository) {
        this.actuatorService = actuatorService;
        this.repository = repository;
    }
    
    @PostMapping("/activate")
    public ResponseEntity<String> activateSimCard(@RequestBody SimCardActivationRequest request) {
        try {
            ActuatorResponse response = actuatorService.activateSimCard(request.getIccid());
            
            boolean success = response.isSuccess();
            
            // Save the activation record to the database
            SimCardActivation activation = new SimCardActivation(
                request.getIccid(),
                request.getCustomerEmail(),
                success
            );
            repository.save(activation);
            
            if (success) {
                logger.info("SIM card activation successful for ICCID: {}", request.getIccid());
                return ResponseEntity.ok("SIM card activated successfully");
            } else {
                logger.warn("SIM card activation failed for ICCID: {}", request.getIccid());
                return ResponseEntity.ok("SIM card activation failed");
            }
        } catch (Exception e) {
            logger.error("Error activating SIM card: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Error activating SIM card: " + e.getMessage());
        }
    }
    
    @GetMapping("/query")
    public ResponseEntity<SimCardActivationResponse> querySimCard(@RequestParam("simCardId") Long simCardId) {
        if (simCardId == null) {
            return ResponseEntity.badRequest().build();
        }
        return repository.findById(simCardId)
            .map(activation -> {
                SimCardActivationResponse response = new SimCardActivationResponse(
                    activation.getIccid(),
                    activation.getCustomerEmail(),
                    activation.getActive()
                );
                return ResponseEntity.ok(response);
            })
            .orElse(ResponseEntity.notFound().build());
    }
}

