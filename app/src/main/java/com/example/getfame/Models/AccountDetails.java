package com.example.getfame.Models;

public class AccountDetails {
    private String username;
    private String birthday;
    private String email;
    private String phone;
    private String password;
    private String defaultProfile;
    //Empty Constructor
    public AccountDetails() {
    }
    //Arg Constructor

    public AccountDetails(String username, String birthday, String email,String phone, String password,String defaultProfile) {
        this.username = username;
        this.birthday = birthday;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.defaultProfile=defaultProfile;
    }

    //Getters

    public String getUsername() {
        return username;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getDefaultProfile() {
        return defaultProfile;
    }
}
