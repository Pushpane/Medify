package com.psl.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
public class MedicineToStore {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long medicineToStoreId;
	@ManyToOne
	@NotBlank(message = "Medicines is required")
	private Medicines medicineId;
	@ManyToOne
	@NotBlank(message = "Store is required")
	private Store storeId;
	@NotBlank(message = "Availability is required")
	private boolean available;
}
