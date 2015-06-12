package com.citrus.citrususer;

import org.json.JSONObject;

import com.citrus.mobile.User;

import android.app.Activity;

public class LoginUser {
	Activity activity;
	String email, password;
	
	public LoginUser(Activity activity, String email, String password) {
		this.activity = activity;
		this.email = email;
		this.password = password;
	}
	
	public JSONObject login() {
		
		User citrususer = new User(activity);
		
		JSONObject signinresult = citrususer.signinUser(email);
		
		if (signinresult.has("status")) {
			PrepaidOauth oauth = new PrepaidOauth(activity, email, password);
			
			JSONObject result = oauth.create();
			
			if (result.has("status")) {
				result = oauth.getsetCookie();
			}
			
			return result;
		}
		
		return signinresult;
		
	}
	
}
