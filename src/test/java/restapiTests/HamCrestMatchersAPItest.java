package restapiTests;

import io.restassured.http.ContentType;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

public class HamCrestMatchersAPItest {

    /*
    Given accept type json
    And path param id is 15
    When user sends a get request to spartans/{id}
    Then status code is 200
    And Content type is Json
    And json data has following
        "id": 15,
        "name": "Meta",
        "gender": "Female",
        "phone": 1938695106

     */
    @Test
    public void getSpartanAndAssertUsingHamcrest() {

        given().accept(ContentType.JSON)
                .and().pathParam("id", 15)
                .when().get("http://ec2-54-174-135-89.compute-1.amazonaws.com:8000/api/spartans/{id}")
                .then().assertThat().statusCode(200)
                .and().assertThat().contentType(ContentType.JSON)
                .and().assertThat().body("id", equalTo(15), "name", equalTo("Meta"),
                "gender", equalTo("Female"), "phone", equalTo(1938695106));

        assertThat(2,is(2)); //hammatcher method

    }
}
