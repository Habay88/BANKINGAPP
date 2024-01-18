package com.bap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bap.dto.BankResponse;
import com.bap.dto.UserRequest;
import com.bap.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	UserService userService;
	@PostMapping
	public BankResponse createAccount(@RequestBody UserRequest userRequest) {
		
		return userService.createAccount(userRequest);
	}

}
