/*
*
* Scott Schreiber
* Brianna Kirkpatrick
*
*  InClass07_Group5.zip
*
* */





package com.example.citru_000.inclass07;

import android.app.*;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ListFragment.OnFragmentInteractionListener, AvatarFragment.OnFragmentInteractionListener, CreateFragment.OnFragmentInteractionListener {

    ArrayList<Contact> contactList;
    Drawable currentDrawable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactList = new ArrayList<>();

        contactList.add(new Contact("user","u@g.com","1234567890","sis",null));




        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new ListFragment(), "listfragment").commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void setCurrntDrawable(Drawable d) {
        currentDrawable = d;
    }

    @Override
    public void addNewContact(Contact c) {
        c.image = currentDrawable;
        contactList.add(c);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new ListFragment(), "listfragment").commit();
        ListFragment f = (ListFragment) getSupportFragmentManager().findFragmentByTag("listfragment");

        //f.contactList.add(c);
        //f.adapter.notifyDataSetChanged();
    }

    @Override
    public Drawable getCurrentDrawable() {
        return currentDrawable;
    }

    @Override
    public void goToCreateFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, new CreateFragment(), "create_fragment").commit();
    }

    @Override
    public ArrayList<Contact> getMainContactList() {
        Log.d("test", "getList called");
        return contactList;
    }
}
