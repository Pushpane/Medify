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

import com.psl.dto.RegisterStoreRequest;
import com.psl.entity.Role;
import com.psl.entity.Store;
import com.psl.exception.MedifyException;
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
	
	@PutMapping("/updateStore")
	public ResponseEntity<HttpStatus> updateStore(@RequestBody Store store) {
		storeService.updateStore(store);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteStore/{id}")
	public ResponseEntity<HttpStatus> deleteStore(@PathVariable long id) {
		storeService.deleteStore(id);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	@GetMapping("/getStoreById/{id}")
	public Store getStoreById(@PathVariable long id) {
		return storeService.getStoreById(id);
	}
	
	@GetMapping("/getAllStore")
	public List<Store> getStores() {
		return storeService.getAllStores();
	}
	
	@GetMapping("/getStore/{storename}")
	public Store getStore(@PathVariable String storename) {
		Optional<Store> store = storeService.findStoreByName(storename);
		store.orElseThrow(()-> new MedifyException("Store not found"));
		return store.get();
	}

}