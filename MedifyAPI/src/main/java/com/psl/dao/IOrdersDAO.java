package com.psl.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psl.entity.Order;
import com.psl.entity.User;

import java.util.List;

@Repository
public interface IOrdersDAO extends JpaRepository<Order, Long>{

    List<Order> findAllByUserId(User userId);
}
