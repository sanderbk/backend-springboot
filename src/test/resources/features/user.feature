Feature: User

  Scenario: Get request to /users will result in List<User> of length 1
    Given I have a valid JWT
    When I call the users endpoint
    Then I receive a status of 200
    And Get a List<User> of length 1