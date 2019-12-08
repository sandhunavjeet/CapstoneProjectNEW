package com.example.capstoneproject;

public class Company {
    private String cid;
    private String cmpid;
    private String name;
    private String email;
    private String phone;

    public Company() {
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCmpid() {
        return cmpid;
    }

    public void setCmpid(String cmpid) {
        this.cmpid = cmpid;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Company(String cid, String cmpid, String name, String email, String phone) {
        this.cid = cid;
        this.cmpid = cmpid;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
}
