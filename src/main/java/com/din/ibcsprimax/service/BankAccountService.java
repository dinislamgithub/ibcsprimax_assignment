package com.din.ibcsprimax.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.din.ibcsprimax.dto.BankAccountDto;
import com.din.ibcsprimax.entity.BankAccount;
import com.din.ibcsprimax.entity.Employee;
import com.din.ibcsprimax.serviceImpl.BankAccountServiceImpl;
import com.din.ibcsprimax.util.EmployeeSalary;
import com.din.ibcsprimax.repository.BackAccountRepo;
import com.din.ibcsprimax.repository.EmployeeRepo;

@Service
@Transactional
public class BankAccountService implements BankAccountServiceImpl{

	@Autowired
	private BackAccountRepo bankAccountRepo;
	@Autowired
	private EmployeeSalary employeeSalary;
	@Autowired
	private EmployeeRepo employeeRepo; 
	 
	@Override
	public BankAccount createBankAccount(BankAccountDto bankAccountDto) {
		BankAccount bankAccount = new BankAccount();
		List<BankAccount> existingMainAcc = (List<BankAccount>) bankAccountRepo.findAll();
		BankAccount bacc = new BankAccount();
		for(BankAccount ba : existingMainAcc) {
			if(ba.isMainAccount()) {
				bacc = ba;
				break;
			}
		}
//		if(existingMainAcc.size() == 0 && existingMainAcc.isEmpty()) {
		if(!bacc.isMainAccount()) {
			if(bankAccountDto != null) {
				bankAccount.setAccountName(bankAccountDto.getAccountName());
				bankAccount.setAccountType(bankAccountDto.getAccountType());
				bankAccount.setAccountNumber(employeeSalary.getLastAccountNum());
				bankAccount.setCurrentBalance(bankAccountDto.getCurrentBalance());
				bankAccount.setBank(bankAccountDto.getBank());
				bankAccount.setBranchName(bankAccountDto.getBranchName());
				bankAccount.setMainAccount(true);
				bankAccountRepo.save(bankAccount);
			}
		}else {
			bankAccount.setErrorMsg("Company main account is already created...");
		}
		
		return bankAccount;
	}

	
	@Override
	public BankAccountDto companyBankAccountDetail() {
		BankAccountDto bankAccountDto = new BankAccountDto();
		BankAccount bankAccount = new BankAccount();
		bankAccount = bankAccountRepo.findBankAccountByIsMainAccount();
		if(bankAccount != null) {
			bankAccountDto.setBankAcID(bankAccount.getBankAcID());
			bankAccountDto.setAccountName(bankAccount.getAccountName());
			bankAccountDto.setAccountNumber(bankAccount.getAccountNumber());
			bankAccountDto.setAccountType(bankAccount.getAccountType());
			bankAccountDto.setBank(bankAccount.getBank());
			bankAccountDto.setBranchName(bankAccount.getBranchName());
			bankAccountDto.setCurrentBalance(bankAccount.getCurrentBalance());
		}
		
		return bankAccountDto;
	}


	double empGrossSalay;
	@Override
	public BankAccount employeeSalaryTransaction(BankAccountDto bankAccountDto) {
		BankAccount bankAccount = new BankAccount();
		Employee employee = new Employee();
		
		if(bankAccountDto != null) {			
			final double gradeSixBasicSalary = bankAccountRepo.findBankAccountByBankAcID(1L).getCurrentBalance();
			bankAccount = bankAccountRepo.findBankAccountByAccountNumber(bankAccountDto.getAccountNumber());
			if(bankAccount != null) {
				 employee = employeeRepo.findEmployeeByBankAccount_BankAcID(bankAccount.getBankAcID());
				 empGrossSalay = employeeSalary.calculateEmployeeMonthlySalary(employee.getGrade_rank(), employee.getBankAccount().getBankAcID(), gradeSixBasicSalary);
			}
			
			BankAccount mainBankAccount = bankAccountRepo.findBankAccountByIsMainAccount();
			if(mainBankAccount.getCurrentBalance() >= empGrossSalay) {
				bankAccountRepo.updateEmployeeAccountSalary(bankAccountDto.getAccountNumber(), bankAccount.getCurrentBalance()+empGrossSalay);
				bankAccountRepo.updateCompanyAccountSalary(mainBankAccount.getAccountNumber(), mainBankAccount.getCurrentBalance()-empGrossSalay);
				bankAccount.setErrorMsg("Employee Salary Transaction Successfully..");
			}else {
				bankAccount.setErrorMsg("Main Account Balance is less for employee salary.");
			}
			
		}else {
			bankAccount.setErrorMsg("Employee Salary Account is not valid and exixt.");
		}
		return bankAccount;
	}


	@Override
	public void addCompanyAccountBalance(BankAccountDto bankAccountDto) {
		BankAccount bankAccount = new BankAccount();
		if(bankAccountDto != null) {
			BankAccount bAcc = bankAccountRepo.findBankAccountByIsMainAccount(); 
			double uerInputBalance = bankAccountDto.getCurrentBalance();
			bankAccount = bankAccountRepo.findBankAccountByAccountNumber(bAcc.getAccountNumber());
			if(bankAccount != null) {
				double companyAvailabeBalace = bankAccount.getCurrentBalance();			
				bankAccountRepo.increseCompanyAccountSalary(bankAccount.getAccountNumber(), companyAvailabeBalace+uerInputBalance);
			}
		}	
	}


	double remainingSalary;
	@Override
	public BankAccountDto companySalaryReport(BankAccountDto bankAccountDto) {
		BankAccount account = new BankAccount();
		BankAccountDto accountDto = new BankAccountDto();
		List<BankAccount> accountList= new ArrayList<>(); 
		List<Double> tempAccountList= new ArrayList<>(); 
		if(bankAccountDto != null) {
			account = bankAccountRepo.findBankAccountByIsMainAccount();
			
			if(account.getAccountNumber() != bankAccountDto.getAccountNumber()) {
				accountDto.setErrorMsg("This Searching Account Number is not Company Salay Account Number.");
			}else {
				accountList = (List<BankAccount>) bankAccountRepo.findAll();
				for(BankAccount bac : accountList) {
//					if(bac.isMainAccount() == true) {
					if(bac.isMainAccount() == true) {
						//accountDto.setErrorMsg("This Searching Account Number is not Company Salay Account Number.");
						//break;
					}else { 
						tempAccountList.add(bac.getCurrentBalance());
					}					
					Double sum = tempAccountList.stream().reduce((double) 0, Double::sum);
					
					accountDto.setAccountNumber(account.getAccountNumber());
					accountDto.setTotalBalance(sum);
					accountDto.setRemainingBalance(account.getCurrentBalance());
					//break;
				}
				
			}
			
			
			
		}
		return accountDto;
	}

	

	
	
	
}
