package com.psl.service;

import java.time.Instant;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.psl.dao.IUserDAO;
import com.psl.dto.RegisterUserRequest;
import com.psl.entity.Role;
import com.psl.entity.User;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class UserService {
	
	private final IUserDAO userDAO;
	private final PasswordEncoder passwordEncoder;
	private final RoleService roleService;
	
	public void registerUser(RegisterUserRequest request) {
		User user = fillUser(request);
		
		userDAO.save(user);
	}
	
	private User fillUser(RegisterUserRequest request) {
		User user = new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setPhoneNumber(request.getPhone());
		
		Optional<Role> role = roleService.findByRole(request.getRole());
		//checking if role is null if role is null throw exception
		role.orElseThrow(()-> new RuntimeException("Role not found"));
		
		user.setRoleId(role.get());
		user.setDateJoined(Instant.now());
		
		return user;
	}

}
