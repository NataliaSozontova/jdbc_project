package day7_put_delete_authorization_schemaValidation;

import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.ConfigurationReader;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;

public class SpartanDeletePut {

    @BeforeClass
    public static void setUp(){
        baseURI = ConfigurationReader.getProperty("spartan.baseuri");
    }

    @Test
    public void updateExistingSpartan_PUT_request_test(){

        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("name","Marufjon");
        requestMap.put("gender","Male");
        requestMap.put("phone",2023615171L);

        given().contentType(ContentType.JSON)
                .pathParam("id",18)
                .and().body(requestMap).when()
                .put("spartans/{id}")
                .then().assertThat().statusCode(204);
    }

    @Test
    public void deleteExistingSpartan_DELETE_request_Test(){
        Random random = new Random();
        int idToDelete =random.nextInt(126)+4;
        System.out.println("idToDelete = " + idToDelete);
        given().pathParam("id",idToDelete)
                .when().delete("spartans/{id}")
                .then().assertThat().statusCode(204);

        //another way to do it
        expect().that().statusCode(204)
                .given().pathParam("id",idToDelete)
                .when().delete("spartans/{id}");

    }
}
