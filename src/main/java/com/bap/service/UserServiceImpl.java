package com.bap.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bap.dto.AccountInfo;
import com.bap.dto.BankResponse;
import com.bap.dto.CreditDebitRequest;
import com.bap.dto.EmailDetails;
import com.bap.dto.EnquiryRequest;
import com.bap.dto.UserRequest;
import com.bap.entity.User;
import com.bap.repository.UserRepository;
import com.bap.utils.AccountUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	EmailService emailService;
	@Override
	public BankResponse createAccount(UserRequest userRequest) {
	// creating an account saving a new user to the db
		// check if user already exist
		if(userRepository.existsByEmail(userRequest.getEmail())) {
			return BankResponse.builder()
					.responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
					.responseCode(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
					.accountInfo(null)
					.build();
			
		}
		User newUser = User.builder()
			.firstName(userRequest.getFirstName())
			.lastName(userRequest.getLastName())
			.otherName(userRequest.getOtherName())
			.gender(userRequest.getOtherName())
			.address(userRequest.getAddress())
			.stateOfOrigin(userRequest.getStateOfOrigin())
			.accountNumber(AccountUtils.generateAccountNumber())
			.accountBalance(BigDecimal.ZERO)
			.phoneNumber(userRequest.getPhoneNumber())
			.alternativePhoneNumber(userRequest.getAlternativePhoneNumber())
			.email(userRequest.getEmail())
			.status("ACTIVE")
				
				.build();
		User savedUser = userRepository.save(newUser);
		// send email alert 
		EmailDetails emailDetails = EmailDetails.builder()
				.recipient(savedUser.getEmail())
				.subject("ACCOUNT CREATION ")
				.messageBody("Congratulations! Your Account Has been sent successfully created. \n Your Account Details: \n "
						+ "Account Name: " + savedUser.getFirstName() + " "+ savedUser.getLastName() + " " + savedUser.getOtherName() 
						+ "\nAccount Number:" + savedUser.getAccountNumber() )
				.build();
		emailService.sendEmailAlert(emailDetails);
		return BankResponse.builder()
				.responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
				.responseMessage(AccountUtils.ACCOUNT_CREATION_CODE)
				.accountInfo(AccountInfo.builder()
						.accountBalance(savedUser.getAccountBalance())
						.accountNumber(savedUser.getAccountNumber())
						.accountName(savedUser.getFirstName() + " " + savedUser.getLastName() + " " + savedUser.getOtherName())
						
						.build())
				.build();
	}
	@Override
	public BankResponse balanceEnquiry(EnquiryRequest enquiryRequest) {
		// check if the account exist 
		boolean isAccountExist = userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber());
		if(!isAccountExist) {
			return BankResponse.builder()
					.responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
					.responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
					.accountInfo(null)
					.build();
		}
		User foundUser = userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());
		
		return BankResponse.builder()
				.responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
				.responseMessage(AccountUtils.ACCOUNT_FOUND_SUCCESS)
				.accountInfo(AccountInfo.builder()
						.accountBalance(foundUser.getAccountBalance())
						.accountNumber(enquiryRequest.getAccountNumber())
						.accountName(foundUser.getFirstName() + " "+  foundUser.getLastName()+ " " + foundUser.getOtherName())
						
						.build())
				
				.build();
	}
	@Override
	public String nameEnquiry(EnquiryRequest request) {
		// check if the account exist 
		boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
		if(!isAccountExist) {
			return AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE;
		}
		User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());
		
		return foundUser.getFirstName() + " " + foundUser.getLastName()+ " " + foundUser.getOtherName() ;
	}
	@Override
	public BankResponse creditAccount(CreditDebitRequest cdRequest) {
		
		boolean isAccountExist = userRepository.existsByAccountNumber(cdRequest.getAccountNumber());
		
		if(!isAccountExist) {
			return BankResponse.builder()
					.responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
					.responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
					.accountInfo(null)
					.build();
		}
		User userToCredit = userRepository.findByAccountNumber(cdRequest.getAccountNumber());
		userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(cdRequest.getAmount()));
		userRepository.save(userToCredit);
		return BankResponse.builder()
				.responseCode(AccountUtils.ACCOUNT_CREDITED_SUCCESS)
				.responseMessage(AccountUtils.ACCOUNT_CREDITED_SUCCESS_MESSAGE)
				.accountInfo(AccountInfo.builder()
						.accountName(userToCredit.getFirstName()+ " " + userToCredit.getLastName()+ " "+ userToCredit.getOtherName())
						.accountBalance(userToCredit.getAccountBalance())
						.accountNumber(cdRequest.getAccountNumber())
						.build())
				.build();
	}
	
	// balance enquiry , name enquiry, credit the account , debit, transfer

}
