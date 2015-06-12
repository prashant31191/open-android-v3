package com.citrus.mobile;

import org.json.JSONException;
import org.json.JSONObject;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CitrusClient extends WebViewClient{
	
	Callback callback;
	JSONObject responsejson;
	
	public CitrusClient(Callback callback) {
		this.callback = callback;
		responsejson = new JSONObject();
	}
		
	
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		
		if (url.contains("#")) {
			String[] response = url.split("#");
			
			if (response.length == 2 && response[1].contains("SUCC")) {
				try {
					responsejson.put("status", 200);
					responsejson.put("message", response[1]);

				} catch (JSONException e) {
					e.printStackTrace();
				}
				callback.onTaskexecuted(responsejson.toString(), "");
				return false;
			}
			
			else {
				try {
					responsejson.put("status", 600);
					responsejson.put("message", response[1]);

				} catch (JSONException e) {
					e.printStackTrace();
				}
				callback.onTaskexecuted("", responsejson.toString());
				return false;
			}
			
		}
		
		return super.shouldOverrideUrlLoading(view, url);
		
	}
}	
