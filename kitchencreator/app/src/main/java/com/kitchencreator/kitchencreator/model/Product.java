package com.kitchencreator.kitchencreator.model;

/**
 * Created by hp on 06-02-2018.
 */

public class Product {
    private String id,productdescription,productdiscount,productimage,productname,productprice;

    public Product() {
    }

    public Product(String id, String productdescription, String productdiscount, String productimage, String productname, String productprice) {
        this.id = id;
        this.productdescription = productdescription;
        this.productdiscount = productdiscount;
        this.productimage = productimage;
        this.productname = productname;
        this.productprice = productprice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductdescription() {
        return productdescription;
    }

    public void setProductdescription(String productdescription) {
        this.productdescription = productdescription;
    }

    public String getProductdiscount() {
        return productdiscount;
    }

    public void setProductdiscount(String productdiscount) {
        this.productdiscount = productdiscount;
    }

    public String getProductimage() {
        return productimage;
    }

    public void setProductimage(String productimage) {
        this.productimage = productimage;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductprice() {
        return productprice;
    }

    public void setProductprice(String productprice) {
        this.productprice = productprice;
    }
}
