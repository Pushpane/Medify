package com.psl.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psl.entity.MedicineToStore;

@Repository
public interface IMedicineToStoreDAO extends JpaRepository<MedicineToStore, Long> {

}
