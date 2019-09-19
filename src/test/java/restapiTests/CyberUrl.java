package restapiTests;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import utils.ConfigurationReader;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CyberUrl {

    @Before
    public void setUp() {
        baseURI = ConfigurationReader.getProperty("cyberapi.baseuri");
    }

    /*
    {
"students": [
    {
        "studentId": 58201,
        "firstName": "Conor",
        "lastName": "McGregor",
        "batch": 11,
        "joinDate": "09/08/2019",
        "birthDate": "09/09/1990",
        "password": "string",
        "subject": "problem solving",
        "gender": "Male",
        "admissionNo": "5000",
        "major": "uneployed",
        "section": "string",
        "contact": {
            "contactId": 58181,
            "phone": "123456",
            "emailAddress": "string@gmail.com",
            "premanentAddress": "main address"
        },
        "company": {
            "companyId": 58201,
            "companyName": "Google",
            "title": "COO",
            "startDate": "09/08/2019",
            "address": {
                "addressId": 58181,
                "street": "7th ave",
                "city": "NYC",
                "state": "NY",
                "zipCode": 10001
            }
        }
    }
]
}
     */
    @Test
    public void getAStudent() {
        JsonPath jsonData = get("/student/58201").jsonPath();

        String firstName = jsonData.getString("students.firstName");
        String lastName = jsonData.getString("students.lastName");
        String phone = jsonData.getString("students.contact.phone");
        String email = jsonData.getString("students.contact.emailAddress");
        String companyName = jsonData.getString("students.company.companyName");
        String companyCity = jsonData.getString("students.company.address.city");

        System.out.println("firstName = " + firstName);
        System.out.println("lastName = " + lastName);
        System.out.println("phone = " + phone);
        System.out.println("email = " + email);
        System.out.println("companyName = " + companyName);
        System.out.println("companyCity = " + companyCity);
    }

    /*
    {
  "teachers": [
    {
      "teacherId": 2381,
      "firstName": "Esen",
      "lastName": "Niiazov",
      "emailAddress": "eniiazov@gmail.com"
    }
  ]
}
     */
    @Test
    public void verifyTeacherInfo() {

        given().accept(ContentType.JSON).and().when()
                .get("/teacher/name/Esen").
                then().contentType(ContentType.JSON).
                and().statusCode(200)
                .assertThat().body("teachers.teacherId", contains(2381),
                "teachers.firstName", contains("Esen"),
                "teachers.lastName", contains("Niiazov"),
                "teachers.emailAddress", contains("eniiazov@gmail.com"))
        .log().all();

    }
    @Test
    public void verifyComputerDepartment(){
        given().accept(ContentType.JSON).and()
                .pathParam("name","Computer")
                .and().get("/teacher/department/{name}")
                .then().statusCode(200)
                .and().contentType(ContentType.JSON)
                .assertThat().body("teachers.firstName",contains("Esen","Albina"));
    }
}
