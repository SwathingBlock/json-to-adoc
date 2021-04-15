package com.swathingblock.main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Controller {

	public static void main(String[] args) {
		
//		System.out.println("Paste the json payload to be converted");
		
//		Scanner in = new Scanner(System.in);
		
//		String json_source = in.next();
		
		String json_source = "{\r\n" + 
				"  \"command\":\"get_devices_by_gateway_response\",\r\n" + 
				"  \"devices\":{\r\n" + 
				"    \"14482\":{\r\n" + 
				"      \"displayName\":\"Dimmer(Lamp Holder) (PAD02)\",\r\n" + 
				"      \"imagePath\":\"/module/tyrrellZWave/web/img/dimmer.png\",\r\n" + 
				"      \"actions\":{\r\n" + 
				"        \"0\":{\r\n" + 
				"          \"facets\":\"precision=0,unit-%,min=0,max=100\",\r\n" + 
				"          \"funcType\":24,\r\n" + 
				"          \"icon\":\"fas fa-sun\",\r\n" + 
				"          \"operators\":[\r\n" + 
				"            \">\",\r\n" + 
				"            \"<\",\r\n" + 
				"            \"=\",\r\n" + 
				"            \"<=\",\r\n" + 
				"            \">=\",\r\n" + 
				"            \"!=\"\r\n" + 
				"          ],\r\n" + 
				"          \"type\":\"Number\"\r\n" + 
				"        }\r\n" + 
				"      },\r\n" + 
				"      \"conditions\":{\r\n" + 
				"        \r\n" + 
				"      }\r\n" + 
				"    }\r\n" + 
				"  }\r\n" + 
				"}";
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser parser = new JsonParser();
		JsonObject obj = parser.parse(json_source).getAsJsonObject();
		JsonPayload jsonPayload = new JsonPayload(obj, 3, "Response");
		String convertedJson = jsonPayload.convertJsonToAdocTable();
		
		
		System.out.println("[source, json]\r\n" +"----\r\n");
		System.out.println(obj.toString());
		System.out.println("\r\n----\r\n");
		System.out.println(convertedJson);
	}
}
