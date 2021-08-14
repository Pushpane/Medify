package com.psl.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.psl.dao.IRoleDAO;
import com.psl.entity.Role;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class RoleService {

	private final IRoleDAO roleDAO;
	
	public void createRole(Role role) {
		roleDAO.save(role);
	}
	
	public void deleteRole(long id) {
		roleDAO.deleteById(id);
	}
	
	public void updateRole(Role role) {
		roleDAO.save(role);
	}
	
	public Role getRoleById(long id) {
		return roleDAO.findById(id).get();
	}
	
	public Optional<Role> findByRole(String role) {
		return roleDAO.findByRole(role);
	}
	
	public List<Role> getAllRoles(){
		return roleDAO.findAll();
	}
}
