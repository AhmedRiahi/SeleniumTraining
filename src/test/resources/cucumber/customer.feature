Feature: Customer Management
    Scenario: Create Customer
        Given user already connected
        When user navigate to "New Customer" page
        And fill formula
        And click on submit button
        Then customer details should be displayed