package com.psl.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psl.dto.RegisterAddressRequest;
import com.psl.dto.RegisterUserRequest;
import com.psl.service.AddressService;
import com.psl.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class AddressController {
	
	private final AddressService addressService;
	
	@PostMapping("/address")
	public void addAddress(@RequestBody RegisterAddressRequest registerAddressRequest) {
		addressService.registerAddress(registerAddressRequest);
	}

}
