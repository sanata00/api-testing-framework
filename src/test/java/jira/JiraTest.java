package jira;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.TestDataUtil;

import java.io.File;
import java.util.List;

import static io.restassured.RestAssured.given;

public class JiraTest {

    @Test
    public void testAttachment() {
        RestAssured.baseURI = "http://localhost:2323";

        SessionFilter session = new SessionFilter();
        given().log().all().header("Content-Type", "application/json")
                .body(TestDataUtil.getDataFromFile("JiraLogin.json")).filter(session)
                .when().post("rest/auth/1/session")
                .then().log().all().assertThat().statusCode(200);

        String response = given().log().all().header("Content-Type", "application/json")
                .body(TestDataUtil.getDataFromFile("JiraCreateIssue.json")).filter(session)
                .when().post("rest/api/2/issue")
                .then().log().all().assertThat().statusCode(201)
                .extract().asString();

        JsonPath jp = new JsonPath(response);
        String issueId = jp.getString("id");
        String issueKey = jp.getString("key");

        response = given().log().all().pathParam("issueId", issueId).header("Content-Type", "application/json")
                .body(TestDataUtil.getDataFromFile("JiraAddComment.json")).filter(session)
                .when().post("rest/api/2/issue/{issueId}/comment")
                .then().log().all().assertThat().statusCode(201)
                .extract().response().asString();

        jp = new JsonPath(response);
        String commentId = jp.getString("id");

        File attachment = new File("src/test/resources/testdata/attachment.txt");
        given().log().all().header("X-Atlassian-Token", "no-check").header("Content-Type", "multipart/form-data")
                .multiPart("file", attachment).pathParam("issueKey", issueKey).filter(session)
                .when().post("rest/api/2/issue/{issueKey}/attachments")
                .then().log().all().assertThat().statusCode(200);

        response = given().log().all().pathParam("issueKey", issueKey).queryParam("fields", List.of("comment", "creator")).filter(session)
                .when().get("rest/api/2/issue/{issueKey}")
                .then().log().all().extract().response().asString();

        jp = new JsonPath(response);
        String commentBody = "";
        int numberOfComments = jp.getInt("fields.comment.comments.size()");
        for (int i = 0; i < numberOfComments; i++) {
            if (jp.getString(String.format("fields.comment.comments[%d].id", i)).equals(commentId)) {
                commentBody = jp.getString(String.format("fields.comment.comments[%d].body", i));
                break;
            }
        }
        Assert.assertEquals(commentBody, "test comment");
    }
}