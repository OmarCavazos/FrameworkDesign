@tag
Feature: Error Validation
  I want to use this template for my feature file
	
	Background:
	

  @ErrorValidation
  Scenario: Title of your scenario
  	Given I landed on Ecommerce Page
    When Logged in with urdername <name> and <password> 
    Then "Incorrect email or password." message is displayed


    Examples: 
      | name  						| 		password			|
      | test969@test.com	|      @Test123			|