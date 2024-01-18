package com.bap.utils;

import java.time.Year;

public class AccountUtils {

	public static  final  String ACCOUNT_EXISTS_CODE = "001";
	public static  final  String ACCOUNT_EXISTS_MESSAGE = "This user already exist and has an active account";
	public static  final  String ACCOUNT_CREATION_SUCCESS = "002";
	public static  final  String ACCOUNT_CREATION_CODE = "Account has already been created";

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
