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
import com.psl.service.MedicineToStoreService;
import com.psl.service.UserDetailsServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(MedicineToStoreController.class)
public class MedicineToStoreControllerTest {
	@Autowired
	MockMvc mvc;
	
	@MockBean
	MedicineToStoreService service;
	
	@MockBean
	JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@MockBean
	PasswordEncoder passwordEncoder;
	
	@MockBean
	UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Test
	public void testRegisterMedicineToStore() throws Exception{
		String request = "{\"medicineId\":9,\"storeId\":8}";
		this.mvc.perform(post("/api/user/addMedicineToStore").contentType(MediaType.APPLICATION_JSON).content(request)).andExpect(status().isOk());
	}
	
	@Test
	public void testDeleteMedicineToStore() throws Exception{
		this.mvc.perform(delete("/api/user/deleteMedicineToStore/")).andExpect(status().isOk());
	}
}
