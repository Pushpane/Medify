package com.psl.service;

import static org.junit.Assert.assertEquals;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import com.psl.dao.IStoreDAO;
import com.psl.dto.RegisterStoreRequest;
import com.psl.entity.Role;
import com.psl.entity.Store;
import com.psl.entity.User;
import com.psl.exception.MedifyException;

@RunWith(SpringRunner.class)
@SpringBootTest
class StoreServiceTest {
	
	@Autowired
	private StoreService storeService;
	
	@MockBean
	private IStoreDAO repository;
	@MockBean
	private UserService userService;
	
	@Test
	public void registerStoreTest() {
		Role role = new Role(2L, "Owner");
		User user = new User(2L, "UserName", "UserName1@email.com", "Password", role, "1234567890", null, true);
		Store store = new Store(0L, user, "StoreName", "StoreDescription");
		
		RegisterStoreRequest request = new RegisterStoreRequest("UserName1@email.com", "StoreName", "StoreDescription");
		when(repository.save(store)).thenReturn(store);
		when(userService.getUser(request.getEmail())).thenReturn(Optional.of(user));

		//Assertions.assertThrows(NoSuchElementException.class, () -> storeService.registerStore(request));
		storeService.registerStore(request);
		verify(repository,times(1)).save(store);
	}
	
	@Test
	public void findStoreByNameTest() {
		Role role1 = new Role(1L, "Owner");
		User user1 = new User(1L, "UserName1", "UserName1@email.com", "Password", role1, "1234567890", null, true);
		Optional<Store> store1 = Optional.ofNullable(new Store(1L, user1, "StoreName1", "StoreDescription"));
		
		String storeName = "StoreName1";
		when(repository.findByName(storeName)).thenReturn((store1));
		//when(repository.findByName(storeName)).thenReturn(Optional.ofNullable(Stream.of( store1, store2).collect(Collector<Store, A, R>)));
		//storeService.findStoreByName(storeName);
		//verify(repository, times(1)).findByName(storeName);
		assertEquals(store1, storeService.findStoreByName(storeName));
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
		assertEquals(store, storeService.getStoreById(1L));
	}
	
	@Test
	public void getAllStoresTest() {
		Role role1 = new Role(1L, "Owner");
		User user1 = new User(1L, "UserName1", "UserName1@email.com", "Password", role1, "1234567890", null, true);
		Store store1 = new Store(1L, user1, "StoreName1", "StoreDescription");
		
		Role role2 = new Role(2L, "Owner");
		User user2 = new User(2L, "UserName2", "UserName2@email.com", "Password", role2, "1234567890", null, true);
		Store store2 = new Store(2L, user2, "StoreName2", "StoreDescription");
		
		when(repository.findAll()).thenReturn(Stream.of( store1, store2).collect(Collectors.toList()));
		assertEquals(2, storeService.getAllStores().size());
	}
	
	@Test
	public void findStoreByUserTest() {
		Role role = new Role(1L, "Owner");
		User user = new User(1L, "UserName", "UserName@email.com", "Password", role, "1234567890", null, true);
		Store store1 = new Store(1L, user, "StoreName", "StoreDescription");
		Store store2 = new Store(2L, user, "StoreName", "StoreDescription");
		
		when(userService.getUser(user.getEmail())).thenReturn(Optional.of(user));
		when(storeService.findStoreByUser(user.getEmail())).thenReturn(Stream.of(store1,store2).collect(Collectors.toList()));
		assertEquals(2,storeService.findStoreByUser(user.getEmail()).size());
	}

	@Test
	public void findStoreByUserException() {
		Assertions.assertThrows(MedifyException.class, new Executable() {
			Role role = new Role(1L, "Owner");
			User user = new User(1L, "UserName", "UserName@email.com", "Password", role, "1234567890", null, true);
			Store store1 = new Store(1L, user, "StoreName", "StoreDescription");
			Store store2 = new Store(2L, user, "StoreName", "StoreDescription");
			
			
			@Override
			public void execute() throws Throwable {
				when(userService.getUser(user.getEmail())).thenReturn(Optional.of(user));
				when(storeService.findStoreByUser(user.getEmail())).thenReturn(Stream.of(store1,store2).collect(Collectors.toList()));
				assertEquals(2,storeService.findStoreByUser("username1@email.com").size());
				
			}});
	}
}
