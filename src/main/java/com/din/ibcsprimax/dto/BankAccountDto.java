package com.din.ibcsprimax.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountDto {
	private Long bankAcID;
	private String accountType;
	private String accountName;
	private int accountNumber;
	private double currentBalance;
	private String bank;
	private String branchName;
	
	private String errorMsg;
	private double totalBalance;
	private double remainingBalance;
}
