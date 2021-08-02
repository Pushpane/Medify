package com.psl.service;

import java.time.Instant;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.psl.dao.IAddressDAO;
import com.psl.dto.RegisterAddressRequest;
import com.psl.entity.Store;
import com.psl.entity.User;
import com.psl.entity.Address;

public class AddressService {
	private final IAddressDAO addressDAO;
	private final StoreService storeService;
	private final UserService userService;
	
	public void registerAddress(RegisterAddressRequest request) {
		Address address = fillAddress(request);
		
		addressDAO.save(address);
	}
	
	private Address fillAddress(RegisterAddressRequest request) {
		Address address = new Address();
		address.setAddress1(request.getAddress1());
		address.setAddress2(request.getAddress2());
		address.setPincode(request.getPincode());
		address.setCity(request.getCity());
		address.setState(request.getState());
		
		Optional<User> user = userService.findByUser(request.getUser());
		user.orElseThrow(()-> new RuntimeException("User not found"));
		
		Optional<Store> store = storeService.findByStore(request.getStore());
		//checking if role is null if role is null throw exception
		store.orElseThrow(()-> new RuntimeException("Store not found"));
		
		address.setStoreId(store.get());
		address.setUserId(user.get());
		
		return address;
	}

}
