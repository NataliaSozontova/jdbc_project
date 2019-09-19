package restapiTests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.ConfigurationReader;

import java.util.Map;

import static org.junit.Assert.*;
import static io.restassured.RestAssured.*;

public class SpartanTestWithMap {

    @BeforeClass
    public static void setUp() {
        //set base uri that is defined in properties file
        RestAssured.baseURI = ConfigurationReader.getProperty("spartan.baseuri");
    }

    /*
    Send Get request to:
    http://54.164.195.86:8000/api/spartans/33
    Convert Json response to Map
    Print map values
    Assert that:
      "id": 33,
      "name": "Wilek",
      "gender": "Male",
      "phone": 2877865902
     */
    @Test
    public void spartanWithId33Map() {

        Response response = given().accept(ContentType.JSON).and()
                .pathParam("id",33).when()
                .get("spartans/{id}");

        Map<String,Object> mapSpartan = response.body().as(Map.class);

        System.out.println(mapSpartan);

    }
}
