package com.psl.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.psl.security.JwtAuthenticationFilter;
import com.psl.service.OrderService;
import com.psl.service.UserDetailsServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
class OrderControllerTest {
	
	@Autowired
	MockMvc mvc;
	
	@MockBean
	OrderService orderService;
	@MockBean
	JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@MockBean
	PasswordEncoder passwordEncoder;
	
	@MockBean
	UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Test
	void testUpdateOrder() throws Exception{
		this.mvc.perform(get("/api/user/updateOrder"))
		.andExpect(status().isOk());
	}

	@Test
	void testDeleteOrder() throws Exception{
		this.mvc.perform(get("/api/user/deleteOrder"))
		.andExpect(status().isOk());
	}

}
