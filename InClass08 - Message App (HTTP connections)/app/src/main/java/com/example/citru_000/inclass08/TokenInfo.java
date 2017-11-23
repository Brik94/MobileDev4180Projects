package com.example.citru_000.inclass08;

import java.io.Serializable;

/**
 * Created by citru_000 on 11/6/2017.
 */

public class TokenInfo implements Serializable {

    String token;
    String user_email;
    String user_fname;
    String user_lname;
    String user_role;
    int user_id;
/*
    {
        "status": "ok",
            "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1MTAwMTMyNjQsImV4cCI6MTU0MTU0OTI2NCwianRpIjoiNE5qOE1QcDJMeEZ5ZXcyUzVIcTFUTyIsInVzZXIiOjF9.F1SsnXmZYlxeJNZgKo1X0SlKwgcB6SeHrJNrA_3UrE4",
            "user_id": 1,
            "user_email": "user@test.com",
            "user_fname": "Bob",
            "user_lname": "Smith",
            "user_role": "USER"
    }
    */

    @Override
    public String toString() {
        return "TokenInfo{" +
                "token='" + token + '\'' +
                ", user_email='" + user_email + '\'' +
                ", user_fname='" + user_fname + '\'' +
                ", user_lname='" + user_lname + '\'' +
                ", user_role='" + user_role + '\'' +
                ", user_id=" + user_id +
                '}';
    }
}
