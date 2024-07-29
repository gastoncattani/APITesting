package jira;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pojo.jira.Payload;

import java.io.File;

import static io.restassured.RestAssured.given;

public class JiraTests extends BaseJiraTests {

    @BeforeMethod
    public void config() {
        //RestAssured.baseURI = "https://gastoncattani.atlassian.net/";
    }

    @Test(groups = {"smoke", "regression"})
    public void failingTest() {
        String response = given().spec(requestSpecification).log().all().when().log().all().get("/rest/api/3/issue/10005")
                .then().assertThat().statusCode(201).extract().response().asString(); //200 make test to FAIL

        JsonPath js = new JsonPath(response);
        var issueId = js.getString("id");

        System.out.println(issueId);
    }

    @Test(groups = {"smoke", "regression"})
    public void getBug() {
        String response = given().spec(requestSpecification).log().all().when().log().all().get("/rest/api/3/issue/10005")
                .then().assertThat().statusCode(/*200*/201).extract().response().asString(); //200 make test to FAIL

        JsonPath js = new JsonPath(response);
        var issueId = js.getString("id");

        System.out.println(issueId);
    }

    @Test(groups = {"smoke", "regression"})
    public void createBug() {
        String payload = Payload.createIssue("API", "Creating issue", "This is an issue created by code", "Bug");

        String createIssueResponse = given().spec(requestSpecification).log().all()
                .body(payload)
                .when().log().all()
                .post("rest/api/2/issue").then().log().all().assertThat().statusCode(201)
                .extract().response().asString();

        JsonPath js = new JsonPath(createIssueResponse);
        var issueId = js.getString("id");
        System.out.println(issueId);

        // Delete created bug
        given().spec(requestSpecification).log().all()
                .when().log().all()
                .delete("rest/api/3/issue/" + issueId)
                .then().log().all().assertThat().statusCode(204);
    }

    @Test(groups = {"smoke", "regression"})
    public void createBugWithAttachment() {
        // Create bug
        String payload = Payload.createIssue("API", "Creating issue", "This is an issue created by code", "Bug");

        String createIssueResponse = given().spec(requestSpecification).log().all()
                .body(payload)
                .when().log().all()
                .post("rest/api/2/issue").then().log().all().assertThat().statusCode(201)
                .extract().response().asString();

        JsonPath js = new JsonPath(createIssueResponse);
        var issueId = js.getString("id");
        System.out.println(issueId);

        // Add attachment
        String attachmentCreated = given()
                .header("Authorization", "Basic Z2FzdG9uY2F0dGFuaUBnbWFpbC5jb206QVRBVFQzeEZmR0YwbUs1eDlMTHJXWE9RaUFoV1V0NjktRE40ZXBPVWFZTnF6bm9qZGZycFdEYjJwdm15MW53ZmdoVlFWbHIwLXZYN0stMmdqa21ac0xtZUU4dUZxM2czYm9MSDhNX25HSW5UYmI3V19CX2s3azJuaEdmY2RXaFRSUnVaOVhra0Nza3cwS25IVGJaY1hCbmhqZ2dzT29aN19Ha2ZsN1VjUWFQTTdLb0hkVmYzeTVNPUU1NjM0NzBB")
                .header("X-Atlassian-Token", "no-check")
                .pathParams("key", issueId).log().all()
                .multiPart("file", new File("src/test/java/resources/Lenovo_ThinkPad_X1_Ultrabook.jpg"))
                .post("/rest/api/3/issue/{key}/attachments")
                .then().log().all().assertThat().statusCode(200).extract().asString();

        js = new JsonPath(attachmentCreated);
        var attachmentId = js.getString("id");
        attachmentId = attachmentId.substring(1, attachmentId.length() - 1); // Remove [] that are returned

        // Verify attachment was added
        String attachmentData = given().spec(requestSpecification).log().all()
                .pathParams("id", attachmentId)
                .when().log().all()
                .get("/rest/api/3/attachment/content/{id}")
                .then().log().all().assertThat().statusCode(200).extract().asString();

        Assert.assertFalse(attachmentData.isEmpty(), "No attachment found");

        // Delete attachment
        given().spec(requestSpecification)
                .pathParams("id", attachmentId)
                .when().log().all()
                .delete("/rest/api/3/attachment/{id}")
                .then().log().all().assertThat().statusCode(204);

        // Delete created bug
        given().spec(requestSpecification).log().all()
                .pathParams("id", issueId)
                .when().log().all()
                .delete("rest/api/3/issue/{id}")
                .then().log().all().assertThat().statusCode(204);
    }

    @Test(dataProvider = "testdata", groups = {"smoke", "regression"})
    public void getEditIssueMetadata(String issueId) {
        System.out.println(issueId);
        var response = given()
                .spec(requestSpecification).log().all()
                .when().log().all()
                .get("rest/api/3/issue/" + issueId + "/editmeta")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();
        System.out.println(response);
    }

    @DataProvider(name = "testdata")
    public Object[] issueIdDataProvider(){
        Object[] issueIdData = new Object[2];
        issueIdData[0] = "10005";
        issueIdData[1] = "10011";
        return issueIdData;
    }
}
