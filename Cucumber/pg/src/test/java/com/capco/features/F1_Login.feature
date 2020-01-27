@ExecuteAll
Feature:F1_Login
 
   
  Scenario Outline: <TestCase>
    Given user Launches PiggyBank application
    When user login "uName" as username and "pwd" as password
    And user click on "signInBtn" as signIn
    Then verify user logs in successfully 
    
    Examples:
    |TestCase|
    |ValidLogin|
    
    
    
  Scenario Outline: <TestCase>
    Given user Launches PiggyBank application
    When user enter login "uName" as username and "pwd" as password
    And user click on "signInBtn" as signIn
    Then verify the invalid login 
    
    Examples:
    |TestCase|
    |Invalid_BlankLogin|