package com.din.ibcsprimax.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.din.ibcsprimax.entity.BankAccount;

@Repository
public interface BackAccountRepo extends CrudRepository<BankAccount, Long>{
	
	@Query(value="SELECT * FROM ibcs_assignment.bank_account bc WHERE bc.is_main_account = 1", nativeQuery = true)
	BankAccount findBankAccountByIsMainAccount();
	
	BankAccount findBankAccountByAccountNumber(int accountNumber);

	BankAccount findBankAccountByBankAcID(Long bankAcID);

	@Modifying
    @Query("UPDATE BankAccount ba SET ba.currentBalance = :currentBalance WHERE ba.accountNumber = :accountNumber")
	void updateEmployeeAccountSalary(@Param("accountNumber") int accountNumber, @Param("currentBalance") double currentBalance);
	
	@Modifying
    @Query("UPDATE BankAccount ba SET ba.currentBalance = :currentBalance WHERE ba.accountNumber = :accountNumber")
	void updateCompanyAccountSalary(@Param("accountNumber") int accountNumber, @Param("currentBalance") double currentBalance);

	
	@Modifying
    @Query("UPDATE BankAccount ba SET ba.currentBalance = :currentBalance WHERE ba.accountNumber = :accountNumber")
	void increseCompanyAccountSalary(@Param("accountNumber") int accountNumber, @Param("currentBalance") double currentBalance);
	
}
