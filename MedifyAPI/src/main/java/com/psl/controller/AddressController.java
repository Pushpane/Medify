package com.psl.controller;

import java.util.List;

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


import com.psl.entity.Address;
import com.psl.entity.Store;
import com.psl.service.AddressService;



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
	
	@GetMapping("/getAddressByCity/{city}")
	public List<Address> getAddress(@PathVariable String city) {
		List<Address> address = addressService.getByCity(city);
		return address;
	}
	
	@GetMapping("/getAddressByPin/{pincode}")
	public List<Address> getAddressByPincode(@PathVariable String pincode) {
		List<Address> address = addressService.getByPincode(pincode);
		return address;
	}
	

	@GetMapping("/getAddressByStore/{store}")
	public Address getAddressByStore(@PathVariable Store store) {
		Address address = addressService.findByStore(store);
		return address;
	}
	//http://localhost:8081/api/user/getAddress?user=user_email&store=store_name
	@GetMapping("/getAddressByUser/{email}")
	public List<Address> getAddressByUserAndStore(@PathVariable String email) {
		List<Address> address = addressService.findByUser(email);
		return address;
	}
	


}

