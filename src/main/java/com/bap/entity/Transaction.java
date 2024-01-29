package com.bap.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="transactions")
public class Transaction {
	 @Id
	 @GeneratedValue(strategy = GenerationType.UUID)
private String transactionId;
private String transactionType;
private BigDecimal amount;
private String accountNumber;
private String status;
@CreationTimestamp
private LocalDateTime createdAt;
	
}
