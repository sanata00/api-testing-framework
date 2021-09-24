package basics;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestDataUtil;

public class SumValidation {

    @Test
    public void sumOfCourses() {
        JsonPath jp = new JsonPath(TestDataUtil.getDataFromFile("ComplexJson.json"));

        int expectedPurchaseAmount = jp.getInt("dashboard.purchaseAmount");

        int numberOfCourses = jp.getInt("courses.size()");

        int actualPurchaseAmount = 0;
        for (int i=0; i<numberOfCourses; i++) {
            int price = jp.getInt(String.format("courses[%d].price", i));
            int numberOfCopies = jp.getInt(String.format("courses[%d].copies", i));
            actualPurchaseAmount += price * numberOfCopies;
        }
        Assert.assertEquals(actualPurchaseAmount, expectedPurchaseAmount);
    }
}
