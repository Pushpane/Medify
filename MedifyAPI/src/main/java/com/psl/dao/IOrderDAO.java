package com.psl.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psl.entity.MedicineToStore;
import com.psl.entity.Orders;
import com.psl.entity.User;

import java.time.Instant;
import java.util.List;

@Repository
public interface IOrderDAO extends JpaRepository<Orders, Long>{

    List<Orders> findAllByUserId(User userId);
    List<Orders> findAllByMedicineToStoreId(MedicineToStore store);
    List<Orders> findAllByMedicineToStoreIdAndCreatedAtAfter(MedicineToStore store, Instant date);
}
