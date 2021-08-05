package com.psl.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psl.dto.CartRequest;
import com.psl.entity.Role;
import com.psl.service.CartService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

	
	private final CartService cartService;
	
	@PostMapping("/add")
	public ResponseEntity<HttpStatus> addCart(@RequestBody CartRequest cartRequest){
		cartService.createCart(cartRequest);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteCartItem/{id}")
	public ResponseEntity<HttpStatus> deleteCartItem(@PathVariable long id) throws Exception {
		cartService.deleteCartItem(id);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	@PutMapping("/updateCart")
	public ResponseEntity<HttpStatus> updateCart(@RequestBody CartRequest cartRequest) {
		cartService.updateCart(cartRequest);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
	@PostMapping("/totalCartCost")
	public ResponseEntity<HttpStatus> totalCartCost(@RequestBody CartRequest cartRequest){
		cartService.totalCartCost(cartRequest);
		return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	}
	
}
