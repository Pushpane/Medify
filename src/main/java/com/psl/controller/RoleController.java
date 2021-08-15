package com.psl.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psl.entity.Role;
import com.psl.exception.MedifyException;
import com.psl.service.RoleService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/admin")
public class RoleController {

	private final RoleService roleService;
	
	@PostMapping("/createRole")
	public ResponseEntity<HttpStatus> createRole(@RequestBody Role role) {
		roleService.createRole(role);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	@PutMapping("/updateRole")
	public ResponseEntity<HttpStatus> updateRole(@RequestBody Role role) {
		roleService.updateRole(role);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteRole/{id}")
	public ResponseEntity<HttpStatus> deleteRole(@PathVariable long id) {
		roleService.deleteRole(id);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	@GetMapping("/getRoleById/{id}")
	public Role getRoleById(@PathVariable long id) {
		return roleService.getRoleById(id);
	}
	
	@GetMapping("/getRole/{userrole}")
	public Role getRole(@PathVariable String userrole) {
		Optional<Role> role = roleService.findByRole(userrole);
		role.orElseThrow(()-> new MedifyException("Role not found"));
		return role.get();
	}
	
	@GetMapping("/getAllRoles")
	public List<Role> getRoles() {
		return roleService.getAllRoles();
	}
	
}
