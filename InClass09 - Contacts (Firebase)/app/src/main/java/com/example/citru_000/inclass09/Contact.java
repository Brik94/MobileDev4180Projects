package com.example.citru_000.inclass09;

import java.io.Serializable;

/**
 * Created by citru_000 on 11/13/2017.
 */

public class Contact implements Serializable{

    String name, email, phone, dept, image;

    public Contact(){

    }

    public Contact(String name, String email, String phone, String dept, String image) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.dept = dept;
        this.image = image;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", dept='" + dept + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
