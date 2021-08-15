package com.psl.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psl.entity.VerificationToken;

@Repository
public interface IVerificationTokenDAO extends JpaRepository<VerificationToken, Long> {

	Optional<VerificationToken> findByToken(String token);
}
