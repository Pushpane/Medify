package com.psl.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psl.entity.Address;
import com.psl.entity.Cart;
import com.psl.entity.User;

@Repository
public interface ICartDAO extends JpaRepository<Cart, Long> {

	List<Address> findByUserId(User user);

}
