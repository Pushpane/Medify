package com.psl.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
public class Store {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long storeId;
	@ManyToOne
	@NotBlank(message = "User is required.")
	private User userId;
	@NotBlank(message = "Name of Store is required.")
	private String name;
	@NotBlank(message = "Description is required")
	@Lob
	private String description;
	
}
