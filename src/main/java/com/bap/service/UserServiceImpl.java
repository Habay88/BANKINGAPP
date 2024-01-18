package com.bap.service;

import com.bap.dto.BankResponse;
import com.bap.dto.UserRequest;
import com.bap.entity.User;

public class UserServiceImpl implements UserService {

	@Override
	public BankResponse createAccount(UserRequest userRequest) {
	// creating an account saving a new user tot the db
		User newUser = User.builder()
			.firstName(userRequest.getFirstName())
			.lastName(userRequest.getLastName())
			.otherName(userRequest.getOtherName())
			.gender(userRequest.getOtherName())
			.address(userRequest.getAddress())
			.stateOfOrigin(userRequest.getStateOfOrigin())
			.accountNumber()
			
				
				.build();
	}

}
