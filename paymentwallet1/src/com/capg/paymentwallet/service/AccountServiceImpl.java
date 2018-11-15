package com.capg.paymentwallet.service;

import java.util.List;

import com.capg.paymentwallet.bean.AccountBean;
import com.capg.paymentwallet.dao.AccountDAOImpl;
import com.capg.paymentwallet.dao.IAccountDao;
import com.capg.paymentwallet.exception.CustomerException;
import com.capg.paymentwallet.exception.CustomerExceptionMessage;

public class AccountServiceImpl implements IAccountService{
IAccountDao dao=new AccountDAOImpl();
	
	@Override
	public boolean createAccount(AccountBean accountBean)
			throws Exception {
		
		IAccountDao dao=new AccountDAOImpl();
		boolean valid=validations(accountBean);
		if(valid)
		{
		boolean result=dao.createAccount(accountBean);
		return result;
	}
		else
			 throw  new CustomerException(CustomerExceptionMessage.ERROR);
	}
	

	@Override
	public boolean deposit(AccountBean accountBean, double depositAmount)
			throws Exception {
		accountBean.setBalance(accountBean.getBalance()+depositAmount);
		IAccountDao dao=new AccountDAOImpl();
		boolean result=dao.updateAccount(accountBean);
		return result;
	}

	@Override
	public boolean withdraw(AccountBean accountBean, double withdrawAmount)
			throws Exception {
		if(accountBean.getBalance()>withdrawAmount)
		{
		accountBean.setBalance(accountBean.getBalance()-withdrawAmount);
		IAccountDao dao=new AccountDAOImpl();
		boolean result=dao.updateAccount(accountBean);
		return result;
	}else
	{
		throw new CustomerException(CustomerExceptionMessage.BALERROR);
	}
		
	}
	public boolean fundAccountValidation(AccountBean transferingAccountBean,AccountBean beneficiaryAccountBean)
	{
		if(transferingAccountBean.getAccountId()==beneficiaryAccountBean.getAccountId())
		{
			return true;
		}
		else
			return false;
	}
	@Override
	
	
	public boolean fundTransfer(AccountBean transferingAccountBean,AccountBean beneficiaryAccountBean, double transferAmount) throws Exception {
		boolean isValid=fundAccountValidation( transferingAccountBean, beneficiaryAccountBean);
		if(isValid)
		{
			if(transferingAccountBean.getBalance()>transferAmount)
			{
			transferingAccountBean.setBalance(transferingAccountBean.getBalance()-transferAmount);
			beneficiaryAccountBean.setBalance(beneficiaryAccountBean.getBalance()+transferAmount);
			
			IAccountDao dao=new AccountDAOImpl();
			boolean result1=dao.updateAccount(transferingAccountBean);
			boolean result2=dao.updateAccount(beneficiaryAccountBean);
			return result1 && result2;
			}
		else
		{
			throw new CustomerException(CustomerExceptionMessage.BALERROR);
		}
		}
		else
		{
			throw new CustomerException(CustomerExceptionMessage.FUNDERROR);
			
		}
			

		}
	
		
		



	@Override
	public AccountBean findAccount(int accountId) throws Exception {
		IAccountDao dao=new AccountDAOImpl();
		AccountBean bean=dao.findAccount(accountId);
		return bean;
	}
	public boolean validations(AccountBean accountBean) throws CustomerException {
		boolean isValid = false;
		if (accountBean.getCustomerBean().getFirstName().trim().length() < 4) {
			throw new CustomerException(CustomerExceptionMessage.FNERROR);
		} else if (accountBean.getCustomerBean().getLastName().trim().length() < 4) {
			throw new CustomerException(CustomerExceptionMessage.LNERROR);
		} else if (!(String.valueOf(accountBean.getCustomerBean().getPhoneNo())
				.matches("(0)?[6-9][0-9]{9}"))) {
			throw new CustomerException(CustomerExceptionMessage.PNOERROR);
		}  else if (accountBean.getCustomerBean().getAddress().length() == 0) {
			throw new CustomerException(CustomerExceptionMessage.ADRERROR);
		} else if (!(accountBean.getCustomerBean().getEmailId()
				.matches("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}"))) {
			throw new CustomerException(CustomerExceptionMessage.EMAILERROR);
		} else {
			isValid = true;
		}
		return isValid;
	}
	
	
	@Override
	public String  gender(String g)
	{    
	
		if(g.equals("F"))
		{
			return "Mrs";
		}
		else if(g.equals("M"))
		{
		return "Ms" ;
		
	}
		return null;
}


	@Override
	public List<String> getOperations() {
		
		return dao.getOperations();
	}



	
}
