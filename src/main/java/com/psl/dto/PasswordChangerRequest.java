package com.psl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangerRequest {
	
	private String email;
	private String token;
	private String password;
}
