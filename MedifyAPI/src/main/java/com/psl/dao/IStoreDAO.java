package com.psl.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psl.entity.Role;
import com.psl.entity.Store;

@Repository
public interface IStoreDAO extends JpaRepository<Store, Long> {

	Optional<Store> findByStore(String store);
}
