package com.psl.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Clock;
import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.time.ZoneId;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.psl.dao.IOrderDAO;
import com.psl.dao.IStoreDAO;
import com.psl.dto.OrderRequest;
import com.psl.entity.Address;
import com.psl.entity.Medicine;
import com.psl.entity.MedicineToStore;
import com.psl.entity.Orders;
import com.psl.entity.Role;
import com.psl.entity.Store;
import com.psl.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
class OrderServiceTest {
	@Autowired
	private OrderService orderService;
	
	@MockBean
	private IOrderDAO orderRepository;
	@MockBean
	private UserService userService;
	@MockBean
	private StoreService storeService;
	@MockBean
	private AddressService addressService;
	@MockBean
	private MedicineService medicineService;
	@MockBean
	private MedicineToStoreService medicineToStoreService;
	
	
	
	private Instant instant;
	
	@Test
	void testRegisterOrder() {

		String instantExpected = "2014-12-22T10:15:30Z"; 
		Clock clock = Clock.fixed(Instant.parse(instantExpected), ZoneId.of("UTC")); 
		Instant instant = Instant.now(clock); 
		mockStatic(Instant.class); 
		when(Instant.now()).thenReturn(instant);
		
		Role roleId = new Role(2L, "Owner");
		User userId = new User(2L, "UserName", "UserName1@email.com", "Password", roleId, "1234567890", null, true);
		Store storeId = new Store(1L, userId, "StoreName", "StoreDescription");
		
		Address addressId = new Address(1L,userId,storeId,"address1","address2","pincode","city","state");
		Medicine medicineId = new Medicine(1L,"MedicineName","Description",200.00,"Image");
		MedicineToStore medicineToStoreId = new MedicineToStore(1L,medicineId,storeId,true);
		
		Orders order = new Orders(0L,userId,addressId,medicineToStoreId,10,"orderStatus",instant);
		OrderRequest orderRequest = new OrderRequest(1L,10,"orderStatus","UserName1@email.com",1L);
		
		when(orderRepository.save(order)).thenReturn(order);
		when(userService.getUser(orderRequest.getEmail())).thenReturn(Optional.of(userId));
		when(addressService.getAddressById(orderRequest.getAddressId())).thenReturn(addressId);
		when(medicineToStoreService.getMedicinesToStoreById(orderRequest.getMedicineToStoreId())).thenReturn(Optional.of(medicineToStoreId));
		
		orderService.registerOrder(orderRequest);
		verify(orderRepository,times(1)).save(order);
	}

	@Test
	void testSaveOrder() {
		Role roleId = new Role(1L,"role");
		User userId = new User(1L,"name","email","password",roleId,"phoneNumber",null,true);
		Store storeId = new Store(1L,userId,"name","description");
		Address addressId = new Address(1L,userId,storeId,"address1","address2","pincode","city","state");
		Medicine medicineId = new Medicine(1L,"medicineName","description",100.00,"image");
		MedicineToStore medicineToStoreId = new MedicineToStore(1L,medicineId,storeId,true);
		
		Orders order = new Orders(1L,userId,addressId,medicineToStoreId,10,"orderStatus",null);
		
		orderService.saveOrder(order);
		verify(orderRepository, times(1)).save(order);	
		
	}

	@Test
	void testDeleteOrder() {
		orderService.deleteOrder(1L);
		verify(orderRepository, times(1)).deleteById(1L);
	}

	@Test
	void testGetAllOrders() {
		Role roleId = new Role(1L,"role");
		User userId = new User(1L,"name","email","password",roleId,"phoneNumber",null,true);
		Store storeId = new Store(1L,userId,"name","description");
		Address addressId = new Address(1L,userId,storeId,"address1","address2","pincode","city","state");
		Medicine medicineId = new Medicine(1L,"medicineName","description",100.00,"image");
		MedicineToStore medicineToStoreId = new MedicineToStore(1L,medicineId,storeId,true);
		
		Orders order1 = new Orders(1L,userId,addressId,medicineToStoreId,10,"orderStatus",null);
		
		Orders order2 = new Orders(2L,userId,addressId,medicineToStoreId,10,"orderStatus",null);
		
		when(orderRepository.findAll()).thenReturn(Stream.of( order1, order2).collect(Collectors.toList()));
		assertEquals(2, orderService.getAllOrders().size());
	}

	@Test
	void testGetAllOrdersByUser() {
		Role roleId = new Role(2L, "Owner");
		User userId = new User(2L, "UserName", "UserName@email.com", "Password", roleId, "1234567890", null, true);
		Store storeId = new Store(0L, userId, "StoreName", "StoreDescription");
		
		Address addressId = new Address(1L,userId,storeId,"address1","address2","pincode","city","state");
		Medicine medicineId = new Medicine(1L,"medicineName","description",100.00,"image");
		MedicineToStore medicineToStoreId = new MedicineToStore(1L,medicineId,storeId,true);
		
		Orders order = new Orders(1L,userId,addressId,medicineToStoreId,10,"orderStatus",null);
		
		when(orderRepository.findAllByUserId(userId)).thenReturn(Stream.of( order).collect(Collectors.toList()));
		assertEquals(1, orderService.getAllOrdersByUser(2L).size());
	}

}
