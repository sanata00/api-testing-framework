import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import utils.TestDataUtil;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static utils.TestDataUtil.getDataFromFile;


public class Basics {
    public static void main(String[] args) {
        String newAddress = "70 Summer walk, USA";

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(getDataFromFile("AddPlace.json"))
        .when().post("maps/api/place/add/json")
        .then().log().all().assertThat().statusCode(200)
                .body("scope", equalTo("APP"))
                .header("Server", "Apache/2.4.18 (Ubuntu)")
                .extract().body().asString();

        JsonPath jsonPath = new JsonPath(response);
        String placeId = jsonPath.getString("place_id");

        given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body("{\n" +
                        "\"place_id\":\"" + placeId + "\",\n" +
                        "\"address\":\"" + newAddress + "\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}")
        .when().put("maps/api/place/update/json")
        .then().log().all().assertThat().statusCode(200)
                .body("msg", equalTo("Address successfully updated"));

        String getResponse = given().log().all().queryParams(Map.of("place_id", placeId, "key", "qaclick123"))
        .when().get("maps/api/place/get/json")
        .then().log().all().assertThat().statusCode(200)
                .body("address", equalTo(newAddress))
        .extract().body().asString();

        String actualAddress = new JsonPath(getResponse).getString("address");
        Assert.assertEquals(actualAddress, newAddress);


    }
}
