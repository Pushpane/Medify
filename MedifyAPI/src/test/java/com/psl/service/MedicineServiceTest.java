package com.psl.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.Collectors;

import com.psl.dao.IMedicinesDAO;
import com.psl.entity.Medicines;

@RunWith(SpringRunner.class)
@SpringBootTest
class MedicineServiceTest {
	
	@Autowired
	private MedicineService medicineService;
	
	@MockBean
	private IMedicinesDAO repository;
	

	@Test
	void testRegisterMedicine() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateMedicine() {
		Medicines medicine = new Medicines(1, "MedicineName", "Description", 200L, "Image");
		medicineService.updateMedicine(medicine);
		verify(repository, times(1)).save(medicine);
	}

	@Test
	void testDeleteMedicine() {
		medicineService.deleteMedicine(1L);
		verify(repository, times(1)).deleteById(1L);
	}

	@Test
	void testGetAllMedicines() {
		Medicines medicine1 = new Medicines(1, "MedicineName1", "Description1", 200L, "Image");
		Medicines medicine2 = new Medicines(2, "MedicineName2", "Description2", 200L, "Image");
		
		when(repository.findAll()).thenReturn(Stream.of( medicine1, medicine2).collect(Collectors.toList()));
		assertEquals(2, medicineService.getAllMedicines().size());
	}

	@Test
	void testFindMedicineByName() {
		Medicines medicine = new Medicines(1, "MedicineName", "Description", 200L, "Image");
		when(repository.findByName("MedicineName")).thenReturn(Optional.of(medicine));
	}

}
