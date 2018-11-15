package com.capg.paymentwallet.dao;

import java.util.List;

import com.capg.paymentwallet.bean.AccountBean;

public interface IAccountDao {


    public boolean createAccount(AccountBean accountBean) throws Exception ;
    public boolean updateAccount(AccountBean accountBean) throws Exception;
    public AccountBean findAccount(int accountId) throws Exception;
	public List<String> getOperations();
  
  
	
	 
    
}
