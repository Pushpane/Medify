package com.psl.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psl.dto.RegisterAddressRequest;


import com.psl.dto.RegisterUserRequest;
import com.psl.entity.Address;
import com.psl.entity.Role;
import com.psl.exception.MedifyException;
import com.psl.service.AddressService;
import com.psl.service.UserService;


import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor

@RequestMapping("/api/user")

public class AddressController {
	
	private final AddressService addressService;

	@PostMapping("/addAddress")
	public void addAddress(@RequestBody RegisterAddressRequest registerAddressRequest) {
		addressService.registerAddress(registerAddressRequest);
	}
	
	@PutMapping("/updateAddress")
	public ResponseEntity<HttpStatus> updateAddress(@RequestBody Address address) {
		addressService.updateAddress(address);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}


	@DeleteMapping("/deleteAddress/{id}")
	public ResponseEntity<HttpStatus> deleteRole(@PathVariable long id) {
		addressService.deleteAddress(id);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	

	@GetMapping("/getAllAddress")
	public List<Address> getAddress() {
		return addressService.getAllAddress();
	}
	
	@GetMapping("/getAddress/{city}")
	public Address getAddress(@PathVariable String city) {
		Optional<Address> address = addressService.findByCity(city);
		address.orElseThrow(()-> new MedifyException("city not found"));
		return address.get();
	}
	
	@GetMapping("/getAddress/{pincode}")
	public Address getAddressByPincode(@PathVariable String pincode) {
		Optional<Address> address = addressService.findByPincode(pincode);
		address.orElseThrow(()-> new MedifyException("pincode not found"));
		return address.get();
	}


}

