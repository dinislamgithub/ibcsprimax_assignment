package com.din.ibcsprimax.enums;

public enum AccountType {
//	SAVINGS,CURRENT;
	
	SAVINGS("Savings"),
	CURRENT("Current");
	public final String enumValue;

 
	private AccountType(String enumValue) {
		this.enumValue = enumValue;
	}


	public String getEnumValue() {
		return enumValue;
	}
	
	
}
