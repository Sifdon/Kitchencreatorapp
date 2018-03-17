package com.kitchencreator.kitchencreator.model;

/**
 * Created by hp on 05-02-2018.
 */

public class commondetails {



    /**
     * Created by hp on 05-02-2018.
     */


        public String address,city,pincode,phoneno,fullname,state,country;

    public commondetails() {
    }

    public commondetails(String address, String city, String pincode, String phoneno, String fullname, String state, String country) {
        this.address = address;
        this.city = city;
        this.pincode = pincode;
        this.phoneno = phoneno;
        this.fullname = fullname;
        this.state = state;
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
