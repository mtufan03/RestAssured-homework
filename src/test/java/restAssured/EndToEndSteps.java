package restAssured;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class EndToEndSteps {
    RequestSpecification request;
    Response response;
    String token;
    int bookingId;



    @Given("username is {string} and password is {string}")
    public void usernameIsAndPasswordIs(String username, String password) {

        request = given()
                .contentType("application/json")
                .body("{\n" +
                        "    \"username\" : \"" + username + "\",\n" +
                        "    \"password\" : \"" + password + "\"\n" +
                        "}");
    }

    @When("I log in herokuBooking")
    public void iLogInHerokuBooking() {
        response = request
                .when()
                .post("https://restful-booker.herokuapp.com/auth");

        token = response
                .then()
                .extract()
                .path("token");
        System.out.println("token = " + token);

    }

    @Then("response status should be {int}")
    public void responseStatusShouldBe(int statusCode) {
        response
                .then()
                .statusCode(statusCode).log().all();
    }

    @When("I delete booking with bookingId {int}")
    public void iDeleteBookingWithBookingId(int bookingId) {
        response = given()
                .header("Cookie", "token=" + token)
                .log()
                .all()
                .delete("https://restful-booker.herokuapp.com/booking/" + bookingId);


        response
                .then()
                .log()
                .all();
    }

    @When("I create a booking with details")
    public void iCreateABookingWithDetails(String body) {
        Response bookingResponse = given()
                .baseUri("https://restful-booker.herokuapp.com")
                .basePath("/booking")
                .contentType("application/json")
                //.header("Content-Type", "application/json")
                //.contentType(ContentType.JSON)
                .body(body)
                .when()
                .post();

        bookingId = bookingResponse
                .then()
                .extract()
                .path("bookingid");

        response = bookingResponse;

    }


}
