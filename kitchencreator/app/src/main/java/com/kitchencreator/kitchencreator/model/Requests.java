package com.kitchencreator.kitchencreator.model;

import java.util.List;
import java.util.Map;

/**
 * Created by hp on 11-02-2018.
 */

public class Requests {

    private String name,address,pincode,city,phoneno,Total,status,id,Paymentmode,Paymentstate;
    private List<Order> products;

    public Requests() {
    }

    public Requests(String name, String address, String pincode, String city, String phoneno, String total, String id, String paymentmode, String paymentstate, List<Order> products) {
        this.name = name;
        this.address = address;
        this.pincode = pincode;
        this.city = city;
        this.phoneno = phoneno;
        Total = total;
        this.id = id;
        this.status = "0";
        this.Paymentmode = paymentmode;
        this.Paymentstate = paymentstate;
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPaymentmode() {
        return Paymentmode;
    }

    public void setPaymentmode(String paymentmode) {
        Paymentmode = paymentmode;
    }

    public String getPaymentstate() {
        return Paymentstate;
    }

    public void setPaymentstate(String paymentstate) {
        Paymentstate = paymentstate;
    }

    public List<Order> getProducts() {
        return products;
    }

    public void setProducts(List<Order> products) {
        this.products = products;
    }
}

