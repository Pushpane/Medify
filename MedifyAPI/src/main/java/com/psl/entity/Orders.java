package com.psl.entity;

import java.time.Instant;

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
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long orderId;
	@ManyToOne
	@NotBlank(message = "User is required")
	private User userId;
	@ManyToOne
	@NotBlank(message = "Address is required")
	private Address addressId;
	@ManyToOne
	@NotBlank(message = "Medicine to Store is required")
	private MedicineToStore medicineToStoreId;
	@NotBlank(message = "Quantity is required")
	private int quantity;
	@NotBlank(message = "Order Status is required")
	private String orderStatus;
	@NotBlank(message = "CreatedAt is required")
	private Instant createdAt;
}
