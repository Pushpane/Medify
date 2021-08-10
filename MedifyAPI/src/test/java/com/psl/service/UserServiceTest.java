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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import com.psl.dao.IUserDAO;
import com.psl.dao.IVerificationTokenDAO;
import com.psl.dto.RegisterUserRequest;
import com.psl.entity.Role;
import com.psl.entity.User;
import com.psl.entity.VerificationToken;
import com.psl.exception.MedifyException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

	private Instant instant;
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserService userService;
	
	@MockBean
	private IUserDAO userRepository;
	
	@MockBean
	private IVerificationTokenDAO verificationDAO;
	
//	@Mock
//	private PasswordEncoder passwordEncoder;
	
//	@Test
//	void testRegisterUser() {
//		
//		String instantExpected = "2021-08-10T09:40:42.257476900Z"; 
//		Clock clock = Clock.fixed(Instant.parse(instantExpected), ZoneId.of("UTC")); 
//		Instant instant = Instant.now(clock); 
//		mockStatic(Instant.class); 
//		when(Instant.now()).thenReturn(instant);
//		
//		Role role = new Role(2L, "Owner");
//		User user = new User(2L, "UserName", "UserName@email.com", "Password", role, "1234567890", null, true);
//		RegisterUserRequest request = new RegisterUserRequest("Name@email.com","UserName","Password","1234567890","Owner");
//		
//		String passwordExpexted = "Password";
//		when(passwordEncoder.encode(user.getPassword())).thenReturn("Encoded password");
//		
//		String token = UUID.randomUUID().toString();
//        VerificationToken verificationToken = new VerificationToken();
//        verificationToken.setToken(token);
//        verificationToken.setUser(user);
//        
//		when(verificationDAO.save(verificationToken)).thenReturn(verificationToken);
//		when(userRepository.save(user)).thenReturn(user);
//		userService.registerUser(request);
//		verify(userRepository,times(1)).save(user);
//	}
	
	
	@Test
	void testUpdateUser() {
		Role role = new Role(2L, "Owner");
		User user = new User(0, "name", "Name@email.com", "Password", role, "1234567890", null, false);
		
		userService.updateUser(user);
		verify(userRepository,times(1)).save(user);
	}
	
	@Before
	public void setUp() {
		Role role = new Role(2L, "Owner");
		Optional<User> user = Optional.ofNullable(new User(0, "name", "Name@email.com", "Password", role, "1234567890", null, false));
		
		String email = "Name@email.com";
		when(userRepository.findByEmail(email)).thenReturn(user);
	}
	
	@Test
	void testGetUser() {
		String email =  "Name@email.com";
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

}
