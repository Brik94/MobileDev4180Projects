package com.example.briannak.inclass03;

import android.content.Intent;
import android.media.Image;
import android.media.tv.TvInputService;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
/**
 * Assignment: InClass03
 * File name: SelectAvatarActivity.java
 * Names: Brianna Kirkpatrick & Scott Schreiber
 */

public class SelectAvatarActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avatar);

        ImageButton f_1 = (ImageButton) findViewById(R.id.f1Button);
        f_1.setOnClickListener(this);

        ImageView f_2 = (ImageView) findViewById(R.id.f2Button);
        f_2.setOnClickListener(this);

        ImageView f_3 = (ImageView) findViewById(R.id.f3Button);
        f_3.setOnClickListener(this);

        ImageView m_1 = (ImageView) findViewById(R.id.m1Button);
        m_1.setOnClickListener(this);

        ImageView m_2 = (ImageView) findViewById(R.id.m2Button);
        m_2.setOnClickListener(this);

        ImageView m_3 = (ImageView) findViewById(R.id.m3Button);
        m_3.setOnClickListener(this);


     }

    @Override
    public void onClick(View view) {
        Intent i = new Intent();
        switch (view.getId()) {

            case (R.id.f1Button):

                i.putExtra(MainActivity.IMAGE_NAME, R.drawable.avatar_f_1);
                setResult(RESULT_OK, i);
                finish();
                break;

            case (R.id.f2Button):
                i.putExtra(MainActivity.IMAGE_NAME, R.drawable.avatar_f_2);
                setResult(RESULT_OK, i);
                finish();
                break;

            case (R.id.f3Button):
                i.putExtra(MainActivity.IMAGE_NAME, R.drawable.avatar_f_3);
                setResult(RESULT_OK, i);
                finish();
                break;

            case (R.id.m1Button):
                i.putExtra(MainActivity.IMAGE_NAME, R.drawable.avatar_m_1);
                setResult(RESULT_OK, i);
                finish();
                break;

            case (R.id.m2Button):
                i.putExtra(MainActivity.IMAGE_NAME, R.drawable.avatar_m_2);
                setResult(RESULT_OK, i);
                finish();
                break;

            case (R.id.m3Button):
                i.putExtra(MainActivity.IMAGE_NAME, R.drawable.avatar_m_3);
                setResult(RESULT_OK, i);
                finish();
                break;
        }
    }
}
