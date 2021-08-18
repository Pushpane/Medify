package com.psl.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.psl.security.JwtAuthenticationFilter;
import com.psl.service.StoreService;
import com.psl.service.UserDetailsServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(StoreController.class)
class StoreControllerTest {

	@Autowired
	MockMvc mvc;
	
	@MockBean
	StoreService service;
	
	@MockBean
	JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@MockBean
	PasswordEncoder passwordEncoder;
	
	@MockBean
	UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Test
	void testAddStore() throws Exception {
		this.mvc.perform(get("/api/owner/addStore"))
		.andExpect(status().isOk());
	}

	@Test
	void testUpdateStore() throws Exception {
		this.mvc.perform(get("/api/owner/updateStore"))
		.andExpect(status().isOk());
	}

	@Test
	void testDeleteStore() throws Exception{
		this.mvc.perform(get("/api/owner/deleteStore/"+1))
		.andExpect(status().isOk());
	}

	@Test
	void testGetStoreById() throws Exception {
		this.mvc.perform(get("/api/owner/getStoreById/"+1))
		.andExpect(status().isOk());
	}

	@Test
	void testGetStores() throws Exception{
		this.mvc.perform(get("/api/owner/getAllStore"))
		.andExpect(status().isOk());
	}

	@Test
	void testGetStore() throws Exception {
		this.mvc.perform(get("/api/owner/getStore/"+"storeName"))
		.andExpect(status().isOk());
	}

	@Test
	void testGetStoreByUser() throws Exception{
		this.mvc.perform(get("/api/owner/getStoreByUser/"+"userName"))
		.andExpect(status().isOk());
	
	}

	

}
