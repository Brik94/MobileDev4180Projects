package com.example.citru_000.inclass08;

import java.io.Serializable;

/**
 * Created by citru_000 on 11/6/2017.
 */

public class ThreadMessage implements Serializable {

    String user_fname, user_lname;
    String title;
    int user_id,id;

    @Override
    public String toString() {
        return "ThreadMessage{" +
                "user_fname='" + user_fname + '\'' +
                ", user_lname='" + user_lname + '\'' +
                ", title='" + title + '\'' +
                ", user_id=" + user_id +
                ", id=" + id +
                '}';
    }
}
