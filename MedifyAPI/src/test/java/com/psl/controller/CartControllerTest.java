package com.psl.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import com.psl.service.CartService;
import com.psl.service.OrderService;
import com.psl.service.UserDetailsServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(CartController.class)
class CartControllerTest {
	@Autowired
	MockMvc mvc;
	
	@MockBean
	CartService cartService;
	@MockBean
	JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@MockBean
	PasswordEncoder passwordEncoder;
	
	@MockBean
	UserDetailsServiceImpl userDetailsServiceImpl;
	
	
	@Test
	void testDeleteCart() throws Exception{
		this.mvc.perform(delete("/api/user/deleteCart"+1))
		.andExpect(status().isOk());
	}

	@Test
	void testUpdateCart() throws Exception{
		this.mvc.perform(put("/api/user/updateCart"))
		.andExpect(status().isOk());
	}

	@Test
	void testDeleteCartByUserAndMed() throws Exception{
		this.mvc.perform(delete("/api/user/deleteCartByUserAndMed/"+1+"/user@email.com"))
		.andExpect(status().isOk());
	}

	@Test
	void testQuantityUpdate() throws Exception{
		this.mvc.perform(put("/api/user/addQuantity"))
		.andExpect(status().isOk());
	
	}

	@Test
	void testRemoveQuantity() throws Exception{
		this.mvc.perform(put("/api/user/removeQuantity"))
		.andExpect(status().isOk());
	}

	@Test
	void testOrder() throws Exception{
		this.mvc.perform(put("/api/user/order/"+"user@email.com"))
		.andExpect(status().isOk());
	
	}

}
