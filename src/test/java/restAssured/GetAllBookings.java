package restAssured;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class GetAllBookings {

    Response response;

    @Given("user authorised with username {string} and password {string}")
    public void userAuthorisedWithUsernameAndPassword(String username, String password) {

        given()
                .contentType("application/json")
                .body("{\n" +
                        "    \"username\" : \"" + username + "\",\n" +
                        "    \"password\" : \"" + password + "\"\n" +
                        "}")
                .when()
                .post("https://restful-booker.herokuapp.com/auth")
                .then()
                .statusCode(200);

    }

    @When("user GETs all of the bookings")
    public void userGETsAllOfTheBookings() {

        response = given()
                .get("https://restful-booker.herokuapp.com/booking");

    }

    @Then("response size should be {int}")
    public void responseSizeShouldBe(int expectedResponseSize) {
        response
                .then()
                .body("bookingid", hasSize(expectedResponseSize))
                .log()
                .all();

    }

    @When("user GETs all of the bookings with firstname filter {string}")
    public void userGETsAllOfTheBookingsWithFirstnameFilter(String filterName) {
        response = given()
                .queryParam("firstname", filterName)
                .get("https://restful-booker.herokuapp.com/booking");
        response.prettyPrint();
    }

    @When("user GETs booking by bookingId {int}")
    public void userGETsBookingByBookingId(int bookingId) {

        response = given()
                .pathParam("id", bookingId)
                .get("https://restful-booker.herokuapp.com/booking/{id}");


    }

    @Then("the field totalprice must be £ {int}")
    public void theFieldTotalpriceMustBe£(int totalPrice) {
        int actualTotalPrice = response
                .then()
                .log()
                .all()
                .extract()
                .path("totalprice");

        Assertions.assertEquals(totalPrice, actualTotalPrice);
        //Assertions.assertTrue(totalPrice == actualTotalPrice);
    }
}
