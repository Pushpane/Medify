package com.psl.service;

import com.psl.dao.IOrdersDAO;
import com.psl.dto.AnalyticsResponse;
import com.psl.dto.OrderRequest;
import com.psl.entity.Address;
import com.psl.entity.MedicineToStore;
import com.psl.entity.Orders;
import com.psl.entity.Store;
import com.psl.entity.User;

import com.psl.exception.MedifyException;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormatSymbols;
import java.time.Instant;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Transactional
@Slf4j
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
        user.orElseThrow(() -> {
            log.error("Role not found " + request.getEmail());
            return new MedifyException("Role not found");
        });

        Optional<Address> address = Optional.ofNullable(addressService.getAddressById(request.getAddressId()));
        address.orElseThrow(() -> {
            log.error("Address not found " + request.getMedicineToStoreId());
            return new MedifyException("Address not found");
        });

        Optional<MedicineToStore> medicineToStore = medicineToStoreService.getMedicinesToStoreById(request.getMedicineToStoreId());
        medicineToStore.orElseThrow(() -> {
            log.error("Address not found " + request.getMedicineToStoreId());
            return new MedifyException("Medicine/Store not found");
        });

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
        user.orElseThrow(() -> {
            log.error("Role not found " + userId);
            return new MedifyException("Role not found");
        });
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

	public List<AnalyticsResponse> getAnalytics(String email) {
		List<Store> stores = storeService.findStoreByUser(email);
        List<Orders> orders = new ArrayList<>();
        Instant date = Instant.now().minus(Period.ofDays(5));
        List<MedicineToStore> medToStores = new ArrayList<MedicineToStore>();
        for(Store store: stores) {
        	medToStores.addAll(medicineToStoreService.getMedicinesByStore(store));
        }
        for(MedicineToStore medStore: medToStores) {
        	orders.addAll(ordersDAO.findAllByMedicineToStoreIdAndCreatedAtAfter(medStore,date));
        }
    	Calendar c = Calendar.getInstance();
        
    	List<AnalyticsResponse> result = new ArrayList<AnalyticsResponse>();
    	for(int day=0;day<5;day++) {
    		c.setTime(Date.from(Instant.now().minus(Period.ofDays(day))));
    		int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
    		int month = c.get(Calendar.MONTH);
    		int year = c.get(Calendar.YEAR);
    		List<Orders> ordersOfDay = orders.stream().filter(x-> {
    		    c.setTime(Date.from(x.getCreatedAt()));
    		    if(dayOfMonth==c.get(Calendar.DAY_OF_MONTH))
    		        return true;return false;}).collect(Collectors.toList());
    		long orderCount = ordersOfDay.stream().count();
    		long delivered = ordersOfDay.stream().filter(x-> x.getOrderStatus().equals("Delivered")).count();
    		long cancelled = ordersOfDay.stream().filter(x-> x.getOrderStatus().equals("Cancelled")).count();
    		double totalCost =0;
    		for(Orders item: ordersOfDay) {
    			totalCost +=  item.getCost().doubleValue();
    		}
            DateFormatSymbols dfs = new DateFormatSymbols();
            String[] months = dfs.getMonths();
            result.add(AnalyticsResponse.builder().month(months[month]).year(year).totalCancelled(cancelled)
            	.totalDelivered(delivered).totalEarning(totalCost).day(dayOfMonth).totalOrders(orderCount).build());
    	}
    	Collections.reverse(result);
    	return result;
	}
}
