package restAssured;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class CreateAndExtractFieldsHomework_FromSG {

    Response response;
    int bookingID;

    @Test
    public void bookingId() {

        String newBooking = "{\n" +
                "\"firstname\" : \"Jim\",\n" +
                "\"lastname\" : \"Brown\",\n" +
                "\"totalprice\" : 111,\n" +
                "\"depositpaid\" : true,\n" +
                "\"bookingdates\" : {\n" +
                "\"checkin\" : \"2018-01-01\",\n" +
                "\"checkout\" : \"2019-01-01\"\n" +
                "},\n" +
                "\"additionalneeds\" : \"Breakfast\"\n" +
                "}";

        response = given()
                .auth().basic("admin", "password123")
                .baseUri("https://restful-booker.herokuapp.com")
                .basePath("/booking")
                .contentType("application/json")
                .body(newBooking)
                .when()
                .post()
                .then().statusCode(200).extract().response();
        response.prettyPrint();

        bookingID = response.then().extract().path("bookingid");

    }

    @Test
    public void deleteBookingId() {

        Response tokenResponse = given()
                .contentType("application/json")
                .body("{\n" +
                        "    \"username\" : \"admin\",\n" +
                        "    \"password\" : \"password123\"\n" +
                        "}")
                .post("https://restful-booker.herokuapp.com/auth");

        String token = tokenResponse
                .then()
                .extract()
                .path("token");

        System.out.println("token = " + token);


        given()
                //.auth().basic("admin", "password123")
                .header("Cookie", "token=" + token)
                .contentType("application/json")
                .when()
                .log()
                .all()
                .delete("https://restful-booker.herokuapp.com/booking/" + bookingID).then().log().all();

    }


}

