package com.bap.service;

import com.bap.dto.TransactionDto;
import com.bap.entity.TransactionHistory;

public interface TransactionService {
    
    void saveTransaction(TransactionDto transaction);
}
