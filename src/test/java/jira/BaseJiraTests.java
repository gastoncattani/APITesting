package jira;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.*;

import static io.restassured.RestAssured.*;

public class BaseJiraTests{

    public RequestSpecification requestSpecification;

    @BeforeClass
    public void generalConfiguration(){
        RestAssured.baseURI = "https://gastoncattani.atlassian.net/";
        requestSpecification = new RequestSpecBuilder()/*.setBaseUri("https://gastoncattani.atlassian.net/")*/
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Basic Z2FzdG9uY2F0dGFuaUBnbWFpbC5jb206QVRBVFQzeEZmR0YwbUs1eDlMTHJXWE9RaUFoV1V0NjktRE40ZXBPVWFZTnF6bm9qZGZycFdEYjJwdm15MW53ZmdoVlFWbHIwLXZYN0stMmdqa21ac0xtZUU4dUZxM2czYm9MSDhNX25HSW5UYmI3V19CX2s3azJuaEdmY2RXaFRSUnVaOVhra0Nza3cwS25IVGJaY1hCbmhqZ2dzT29aN19Ha2ZsN1VjUWFQTTdLb0hkVmYzeTVNPUU1NjM0NzBB")
                        .build();
    }
}
