package com.psl.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.psl.dao.IMedicineToStoreDAO;
import com.psl.dto.RegisterMedicineToStoreRequest;
import com.psl.entity.MedicineToStore;
import com.psl.entity.Medicine;
import com.psl.entity.Store;
import com.psl.exception.MedifyException;

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
		
		Optional<Medicine> medicine = medicineService.findMedicineById(request.getMedicineId());
		medicine.orElseThrow(()-> new MedifyException("Medicine Not Found"));
		Store store = storeService.getStoreById(request.getStoreId());

		
		medToStoreEntity.setMedicineId(medicine.get());
		medToStoreEntity.setStoreId(store);
		medToStoreEntity.setAvailable(true);
		
		return medToStoreEntity;
	}
	
	public void deleteMedicineToStore(long id) {
		medicineToStoreDAO.deleteById(id);
	}
	
	public List<MedicineToStore> getMedicinesByStore(String storeName){
		Optional<Store> store = storeService.findStoreByName(storeName);
		return medicineToStoreDAO.findByStoreId(store.get());
	}
	
	public List<MedicineToStore> getStoreByMedicine(String medicineName){

		Optional<Medicine> medicine = medicineService.findMedicineByName(medicineName);
		return medicineToStoreDAO.findByMedicineId(medicine.get());
	}
	
	public List<MedicineToStore> getAllMedicine(){
		return medicineToStoreDAO.findAll();
	}
	
	public Optional<MedicineToStore> getMedicinesToStoreById(long id){
		return medicineToStoreDAO.findById(id);
	}
	
	
}
