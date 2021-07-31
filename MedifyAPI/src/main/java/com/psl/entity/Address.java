package com.psl.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long addressId;
	@ManyToOne
	@Nullable
	private User userId;
	@ManyToOne
	@Nullable
	private Store storeId;
	@NotBlank(message = "Address1 is required.")
	private String address1;
	private String address2;
	@NotBlank(message = "Pincode is requuired.")
	private String pincode;
	@NotBlank(message = "City is required.")
	private String city;
	@NotBlank(message = "State is required.")
	private String state;
}
