Feature: SIM Card Activation
  As a Telstra store employee
  I want to activate SIM cards for customers
  So that customers can use their SIM cards

  Scenario: Successful SIM card activation
    Given I have a valid SIM card with ICCID "1255789453849037777"
    When I submit an activation request for ICCID "1255789453849037777" with customer email "customer@example.com"
    Then the activation should be successful
    And the activation record with ID 1 should have active status true

  Scenario: Failed SIM card activation
    Given I have an invalid SIM card with ICCID "8944500102198304826"
    When I submit an activation request for ICCID "8944500102198304826" with customer email "customer2@example.com"
    Then the activation should fail
    And the activation record with ID 2 should have active status false

