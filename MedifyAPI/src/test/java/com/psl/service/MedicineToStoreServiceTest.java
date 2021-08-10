package com.psl.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.psl.dao.IMedicineToStoreDAO;

class MedicineToStoreServiceTest {
	
	@Autowired
	private MedicineToStoreService medicineToStoreService;
	
	@MockBean
	private IMedicineToStoreDAO repository;

//	@Test
//	void testRegisterMedicineToStore() {
//		fail("Not yet implemented");
//	}

	@Test
	void testDeleteMedicineToStore() {
		medicineToStoreService.deleteMedicineToStore(1L);
		verify(repository, times(1)).deleteById(1L);
	}

	@Test
	void testGetMedicinesByStore() {
		fail("Not yet implemented");
	}

	@Test
	void testGetStoreByMedicine() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAllMedicine() {
		fail("Not yet implemented");
	}

	@Test
	void testGetMedicinesToStoreById() {
		fail("Not yet implemented");
	}

	@Test
	void testMedicineToStoreService() {
		fail("Not yet implemented");
	}

}
