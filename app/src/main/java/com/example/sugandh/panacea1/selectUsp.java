package com.example.sugandh.panacea1;

/**
 * Created by sugandh on 3/21/2017.
 */

public class selectUsp {
    int id;
    String name;
    String email;
    String password;
    String address;
    String service;
    String pincode;
    String experience;
    String mobile;

    public selectUsp(int id,String name, String email, String address, String service, String pincode,String mobile) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.service = service;
        this.pincode = pincode;
        this.mobile = mobile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getMobile() {  return mobile; }

    public void setMobile(String mobile) { this.mobile = mobile;    }

}
