package com.din.ibcsprimax.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.din.ibcsprimax.entity.BankAccount;
import com.din.ibcsprimax.entity.Employee;

@Repository
public interface EmployeeRepo extends CrudRepository<Employee, Long>{ 
	Employee findEmployeeByBankAccount_BankAcID(Long fkBankAcID);
	Employee findEmployeeByemployeeID(Long employeeID);
}
