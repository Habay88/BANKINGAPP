package com.bap.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bap.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, String>{
    
}
