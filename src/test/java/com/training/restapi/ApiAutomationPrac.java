package com.training.restapi;

import java.util.HashMap;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiAutomationPrac {

	String sHostURI = "https://us-central1-qa01-tekarch-accmanager.cloudfunctions.net";
	String sURI;
	String sEpLogin = "/login";
	String sEpGetData = "/getdata";
	String sEpLogout = "/logout";
	String token;

	@Test
	public void loginUser() {
		sURI = sHostURI + sEpLogin;
		RestAssured.baseURI = sURI;
		Response response = RestAssured.given()
				.body("{\"username\": \"teenaraghav3017@tekarch.com\", \"password\": \"Admin123\"}\r\n" + "").when()
				.contentType("application/Json").post();
		System.out.println(response.getStatusCode());
		token = response.jsonPath().get("token[0]");
//		System.out.println(token);
	}

	@Test
	public void getData() {
		sURI = sHostURI + sEpGetData;
	RestAssured.baseURI=sURI;
	HashMap<String,String> headerData = new HashMap<>();
	headerData.put("token", token);
	headerData.put("Content-Type", "application/json");
	Response response = RestAssured.given().headers(headerData).get();
	System.out.println(response.getStatusCode());
//	response.prettyPrint();
	
	
	}
	
	@Test
	public void logout() {
		sURI = sHostURI + sEpLogout;
		RestAssured.baseURI= sURI;
		HashMap<String,String> headerData = new HashMap<>();
		headerData.put("token", token);
		headerData.put("Content-Type", "application/json");
		Response response = RestAssured.given().body("{\"username\": \"teenaraghav3017@tekarch.com\", \"password\": \"Admin123\"}\r\n"
				+ "").when().headers(headerData).post();
		System.out.println(response.getStatusCode());
	}
}
