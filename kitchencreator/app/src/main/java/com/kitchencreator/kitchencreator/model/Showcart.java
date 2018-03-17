package com.kitchencreator.kitchencreator.model;

/**
 * Created by MOHIT on 06-03-2018.
 */

public class Showcart {
    private String discount,price,productId,productname,quantity;

    public Showcart() {
    }

    public Showcart(String discount, String price, String productId, String productname, String quantity) {
        this.discount = discount;
        this.price = price;
        this.productId = productId;
        this.productname = productname;
        this.quantity = quantity;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
