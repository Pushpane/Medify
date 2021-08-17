package com.psl.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.psl.dao.ICartDAO;
import com.psl.dto.CartRequest;
import com.psl.dto.OrderRequest;
import com.psl.entity.Address;
import com.psl.entity.Cart;
import com.psl.entity.Medicine;
import com.psl.entity.MedicineToStore;
import com.psl.entity.Role;
import com.psl.entity.Store;
import com.psl.entity.User;
import com.psl.exception.MedifyException;

@RunWith(SpringRunner.class)
@SpringBootTest
class CartServiceTest {

	@Autowired
	private CartService cartService;

	@MockBean
	private ICartDAO cartRepository;
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
	@MockBean
	private OrderService orderService;



	@Test
	void testRegisterCart() {
		Role roleId = new Role(2L, "Owner");
		User userId = new User(2L, "UserName", "UserName1@email.com", "Password", roleId, "1234567890", null, true);
		Store storeId = new Store(1L, userId, "StoreName", "StoreDescription");

		Medicine medicineId = new Medicine(1L,"MedicineName","Description",200,"Image");
		MedicineToStore medicineToStoreId = new MedicineToStore(1L,medicineId,storeId,true);
		Cart cart = new Cart(0L,userId,medicineToStoreId,1,new BigDecimal("200.0"));
		CartRequest request = new CartRequest("UserName@email.com",0L,1);

		when(userService.getUser(request.getEmail())).thenReturn(Optional.of(userId));
		when(medicineToStoreService.getMedicinesToStoreById(request.getId())).thenReturn(Optional.of(medicineToStoreId));

		cartService.registerCart(request);
		verify(cartRepository,times(1)).save(cart);
	}

	@Test
	void testDeleteCart() {
		Role roleId = new Role(2L, "Owner");
		User userId = new User(2L, "UserName", "UserName1@email.com", "Password", roleId, "1234567890", null, true);
		Store storeId = new Store(1L, userId, "StoreName", "StoreDescription");

		Medicine medicineId = new Medicine(1L,"MedicineName","Description",200.00,"Image");
		MedicineToStore medicineToStoreId = new MedicineToStore(1L,medicineId,storeId,true);
		Cart cart = new Cart(1L,userId,medicineToStoreId,10,new BigDecimal(100));

		when(cartRepository.existsById(cart.getCartId())).thenReturn(true);
		cartRepository.save(cart);
		cartService.deleteCart(1L);
		verify(cartRepository, times(1)).deleteById(1L);
	}

	@Test
	void testDeleteCartCartIdInvalidException() {
		Assertions.assertThrows(MedifyException.class, new Executable() {
			Role roleId = new Role(2L, "Owner");
			User userId = new User(2L, "UserName", "UserName1@email.com", "Password", roleId, "1234567890", null, true);
			Store storeId = new Store(1L, userId, "StoreName", "StoreDescription");

			Medicine medicineId = new Medicine(1L,"MedicineName","Description",200.00,"Image");
			MedicineToStore medicineToStoreId = new MedicineToStore(1L,medicineId,storeId,true);
			Cart cart = new Cart(1L,userId,medicineToStoreId,10,new BigDecimal(100));
			@Override
			public void execute() throws Throwable {
				when(cartRepository.existsById(2L)).thenReturn(true);
				cartRepository.save(cart);
				cartService.deleteCart(1L);
				verify(cartRepository, times(1)).deleteById(1L);
			}
		});
	}

	@Test
	void testUpdateCart() {
		Role roleId = new Role(2L, "Owner");
		User userId = new User(2L, "UserName", "UserName1@email.com", "Password", roleId, "1234567890", null, true);
		Store storeId = new Store(1L, userId, "StoreName", "StoreDescription");

		Medicine medicineId = new Medicine(1L,"MedicineName","Description",200.00,"Image");
		MedicineToStore medicineToStoreId = new MedicineToStore(1L,medicineId,storeId,true);

		Cart cart = new Cart(1L,userId,medicineToStoreId,10,new BigDecimal(100));
		CartRequest request = new CartRequest("UserName@email.com",0L,1);


		when(medicineToStoreService.getMedicinesToStoreById(request.getId())).thenReturn(Optional.of(medicineToStoreId));
		when(userService.getUser(request.getEmail())).thenReturn(Optional.of(userId));
		when(cartRepository.save(cart)).thenReturn(cart);
		cartService.updateCart(cart);
		verify(cartRepository,times(1)).save(cart);

	}

	@Test
	void testGetAllCarts() {
		Role roleId = new Role(2L, "Owner");
		User userId = new User(2L, "UserName", "UserName1@email.com", "Password", roleId, "1234567890", null, true);
		Store storeId = new Store(1L, userId, "StoreName", "StoreDescription");

		Medicine medicineId = new Medicine(1L,"MedicineName","Description",200.00,"Image");
		MedicineToStore medicineToStoreId = new MedicineToStore(1L,medicineId,storeId,true);

		Cart cart1 = new Cart(1L,userId,medicineToStoreId,10,new BigDecimal(100));
		Cart cart2 = new Cart(2L,userId,medicineToStoreId,5,new BigDecimal(100));


		when(cartRepository.findAll()).thenReturn(Stream.of( cart1, cart2).collect(Collectors.toList()));
		assertEquals(2, cartService.getAllCarts().size());
	}

	@Test
	void testGetCartByUser() {
		Role roleId = new Role(2L, "Owner");
		User userId = new User(2L, "UserName", "UserName1@email.com", "Password", roleId, "1234567890", null, true);
		Store storeId = new Store(1L, userId, "StoreName", "StoreDescription");

		Medicine medicineId = new Medicine(1L,"MedicineName","Description",200.00,"Image");
		MedicineToStore medicineToStoreId = new MedicineToStore(1L,medicineId,storeId,true);

		Cart cart1 = new Cart(1L,userId,medicineToStoreId,10,new BigDecimal(100));
		Cart cart2 = new Cart(2L,userId,medicineToStoreId,5,new BigDecimal(100));

		when(userService.getUser(userId.getEmail())).thenReturn(Optional.of(userId));

		when(cartRepository.findByUserId(userId)).thenReturn(Stream.of(cart1,cart2).collect(Collectors.toList()));
		assertEquals(2, cartService.getCartByUser(userId.getEmail()).size());
	}

	@Test
	void testGetCartByUserUserNotFoundException() {
		Assertions.assertThrows(MedifyException.class, new Executable() {
			Role roleId = new Role(2L, "Owner");
			User userId = new User(2L, "UserName", "UserName1@email.com", "Password", roleId, "1234567890", null, true);
			Store storeId = new Store(1L, userId, "StoreName", "StoreDescription");

			Medicine medicineId = new Medicine(1L,"MedicineName","Description",200.00,"Image");
			MedicineToStore medicineToStoreId = new MedicineToStore(1L,medicineId,storeId,true);

			Cart cart1 = new Cart(1L,userId,medicineToStoreId,10,new BigDecimal(100));
			Cart cart2 = new Cart(2L,userId,medicineToStoreId,5,new BigDecimal(100));
			@Override
			public void execute() throws Throwable {
				when(userService.getUser(userId.getEmail())).thenReturn(Optional.of(userId));

				when(cartRepository.findByUserId(userId)).thenReturn(Stream.of(cart1,cart2).collect(Collectors.toList()));
				assertEquals(2, cartService.getCartByUser("UserName@email.com").size());
			}
		});
	}

	@Test
	void testDeleteByMedToStoreAndUser() {
		Role roleId = new Role(2L, "Owner");
		User userId = new User(2L, "UserName", "UserName1@email.com", "Password", roleId, "1234567890", null, true);
		Store storeId = new Store(1L, userId, "StoreName", "StoreDescription");

		Medicine medicineId = new Medicine(1L,"MedicineName","Description",200.00,"Image");
		MedicineToStore medicineToStoreId = new MedicineToStore(1L,medicineId,storeId,true);

		when(medicineToStoreService.getMedicinesToStoreById(medicineToStoreId.getMedicineToStoreId())).thenReturn(Optional.of(medicineToStoreId));
		when(userService.getUser(userId.getEmail())).thenReturn(Optional.of(userId));

		CartRequest request = new CartRequest("UserName1@email.com",1L,1);

		cartService.deleteByMedToStoreAndUser(request);
		verify(cartRepository,times(1)).deleteByMedicineToStoreIdAndUserId(medicineToStoreId, userId);
	}

	@Test
	void testDeleteByMedToStoreAndUserUserNotFoundException() {
		Assertions.assertThrows(MedifyException.class, new Executable() {
			Role roleId = new Role(2L, "Owner");
			User userId = new User(2L, "UserName", "UserName1@email.com", "Password", roleId, "1234567890", null, true);
			Store storeId = new Store(1L, userId, "StoreName", "StoreDescription");

			Medicine medicineId = new Medicine(1L,"MedicineName","Description",200.00,"Image");
			MedicineToStore medicineToStoreId = new MedicineToStore(1L,medicineId,storeId,true);
			@Override
			public void execute() throws Throwable {
				when(medicineToStoreService.getMedicinesToStoreById(medicineToStoreId.getMedicineToStoreId())).thenReturn(Optional.of(medicineToStoreId));
				when(userService.getUser(userId.getEmail())).thenReturn(Optional.of(userId));

				CartRequest request = new CartRequest("UserName@email.com",1L,1);

				cartService.deleteByMedToStoreAndUser(request);
				verify(cartRepository,times(1)).deleteByMedicineToStoreIdAndUserId(medicineToStoreId, userId);
			}
		});
	}

	@Test
	void testDeleteByMedToStoreAndUserMedicineToStoreException() {
		Assertions.assertThrows(MedifyException.class, new Executable() {
			Role roleId = new Role(2L, "Owner");
			User userId = new User(2L, "UserName", "UserName1@email.com", "Password", roleId, "1234567890", null, true);
			Store storeId = new Store(1L, userId, "StoreName", "StoreDescription");

			Medicine medicineId = new Medicine(1L,"MedicineName","Description",200.00,"Image");
			MedicineToStore medicineToStoreId = new MedicineToStore(1L,medicineId,storeId,true);
			@Override
			public void execute() throws Throwable {
				when(medicineToStoreService.getMedicinesToStoreById(medicineToStoreId.getMedicineToStoreId())).thenReturn(Optional.of(medicineToStoreId));
				when(userService.getUser(userId.getEmail())).thenReturn(Optional.of(userId));

				CartRequest request = new CartRequest("UserName1@email.com",2L,1);

				cartService.deleteByMedToStoreAndUser(request);
				verify(cartRepository,times(1)).deleteByMedicineToStoreIdAndUserId(medicineToStoreId, userId);
			}
		});
	}

	@Test
	void testUpdateQuantity() {
		Role roleId = new Role(2L, "Owner");
		User userId = new User(2L, "UserName", "UserName1@email.com", "Password", roleId, "1234567890", null, true);

		Store storeId = new Store(1L, userId, "StoreName", "StoreDescription");
		Address address = new Address(1L,userId,storeId,"address1","address2","pincode","city","state");

		Medicine medicineId = new Medicine(1L,"MedicineName","Description",10,"Image");
		MedicineToStore medicineToStoreId = new MedicineToStore(1L,medicineId,storeId,true);

		Cart cart1 = new Cart(1L,userId,medicineToStoreId,9,new BigDecimal("90"));
		when(cartRepository.findById(1L)).thenReturn(Optional.of(cart1));
		cartService.updateQuantity(cart1.getCartId());

		cart1.setCost(new BigDecimal(100));
		verify(cartRepository,times(1)).save(cart1);
	}

	@Test
	void testUpdateQuantityCartItemNotFound() {
		Assertions.assertThrows(MedifyException.class, new Executable() {
			Role roleId = new Role(2L, "Owner");
			User userId = new User(2L, "UserName", "UserName1@email.com", "Password", roleId, "1234567890", null, true);

			Store storeId = new Store(1L, userId, "StoreName", "StoreDescription");
			Address address = new Address(1L,userId,storeId,"address1","address2","pincode","city","state");

			Medicine medicineId = new Medicine(1L,"MedicineName","Description",10,"Image");
			MedicineToStore medicineToStoreId = new MedicineToStore(1L,medicineId,storeId,true);

			Cart cart1 = new Cart(1L,userId,medicineToStoreId,9,new BigDecimal("90"));
			@Override
			public void execute() throws Throwable {
				when(cartRepository.findById(2L)).thenReturn(Optional.of(cart1));
				cartService.updateQuantity(cart1.getCartId());

				cart1.setCost(new BigDecimal(100));
				verify(cartRepository,times(1)).save(cart1);
			}
		});
	}

	@Test
	void testRemoveQuantity() {
		Role roleId = new Role(2L, "Owner");
		User userId = new User(2L, "UserName", "UserName1@email.com", "Password", roleId, "1234567890", null, true);

		Store storeId = new Store(1L, userId, "StoreName", "StoreDescription");
		Address address = new Address(1L,userId,storeId,"address1","address2","pincode","city","state");

		Medicine medicineId = new Medicine(1L,"MedicineName","Description",10,"Image");
		MedicineToStore medicineToStoreId = new MedicineToStore(1L,medicineId,storeId,true);

		Cart cart1 = new Cart(1L,userId,medicineToStoreId,10,new BigDecimal("100"));
		when(cartRepository.findById(1L)).thenReturn(Optional.of(cart1));
		cartService.removeQuantity(cart1.getCartId());

		cart1.setCost(new BigDecimal(90));		
		verify(cartRepository,times(1)).save(cart1);
	}

	@Test
	void testRemoveQuantityExceptionCartItemNotFound() {
		Assertions.assertThrows(MedifyException.class, new Executable() {
			Role roleId = new Role(2L, "Owner");
			User userId = new User(2L, "UserName", "UserName1@email.com", "Password", roleId, "1234567890", null, true);

			Store storeId = new Store(1L, userId, "StoreName", "StoreDescription");
			Address address = new Address(1L,userId,storeId,"address1","address2","pincode","city","state");

			Medicine medicineId = new Medicine(1L,"MedicineName","Description",10,"Image");
			MedicineToStore medicineToStoreId = new MedicineToStore(1L,medicineId,storeId,true);

			Cart cart1 = new Cart(1L,userId,medicineToStoreId,10,new BigDecimal("100"));
			@Override
			public void execute() throws Throwable {
				when(cartRepository.findById(2L)).thenReturn(Optional.of(cart1));
				cartService.removeQuantity(cart1.getCartId());

				cart1.setCost(new BigDecimal(90));		
				verify(cartRepository,times(1)).save(cart1);
			}
		});
	}

	@Test
	void testRemoveQuantityExceptionQuantityOne() {
		Assertions.assertThrows(MedifyException.class, new Executable() {
			Role roleId = new Role(2L, "Owner");
			User userId = new User(2L, "UserName", "UserName1@email.com", "Password", roleId, "1234567890", null, true);

			Store storeId = new Store(1L, userId, "StoreName", "StoreDescription");
			Address address = new Address(1L,userId,storeId,"address1","address2","pincode","city","state");

			Medicine medicineId = new Medicine(1L,"MedicineName","Description",10,"Image");
			MedicineToStore medicineToStoreId = new MedicineToStore(1L,medicineId,storeId,true);

			Cart cart1 = new Cart(1L,userId,medicineToStoreId,1,new BigDecimal("100"));
			@Override
			public void execute() throws Throwable {
				when(cartRepository.findById(1L)).thenReturn(Optional.of(cart1));
				cartService.removeQuantity(cart1.getCartId());

				cart1.setCost(new BigDecimal(90));		
				verify(cartRepository,times(1)).save(cart1);
			}
		});
	}

	@Test
	void testOrder() {
		Role roleId = new Role(2L, "Owner");
		User userId = new User(2L, "UserName", "UserName1@email.com", "Password", roleId, "1234567890", null, true);

		Store storeId = new Store(1L, userId, "StoreName", "StoreDescription");
		Address address = new Address(1L,userId,storeId,"address1","address2","pincode","city","state");

		Medicine medicineId = new Medicine(1L,"MedicineName","Description",200.00,"Image");
		MedicineToStore medicineToStoreId = new MedicineToStore(1L,medicineId,storeId,true);

		Cart cart1 = new Cart(1L,userId,medicineToStoreId,10,new BigDecimal("100"));
		Cart cart2 = new Cart(2L,userId,medicineToStoreId,5,new BigDecimal("100"));

		List<Cart> cartList = new ArrayList<>();
		cartList.add(cart1);
		cartList.add(cart2);

		OrderRequest orderRequest1 = new OrderRequest(address.getAddressId(),10,"Order Placed","UserName1@email.com",1L,new BigDecimal("100"));
		OrderRequest orderRequest2 = new OrderRequest(address.getAddressId(),5,"Order Placed","UserName1@email.com",1L,new BigDecimal("100"));

		when(medicineToStoreService.getMedicinesToStoreById(medicineToStoreId.getMedicineToStoreId())).thenReturn(Optional.of(medicineToStoreId));
		when(userService.getUser(userId.getEmail())).thenReturn(Optional.of(userId));
		when(cartService.getCartByUser(userId.getEmail())).thenReturn(cartList);

		when(addressService.findByStore(cart1.getMedicineToStoreId().getStoreId())).thenReturn(address);
		when(addressService.findByStore(cart2.getMedicineToStoreId().getStoreId())).thenReturn(address);
		when(addressService.getAddressById(address.getAddressId())).thenReturn(address);

		orderService.registerOrder(orderRequest1);
		orderService.registerOrder(orderRequest2);
		cartService.order(userId.getEmail());
		verify(cartRepository,times(1)).deleteAll(cartList);
	}



}
