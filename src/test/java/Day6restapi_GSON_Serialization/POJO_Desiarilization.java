package Day6restapi_GSON_Serialization;

import com.google.gson.Gson;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

public class POJO_Desiarilization {

    @Test
    public void spartan_to_pojo_object_desirialization() {
        Response response = given().accept(ContentType.JSON)
                .when().get("http://ec2-54-174-135-89.compute-1.amazonaws.com:8000/api/spartans/15");

        //deserialize json to pojo java object
        //JSON response body -> custom Java class object
        Spartan spartan = response.body().as(Spartan.class);
        System.out.println(spartan.getName());
        System.out.println(spartan.getGender());
        System.out.println(spartan.getId());
        System.out.println(spartan.getPhone());

        assertEquals("Meta", spartan.getName());
        assertEquals("Female", spartan.getGender());
        assertEquals(new Integer(15), spartan.getId());
        assertEquals(new Long(1938695106), spartan.getPhone());

    }

    @Test
    public void GSON_example() {

        Spartan spartan = new Spartan(20, "Vlad", "male", 7033964165L);

        Gson gson = new Gson();
        //serialize spartan object to JSON format using GSON
        String json = gson.toJson(spartan);
        System.out.println("json = " + json);

        //desiarilization - convert json to Java Spartan Object
        String myJson = "{\"id\":25,\"name\":\"Roman\",\"gender\":\"male\",\"phone\":7033960000}";
        Spartan Roman = gson.fromJson(myJson, Spartan.class);
        System.out.println(Roman.toString());

    }

    @Test
    public void list_of_spartans_pojo_deserialization() {
        Response response = given().accept(ContentType.JSON)
                .when().get("http://ec2-54-174-135-89.compute-1.amazonaws.com:8000/api/spartans/");

        //List<Spartan> allSpartans = response.body().as(List.class);
        //or if it takes as LinkedMap
        List<Spartan> allSpartans1 = response.body().as(new TypeRef<List<Spartan>>() {
        });

        System.out.println(allSpartans1.get(0));
        Spartan first = allSpartans1.get(0);
        System.out.println(first);

        for (Spartan sp : allSpartans1) {
            System.out.println(sp.toString());

        }
        //TODO fix deserialization issue
        System.out.println("##########################");
        //USING AllSpartans class for deserialization
        AllSpartans allSpartansObj = response.body().as(AllSpartans.class);

        System.out.println(allSpartansObj.getSpartanList().get(0).toString());
    }



}
