package restapiTests;

import io.restassured.response.Response;
import org.junit.Test;
import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

public class HRApiGetTests {

    String hrURL = "http://ec2-54-174-135-89.compute-1.amazonaws.com:1000/ords/hr/regions";

    /**
     * send a request to all regions
     * print status code
     * print content type
     * pretty print response json
     * verify status code 200
     * verify content/type "application/json
     * verify that json response body contains "Americas"
     * verify that json response body contains "Europe"
     */
    @Test
    public void getAllRegionsTest(){

        Response response = get(hrURL);
        System.out.println(response.getStatusCode());
        System.out.println(response.contentType());
        response.prettyPrint();

        assertEquals(2, response.getTime());
        assertEquals("application/json",response.contentType());
        assertTrue(response.body().asString().contains("Americas"));
        assertTrue(response.body().asString().contains("Europe"));



    }
}
