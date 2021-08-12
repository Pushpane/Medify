package com.psl.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import com.psl.dao.IUserDAO;
import com.psl.dao.IVerificationTokenDAO;
import com.psl.dto.AuthenticationResponse;
import com.psl.dto.LoginRequest;
import com.psl.dto.RegisterUserRequest;
import com.psl.entity.Role;
import com.psl.entity.User;
import com.psl.entity.VerificationToken;
import com.psl.exception.MedifyException;
import com.psl.security.JwtProvider;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

	private Instant instant;
	@Mock
	private AuthenticationManager authenticationManager;
	@Mock
	private JwtProvider jwtProvider;
	
	@Mock
	AuthenticationResponse response;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RefreshTokenService refreshTokenService;
	
	@MockBean
	private IUserDAO userRepository;
	
	@MockBean
	private IVerificationTokenDAO verificationDAO;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	@Mock
	private LoginRequest loginRequest;
	
	@Mock
	Authentication authenticate;
		
	
	/* Issue With the Password Encoder needs to Resolve */
	
	
//	@Test
//	void testRegisterUser() {
//		
//		String instantExpected = "2021-08-10T09:40:42.257476900Z"; 
//		Clock clock = Clock.fixed(Instant.parse(instantExpected), ZoneId.of("UTC")); 
//		Instant instant = Instant.now(clock); 
//		mockStatic(Instant.class); 
//		when(Instant.now()).thenReturn(instant);
//		Instant date = Instant.now();
//		
//		when(passwordEncoder.encode("Password")).thenReturn("Password");
//		
//		Role role = new Role(2L, "Owner");
//		User user = new User(0, "UserName", "UserName@email.com","Password", role, "1234567890",date, false);
//		RegisterUserRequest request = new RegisterUserRequest("UserName@email.com","UserName","Password","1234567890","Owner");
//		
//		
//		
//		
//		String token = UUID.randomUUID().toString();
//        VerificationToken verificationToken = new VerificationToken();
//        verificationToken.setToken(token);
//        verificationToken.setUser(user);
//        
//        userService.registerUser(request);
//		when(verificationDAO.save(verificationToken)).thenReturn(verificationToken);
//		when(userRepository.save(user)).thenReturn(user);
//		verify(userRepository,times(1)).save(user);
//	}
	
	
	@Test
	void testUpdateUser() {
		Role role = new Role(2L, "Owner");
		User user = new User(0, "name", "Name@email.com", "Password", role, "1234567890", null, false);
		
		userService.updateUser(user);
		verify(userRepository,times(1)).save(user);
	}
	

	
	@Test
	void testGetUser() {
		Role role = new Role(2L, "Owner");
		Optional<User> user = Optional.ofNullable(new User(0, "name", "Name@email.com", "Password", role, "1234567890", null, false));
		String email = "Name@email.com";
		when(userRepository.findByEmail(email)).thenReturn(user);
		Optional<User> found = userRepository.findByEmail(email);
		assertEquals(found, userService.getUser(email));
	}
	
	@Test
	void testFindByUser() {
		String email =  "Name@email.com";
		Optional<User> found = userRepository.findByEmail(email);
		assertEquals(found, userService.findByUser(email));
	}
	
	@Test
	void testGetUserById() {
		Role role = new Role(2L, "Owner");
		Optional<User> user = Optional.ofNullable(new User(0, "name", "Name@email.com", "Password", role, "1234567890", null, false));
	
		long id = 0;
		when(userRepository.findById(id)).thenReturn(user);
		
		Optional<User> found = userRepository.findById(id);
		assertEquals(found, userService.getUserById(id));
	}
	
	
//	@Test
//	void testLogin() {
//		
//		loginRequest = new LoginRequest("abc@gmail.com","1234");
//		authenticate = authenticationManager.authenticate(new 
//				UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
//		SecurityContextHolder.getContext().setAuthentication(authenticate);
//		String token = jwtProvider.generateToken(authenticate);
//		AuthenticationResponse res = AuthenticationResponse.builder()
//				.authenticationToken(token)
//				.refreshToken(refreshTokenService.generateRefreshToken().getToken())
//				.expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
//				.username(loginRequest.getEmail())
//				.build();
//		when(jwtProvider.generateToken(authenticate)).thenReturn(token);
//		when(userService.login(loginRequest)).thenReturn(res);
//		AuthenticationResponse actual = userService.login(loginRequest);
//		assertEquals(res, actual);
//	}
	

}
