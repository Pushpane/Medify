package com.psl.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.psl.dao.IAddressDAO;
import com.psl.dto.RegisterAddressRequest;
import com.psl.entity.Address;
import com.psl.entity.Store;
import com.psl.entity.User;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
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
		
		Optional<User> user=null;
		if(request.getEmail()!=null) {
			user = userService.getUser(request.getEmail());
		}
		
		Optional<Store> store = null;
		if(request.getName()!=null) {
			store = storeService.findStoreByName(request.getName());
		}
		
		address.setStoreId(store.get());
		address.setUserId(user.get());
		
		return address;
	}
}
