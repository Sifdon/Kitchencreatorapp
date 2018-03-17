package com.kitchencreator.kitchencreator.model;

import java.util.List;
import java.util.Map;

/**
 * Created by MOHIT on 08-03-2018.
 */

public class Buynow {
    private String name,address,pincode,phoneno,Total,status,id,city;
    private List<Order> product;

    public Buynow() {
    }

    public Buynow(String name, String address, String pincode, String phoneno, String total, String id, String city, List<Order> product) {
        this.name = name;
        this.address = address;
        this.pincode = pincode;
        this.phoneno = phoneno;
        Total = total;
        this.id = id;
        this.status="0";
        this.city = city;
        this.product = product;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Order> getProduct() {
        return product;
    }

    public void setProduct(List<Order> product) {
        this.product = product;
    }
}
