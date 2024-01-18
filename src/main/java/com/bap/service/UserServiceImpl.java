package com.bap.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import com.bap.dto.AccountInfo;
import com.bap.dto.BankResponse;
import com.bap.dto.UserRequest;
import com.bap.entity.User;
import com.bap.repository.UserRepository;
import com.bap.utils.AccountUtils;

public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
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
			//.accountNumber(AccountUtils.generateAccountNumber())
			.accountBalance(BigDecimal.ZERO)
			.phoneNumber(userRequest.getPhoneNumber())
			.alternativePhoneNumber(userRequest.getAlternativePhoneNumber())
			.email(userRequest.getEmail())
			.status("ACTIVE")
				
				.build();
		User savedUser = userRepository.save(newUser);
		return BankResponse.builder()
				.responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
				.responseMessage(AccountUtils.ACCOUNT_EXISTS_MESSAGE)
				.accountInfo(AccountInfo.builder()
						.accountBalance(savedUser.getAccountBalance())
						.accountNumber(savedUser.getAccountNumber())
						.accountName(savedUser.getFirstName() + " " + savedUser.getLastName() + " " + savedUser.getOtherName())
						
						.build())
				.build();
	}

}
