package com.citrus.netbank;

public enum BankPaymentType {

	CID("CID"),
	
	TOKEN("paymentOptionIdToken");
	
	private final String name;
	
	private BankPaymentType(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}

}
