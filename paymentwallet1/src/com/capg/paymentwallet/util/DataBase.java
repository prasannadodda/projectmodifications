package com.capg.paymentwallet.util;

import java.util.ArrayList;
import java.util.List;

import com.capg.paymentwallet.bean.AccountBean;



public class DataBase {
	public static List<String> operations=new ArrayList<>();
	
	AccountBean accountBean=new AccountBean();
	public static List<String> getOperations(){
		operations.add(" CreateAccount");
		operations.add("ShowBalance");
		operations.add("Deposit");
		operations.add("WithDraw");
		operations.add("FundTransfer");
		operations.add("PrintTranscations");
		return operations;
		
	}
}
