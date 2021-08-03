package com.psl.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psl.entity.Address;
import com.psl.entity.Role;

@Repository
public interface IAddressDAO extends JpaRepository<Address, Long> {
	
	//will send address if city is found otherwise null 
	Optional<Address> findByCity(String city);
	
	//will send address if pincode is found otherwise null 
	Optional<Address> findByPincode(String pincode);
}
