package com.din.ibcsprimax.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.din.ibcsprimax.enums.AccountType;

//import lombok.AllArgsConstructor;
import lombok.Getter;
//import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "bank_account")
public class BankAccount implements Serializable{
	private static final long serialVersionUID=1L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bankAc_ID")
	private Long bankAcID;
	

	 @Column(name = "account_type")
		private String accountType;
	 
	 @Column(name = "account_name")
	private String accountName;
	 
	 @Column(name = "account_number", unique=true, length=10)
	private int accountNumber;
	 
	 @Column(name = "current_balance")
	private double currentBalance;
	 
	 @Column(name = "bank")
	private String bank;
	 
	 @Column(name = "branch_name")
	private String branchName;
	
	 @Column(name = "isMain_account")
	 private boolean isMainAccount;
	 
	 @Transient
	 private String errorMsg;
	 

	 
}
