package com.psl.entity;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
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
@Table(name = "medify_users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;
	@NotBlank(message = "User's name is required.")
	private String name;
	@Email
	@NotBlank(message = "Email is required.")
	private String email;
	@NotBlank(message = "Password is required.")
	private String password;
	@ManyToOne
	private Role roleId;
	@NotBlank(message = "Phone Number is required.")
	private String phoneNumber;
	@NotBlank(message = "Joining Date is required.")
	private Instant dateJoined;
	
}
