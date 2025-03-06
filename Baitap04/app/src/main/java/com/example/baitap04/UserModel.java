package com.example.baitap04;

public class UserModel {
    private String name;
    private String address;

    public UserModel(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
