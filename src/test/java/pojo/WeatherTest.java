package pojo;

import io.restassured.RestAssured;
import models.weather.Weather;
import models.weather.WeatherList;
import models.weather.WeatherResponse;
import org.testng.annotations.Test;
import utils.TestDataUtil;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class WeatherTest {
    @Test
    public void getWeather() {
        RestAssured.baseURI = "http://api.openweathermap.org";

        WeatherResponse response = given().log().all().header("Content-Type", "application/json")
                .when().get("data/2.5/find?lat=55.5&lon=37.5&cnt=10&appid=secret").as(WeatherResponse.class);

        List<String> names = response.getList().stream().map(WeatherList::getName).collect(Collectors.toList());
        System.out.println(names);
    }
}
