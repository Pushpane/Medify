package com.psl.controller;

import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psl.dto.RegisterMedicineRequest;
import com.psl.entity.Medicine;
import com.psl.exception.MedifyException;
import com.psl.service.MedicineService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/owner")
@Slf4j
public class MedicineController {
	private final MedicineService medicineService;
	
	@PostMapping("/addMedicine")
	public ResponseEntity<?> addMedicine(@ModelAttribute RegisterMedicineRequest registerMedicineRequest) {
		log.info("Medicine added" + registerMedicineRequest.getName());
		return new ResponseEntity<>( medicineService.registerMedicine(registerMedicineRequest),HttpStatus.OK);
	}
	
	@PutMapping("/updateMedicine")
	public ResponseEntity<HttpStatus> updateMedicine(@RequestBody Medicine medicine) {
		medicineService.updateMedicine(medicine);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteMedicine/{id}")
	public ResponseEntity<HttpStatus> deleteMedicine(@PathVariable long id) {
		medicineService.deleteMedicine(id);
		log.info("Medicine deleted" + id);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	@GetMapping("/getAllMedicines")
	public List<Medicine> getMedicines() {
		return medicineService.getAllMedicines();
	}
	
	@GetMapping("/getMedicine/{medicinename}")
	public Medicine getStore(@PathVariable String medicinename) {
		Optional<Medicine> medicine = medicineService.findMedicineByName(medicinename);
		medicine.orElseThrow(()-> new MedifyException("Store not found"));
		return medicine.get();
	}
	
}
