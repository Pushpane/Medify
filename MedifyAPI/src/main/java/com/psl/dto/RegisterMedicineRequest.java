package com.psl.dto;

import org.springframework.web.multipart.MultipartFile;

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
	private MultipartFile image; 
}
