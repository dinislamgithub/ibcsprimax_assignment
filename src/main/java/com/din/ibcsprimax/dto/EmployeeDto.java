package com.din.ibcsprimax.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
	private Long employeeID;
	private String name;
	private String grade_rank;
	private String address;
	private String mobile;
	private BankAccountDto bankAccount;
	 private double empSalary;
		
}
