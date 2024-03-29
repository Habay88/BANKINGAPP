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
import com.bap.dto.LoginDto;
import com.bap.dto.TransferRequest;
import com.bap.dto.UserRequest;
import com.bap.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/user")
@Tag(name="User Account Management API")
public class UserController {
	
	@Autowired
	private  UserService userService;
	@Operation(
		summary = "Create new user for the application",
		description="create a new user by just filling in basic details, Account number will be autogenerated and sent to the email provided"
	)
	@ApiResponse(
		responseCode = "201",
		description = "Http status 201 created"
	)
	
	@PostMapping
	public BankResponse createAccount(@RequestBody UserRequest userRequest) {
		
		return userService.createAccount(userRequest);
	}
	@PostMapping("/login")
	public BankResponse login(@RequestBody LoginDto loginDto) {
		
		return userService.login(loginDto);
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
	
	@PostMapping("/debitAccount")
	public BankResponse debitAccount(@RequestBody CreditDebitRequest dbRequest) {
		return userService.debitAccount(dbRequest);
	}
	
	@PostMapping("/transfer")
	public BankResponse transfer(@RequestBody TransferRequest transferRequest) {
		return userService.transferCredit(transferRequest);
	}
}
