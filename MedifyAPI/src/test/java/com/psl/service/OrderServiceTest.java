package com.psl.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.time.ZoneId;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.psl.dao.IOrdersDAO;
import com.psl.dao.IStoreDAO;
import com.psl.dto.AnalyticsResponse;
import com.psl.dto.OrderRequest;
import com.psl.entity.Address;
import com.psl.entity.Medicine;
import com.psl.entity.MedicineToStore;
import com.psl.entity.Orders;
import com.psl.entity.Role;
import com.psl.entity.Store;
import com.psl.entity.User;
import com.psl.exception.MedifyException;

import io.jsonwebtoken.lang.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
class OrderServiceTest {
	@Autowired
	private OrderService orderService;

	@MockBean
	private IOrdersDAO orderRepository;
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

		Orders order = new Orders(0L,userId,addressId,medicineToStoreId,10,"orderStatus",instant,new BigDecimal("100"));
		OrderRequest orderRequest = new OrderRequest(1L,10,"orderStatus","UserName1@email.com",1L,new BigDecimal("100"));
		
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

		Orders order = new Orders(1L,userId,addressId,medicineToStoreId,10,"orderStatus",null,new BigDecimal("100"));

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

		Orders order1 = new Orders(1L,userId,addressId,medicineToStoreId,10,"orderStatus",null,new BigDecimal("100"));

		Orders order2 = new Orders(2L,userId,addressId,medicineToStoreId,10,"orderStatus",null,new BigDecimal("100"));

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

		Orders order = new Orders(1L,userId,addressId,medicineToStoreId,10,"orderStatus",null,new BigDecimal("100"));
		when(orderRepository.save(order)).thenReturn(order);
		when(userService.getUser(userId.getEmail())).thenReturn(Optional.of(userId));

		when(orderRepository.findAllByUserId(userId)).thenReturn(Stream.of( order).collect(Collectors.toList()));
		assertEquals(1, orderService.getAllOrdersByUser("UserName@email.com").size());
	}

	@Test()
	void testGetAllOrdersByUserException() {
		Assertions.assertThrows(MedifyException.class, new Executable() {
			Role roleId = new Role(2L, "Owner");
			User userId = new User(2L, "UserName", "UserName1@email.com", "Password", roleId, "1234567890", null, true);
			Store storeId = new Store(0L, userId, "StoreName", "StoreDescription");

<<<<<<< Updated upstream
		@Override
		public void execute() throws Throwable {
			when(orderRepository.save(order)).thenReturn(order);
			when(userService.getUser(userId.getEmail())).thenReturn(Optional.of(userId));
			
			when(orderRepository.findAllByUserId(userId)).thenReturn(Stream.of( order).collect(Collectors.toList()));
			assertEquals(1, orderService.getAllOrdersByUser("UserName@email.com").size());
			
		}});
		
		
=======
			Address addressId = new Address(1L,userId,storeId,"address1","address2","pincode","city","state");
			Medicine medicineId = new Medicine(1L,"medicineName","description",100.00,"image");
			MedicineToStore medicineToStoreId = new MedicineToStore(1L,medicineId,storeId,true);

			Orders order = new Orders(1L,userId,addressId,medicineToStoreId,10,"orderStatus",null,new BigDecimal("100"));

			@Override
			public void execute() throws Throwable {
				when(orderRepository.save(order)).thenReturn(order);
				when(userService.getUser(userId.getEmail())).thenReturn(Optional.of(userId));

				when(orderRepository.findAllByUserId(userId)).thenReturn(Stream.of( order).collect(Collectors.toList()));
				assertEquals(1, orderService.getAllOrdersByUser("UserName@email.com").size());

			}
		});
>>>>>>> Stashed changes
	}

	@Test
	void testGetOrdersReceived() {
		Role roleId = new Role(2L, "Owner");
		User userId = new User(2L, "UserName", "UserName@email.com", "Password", roleId, "1234567890", null, true);
		Store storeId = new Store(0L, userId, "StoreName", "StoreDescription");

		Address addressId = new Address(1L,userId,storeId,"address1","address2","pincode","city","state");
		Medicine medicineId = new Medicine(1L,"medicineName","description",100.00,"image");
		MedicineToStore medicineToStoreId = new MedicineToStore(1L,medicineId,storeId,true);

		Orders order = new Orders(1L,userId,addressId,medicineToStoreId,10,"orderStatus",null,new BigDecimal("100"));

		when(orderRepository.save(order)).thenReturn(order);
		when(storeService.findStoreByUser(userId.getEmail())).thenReturn(Stream.of(storeId).collect(Collectors.toList()));
		when(medicineToStoreService.getMedicinesByStore(storeId)).thenReturn(Stream.of(medicineToStoreId).collect(Collectors.toList()));
		when((orderRepository.findAllByMedicineToStoreId(medicineToStoreId))).thenReturn(Stream.of(order).collect(Collectors.toList()));

		List<Orders> orders = new ArrayList<>();
		orders.add(order);

		assertEquals(orders, orderService.getOrdersReceived(userId.getEmail()));
	}

	//	@Test
	//	void testGetAnalytics() {
	//		Role roleId = new Role(2L, "Owner");
	//		User userId = new User(2L, "UserName", "UserName@email.com", "Password", roleId, "1234567890", null, true);
	//		Store storeId = new Store(0L, userId, "StoreName", "StoreDescription");
	//		
	//		Address addressId = new Address(1L,userId,storeId,"address1","address2","pincode","city","state");
	//		Medicine medicineId = new Medicine(1L,"medicineName","description",100.00,"image");
	//		MedicineToStore medicineToStoreId = new MedicineToStore(1L,medicineId,storeId,true);
	//		
	//		Orders order = new Orders(1L,userId,addressId,medicineToStoreId,10,"orderStatus",null,new BigDecimal("100"));
	//		
	//		when(orderRepository.save(order)).thenReturn(order);
	//		when(storeService.findStoreByUser(userId.getEmail())).thenReturn(Stream.of(storeId).collect(Collectors.toList()));
	//		when(medicineToStoreService.getMedicinesByStore(storeId)).thenReturn(Stream.of(medicineToStoreId).collect(Collectors.toList()));
	//		when((orderRepository.findAllByMedicineToStoreId(medicineToStoreId))).thenReturn(Stream.of(order).collect(Collectors.toList()));
	//		
	//		List<Orders> orders = new ArrayList<>();
	//		orders.add(order);
	//		
	//		AnalyticsResponse analyticsResponse = new AnalyticsResponse(10L, 300.00, 8L, 1L, 2021, "07", 20);
	//		
	//		List<AnalyticsResponse> result = new ArrayList<AnalyticsResponse>();
	//		result.add(analyticsResponse);
	//		//Collections.reverse(result);
	//		
	//		assertEquals(result, orderService.getAnalytics(userId.getEmail()));
	//	}

	@Test
	void testChangeStatus() {
		Role roleId = new Role(2L, "Owner");
		User userId = new User(2L, "UserName", "UserName@email.com", "Password", roleId, "1234567890", null, true);
		Store storeId = new Store(0L, userId, "StoreName", "StoreDescription");

		Address addressId = new Address(1L,userId,storeId,"address1","address2","pincode","city","state");
		Medicine medicineId = new Medicine(1L,"medicineName","description",100.00,"image");
		MedicineToStore medicineToStoreId = new MedicineToStore(1L,medicineId,storeId,true);

		Orders order = new Orders(1L,userId,addressId,medicineToStoreId,10,"orderStatus",null,new BigDecimal("100"));

		when(orderRepository.findById(order.getOrderId())).thenReturn(Optional.of(order));
		order.setOrderStatus("Packed");
		assertEquals(order, orderService.changeStatus(order.getOrderId()));
	}

	@Test
	void testChangeStatusException() {
		Assertions.assertThrows(MedifyException.class, new Executable() {
			Role roleId = new Role(2L, "Owner");
			User userId = new User(2L, "UserName", "UserName@email.com", "Password", roleId, "1234567890", null, true);
			Store storeId = new Store(0L, userId, "StoreName", "StoreDescription");

			Address addressId = new Address(1L,userId,storeId,"address1","address2","pincode","city","state");
			Medicine medicineId = new Medicine(1L,"medicineName","description",100.00,"image");
			MedicineToStore medicineToStoreId = new MedicineToStore(1L,medicineId,storeId,true);

			Orders order = new Orders(1L,userId,addressId,medicineToStoreId,10,"orderStatus",null,new BigDecimal("100"));
			@Override
			public void execute() throws Throwable {
				when(orderRepository.findById(order.getOrderId())).thenReturn(Optional.of(order));
				order.setOrderStatus("Packed");
				assertEquals(order, orderService.changeStatus(2L));
			}
		});
	}

	@Test
	void testChangeStatusDelivered() {
		Role roleId = new Role(2L, "Owner");
		User userId = new User(2L, "UserName", "UserName@email.com", "Password", roleId, "1234567890", null, true);
		Store storeId = new Store(0L, userId, "StoreName", "StoreDescription");

		Address addressId = new Address(1L,userId,storeId,"address1","address2","pincode","city","state");
		Medicine medicineId = new Medicine(1L,"medicineName","description",100.00,"image");
		MedicineToStore medicineToStoreId = new MedicineToStore(1L,medicineId,storeId,true);

		Orders order = new Orders(1L,userId,addressId,medicineToStoreId,10,"orderStatus",null,new BigDecimal("100"));

		when(orderRepository.findById(order.getOrderId())).thenReturn(Optional.of(order));
		order.setOrderStatus("Delivered");
		assertEquals(order, orderService.changeStatusDelivered(order.getOrderId()));
	}

	@Test
	void testDeclineOrder() {
		Role roleId = new Role(2L, "Owner");
		User userId = new User(2L, "UserName", "UserName@email.com", "Password", roleId, "1234567890", null, true);
		Store storeId = new Store(0L, userId, "StoreName", "StoreDescription");

		Address addressId = new Address(1L,userId,storeId,"address1","address2","pincode","city","state");
		Medicine medicineId = new Medicine(1L,"medicineName","description",100.00,"image");
		MedicineToStore medicineToStoreId = new MedicineToStore(1L,medicineId,storeId,true);

		Orders order = new Orders(1L,userId,addressId,medicineToStoreId,10,"orderStatus",null,new BigDecimal("100"));

		when(orderRepository.findById(order.getOrderId())).thenReturn(Optional.of(order));
		order.setOrderStatus("Declined");
		assertEquals(order, orderService.declineOrder(order.getOrderId()));
	}

	@Test
	void testCancelOrder() {
		Role roleId = new Role(2L, "Owner");
		User userId = new User(2L, "UserName", "UserName@email.com", "Password", roleId, "1234567890", null, true);
		Store storeId = new Store(0L, userId, "StoreName", "StoreDescription");

		Address addressId = new Address(1L,userId,storeId,"address1","address2","pincode","city","state");
		Medicine medicineId = new Medicine(1L,"medicineName","description",100.00,"image");
		MedicineToStore medicineToStoreId = new MedicineToStore(1L,medicineId,storeId,true);

		Orders order = new Orders(1L,userId,addressId,medicineToStoreId,10,"orderStatus",null,new BigDecimal("100"));

		when(orderRepository.findById(order.getOrderId())).thenReturn(Optional.of(order));
		order.setOrderStatus("Cancelled");
		assertEquals(order, orderService.cancelOrder(order.getOrderId()));
	}



}
