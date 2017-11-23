package com.example.citru_000.inclass08;

/**
 * Created by citru_000 on 11/8/2017.
 */

public class Message {

    String user_fname, user_lname;
    String message;
    String created_at;
    int user_id,id;

    /*
    * {
    "status": "ok",
    "message": {
        "user_fname": "Bob",
        "user_lname": "Smith",
        "user_id": 1,
        "id": 1110,
        "message": "testing message 2",
        "created_at": "2017-11-09 03:08:46"
    }
}
    *
    * */

    @Override
    public String toString() {
        return "Message{" +
                "user_fname='" + user_fname + '\'' +
                ", user_lname='" + user_lname + '\'' +
                ", message='" + message + '\'' +
                ", created_at='" + created_at + '\'' +
                ", user_id=" + user_id +
                ", id=" + id +
                '}';
    }
}
