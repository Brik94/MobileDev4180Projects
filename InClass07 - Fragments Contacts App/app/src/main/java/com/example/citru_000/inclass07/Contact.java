package com.example.citru_000.inclass07;

import android.graphics.drawable.Drawable;

/**
 * Created by citru_000 on 10/30/2017.
 */

public class Contact {
    String name, email, phone, dept;
    Drawable image;

    public Contact(){

    }

    public Contact(String name, String email, String phone, String dept, Drawable image) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.dept = dept;
        this.image = image;
    }
}
