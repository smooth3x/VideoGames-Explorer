package com.example.videogames_explorer.models;

public class Person {

    String phoneNumber;
    String email;

    public Person (String PhoneVal, String mailVal){
        this.phoneNumber = PhoneVal;
        this.email = mailVal;
    }
    public Person (){

    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
