package com.bap.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bap.entity.TransactionHistory;

public interface TransactionRepository extends JpaRepository<TransactionHistory, String>{
    
}
