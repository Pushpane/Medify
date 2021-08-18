package com.psl.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.psl.config.SecurityConfig;
import com.psl.config.WebConfig;
import com.psl.security.JwtAuthenticationFilter;
import com.psl.service.AddressService;
import com.psl.service.UserDetailsServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(AddressController.class)
public class AddressControllerTest {
	
	@Autowired
	MockMvc mvc;
	
	@MockBean
	AddressService service;
	
	@MockBean
	JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@MockBean
	PasswordEncoder passwordEncoder;
	
	@MockBean
	UserDetailsServiceImpl userDetailsServiceImpl;
   	
	
	@Test
	public void testAddAddress() throws Exception{
		String request = "{\"email\":\"abcd12@gmail.com\",\"storeId\":0,\"address1\":\"string\",\"address2\":\"string\",\"pincode\":\"string\",\"city\":\"string\",\"state\":\"string\"}";
		this.mvc.perform(post("/api/user/addAddress").contentType(MediaType.APPLICATION_JSON).content(request)).andExpect(status().isOk());
	}

}
