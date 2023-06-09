package everythingAboutYourPets;

import com.google.gson.JsonObject;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import javax.sound.midi.Soundbank;
import java.util.*;

public class PetOperations {
    public static final String BASE_URL = "https://petstore.swagger.io/v2";
    @Test
    public void createANewPetToTheStore() {
//        {
        //the value of "id" is a single String
//            "id": 100,
        //the value of a "category" is a single object
//                "category": {
//            "id": 200,
//                    "name": "Black"
//        }, //here "name" is a key
//            "name": "Eric",
        //the value of "photoUrls" is an array of String
//                "photoUrls": [
//                    "E:\\black dog 1.jpg",
//                    "E:\\black dog 2.jpg",
//                    "E:\\black dog 3.jpg"
//    ],
        //the value of "tags" is a single array of objects
//            "tags": [
//            {
//                "id": 300,
//                    "name": "Black Shepard"
//            },
//            {
//                "id": 301,
//                    "name": "Beautiful Dog"
//            },
//            {
//                "id": 302,
//                    "name": "Pretty Dog"
//            }
//    ], //here "status" is a key
//            "status": "available"
//        }
        Map<String, Object> categoryValuePayload = new HashMap<String, Object>();
        categoryValuePayload.put("id", 200);
        categoryValuePayload.put("name", "Black");

        Map<String, Object> tagsPayloadForFirstObject = new HashMap<String, Object>();
        tagsPayloadForFirstObject.put("id", 300);
        tagsPayloadForFirstObject.put("name", "Black Shepard");

        Map<String, Object> tagsPayloadForSecondObject = new HashMap<String, Object>();
        tagsPayloadForSecondObject.put("id", 301);
        tagsPayloadForSecondObject.put("name", "Beautiful Dog");

        Map<String, Object> tagsPayloadForThirdObject = new HashMap<String, Object>();
        tagsPayloadForThirdObject.put("id", 302);
        tagsPayloadForThirdObject.put("name", "Pretty Dog");

        List<Map<String, Object>> tagsPayload = new ArrayList<Map<String, Object>>();
        tagsPayload.add(tagsPayloadForFirstObject);
        tagsPayload.add(tagsPayloadForSecondObject);
        tagsPayload.add(tagsPayloadForThirdObject);



        Map<String, Object> requestBodyPayload = new HashMap<String, Object>();
        requestBodyPayload.put("id", 100);
        requestBodyPayload.put("category", categoryValuePayload);
        requestBodyPayload.put("name", "Erik");
        requestBodyPayload.put("photoUrls", Arrays.asList("E:\\black dog 1.jpg", "E:\\black dog 2.jpg","E:\\black dog 3.jpg"));
        requestBodyPayload.put("tags", tagsPayload);
        requestBodyPayload.put("status", "available");

        //object can be converted in two ways: 1. Json (when converting a single object)
        // 2.Gson (when the object is Array of objects)
        JSONObject payload = new JSONObject(requestBodyPayload);

        System.out.println(payload);

        Response response = given().
                body(payload.toJSONString()).
                contentType("application/json").
        when().
                post(BASE_URL + "/pet").
        then().
                assertThat().
                statusCode(200).
                log().all().extract().response();

        ValidatableResponse validatableResponse = response.then();

        //are there 6 keys such as "id", "category"
        // "name", "photoUrls", "tags", and "status"?
        validatableResponse.body("$", hasKey("id"));
        validatableResponse.body("$", hasKey("category"));
        validatableResponse.body("$", hasKey("name"));
        validatableResponse.body("$", hasKey("photoUrls"));
        validatableResponse.body("$", hasKey("tags"));
        validatableResponse.body("$", hasKey("status"));


        //Are there 6 values for 6 keys?
        validatableResponse.body("id", is(notNullValue()));
        validatableResponse.body("category", is(notNullValue()));
        validatableResponse.body("name", is(notNullValue()));
        validatableResponse.body("photoUrls", is(notNullValue()));
        validatableResponse.body("tags", is(notNullValue()));
        validatableResponse.body("status", is(notNullValue()));

        JsonPath jsonPathEvaluator = response.jsonPath();

        //Are the values for the particular keys are matching or valid?
        assertEquals((Integer) jsonPathEvaluator.get("id"), 100);

        assertEquals((Integer) jsonPathEvaluator.get("category.id"), 200);
        assertEquals(jsonPathEvaluator.get("category.name"), "Black");

        assertEquals(jsonPathEvaluator.get("name"), "Erik");

        assertEquals(jsonPathEvaluator.get("photoUrls[0]"), "E:\\black dog 1.jpg");
        assertEquals(jsonPathEvaluator.get("photoUrls[1]"), "E:\\black dog 2.jpg");
        assertEquals(jsonPathEvaluator.get("photoUrls[2]"), "E:\\black dog 3.jpg");

        assertEquals((Integer)jsonPathEvaluator.get("tags[0].id"), 300);
        assertEquals(jsonPathEvaluator.get("tags[0].name"), "Black Shepard");

        assertEquals((Integer)jsonPathEvaluator.get("tags[1].id"), 301);
        assertEquals(jsonPathEvaluator.get("tags[1].name"), "Beautiful Dog");

        assertEquals((Integer)jsonPathEvaluator.get("tags[2].id"), 302);
        assertEquals(jsonPathEvaluator.get("tags[2].name"), "Pretty Dog");

        assertEquals(jsonPathEvaluator.get("status"), "available");
    }

}
