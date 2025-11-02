package au.com.telstra.simcardactivator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimCardActivationController {
    
    private final ActuatorService actuatorService;
    
    public SimCardActivationController(ActuatorService actuatorService) {
        this.actuatorService = actuatorService;
    }
    
    @PostMapping("/activate")
    public ResponseEntity<String> activateSimCard(@RequestBody SimCardActivationRequest request) {
        try {
            ActuatorResponse response = actuatorService.activateSimCard(request.getIccid());
            
            if (response.isSuccess()) {
                System.out.println("SIM card activation successful for ICCID: " + request.getIccid());
                return ResponseEntity.ok("SIM card activated successfully");
            } else {
                System.out.println("SIM card activation failed for ICCID: " + request.getIccid());
                return ResponseEntity.ok("SIM card activation failed");
            }
        } catch (Exception e) {
            System.err.println("Error activating SIM card: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error activating SIM card: " + e.getMessage());
        }
    }
}

