package com.psl.service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.psl.dao.ICartDAO;
import com.psl.dao.IUserDAO;
import com.psl.dao.IVerificationTokenDAO;
import com.psl.dto.CartRequest;
import com.psl.entity.Cart;
import com.psl.entity.MedicineToStore;
import com.psl.entity.User;
import com.psl.exception.MedifyException;
import com.psl.security.JwtProvider;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
@Transactional
public class CartService {

	private final ICartDAO cartDAO = null;
	private final UserService userService;
	private final MedicineService medicineService;
	
	public void createCart(CartRequest request) {
		Cart cart = addCart(request);
		
		cartDAO.save(cart);
	}
	
	
	 public static CartRequest getDtoFromCart(Cart cart) {
	        CartRequest cartRequest = new CartRequest(cart);
	        return cartRequest;
	    }
	
	private Cart addCart(CartRequest request) {
		Cart cart = new Cart();
		//MedticineToStore md = medicineToStoreService.getById(request.getId());
		//md.getMedicineId.getPrice
		//cart.setMedicineToStoreId(md);
		cart.setQuantity(request.getQuantity());
		
		
		Optional<User> user= userService.getUser(request.getEmail());
		user.orElseThrow(()-> new MedifyException("User not found"));
		
		cart.setUserId(user.get());
		
		
		return cart;
	}
	
	public void deleteCartItem(long cartId) throws Exception{
		if (!cartDAO.existsById(cartId))
			throw new Exception("Cart id is invalid : " + cartId);
		cartDAO.deleteById(cartId);
	}
	
	public void updateCart(CartRequest request) {
		Cart cart = addCart(request);
		
		cart.setQuantity(request.getQuantity());
		
		cartDAO.save(cart);
	}
	
	 public BigDecimal totalCartCost(CartRequest request) {
		    Optional<User> user= userService.getUser(request.getEmail());
			user.orElseThrow(()-> new MedifyException("User not found"));
	        
			List<Cart> cartList = cartDAO.findAllByUserId(user);
	        List<CartRequest> cartItems = new ArrayList<>();
	        for (Cart cart:cartList){
	            CartRequest cartRequest = getDtoFromCart(cart);
	            cartItems.add(cartRequest);
	        }
	        BigDecimal totalCost = BigDecimal.ZERO;
	        for (CartRequest cartRequest:cartItems){
	            totalCost += (cartRequest.getMedicineId().getPrice()* cartRequest.getQuantity());
	        }
	        //CartCost cartCost = new CartCost(cartItems,totalCost);
	        return totalCost;
	    }
	
//	
//	public void addCart(Cart cartId,MedicineToStore medicineToStoreId,Integer quantity) {
//		
//		cart.set
//		
//		cartDAO.save(null);
//	}
//	
}
