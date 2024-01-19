package com.bap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bap.dto.BankResponse;
import com.bap.dto.CreditDebitRequest;
import com.bap.dto.EnquiryRequest;
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
	
	@GetMapping("/balanceEnquiry")
	public BankResponse balanceEnquiry(@RequestBody EnquiryRequest request) {
		return userService.balanceEnquiry(request);
	}
	@GetMapping("/nameEnquiry")
	public String nameEnquiry(@RequestBody EnquiryRequest request) {
		return userService.nameEnquiry(request);
	}
	@PostMapping("/creditAccount")
	public BankResponse creditAccount(@RequestBody CreditDebitRequest cdRequest) {
		return userService.creditAccount(cdRequest);
	}
	
}
