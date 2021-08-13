package com.psl.service;


import java.util.List;
import java.util.Optional;

import com.psl.dao.IAddressDAO;
import com.psl.dto.RegisterAddressRequest;
import com.psl.entity.Store;
import com.psl.entity.User;
import com.psl.exception.MedifyException;
import com.psl.entity.Address;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class AddressService {
    private final IAddressDAO addressDAO;
    private final StoreService storeService;
    private final UserService userService;

    //Register address for new user
    public void registerAddress(RegisterAddressRequest request) {
        Address address = fillAddress(request);

        addressDAO.save(address);
    }

    private Address fillAddress(RegisterAddressRequest request) {
        Address address = new Address();
        address.setAddress1(request.getAddress1());
        address.setAddress2(request.getAddress2());
        address.setPincode(request.getPincode());
        address.setCity(request.getCity());
        address.setState(request.getState());


        Optional<User> user = null;
        if (request.getEmail() != null) {
            user = userService.getUser(request.getEmail());
        }

        Store store = null;
        if (request.getStoreId() != 0) {
            store = storeService.getStoreById(request.getStoreId());
        }


        address.setStoreId(store);
        address.setUserId(user.get());

        return address;
    }

    //Update address of existing user
    public void updateAddress(Address address) {
        addressDAO.save(address);
    }

    //Delete address of existing user
    public void deleteAddress(long id) {
        addressDAO.deleteById(id);
    }

    //Get all addresses
    public List<Address> getAllAddress() {
        return addressDAO.findAll();
    }

    //Get address in a particular city
    public List<Address> findByCity(String city) {
        return addressDAO.findByCity(city);
    }

    //Get address in a particular city
    public List<Address> findByPincode(String pincode) {
        return addressDAO.findByPincode(pincode);
    }


    //Get address for a particular store
    public List<Address> findByStore(Store store) {
        return addressDAO.findByStoreId(store);
    }

    //Get address for a particular store owned by user given
    public List<Address> findByUser(String email) {
        Optional<User> user = userService.getUser(email);
        user.orElseThrow(() -> {
            log.error("User not found" + email);
            return new MedifyException("User not found");
        });
        return addressDAO.findByUserId(user.get());
    }

    public Address getAddressById(long id) {
        return addressDAO.getById(id);
    }

}
