package com.psl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RegisterMedicineRequest {
	private String name;
	private String description;
	private double price;
	private String image; 
}
