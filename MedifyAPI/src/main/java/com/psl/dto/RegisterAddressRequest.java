package com.psl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterAddressRequest {
	
	private String user;
	private String store;
	private String email;
	private String name;
	private String address1;
	private String address2;
	private String pincode;
	private String city;
	private String state;
}
