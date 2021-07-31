package com.psl.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psl.dto.RegisterUserRequest;
import com.psl.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class UserController {
	
	private final UserService userService;
	
	@PostMapping("/signup")
	public void signup(@RequestBody RegisterUserRequest registerUserRequest) {
		userService.registerUser(registerUserRequest);
	}

}
