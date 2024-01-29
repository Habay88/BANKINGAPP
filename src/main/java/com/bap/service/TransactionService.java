package com.bap.service;

import com.bap.dto.TransactionDto;
import com.bap.entity.Transaction;

public interface TransactionService {
    
    void saveTransaction(TransactionDto transaction);
}
