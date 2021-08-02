package com.psl.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psl.entity.User;

@Repository
public interface IUserDAO extends JpaRepository<User, Long>{

}
