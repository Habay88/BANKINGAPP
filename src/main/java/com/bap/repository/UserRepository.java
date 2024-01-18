package com.bap.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bap.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
