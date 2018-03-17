package com.kitchencreator.kitchencreator.model;

/**
 * Created by hp on 20-01-2018.
 */

public class UserInformation {
    public String  fullname,phoneno,address,city,pincode,state,country;

    public UserInformation() {
    }

    public UserInformation(String fullname, String phoneno, String address, String city, String pincode, String state, String country) {
        this.fullname = fullname;
        this.phoneno = phoneno;
        this.address = address;
        this.city = city;
        this.pincode = pincode;
        this.state = state;
        this.country = country;
    }
}
