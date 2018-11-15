package com.capg.paymentwallet.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.capg.paymentwallet.bean.AccountBean;
import com.capg.paymentwallet.bean.CustomerBean;
import com.capg.paymentwallet.bean.WalletTransaction;
import com.capg.paymentwallet.exception.CustomerException;
import com.capg.paymentwallet.service.AccountServiceImpl;
import com.capg.paymentwallet.service.IAccountService;

public class Client {
	   
	Scanner scanner=new Scanner(System.in);
	static IAccountService service=new AccountServiceImpl();
	CustomerBean customer=new CustomerBean();
	
	 AccountBean accountBean=new AccountBean();
	

	
	
	public static void main(String[] args) throws Exception {
		char ch;
		
		List<String> operations =service.getOperations();
		
		Client client=new  Client();
		do
		{
		System.out.println("========Payment wallet application========");
		int i=1;
		for (String operation : operations) {
			
			System.out.print(i+": ");
			System.out.println(operation);
			i=i+1;
			
		}
		int option =client. scanner.nextInt();
		
		switch (option) {
		case 1:client.create();
               break;
		case 2:client.showbalance();

			break;

		case 3:client.deposit();

			break;
			
			
		case 4:client.withdraw();

			break;
			
	
		case 5:client.fundtransfer();

			break;
			
		
		case 6:client.printTransaction();

			break;
		case 7:System.exit(0);

			break;
			
			
		default:System.out.println("invalid option, please choose from the above list");
			break;
		}
		
		System.out.println("Do you want to continue press Y/N");
		ch=client.scanner.next().charAt(0);
		
		}while(ch=='y' || ch=='Y');

		
	}
	
	
	void create() throws Exception
	{
		
		System.out.print("Enter Customer firstname :\t");
		String fname=scanner.next();
		
		System.out.print("Enter Customer lastname :\t");
		String lname=scanner.next();
		System.out.println("Enter Gender");
		 String gender=scanner.next();
		System.out.print("Enter  Customer  email id :\t");
		String email=scanner.next();
		
		System.out.print("Enter  Customer  phone number :\t");
		String phone=scanner.next();
		
		System.out.print("Enter  Customer PAN number :\t");
		String pan=scanner.next();
		
		System.out.print("Enter  Customer  address :\t");
		String address=scanner.next();
		
		
		CustomerBean customerBean=new CustomerBean();
		customerBean.setAddress(address);
		customerBean.setEmailId(email);
		customerBean.setPanNum(pan);
		customerBean.setPhoneNo(phone);
		customerBean.setFirstName(fname);
		customerBean.setLastName(lname);
		customerBean.setGender(gender);
		
		Random rand = new Random();
	int	accId = rand.nextInt(90000000) + 1000000000;
		
		
	LocalDateTime ldt = LocalDateTime.now();
	DateTimeFormatter f = DateTimeFormatter.ofPattern("dd MMMM yyyy hh:mm a");
	String accDateInput = ldt.format(f);
		
		
		
		
		System.out.println("Enter balance to create account");
		double balance=scanner.nextDouble();
		
		
		accountBean.setAccountId(accId);
		accountBean.setBalance(balance);
		accountBean.setInitialDeposit(balance);
		accountBean.setCustomerBean(customerBean);
		accountBean.setDateOfOpening(accDateInput);
		
		

		
		boolean result=service.createAccount(accountBean);
		System.out.println(result);
		if(result)
		{
			System.out.println("\n\n\t\tCongratulations Customer account has been created successfully...\n\n\t\t");
			System.out.println("Your Accound ID is :");
			System.out.println(accountBean.getAccountId());
			System.out.println("Account created at "+accDateInput);
			
	
		}
		else
		{
			System.err.println("\n\n\t\tEnter valid details..\n\n\t\t");
		}
	}
	
	
	void showbalance() throws CustomerException, Exception 
	{
		String g=accountBean.getCustomerBean().getGender();
		String gender=service.gender(g);
		
		System.out.println("Enter Account ID");
		int accId=scanner.nextInt();
		
		AccountBean accountBean=service.findAccount(accId);
		
		if(accountBean==null){
			System.out.println("Account Does not exist");
			return ;
		}
		
		double balance=accountBean.getBalance();
				
		System.out.println(gender+":"+accountBean.getCustomerBean().getFirstName());
		System.out.println("Your balance is: " +balance);
		
			
		
	}
	
	void deposit() throws Exception
	{
		System.out.println("Enter Account ID");
		int accId=scanner.nextInt();
		
		AccountBean accountBean=service.findAccount(accId);
		
		System.out.println("Enter amount that you want to deposit");
		double depositAmt=scanner.nextDouble();
		
		WalletTransaction wt=new WalletTransaction();
		wt.setTransactionType(1);
		wt.setTransactionDate(new Date());
		wt.setTransactionAmt(depositAmt);
		wt.setBeneficiaryAccountBean(null);
		
		accountBean.addTransation(wt);
		
		
		
		
		
		if(accountBean==null)
		{
			System.out.println("Account Does not exist");
			return ;
		}
		
		
		boolean result=service.deposit(accountBean, depositAmt);
		
		
		if(result){
			System.out.println("Deposited Money into Account ");
		}else{
			System.out.println("NOT Deposited Money into Account ");
		}
			
	}
	
	void withdraw() throws Exception
	{
		System.out.println("Enter Account ID");
		int accId=scanner.nextInt();
		
		AccountBean accountBean=service.findAccount(accId);
		
		System.out.println("Enter amount that you want to withdraw");
		double withdrawAmt=scanner.nextDouble();
		
		
		
		WalletTransaction wt=new WalletTransaction();
		wt.setTransactionType(2);
		wt.setTransactionDate(new Date());
		wt.setTransactionAmt(withdrawAmt);
		wt.setBeneficiaryAccountBean(null);
		
		accountBean.addTransation(wt);
		
		
		if(accountBean==null){
			System.out.println("Account Does not exist");
			return ;
		}
		
		
		boolean result=service.withdraw(accountBean, withdrawAmt);
		if(result){
			System.out.println("Withdaw Money from Account done");
		}else{
			System.out.println("Withdaw Money from Account -Failed ");
		}
		
	}
	
	void fundtransfer() throws Exception
	{
		System.out.println("Enter Account ID to Transfer Money From");
		int srcAccId=scanner.nextInt();
		String g=accountBean.getCustomerBean().getGender();
		String gender=service.gender(g);
		
		//IAccountService service1 =new AccountServiceImpl();
		AccountBean accountBean1=service.findAccount(srcAccId);
		
				System.out.println( gender+"." +"Name: "+accountBean.getCustomerBean().getFirstName());
		System.out.println("Current Balance is: "+accountBean.getBalance());
		
		System.out.println("Enter Account ID to Transfer Money to");
		int targetAccId=scanner.nextInt();
	
		
		AccountBean accountBean2=service.findAccount(targetAccId);
		System.out.println("Name: "+accountBean.getCustomerBean().getFirstName());
		System.out.println("Current Balance is: "+accountBean.getBalance());
		
		System.out.println("Enter amount that you want to transfer");
		double transferAmt=scanner.nextDouble();
		
		WalletTransaction wt=new WalletTransaction();
		wt.setTransactionType(3);
		wt.setTransactionDate(new Date());
		wt.setTransactionAmt(transferAmt);
		wt.setBeneficiaryAccountBean(accountBean2);
		
		accountBean1.addTransation(wt);
		
		WalletTransaction wt1=new WalletTransaction();
		wt1.setTransactionType(1);
		wt1.setTransactionDate(new Date());
		wt1.setTransactionAmt(transferAmt);
		wt1.setBeneficiaryAccountBean(accountBean2);
		
		accountBean2.addTransation(wt1);
		
		
		
		boolean result=service.fundTransfer(accountBean1, accountBean2, transferAmt);
		
		if(result){
			System.out.println("Transfering Money from Account done");
		}else{
			System.out.println("Transfering Money from Account Failed ");
		}
		
	}
	
	
	void printTransaction() throws Exception
	{
		System.out.println("Enter Account ID (for printing Transaction Details");
		int accId=scanner.nextInt();
		
		AccountBean accountBean=service.findAccount(accId);
		
		List<WalletTransaction>  transactions=accountBean.getAllTransactions();
		
		/*System.out.println(accountBean);
		System.out.println(accountBean.getCustomerBean());*/
		
		System.out.println("Name: "+accountBean.getCustomerBean().getFirstName() +" "+ accountBean.getCustomerBean().getLastName());
		System.out.println("Phone Number : "+ accountBean.getCustomerBean().getPhoneNo());
		System.out.println("Initial Deposit : "+accountBean.getInitialDeposit());
		
		System.out.println("------------------------------------------------------------------");
		
		for(WalletTransaction wt:transactions){
			
			String str="";
			if(wt.getTransactionType()==1){
				str=str+"DEPOSIT";
			}
			if(wt.getTransactionType()==2){
				str=str+"WITHDRAW";
			}
			if(wt.getTransactionType()==3){
				str=str+"FUND TRANSFER";
			}
			
			str=str+"\t\t"+wt.getTransactionDate();
			
			str=str+"\t\t"+wt.getTransactionAmt();
			System.out.println(str);
		}
		
		System.out.println("------------------------------------------------------------------");
	
	}
	
	    
	
}
