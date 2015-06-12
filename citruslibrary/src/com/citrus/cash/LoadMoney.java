package com.citrus.cash;

public class LoadMoney {
	String amount = "", return_url="";
	
	public LoadMoney(String amount, String return_url) {
		this.amount = amount;
		this.return_url = return_url;
	}
	
	public String getAmount() {
		return this.amount;
	}
	
	public String getReturl() {
		return this.return_url;
	}
	
}
