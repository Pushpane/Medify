package com.psl.service;


import com.psl.dao.ICartDAO;
import com.psl.dto.CartRequest;
import com.psl.entity.Cart;
import com.psl.entity.MedicineToStore;

public class CartService {

	private final ICartDAO cartDAO = null;
	
	public void createCart(CartRequest request) {
		Cart cart = addCart(request);
		
		cartDAO.save(cart);
	}
	
	private Cart addCart(CartRequest request) {
		Cart cart = new Cart();
		cart.setCartId(request.getCartId());
		cart.setMedicineToStoreId(request.getMedicineToStoreId());
		cart.setUserId(request.getUserId());
		cart.setQuantity(request.getQuantity());
		
		return cart;
	}
	
	public void deleteCart(long cartId) throws Exception{
		if (!cartDAO.existsById(cartId))
			throw new Exception("Cart id is invalid : " + cartId);
		cartDAO.deleteById(cartId);
	}
	
	public void updateCart() {
		
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
