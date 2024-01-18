package com.bap.service;

import com.bap.dto.BankResponse;
import com.bap.dto.UserRequest;

public interface UserService {

	 BankResponse createAccount(UserRequest userRequest);
}
