package com.avinash.rider_gps_sos.Model;


public class Users {
    private String name,phone ,password,sos1;

    public Users()
    {

    }

    public Users(String name, String phone, String password,String sos1) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.sos1=sos1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSos1() {
        return sos1;
    }

    public void setSos1(String sos1) {
        this.sos1 = sos1;
    }
}
