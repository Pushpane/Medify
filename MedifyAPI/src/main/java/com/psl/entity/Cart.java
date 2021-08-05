package com.psl.entity;

import java.math.BigDecimal;

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
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long cartId;
	@ManyToOne
	@NotBlank(message = "User is required")
	private User userId;
	@NotBlank(message = "Medicine is required")
	@ManyToOne
	private MedicineToStore medicineToStoreId;
	@NotBlank(message = "Quantity is required")
	private int quantity;
	private BigDecimal cost;
}
