package utils;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
public class BookitApiUtil {

    private static Response response;

    public static void setResponse(Response res){
       response=res;
    }

    public static Response getResponse(){
        return response;
    }

    public static String generateToken(){
    Response response = given().queryParams("email", ConfigurationReader.getProperty("bookit.email"),
            "password", ConfigurationReader.getProperty("bookit.password"))
            .when().get(ConfigurationReader.getProperty("bookit.baseuri")+ "/sign");
    JsonPath jsonPath = response.body().jsonPath();

    String token = jsonPath.getString("accessToken");


        return token;
}
    @Test
    public void testUtil(){
        String bookItToken =BookitApiUtil.generateToken();
        System.out.println("bookItToken = " + bookItToken);
    }
}
