Feature: Add a new pet to the store

  /pet endpoint is for adding a new pet to the store

  @AddNew
  Scenario: Add a pet to the store successfully
    Given the baseUri is "https://petstore.swagger.io/v2"
    And basePath is "/pet"
    When user creates adds a new pet
   """
   {
 "id": 123456788,
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