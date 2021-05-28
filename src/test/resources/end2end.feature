Feature: End to end test of booking

  @Happy @US_0001
  Scenario: Validate authentication with valid credentials
    Given username is "admin" and password is "password123"
    When I log in herokuBooking
    Then response status should be 200

  @Unhappy @Smoke @Regression
  Scenario Outline: Validate authentication with invalid credentials
    Given username is "<username>" and password is "<password>"
    When I log in herokuBooking
    Then response status should be <statusCode>
    Examples:
      | username | password     | statusCode |
      | a        | passwords123 | 400        |
      # invalid username
      | adminn   | password123  | 400        |
      | admi     | password123  | 400        |
      | admin1   | password123  | 400        |
      |          | password123  | 400        |
      # invalid password
      | admin    | password12   | 400        |
      | admin    | password1234 | 400        |
      | admin    |              | 400        |

  @DeleteBooking
  Scenario: Delete a booking with valid authorization
    Given username is "admin" and password is "password123"
    And I log in herokuBooking
    When I delete booking with bookingId 1
    Then response status should be 200


  Scenario: Create a booking
    Given username is "admin" and password is "password123"
    When I create a booking with details
      """
      {
    "firstname" : "Jim",
    "lastname" : "Brown",
    "totalprice" : 111,
    "depositpaid" : true,
    "bookingdates" : {
        "checkin" : "2018-01-01",
        "checkout" : "2019-01-01"
    },
    "additionalneeds" : "Breakfast"
    }
      """
    Then response status should be 201




