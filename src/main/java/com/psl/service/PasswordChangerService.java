package com.psl.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.psl.dao.IPasswordChangerTokenDAO;
import com.psl.dto.PasswordChangerRequest;
import com.psl.entity.NotificationEmail;
import com.psl.entity.PasswordChangerToken;
import com.psl.entity.User;
import com.psl.exception.MedifyException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class PasswordChangerService {

	private IPasswordChangerTokenDAO changerTokenDAO;
	private MailService mailService;
	private final PasswordEncoder passwordEncoder;
	private UserService userService;
	
	public void addVerificationToken(String email) {
		PasswordChangerToken pcToken = new PasswordChangerToken();
		pcToken.setCreatedDate(Instant.now());
		pcToken.setEmail(email);
		String token = UUID.randomUUID().toString();
		pcToken.setToken(token);
		changerTokenDAO.save(pcToken);
		
		mailService.sendMail(new NotificationEmail("Please Change your Medify Account ",email,
				" Password, Use this verification token for changing password : " + token));
	}
	
	public void changePassword(PasswordChangerRequest request) {
		 Optional<PasswordChangerToken> pcToken=  changerTokenDAO.findByToken(request.getToken());
		pcToken.orElseThrow(() -> {
			log.error("Token not found " + request.getEmail());
			return new MedifyException("Token Not found");
		});

		 Optional<User> user = userService.getUser(pcToken.get().getEmail());
		user.orElseThrow(() -> {
			log.error("User not found " + request.getEmail());
			return new MedifyException("User Not found");
		});

		 user.get().setPassword(passwordEncoder.encode(request.getPassword()));
		 userService.updateUser(user.get());
		 
		 changerTokenDAO.deleteByToken(request.getToken());
	}
}
