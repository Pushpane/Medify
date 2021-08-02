package com.psl.service;

import java.time.Instant;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.psl.dao.IStoreDAO;
import com.psl.dao.IUserDAO;
import com.psl.dto.RegisterStoreRequest;
import com.psl.dto.RegisterUserRequest;
import com.psl.entity.Role;
import com.psl.entity.Store;
import com.psl.entity.User;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class StoreService {
	
	private final IStoreDAO storeDAO;
	private final UserService userService;
	
	public void registerStore(RegisterStoreRequest request) {
		Store store = fillStore(request);
		
		storeDAO.save(store);
	}
	
	private Store fillStore(RegisterStoreRequest request) {
		Store store = new Store();
		store.setName(request.getName);
		store.setDescription(request.getDescription);
		
		Optional<User> user = userService.findByUser(request.getUser());
		//checking if role is null if role is null throw exception
		user.orElseThrow(()-> new RuntimeException("Role not found"));
		
		store.setUserId(user.get());
		
		return store;
	}
	
	public Optional<Store> findByStore(String store) {
		return storeDAO.findByStore(store);
	}

}
