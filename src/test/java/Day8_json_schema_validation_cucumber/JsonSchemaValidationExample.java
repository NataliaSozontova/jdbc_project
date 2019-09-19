package Day8_json_schema_validation_cucumber;

import io.restassured.http.ContentType;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class JsonSchemaValidationExample {

    @Test

    //to do finish code - git practice until tomorrow, conflict merge

    public void jsonSchemaValidationOfSingleSpartanAPI(){
        given().accept(ContentType.JSON)
                .pathParam("id",97)
                .when().get("http://ec2-54-174-135-89.compute-1.amazonaws.com:8000/api/spartans/{id}")
                .then().assertThat().body(matchesJsonSchemaInClasspath("SingleSpartanJsonSchema.json"));

        given().accept(ContentType.JSON)
                .pathParam("id",97)
                .when().get("http://ec2-54-174-135-89.compute-1.amazonaws.com:8000/api/spartans/{id}")
                .then().assertThat().body(matchesJsonSchema(new File("/Users/nataliasozontova/Desktop/SingleSpartanJsonSchema.json")));


    }
}
