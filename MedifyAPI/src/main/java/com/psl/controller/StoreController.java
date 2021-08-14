package com.psl.controller;

import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
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
import com.psl.entity.Store;
import com.psl.exception.MedifyException;
import com.psl.service.StoreService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/owner")
public class StoreController {
	
	private final StoreService storeService;

	@PostMapping("/addStore")
	public Store addStore(@RequestBody RegisterStoreRequest registerUserRequest) {
		log.info("New store added to medify" + registerUserRequest.getEmail());
		return storeService.registerStore(registerUserRequest);
	}
	
	@PutMapping("/updateStore")
	public ResponseEntity<HttpStatus> updateStore(@RequestBody Store store) {
		storeService.updateStore(store);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteStore/{id}")
	public ResponseEntity<HttpStatus> deleteStore(@PathVariable long id) {
		storeService.deleteStore(id);
		log.info("New store deleted from medify" + id);
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
	
	@GetMapping("/getStoreByUser/{username}")
	public ResponseEntity<List<Store>> getStoreByUser(@PathVariable String username) {
		List<Store> store = storeService.findStoreByUser(username);
		return new ResponseEntity<>(store, HttpStatus.OK);
	}

}