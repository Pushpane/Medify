package com.psl.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psl.entity.Role;
import com.psl.entity.User;

@Repository
public interface IUserDAO extends JpaRepository<User, Long>{

	Optional<User> findByUser(String user);
}
