package com.bap.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.bap.entity.User;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Long>{

	Boolean existsByEmail(String email);
	Boolean existsByAccountNumber(String accountNumber);
	User findByAccountNumber(String accountNumber);
     Optional<User> findByEmail(String email);
}
