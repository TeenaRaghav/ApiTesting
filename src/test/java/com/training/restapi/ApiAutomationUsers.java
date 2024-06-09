package com.training.restapi;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

public class ApiAutomationUsers {

	/* - Status Code 
	 * - Response body - Schema - structure and key
	 * - Data in the body
	 * - Response time is optional .
	 */

	/* String 
	 *   equalTo
	 *   startsWith
	 *   equalToIgnoreCase
	 *   EndsWith
	 *   containsString
	 *   equaltoIgnoreCaseWhitespace
	 *   
	 *   
	 *   Number:
	 *   equalTo
	 *   greaterthan
	 *   lessThan
	 *   greaterthanorEqualTo
	 *   lessthenorEqualTo
	 *   
	 *   collections:
	 *   hasItem
	 *   hasItems
	 *   hasKey
	 *   hadEntry
*/
	String sHostUrl ="https://jsonplaceholder.typicode.com/";
	String sEndpoint = "users";
	String sURI;
	
	@Test
	public void validateUser() throws IOException {
		sURI =sHostUrl + sEndpoint;
		RestAssured.baseURI = sURI; // copy paste URI
		Response response =RestAssured.get();
		System.out.println(response.getStatusCode());
//		response.prettyPrint();
//	get the expected value from json file
		String path ="C:\\Users\\teena\\Api\\ExpectedResponse.json";
		String fileReadyToRead = readFileReturnString(path);
		String Expectedemail= JsonPath.read(fileReadyToRead,"$.[0].email");
		System.out.println(Expectedemail);
		String ExpectedCity= JsonPath.read(fileReadyToRead,"$.[0].address.city");

//		equalTo - Hamcrestmatcher
		response.then().body("email[0]", equalTo(Expectedemail));	
		response.then().body("address.city",hasItem(ExpectedCity));
				
//	String Expectedemail = "Sincere@april.biz";
////	get value
//	String Actualemail = response.jsonPath().get("email[0]");
//	System.out.println(Actualemail);
//	response.then().body("email[0]",equalTo(Expectedemail));
//	response.then().body("email[0]",startsWith("S"));
//	response.then().body("email[0]",endsWith("z"));
//	response.then().body("email[0]",containsString("b"));
//	response.then().body("address.zipcode[0]",equalTo("92998-3874"));
//	response.then().body("address.city",hasItem("Howemouth1"));
//	Assert.assertEquals(Actualemail, Expectedemail);
//	List<String>cities =response.jsonPath().getList("address.city");
//	for(String city : cities) {
//		System.out.println(city);
//	}

		String actualResponse = response.prettyPrint();
		actualResponse = actualResponse.replace("[", "").replace("]","");
		validateSchema(actualResponse,"C:\\Users\\teena\\Api\\ExpectedSchema.json");
		
		
	}

	private void validateSchema(String actualResponse, String fileName) throws IOException {
//		convert response string to json Object
		JSONObject jsonActual = new JSONObject(new JSONTokener(actualResponse));
//		Deserialization - converting string to jsonObject
		
//		convert the expected schema to a jsonObject
//		read the content of the json file given in the path
	String jsonContent = new String(Files.readAllBytes(Paths.get(fileName)));
	
	JSONObject jsonExpected = new JSONObject(jsonContent);
	
	Schema schema = SchemaLoader.load(jsonExpected);
	schema.validate(jsonActual);
//	convert the jsonObject to String -Serialization
	
	}

	private String readFileReturnString(String path) throws IOException {
		byte[] encoded =Files.readAllBytes(Paths.get(path));
		return new String(encoded,StandardCharsets.UTF_8);
	}
}
