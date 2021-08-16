package com.psl.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psl.entity.Address;
import com.psl.entity.Store;
import com.psl.entity.User;


@Repository
public interface IAddressDAO extends JpaRepository<Address, Long> {
	
	//will send address if city is found otherwise null 
	List<Address> findByCity(String city);
	
	//will send address if pincode is found otherwise null 
	List<Address> findByPincode(String pincode);
	
	
	Address findByStoreId(Store store);

	List<Address> findByUserId(User user);
	List<Address> findByUserIdAndStoreId(User user, Store store);
}
