package com.swathingblock.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonPayload {

	private HashMap<String, ElementDetails> map = new HashMap<String, ElementDetails>();
	private HashMap<String, JsonPayload> embeddedJsonObjects = new HashMap<String, JsonPayload>();
	private HashMap<String, String> embeddedPrimativeJsonArrays = new HashMap<String, String>();
	public JsonPayload(JsonObject json_obj) {
		parseJson(json_obj);
	}
	
	private void parseJson(JsonObject json_obj) {
		Set<String> keySet = json_obj.keySet();
		
		for (String key : keySet) {
			if(json_obj.get(key).isJsonPrimitive()) {
				if(json_obj.get(key).getAsJsonPrimitive().isString()) {
					map.put(key, ValueTypeEnum.STRING);
				}
				else if(json_obj.get(key).getAsJsonPrimitive().isBoolean()) {
					map.put(key, ValueTypeEnum.BOOLEAN);
				}
				else if(json_obj.get(key).getAsJsonPrimitive().isNumber()) {
					map.put(key, ValueTypeEnum.NUMBER);
				}
			}
			else if(json_obj.get(key).isJsonObject()) {
				JsonPayload jsonPayload = new JsonPayload(json_obj.get(key).getAsJsonObject());
				map.put(key, ValueTypeEnum.JSONOBJECT);
				embeddedJsonObjects.put(key, jsonPayload);
			}
			else if(json_obj.get(key).isJsonArray()) {
				map.put(key, ValueTypeEnum.JSONARRAY);
				JsonArray json_array = json_obj.get(key).getAsJsonArray();
				ArrayList<JsonElement> primatives = new ArrayList<JsonElement>();
				for (JsonElement el : json_array) {
					if(el.isJsonPrimitive()) {
						primatives.add(el);
					}
				}
				
				if(primatives.size() != json_array.size()) {System.out.println("array contains primatives mixed with non primatives"); continue;}
				embeddedPrimativeJsonArrays.put(key, Arrays.toString(primatives.toArray()));
				
//				if(json_array.get(0).isJsonObject()) {
//					
//				}
//				else {
//					
//				}
//				if(json_obj.get(key).getAsJsonArray().size() > 0) {
//					
//					
//					
//					JsonPayload jsonPayload = new JsonPayload(json_obj.get(key).getAsJsonArray().get(0).getAsJsonObject());
//					embeddedJsonObjects.put(key, jsonPayload);
//				}
				
			}
		}
	}
	
	public String convertJsonToAdocTable() {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("[discrete]\n");
		builder.append("[cols=\"1,8\", options=\"header\"]\n");
		builder.append("|===\n");
		builder.append("|Field |Description\n");
		
		for(Map.Entry<String,Integer> entry : map.entrySet()) {
			String key = entry.getKey();
			int value = entry.getValue();
			
			if(value == ValueTypeEnum.STRING) {
					builder.append("|`" + key + "`\n");
					builder.append("|`String`, DESCRIPTION\n");
			}
			else if(value == ValueTypeEnum.BOOLEAN) {
				builder.append("|`" + key + "`\n");
				builder.append("|`Boolean`, DESCRIPTION\n");
			}
			else if(value == ValueTypeEnum.NUMBER) {
				builder.append("|`" + key + "`\n");
				builder.append("|`Number`, DESCRIPTION\n");
			}
			else if(value == ValueTypeEnum.JSONOBJECT) {
				builder.append("|`" + key + "`\n");
				builder.append("|`Json Object`, DESCRIPTION\n");
			}
			else if(value == ValueTypeEnum.JSONARRAY) {
				builder.append("|`" + key + "`\n");
				builder.append("|`Json Array`, DESCRIPTION\n");
			}
		}
		
		builder.append("|===");
		builder.append("\n\n");
		
		for(Map.Entry<String,JsonPayload> entry : embeddedJsonObjects.entrySet()) {
			builder.append(entry.getValue().convertJsonToAdocTable());
		}
		
		return builder.toString();
	}
	
}
