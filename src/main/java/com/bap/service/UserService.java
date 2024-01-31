package com.bap.service;

import org.springframework.stereotype.Component;

import com.bap.dto.BankResponse;
import com.bap.dto.CreditDebitRequest;
import com.bap.dto.EnquiryRequest;
import com.bap.dto.TransferRequest;
import com.bap.dto.UserRequest;
@Component
public interface UserService {

	 BankResponse createAccount(UserRequest userRequest);
	 BankResponse balanceEnquiry(EnquiryRequest enquiryRequest);
	 String nameEnquiry(EnquiryRequest request);
	 BankResponse creditAccount(CreditDebitRequest cdRequest);
	 BankResponse debitAccount(CreditDebitRequest dbRequest);
	 BankResponse transferCredit(TransferRequest transfer);
}
