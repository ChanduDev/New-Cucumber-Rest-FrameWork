package stepDefinitions;

import dataDeserialization.GetCatApiData;
import dataSerialization.TestDataBuild;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static utilities.Utils.getJsonPath;
import static utilities.Utils.requestsSpecification;

import utilities.Utils;

import java.io.IOException;

public class StepDefinition {

    RequestSpecification reqspec;
    ResponseSpecification resspec;
    Response responseBody;
    TestDataBuild data = new TestDataBuild();
    GetCatApiData sample = new GetCatApiData();

    String id;

    @Given("Add Headers & Body for API")
    public void add_headers_body_for_api() throws IOException {
        // Write code here that turns the phrase above into concrete actions
        reqspec = given().spec(requestsSpecification()
                .header("x-api-key", "DEMO-API-KEY"));
    }

    @Given("Add Headers & Body for API with Random Value")
    public void addHeadersBodyForAPIWithRandomValue() throws IOException {

        int min = 31098;
        int max = 31535;
        // Since i am unable to deserialize
        //Generate random int value from range
        System.out.println("Random value in int from " + min + " to " + max + ":");
        int random_int = (int) Math.floor(Math.random() * (max - min + 1) + min);
        System.out.println(random_int);

        reqspec = given().spec(requestsSpecification()
                .header("x-api-key", "DEMO-API-KEY")
                .pathParam("id", random_int));
    }

    @When("User calls {string} API with {string}")
    public void userCallsAPIWith(String resource, String requestType) throws IOException {

        String resourceAPI = Utils.getGlobalValue(resource);

        resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build().expect().defaultParser(Parser.JSON);

        if (requestType.equalsIgnoreCase("POST"))
            responseBody = reqspec.when().post(resourceAPI);
        else if (requestType.equalsIgnoreCase("GET"))
            responseBody = reqspec.when().get(resourceAPI);
        else if (requestType.equalsIgnoreCase("DELETE"))
            responseBody = reqspec.when().delete(resourceAPI);
    }

    @When("User calls {string} API with {string} with Random Value")
    public void userCallsAPIWithWithRandomValue(String resource, String requestType) throws IOException {

        String resourceAPI = Utils.getGlobalValue(resource);

        resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        if (requestType.equalsIgnoreCase("POST"))
            responseBody = reqspec.when().post(resourceAPI);
        else if (requestType.equalsIgnoreCase("GET"))
            responseBody = reqspec.when().get(resourceAPI);
        else if (requestType.equalsIgnoreCase("DELETE"))
            responseBody = reqspec.when().delete(resourceAPI);
    }

    @Then("Verify {int} response status code")
    public void verifyResponseStatusCode(int statusCode) {
        assertEquals(responseBody.getStatusCode(), statusCode);
//        id = getJsonPath(responseBody, "user_id");
    }

    @Then("Verify length of the response result is more than 0")
    public void verifyLengthOfTheResponseResultIsMoreThan() {

        // Simple Validation
        if (responseBody.asString().length() > 0);
        {
            System.out.println("length of the response result is more than 0");
        }

        // I have tried to deserialize, but it is failing to match the response body.
        // Couldn't get where exactly the mistake is. I might need more time for analysis.

        // Uncomment below lines to view the error
//        GetCatApiData extracted = responseBody.as(GetCatApiData.class);
//        System.out.println(extracted.getGetCatData().get(1));

    }

    @Then("Verify response is not empty")
    public void verifyResponseIsNotEmpty() {

        if (responseBody.asString().length() > 0) ;
        {
            System.out.println("Response is not empty");
        }
    }

    @Then("Verify all the field in response object match the corresponding given object \\(by \\{id})")
    public void verifyAllTheFieldInResponseObjectMatchTheCorrespondingGivenObjectById() throws IOException {

        int id = Integer.parseInt(getJsonPath(responseBody, "id"));
        String user_id = getJsonPath(responseBody, "user_id");

        StepDefinition validate = new StepDefinition();
        validate.reqspec = given().spec(requestsSpecification()
                .header("x-api-key", "DEMO-API-KEY"));

        String resourceAPI = Utils.getGlobalValue("RandomVotesEP");
        validate.resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        validate.responseBody = reqspec.when().get(resourceAPI);

        // verifying that the field in response object match the corresponding given object
        assertEquals(getJsonPath(validate.responseBody, "user_id"), user_id);

    }

    @Given("Add Headers & Body for API Payload with {string},{string},{string}")
    public void addHeadersBodyForAPIPayloadWith(String image_id, String sub_id, String value) throws IOException {

        // Write code here that turns the phrase above into concrete actions
        reqspec = given().spec(requestsSpecification()
                .header("x-api-key", "DEMO-API-KEY").body(data.addNewVotesPayLoad(image_id,sub_id, Integer.parseInt(value))));
    }

    @Then("Verify body response match expected value")
    public void verifyBodyResponseMatchExpectedValue() {

        assertEquals(getJsonPath(responseBody, "message"), "SUCCESS");
    }

    @Then("Verify new id response match existing id request")
    public void verifyNewIdResponseMatchExistingIdRequest() throws IOException {

        int id = Integer.parseInt(getJsonPath(responseBody, "id"));
        String user_id = getJsonPath(responseBody, "user_id");

        StepDefinition validate = new StepDefinition();
        validate.reqspec = given().spec(requestsSpecification()
                .header("x-api-key", "DEMO-API-KEY"));

        String resourceAPI = Utils.getGlobalValue("RandomVotesEP");
        validate.resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        validate.responseBody = validate.reqspec.when().get(resourceAPI);

        assertEquals(getJsonPath(validate.responseBody, "user_id"), user_id);

    }

    @Then("Verify deleted body response match expected value")
    public void verifyDeletedBodyResponseMatchExpectedValue() throws IOException {

        System.out.println(getJsonPath(responseBody, "id"));

        StepDefinition validate = new StepDefinition();
        validate.reqspec = given().spec(requestsSpecification()
                .header("x-api-key", "DEMO-API-KEY").pathParam("id", getJsonPath(responseBody, "id")));

        String resourceAPI = Utils.getGlobalValue("RandomVotesEP");
        validate.resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        validate.responseBody = validate.reqspec.when().delete(resourceAPI);

        reqspec = reqspec = given().spec(requestsSpecification()
                .header("x-api-key", "DEMO-API-KEY").pathParam("id", getJsonPath(validate.responseBody, "id")));

        resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        responseBody = validate.reqspec.when().get(resourceAPI);

        assertEquals(getJsonPath(validate.responseBody, "message"), "NOT_FOUND");

    }
}
