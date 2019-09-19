package restapiTests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.ConfigurationReader;
import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;
public class HRapiTestWithParams {

    @BeforeClass
    public static void setUp(){
        //set base uri that is defined in properties file
        RestAssured.baseURI= ConfigurationReader.getProperty("hrapi.baseuri");
    }
    /*
    Given accept type is Json
    And parameters: q=region_id =2 - {"region_id":2}
    When users sends a GET request to "/countries"
    Then status code is 200
    And Content type is application/json
    And payload should contain "United States of America"
     */
    @Test
    public void getCountriesUsingQueryParams(){

        Response response = given().accept(ContentType.JSON).and()
                .queryParam("q","{\"region_id\":2}").   ///
                when().get("/countries");

        assertEquals(200, response.getStatusCode());
        assertEquals("application/json", response.contentType());
        assertTrue(response.getBody().asString().contains("United States of America"));
    }

    @Test
    public void getEmployeesUsingQueryParams() {

        Response response = given().accept(ContentType.JSON).and()
                .queryParam("q", "{\"job_id\":\"IT_PROG\"}").
                        when().get("/employees");

        response.prettyPrint();

        assertEquals(200, response.getStatusCode());
        assertEquals("application/json", response.contentType());
        assertTrue(response.getBody().asString().contains("IT_PROG"));
     }
    }
