package com.psl.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import com.psl.service.MedicineService;
import com.psl.service.UserDetailsServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(MedicineController.class)
class MedicineControllerTest {

	@Autowired
	MockMvc mvc;
	
	@MockBean
	MedicineService service;
	
	@MockBean
	JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@MockBean
	PasswordEncoder passwordEncoder;
	
	@MockBean
	UserDetailsServiceImpl userDetailsServiceImpl;
	
//	@Test
//	void testAddMedicine() {
//		fail("Not yet implemented");
//	}
//
	@Test
	void testUpdateMedicine() throws Exception {
		String request = "{\"medicineId\": 0,\"name\": \"string\",\"description\": \"string\",\"price\": 0,\"image\": \"string\" }";
		this.mvc.perform(put("/api/owner/updateMedicine").contentType(MediaType.APPLICATION_JSON).content(request))
		.andExpect(status().isOk());
	}

	@Test
	void testDeleteMedicine() throws Exception {
		this.mvc.perform(delete("/api/owner/deleteMedicine/"+1))
		.andExpect(status().isOk());
	}

	@Test
	void testGetMedicines() throws Exception {
		this.mvc.perform(get("/api/owner/getAllMedicines"))
		.andExpect(status().isOk());
	}

	@Test
	void testGetStore() throws Exception {
		String request = "{\"medicineId\": 0,\"name\": \"string\",\"description\": \"string\",\"price\": 0,\"image\": \"string\" }";
		this.mvc.perform(put("/api/owner/getMedicine/medicinename").contentType(MediaType.APPLICATION_JSON).content(request))
		.andExpect(status().isOk());
	}

}
