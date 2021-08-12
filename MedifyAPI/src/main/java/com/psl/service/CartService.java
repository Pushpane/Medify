package com.psl.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.psl.dao.ICartDAO;
import com.psl.dto.CartRequest;
import com.psl.entity.Address;
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

	public void registerCart(CartRequest request) {
		Cart cart = fillCart(request);

		cartDAO.save(cart);
	}


	private Cart fillCart(CartRequest request) {
		Cart cart = new Cart();
		cart.setQuantity(request.getQuantity());
		
		Optional<User> user = userService.getUser(request.getEmail());
        user.orElseThrow(() -> new MedifyException("User not found"));
		cart.setUserId(user.get());
		
		Optional<MedicineToStore> medicineToStore = medicineToStoreService.getMedicinesToStoreById(request.getId());
        medicineToStore.orElseThrow(() -> new MedifyException("Medicine/Store not found"));
        cart.setMedicineToStoreId(medicineToStore.get());
        
        cart.setCost(totalCost(request.getId(),request.getQuantity()));
        
        return cart;
	}

	private BigDecimal totalCost(long id, int quantity) {
		Optional<MedicineToStore> medicineToStore = medicineToStoreService.getMedicinesToStoreById(id);
        medicineToStore.orElseThrow(() -> new MedifyException("Medicine/Store not found"));
        double price = medicineToStore.get().getMedicineId().getPrice();
		BigDecimal totalCost = BigDecimal.valueOf(price*quantity);
		return totalCost;
	}

	public void deleteCart(long cartId){
		if (!cartDAO.existsById(cartId))
			throw new MedifyException("Cart id is invalid : " + cartId);
		cartDAO.deleteById(cartId);
	}

	public void updateCart(Cart cart) {
		cartDAO.save(cart);
	}

	public List<Cart> getAllCarts(){
		return cartDAO.findAll();
	}

	public List<Address> getCartByUser(String email) {
		Optional<User> user = userService.getUser(email);
		user.orElseThrow(()-> new MedifyException("User not found"));
		return cartDAO.findByUserId(user.get());
	}

}