package com.psl.service;

import com.psl.dao.IOrdersDAO;
import com.psl.dto.OrderRequest;
import com.psl.entity.Address;
import com.psl.entity.MedicineToStore;
import com.psl.entity.Orders;
import com.psl.entity.Store;
import com.psl.entity.User;

import com.psl.exception.MedifyException;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
@Transactional
public class OrderService {

    private IOrdersDAO ordersDAO;
    private UserService userService;
    private AddressService addressService;
    private MedicineToStoreService medicineToStoreService;
    private StoreService storeService;

    public void registerOrder(OrderRequest orderRequest) {
        Orders order = fillOrder(orderRequest);

        ordersDAO.save(order);
    }

    private Orders fillOrder(OrderRequest request) {
        Orders order = new Orders();

        order.setCreatedAt(Instant.now());
        order.setOrderStatus(request.getOrderStatus());
        order.setQuantity(request.getQuantity());

        Optional<User> user = userService.getUser(request.getEmail());
        user.orElseThrow(() -> new MedifyException("Role not found"));

        Optional<Address> address = Optional.ofNullable(addressService.getAddressById(request.getAddressId()));
        address.orElseThrow(() -> new MedifyException("Address not found"));

        Optional<MedicineToStore> medicineToStore = medicineToStoreService.getMedicinesToStoreById(request.getMedicineToStoreId());
        medicineToStore.orElseThrow(() -> new MedifyException("Medicine/Store not found"));
        
        order.setUserId(user.get());
        order.setAddressId(address.get());
        order.setMedicineToStoreId(medicineToStore.get());
        order.setCost(request.getCost());


        return order;
    }

    public void saveOrder(Orders order) {
        ordersDAO.save(order);
    }

    public void deleteOrder(long id) {
        ordersDAO.deleteById(id);
    }

    public List<Orders> getAllOrders() {
        return ordersDAO.findAll();
    }

    public List<Orders> getAllOrdersByUser(String userId) {
        Optional<User> user = userService.getUser(userId);
        user.orElseThrow(() -> new MedifyException("User not found"));
        return ordersDAO.findAllByUserId(user.get());
    }

	public List<Orders> getOrdersReceived(String email) {
        List<Store> stores = storeService.findStoreByUser(email);
        List<Orders> orders = new ArrayList<>();
        List<MedicineToStore> medToStores = new ArrayList<MedicineToStore>();
        for(Store store: stores) {
        	medToStores.addAll(medicineToStoreService.getMedicinesByStore(store));
        }
        for(MedicineToStore medStore: medToStores) {
        	orders.addAll(ordersDAO.findAllByMedicineToStoreId(medStore));
        }
		return orders;
	}

	public Orders changeStatus(long id) {
		Optional<Orders> order = ordersDAO.findById(id);
		order.orElseThrow(()-> new MedifyException("Order not found"));
		order.get().setOrderStatus("Packed");
		ordersDAO.save(order.get());
		return order.get();
	}

	public Orders changeStatusDelivered(long id) {
		Optional<Orders> order = ordersDAO.findById(id);
		order.orElseThrow(()-> new MedifyException("Order not found"));
		order.get().setOrderStatus("Delivered");
		ordersDAO.save(order.get());
		return order.get();
	}

	public Orders declineOrder(long id) {
		Optional<Orders> order = ordersDAO.findById(id);
		order.orElseThrow(()-> new MedifyException("Order not found"));
		order.get().setOrderStatus("Declined");
		ordersDAO.save(order.get());
		return order.get();
	}

	public Orders cancelOrder(long id) {
		Optional<Orders> order = ordersDAO.findById(id);
		order.orElseThrow(()-> new MedifyException("Order not found"));
		order.get().setOrderStatus("Cancelled");
		ordersDAO.save(order.get());
		return order.get();
	}

}
