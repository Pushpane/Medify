package com.psl.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psl.entity.Address;


@Repository
public interface IAddressDAO extends JpaRepository<Address, Long> {
	
	//will send address if city is found otherwise null 
	Optional<Address> findByCity(String city);
	
	//will send address if pincode is found otherwise null 
	Optional<Address> findByPincode(String pincode);
	
	//will send address if user is found otherwise null 
	Optional<Address> findByUserId(String user);
	
	//will send address if store is found otherwise null 
	Optional<Address> findByStoreId(String store);
	
	//will send address if store and user is found otherwise null 
	Optional<Address> findByUserIdAndStoreId(String user, String store);
}
