package com.psl.service;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.psl.dao.IMedicinesDAO;
import com.psl.dto.RegisterMedicineRequest;
import com.psl.entity.Medicine;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class MedicineService {

	private final IMedicinesDAO medicineDAO;

	public void registerMedicine(RegisterMedicineRequest request) {
		Medicine medicine = fillMedicine(request);

		medicineDAO.save(medicine);
	}

	private Medicine fillMedicine(RegisterMedicineRequest request) {
		Medicine medicine = new Medicine();

		medicine.setName(request.getName());
		medicine.setDescription(request.getDescription());
		medicine.setPrice(request.getPrice());
		medicine.setImage(request.getImage());

		return medicine;
	}

	//updating medicine
	public void updateMedicine(Medicine medicine) {
		medicineDAO.save(medicine);
	}

	//deleting medicine
	public void deleteMedicine(long id) {
		medicineDAO.deleteById(id);
	}

	//Listing all medicine
	public List<Medicine> getAllMedicines(){
		return medicineDAO.findAll();
	}

	//finding medicine by name
	public Optional<Medicine> findMedicineByName(String medicine) {
		return medicineDAO.findByName(medicine);
	}
	
}
