package com.bap.service;

import com.bap.dto.BankResponse;
import com.bap.dto.CreditDebitRequest;
import com.bap.dto.EnquiryRequest;
import com.bap.dto.UserRequest;

public interface UserService {

	 BankResponse createAccount(UserRequest userRequest);
	 BankResponse balanceEnquiry(EnquiryRequest enquiryRequest);
	 String nameEnquiry(EnquiryRequest request);
	 BankResponse creditAccount(CreditDebitRequest cdRequest);
	 BankResponse debitAccount(CreditDebitRequest dbRequest);
}
