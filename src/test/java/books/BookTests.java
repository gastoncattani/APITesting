package books;

import io.restassured.RestAssured;
import org.testng.annotations.Test;
import pojo.books.Book;

import static io.restassured.RestAssured.given;

public class BookTests {

    @Test
    public void GetBooksByAuthor() {
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        var response = given().log().all().queryParams("AuthorName", "John foe")
                .when().get("/Library/GetBook.php")
                .then().extract().response().as(Book[].class);
        String a = "";
    }
}
