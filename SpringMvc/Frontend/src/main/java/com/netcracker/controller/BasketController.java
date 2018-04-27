package com.netcracker.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.netcracker.config.Constant;
import com.netcracker.model.Address;
import com.netcracker.model.Item;
import com.netcracker.service.OrderService;
import com.netcracker.service.UserService;
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.security.Principal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes(value = {"basketItems"})
public class BasketController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/basket", method = RequestMethod.GET)
    public ModelAndView toBasket(HttpSession httpSession, @RequestParam(value = "message", required = false) String message, ModelAndView model){
       // ModelAndView model = new ModelAndView();
        List<Item> basketItems = (ArrayList<Item>) httpSession.getAttribute("basketItems");
        if(basketItems != null && basketItems.size() != 0){
            BigInteger sum = orderService.totalOrder((ArrayList<Item>) basketItems);
            model.addObject("totalOrder", sum);
        }
        model.addObject("userAddresses", httpSession.getAttribute("userAddresses"));

        if (message != null){
            model.addObject("paymentError", "paymentError");
        }
        model.setViewName("basket");
        return model;
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public ModelAndView checkout(@RequestParam("input-address-latitude") String latitude,@RequestParam("input-address-longitude") String longitude, @RequestParam("input-phone") String inputPhone, @RequestParam(value = "stripeToken", required = false) String stripeToken, Principal principal, HttpSession httpSession, SessionStatus sessionStatus) throws SQLException {
        ModelAndView model = new ModelAndView();
        ArrayList<Item> basketItems = (ArrayList<Item>)httpSession.getAttribute("basketItems");
        if(basketItems != null && basketItems.size() != 0){
            try {
                BigInteger orderId = orderService.getObjectId();
                Address orderAddress = new Address(Double.parseDouble(latitude), Double.parseDouble(longitude));
                double usd = 57.5043128;
                int sum = (int)(orderService.totalOrder(basketItems).doubleValue()/usd*100);
                if (stripeToken != null){
                    try{
                        Stripe.apiKey = "sk_test_cUafSvNbAV9skSbmHLzJ6rX0";
                        //Customer
                        /*Map<String, Object> customerParams = new HashMap<>();
                        customerParams.put("description", "Customer for");
                        customerParams.put("email", userService.getByUsername(principal.getName()).getEmail());
                        customerParams.put("source", stripeToken);
                        Customer customer = Customer.create(customerParams);
                        String id = customer.getId().toString();
                        System.out.println(id);*/
                        // Charge the user's card:
                        Map<String, Object> chargeParams = new HashMap<>();
                        chargeParams.put("amount", sum);
                        chargeParams.put("currency", "usd");
                        chargeParams.put("description", "Order for GoodFood");
                        chargeParams.put("receipt_email", userService.getByUsername(principal.getName()).getEmail());

                        Map<String, String> metadata = new HashMap<>();
                        metadata.put("order_id", orderId.toString());

                        //chargeParams.put("customer", customer.getId());
                        chargeParams.put("metadata", metadata);
                        chargeParams.put("source", stripeToken);
                        Charge charge = Charge.create(chargeParams);

                        if (charge.getPaid() && charge.getStatus().equals("succeeded")){
                            orderService.checkout(orderId,basketItems,principal.getName(), orderAddress, inputPhone, Constant.PAYMENT_BY_CARD_ENUM_ID, true);
                        }else{
                            throw new Exception();
                        }
                    }catch (Exception e){
                        model.addObject("message","paymentError");
                        model.setViewName("redirect:/basket");
                        return model;
                    }
                }else{
                    orderService.checkout(orderId, basketItems, principal.getName(), orderAddress, inputPhone, Constant.CASH_PAYMENT_ENUM_ID, false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            sessionStatus.setComplete();
            model.setViewName("redirect:/my-orders/1");
            return model;
        }else{
            model.setViewName("redirect:/basket");
            return model;
        }
    }

    @RequestMapping(value = "/updateBasket", method = RequestMethod.GET)
    public @ResponseBody void updateBasket(@RequestParam BigInteger itemId, @RequestParam int newQuantity, HttpSession httpSession){

        List<Item> curItems = (List<Item>) httpSession.getAttribute("basketItems");
        for(Item itemInBasket : curItems){
            if(itemInBasket.getProductId().equals(itemId)){
                itemInBasket.setProductQuantity(newQuantity);
                break;
            }
        }
        httpSession.setAttribute("basketItems", curItems);
    }

    @RequestMapping(value = "/removeItem", method = RequestMethod.GET)
    public @ResponseBody String removeItem(@RequestParam BigInteger itemId, HttpSession httpSession){
        List<Item> curItems = (List<Item>) httpSession.getAttribute("basketItems");
        for(Item itemInBasket : curItems){
            if(itemInBasket.getProductId().equals(itemId)){
                curItems.remove(itemInBasket);
                break;
            }
        }
        httpSession.setAttribute("basketItems", curItems);
        BigInteger sum = orderService.totalOrder((ArrayList<Item>) curItems);
        return sum.toString();
    }

    @RequestMapping(value = "/isBasketEmpty", method = RequestMethod.GET)
    public @ResponseBody String isBasketEmpty(HttpSession httpSession){
        List<Item> curItems = (List<Item>) httpSession.getAttribute("basketItems");
        if(curItems == null || curItems.size() == 0){
            return "true";
        }else{
            return "false";
        }
    }
}