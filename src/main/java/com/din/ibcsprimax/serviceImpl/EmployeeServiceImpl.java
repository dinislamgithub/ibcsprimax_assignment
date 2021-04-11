package com.din.ibcsprimax.serviceImpl;

import com.din.ibcsprimax.dto.EmployeeDto;
import com.din.ibcsprimax.entity.Employee;

public interface EmployeeServiceImpl {
	public Employee registerEmployeeAccount(EmployeeDto employeeDto);
	public Employee employeeReport(EmployeeDto employeeDto);
}
