package dynamicpaload;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DynamicJson {

    @Test
    public void addBook() {
        RestAssured.baseURI = "http://216.10.245.166";

        String response = given().log().all().header("Content-Type", "application/json")
                .body(Payload.addBook("dsfdsf", "345435")).when().post("Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath jp = new JsonPath(response);
        System.out.println(jp.getString("ID"));
    }

    @Test(dataProvider="bookData")
    public void addBook2(String isbn, String aisle) {
        RestAssured.baseURI = "http://216.10.245.166";

        String response = given().log().all().header("Content-Type", "application/json")
                .body(Payload.addBook(isbn, aisle)).when().post("Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        JsonPath jp = new JsonPath(response);
        System.out.println(jp.getString("ID"));
    }

    @DataProvider(name="bookData")
    public Object[][] getBookData() {
        return new Object[][] { {"asdf","123423"}, {"sdfsdf", "32432"}, {"oewrweo", "slfjsd"} };
    }
}
