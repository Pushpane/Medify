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

import com.psl.dto.RegisterMedicineRequest;
import com.psl.entity.Medicines;
import com.psl.exception.MedifyException;
import com.psl.service.MedicineService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/owner")

public class MedicineController {
	private final MedicineService medicineService;
	
	@PostMapping("/addMedicine")
	public void addStore(@RequestBody RegisterMedicineRequest registerMedicineRequest) {
		medicineService.registerMedicine(registerMedicineRequest);
	}
	
	@PutMapping("/updateMedicine")
	public ResponseEntity<HttpStatus> updateMedicine(@RequestBody Medicines medicine) {
		medicineService.updateMedicine(medicine);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteMedicine/{id}")
	public ResponseEntity<HttpStatus> deleteMedicine(@PathVariable long id) {
		medicineService.deleteMedicine(id);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	@GetMapping("/getAllMedicines")
	public List<Medicines> getMedicines() {
		return medicineService.getAllMedicines();
	}
	
	@GetMapping("/getMedicine/{medicinename}")
	public Medicines getStore(@PathVariable String medicinename) {
		Optional<Medicines> medicine = medicineService.findMedicineByName(medicinename);
		medicine.orElseThrow(()-> new MedifyException("Store not found"));
		return medicine.get();
	}
	
}