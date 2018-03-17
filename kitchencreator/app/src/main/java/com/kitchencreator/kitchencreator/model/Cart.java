package com.kitchencreator.kitchencreator.model;

import java.util.List;

/**
 * Created by MOHIT on 06-03-2018.
 */

public class Cart {
    private List<Order> cart;

    public Cart() {
    }

    public Cart(List<Order> cart) {
        this.cart = cart;
    }

    public List<Order> getCart() {
        return cart;
    }

    public void setCart(List<Order> cart) {
        this.cart = cart;
    }
}
