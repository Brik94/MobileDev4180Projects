package com.example.briannak.inclass03;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Assignment: InClass03
 * File name: Display.java
 * Names: Brianna Kirkpatrick & Scott Schreiber
 */
public class Display extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display);

        TextView nameView = (TextView) findViewById(R.id.nameView);
        TextView emailView = (TextView) findViewById(R.id.emailView);
        TextView deptView = (TextView) findViewById(R.id.deptText);
        TextView statusLabel = (TextView) findViewById(R.id.statusLabel);
        ImageView avatarView = (ImageView) findViewById(R.id.avatarView);
        ImageView moodView = (ImageView) findViewById(R.id.moodView);

        if(getIntent() != null && getIntent().getExtras() != null){
            MainActivity.Profile newUser = (MainActivity.Profile) getIntent().getExtras().getSerializable(MainActivity.USER_KEY);

            nameView.setText(newUser.name);
            emailView.setText(newUser.email);
            deptView.setText(newUser.department);
            statusLabel.setText(newUser.mood);
            avatarView.setImageResource(newUser.avatarID);

            if(newUser.mood.equals("I am angry!")){
                moodView.setImageResource(R.drawable.angry);
            }else if(newUser.mood.equals("I am sad!")){
                moodView.setImageResource(R.drawable.sad);
            }else if(newUser.mood.equals("I am happy!")){
                moodView.setImageResource(R.drawable.happy);
            }else if(newUser.mood.equals("I am awesome!")){
                moodView.setImageResource(R.drawable.awesome);
            }
        }
    }
}
