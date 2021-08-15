package com.psl.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psl.entity.RefreshToken;

@Repository
public interface IRefreshTokenDAO extends JpaRepository<RefreshToken, Long> {
	Optional<RefreshToken> findByToken(String token);
	
	void deleteByToken(String token);

}
