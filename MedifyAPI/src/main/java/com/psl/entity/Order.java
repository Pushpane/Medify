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
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long orderId;
	@ManyToOne
	@NotBlank(message = "User is reuired")
	private User userId;
	@ManyToOne
	@NotBlank(message = "Address is reuired")
	private Address addressId;
	@ManyToOne
	@NotBlank(message = "Medicine to Store is reuired")
	private MedicineToStore medicineToStoreId;
	@NotBlank(message = "Quantity is reuired")
	private int quantity;
	@NotBlank(message = "Order Status is reuired")
	private String orderStatus;
	@NotBlank(message = "CreatedAt is reuired")
	private Instant createdAt;
	
}
