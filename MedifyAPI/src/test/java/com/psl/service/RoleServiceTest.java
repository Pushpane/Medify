package com.psl.service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.psl.dao.IRoleDAO;
import com.psl.entity.Role;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = RoleService.class, loader = AnnotationConfigContextLoader.class)

class RoleServiceTest {
	
	@Autowired
	private RoleService roleService;
	
	@MockBean
	private IRoleDAO roleRepository;
	
	@Test
	void testCreateRole() {
		Role role = new Role(1,"Admin");
		roleService.createRole(role);
		verify(roleRepository, times(1)).save(role);
	}

	@Test
	void testDeleteRole() {
		roleService.deleteRole(1);
		verify(roleRepository, times(1)).deleteById(1L);
	}

	@Test
	void testUpdateRole() {
		Role role = new Role(1,"Admin");
		roleService.updateRole(role);
		verify(roleRepository, times(1)).save(role);
	}

	@Test
	void testGetRoleById() {
		//Role role = new Role(1L,"Admin");
		Role role = new Role(1L, "Admin");
		when(roleRepository.findById(1L)).thenReturn(Optional.of(role));
		assertEquals(role, roleService.getRoleById(1L));
	}

	@Test
	void testFindByRole() {
		//Role role = new Role(1,"Admin");
		Optional<Role> role = Optional.ofNullable(new Role(1L, "Admin"));
		String roleName = "Admin";
		when(roleRepository.findByRole(roleName)).thenReturn(role);
		assertEquals(role, roleService.findByRole(roleName));
	}

	@Test
	void testGetAllRoles() {
		Role role1 = new Role(1,"Admin");
		Role role2 = new Role(2,"Owner");
		Role role3 = new Role(3,"Customer");
		when(roleRepository.findAll()).thenReturn(Stream.of(role1, role2,role3).collect(Collectors.toList()));
		assertEquals(3, roleService.getAllRoles().size());
	}


}
