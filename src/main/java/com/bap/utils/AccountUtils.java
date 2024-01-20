package com.bap.utils;

import java.time.Year;

public class AccountUtils {

	public static  final  String ACCOUNT_EXISTS_CODE = "001";
	public static  final  String ACCOUNT_EXISTS_MESSAGE = "User already exist ";
	public static  final  String ACCOUNT_CREATION_SUCCESS = "002";
	public static  final  String ACCOUNT_CREATION_CODE = "Account has already been created";
	
	public static  final  String ACCOUNT_NOT_EXIST_CODE = "003";
	public static  final  String ACCOUNT_NOT_EXIST_MESSAGE = "USER WITH THE PROVIDED ACCOUNT DOES NOT EXIST";
	
	public static  final  String ACCOUNT_FOUND_CODE = "004";
	public static  final  String ACCOUNT_FOUND_SUCCESS = "User Account found";
	
	public static  final  String ACCOUNT_CREDITED_SUCCESS = "005";
	public static  final  String ACCOUNT_CREDITED_SUCCESS_MESSAGE = "User Account has been credited successfully";
	
	public static  final  String INSUFFICCIENT_BALANCE_CODE = "006";
	public static  final  String INSUFFICCIENT_BALANCE_MESSAGE = "Insufficient Balance";
	public static  final  String ACCOUNT_DEBIT_CODE = "007";
	public static  final  String ACCOUNT_DEBIT_MESSAGE = "Account has been succesfully debited";
	
	
	public static String generateAccountNumber() {
		// 2024 + randomsix digits
		Year currentYear = Year.now();
		
		int min = 100000;
		
		int max = 999999;
		
		//generate a random number between min and max
		
		int randNumber = (int)Math.floor(Math.random()* (max -min + 1)+ min);
		
		// convert current year and random numbe rto strings and concat them together
		String year = String.valueOf(currentYear);
		String randomNumber = String.valueOf(randNumber);
		StringBuilder accountNumber = new StringBuilder();
		return accountNumber.append(year).append(randomNumber).toString();
	}
}
