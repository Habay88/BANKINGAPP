package com.bap.service;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bap.dto.AccountInfo;
import com.bap.dto.BankResponse;
import com.bap.dto.CreditDebitRequest;
import com.bap.dto.EmailDetails;
import com.bap.dto.EnquiryRequest;
import com.bap.dto.TransactionDto;
import com.bap.dto.TransferRequest;
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
	@Autowired
	TransactionService transactionService;
	@Autowired
	PasswordEncoder passwordEncoder;
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
			.password(passwordEncoder.encode(userRequest.getPassword()))
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
		// for transaction records
		TransactionDto transactionDto = TransactionDto.builder()
		.accountNumber(userToCredit.getAccountNumber())
		.transactionType("CREDIT")
		.amount(cdRequest.getAmount())

		.build();
		transactionService.saveTransaction(transactionDto);
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
	@Override
	public BankResponse debitAccount(CreditDebitRequest dbRequest) {
		// check if the account exist 
		boolean isAccountExist = userRepository.existsByAccountNumber(dbRequest.getAccountNumber());
		if(!isAccountExist) {
			return BankResponse.builder()
					.responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
					.responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
					.accountInfo(null)
					.build();
		}
		//check if the amount you intend to withdraw is not more than what the current ac balance
		 User userToDebit = userRepository.findByAccountNumber(dbRequest.getAccountNumber());
		 // convert big decimal to int 
		 BigInteger availableBalance = (userToDebit.getAccountBalance().toBigInteger());
		 BigInteger debitAmount = dbRequest.getAmount().toBigInteger();
		 if(availableBalance.intValue() < debitAmount.intValue()) {
			 return BankResponse.builder()
					 .responseCode(AccountUtils.INSUFFICCIENT_BALANCE_CODE)
					 .responseMessage(AccountUtils.INSUFFICCIENT_BALANCE_MESSAGE)
					 .accountInfo(null)
					 .build();
		 }
		
		 else {
			 userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(dbRequest.getAmount()));
			 userRepository.save(userToDebit);
			 		  // for transaction records
		TransactionDto transactionDto = TransactionDto.builder()
		.accountNumber(userToDebit.getAccountNumber())
		.transactionType("DEBIT")
		.amount(dbRequest.getAmount())

		.build();
		transactionService.saveTransaction(transactionDto);
			 return BankResponse.builder()
					 .responseCode(AccountUtils.ACCOUNT_DEBIT_CODE)
					 .responseMessage(AccountUtils.ACCOUNT_DEBIT_MESSAGE)
					 .accountInfo(AccountInfo.builder()
							 .accountNumber(dbRequest.getAccountNumber())
							 .accountName(userToDebit.getFirstName()+ " "+ userToDebit.getLastName()+ " "+ userToDebit.getOtherName())
							 .accountBalance(userToDebit.getAccountBalance())
							 .build())
							 
					 .build();
			
		 }
		//return null;
	}
	@Override
	public BankResponse transferCredit(TransferRequest transfer) {
		// get the account to debit (check if account exist )
	//	boolean isSouceAccountExist = userRepository.existsByAccountNumber(transfer.getSourceAccountNumber());
		boolean isDestinationExist =  userRepository.existsByAccountNumber(transfer.getDestinationAccountNumber());
		if(!isDestinationExist) {
			return BankResponse.builder()
					.responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
					.responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
					.accountInfo(null)
					.build();
		}
		// check if the amount i am debiting is not more than the current balance
		User sourceAccountUser = userRepository.findByAccountNumber(transfer.getSourceAccountNumber());
		if(transfer.getAmount().compareTo(sourceAccountUser.getAccountBalance()) > 0) {
			 return BankResponse.builder()
					 .responseCode(AccountUtils.INSUFFICCIENT_BALANCE_CODE)
					 .responseMessage(AccountUtils.INSUFFICCIENT_BALANCE_MESSAGE)
					 .accountInfo(null)
					 .build();
		}
		sourceAccountUser.setAccountBalance(sourceAccountUser.getAccountBalance().subtract(transfer.getAmount()));
		String sourceusername  = sourceAccountUser.getFirstName()+ " "+ sourceAccountUser.getLastName() + " "+sourceAccountUser.getOtherName();
		userRepository.save(sourceAccountUser);
		// send email 
		EmailDetails debitAlert = EmailDetails.builder()
				.subject("DEBIT ALERT")
				.recipient(sourceAccountUser.getEmail())
				.messageBody("The sum of "+ transfer.getAmount() + "has been deducted from your account!! Your current balance is " + sourceAccountUser.getAccountBalance())
				
				.build();
		emailService.sendEmailAlert(debitAlert); 
		 // for transaction records
		 TransactionDto transactionDto = TransactionDto.builder()
		 .accountNumber(sourceAccountUser.getAccountNumber())
		 .transactionType("Transfer")
		 .amount(transfer.getAmount())
 
		 .build();
		 transactionService.saveTransaction(transactionDto);
		// debit the account 
		// get the account to credit 
		// credit the account
		User destinationAccountUser = userRepository.findByAccountNumber(transfer.getDestinationAccountNumber());
		destinationAccountUser.setAccountBalance(destinationAccountUser.getAccountBalance().add(transfer.getAmount()));
		//String recipientUsername = destinationAccountUser.getFirstName()+ " "+ destinationAccountUser.getLastName()+" " + destinationAccountUser.getOtherName();
		userRepository.save(destinationAccountUser);
		// send email 
		EmailDetails creditAlert = EmailDetails.builder()
				.subject("CREDIT ALERT")
				.recipient(sourceAccountUser.getEmail())
				.messageBody("The sum of "+ transfer.getAmount() + "has been sent to your account from !!" +sourceusername +" Your current balance is " + sourceAccountUser.getAccountBalance())
				
				.build();
		emailService.sendEmailAlert(debitAlert); 
		return BankResponse.builder()
				 .responseCode(AccountUtils.ACCOUNT_SUCCESFUL_CODE)
				 .responseMessage(AccountUtils.ACCOUNT_SUCCESFUL_MESSAGE)
				 .accountInfo(null)
				 .build();
	}
	
	// balance enquiry , name enquiry, credit the account , debit, transfer

}
