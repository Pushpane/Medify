package com.psl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterStoreRequest {
	
	private String user;
	private String name;
	private String description;

}
