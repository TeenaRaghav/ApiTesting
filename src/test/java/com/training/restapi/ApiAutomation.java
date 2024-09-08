package com.training.restapi;

import java.util.HashMap;
import java.util.List;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiAutomation {

	String sHostURL = "https://us-central1-qa01-tekarch-accmanager.cloudfunctions.net";
	String sEpLogin = "/login";
	String sEpgetData = "/getdata";
	String sURI;
	Response response;
	String token;

	@Test
	public void loginUser() {
//		create the URL
		sURI = sHostURL + sEpLogin;
//		assign this to Restassured
		RestAssured.baseURI = sURI;

		response = RestAssured.given().body("{\r\n"
				+ "\"username\": \"teenaraghav3017@tekarch.com\",\r\n"
				+ " \"password\": \"Admin123\"\r\n"
				+ " }").when().contentType("application/json").post();
		System.out.println(response.getStatusCode());
//		to see response
//		response.prettyPrint();

//		how did we got particular value
		token = response.jsonPath().get("token[0]");
		System.out.println(token);
	}

	@Test
	public void getData() {
		sURI = sHostURL + sEpgetData;
		RestAssured.baseURI = sURI;
		HashMap<String, String> headerData = new HashMap<String,String>();
		headerData.put("token", token);
		headerData.put("Content-Type", "application/json");
		response = RestAssured.given().headers(headerData).get();
//		response.prettyPrint();
		List<Object> accountnos = response.jsonPath().get("accountno");
		for (Object accountno : accountnos) {
			System.out.println(accountno);
		}
	}
}
