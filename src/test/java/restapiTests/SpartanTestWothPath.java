package restapiTests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.ConfigurationReader;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

public class SpartanTestWothPath {


    @BeforeClass
    public static void setUp() {
        //set base uri that is defined in properties file
        RestAssured.baseURI = ConfigurationReader.getProperty("spartan.baseuri");
    }

    @Test
    public void PathTest() {

        Response response = given().accept(ContentType.JSON).and().pathParam("id", 10)
                .when().get("/spartans/{id}");

        System.out.println(response.body().path("id").toString());
        System.out.println(response.body().path("name").toString());
        System.out.println(response.path("gender").toString());
        System.out.println(response.path("phone").toString());

        int id = response.body().path("id");
        String name = response.body().path("name");
        String spartanGender = response.path("gender");
        long phoneNum = response.path("phone");

        System.out.println("id = " + id);
        System.out.println("name = " + name);
        System.out.println("phoneNum = " + phoneNum);
        System.out.println("spartanGender = " + spartanGender);

        assertEquals(10, id);
        assertEquals("Female", spartanGender);
        assertEquals(3312820936L, phoneNum);
        assertEquals("Lorenza", name);
    }

    @Test
    public void getAllSpartansUsingPathList() {
        Response response = get("/spartans/");

        //extract first id
        int firstID = response.path("id[0]");
        System.out.println("firstID = " + firstID);

        //extract first name
        String firstName = response.path("name[0]");
        System.out.println("firstName = " + firstName);

        //get last firstname

        String lastFirstName = response.path("name[-1]");
        System.out.println("lastFirstName = " + lastFirstName);

        //extract all firstNames and print out
        List<String> names = response.path("name");
        System.out.println("number of names: " + names.size());
        System.out.println("names = " + names);

        //extract all phone numbers
        List<Object> numbers = response.path("phone");

        //  System.out.println("numbers = " + numbers);
        for (Object n : numbers) {
            System.out.println("n = " + n);
        }

    }

    @Test
    public void getCountiesExtractKeyValuesByPath() {
        RestAssured.baseURI = ConfigurationReader.getProperty("hrapi.baseuri");
        //list with all countries names
        Response response = given().queryParam("q","{\"region_id\":2}")
                .when().get("/countries");

        String firstCountryID = response.path("items.country_id[0]");
        String firstCountryName = response.path("items.country_name[0]");

        System.out.println("firstCountryName = " + firstCountryName);
        System.out.println("firstCountryID = " + firstCountryID);

        List<String> countries = response.path("items.country_name");
        System.out.println("countries = " + countries);

        //assert that all region ids equal to 2
        List<Object> ids = response.path("items.region_id");

        for(Object id: ids){
            assertEquals(2,id);
        }


    }

}
