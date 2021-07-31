package com.psl.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psl.entity.Medicines;

@Repository
public interface IMedicinesDAO extends JpaRepository<Medicines, Long> {

}
