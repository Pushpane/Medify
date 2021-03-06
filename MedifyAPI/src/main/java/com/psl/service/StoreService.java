package com.psl.service;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.psl.dao.IStoreDAO;


import com.psl.dto.RegisterStoreRequest;
import com.psl.entity.Store;
import com.psl.entity.User;

import com.psl.exception.MedifyException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class StoreService {
	
	private final IStoreDAO storeDAO;
	private final UserService userService;
	
	public Store registerStore(RegisterStoreRequest request) {
		Store store = fillStore(request);
		
		storeDAO.save(store);
		return store;
	}
	
	private Store fillStore(RegisterStoreRequest request) {
		Store store = new Store();

		store.setName(request.getName());
		store.setDescription(request.getDescription());
		
		Optional<User> user = userService.getUser(request.getEmail());
		//checking if role is null if role is null throw exception
		user.orElseThrow(()-> new MedifyException("Role not found"));

		
		store.setUserId(user.get());
		
		return store;
	}
	

	//finding store by name
	public Optional<Store> findStoreByName(String store) {
		return storeDAO.findByName(store);
	}
	
	//updating store
	public void updateStore(Store store) {
		storeDAO.save(store);
	}
	
	//deleting store
	public void deleteStore(long id) {
		storeDAO.deleteById(id);
	}
	
	//finding store by ID
	public Store getStoreById(long id) {
		return storeDAO.findById(id).get();
	}
	
	//Listing all stores
	public List<Store> getAllStores(){
		return storeDAO.findAll();
	}

	public List<Store> findStoreByUser(String username) {
		Optional<User> user = userService.getUser(username);
		user.orElseThrow(()-> new MedifyException("User not found"));
		return storeDAO.findByUserId(user.get());
	}
	

}
