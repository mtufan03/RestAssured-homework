package restAssured;

import static io.restassured.RestAssured.*;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class CreateBookingAndExtractFields {

    @Test
    public void createBookingandExtractFields() {

        Response response = given()
                .baseUri("https://restful-booker.herokuapp.com")
                .basePath("/booking")
                .contentType("application/json")
                //.header("Content-Type", "application/json")
                //.contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"firstname\" : \"Jim\",\n" +
                        "    \"lastname\" : \"Brown\",\n" +
                        "    \"totalprice\" : 111,\n" +
                        "    \"depositpaid\" : true,\n" +
                        "    \"bookingdates\" : {\n" +
                        "        \"checkin\" : \"2018-01-01\",\n" +
                        "        \"checkout\" : \"2019-01-01\"\n" +
                        "    },\n" +
                        "    \"additionalneeds\" : \"Breakfast\"\n" +
                        "}\n")
                .when()
                .post();

        response.then().log().all();

        int bookingId = response.then().extract().path("bookingid");
        System.out.println("bookingId = " + bookingId);

        String lastname = response.then().extract().path("booking.lastname");
        System.out.println("lastname = " + lastname);

    }


}
