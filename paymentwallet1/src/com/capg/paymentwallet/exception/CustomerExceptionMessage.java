package com.capg.paymentwallet.exception;

public class CustomerExceptionMessage extends CustomerException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String ERROR="Invalid Details";
	public static final String FNERROR ="First Name should be more than 4 characters";
	public static final String LNERROR ="Last Name should be more than 4 characters";
	public static final String EMAILERROR = "Email should be valid one";
	public static final String PANERROR = "Pan number should be valid one";
	public static final String PNOERROR = "phone number should be valid one";
	public static final String BALERROR = "Balance should be greater than 500";
	public static final String ADRERROR = "Address should not be null";
	public static final String FUNDERROR = "Both accountis are same ";
	
	

}
