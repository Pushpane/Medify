package com.psl.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Medicines {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long medicineId;
	@NotBlank(message = "Name of medicine is required")
	private String name;
	@NotBlank(message = "Description of medicine is required")
	@Lob
	private String description;
	@NotBlank(message = "Price is required")
	private double price;
	private String image;
}
