package com.kitchencreator.kitchencreator.model;

/**
 * Created by MOHIT on 12-03-2018.
 */

public class Rating {

    private String phone,productid,ratevalue,comment,productname;

    public Rating() {
    }

    public Rating(String phone, String productid, String ratevalue, String comment, String productname) {
        this.phone = phone;
        this.productid = productid;
        this.ratevalue = ratevalue;
        this.comment = comment;
        this.productname = productname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getRatevalue() {
        return ratevalue;
    }

    public void setRatevalue(String ratevalue) {
        this.ratevalue = ratevalue;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }
}
