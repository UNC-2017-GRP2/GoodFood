package com.netcracker.controller;


import com.netcracker.model.Address;
import com.netcracker.model.Item;
import com.netcracker.model.Order;
import com.netcracker.service.OrderService;
import com.sun.istack.internal.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    OrderService orderService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Order> getAllOrders(@PathVariable Locale locale){
        return orderService.getAllOrders(locale);
    }

    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    public List<Order> getOrdersByUsername(@PathVariable String username){
        return orderService.getOrdersByUsername(username);
    }

    @RequestMapping(value = "/total", method = RequestMethod.POST)
    public BigInteger totalOrder(@RequestBody ArrayList<Item> items){
        return  orderService.totalOrder(items);
    }

    @RequestMapping(value = "/object/id", method = RequestMethod.GET)
    public BigInteger getObjectId(){
        return  orderService.getObjectId();
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void checkout(@RequestBody Order order) throws SQLException{
        //TODO: разобраться, что прилетает в джейсоне и почему принимаются нули

        if (order.getPaid() == null){
            order.setPaid(false);
        }
        try{
            orderService.checkout(
                    order.getOrderId(),
                    (ArrayList<Item>) order.getOrderItems(),
                    order.getUserId(),
                    order.getOrderAddress(),
                    order.getOrderPhone(),
                    new Long(order.getPaymentType()),
                    order.getPaid());

        }
        catch (Exception e){
            //TODO; вернуть что-то обратно, или просто вывести в логи. Думаю, надо вернуть
            System.out.print(e.getMessage());
        }
    }

    @RequestMapping(value = "/free", method = RequestMethod.GET)
    public List<Order> getAllFreeOrders(@PathVariable Locale locale){
        return orderService.getAllFreeOrders(locale);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Order getOrderById(@PathVariable BigInteger id){
        return orderService.getOrderById(id);
    }

    @RequestMapping(value = "/courier/{username}", method = RequestMethod.GET)
    public  List<Order> getAllOrdersByCourier(@PathVariable String username,@PathVariable Locale locale){
        return orderService.getAllOrdersByCourier(username,locale);
    }

    @RequestMapping(value = "/{id}/status/{statusId}", method = RequestMethod.POST)
    public void changeOrderStatus(@PathVariable BigInteger id, @PathVariable long statusId){
        orderService.changeOrderStatus(id,statusId);
    }

    @RequestMapping(value = "/{id}/courier/{courierName}", method = RequestMethod.POST)
    public void setCourier(@PathVariable BigInteger id, @PathVariable String courierName){
        setCourier(id,courierName);
    }



    /*
    +BigInteger totalOrder(ArrayList<Item> items);
    +void checkout(ArrayList<Item> items, String username, Address orderAddress, String inputPhone) throws SQLException;
    +List<Order> getAllFreeOrders();
    //List<Order> getAllOrdersByUser(String username);
    +Order getOrderById(BigInteger orderId);
    +List<Order> getAllOrdersByCourier(String username);
    +void changeOrderStatus(BigInteger orderId, long statusId);
    +void setCourier(BigInteger orderId, String username);
    +List<Order> getAllOrders();
    List<Order> getOrdersByUsername(String username);
     */
}
