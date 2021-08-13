package com.psl.service;

import java.math.BigDecimal;
import java.util.List;
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
        
        cart.setCost(totalCost(medicineToStore.get(),request.getQuantity()));
        
        return cart;
	}

	private BigDecimal totalCost(MedicineToStore medToStore, int quantity) {
        double price = medToStore.getMedicineId().getPrice();
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

	public List<Cart> getCartByUser(String email) {
		Optional<User> user = userService.getUser(email);
		user.orElseThrow(()-> new MedifyException("User not found"));
		return cartDAO.findByUserId(user.get());
	}
	
	public void deleteByMedToStoreAndUser(CartRequest request) {
		Optional<User> user=userService.getUser(request.getEmail());
		user.orElseThrow(()-> new MedifyException("User not found"));
		Optional<MedicineToStore> medToStore = medicineToStoreService.getMedicinesToStoreById(request.getId());
		medToStore.orElseThrow(()-> new MedifyException("Medicine To Store not found"));
		
		cartDAO.deleteByMedicineToStoreIdAndUserId(medToStore.get(), user.get());
	}
	
	public void updateQuantity(long id) {
		Optional<Cart> cart = cartDAO.findById(id);
		cart.orElseThrow(()-> new MedifyException("Cart Item Not Fund"));
		cart.get().setQuantity(cart.get().getQuantity()+1);
		cart.get().setCost(BigDecimal.valueOf(cart.get().getQuantity() * cart.get().getMedicineToStoreId().getMedicineId().getPrice()));
		
		cartDAO.save(cart.get());
	}

	public void removeQuantity(long id) {
		Optional<Cart> cart = cartDAO.findById(id);
		cart.orElseThrow(()-> new MedifyException("Cart Item Not Fund"));
		if(cart.get().getQuantity()==1) {
			throw new MedifyException("Item quantity can't be less than 1");
		}
		cart.get().setQuantity(cart.get().getQuantity()-1);
		cart.get().setCost(BigDecimal.valueOf(cart.get().getQuantity() * cart.get().getMedicineToStoreId().getMedicineId().getPrice()));
		
		cartDAO.save(cart.get());
	}
}
