package com.citrus.mobile;

import org.json.JSONException;
import org.json.JSONObject;

public class SuccessCall {
	
	
	public static JSONObject successMessage(String message, JSONObject object) {
		if (object == null) {
			object = new JSONObject();
		}
		
		try {
			object.put("status", "200");
			object.put("message", message);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return object;
	}
}
