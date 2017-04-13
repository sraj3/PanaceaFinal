package com.example.sugandh.panacea1;

/**
 * Created by sugandh on 3/23/2017.
 */

public class User {
    int id;
    String name;
    String mobile;
    String address;
    String pincode;

    static String email;


    public User(int id, String name, String mobile, String address, String pincode) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.address = address;
        this.pincode = pincode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        User.email = email;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
