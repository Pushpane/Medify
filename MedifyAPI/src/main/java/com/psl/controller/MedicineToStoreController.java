package com.psl.controller;

import java.util.List;
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
import com.psl.entity.MedicineToStore;
import com.psl.exception.MedifyException;
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

	@GetMapping("/getStoreByMedicines/{medicineName}")
	public List<MedicineToStore> getStoreByMedicines(@PathVariable String medicineName) {
		List<MedicineToStore> store = medicineToStoreService.getStoreByMedicine(medicineName);
		return store;

	}
	
	@GetMapping("/getAllMedicine")
	public List<MedicineToStore> getAllMedicine() {
		List<MedicineToStore> med = medicineToStoreService.getAllMedicine();
		return med;

	}

	@GetMapping("/getMedToStoreById/{id}")
	public MedicineToStore getMedToStoreById(@PathVariable long id) {
		Optional<MedicineToStore> med = medicineToStoreService.getMedicinesToStoreById(id);
		med.orElseThrow(()-> new MedifyException("MedToStore not found"));
		return med.get();

	}
}
