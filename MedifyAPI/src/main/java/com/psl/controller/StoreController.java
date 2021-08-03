package com.psl.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psl.dto.RegisterStoreRequest;
<<<<<<< HEAD
import com.psl.dto.RegisterUserRequest;
=======
>>>>>>> 282b6e385d5fd8cda76fdfdf83ea19a301da8e72
import com.psl.service.StoreService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor

@RequestMapping("/api/owner")
public class StoreController {
	
	private final StoreService storeService;

	@PostMapping("/addStore")
	public void addStore(@RequestBody RegisterStoreRequest registerUserRequest) {
		storeService.registerStore(registerUserRequest);
	}

}