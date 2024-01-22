package com.bap.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


import org.springframework.stereotype.Component;

import com.bap.entity.TransactionHistory;
import com.bap.repository.TransactionRepository;

import lombok.AllArgsConstructor;

@Component

////@AllArgsConstructor
public class BankStatement {

	private TransactionRepository transactionRepository;
	// retreive list of trasactions  within a date range given an account number 
	// generate a pdf file of transaction 
	// send the file via email
/*	public List<TransactionHistory> generateStatement(String accountNumber, String startDate, String endDate){
		LocalDate start = LocalDate.parse(startDate,DateTimeFormatter.ISO_DATE);
		LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);*/
	//	List<TransactionHistory> findByDateBetweenAndAccountNumber(String accountNumber, String startDate, String endDate);
		/*
		 * List<TransactionHistory> transactionList =
		 * transactionRepository.findAll().stream().filter(transaction ->
		 * transaction.getAccountNumber().equals(accountNumber)) .filter(transaction ->
		 * transaction.getCreatedAt().isEqual(start)).filter(transaction ->
		 * transaction.getCreatedAt().isEqual(end)).toList(); return null;
		 */

	}
//}
