package com.psl.controller;

import java.util.List;

import com.psl.dto.OrderRequest;
import com.psl.entity.Orders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.psl.service.OrderService;


import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor

@RequestMapping("/api/user")
public class OderController {

    private final OrderService orderService;

    @PostMapping("/addOrder")
    public void addOrder(@RequestBody OrderRequest orderRequest) {
        orderService.registerOrder(orderRequest);
    }

    @PutMapping("/updateOrder")
    public ResponseEntity<HttpStatus> updateOrder(@RequestBody Orders order) {
        orderService.saveOrder(order);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }


    @DeleteMapping("/deleteOrder/{id}")
    public ResponseEntity<HttpStatus> deleteOrder(@PathVariable long id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }


    @GetMapping("/getAllOrders")
    public List<Orders> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/getUserOrder/{UserId}")
    public List<Orders> getAllUserOrders(@PathVariable long UserId) {
        return orderService.getAllOrdersByUser(UserId);
    }


}

