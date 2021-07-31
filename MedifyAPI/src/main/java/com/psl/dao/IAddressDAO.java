package com.psl.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psl.entity.Address;

@Repository
public interface IAddressDAO extends JpaRepository<Address, Long> {

}
