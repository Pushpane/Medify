package com.psl.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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

import com.psl.dao.IStoreDAO;
import com.psl.entity.Role;
import com.psl.entity.Store;
import com.psl.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
class StoreServiceTest {
	
	@Autowired
	private StoreService storeService;
	
	@MockBean
	private IStoreDAO repository;
	
	@Test
	public void registerStoreTest() {
		
	}
	
	@Test
	public void findStoreByNameTest() {
		Role role = new Role(1L, "Owner");
		User user = new User(1L, "UserName", "UserName@email.com", "Password", role, "1234567890", null, true);
		Store store = new Store(1L, user, "StoreName", "StoreDescription");
		String storeName = "StoreName";
		when(repository.findByName(storeName)).thenReturn(Optional.of(store));
	}

	@Test
	public void updateStoreTest() {
		Role role = new Role(1L, "Owner");
		User user = new User(1L, "UserName", "UserName@email.com", "Password", role, "1234567890", null, true);
		Store store = new Store(1L, user, "StoreName", "StoreDescription");
		
//		when(repository.save(store)).thenReturn(store);
//		assertEquals(store, storeService.updateStore(store));
		storeService.updateStore(store);
		verify(repository, times(1)).save(store);
	}

	@Test
	public void deleteStoreTest() {
		storeService.deleteStore(1L);
		verify(repository, times(1)).deleteById(1L);
	}
	
	@Test
	public void getStoreByIdTest() {
		Role role = new Role(1L, "Owner");
		User user = new User(1L, "UserName", "UserName@email.com", "Password", role, "1234567890", null, true);
		Store store = new Store(1L, user, "StoreName", "StoreDescription");
		when(repository.findById(1L)).thenReturn(Optional.of(store));
	}
	
	@Test
	public void getAllStoresTest() {
		Role role1 = new Role(1L, "Owner");
		User user1 = new User(1L, "UserName1", "UserName1@email.com", "Password", role1, "1234567890", null, true);
		Store store1 = new Store(1L, user1, "StoreName1", "StoreDescription");
		
		Role role2 = new Role(1L, "Owner");
		User user2 = new User(1L, "UserName2", "UserName2@email.com", "Password", role2, "1234567890", null, true);
		Store store2 = new Store(1L, user2, "StoreName2", "StoreDescription");
		
		when(repository.findAll()).thenReturn(Stream.of( store1, store2).collect(Collectors.toList()));
		assertEquals(2, storeService.getAllStores().size());
	}

}
