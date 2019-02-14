Feature: seleniumeasy example
    Scenario: Single Input Field
        Given an input message "hello selenium"
        When user click on <show message> button
        Then input message should be displayed

    Scenario: Drag and drop
        Given item "Draggable 1"
        When user drag and drop the item
        Then item should be removed from draggable list
        And item should be added to dropped item list