package restapiTests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.ConfigurationReader;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

import java.util.List;


public class WarmUpORDS {

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = ConfigurationReader.getProperty("hrapi.baseuri");
    }

    /*
            - Given accept type is Json
            - Path param value- US
            - When users sends request to /countries
            - Then status code is 200
            - And country_id is US
            - And Country_name is United States of America
            - And Region_id is 2
     */
    @Test
    public void Test1() {
        Response response = given().accept(ContentType.JSON).and()
                .pathParam("country_id", "US").when().get("/countries/{country_id}");

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        String country_id = response.path("country_id");
        assertEquals("US", country_id);

        String country_name = response.path("country_name");
        assertEquals("United States of America", country_name);

        int region_id = response.path("region_id");
        assertEquals(2, region_id);
    }

    /*
    - Given accept type is Json
    - Query param value - q={"department_id":80}
    - When users sends request to /employees
    - Then status code is 200
    - And all job_ids start with 'SA'
    - And all department_ids are 80
    - Count is 25
 */
    @Test
    public void Test2() {
        Response response = given().accept(ContentType.JSON).and()
                .queryParam("q", "{\"department_id\":80}").when()
                .get("/employees");

        //validations
        assertEquals(200, response.getStatusCode());

        //extract values from json into list, variables
        List<String> jobIDs = response.path("items.job_id");
        List<Integer> departmentIDs = response.path("items.department_id");
        int count = response.path("count");

        //do assertions
        for (String jobID : jobIDs) {
            assertTrue(jobID.startsWith("SA"));
            assertEquals("SA", jobID.substring(0, 2));
        }

        departmentIDs.forEach(id -> assertEquals(new Integer(80), id));

        assertEquals(25, count);
    }
    /*
     "items": [
        {
            "country_name": "Argentina",
            "region_id": 2,
            "links": [
                {
                    "rel": "self",
                    "href": "http://ec2-54-174-135-89.compute-1.amazonaws.com:1000/ords/hr/countries/AR"
                }
            ],
            "country_id": "AR"
        },
     */
    @Test
    public void hr_api_countries_jsonpath_list(){
        JsonPath json = get("/countries").jsonPath();

        //json.prettyPrint();

        List<String> countryName = json.getList("items.country_name");
        System.out.println("countryName = " + countryName);

        List<String> countryID = json.getList("items.country_id");
        System.out.println("countryID = " + countryID);

        //get countries names of all countries in region id 2
        List<String> countriesInRegion2 = json.getList("items.findAll{it.region_id==2}.country_name");
        System.out.println("countriesInRegion2 = " + countriesInRegion2);

        //get countries ids of all countries in region id 2
        List<String> countriesIDinRegion2 = json.getList("items.findAll{it.region_id==2}.country_id");
        System.out.println("countriesIDinRegion2 = " + countriesIDinRegion2);
    }
}
