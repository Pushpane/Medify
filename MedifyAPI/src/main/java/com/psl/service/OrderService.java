package com.psl.service;

import com.psl.dao.IOrdersDAO;
import com.psl.dto.OrderRequest;
import com.psl.entity.Address;
import com.psl.entity.MedicineToStore;
import com.psl.entity.Order;

import com.psl.entity.User;

import com.psl.exception.MedifyException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
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

    public void registerOrder(OrderRequest orderRequest) {
        Order order = fillOrder(orderRequest);

        ordersDAO.save(order);
    }

    private Order fillOrder(OrderRequest request) {
        Order order = new Order();

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


        return order;
    }

    public void saveOrder(Order order) {
        ordersDAO.save(order);
    }

    public void deleteOrder(long id) {
        ordersDAO.deleteById(id);
    }

    public List<Order> getAllOrders() {
        return ordersDAO.findAll();
    }

    public List<Order> getAllOrdersByUser(long userId) {
        Optional<User> user = userService.getUserById(userId);
        user.orElseThrow(() -> new MedifyException("Role not found"));
        return ordersDAO.findAllByUserId(userId);
    }

}
