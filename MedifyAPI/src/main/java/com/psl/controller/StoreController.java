package com.psl.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psl.dto.RegisterStoreRequest;
import com.psl.dto.RegisterUserRequest;
import com.psl.service.StoreService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class StoreController {
	
	private final StoreService storeService;
	
	@PostMapping("/store")
	public void addStore(@RequestBody RegisterStoreRequest registerUserRequest) {
		storeService.registerStore(registerUserRequest);
	}

}