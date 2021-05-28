package restAssured;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Assertions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/*
    Bu project'de, junit yerine TestNG test framework kullanilmis.
    O yuzden yukaridaki tum "import org.junit.jupiter.*" 'lari siliyoruz (@Test annotationlari karismamasi icin)
    Ayrica build.gradle dosyasina asagidaki dependency'i de ekleyelim (Bu dependency'i ekledikten sonra sag ust kosede cikacak
    build isaretine tiklamayi unutmayalim.

   --->>  testImplementation 'org.testng:testng:6.14.3'

 */


import java.io.File;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class TechBeaconSampleProject {

    ResponseSpecification checkStatusCodeAndContentType =
            new ResponseSpecBuilder().
                    expectStatusCode(200).
                    expectContentType(ContentType.JSON).
                    build();


    // Check whether response size equals expected
    @Test
    public void test_NumberOfCircuitsFor2017Season_ShouldBe20() {
//        given().
//                when().
//                get("http://ergast.com/api/f1/2017/circuits.json").
//                then().
//                assertThat().
//                body("MRData.CircuitTable.Circuits.circuitId", hasSize(20));

        //Bu methodun yukaridakinden farki, url'inin baseUri ve basePath olarak ayri yazilmis olmasi
        //Bir alternatif gostermesi icin yazdim.
        given()
                .baseUri("http://ergast.com/api")
                .basePath("/f1/2017/circuits.json")
                .get()
                .then()
                .assertThat()
                .body("MRData.CircuitTable.Circuits.circuitId", hasSize(20)).log().all();
    }

    // Extract a field value from the response E.g. 1
    @Test
    public void test_NumberOfCircuitsFor2017Season_ShouldBe20_alternative() {
        Response response = given().
                when().
                get("http://ergast.com/api/f1/2017/circuits.json");

        response.then().
                assertThat().
                body("MRData.CircuitTable.Circuits.circuitId", hasSize(20)).log().all();

        //Get a specific circuit ID
        String circuitId = response
                .then()
                .extract()
                .path("MRData.CircuitTable.Circuits[0].circuitId");

        System.out.println("circuitId = " + circuitId);
    }

    //bu method ile, yaptigimiz Get request sonrasi donen response body icerisinden
    // firstname field'indaki value'yu almis(extract) etmis olduk.
    @Test
    public void getCheckInDate() {

        String checkInDate = given()
                .baseUri("https://restful-booker.herokuapp.com")
                .basePath("/booking/1")
                .get()
                .then()
                .statusCode(200)
                .extract()
                .path("bookingdates.checkin");

        System.out.println("checkInDate = " + checkInDate);
        String firstname = given()
                .baseUri("https://restful-booker.herokuapp.com")
                .basePath("/booking/1")
                .get()
                .then()
                .statusCode(200)
                .extract()
                .path("firstname");

        System.out.println("firstname = " + firstname);
    }


    // Extract a field value from the response E.g. 2
    //Bu metod icerisinde donen response body'den token'i aliyoruz.
    @Test
    public void getNewToken() {
        Response response = given().
                baseUri("https://restful-booker.herokuapp.com").
                basePath("/auth").
                contentType("application/json").
                body("{\n" +
                        "    \"username\": \"admin\",\n" +
                        "    \"password\": \"password123\"\n" +
                        "}").
                post();
// body() is used with POST, PUT, DELETE requests
        String token = response
                .then()
                .extract()
                .path("token");
        System.out.println("token = " + token);

        //verifying response token
        Assertions.assertTrue(token == "123");
    }

    // Verifying response data
    @Test
    public void test_ResponseHeaderData_ShouldBeCorrect() {
        given().
                when().
                get("http://ergast.com/api/f1/2017/circuits.json").
                then().
                assertThat().
                statusCode(200).
                and().
                contentType(ContentType.JSON).
                and().
                header("Content-Length", equalTo("4551"));
    }

    //Bu method, response time i, expected response time ile karsilastirabilmek icin;
    @Test
    public void measureResponseTime() {

        //URL'e get request'i gonderip donen response'u response variable'i icine tanimladik
        Response response = given().
                when().
                get("http://ergast.com/api/f1/2017/circuits.json");

        //Yukarida tanimlanan response'dan, bu response'un donmesinin ne kadar surdugunu aldik ve yazdirdik gormek icin.
        long timeInMs = response.time();
        System.out.println("timeInMs = " + timeInMs);

        //Burada ise donen response 2000L milisaniye (2 saniye)'ye esit ya da daha az mi diye karsilastiriyoruz.
        response.
                then().
                time(lessThanOrEqualTo(2000L));

    }

    //upload file
    @Test
    public void uploadFile() {
        given()
                .baseUri("https://petstore.swagger.io/v2/pet/{petId}/uploadImage")
                .basePath("/pet/{petId}/uploadImage")
                .pathParam("petId", 12121212)
                .multiPart(new File("src/test/resources/mavi.jpeg"))
                .log()
                .all()
                .post()
                //.post("https://petstore.swagger.io/v2/pet/12121212/uploadImage")
                .then()
                .statusCode(200)
                .log()
                .all();
    }

    @Test
    public void test_Md5CheckSumForTest_ShouldBe098f6bcd4621d373cade4e832627b4f6() {

        String originalText = "test";
        String expectedMd5CheckSum = "098f6bcd4621d373cade4e832627b4f6";

        given().
                param("text", originalText).
                when().
                get("http://md5.jsontest.com").
                then().
                assertThat().
                body("md5", equalTo(expectedMd5CheckSum))
                .log()
                .body();
    }

    @Test
    public void test_NumberOfCircuits_ShouldBe20_Parameterized() {

        String season = "2017";
        int numberOfRaces = 20;

        given().
                pathParam("raceSeason", season).
                when().
                get("http://ergast.com/api/f1/{raceSeason}/circuits.json").
                then().
                assertThat().
                body("MRData.CircuitTable.Circuits.circuitId", hasSize(numberOfRaces)).log().all();
    }

    @DataProvider(name = "seasonsAndNumberOfRaces")
    public Object[][] createTestDataRecords() {
        return new Object[][]{
                {"2017", 20},
                {"2016", 21},
                {"1966", 9}
        };
    }


    //Eger 'dataProvider' variable kirmizi geliyorsa, yukaridan junit import'lari sildigimizden ve
    // import org.testng.annotations.DataProvider; ekledigimizden emin olalim
    @Test(dataProvider = "seasonsAndNumberOfRaces")
    public void test_NumberOfCircuits_ShouldBe_DataDriven(String season, int numberOfRaces) {

        given().
                pathParam("raceSeason", season).
                when().
                get("http://ergast.com/api/f1/{raceSeason}/circuits.json").
                then().
                assertThat().
                body("MRData.CircuitTable.Circuits.circuitId", hasSize(numberOfRaces)).log().body();
    }

    @Test
    public void test_APIWithBasicAuthentication_ShouldBeGivenAccess() {

        given().
                auth().
                preemptive().
                basic("username", "password").
                when().
                get("http://path.to/basic/secured/api").
                then().
                assertThat().
                statusCode(200);
    }

    @Test
    public void test_APIWithOAuth2Authentication_ShouldBeGivenAccess() {

        given().
                auth().
                oauth2("YOUR_AUTHENTICATION_TOKEN_GOES_HERE").
                when().
                get("http://path.to/oath2/secured/api").
                then().
                assertThat().
                statusCode(200).log().body();
    }

    @Test
    public void test_ScenarioRetrieveFirstCircuitFor2017SeasonAndGetCountry_ShouldBeAustralia() {

        // First, retrieve the circuit ID for the first circuit of the 2017 season
        String circuitId = given().
                when().
                get("http://ergast.com/api/f1/2017/circuits.json").
                then().
                extract().
                path("MRData.CircuitTable.Circuits.circuitId[0]");

        // Then, retrieve the information known for that circuit and verify it is located in Australia
        given().
                pathParam("circuitId", circuitId).
                when().
                get("http://ergast.com/api/f1/circuits/{circuitId}.json").
                then().
                assertThat().
                body("MRData.CircuitTable.Circuits.Location[0].country", equalTo("Australia"));
    }

    @Test
    public void test_NumberOfCircuits_ShouldBe20_UsingResponseSpec() {

        given().
                when().
                get("http://ergast.com/api/f1/2017/circuits.json").
                then().
                assertThat().
                spec(checkStatusCodeAndContentType).
                and().
                body("MRData.CircuitTable.Circuits.circuitId", hasSize(20));
    }
}
