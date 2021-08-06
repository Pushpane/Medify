package com.psl.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psl.entity.MedicineToStore;
import com.psl.entity.Medicines;
import com.psl.entity.Store;



@Repository
public interface IMedicineToStoreDAO extends JpaRepository<MedicineToStore, Long> {
	 List<MedicineToStore> getMedicinesByStoreId(Store store);
	 List<MedicineToStore> getStoreByMedicineId(Medicines medicines);
}
