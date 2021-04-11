package com.din.ibcsprimax.util;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.din.ibcsprimax.entity.BankAccount;
import com.din.ibcsprimax.repository.BackAccountRepo;
import com.din.ibcsprimax.repository.EmployeeRepo;

@Component
public class EmployeeSalary {
	@Autowired
	private BackAccountRepo backAccountRepo;
	
	public int getLastAccountNum() {
		int accountNum;
		List<BankAccount> accountList = (List<BankAccount>) backAccountRepo.findAll();
		if(accountList.isEmpty() || accountList.size()==0) {
			accountNum = 1000000001;
		}else {
			Optional<BankAccount> account = accountList.stream().reduce((first, second) -> second);
			accountNum = account.get().getAccountNumber()+1;
		}		
		return accountNum;
	}
	
	double grossSalary;
	public double calculateEmployeeMonthlySalary(String gradeRank, Long fkBankAccID, double basicSalary) {
		
		if(gradeRank.equalsIgnoreCase("Six")) {
			double empBasicSalary = basicSalary;
			grossSalary = empBasicSalary + houseRent(empBasicSalary) + medicalAllowance(empBasicSalary);
		}else if(gradeRank.equalsIgnoreCase("Five")) {
			double empBasicSalary = basicSalary+5000*1;
			grossSalary = empBasicSalary + houseRent(empBasicSalary) + medicalAllowance(empBasicSalary);
		}else if(gradeRank.equalsIgnoreCase("Four")) {
			double empBasicSalary = basicSalary+5000*2;
			grossSalary = empBasicSalary + houseRent(empBasicSalary) + medicalAllowance(empBasicSalary);
		}else if(gradeRank.equalsIgnoreCase("Three")) {
			double empBasicSalary = basicSalary+5000*3;
			grossSalary = empBasicSalary + houseRent(empBasicSalary) + medicalAllowance(empBasicSalary);
		}else if(gradeRank.equalsIgnoreCase("Two")) {
			double empBasicSalary = basicSalary+5000*4;
			grossSalary = empBasicSalary + houseRent(empBasicSalary) + medicalAllowance(empBasicSalary);
		}else if(gradeRank.equalsIgnoreCase("One")) {
			double empBasicSalary = basicSalary+5000*5;
			grossSalary = empBasicSalary + houseRent(empBasicSalary) + medicalAllowance(empBasicSalary);
		}
		return grossSalary;
	}
	
	public double houseRent(double basicSalary) {		
		return basicSalary*20/100;
	}
	public double medicalAllowance(double basicSalary) {
		return basicSalary*15/100;
	}
}
