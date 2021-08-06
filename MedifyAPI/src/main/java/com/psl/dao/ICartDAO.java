package com.psl.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psl.entity.Cart;

@Repository
public interface ICartDAO extends JpaRepository<Cart, Long> {

}
