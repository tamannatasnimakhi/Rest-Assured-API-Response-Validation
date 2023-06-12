package everythingAboutYourPets;

import com.google.gson.JsonObject;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import javax.sound.midi.Soundbank;
import java.util.*;

public class PetOperations {
    public static final String BASE_URL = "https://petstore.swagger.io/v2";
    public static int id = 100;
    public static int categoryId = 200;
    public static String categoryName = "Black";
    public static String petName = "Erik";
    public static String firstPhoto = "E:\\black dog 1.jpg";
    public static String secondPhoto = "E:\\black dog 2.jpg";
    public static String thirdPhoto = "E:\\black dog 3.jpg";
    public static int firstTagId = 300;
    public static String firstTagName = "Black Shephard";
    public static int secondTagId = 301;
    public static String secondTagName = "Beautiful Dog";
    public static int thirdTagId = 302;
    public static String thirdTagName = "Pretty Dog";
    public static String petStatus = "available";
    public static String updatedCategoryName = "White";
    public static String updatedPetName = "Montu";

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
        categoryValuePayload.put("id", categoryId);
        categoryValuePayload.put("name", categoryName);

        Map<String, Object> tagsPayloadForFirstObject = new HashMap<String, Object>();
        tagsPayloadForFirstObject.put("id", firstTagId);
        tagsPayloadForFirstObject.put("name", firstTagName);

        Map<String, Object> tagsPayloadForSecondObject = new HashMap<String, Object>();
        tagsPayloadForSecondObject.put("id", secondTagId);
        tagsPayloadForSecondObject.put("name", secondTagName);

        Map<String, Object> tagsPayloadForThirdObject = new HashMap<String, Object>();
        tagsPayloadForThirdObject.put("id", thirdTagId);
        tagsPayloadForThirdObject.put("name", thirdTagName);

        List<Map<String, Object>> tagsPayload = new ArrayList<Map<String, Object>>();
        tagsPayload.add(tagsPayloadForFirstObject);
        tagsPayload.add(tagsPayloadForSecondObject);
        tagsPayload.add(tagsPayloadForThirdObject);



        Map<String, Object> requestBodyPayload = new HashMap<String, Object>();
        requestBodyPayload.put("id", id);
        requestBodyPayload.put("category", categoryValuePayload);
        requestBodyPayload.put("name", petName);
        requestBodyPayload.put("photoUrls", Arrays.asList(firstPhoto, secondPhoto,thirdPhoto));
        requestBodyPayload.put("tags", tagsPayload);
        requestBodyPayload.put("status", petStatus);

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
        assertEquals((Integer) jsonPathEvaluator.get("id"), id);

        assertEquals((Integer) jsonPathEvaluator.get("category.id"), categoryId);
        assertEquals(jsonPathEvaluator.get("category.name"), categoryName);

        assertEquals(jsonPathEvaluator.get("name"), petName);

        assertEquals(jsonPathEvaluator.get("photoUrls[0]"), firstPhoto);
        assertEquals(jsonPathEvaluator.get("photoUrls[1]"), secondPhoto);
        assertEquals(jsonPathEvaluator.get("photoUrls[2]"), thirdPhoto);

        assertEquals((Integer)jsonPathEvaluator.get("tags[0].id"), firstTagId);
        assertEquals(jsonPathEvaluator.get("tags[0].name"), firstTagName);

        assertEquals((Integer)jsonPathEvaluator.get("tags[1].id"), secondTagId);
        assertEquals(jsonPathEvaluator.get("tags[1].name"), secondTagName);

        assertEquals((Integer)jsonPathEvaluator.get("tags[2].id"), thirdTagId);
        assertEquals(jsonPathEvaluator.get("tags[2].name"), thirdTagName);

        assertEquals(jsonPathEvaluator.get("status"), petStatus);
    }
    @Test
    public void findPetById(){
        Response response = given().
                pathParam("petId", id).
        when().
                get(BASE_URL + "/pet/{petId}").
        then().
                assertThat().statusCode(200).
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
        assertEquals((Integer) jsonPathEvaluator.get("id"), id);

        assertEquals((Integer) jsonPathEvaluator.get("category.id"), categoryId);
        assertEquals(jsonPathEvaluator.get("category.name"), categoryName);

        assertEquals(jsonPathEvaluator.get("name"), petName);

        assertEquals(jsonPathEvaluator.get("photoUrls[0]"), firstPhoto);
        assertEquals(jsonPathEvaluator.get("photoUrls[1]"), secondPhoto);
        assertEquals(jsonPathEvaluator.get("photoUrls[2]"), thirdPhoto);

        assertEquals((Integer)jsonPathEvaluator.get("tags[0].id"), firstTagId);
        assertEquals(jsonPathEvaluator.get("tags[0].name"), firstTagName);

        assertEquals((Integer)jsonPathEvaluator.get("tags[1].id"), secondTagId);
        assertEquals(jsonPathEvaluator.get("tags[1].name"), secondTagName);

        assertEquals((Integer)jsonPathEvaluator.get("tags[2].id"), thirdTagId);
        assertEquals(jsonPathEvaluator.get("tags[2].name"), thirdTagName);

        assertEquals(jsonPathEvaluator.get("status"), petStatus);
    }
@Test
    public void updateAnExistingPet() {

//    {
//        "id": 100,
//            "category": {
//        "id": 200,
//                "name": "White"
//    },
//        "name": "Montu",
//            "photoUrls": [
//        "E:\\black dog 1.jpg",
//                "E:\\black dog 2.jpg",
//                "E:\\black dog 3.jpg"
//    ],
//        "tags": [
//        {
//            "id": 300,
//                "name": "Black Shephard"
//        },
//        {
//            "id": 301,
//                "name": "Beautiful Dog"
//        },
//        {
//            "id": 302,
//                "name": "Pretty Dog"
//        }
//    ],
//        "status": "available"
//    }
    Map<String, Object> categoryValuePayload = new HashMap<String, Object>();
    categoryValuePayload.put("id", categoryId);
    categoryValuePayload.put("name", updatedCategoryName);

    Map<String, Object> tagsPayloadForFirstObject = new HashMap<String, Object>();
    tagsPayloadForFirstObject.put("id", firstTagId);
    tagsPayloadForFirstObject.put("name", firstTagName);

    Map<String, Object> tagsPayloadForSecondObject = new HashMap<String, Object>();
    tagsPayloadForSecondObject.put("id", secondTagId);
    tagsPayloadForSecondObject.put("name", secondTagName);

    Map<String, Object> tagsPayloadForThirdObject = new HashMap<String, Object>();
    tagsPayloadForThirdObject.put("id", thirdTagId);
    tagsPayloadForThirdObject.put("name", thirdTagName);

    List<Map<String, Object>> tagsPayload = new ArrayList<Map<String, Object>>();
    tagsPayload.add(tagsPayloadForFirstObject);
    tagsPayload.add(tagsPayloadForSecondObject);
    tagsPayload.add(tagsPayloadForThirdObject);



    Map<String, Object> requestBodyPayload = new HashMap<String, Object>();
    requestBodyPayload.put("id", id);
    requestBodyPayload.put("category", categoryValuePayload);
    requestBodyPayload.put("name", updatedPetName);
    requestBodyPayload.put("photoUrls", Arrays.asList(firstPhoto, secondPhoto,thirdPhoto));
    requestBodyPayload.put("tags", tagsPayload);
    requestBodyPayload.put("status", petStatus);

    JSONObject payload = new JSONObject(requestBodyPayload);

    System.out.println(payload);

    Response response = given().
            body(payload.toJSONString()).
            contentType("application/json").
            when().
            put(BASE_URL + "/pet").
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
    assertEquals((Integer) jsonPathEvaluator.get("id"), id);

    assertEquals((Integer) jsonPathEvaluator.get("category.id"), categoryId);
    assertEquals(jsonPathEvaluator.get("category.name"), updatedCategoryName);

    assertEquals(jsonPathEvaluator.get("name"), updatedPetName);

    assertEquals(jsonPathEvaluator.get("photoUrls[0]"), firstPhoto);
    assertEquals(jsonPathEvaluator.get("photoUrls[1]"), secondPhoto);
    assertEquals(jsonPathEvaluator.get("photoUrls[2]"), thirdPhoto);

    assertEquals((Integer)jsonPathEvaluator.get("tags[0].id"), firstTagId);
    assertEquals(jsonPathEvaluator.get("tags[0].name"), firstTagName);

    assertEquals((Integer)jsonPathEvaluator.get("tags[1].id"), secondTagId);
    assertEquals(jsonPathEvaluator.get("tags[1].name"), secondTagName);

    assertEquals((Integer)jsonPathEvaluator.get("tags[2].id"), thirdTagId);
    assertEquals(jsonPathEvaluator.get("tags[2].name"), thirdTagName);

    assertEquals(jsonPathEvaluator.get("status"), petStatus);
    }

    @Test
    public void reCheckPetById(){
        Response response = given().
                pathParam("petId", id).
                when().
                get(BASE_URL + "/pet/{petId}").
                then().
                assertThat().statusCode(200).
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
        assertEquals((Integer) jsonPathEvaluator.get("id"), id);

        assertEquals((Integer) jsonPathEvaluator.get("category.id"), categoryId);
        assertEquals(jsonPathEvaluator.get("category.name"), updatedCategoryName);

        assertEquals(jsonPathEvaluator.get("name"), updatedPetName);

        assertEquals(jsonPathEvaluator.get("photoUrls[0]"), firstPhoto);
        assertEquals(jsonPathEvaluator.get("photoUrls[1]"), secondPhoto);
        assertEquals(jsonPathEvaluator.get("photoUrls[2]"), thirdPhoto);

        assertEquals((Integer)jsonPathEvaluator.get("tags[0].id"), firstTagId);
        assertEquals(jsonPathEvaluator.get("tags[0].name"), firstTagName);

        assertEquals((Integer)jsonPathEvaluator.get("tags[1].id"), secondTagId);
        assertEquals(jsonPathEvaluator.get("tags[1].name"), secondTagName);

        assertEquals((Integer)jsonPathEvaluator.get("tags[2].id"), thirdTagId);
        assertEquals(jsonPathEvaluator.get("tags[2].name"), thirdTagName);

        assertEquals(jsonPathEvaluator.get("status"), petStatus);
    }

    @Test
    public void updateAPetToTheStoreWithFormData(){

        Response response =
        given().
                pathParam("petId",id).
                formParam("name", "Tommy").
                formParam("status", "sold").
                config(RestAssuredConfig.config().
        //as we are providing a "header" for "formParam" that's why we
        //need to configure so that we are using ".config"
                encoderConfig(EncoderConfig.encoderConfig().
                encodeContentTypeAs("multipart/form-data", ContentType.TEXT))).
        when().
                post(BASE_URL + "/pet/{petId}").
        then().
                assertThat().
                statusCode(200).
                log().all().extract().response();


        //        {
//            "code": 200,
//                "type": "unknown",
//                "message": "100"
//        }

        //are there 3 keys such as "code", "type", "message",

        ValidatableResponse validatableResponse = response.then();
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
        assertEquals(jsonPathEvaluator.get("message"), Integer.toString(id));
    }
}
