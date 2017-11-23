package com.example.citru_000.inclass09;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AvatarActivity extends AppCompatActivity {


    Intent contactListIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);


        contactListIntent = new Intent(AvatarActivity.this, NewContactActivity.class);

        findViewById(R.id.f1AvatarButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactListIntent.putExtra(MainActivity.AVATAR_KEY,0);
                startActivity(contactListIntent);
                finish();
            }
        });
        findViewById(R.id.f2AvatarButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactListIntent.putExtra(MainActivity.AVATAR_KEY,1);
                startActivity(contactListIntent);
                finish();
            }
        });
        findViewById(R.id.f3AvatarButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactListIntent.putExtra(MainActivity.AVATAR_KEY,2);
                startActivity(contactListIntent);
                finish();
            }
        });

        findViewById(R.id.m1AvatarButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactListIntent.putExtra(MainActivity.AVATAR_KEY,3);
                startActivity(contactListIntent);
                finish();
            }
        });
        findViewById(R.id.m2AvatarButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactListIntent.putExtra(MainActivity.AVATAR_KEY,4);
                startActivity(contactListIntent);
                finish();
            }
        });
        findViewById(R.id.m3AvatarButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactListIntent.putExtra(MainActivity.AVATAR_KEY,5);
                startActivity(contactListIntent);
                finish();
            }
        });


    }
}
