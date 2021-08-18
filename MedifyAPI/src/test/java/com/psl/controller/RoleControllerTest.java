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
import com.psl.service.RoleService;
import com.psl.service.UserDetailsServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(RoleController.class)
public class RoleControllerTest {
	
	@Autowired
	MockMvc mvc;
	
	@MockBean
	RoleService service;
	
	@MockBean
	JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@MockBean
	PasswordEncoder passwordEncoder;
	
	@MockBean
	UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Test
	public void testCreateRole() throws Exception {
		String request= "{\"roleId\":4,\"role\":\"string\"}";
		this.mvc.perform(post("/api/admin/createRole").contentType(MediaType.APPLICATION_JSON).content(request)).andExpect(status().isOk());
	}
	
	@Test
	public void testUpdateRole() throws Exception{
		String request = "{\"roleId\":4,\"role\":\"stri\"}";
		this.mvc.perform(put("/api/admin/updateRole").contentType(MediaType.APPLICATION_JSON).content(request)).andExpect(status().isOk());
	}
	
	@Test
	public void testDeleteRole() throws Exception{
		this.mvc.perform(delete("/api/admin/deleteRole/8")).andExpect(status().isOk());
	}
	
	
}
