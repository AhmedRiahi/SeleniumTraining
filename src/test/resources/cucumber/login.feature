Feature: Login
    Scenario: Correct Login
        Given username "mngr178533" and password "uvyruqU"
        When user navigate to "http://www.demo.guru99.com/V4/index.php"
        And user types username and password
        And user click on Login button
        Then user user should be logged correctly
