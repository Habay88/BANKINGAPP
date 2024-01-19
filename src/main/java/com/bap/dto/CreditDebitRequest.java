package com.bap.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bap.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditDebitRequest {

	private String accountNumber;
	private BigDecimal amount;
}
