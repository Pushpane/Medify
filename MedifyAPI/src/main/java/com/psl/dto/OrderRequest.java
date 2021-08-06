package com.psl.dto;

import com.psl.entity.MedicineToStore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

    private long addressId;
    private int quantity;
    private String orderStatus;
    private String email;
    private long medicineToStoreId;

}
