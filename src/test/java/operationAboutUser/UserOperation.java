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

	@Test

	public void createAnUser() {
		
		int id = 1000;

		//mapping start
		Map<String, Object> payload = new HashMap<String, Object>();
		payload.put("id", id);
		payload.put("username", "Tamanna123");
		payload.put("firstName", "Tamanna");
		payload.put("lastName", "Tasnim");
		payload.put("email", "tamanna.tasnim.akhi@gmail.com");
		payload.put("password", "chayan1@4");
		payload.put("phone", "01728805188");
		payload.put("userStatus", "1");
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
	    assertEquals(jsonPathEvaluator.get("message"), Integer.toString(id));
	  //through response variable, validating the response in three more steps end
	}

	// Key-Value pair
	// Key --> String
	// "email": "tamanna.tasnim.akhi@gmail.com",
	// "password": "chayan1@4",
	// "phone": "01728805188",
	// "userStatus": 1
	// }

}
