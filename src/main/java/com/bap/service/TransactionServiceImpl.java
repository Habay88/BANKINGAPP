package com.bap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bap.dto.TransactionDto;
import com.bap.entity.Transaction;
//import com.bap.entity.TransactionHistory;
import com.bap.repository.TransactionRepository;
@Service
public class TransactionServiceImpl  implements TransactionService{
 @Autowired
 TransactionRepository transactionRepository;
    @Override
    public void saveTransaction(TransactionDto transaction) {
        Transaction transactionHistory = Transaction.builder()
        .transactionType(transaction.getTransactionType())
        .accountNumber(transaction.getAccountNumber())
        .amount(transaction.getAmount())
        .status("Success")
        
        .build();
        transactionRepository.save(transactionHistory);
        System.out.println("Transaction saved successsfully");
    }
    
}
