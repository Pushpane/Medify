package com.psl.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psl.dto.CartRequest;
import com.psl.service.CartService;
import com.psl.entity.Address;
import com.psl.entity.Cart;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class CartController {


	private final CartService cartService;

	@PostMapping("/addCart")
	public void addCart(@RequestBody CartRequest cartRequest){
		cartService.registerCart(cartRequest);
	}

	@DeleteMapping("/deleteCart/{id}")
	public ResponseEntity<HttpStatus> deleteRole(@PathVariable long id) {
		cartService.deleteCart(id);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	@PutMapping("/updateCart")
	public ResponseEntity<HttpStatus> updateAddress(@RequestBody Cart cart) {
		cartService.updateCart(cart);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}

	@GetMapping("/getAllCarts")
	public List<Cart> getAllCarts(){
		return cartService.getAllCarts();
	}
	
	@GetMapping("/getCartByUser/{email}")
	public List<Cart> getCartByUser(@PathVariable String email){
		List<Cart> cart = cartService.getCartByUser(email);
		return cart;
	}

}
