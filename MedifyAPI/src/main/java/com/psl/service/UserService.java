package com.psl.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.psl.dao.IUserDAO;
import com.psl.dao.IVerificationTokenDAO;
import com.psl.dto.AuthenticationResponse;
import com.psl.dto.LoginRequest;
import com.psl.dto.RefreshTokenRequest;
import com.psl.dto.RegisterUserRequest;
import com.psl.entity.NotificationEmail;
import com.psl.entity.Role;
import com.psl.entity.User;
import com.psl.entity.VerificationToken;
import com.psl.exception.MedifyException;
import com.psl.security.JwtProvider;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class UserService {
	
	private final IUserDAO userDAO;
	private final PasswordEncoder passwordEncoder;
	private final RoleService roleService;
	private final IVerificationTokenDAO verificationTokenDAO;
	private final MailService mailService;
	private final AuthenticationManager authenticationManager;
	private final JwtProvider jwtProvider;
	private final RefreshTokenService refreshTokenService;
	
	public void registerUser(RegisterUserRequest request) {
		User user = fillUser(request);
		
		userDAO.save(user);
		
		String token = generateVerificationToken(user);
		mailService.sendMail(new NotificationEmail("Please Activate your Medify Account",request.getEmail(),
				"Thank you for signing up to Medify, please click on the below url to activate " +
				"your account : http://localhost:8081/Medify/api/auth/accountVerification/"+token));
	}
	
	private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenDAO.save(verificationToken);
        return token;
    }
	
	private User fillUser(RegisterUserRequest request) {
		User user = new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setPhoneNumber(request.getPhone());
		
		Optional<Role> role = roleService.findByRole(request.getRole());
		//checking if role is null if role is null throw exception
		role.orElseThrow(()-> new MedifyException("Role not found"));
		
		user.setRoleId(role.get());
		user.setDateJoined(Instant.now());
		user.setEnabled(false);
		
		return user;
	}
	
	public void updateUser(User user) {
		userDAO.save(user);
	}
	
	public Optional<User> getUser(String email){
		return userDAO.findByEmail(email);
	}

	public void verifyAccount(String token) {
		Optional<VerificationToken> verificationToken = verificationTokenDAO.findByToken(token);
		verificationToken.orElseThrow(() -> new MedifyException("Invalid Token"));
		fetchUserAndEnable(verificationToken.get());
	}
	
	private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getEmail();
        User user = userDAO.findByEmail(username).orElseThrow(() -> new MedifyException("User Not Found with username - " + username));
        user.setEnabled(true);
        userDAO.save(user);
    }

	public AuthenticationResponse login(LoginRequest loginRequest) {
		Authentication authenticate = authenticationManager.authenticate(new 
				UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		String token = jwtProvider.generateToken(authenticate);
		return AuthenticationResponse.builder()
				.authenticationToken(token)
				.refreshToken(refreshTokenService.generateRefreshToken().getToken())
				.expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
				.username(loginRequest.getEmail())
				.build();
	}

	public AuthenticationResponse refreshToken(@Valid RefreshTokenRequest refreshTokenRequest) {
		refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
		String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getEmail());
		return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getEmail())
                .build();
	}

	public Optional<User> findByUser(String user) {
		return userDAO.findByEmail(user);
	}
	
	public Optional<User> findById(long id) {
		return userDAO.findById(id);
	}

}
