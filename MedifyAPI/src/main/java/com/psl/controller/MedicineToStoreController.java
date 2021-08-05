package com.psl.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psl.dto.RegisterMedicineToStoreRequest;
import com.psl.entity.Medicines;
import com.psl.service.MedicineToStoreService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class MedicineToStoreController {
	
	private final MedicineToStoreService medicineToStoreService;
	
	@PostMapping("/addMedicineToStore")
	public ResponseEntity<HttpStatus> registerMedicineToStore(@RequestBody RegisterMedicineToStoreRequest registerMedicineToStoreRequest) {
		medicineToStoreService.registerMedicineToStore(registerMedicineToStoreRequest);
		
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteMedicineToStore/{id}")
	public ResponseEntity<HttpStatus> deleteMedicineToStore(@PathVariable long id){
		medicineToStoreService.deleteMedicineToStore(id);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	//UnComplete
	
	@GetMapping("/getMedicinesByStore/{storeName}")
	public void getMedicineByStore(@PathVariable String storeName) {
		
	}
	
	@GetMapping("/getStoreByMedicine/{medicineName}")
	public void getStoreByMedicine(@PathVariable String storeName) {
		
	}
	
}
