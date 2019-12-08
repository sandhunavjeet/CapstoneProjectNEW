package com.example.capstoneproject;

public class User {

    private String uid;
    private String usrid;
    private String name;
    private String email;
    private String phone;

    public User() {
    }

    public User(String uid, String usrid, String name, String email,
                String phone) {
        this.uid = uid;
        this.name = name;
        this.usrid = usrid;
        this.email = email;
        this.phone = phone;
        ;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsrid() {
        return usrid;
    }

    public void setUsrid(String usrid) {
        this.usrid = usrid;
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



}
