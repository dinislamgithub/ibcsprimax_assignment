package com.din.ibcsprimax.serviceImpl;

import com.din.ibcsprimax.dto.BankAccountDto;
import com.din.ibcsprimax.entity.BankAccount;

public interface BankAccountServiceImpl {
	public BankAccount createBankAccount(BankAccountDto bankAccountDto);
	public BankAccountDto companyBankAccountDetail();
	public BankAccount employeeSalaryTransaction(BankAccountDto bankAccountDto);
	public void addCompanyAccountBalance(BankAccountDto bankAccountDto);
	public BankAccountDto companySalaryReport(BankAccountDto bankAccountDto);
	
}
