package Day6restapi_GSON_Serialization;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static io.restassured.RestAssured.*;

public class JsonToCollection {

    @Test
    public void hrApiEmployee_jsondata_to_java_object() {
        Response response = given().accept(ContentType.JSON)
                .pathParam("employee_id", 105).when()
                .get("http://ec2-54-174-135-89.compute-1.amazonaws.com:1000/ords/hr/employees/{employee_id}");

        response.prettyPrint();

        Map<String, ?> employeeMap = response.body().as(Map.class);
        System.out.println(employeeMap.toString());
        String firstName = employeeMap.get("first_name").toString();
        System.out.println("firstName = " + firstName);
        double salary = (Double) employeeMap.get("salary");
        System.out.println("salary = " + salary);

        assertEquals("David", firstName);
        assertEquals(4800.0,salary,1);
        //delta says if difference 1 means it's not mismatch accept it. delta amount - if there is difference
        //by delta amount or less, values will be considered equal

    }
    @Test
    public void convertAllSpartansToListOfMaps() {

        Response response = given().accept(ContentType.JSON).when()
                .get("http://ec2-54-174-135-89.compute-1.amazonaws.com:8000/api/spartans/");


        List<Map<String, Object>> list = response.body().as(List.class);

        //print all data of first spartan
        System.out.println(list.get(0).toString());
        //or store it into the map
        Map<String,Object> map = list.get(0);
        System.out.println(map);

        int counter=1;
        for(Map<String, ?> spartan : list) {
            System.out.println(counter + " spartan = " + spartan);
            counter++;
        }
    }

    @Test
    public void regions_data_to_collections(){
        Response response = given().accept(ContentType.JSON)
                .when().get("http://ec2-54-174-135-89.compute-1.amazonaws.com:1000/ords/hr/regions");

        Map<String,?> dataMap = response.as(Map.class);//you can skip body() method
       // System.out.println("dataMap = " + dataMap);

        //extract Europe, Americas, Asia from the above map (it's map of maps)
        System.out.println(dataMap.get("items"));
        List<Map<String,?>> regionsList =  (List<Map<String,?>>) dataMap.get("items");

        System.out.println(regionsList.get(0).get("region_name"));
        System.out.println(regionsList.get(1).get("region_name"));
        System.out.println(regionsList.get(2).get("region_name"));


    }
}
