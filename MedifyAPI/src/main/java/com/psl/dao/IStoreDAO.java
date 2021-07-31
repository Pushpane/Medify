package com.psl.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psl.entity.Store;

@Repository
public interface IStoreDAO extends JpaRepository<Store, Long> {

}
