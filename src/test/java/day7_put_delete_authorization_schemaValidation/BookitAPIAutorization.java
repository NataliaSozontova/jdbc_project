package day7_put_delete_authorization_schemaValidation;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;


public class BookitAPIAutorization {
    String accessToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4NiIsImF1ZCI6InN0dWRlbnQtdGVhbS1sZWFkZXIifQ.lEfjcu6RpBfcZ4qWthzZU8uH8fX4FCJFfxBnPNgh4Mo";
    @BeforeClass
    public static void setUP(){
        baseURI = "https://cybertek-reservation-api-qa3.herokuapp.com/api";
    }
    @Test
    public void getAllcampuses_using_access_token(){
        Response response = given().header("Authorization", accessToken).
                accept(ContentType.JSON).
                when().get("/campuses");

        JsonPath jsonPath = response.jsonPath();

        //print name of room with id 111 in light side
        String roomName = jsonPath.getString("clusters[0].rooms[0].name[0]");
        System.out.println("roomName = " + roomName);
    }

}
