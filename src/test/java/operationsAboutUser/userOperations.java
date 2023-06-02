package operationsAboutUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import com.google.gson.Gson;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.*;

public class userOperations {
    public static final String BASE_URL = "https://petstore.swagger.io/v2";
    public static final int ID = 1000;
    public static final String USER_NAME = "Tamanna123";
    public static final String FIRST_NAME = "Tamanna";
    public static final String LAST_NAME = "Tasnim";
    public static final String EMAIL = "tamanna.tasnim.akhi@gmail.com";
    public static final String PASSWORD = "chayan1@4";
    public static final String PHONE = "01728805168";
    public static final int USER_STATUS = 1;

    public static final String UPDATED_EMAIL = "akhi.pojf@gmail.com";
    public static final String UPDATED_PASSWORD = "chayan1@4chayan1@4";

    @Test(priority = 0)

    public void createAnUser() {



        //mapping start
        Map<String, Object> payload = new HashMap<String, Object>();
        payload.put("id", ID);
        payload.put("username", USER_NAME);
        payload.put("firstName", FIRST_NAME);
        payload.put("lastName", LAST_NAME);
        payload.put("email", EMAIL);
        payload.put("password", PASSWORD);
        payload.put("phone", PHONE);
        payload.put("userStatus", USER_STATUS);
        //mapping end

        //convert to Json Object start
        JSONObject request = new JSONObject(payload);
        System.out.println(request);
        //convert to Json Object end


        //sending a request start
        Response response = given().
                body(request.toJSONString()).
                contentType("application/json").
                when().
                post(BASE_URL + "/user").  //sending a request end
                        then() //extracting the request and saving it to a variable named response
                .assertThat().statusCode(200).log().all().extract().response();


        // body example:
        // {
        // "code": 200,
        // "type": "unknown",
        // "message": "1000"
        // }


        //through response variable, validating the response in three more steps start
        ValidatableResponse validatableResponse = response.then();

        //are there 3 keys such as "code", "type" and "message"?
        validatableResponse.body("$", hasKey("code"));
        validatableResponse.body("$", hasKey("type"));
        validatableResponse.body("$", hasKey("message"));

        //Are there 3 values for 3 keys?
        validatableResponse.body("code", is(notNullValue()));
        validatableResponse.body("type", is(notNullValue()));
        validatableResponse.body("message", is(notNullValue()));

        JsonPath jsonPathEvaluator = response.jsonPath();

        //Are the values for the particular keys are matching or valid?
        assertEquals((Integer) jsonPathEvaluator.get("code"), 200);
        assertEquals(jsonPathEvaluator.get("type"), "unknown");
        assertEquals(jsonPathEvaluator.get("message"), Integer.toString(ID));
        //through response variable, validating the response in three more steps end
    }

    @Test (priority = 1)
    public void getUserByUserName() {

//		{
//		    "id": 9223372036854747349,
//		    "username": "Tamanna123",
//		    "firstName": "Tamanna",
//		    "lastName": "Tasnim",
//		    "email": "tamanna.tasnim.akhi@gmail.com",
//		    "password": "chayan1@4",
//		    "phone": "01728805188",
//		    "userStatus": 1
//		}
        //get request = we are trying to get information, for this why we don't need
        //anybody(payload) for get request.
        Response response =
                given().
                        pathParam("username", USER_NAME).
                        when().
                        get(BASE_URL + "/user/{username}").
                        then().
                        assertThat().statusCode(200).log().all().extract().response();

        ValidatableResponse validatableResponse = response.then();

        //are there 8 keys such as "id", "username", "firstName",
        //"lastName", "email", "password", "phone" and "userStatus"?
        validatableResponse.body("$", hasKey("id"));
        validatableResponse.body("$", hasKey("username"));
        validatableResponse.body("$", hasKey("firstName"));
        validatableResponse.body("$", hasKey("lastName"));
        validatableResponse.body("$", hasKey("email"));
        validatableResponse.body("$", hasKey("password"));
        validatableResponse.body("$", hasKey("phone"));
        validatableResponse.body("$", hasKey("userStatus"));

        //Are there 8 values for 8 keys?
        validatableResponse.body("id", is(notNullValue()));
        validatableResponse.body("username", is(notNullValue()));
        validatableResponse.body("firstName", is(notNullValue()));
        validatableResponse.body("lastName", is(notNullValue()));
        validatableResponse.body("email", is(notNullValue()));
        validatableResponse.body("password", is(notNullValue()));
        validatableResponse.body("phone", is(notNullValue()));
        validatableResponse.body("userStatus", is(notNullValue()));

        JsonPath jsonPathEvaluator = response.jsonPath();

        //Are the values for the particular keys are matching or valid?
        assertEquals((Integer)jsonPathEvaluator.get("id"), ID);
        assertEquals(jsonPathEvaluator.get("username"), USER_NAME);
        assertEquals(jsonPathEvaluator.get("firstName"), FIRST_NAME);
        assertEquals(jsonPathEvaluator.get("lastName"), LAST_NAME);
        assertEquals(jsonPathEvaluator.get("email"), EMAIL);
        assertEquals(jsonPathEvaluator.get("password"), PASSWORD);
        assertEquals(jsonPathEvaluator.get("phone"), PHONE);
        assertEquals((Integer)jsonPathEvaluator.get("userStatus"), USER_STATUS);



    }
    @Test (priority = 2)
    public void updateAnUser() {

        //mapping start
        Map<String, Object> payload = new HashMap<String, Object>();
        payload.put("id", ID);
        payload.put("username", USER_NAME);
        payload.put("firstName", FIRST_NAME);
        payload.put("lastName", LAST_NAME);
        payload.put("email", UPDATED_EMAIL);
        payload.put("password", UPDATED_PASSWORD);
        payload.put("phone", PHONE);
        payload.put("userStatus", USER_STATUS);
        //mapping end

        //convert to Json Object start
        JSONObject request = new JSONObject(payload);
        System.out.println(request);
        //convert to Json Object end
        Response response =
                given().
                        pathParam("username", USER_NAME).
                        body(request.toJSONString()).
                        contentType("application/json").
                        when().
                        put(BASE_URL + "/user/{username}").
                        then().
                        assertThat().statusCode(200).log().all().extract().response();

        ValidatableResponse validatableResponse = response.then();

//		        {
//		            "code": 200,
//		            "type": "unknown",
//		            "message": "1000"
//		        }

        //are there 3 keys such as "id", "username", "firstName",
        //"lastName", "email", "password", "phone" and "userStatus"?
        validatableResponse.body("$", hasKey("code"));
        validatableResponse.body("$", hasKey("type"));
        validatableResponse.body("$", hasKey("message"));


        //Are there 3 values for 3 keys?
        validatableResponse.body("code", is(notNullValue()));
        validatableResponse.body("type", is(notNullValue()));
        validatableResponse.body("message", is(notNullValue()));


        JsonPath jsonPathEvaluator = response.jsonPath();

        //Are the values for the particular keys are matching or valid?
        assertEquals((Integer)jsonPathEvaluator.get("code"), 200);
        assertEquals(jsonPathEvaluator.get("type"), "unknown");
        assertEquals(jsonPathEvaluator.get("message"), Integer.toString(ID));



    }

    @Test (priority = 3)
    public void getUpdatedUserByUserName() {
        // {
        // "id": 9223372036854747349,
        // "username": "Tamanna123",
        // "firstName": "Tamanna",
        // "lastName": "Tasnim",
        // "email": "akhi.pojf@gmail.com",
        // "password": "chayan1@4",
        // "phone": "01728805188",
        // "userStatus": 1
        // }

        Response response =
                given().
                        pathParam("username", USER_NAME).
                        when().
                        get(BASE_URL + "/user/{username}").
                        then().
                        assertThat().statusCode(200).log().all().extract().response();

        ValidatableResponse validatableResponse = response.then();

        //are there 8 keys such as "id", "username", "firstName",
        //"lastName", "email", "password", "phone" and "userStatus"?
        validatableResponse.body("$", hasKey("id"));
        validatableResponse.body("$", hasKey("username"));
        validatableResponse.body("$", hasKey("firstName"));
        validatableResponse.body("$", hasKey("lastName"));
        validatableResponse.body("$", hasKey("email"));
        validatableResponse.body("$", hasKey("password"));
        validatableResponse.body("$", hasKey("phone"));
        validatableResponse.body("$", hasKey("userStatus"));

        //Are there 8 values for 8 keys?
        validatableResponse.body("id", is(notNullValue()));
        validatableResponse.body("username", is(notNullValue()));
        validatableResponse.body("firstName", is(notNullValue()));
        validatableResponse.body("lastName", is(notNullValue()));
        validatableResponse.body("email", is(notNullValue()));
        validatableResponse.body("password", is(notNullValue()));
        validatableResponse.body("phone", is(notNullValue()));
        validatableResponse.body("userStatus", is(notNullValue()));

        JsonPath jsonPathEvaluator = response.jsonPath();

        //Are the values for the particular keys are matching or valid?
        assertEquals((Integer)jsonPathEvaluator.get("id"), ID);
        assertEquals(jsonPathEvaluator.get("username"), USER_NAME);
        assertEquals(jsonPathEvaluator.get("firstName"), FIRST_NAME);
        assertEquals(jsonPathEvaluator.get("lastName"), LAST_NAME);
        assertEquals(jsonPathEvaluator.get("email"), UPDATED_EMAIL);
        assertEquals(jsonPathEvaluator.get("password"), UPDATED_PASSWORD);
        assertEquals(jsonPathEvaluator.get("phone"), PHONE);
        assertEquals((Integer)jsonPathEvaluator.get("userStatus"), USER_STATUS);



    }
    @Test (priority = 4  )
    public void deleteUser() {
        Response response =  given().
                pathParam("username", USER_NAME).
                when().
                delete(BASE_URL + "/user/{username}").
                then().
                assertThat().statusCode(200).log().all().extract().response();

        //through response variable, validating the response in three more steps start
        ValidatableResponse validatableResponse = response.then();

        // body example:
        // {
//			    "code": 200,
//			    "type": "unknown",
//			    "message": "Tamanna123"
//			}

        //are there 3 keys such as "code", "type" and "message"?
        validatableResponse.body("$", hasKey("code"));
        validatableResponse.body("$", hasKey("type"));
        validatableResponse.body("$", hasKey("message"));

        //Are there 3 values for 3 keys?
        validatableResponse.body("code", is(notNullValue()));
        validatableResponse.body("type", is(notNullValue()));
        validatableResponse.body("message", is(notNullValue()));

        JsonPath jsonPathEvaluator = response.jsonPath();

        //Are the values for the particular keys are matching or valid?
        assertEquals((Integer)jsonPathEvaluator.get("code"), 200);
        assertEquals(jsonPathEvaluator.get("type"), "unknown");
        assertEquals(jsonPathEvaluator.get("message"), USER_NAME);
        //through response variable, validating the response in three more steps end
    }
    @Test
    public void createListOfUsersUsingArray() {
//		[
//		    {
//		        "id": 1000,
//		        "username": "Sabah456",
//		        "firstName": "Munshi",
//		        "lastName": "Saif",
//		        "email": "saif@gmail.com",
//		        "password": "saif0990",
//		        "phone": "01236548965",
//		        "userStatus": 1
//		    },
//		    {
//		        "id": 1001,
//		        "username": "Ananya456",
//		        "firstName": "Tazkia",
//		        "lastName": "Zaman",
//		        "email": "zaman@gmail.com",
//		        "password": "ananya0990",
//		        "phone": "0123615665",
//		        "userStatus": 1
//		    },
//		    {
//		        "id": 1002,
//		        "username": "Saad456",
//		        "firstName": "Bin",
//		        "lastName": "Zaman",
//		        "email": "bin@gmail.com",
//		        "password": "binbin0990",
//		        "phone": "01564563525",
//		        "userStatus": 1
//		    }
//		]

        Map<String, Object> payloadForFirstObject = new HashMap<String, Object>();
        payloadForFirstObject.put("id", 1000);
        payloadForFirstObject.put("username", "Sabah456");
        payloadForFirstObject.put("firstName", "Munshi");
        payloadForFirstObject.put("lastName", "Saif");
        payloadForFirstObject.put("email", "saif@gmail.com");
        payloadForFirstObject.put("password", "saif0990");
        payloadForFirstObject.put("phone", "01236548965");
        payloadForFirstObject.put("userStatus", 1);

        Map<String, Object> payloadForSecondObject = new HashMap<String, Object>();
        payloadForSecondObject.put("id", 1001);
        payloadForSecondObject.put("username", "Ananya456");
        payloadForSecondObject.put("firstName", "Tazkia");
        payloadForSecondObject.put("lastName", "Zaman");
        payloadForSecondObject.put("email", "zaman@gmail.com");
        payloadForSecondObject.put("password", "ananya0990");
        payloadForSecondObject.put("phone", "0123615665");
        payloadForSecondObject.put("userStatus", 1);

        Map<String, Object> payloadForThirdObject = new HashMap<String, Object>();
        payloadForThirdObject.put("id", 1002);
        payloadForThirdObject.put("username", "Saad456");
        payloadForThirdObject.put("firstName", "Bin");
        payloadForThirdObject.put("lastName", "Zaman");
        payloadForThirdObject.put("email", "bin@gmail.com");
        payloadForThirdObject.put("password", "binbin0990");
        payloadForThirdObject.put("phone", "01564563525");
        payloadForThirdObject.put("userStatus", 1);

        List<Map<String, Object>> arrayOfObjects = new ArrayList<Map<String,Object>>();
        arrayOfObjects.add(payloadForFirstObject);
        arrayOfObjects.add(payloadForSecondObject);
        arrayOfObjects.add(payloadForThirdObject);

        //converting arrayOfObjects to json format by using Gson class
        Gson gson = new Gson();
        String payload = gson.toJson(arrayOfObjects);
        System.out.println(payload);

        Response response = given().
                body(payload).
                contentType("application/json").
                when().
                post(BASE_URL + "/user/createWithArray").
                then().
                assertThat().statusCode(200).log().all().extract().response();

//		{
//		    "code": 200,
//		    "type": "unknown",
//		    "message": "ok"
//		}
        ValidatableResponse validatableResponse = response.then();
        //are there 3 keys such as "code", "type" and "message"?
        validatableResponse.body("$", hasKey("code"));
        validatableResponse.body("$", hasKey("type"));
        validatableResponse.body("$", hasKey("message"));

        //Are there 3 values for 3 keys?
        validatableResponse.body("code", is(notNullValue()));
        validatableResponse.body("type", is(notNullValue()));
        validatableResponse.body("message", is(notNullValue()));

        JsonPath jsonPathEvaluator = response.jsonPath();

        //Are the values for the particular keys are matching or valid?
        assertEquals((Integer)jsonPathEvaluator.get("code"), 200);
        assertEquals(jsonPathEvaluator.get("type"), "unknown");
        assertEquals(jsonPathEvaluator.get("message"), "ok");



    }

    @Test
    public void createListOfUsersUsingList() {
//		[
//		    {
//		        "id": 1000,
//		        "username": "Sabah456",
//		        "firstName": "Munshi",
//		        "lastName": "Saif",
//		        "email": "saif@gmail.com",
//		        "password": "saif0990",
//		        "phone": "01236548965",
//		        "userStatus": 1
//		    },
//		    {
//		        "id": 1001,
//		        "username": "Ananya456",
//		        "firstName": "Tazkia",
//		        "lastName": "Zaman",
//		        "email": "zaman@gmail.com",
//		        "password": "ananya0990",
//		        "phone": "0123615665",
//		        "userStatus": 1
//		    },
//		    {
//		        "id": 1002,
//		        "username": "Saad456",
//		        "firstName": "Bin",
//		        "lastName": "Zaman",
//		        "email": "bin@gmail.com",
//		        "password": "binbin0990",
//		        "phone": "01564563525",
//		        "userStatus": 1
//		    }
//		]

        Map<String, Object> payloadForFirstObject = new HashMap<String, Object>();
        payloadForFirstObject.put("id", 1000);
        payloadForFirstObject.put("username", "Sabah456");
        payloadForFirstObject.put("firstName", "Munshi");
        payloadForFirstObject.put("lastName", "Saif");
        payloadForFirstObject.put("email", "saif@gmail.com");
        payloadForFirstObject.put("password", "saif0990");
        payloadForFirstObject.put("phone", "01236548965");
        payloadForFirstObject.put("userStatus", 1);

        Map<String, Object> payloadForSecondObject = new HashMap<String, Object>();
        payloadForSecondObject.put("id", 1001);
        payloadForSecondObject.put("username", "Ananya456");
        payloadForSecondObject.put("firstName", "Tazkia");
        payloadForSecondObject.put("lastName", "Zaman");
        payloadForSecondObject.put("email", "zaman@gmail.com");
        payloadForSecondObject.put("password", "ananya0990");
        payloadForSecondObject.put("phone", "0123615665");
        payloadForSecondObject.put("userStatus", 1);

        Map<String, Object> payloadForThirdObject = new HashMap<String, Object>();
        payloadForThirdObject.put("id", 1002);
        payloadForThirdObject.put("username", "Saad456");
        payloadForThirdObject.put("firstName", "Bin");
        payloadForThirdObject.put("lastName", "Zaman");
        payloadForThirdObject.put("email", "bin@gmail.com");
        payloadForThirdObject.put("password", "binbin0990");
        payloadForThirdObject.put("phone", "01564563525");
        payloadForThirdObject.put("userStatus", 1);

        List<Map<String, Object>> arrayOfObjects = new ArrayList<Map<String,Object>>();
        arrayOfObjects.add(payloadForFirstObject);
        arrayOfObjects.add(payloadForSecondObject);
        arrayOfObjects.add(payloadForThirdObject);

        //converting arrayOfObjects to json format by using Gson class
        Gson gson = new Gson();
        String payload = gson.toJson(arrayOfObjects);
        System.out.println(payload);

        Response response = given().
                body(payload).
                contentType("application/json").
                when().
                post(BASE_URL + "/user/createWithList").
                then().
                assertThat().statusCode(200).log().all().extract().response();

//		{
//		    "code": 200,
//		    "type": "unknown",
//		    "message": "ok"
//		}
        ValidatableResponse validatableResponse = response.then();
        //are there 3 keys such as "code", "type" and "message"?
        validatableResponse.body("$", hasKey("code"));
        validatableResponse.body("$", hasKey("type"));
        validatableResponse.body("$", hasKey("message"));

        //Are there 3 values for 3 keys?
        validatableResponse.body("code", is(notNullValue()));
        validatableResponse.body("type", is(notNullValue()));
        validatableResponse.body("message", is(notNullValue()));

        JsonPath jsonPathEvaluator = response.jsonPath();

        //Are the values for the particular keys are matching or valid?
        assertEquals((Integer)jsonPathEvaluator.get("code"), 200);
        assertEquals(jsonPathEvaluator.get("type"), "unknown");
        assertEquals(jsonPathEvaluator.get("message"), "ok");



    }
    // Key-Value pair
    // Key --> String
    // "email": "tamanna.tasnim.akhi@gmail.com",
    // "password": "chayan1@4",
    // "phone": "01728805188",
    // "userStatus": 1
    // }
}
// Key-Value pair
// Key --> String
// "email": "tamanna.tasnim.akhi@gmail.com",
// "password": "chayan1@4",
// "phone": "01728805188",
// "userStatus": 1
// }

