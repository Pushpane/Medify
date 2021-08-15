package com.psl.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psl.entity.PasswordChangerToken;

public interface IPasswordChangerTokenDAO extends JpaRepository<PasswordChangerToken, Long> {

	Optional<PasswordChangerToken> findByToken(String token);
	
	void deleteByToken(String token);
}
