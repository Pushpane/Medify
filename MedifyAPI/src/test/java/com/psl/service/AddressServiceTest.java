package com.psl.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.psl.dao.IAddressDAO;
import com.psl.dao.IStoreDAO;
import com.psl.dto.RegisterAddressRequest;
import com.psl.entity.Address;
import com.psl.entity.Role;
import com.psl.entity.Store;
import com.psl.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
class AddressServiceTest {
	
	@Autowired
	private AddressService addressService;
	
	@MockBean
	private IAddressDAO addressRepository;
	@Test
	void testRegisterAddress() {
		
//		RegisterAddressRequest addressRequest = new RegisterAddressRequest("email","name","address1","address2","pincode","city","state"); 
//		//fillAddress is private
//		Address address = addressService.fillAddress(addressRequest); 
//			//when(repository.save(address)).thenReturn(address);
//			//assertEquals(address, addressService.registerAddress(addressRequest));
//		addressService.registerAddress(addressRequest);
//		verify(repository, times(1)).save(address);
//		
		
	}

	@Test
	void testUpdateAddress() {
		Role roleId = new Role(1L,"role");
		User userId = new User(1L,"name","email","password",roleId,"phoneNumber",null,true);
		Store storeId = new Store(1L,userId,"name","description");
		Address address = new Address(1L,userId,storeId,"address1","address2","pincode","city","state");
	
		addressService.updateAddress(address);
		verify(addressRepository, times(1)).save(address);
	}

	@Test
	void testDeleteAddress() {
		addressService.deleteAddress(1L);
		verify(addressRepository, times(1)).deleteById(1L);
	}

	@Test
	void testGetAllAddress() {
		Role roleId1 = new Role(1L,"role");
		User userId1 = new User(1L,"name","email","password",roleId1,"phoneNumber",null,true);
		Store storeId1 = new Store(1L,userId1,"name","description");
		Address address1 = new Address(1L,userId1,storeId1,"address1","address2","pincode","city","state");
		
		Role roleId2 = new Role(2L,"role");
		User userId2 = new User(2L,"name","email","password",roleId2,"phoneNumber",null,true);
		Store storeId2 = new Store(2L,userId2,"name","description");
		Address address2 = new Address(2L,userId2,storeId2,"address1","address2","pincode","city","state");
		
		when(addressRepository.findAll()).thenReturn(Stream.of( address1, address2).collect(Collectors.toList()));
		assertEquals(2, addressService.getAllAddress().size());
	}
	@Before
	public void setUp() {
		Role roleId = new Role(1L,"role");
		User userId = new User(1L,"name","email","password",roleId,"phoneNumber",null,true);
		Store storeId = new Store(1L,userId,"name","description");
		Address address = new Address(1L,userId,storeId,"address1","address2","pincode","city","state");
		
		String city = "city";
		when(addressRepository.findByCity(city)).thenReturn(Stream.of(address).collect(Collectors.toList()));
		
	}
	@Test
	void testFindByCity() {
		 String city = "city";
		 List<Address> found = addressService.getByCity(city);
		 assertEquals(found.size(), addressService.getByCity(city).size());
	}

	@Test
	void testFindByPincode() {
		Role roleId = new Role(1L,"role");
		User userId = new User(1L,"name","email","password",roleId,"phoneNumber",null,true);
		Store storeId = new Store(1L,userId,"name","description");
		Address address = new Address(1L,userId,storeId,"address1","address2","pincode","city","state");
		
		String pincode = "pincode";
		when(addressRepository.findByPincode(pincode)).thenReturn(Stream.of(address).collect(Collectors.toList()));
		assertEquals(1, addressService.getByPincode(pincode).size());
	}

	@Test
	void testFindByStore() {
		Role roleId = new Role(1L,"role");
		User userId = new User(1L,"name","email","password",roleId,"phoneNumber",null,true);
		Store storeId = new Store(1L,userId,"name","description");
		Address address = new Address(1L,userId,storeId,"address1","address2","pincode","city","state");
		
		
		when(addressRepository.findByStoreId(storeId)).thenReturn(Stream.of(address).collect(Collectors.toList()));
		assertEquals(1, addressService.getByStore(storeId).size());	
		
	}

	@Test
	void testFindByUserAndStore() {
		Role roleId = new Role(1L,"role");
		User userId = new User(1L,"name","email","password",roleId,"phoneNumber",null,true);
		Store storeId = new Store(1L,userId,"name","description");
		Address address = new Address(1L,userId,storeId,"address1","address2","pincode","city","state");
		
		
		when(addressRepository.findByUserIdAndStoreId(userId,storeId)).thenReturn(Stream.of(address).collect(Collectors.toList()));
		assertEquals(1, addressService.getByUserAndStore(userId,storeId).size());
	}
	@Test
	void testGetAddressById() {
		Role roleId = new Role(1L,"role");
		User userId = new User(1L,"name","email","password",roleId,"phoneNumber",null,true);
		Store storeId = new Store(1L,userId,"name","description");
		Address address = new Address(1L,userId,storeId,"address1","address2","pincode","city","state");
		
		
		when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
		assertEquals(address, addressService.getAddressById(1L));
	}

}
