package com.psl.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.psl.dao.IRefreshTokenDAO;
import com.psl.entity.RefreshToken;


@RunWith(SpringRunner.class)
@SpringBootTest
class RefreshTokenServiceTest {
	
	@Autowired
	private RefreshTokenService refreshTokenService;
	
	@MockBean
	private IRefreshTokenDAO repository;

	@Test
	void testGenerateRefreshToken() {
		String instantExpected = "2014-12-22T10:15:30Z"; 
		Clock clock = Clock.fixed(Instant.parse(instantExpected), ZoneId.of("UTC")); 
		Instant instant = Instant.now(clock); 
		mockStatic(Instant.class); 
		when(Instant.now()).thenReturn(instant);
		
		String tokenExpected = "ab46fe80-aa7a-45d9-a83f-0ae4333d40f1";
		//String uuid = UUID.randomUUID().toString();
		UUID uuid = UUID.fromString(tokenExpected);
		//String uuid1=uuid.toString();
		mockStatic(UUID.class);
		when(UUID.randomUUID()).thenReturn(uuid);
		
		
		RefreshToken refreshToken = new RefreshToken(0L, uuid.toString(), instant);
		when(repository.save(refreshToken)).thenReturn(refreshToken);
		assertEquals(refreshToken, refreshTokenService.generateRefreshToken());
		//verify(repository, times(1)).save(refreshToken);
	}

//	@Test
//	void testValidateRefreshToken() {
//		//String token = "ab46fe80-aa7a-45d9-a83f-0ae4333d40f1";
//		String token = "925f07ed-09bf-47cb-8d4f-f9081a54d650";
//		refreshTokenService.validateRefreshToken(token);
//		verify(repository, times(1)).findByToken(token);
//	}

	@Test
	void testDeleteRefreshToken() {
		String token = "token";
		refreshTokenService.deleteRefreshToken(token);
		verify(repository, times(1)).deleteByToken(token);
	}

}
