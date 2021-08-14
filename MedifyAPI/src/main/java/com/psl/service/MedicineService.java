package com.psl.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.psl.dao.IMedicinesDAO;
import com.psl.dto.RegisterMedicineRequest;
import com.psl.entity.Medicine;
import com.psl.exception.MedifyException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class MedicineService {

	private final IMedicinesDAO medicineDAO;

	public Medicine registerMedicine(RegisterMedicineRequest request) {
		Medicine medicine = fillMedicine(request);

		medicineDAO.save(medicine);
		return medicine;
	}

	private Medicine fillMedicine(RegisterMedicineRequest request) {
		Medicine medicine = new Medicine();
		final String uuid = UUID.randomUUID().toString();
		try {

			//final String UPLOAD_DIR = System.getProperty("user.dir")+"/src/main/resources/static/image/";
			String UPLOAD_DIR = new ClassPathResource("static/image/").getFile().getAbsolutePath();

			new File(UPLOAD_DIR).mkdir();
			
			if (request.getImage().isEmpty()) {
				log.error("File is empty please provide a file" + request.getImage());
				throw new MedifyException("File is empty please provide a file");
			}
			if (!request.getImage().getContentType().equals("image/jpeg")) {
				log.error("Only Jpeg files are allowed");
				throw new MedifyException("Only Jpeg files are allowed");
			}
			Files.copy(
					request.getImage().getInputStream(), Paths.get(UPLOAD_DIR + File.separator
							+ uuid + request.getImage().getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
			UPLOAD_DIR = System.getProperty("user.dir")+"/src/main/resources/static/image/";
			new File(UPLOAD_DIR).mkdir();
			
			Files.copy(
					request.getImage().getInputStream(), Paths.get(UPLOAD_DIR + File.separator
							+ uuid + request.getImage().getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
			
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new MedifyException(e.toString());
		}

		medicine.setName(request.getName());
		medicine.setDescription(request.getDescription());
		medicine.setPrice(request.getPrice());
		medicine.setImage(ServletUriComponentsBuilder.fromCurrentContextPath().path("/image/").path(uuid).path(request.getImage().getOriginalFilename()).toUriString());

		return medicine;
	}

	// updating medicine
	public void updateMedicine(Medicine medicine) {
		medicineDAO.save(medicine);
	}

	// deleting medicine
	public void deleteMedicine(long id) {
		medicineDAO.deleteById(id);
	}

	// Listing all medicine
	public List<Medicine> getAllMedicines() {
		return medicineDAO.findAll();
	}

	// finding medicine by name
	public Optional<Medicine> findMedicineByName(String medicine) {
		return medicineDAO.findByName(medicine);
	}
	
	public Optional<Medicine> findMedicineById(long id) {
		return medicineDAO.findById(id);
	}

}
