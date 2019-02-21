Feature: Customer Management
    Scenario: Create Customer
        Given user already connected
        When user click on New Customer button
        And user set customer name as "The name"
        And Gender as "Male"
        And Date of birth as "01/01/1990"
        And adress as "address"
        And city as "city"
        And state as "state"
        And pin as "123456"
        And mobile number as "555"
        And email as "me@me.com"
        And password as "pass"
        And click on submit button
        Then customer details should be displayed