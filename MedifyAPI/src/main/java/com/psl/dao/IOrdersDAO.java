package com.psl.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psl.entity.Orders;
import com.psl.entity.User;

import java.util.List;

@Repository
public interface IOrdersDAO extends JpaRepository<Orders, Long>{

    List<Orders> findAllByUserId(User userId);
}
