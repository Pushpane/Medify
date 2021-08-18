package com.psl.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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
	
	@Test
	public void testUpdateAddress() throws Exception{
		String request = "{\"addressId\":8,\"userId\":{\"userId\":1,\"name\":\"Abcd\",\"email\":\"abcd12@gmail.com\",\"password\":\"$2a$10$OdZ8QQfg9dzw7AsUdS9vA..Wja68leQHaxKqIIzeTFg94.Xa6By8a\",\"roleId\":{\"roleId\":2,\"role\":\"Owner\"},\"phoneNumber\":\"7854123690\",\"dateJoined\":1628429318.188148,\"enabled\":true},\"storeId\":null,\"address1\":\"string\",\"address2\":\"string\",\"pincode\":\"string\",\"city\":\"string\",\"state\":\"string\"}";
		this.mvc.perform(put("/api/user/updateAddress").contentType(MediaType.APPLICATION_JSON).content(request)).andExpect(status().isOk());
	}
	
	@Test
	public void testDeleteAddress() throws Exception {
		this.mvc.perform(delete("/api/user/deleteAddress/8")).andExpect(status().isOk());
	}
	

}
