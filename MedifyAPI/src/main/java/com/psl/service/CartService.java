package com.psl.service;


import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.psl.dao.ICartDAO;
import com.psl.dto.CartRequest;
import com.psl.entity.Cart;
import com.psl.entity.MedicineToStore;
import com.psl.entity.User;
import com.psl.exception.MedifyException;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
@Transactional
public class CartService {

	private final ICartDAO cartDAO;
	private final UserService userService;
	private final MedicineToStoreService medicineToStoreService;
	
	public void createCart(CartRequest request) {
		Cart cart = addCart(request);
		
		cartDAO.save(cart);
	}
	
	
	private Cart addCart(CartRequest request) {
		Cart cart = new Cart();
		Optional<MedicineToStore> md = medicineToStoreService.getMedicinesToStoreById(request.getId());
		md.get().getMedicineId().getPrice();
		cart.setMedicineToStoreId(md.get());
		cart.setQuantity(request.getQuantity());
		
		
		Optional<User> user= userService.getUser(request.getEmail());
		user.orElseThrow(()-> new MedifyException("User not found"));
		
		cart.setUserId(user.get());
		cart.setCost(totalCost(md.get(),request.getQuantity()));
		
		return cart;
	}
	
	public void deleteCartItem(long cartId){
		if (!cartDAO.existsById(cartId))
			throw new MedifyException("Cart id is invalid : " + cartId);
		cartDAO.deleteById(cartId);
	}
	
	public void updateCart(CartRequest request) {
		Cart cart = addCart(request);
		
		cart.setQuantity(request.getQuantity());
		
		cartDAO.save(cart);
	}
	
	 private BigDecimal totalCost(MedicineToStore request, int quantity) {
	        
	        BigDecimal totalCost = BigDecimal.valueOf(request.getMedicineId().getPrice()*quantity);
	        return totalCost;
	    }
	
}
