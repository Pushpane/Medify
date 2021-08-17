package com.psl.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.psl.dao.IMedicineToStoreDAO;
import com.psl.dto.RegisterMedicineToStoreRequest;
import com.psl.entity.Medicine;
import com.psl.entity.MedicineToStore;
import com.psl.entity.Role;
import com.psl.entity.Store;
import com.psl.entity.User;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = MedicineToStoreService.class, loader = AnnotationConfigContextLoader.class)

class MedicineToStoreServiceTest {
	
	@Autowired
	private MedicineToStoreService medicineToStoreService;
	
	@MockBean
	private IMedicineToStoreDAO repository;
	@MockBean
	MedicineService medicineService;
	@MockBean
	StoreService storeService;

	@Test
	void testRegisterMedicineToStore() {
		Role role = new Role(2L, "Owner");
		User user = new User(2L, "UserName", "UserName@email.com", "Password", role, "1234567890", null, true);
		Store store = new Store(1L, user, "StoreName1", "StoreDescription");
		Medicine medicine = new Medicine(1L, "MedicineName1", "Description", 200L, "Image");
		MedicineToStore medicineToStore = new MedicineToStore(0L, medicine, store, true);
		
		RegisterMedicineToStoreRequest request = new RegisterMedicineToStoreRequest(1L, 1L);
		when(repository.save(medicineToStore)).thenReturn(medicineToStore);
		when(medicineService.findMedicineById(request.getMedicineId())).thenReturn(Optional.of(medicine));
		when(storeService.getStoreById(request.getStoreId())).thenReturn(store);
		medicineToStoreService.registerMedicineToStore(request);
		verify(repository, times(1)).save(medicineToStore);
	}

	@Test
	void testDeleteMedicineToStore() {
		medicineToStoreService.deleteMedicineToStore(1L);
		verify(repository, times(1)).deleteById(1L);
	}

	@Test
	void testGetMedicinesByStore() {
		Role role = new Role(2L, "Owner");
		User user = new User(2L, "UserName", "UserName@email.com", "Password", role, "1234567890", null, true);
		Store store = new Store(1L, user, "StoreName", "StoreDescription");
		Medicine medicine = new Medicine(1L, "MedicineName", "Description", 200L, "Image");
		MedicineToStore medicineToStore =new MedicineToStore(1L, medicine, store, true);
		
		when(repository.save(medicineToStore)).thenReturn(medicineToStore);
		when(medicineService.findMedicineByName("MedicineName")).thenReturn(Optional.of(medicine));
		when(storeService.findStoreByName("StoreName")).thenReturn(Optional.of(store));
		when(repository.findByStoreId(store)).thenReturn(Stream.of(medicineToStore).collect(Collectors.toList()));
		when(medicineToStoreService.getMedicinesByStore(store)).thenReturn(Stream.of(medicineToStore).collect(Collectors.toList()));
		assertEquals(1, medicineToStoreService.getMedicinesByStore(store).size());
	}

	@Test
	void testGetStoreByMedicine() {
		Role role = new Role(2L, "Owner");
		User user = new User(2L, "UserName", "UserName@email.com", "Password", role, "1234567890", null, true);
		Store store = new Store(1L, user, "StoreName", "StoreDescription");
		Medicine medicine = new Medicine(1L, "MedicineName", "Description", 200L, "Image");
		MedicineToStore medicineToStore =new MedicineToStore(1L, medicine, store, true);
		when(repository.save(medicineToStore)).thenReturn(medicineToStore);
		when(medicineService.findMedicineByName("MedicineName")).thenReturn(Optional.of(medicine));
		when(storeService.findStoreByName("StoreName")).thenReturn(Optional.of(store));
		when(repository.findByMedicineId(medicine)).thenReturn(Stream.of(medicineToStore).collect(Collectors.toList()));
		assertEquals(1, medicineToStoreService.getStoreByMedicine("MedicineName").size());
		
	}

	@Test
	void testGetAllMedicine() {
		Role role1 = new Role(1L, "Owner");
		User user1 = new User(1L, "UserName", "UserName@email.com", "Password", role1, "1234567890", null, true);
		Store store1 = new Store(1L, user1, "StoreName", "StoreDescription");
		Medicine medicine1 = new Medicine(1L, "MedicineName", "Description", 200L, "Image");
		MedicineToStore medicineToStore1 = new MedicineToStore(1L, medicine1, store1, true);
		
		Role role2 = new Role(1L, "Owner");
		User user2 = new User(1L, "UserName", "UserName@email.com", "Password", role2, "1234567890", null, true);
		Store store2 = new Store(1L, user2, "StoreName", "StoreDescription");
		Medicine medicine2 = new Medicine(1L, "MedicineName", "Description", 200L, "Image");
		MedicineToStore medicineToStore2 = new MedicineToStore(1L, medicine2, store2, true);
		
		when(repository.findAll()).thenReturn(Stream.of(medicineToStore1, medicineToStore2).collect(Collectors.toList()));
		assertEquals(2, medicineToStoreService.getAllMedicine().size());
	}

	@Test
	void testGetMedicinesToStoreById() {
		Role role = new Role(1L, "Owner");
		User user = new User(1L, "UserName", "UserName@email.com", "Password", role, "1234567890", null, true);
		Store store = new Store(1L, user, "StoreName", "StoreDescription");
		Medicine medicine = new Medicine(1L, "MedicineName", "Description", 200L, "Image");
		Optional<MedicineToStore> medicineToStore = Optional.ofNullable(new MedicineToStore(1L, medicine, store, true));
		
		when(repository.findById(1L)).thenReturn(medicineToStore);
		assertEquals(medicineToStore, medicineToStoreService.getMedicinesToStoreById(1L));
	}


}
