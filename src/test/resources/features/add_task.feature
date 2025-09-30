Feature: Add task
  As a user
  I want to add a new task
  So that I can track my work

  Scenario: Add task with valid name
    Given the application is running
    When I send POST /api/tasks with body {"name":"Buy milk"}
    Then the response status should be 201
    And the response should contain "Buy milk"
