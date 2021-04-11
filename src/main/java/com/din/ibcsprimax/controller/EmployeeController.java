package com.din.ibcsprimax.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.din.ibcsprimax.dto.BankAccountDto;
import com.din.ibcsprimax.dto.EmployeeDto;
import com.din.ibcsprimax.entity.BankAccount;
import com.din.ibcsprimax.entity.Employee;
import com.din.ibcsprimax.enums.AccountType;
import com.din.ibcsprimax.service.BankAccountService;
import com.din.ibcsprimax.service.EmployeeService;

//@RestController
@Controller
public class EmployeeController {	 
	
	 @Autowired
	private EmployeeService employeeService;
	 @Autowired
	 private BankAccountService bankAccountService;
	 
	 List<String> gradOrRankList = new ArrayList<>(); //
	 List<Employee> empList = new ArrayList<>();//
	 List<String> accountTypesList = new ArrayList<>();//
	 boolean basicSalryBlock = false; //
	 
	 
	@GetMapping("/")
    public String showSignUpForm(EmployeeDto employeeDto,  Model model, BankAccountDto bankAccountDto) { 
		accountTypesList = List.of(AccountType.SAVINGS.enumValue.toString(), AccountType.CURRENT.enumValue.toString());
		empList = employeeService.getEmployeeListSize();
		if(empList.size() == 0 || empList.isEmpty()) {
			gradOrRankList = List.of("Six");
			//basicSalryBlock = true;
			model.addAttribute("basicSalaryFieldVisible", basicSalryBlock==true);
		}else {
			gradOrRankList = List.of("One", "Two", "Three", "Four", "Five", "Six"); 
			//basicSalryBlock = false;
			model.addAttribute("basicSalaryFieldVisible", basicSalryBlock==false);
		}		
		model.addAttribute("accountTypesList", accountTypesList);
		model.addAttribute("gradeOrRanks", gradOrRankList); 
		
		model.addAttribute("companyBankDetail", bankAccountService.companyBankAccountDetail()); 
		//model.addAttribute("basicSalryBlock", basicSalryBlock);
		//model.addAttribute("basicSalaryFieldVisible", "");//
        return "index";
    }
	
	
	
	 @RequestMapping(value = "/registerEmpoyeeAccount", method = RequestMethod.GET) 
	 public String home(EmployeeDto employeeDto, Model model, BankAccountDto bankAccountDto) {
		 model.addAttribute("employeeDto", new EmployeeDto());  
		 model.addAttribute("companyBankDetail", bankAccountService.companyBankAccountDetail()); 	
		 
		 model.addAttribute("accountTypesList", accountTypesList);//
		 model.addAttribute("gradeOrRanks", gradOrRankList); //
//	     return "index";
		 return "employeeAccount";
	 }  
    @RequestMapping(value = "/registerEmpoyeeAccount", method = RequestMethod.POST) 
    	 public String registerEmpoyeeAccount(@Validated EmployeeDto employeeDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "index";
        }
    	employeeService.registerEmployeeAccount(employeeDto);
        model.addAttribute("employeeDto", new EmployeeDto());  
        model.addAttribute("companyBankDetail", bankAccountService.companyBankAccountDetail()); 
        model.addAttribute("msg", "Employee Bank Account Save Successfully."); 
        
        model.addAttribute("accountTypesList", accountTypesList);//
		model.addAttribute("gradeOrRanks", gradOrRankList); //
//        return "index";
        return "employeeAccount";
    }
	
	

    
    
    
    @RequestMapping(value = "/createCompanyAccount", method = RequestMethod.GET) 
	 public String compAccount(BankAccountDto bankAccountDto, Model model, EmployeeDto employeeDto) {
		 model.addAttribute("bankAccountDto", new BankAccountDto()); 
		 model.addAttribute("companyBankDetail", bankAccountService.companyBankAccountDetail()); 
		 
		 model.addAttribute("accountTypesList", accountTypesList);//
//	     return "index";
		 return "companyAccount";
	 }
  @RequestMapping(value = "/createCompanyAccount", method = RequestMethod.POST) 
  	 public String createCompanyAccount(@Validated BankAccountDto bankAccountDto, BindingResult result, Model model) {
      if (result.hasErrors()) {
          return "index";
      }
      //bankAccountService.createBankAccount(bankAccountDto);
      BankAccount bac = bankAccountService.createBankAccount(bankAccountDto);
      if(bac.getErrorMsg() != null) {
    	  model.addAttribute("duplicateBankMsg", bac.getErrorMsg()); 
      }else {
    	  model.addAttribute("bankAccountDto", new BankAccountDto());
          model.addAttribute("companyBankDetail", bankAccountService.companyBankAccountDetail());
          model.addAttribute("msg", "Company Bank Account Save Successfully."); 
          
          model.addAttribute("accountTypesList", accountTypesList);//
      }
      
//      return "index";
      return "companyAccount";
  }
	
	
  
  
  
	
	
  @RequestMapping(value = "/empSalaryTransaction", method = RequestMethod.GET) 
	 public String empSalaryTransactions(EmployeeDto employeeDto, Model model, BankAccountDto bankAccountDto) {
		 model.addAttribute("bankAccountDto", new BankAccountDto());  
		 model.addAttribute("companyBankDetail", bankAccountService.companyBankAccountDetail());// 
//	     return "index";
	     return "salaryTransaction";
	 }
  @RequestMapping(value = "/empSalaryTransaction", method = RequestMethod.POST) 
	 public String empSalaryTransaction(@Validated BankAccountDto bankAccountDto, BindingResult result, Model model, EmployeeDto employeeDto) {
   if (result.hasErrors()) {
       return "index";
   }
   BankAccount bankAccount = bankAccountService.employeeSalaryTransaction(bankAccountDto);
   if(bankAccount.getErrorMsg() != null) {
	   model.addAttribute("msg", bankAccount.getErrorMsg()); 
   }
   model.addAttribute("bankAccountDto", new BankAccountDto());
   model.addAttribute("companyBankDetail", bankAccountService.companyBankAccountDetail());
   
//   model.addAttribute("msg", "Employee Salary Transaction Successfully."); 
//   return "index";
   return "salaryTransaction";
}
	
	
	
	
    

  @RequestMapping(value = "/addCompanyAccountBalance", method = RequestMethod.GET, produces = "text/html") 
	 public String addCompanyAccountBalance(Model model, BankAccountDto bankAccountDto, EmployeeDto employeeDto) {
	   model.addAttribute("bankAccountDto", new BankAccountDto());  
		model.addAttribute("companyBankDetail", bankAccountService.companyBankAccountDetail()); 
//		return "index";
		return "addCompanyBalance";
	 }
  @RequestMapping(value = "/addCompanyAccountBalance", method = RequestMethod.POST) 
  public String empSalaryTransaction(BankAccountDto bankAccountDto,  Model model,  EmployeeDto employeeDto) {
	bankAccountService.addCompanyAccountBalance(bankAccountDto);
	model.addAttribute("bankAccountDto", new BankAccountDto());
	 model.addAttribute("companyBankDetail", bankAccountService.companyBankAccountDetail()); 
	model.addAttribute("msg", "Company Bank Account Balance Increased Successfully."); 
//	return "index";
	return "addCompanyBalance";
  }
	    
    
    
    
  //report section emp
  @RequestMapping(value = "/employeeReport", method = RequestMethod.GET) 
	 public String employeeReport(EmployeeDto employeeDto, Model model, BankAccountDto bankAccountDto) {
		 model.addAttribute("bankAccountDto", new BankAccountDto());  
		 model.addAttribute("employeeDto", new EmployeeDto()); 
		 model.addAttribute("companyBankDetail", bankAccountService.companyBankAccountDetail());
	     return "employeeReport";
//	     return "index";
	 }
  @RequestMapping(value = "/employeeReport", method = RequestMethod.POST) 
  public String employeeReport(BankAccountDto bankAccountDto,  Model model,  EmployeeDto employeeDto) {
	  Employee employee = new Employee();
	  employee = employeeService.employeeReport(employeeDto);
     model.addAttribute("employeeDto", employee);
	model.addAttribute("bankAccountDto", new BankAccountDto());
	 model.addAttribute("companyBankDetail", bankAccountService.companyBankAccountDetail());
	 model.addAttribute("msg", " "); //
	 return "employeeReport";
//	return "index";
  }
  
  
  
	
//companyReport 
  @RequestMapping(value = "/companyReport", method = RequestMethod.GET) 
	 public String companyReport(EmployeeDto employeeDto, Model model, BankAccountDto bankAccountDto) {
		 model.addAttribute("bankAccountDto", new BankAccountDto());  
		 model.addAttribute("employeeDto", new EmployeeDto()); 
		 model.addAttribute("companyBankDetail", bankAccountService.companyBankAccountDetail());
//	     return "index";
		 return "companyReport";
	 }
@RequestMapping(value = "/companyReport", method = RequestMethod.POST) 
public String companyReport(BankAccountDto bankAccountDto,  Model model,  EmployeeDto employeeDto) {
	BankAccountDto accountDto = bankAccountService.companySalaryReport(bankAccountDto);
	if(accountDto.getErrorMsg() != null) {
		model.addAttribute("dupCompMainAccMsg", accountDto.getErrorMsg());
	}else {
		model.addAttribute("bankAccountDto", new BankAccountDto());
		model.addAttribute("bankAccountDto", bankAccountService.companySalaryReport(bankAccountDto));
		 model.addAttribute("companyBankDetail", bankAccountService.companyBankAccountDetail());
		 model.addAttribute("msg", " "); //
	}
	
//	return "index";
	 return "companyReport";
}
  
}
