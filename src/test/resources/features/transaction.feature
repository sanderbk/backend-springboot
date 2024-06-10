Feature: Transaction

  Scenario: Post request to create a new transaction
    Given I have valid jwt to create new transaction
    When I call endpoint with post request to create new transaction
    Then I receive a status of 201
    And I receive new created transaction

  Scenario: Get request to get one transaction with specific id
    Given I have valid jwt to get one transaction with specific id
    When I call endpoint with get request to get one transaction
    Then I receive http code 200 ok for one transaction
    And I receive one transaction with the specified id

  Scenario: Put request to update transaction
    Given I have valid jwt to update transaction
    When I call endpoint with put request to update transaction
    Then I receive a status of 200
    And I receive the updated transaction

  Scenario: Delete request to delete transaction
    Given I have valid jwt to delete transaction
    When I call endpoint with delete request to delete transaction
    Then I receive a status of 202
    And The transaction has been deleted