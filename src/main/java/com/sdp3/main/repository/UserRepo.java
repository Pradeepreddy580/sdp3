package com.sdp3.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sdp3.main.entity.User;

public interface UserRepo extends JpaRepository<User, Integer>{


	User findByEmail(String email);

	User findByPassword(String password);

	
	
}
