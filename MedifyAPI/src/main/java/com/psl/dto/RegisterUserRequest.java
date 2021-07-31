package com.psl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserRequest {
	
	private String email;
	private String name;
	private String password;
	private String phone;
	private String role;

}
