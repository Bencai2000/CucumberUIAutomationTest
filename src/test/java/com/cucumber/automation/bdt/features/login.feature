Feature: login Page
  In order to test login page
  As a Registered user
  I want to specify the login conditions

Background: 
	Given test environment is ready
	
  @testme
  Scenario: Go to Login Page
    Given user is on TAB home page
    When user clicks on Login button
    Then user is displayed login screen

  @testme
  Scenario: Go to Login Page
    Given user is on TAB home page
    When user clicks on Login button
    Then user is displayed login screen
    
  @testme
  Scenario: Go to Login Page
    Given user is on TAB home page
    When user clicks on Login button
    Then user is displayed login screen

  Scenario: login without username and password
    Given user is on github homepage
    When user clicks on Sign in button
    Then user is displayed login screen
    When user clicks Sign in button
    Then user gets an error message "Incorrect username or password."
    