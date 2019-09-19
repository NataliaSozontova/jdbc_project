package step_definitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.BookitApiUtil;
import utils.ConfigurationReader;

import static io.restassured.RestAssured.*;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BookApiStepDefs {

    String oauthToken;
    Response response;

    @Given("Authorization token is provided")
    public void authorization_token_is_provided() {
        oauthToken = BookitApiUtil.generateToken();
        if(oauthToken==null || oauthToken.isEmpty()){
            fail("oathToken was not generated successfully");
        }
    }

    @When("user sends post request to \\/api\\/teams\\/team with following data")
    public void user_sends_post_request_to_api_teams_team_with_following_data(Map<String, String> data) {

        System.out.println("data = " + data);
        response =given().accept(ContentType.JSON)
                .header("Authorization",oauthToken)
                .and().queryParams(data)
                .when().post(ConfigurationReader.getProperty("bookit.baseuri")+"/api/teams/team");

        //set response object into BookItApiUtil class in case we need it in different class
        BookitApiUtil.setResponse(response);
    }

    @Then("status code should be {int}")
    public void status_code_should_be(int statusCode) {
        System.out.println("status code should be"+ statusCode);
        assertEquals(statusCode,response.statusCode());
    }

}
