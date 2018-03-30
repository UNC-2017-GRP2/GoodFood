package com.netcracker.controller;

import com.netcracker.model.Address;
import com.netcracker.model.Item;
import com.netcracker.service.OrderService;
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
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

    @RequestMapping(value = "/basket", method = RequestMethod.GET)
    public ModelAndView toBasket(HttpSession httpSession){
        ModelAndView model = new ModelAndView();
        List<Item> basketItems = (ArrayList<Item>) httpSession.getAttribute("basketItems");
        if(basketItems != null && basketItems.size() != 0){
            BigInteger sum = orderService.totalOrder((ArrayList<Item>) basketItems);
            model.addObject("totalOrder", sum);
        }
        model.addObject("userAddresses", httpSession.getAttribute("userAddresses"));
        model.setViewName("basket");
        return model;
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public String checkout(@RequestParam("input-address-latitude") String latitude,@RequestParam("input-address-longitude") String longitude, @RequestParam("input-phone") String inputPhone, @RequestParam(value = "stripeToken", required = false) String stripeToken, Principal principal, HttpSession httpSession, SessionStatus sessionStatus) throws SQLException {
        ArrayList<Item> basketItems = (ArrayList<Item>)httpSession.getAttribute("basketItems");
        if(basketItems != null && basketItems.size() != 0){
            Address orderAddress = new Address(Double.parseDouble(latitude), Double.parseDouble(longitude));
            orderService.checkout(basketItems,principal.getName(), orderAddress, inputPhone);
            double usd = 57.5043128;
            int summa = (int)(orderService.totalOrder(basketItems).doubleValue()/usd*100);
            try {
                Stripe.apiKey = "sk_test_cUafSvNbAV9skSbmHLzJ6rX0";
                // Charge the user's card:
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("amount", summa);
                params.put("currency", "usd");
                params.put("description", "Example charge last last");
                params.put("source", stripeToken);
                Charge.create(params);
            } catch (Exception e) {
                e.printStackTrace();
            }

            sessionStatus.setComplete();
            return "redirect:/my-orders/1";
        }else{
            return "redirect:/basket";
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