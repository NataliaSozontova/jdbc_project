package restapiTests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.ConfigurationReader;

import static org.junit.Assert.*;
import static io.restassured.RestAssured.*;

public class SpartanTestsWithJsonPath {
    @BeforeClass
    public static void setUp() {
        //set base uri that is defined in properties file
        RestAssured.baseURI = ConfigurationReader.getProperty("spartan.baseuri");
    }

    /* Given accept type is json
       And path param spartan id is 11
       When user sends a get request to /spartans/{id}
       Then status code is 200
       And content type is json
       And  "id": 11,
         "name": "Nona",
         "gender": "Female",
         "phone": 7959094216
     */
    @Test
    public void verifySpartanUsingJsonPath(){
        Response response = given().accept(ContentType.JSON)
                .pathParam("id",11)
                .when().get("/spartans/{id}");

        assertEquals(200, response.statusCode());
        assertEquals("application/json;charset=UTF-8",response.contentType());

        //store response json body/payload into JSONPATH object

        JsonPath json = response.jsonPath();
        //or
        JsonPath json2 = new JsonPath(response.getBody().asString());

        int id = json.getInt("id");
        String name = json.getString("name");
        String gender = json.getString("gender");
        long phone = json.getLong("phone");

        System.out.println("id = " + id);
        System.out.println("name = " + name);
        System.out.println("gender = " + gender);
        System.out.println("phone = " + phone);

        assertEquals(11,id);
        assertEquals("Nona",name);
        assertEquals("Female",gender);
        assertEquals(7959094216L,phone);
    }


}
