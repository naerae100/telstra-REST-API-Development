package stepDefinitions;

import au.com.telstra.simcardactivator.SimCardActivator;
import au.com.telstra.simcardactivator.SimCardActivationRequest;
import au.com.telstra.simcardactivator.SimCardActivationResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import org.junit.Assert;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = SimCardActivator.class, loader = SpringBootContextLoader.class)
public class SimCardActivatorStepDefinitions {
    @Autowired
    private TestRestTemplate restTemplate;
    
    private String currentIccid;
    private String currentCustomerEmail;
    private ResponseEntity<String> activationResponse;
    private ResponseEntity<SimCardActivationResponse> queryResponse;
    
    @Given("I have a valid SIM card with ICCID {string}")
    public void i_have_a_valid_sim_card_with_iccid(String iccid) {
        this.currentIccid = iccid;
    }
    
    @Given("I have an invalid SIM card with ICCID {string}")
    public void i_have_an_invalid_sim_card_with_iccid(String iccid) {
        this.currentIccid = iccid;
    }
    
    @When("I submit an activation request for ICCID {string} with customer email {string}")
    public void i_submit_an_activation_request_for_iccid_with_customer_email(String iccid, String email) {
        this.currentIccid = iccid;
        this.currentCustomerEmail = email;
        
        SimCardActivationRequest request = new SimCardActivationRequest(iccid, email);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<SimCardActivationRequest> entity = new HttpEntity<>(request, headers);
        
        this.activationResponse = restTemplate.exchange(
            "http://localhost:8080/activate",
            HttpMethod.POST,
            entity,
            String.class
        );
    }
    
    @Then("the activation should be successful")
    public void the_activation_should_be_successful() {
        Assert.assertNotNull(activationResponse);
        Assert.assertEquals(200, activationResponse.getStatusCodeValue());
        Assert.assertNotNull(activationResponse.getBody());
        Assert.assertTrue(activationResponse.getBody().contains("successfully"));
    }
    
    @Then("the activation should fail")
    public void the_activation_should_fail() {
        Assert.assertNotNull(activationResponse);
        Assert.assertEquals(200, activationResponse.getStatusCodeValue());
        Assert.assertNotNull(activationResponse.getBody());
        Assert.assertTrue(activationResponse.getBody().contains("failed"));
    }
    
    @And("the activation record with ID {int} should have active status true")
    public void the_activation_record_with_id_should_have_active_status_true(int recordId) {
        queryResponse = restTemplate.getForEntity(
            "http://localhost:8080/query?simCardId=" + recordId,
            SimCardActivationResponse.class
        );
        
        Assert.assertNotNull(queryResponse);
        Assert.assertEquals(200, queryResponse.getStatusCodeValue());
        Assert.assertNotNull(queryResponse.getBody());
        Assert.assertEquals(currentIccid, queryResponse.getBody().getIccid());
        Assert.assertEquals(currentCustomerEmail, queryResponse.getBody().getCustomerEmail());
        Assert.assertTrue(queryResponse.getBody().getActive());
    }
    
    @And("the activation record with ID {int} should have active status false")
    public void the_activation_record_with_id_should_have_active_status_false(int recordId) {
        queryResponse = restTemplate.getForEntity(
            "http://localhost:8080/query?simCardId=" + recordId,
            SimCardActivationResponse.class
        );
        
        Assert.assertNotNull(queryResponse);
        Assert.assertEquals(200, queryResponse.getStatusCodeValue());
        Assert.assertNotNull(queryResponse.getBody());
        Assert.assertEquals(currentIccid, queryResponse.getBody().getIccid());
        Assert.assertEquals(currentCustomerEmail, queryResponse.getBody().getCustomerEmail());
        Assert.assertFalse(queryResponse.getBody().getActive());
    }
}