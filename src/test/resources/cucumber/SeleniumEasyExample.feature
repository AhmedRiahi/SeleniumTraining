Feature: seleniumeasy example
    Scenario: Single Input Field
        Given an input message "hello selenium"
        When user click on <show message> button
        Then input message should be displayed