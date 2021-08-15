package com.psl.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psl.entity.Store;
import com.psl.entity.User;

@Repository
public interface IStoreDAO extends JpaRepository<Store, Long> {

	Optional<Store> findByName(String name);

	List<Store> findByUserId(User user);
}
