package com.din.ibcsprimax.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.din.ibcsprimax.dto.EmployeeDto;
import com.din.ibcsprimax.entity.BankAccount;
import com.din.ibcsprimax.entity.Employee;
import com.din.ibcsprimax.repository.BackAccountRepo;
import com.din.ibcsprimax.repository.EmployeeRepo;
import com.din.ibcsprimax.serviceImpl.EmployeeServiceImpl;
import com.din.ibcsprimax.util.EmployeeSalary;

@Service
@Transactional
public class EmployeeService implements EmployeeServiceImpl{

	@Autowired
	private EmployeeRepo employeeRepo;
	@Autowired
	private BackAccountRepo backAccountRepo;
	@Autowired
	private EmployeeSalary employeeSalary;

	@Override
	public Employee registerEmployeeAccount(EmployeeDto employeeDto) {
//		Employee employee = new Employee();
//		if(employeeDto != null) {
//			employee = copyFrmEmployeeDto(employeeDto);
//			employeeRepo.save(employee);
//		}
//		return null;
		
		Employee employee = new Employee();
		BankAccount bankAccount = new BankAccount();
		if(employeeDto != null) {
			employee.setName(employeeDto.getName());
			employee.setGrade_rank(employeeDto.getGrade_rank());
			employee.setAddress(employeeDto.getAddress());
			employee.setMobile(employeeDto.getMobile());
			
			bankAccount.setAccountType(employeeDto.getBankAccount().getAccountType());
			bankAccount.setAccountName(employeeDto.getBankAccount().getAccountName());
			bankAccount.setAccountNumber(employeeSalary.getLastAccountNum());
			if(employeeDto.getGrade_rank().equalsIgnoreCase("Six")) {
				double basicSalary = employeeDto.getBankAccount().getCurrentBalance();
				bankAccount.setCurrentBalance(basicSalary);
			}else {
				bankAccount.setCurrentBalance(0.0);
			}
			
			bankAccount.setBank(employeeDto.getBankAccount().getBank());
			bankAccount.setBranchName(employeeDto.getBankAccount().getBranchName());
			bankAccount.setMainAccount(false);
			
			employee.setBankAccount(bankAccount);
			employeeRepo.save(employee);
		} 
		return null;
	}
	
	public List<Employee> getEmployeeListSize(){
		return (List<Employee>) employeeRepo.findAll();
	}
	
	
	//employee report
	@Override
	public Employee employeeReport(EmployeeDto employeeDto) {
		Employee employee = new Employee();
		BankAccount bankAccount = new BankAccount();
		if(employeeDto != null) {
			employee = employeeRepo.findEmployeeByemployeeID(employeeDto.getEmployeeID());
			if(employee != null) {				
				bankAccount = backAccountRepo.findBankAccountByBankAcID(employee.getBankAccount().getBankAcID());
				employee.setEmpSalary(bankAccount.getCurrentBalance());				
			}
			
		}
		return employee;
	}


	public Employee copyFrmEmployeeDto(EmployeeDto employeeDto) { 
		Employee employee = new Employee();
		if(employeeDto != null) { 
			BeanUtils.copyProperties(employee, employeeDto);
		} 
		return employee; 
	}
	
	public EmployeeDto copyFrmEmployee(Employee employee) {
		EmployeeDto employeeDto = new EmployeeDto();
		if(employee.getEmployeeID() != null && employee.getMobile() != null) {
			BeanUtils.copyProperties(employeeDto, employee);
		}
		return employeeDto;		
	}

}
