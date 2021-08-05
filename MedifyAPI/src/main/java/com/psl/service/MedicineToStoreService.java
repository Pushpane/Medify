package com.psl.service;

import java.lang.StackWalker.Option;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.psl.dao.IMedicineToStoreDAO;
import com.psl.dto.RegisterMedicineToStoreRequest;
import com.psl.entity.MedicineToStore;
import com.psl.entity.Medicines;
import com.psl.entity.Store;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class MedicineToStoreService {
	
	private IMedicineToStoreDAO medicineToStoreDAO;
	private final StoreService storeService;
	private final MedicineService medicineService;
	
	public void registerMedicineToStore(RegisterMedicineToStoreRequest request) {
		MedicineToStore medToStore = fillMedicineToStore(request);
		
		medicineToStoreDAO.save(medToStore);
	}
	
	private MedicineToStore fillMedicineToStore(RegisterMedicineToStoreRequest request) {
		MedicineToStore medToStoreEntity = new MedicineToStore();
		
		
		
		Optional<Medicines> medicine = medicineService.findMedicineByName(request.getMedicineName());
		
		
		Optional<Store> store = storeService.findStoreByName(request.getStoreName());

		
		medToStoreEntity.setMedicineId(medicine.get());
		medToStoreEntity.setStoreId(store.get());;
		medToStoreEntity.setAvailable(true);
		
		return medToStoreEntity;
	}
	
	public void deleteMedicineToStore(long id) {
		medicineToStoreDAO.deleteById(id);
	}
	
	//unComplete
	
//	public Optional<Medicines> getMedicineByStore(String storeName) {
//		
//	}
//	
//	public Optional<Store> getStoreByMedicine(String medicineName){
//		
//	}
	
}
