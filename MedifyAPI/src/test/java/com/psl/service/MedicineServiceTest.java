package com.psl.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.stream.Collectors;

import com.psl.dao.IMedicinesDAO;
import com.psl.dto.RegisterMedicineRequest;
import com.psl.entity.Medicine;
import com.psl.exception.MedifyException;



@RunWith(SpringRunner.class)
@SpringBootTest
class MedicineServiceTest {

	@Autowired
	private MedicineService medicineService;

	@MockBean
	private IMedicinesDAO repository;


//	@Test
//	void testRegisterMedicine() throws IOException {
//		
//		MockMultipartFile imageFile = new MockMultipartFile("image", "image1.jpeg", "image/jpeg", "image1.jpeg".getBytes());
//	       
//        String tokenExpected = "ab46fe80-aa7a-45d9-a83f-0ae4333d40f1";
//        UUID uuid = UUID.fromString(tokenExpected);
//        mockStatic(UUID.class);
//        when(UUID.randomUUID()).thenReturn(uuid);
//       
//        String image = "http://localhost/image/"+uuid.toString()+"image1.jpeg";
//       
//        Medicine medicine = new Medicine(0, "MedicineName1", "Description", 200L, image);
//        RegisterMedicineRequest request = new RegisterMedicineRequest("MedicineName1", "Description", 200.00, imageFile);
//		
//        when(repository.save(medicine)).thenReturn(medicine);
//		medicineService.registerMedicine(request);
//		verify(repository,times(1)).save(medicine);
//	}

	@Test
	void testUpdateMedicine() {
		Medicine medicine = new Medicine(1, "MedicineName", "Description", 200L, "Image");
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
		Medicine medicine1 = new Medicine(1, "MedicineName1", "Description1", 200L, "Image");
		Medicine medicine2 = new Medicine(2, "MedicineName2", "Description2", 200L, "Image");

		when(repository.findAll()).thenReturn(Stream.of( medicine1, medicine2).collect(Collectors.toList()));
		assertEquals(2, medicineService.getAllMedicines().size());
	}

	@Test
	void testFindMedicineByName() {
		Optional<Medicine> medicine = Optional.ofNullable(new Medicine(1, "MedicineName", "Description", 200L, "Image"));
		String medicineName="MedicineName";
		when(repository.findByName(medicineName)).thenReturn(medicine);
		assertEquals(medicine, medicineService.findMedicineByName(medicineName));
	}
	
	@Test
	void testFindMedicineById() {
		Medicine medicine = new Medicine(1, "MedicineName", "Description", 200L, "Image");
		repository.save(medicine);
		medicineService.findMedicineById(1);
		verify(repository, times(1)).findById(1L);
	}
	
	
	@Test
	void testRegisterMedicineJpegException(){
		Assertions.assertThrows(MedifyException.class, new Executable() {
			
			MockMultipartFile file 
		      = new MockMultipartFile(
		        "file", 
		        "hello.txt", 
		        MediaType.TEXT_PLAIN_VALUE, 
		        "Hello, World!".getBytes()
		      );   
        String tokenExpected = "ab46fe80-aa7a-45d9-a83f-0ae4333d40f1";
        UUID uuid = UUID.fromString(tokenExpected);
        
        String image = "http://localhost/image/"+uuid.toString()+"image1.jpeg";
       
        Medicine medicine = new Medicine(0, "MedicineName1", "Description", 200L, image);
        RegisterMedicineRequest request = new RegisterMedicineRequest("MedicineName1", "Description", 200.00, file);
		
        
		@Override
		public void execute() throws Throwable {
			mockStatic(UUID.class);
	        when(UUID.randomUUID()).thenReturn(uuid);
	       
			when(repository.save(medicine)).thenReturn(medicine);
			medicineService.registerMedicine(request);
			verify(repository,times(1)).save(medicine);
			
		}});
	}
//	@Test
//	void testRegisterMedicineFileEmptyException(){
//		Assertions.assertThrows(NullPointerException.class, new Executable() {
//			
//			MockMultipartFile file = new MockMultipartFile("image", "image1.jpeg", "image/jpeg", "".getBytes());
//        String tokenExpected = "ab46fe80-aa7a-45d9-a83f-0ae4333d40f1";
//        UUID uuid = UUID.fromString(tokenExpected);
//        
//        String image = "http://localhost/image/"+uuid.toString()+"image1.jpeg";
//       
//        Medicine medicine = new Medicine(0, "MedicineName1", "Description", 200L, image);
//        RegisterMedicineRequest request = new RegisterMedicineRequest("MedicineName1", "Description", 200.00, file);
//		
//        
//		@Override
//		public void execute() throws Throwable {
//			mockStatic(UUID.class);
//	        when(UUID.randomUUID()).thenReturn(uuid);
//	       
//			when(repository.save(medicine)).thenReturn(medicine);
//			medicineService.registerMedicine(request);
//			verify(repository,times(1)).save(medicine);
//			
//		}});
//	}

}
