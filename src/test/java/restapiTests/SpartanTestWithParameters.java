package restapiTests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

public class SpartanTestWithParameters {

    @BeforeClass
    public static void setUp() {
        //set based uri so we don't have to type it
        RestAssured.baseURI = "http://ec2-54-174-135-89.compute-1.amazonaws.com:8000/api";
    }

    //    Given no headers provided
//    When Users sends request to /api/hello
//    Then response status code should be 200
//    And Content type header should be "text/plain;charset=UTF-8"
//    And header should contain date
//    And Content-Length should be 17
//    And body should be "Hello from Sparta"
    @Test
    public void helloTest() {
        //request
        Response response = get("/hello");

        //response
        assertEquals(200, response.getStatusCode());
        //verify headers - will receive headers
        //content type
        assertEquals("text/plain;charset=UTF-8", response.contentType());
        //header should contain data
        System.out.println(response.getHeader("Date"));
        assertTrue(response.headers().hasHeaderWithName("Date"));
        System.out.println(response.headers().asList());
        //And Content-Length should be 17
        assertEquals("17", response.getHeader("Content-Length"));
        System.out.println(response.getHeader("Content-Length"));
        assertEquals("Hello from Sparta", response.body().asString());
        System.out.println(response.body().asString());
    }

//    Given accept type is Json
//    And Id parameter value is 5
//    When user sends GET request to /api/spartans/{id}
//    Then response status code should be 200
//    And response content-type: application/json;charset=UTF-8
//    And "Blythe" should be in response payload

    @Test
    public void verifyParamsPositiveTest() {
        Response response = given().accept(ContentType.JSON).
                and().pathParam("id", 5).
                when().get("/spartans/{id}" +
                "");

        assertEquals(200, response.getStatusCode());
        // System.out.println(response.getTime());
        assertEquals("application/json;charset=UTF-8", response.contentType());
        assertTrue(response.getBody().asString().contains("Blythe"));
    }
//    Given accept type is Json
//    And Id parameter value is 500
//    When user sends GET request to /api/spartans/{id}
//    Then response status code should be 404
//    And response content-type: application/json;charset=UTF-8
//    And Spartan Not Found" message should be in response payload

    @Test
    public void verifyParamsNegativeTest() {
        Response response = given().accept(ContentType.JSON).when().
                pathParam("id", 500).and().
                get("/spartans/{id}");

        assertEquals(404, response.getStatusCode());
        assertEquals("application/json;charset=UTF-8", response.contentType());
        assertTrue(response.body().asString().contains("Spartan Not Found"));
    }

    //    Given accept type is Json
//    And query parameter values are :
//    gender|Female
//    nameContains|e
//    When user sends GET request to /api/spartans/search
//    Then response status code should be 200
//    And response content-type: application/json;charset=UTF-8
//    And "Female" should be in response payload
//    And "Janette" should be in response payload
    @Test
    public void verifyQueryParamsPositiveTest() {

        Response response = given().accept(ContentType.JSON).when().
                queryParam("gender", "Female").
                queryParam("nameContains", "e").when()
                .get("/spartans/search");

        //second way
        Response response1 = given().accept(ContentType.JSON).when()
                .queryParams("gender","Female","nameContains","e")
                .when().get("/spartans/search");
        //third way
        Response response2 = given().accept(ContentType.JSON)
                .when().get("/spartans/search?gender=female&nameContains=e");

        assertEquals(200,response.getStatusCode());
        assertEquals("application/json;charset=UTF-8",response.contentType());
        assertTrue(response.getBody().asString().contains("Female"));
        assertTrue(response.getBody().asString().contains("Janette"));

    }

     /*
    Given accept type is Xml
    And query parameter values are :
    gender|Female
    nameContains|e
    When user sends GET request to /api/spartans/search
    Then response status code should be 406
    And "Could not find acceptable representation" should be in response payload
   */
     @Test
     public void invalidHeaderTestWithParams(){
         Response response = given().accept(ContentType.XML).and().
                 queryParams("gender","female","nameContains","e")
                 .when().get("/spartans/search");

         //or other way with map
         Map<String,Object> map = new HashMap<>();
         map.put("gender","female");
         map.put("nameContains","e");

         Response response1 = given().accept(ContentType.XML).and()
                 .queryParams(map).when().get("/spartans/search");

         assertEquals(406,response.getStatusCode());
         assertEquals(406,response1.getStatusCode());

         assertTrue(response.getBody().asString().contains("Could not find acceptable representation"));
         assertTrue(response1.getBody().asString().contains("Could not find acceptable representation"));

     }






}
