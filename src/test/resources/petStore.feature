Feature: Add a new pet to the store

  /pet endpoint is for adding a new pet to the store

  Scenario: Add a pet to the store successfully
    Given the baseUri is "https://petstore.swagger.io/v2"
    And basePath is "/pet"
    When user creates adds a new pet
    """
    {
  "id": 12345,
  "category": {
    "id": 10,
    "name": "Dogs"
  },
  "name": "Mavi",
  "photoUrls": [
    "string"
  ],
  "tags": [
    {
      "id": 5,
      "name": "New"
    }
  ],
  "status": "available"
}
    """
    Then status code must be 200
    And new pet's name must be "Mavi"
    And tags name must be "Neww"

  @Update
  Scenario: Update an existing pet (PUT)
    Given the baseUri is "https://petstore.swagger.io/v2"
    And basePath is "/pet"
    When user updates an existing pet
      """
      {
  "id": 12345,
  "category": {
    "id": 11,
    "name": "string"
  },
  "name": "Boncuk",
  "photoUrls": [
    "string"
  ],
  "tags": [
    {
      "id": 6,
      "name": "Cheerful"
    }
  ],
  "status": "pending"
}
      """
    Then status code must be 200
    And dog's new status must be "pending"

  @Delete
  Scenario: Delete pet (DELETE)
    Given the baseUri is "https://petstore.swagger.io/v2"
    And basePath is "/pet/{petId}"
    And dog's ID is 12345
    When I delete the dog
    Then status code must be 200


  Scenario: Assert the dog is no longer in the store (GET)
    #You will need to make a GET request to this url: https://petstore.swagger.io/v2/pet/{petId}
    # Remember to add a path parameter to your request

    Given the pet's id is 12345
    When I get the pet by Id
    Then status code must be 404
    And message must be "Pet is gone"





