package restapiTests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static  org.junit.Assert.*;

public class SpartanGetTests {

    String spartanAllURL = "http://ec2-54-174-135-89.compute-1.amazonaws.com:8000/api/spartans/";

    @Test
    public void viewAllSpartansTest() {
        Response response = RestAssured.get(spartanAllURL);
        System.out.println(response.statusCode());
        // System.out.println(response.body().asString());
        response.body().prettyPrint();
    }

    /**
     * given accept type is Json
     * when user sends a get request to spartan url
     * then response status code is 200
     * and response body should be json
     * and response should contain "name": "Bunnie"
     */

    @Test
    public void viewAllSpartansTest2() {
        // Response response = RestAssured.get(spartanAllURL);
        Response response = RestAssured.given().accept(ContentType.JSON).
                when().get(spartanAllURL);
        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals("application/json;charset=UTF-8", response.contentType());
        Assert.assertTrue(response.body().asString().contains("Bunnie"));

    }

    @Test
    public void viewAllSpartansTest3() {
        //if you import static package from restAssured you can start from given()

        //part of request
        given().accept(ContentType.JSON) //we want json back from api
                .when().get(spartanAllURL) //we send get request to url
                //part of response
                .then().statusCode(200) //we check status code
                .and().contentType("application/json;charset=UTF-8");//we check content response type
    }

    /**
     * When users sends a get requests to /spartans/3
     * Then status code should be 200
     * And Content type should be application/json
     * And json body should contain "Fidole"
     */

    @Test
    public void viewASpartanTest() {
        Response response = when().get(spartanAllURL+3);

        //import static assert package and you don't need to use Assert
       assertEquals(200, response.getStatusCode());
       assertEquals("application/json;charset=UTF-8", response.contentType());
       assertTrue(response.body().asString().contains("Fidole"));
    }


}
