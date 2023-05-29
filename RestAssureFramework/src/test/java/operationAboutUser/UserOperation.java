package operationAboutUser;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.*;

public class UserOperation {

	public static final String BASE_URL = "https://petstore.swagger.io/v2";
	public static final int ID = 1000;
	public static final String USER_NAME = "Tamanna123";
	public static final String FIRST_NAME = "Tamanna";
	public static final String LAST_NAME = "Tasnim";
	public static final String EMAIL = "tamanna.tasnim.akhi@gmail.com";
	public static final String PASSWORD = "chayan1@4";
	public static final String PHONE = "01728805188";
	public static final int USER_STATUS = 1;

	@Test

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
	    assertEquals(jsonPathEvaluator.get("code"), 200);
	    assertEquals(jsonPathEvaluator.get("type"), "unknown");
	    assertEquals(jsonPathEvaluator.get("message"), Integer.toString(ID));
	  //through response variable, validating the response in three more steps end
	}

	@Test
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
			    assertEquals(jsonPathEvaluator.get("id"), ID);
			    assertEquals(jsonPathEvaluator.get("username"), USER_NAME);
			    assertEquals(jsonPathEvaluator.get("firstName"), FIRST_NAME);
			    assertEquals(jsonPathEvaluator.get("lastName"), LAST_NAME);
			    assertEquals(jsonPathEvaluator.get("email"), EMAIL);
			    assertEquals(jsonPathEvaluator.get("password"), PASSWORD);
			    assertEquals(jsonPathEvaluator.get("phone"), PHONE);
			    assertEquals(jsonPathEvaluator.get("userStatus"), USER_STATUS);
			   
				
		
	}
	// Key-Value pair
	// Key --> String
	// "email": "tamanna.tasnim.akhi@gmail.com",
	// "password": "chayan1@4",
	// "phone": "01728805188",
	// "userStatus": 1
	// }

}
