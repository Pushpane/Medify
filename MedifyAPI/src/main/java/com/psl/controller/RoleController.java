package com.psl.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psl.entity.Role;
import com.psl.service.RoleService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class RoleController {

	private final RoleService roleService;
	
	@PostMapping("/createRole")
	public void createRole(@RequestBody Role role) {
		roleService.createRole(role);
	}
	
	@PutMapping("/updateRole")
	public void updateRole(@RequestBody Role role) {
		roleService.updateRole(role);
	}
	
	@DeleteMapping("/deleteRole/{id}")
	public void deleteRole(@PathVariable long id) {
		roleService.deleteRole(id);
	}
	
	@GetMapping("/getRoleById/{id}")
	public Role getRoleById(@PathVariable long id) {
		return roleService.getRoleById(id);
	}
	
	@GetMapping("/getRole/{role}")
	public Role getRole(@PathVariable String userrole) {
		Optional<Role> role = roleService.findByRole(userrole);
		role.orElseThrow(()-> new RuntimeException("Role not found"));
		return role.get();
	}
	
	@GetMapping("/getAllRoles")
	public List<Role> getRoles() {
		return roleService.getAllRoles();
	}
	
}
