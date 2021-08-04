package com.psl.dto;

import com.psl.entity.MedicineToStore;
import com.psl.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartRequest {

	private long cartId;
	private User userId;
	private MedicineToStore medicineToStoreId;
	private int quantity;
		
}
