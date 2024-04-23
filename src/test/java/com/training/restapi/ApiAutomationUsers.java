package com.training.restapi;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

public class ApiAutomationUsers {

	String sHostUrl ="https://jsonplaceholder.typicode.com/";
	String sEndpoint = "users";
	String sURI;
	
	@Test
	public void validateUser() {
		sURI =sHostUrl + sEndpoint;
		RestAssured.baseURI = sURI; // copy paste URI
		Response response =RestAssured.get();
		System.out.println(response.getStatusCode());
//		response.prettyPrint();
	String Expectedemail = "Sincere@april.biz";
//	get value
	String Actualemail = response.jsonPath().get("email[0]");
	System.out.println(Actualemail);
	response.then().body("email[0]",equalTo(Expectedemail));
	response.then().body("email[0]",startsWith("S"));
	response.then().body("email[0]",endsWith("z"));
	response.then().body("email[0]",containsString("b"));
	response.then().body("address.zipcode[0]",equalTo("92998-3874"));
	response.then().body("address.city",hasItem("Howemouth1"));



//	Assert.assertEquals(Actualemail, Expectedemail);
//	List<String>cities =response.jsonPath().getList("address.city");
//	for(String city : cities) {
//		System.out.println(city);
//	}
	}
}
