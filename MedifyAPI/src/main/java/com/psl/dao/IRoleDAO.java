package com.psl.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psl.entity.Role;

@Repository
public interface IRoleDAO extends JpaRepository<Role, Long>{

	//will send role if found otherwise null 
	Optional<Role> findByRole(String role);
}
