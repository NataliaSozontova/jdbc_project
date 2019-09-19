package Day6restapi_GSON_Serialization;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

public class POSTActions {

    /*      Given accept type and Content type is JSON
            And request json body is:
         {
             "gender":"Male",
            "name":"Maximus",
             "phone":8877445596
        }
           When user sends POST request to '/spartans/'
           Then status code should be 201
           And content type should be "applocation/json"
           And json payload should contain "success": "A Spartan is Born!" message
           And same data what is posted
            */
    @Test
    public void postNewSpartan() {

        Response response = given().accept(ContentType.JSON).
                and().contentType(ContentType.JSON)
                .and().body("{\"gender\":\"Male\",\n" +
                        "            \"name\":\"POST Test\",\n" +
                        "             \"phone\":8877445596}").
                        when().post("http://ec2-54-174-135-89.compute-1.amazonaws.com:8000/api/spartans/");

        //response validation
        assertEquals(201, response.statusCode());
        assertEquals("application/json", response.getContentType());
        //extract message using path method
        String message1 = response.path("success");
        //extract message using jsonpath
        JsonPath jsonPath = response.jsonPath();
        String message2 = jsonPath.getString("success");

        System.out.println("message1 = " + message1);
        System.out.println("message2 = " + message2);

        assertEquals("A Spartan is Born!", message1);
        assertEquals("A Spartan is Born!", message2);

        //assert name, gender, phone
        assertEquals("Male", jsonPath.getString("data.gender"));
        assertEquals("POST Test", jsonPath.getString("data.name"));
        assertEquals(8877445596L, jsonPath.getLong("data.phone"));

        String name = jsonPath.getString("data.name");
        String gender = jsonPath.get("data.gender");
        Long phone = jsonPath.get("data.phone");

        System.out.println("name = " + name);
        System.out.println("gender = " + gender);
        System.out.println("phone = " + phone);


    }

    @Test
    public void postNewSpartanWithMap() {

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("gender", "Female");
        requestMap.put("name", "Olga");
        requestMap.put("phone", 5713455667L);

        Response response = given().accept(ContentType.JSON).
                and().contentType(ContentType.JSON)
                .and().body(requestMap)
                .when().post("http://ec2-54-174-135-89.compute-1.amazonaws.com:8000/api/spartans/");

        //response validation
        assertEquals(201, response.statusCode());
        assertEquals("application/json", response.getContentType());
        //extract message using path method
        String message1 = response.path("success");
        //extract message using jsonpath
        JsonPath jsonPath = response.jsonPath();
        String message2 = jsonPath.getString("success");

        System.out.println("message1 = " + message1);
        System.out.println("message2 = " + message2);

        assertEquals("A Spartan is Born!", message1);
        assertEquals("A Spartan is Born!", message2);

        //assert name, gender, phone
        assertEquals("Female", jsonPath.getString("data.gender"));
        assertEquals("Olga", jsonPath.getString("data.name"));
        assertEquals(5713455667L, jsonPath.getLong("data.phone"));

        //verify that is was created by GET request
        int spartanID = jsonPath.getInt("data.id");
        System.out.println("spartanID = " + spartanID);
        System.out.println("sending get request with spartan id " + spartanID);

        get("http://ec2-54-174-135-89.compute-1.amazonaws.com:8000/api/spartans/" + spartanID).body().prettyPrint();

    }

    @Test
    public void postNewSpartanWithPOJOObject() {

        //create a spartan object to be used as body request
        Spartan spartan = new Spartan();
        spartan.setGender("Male");
        spartan.setName("POSTSpartan");
        spartan.setPhone(5712243546l);

        Response response = given().accept(ContentType.JSON).
                and().contentType(ContentType.JSON)
                .and().body(spartan)
                .when().post("http://ec2-54-174-135-89.compute-1.amazonaws.com:8000/api/spartans/");

        //response validation
        assertEquals(201, response.statusCode());
        assertEquals("application/json", response.getContentType());
        //extract message using path method
        String message1 = response.path("success");
        //extract message using jsonpath
        JsonPath jsonPath = response.jsonPath();
        String message2 = jsonPath.getString("success");

        System.out.println("message1 = " + message1);
        System.out.println("message2 = " + message2);

        assertEquals("A Spartan is Born!", message1);
        assertEquals("A Spartan is Born!", message2);

        //assert name, gender, phone
        assertEquals("Male", jsonPath.getString("data.gender"));
        assertEquals("POSTSpartan", jsonPath.getString("data.name"));
        assertEquals(5712243546l, jsonPath.getLong("data.phone"));

        //verify that is was created by GET request
        int spartanID = jsonPath.getInt("data.id");
        System.out.println("spartanID = " + spartanID);
        System.out.println("sending get request with spartan id " + spartanID);

        get("http://ec2-54-174-135-89.compute-1.amazonaws.com:8000/api/spartans/" + spartanID).body().prettyPrint();

    }

    public static String initCap(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    @Test
    public void postNewSpartanWithPOJOObjectFromUINamesAPI() {

        for (int i = 0; i < 5; i++) {
            //send get request to http://uinames.com/api/ and extract name and gender
            //{"name":"Marijo","surname":"Balašević","gender":"male","region":"Bosnia and Herzegovina"}
            JsonPath uinamesJson = get("http://uinames.com/api/").body().jsonPath();
            Spartan spartan = new Spartan();
            spartan.setGender(initCap(uinamesJson.getString("gender")));
            spartan.setName(uinamesJson.getString("name") + " " + uinamesJson.getString("surname"));
            spartan.setPhone(5712243546l);

            Response response = given().accept(ContentType.JSON).
                    and().contentType(ContentType.JSON)
                    .and().body(spartan)
                    .when().post("http://ec2-54-174-135-89.compute-1.amazonaws.com:8000/api/spartans/");

            //response validation
            assertEquals(201, response.statusCode());
            assertEquals("application/json", response.getContentType());
            //extract message using path method
            String message1 = response.path("success");
            //extract message using jsonpath
            JsonPath jsonPath = response.jsonPath();
            String message2 = jsonPath.getString("success");

            System.out.println("message1 = " + message1);
            System.out.println("message2 = " + message2);

            assertEquals("A Spartan is Born!", message1);
            assertEquals("A Spartan is Born!", message2);


//        //assert name, gender, phone
//        assertEquals("Male", jsonPath.getString("data.gender"));
//        assertEquals("POSTSpartan", jsonPath.getString("data.name"));
//        assertEquals(5712243546l, jsonPath.getLong("data.phone"));

            //verify that is was created by GET request
            int spartanID = jsonPath.getInt("data.id");
            System.out.println("spartanID = " + spartanID);
            System.out.println("sending get request with spartan id " + spartanID);

            get("http://ec2-54-174-135-89.compute-1.amazonaws.com:8000/api/spartans/" + spartanID).body().prettyPrint();
        }
    }
}





