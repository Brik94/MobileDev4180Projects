package com.example.briannak.inclass03;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.Serializable;

/**
 * Assignment: InClass03
 * File name: MainActivity.java
 * Names: Brianna Kirkpatrick & Scott Schreiber
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener{


    public static class Profile implements Serializable{
        public String name, email, department, mood;
        public int avatarID;


        public Profile(String name, String email, String department, String mood, int avatarID) {
            this.name = name;
            this.email = email;
            this.department = department;
            this.mood = mood;
            this.avatarID = avatarID;
        }
    }

    public static final int REQ_CODE = 100;
    public static final String IMAGE_NAME = "name";
    static String USER_KEY = "USER";


    private String department, mood;
    private int imageID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mood = "I am angry!";
        department = "SIS";
        imageID = R.drawable.select_avatar;
        RadioGroup rb = (RadioGroup) findViewById(R.id.selectDepartment);
        rb.setOnCheckedChangeListener(this);

        ImageView selectAvatar = (ImageView)findViewById(R.id.selectAvatar);
        selectAvatar.setImageResource(R.drawable.select_avatar);
        selectAvatar.setOnClickListener(this);

        Button submitButton = (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(this);

        SeekBar selectMood = (SeekBar) findViewById(R.id.selectMood);
        selectMood.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int percentageChanged = 0;
            ImageView moodView = (ImageView) findViewById(R.id.moodImage);

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                percentageChanged = progress;
                switch (percentageChanged){
                    case (0):
                        moodView.setImageResource(R.drawable.angry);
                        mood = "I am angry!";
                        break;
                    case(1):
                        moodView.setImageResource(R.drawable.sad);
                        mood = "I am sad!";
                        break;
                    case(2):
                        moodView.setImageResource(R.drawable.happy);
                        mood = "I am happy!";
                        break;
                    case(3):
                        moodView.setImageResource(R.drawable.awesome);
                        mood = "I am awesome!";
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        EditText nameInput = (EditText) findViewById(R.id.nameEditText);
        EditText emailInput = (EditText) findViewById(R.id.emailEditText);
        ImageView selectAvatar = (ImageView)findViewById(R.id.selectAvatar);

        //SUBMIT BUTTON
        if (view.getId() == R.id.submitButton){
            //VALIDATE EMAIL & NAME HERE------INCOMPLETE AND IMAGE VALIDATION
            if(TextUtils.isEmpty(nameInput.getText().toString()) || TextUtils.isEmpty(emailInput.getText().toString()) ){
                CharSequence text = "Name input or Email input is missing!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(MainActivity.this, text, duration);
                toast.show();
            }else if(!Patterns.EMAIL_ADDRESS.matcher(emailInput.getText().toString()).matches()){
                CharSequence text = "Email input is incorrcect!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(MainActivity.this, text, duration);
                toast.show();
            }else if(imageID == R.drawable.select_avatar){
                CharSequence text = "Please select an image!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(MainActivity.this, text, duration);
                toast.show();
            }
            else{ //CREATE NEW USER PROFILE
                Intent i = new Intent(MainActivity.this, Display.class);

                //Creating user params,
                String name = nameInput.getText().toString();
                String email = emailInput.getText().toString();
                String department = getDepartment();

                Profile newUser = new Profile(name, email, department, mood, imageID);

                i.putExtra(USER_KEY, newUser);
                startActivity(i);
            }

        }else if (view.getId() == R.id.selectAvatar){
            Intent i = new Intent(getBaseContext(), SelectAvatarActivity.class);
            startActivityForResult(i, REQ_CODE);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedID) {
        RadioButton rb = (RadioButton) findViewById(checkedID);
        if(rb.getId() == R.id.sisButton){
            setDepartment(rb.getText().toString());
        }else if (rb.getId() == R.id.csButton){
            setDepartment(rb.getText().toString());
        }else if(rb.getId() == R.id.bioButton){
            setDepartment(rb.getText().toString());
        }
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        ImageView selectAvatar = (ImageView)findViewById(R.id.selectAvatar);
        selectAvatar.setImageResource(R.drawable.avatar_f_1);

        if(requestCode == REQ_CODE){
            if(resultCode == RESULT_OK && data.getExtras().containsKey(IMAGE_NAME)){
                int imageName = data.getExtras().getInt(IMAGE_NAME);
                selectAvatar.setImageResource(imageName);
                imageID = imageName;
            }
        }else if (resultCode == RESULT_CANCELED){
                Log.d("cw3", "Image cancelled");
        }
    }
}
