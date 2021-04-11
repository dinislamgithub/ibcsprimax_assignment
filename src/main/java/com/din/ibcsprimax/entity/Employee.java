package com.din.ibcsprimax.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
//import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

//import lombok.AllArgsConstructor;
import lombok.Getter;
//import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "employee")
public class Employee implements Serializable{
	private static final long serialVersionUID=1L;
	 @Id
	 @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="employee_seq")
	 @SequenceGenerator(name="employee_seq", sequenceName="employee_seq", initialValue=1001,allocationSize=1)
	 @Column(name = "employee_id")
	private Long employeeID;
	 
	 @Column(name = "name")
	private String name;
	 
	 @Column(name = "grade_rank")
	private String grade_rank;
	 
	 @Column(name = "address")
	private String address;
	 
	
	 @Column(name = "mobile", length=11) 
	private String mobile;
	 
	 @Transient
	 private double empSalary;
	 
	 @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	    @JoinColumn(name = "fk_bankAc_ID", referencedColumnName = "bankAc_ID")
	private BankAccount bankAccount;
	
}
