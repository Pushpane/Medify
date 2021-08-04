package com.psl.service;



import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.psl.dao.IAddressDAO;
import com.psl.dto.RegisterAddressRequest;
import com.psl.entity.Store;
import com.psl.entity.User;
import com.psl.entity.Address;
import com.psl.entity.Role;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional

public class AddressService {
	private final IAddressDAO addressDAO;
	private final StoreService storeService;
	private final UserService userService;
	
	//Register address for new user
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
	
	//Update address of existing user
	public void updateAddress(Address address) {
		addressDAO.save(address);
	}
	
	//Delete address of existing user
	public void deleteAddress(long id) {
		addressDAO.deleteById(id);
	}
	
	//Get all addresses
	public List<Address> getAllAddress(){
		return addressDAO.findAll();
	}
	
	//Get address in a particular city
	public Optional<Address> findByCity(String city) {
		return addressDAO.findByCity(city);
	}
	
	//Get address in a particular city
	public Optional<Address> findByPincode(String pincode) {
		return addressDAO.findByPincode(pincode);
	}
	
	//Get address for a particular user
	public Optional<Address> findByUser(String user) {
			return addressDAO.findByUserId(user);
	}
	
	//Get address for a particular store
	public Optional<Address> findByStore(String store) {
			return addressDAO.findByStoreId(store);
	}
	//Get address for a particular store owned by user given
	public Optional<Address> findByUserAndStore(String user,String store) {
			return addressDAO.findByUserIdAndStoreId(user,store);
	}
	
}
