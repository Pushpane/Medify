package com.psl.dto;


import java.util.Optional;

import com.psl.entity.Cart;
import com.psl.entity.User;
import com.psl.exception.MedifyException;
import com.psl.service.UserService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartRequest {

	private final UserService userService;
	private long cartId;
	private String email;
	private long id;
	private int quantity;
	
	public CartRequest(Cart cart) {
        this.setId(cart.getCartId());
        this.setQuantity(cart.getQuantity());
        
        
        
    }
		
}
