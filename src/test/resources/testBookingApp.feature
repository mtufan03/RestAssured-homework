Feature: Get bookings

  Background:
    Given user authorised with username "admin" and password "password123"

  @NumberOfBookings
  Scenario: Get all bookings and assert number of bookings
    #Given user authorised with username "admin" and password "password123"

    #You can use this url "https://restful-booker.herokuapp.com/booking" to get all bookingIds
    When user GETs all of the bookings

    #You can use .body("bookingid",hasSize(?)) method in your request, but where? (please refer to techbeacon article for an example method)
    Then response size should be 10

  @Filter
  Scenario: Get all bookings and filter with firstname
   # Given user authorised with username "admin" and password "password123"

    #You can use this url "https://restful-booker.herokuapp.com/booking" to get all bookingIds, but you will need to add a query parameter
    When user GETs all of the bookings with firstname filter "Sally"
    Then response size should be 2

  @AssertPrice
  Scenario: Assert total price paid by customers
   # Given user authorised with username "admin" and password "password123"
    When user GETs booking by bookingId 1
    Then the field totalprice must be £ 111



