import io.restassured.path.json.JsonPath;
import utils.TestDataUtil;

public class ParseComplexJson {
    public static void main(String[] args) {

        JsonPath jp = new JsonPath(TestDataUtil.getDataFromFile("ComplexJson.json"));
        int numberOfCourses = jp.getInt("courses.size()");
        int purchaseAmount = jp.getInt("dashboard.purchaseAmount");
        String titleFirstCourse = jp.getString("courses[0].title");

        System.out.println(numberOfCourses);
        System.out.println(purchaseAmount);
        System.out.println(titleFirstCourse);

        for (int i=0; i < numberOfCourses; i++) {
            System.out.println(jp.getString(String.format("courses[%d].title", i)));
        }
    }
}
