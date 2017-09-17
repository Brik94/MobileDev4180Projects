package com.example.bri.baccalculator;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;

/**
 * Assignment #: HW1
 * File name: MainActivity.java
 * Full name: Brianna Kirkpatrick, Kevin Heu
 *
 * To do: Go back and optimize. Figure out hot to stop recreating objects and add in more comments.
 */
public class MainActivity extends Activity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private double weight, BAC, drinkSize, counter;
    private String gender;
    private static DecimalFormat df2 = new DecimalFormat("#.##");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BAC = 0.00;
        counter = 0;
        drinkSize = 1;

        //Buttons & Listeners
        Button savebutton = (Button) findViewById(R.id.saveButton);
        Button addButton = (Button) findViewById(R.id.addButton);
        Button resetButton = (Button) findViewById(R.id.resetButton);
        RadioGroup rg = (RadioGroup) findViewById(R.id.drinkSizeGroup);
        savebutton.setOnClickListener(this);
        addButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        rg.setOnCheckedChangeListener(this);

        TextView BloodAlcoholContent = (TextView) findViewById(R.id.bacLevel);
        BloodAlcoholContent.setText("BAC level: " + Double.toString(BAC));

        //Seek Bar
        final TextView alcoholPercentage = (TextView) findViewById(R.id.seekBarProgress);
        SeekBar alcoholPercentageBar = (SeekBar) findViewById(R.id.seekBar);
        alcoholPercentageBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int percentageChanged = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                percentageChanged = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //alcoholPercentage.setText(Integer.toString(percentageChanged));

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                alcoholPercentage.setText(Integer.toString(percentageChanged)+ "%");
            }
        });
    }


    @Override
    public void onClick(View view) {
        EditText weightInput = (EditText) findViewById(R.id.weightEdit);
        RadioButton rb = (RadioButton) findViewById(R.id.oneRadio);
        TextView alcoholPercentage = (TextView) findViewById(R.id.seekBarProgress);
        Switch genderSwitch = (Switch) findViewById(R.id.genderSwitch);
        SeekBar alcoholPercentageBar = (SeekBar) findViewById(R.id.seekBar);
        TextView newStatus = (TextView) findViewById(R.id.newStatus);
        TextView BloodAlcoholContent = (TextView) findViewById(R.id.bacLevel);
        ProgressBar bacPercentageBar = (ProgressBar) findViewById(R.id.progressBar);

        if(view.getId() == R.id.saveButton){
            if(TextUtils.isEmpty(weightInput.getText().toString())){
                weightInput.setError("Enter the weight in lbs.");
            }else{
                counter = 1;
                setWeight(Double.parseDouble(weightInput.getText().toString()));
                getGender();
                Log.d("hw1", "Weight: " + weight + " Gender: " + gender);
            }
        }else if(view.getId() == R.id.addButton){
            if(counter == 0){
                CharSequence text = "You forgot to save!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(MainActivity.this, text, duration);
                toast.show();
            }else{
                double newABC = calculateBAC();
                Log.d("hw1", Double.toString(newABC));
            }
        }else if(view.getId() == R.id.resetButton){ //Reset the drink size & alco % to default. Clear weight and gender values. Enable buttons again
            enableAllButtons();
            weightInput.setText("");
            setWeight(0);
            setBAC(0);
            counter = 0;
            rb.setChecked(true);
            alcoholPercentage.setText("5%");
            bacPercentageBar.setProgress(0);
            genderSwitch.setChecked(false);
            alcoholPercentageBar.setProgress(5);
            BloodAlcoholContent.setText("BAC Level: " + Double.toString(getBAC()));
            newStatus.setText(R.string.safe);
            newStatus.setBackgroundColor(Color.parseColor("#ff669900"));
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int checkedID) {
        RadioButton rb = (RadioButton) findViewById(checkedID);
        //Log.d("hw1", Character.toString(rb.getText().charAt(0)));
        if (rb.getText().equals("1 oz")){
            Log.d("hw1", rb.getText().toString());
            setDrinkSize(1);
        }
        else if (rb.getText().equals("5 oz")){
            Log.d("hw1", rb.getText().toString());
            setDrinkSize(5);
        }
        else if (rb.getText().equals("12 oz")){
            Log.d("hw1", rb.getText().toString());
            setDrinkSize(12);
        }
    }

    //“Widmark BAC Formula:” % BAC = (A x 6.24 / (W x r)).
    public double calculateBAC(){
        TextView BloodAlcoholContent = (TextView) findViewById(R.id.bacLevel);
        TextView newStatus = (TextView) findViewById(R.id.newStatus);
        SeekBar alcoholPercentageBar = (SeekBar) findViewById(R.id.seekBar);
        ProgressBar bacPercentageBar = (ProgressBar) findViewById(R.id.progressBar);

        double A = (getDrinkSize() * (alcoholPercentageBar.getProgress() * .01));
        double W = getWeight();
        double r = .68;
        double tempBAC, finalBAC;
        if (gender.equals("Male")){
            r = .68;
        }else if(gender.equals("Female")){
            r = .55;
        }

        tempBAC = A * 6.24 / (W * r);
        if(getBAC() == 0) { //hasn't been calced yet, we want a fresh variable.
            setBAC(tempBAC);
            finalBAC = getBAC();
            Log.d("hw1", "1st if");
        }else{ //We want to add onto the previous bac
            finalBAC = getBAC() + tempBAC;
            setBAC(finalBAC);
            Log.d("hw1", "2nd if");
        }


        //setBAC(A * 6.24 / (W * r));
        //tempBAC = getBAC();
        Log.d("hw1", "New BAC: " + finalBAC);
        BloodAlcoholContent.setText("BAC Level: " + df2.format(finalBAC));
        double newProgress = finalBAC * 100;
        Log.d("hw1", "New progress: " + newProgress);
        bacPercentageBar.setProgress((int)newProgress);
        if(finalBAC > 0.08 && finalBAC < .20){
            newStatus.setText(R.string.care);
            newStatus.setBackgroundColor(Color.parseColor("#FFA500"));
        }else if(finalBAC > .2){
            newStatus.setText(R.string.limit);
            newStatus.setBackgroundColor(Color.parseColor("#FF0000"));
            if(finalBAC > .25){
                disableAllButtons();
                CharSequence text = "No more drinks for you";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(MainActivity.this, text, duration);
                toast.show();
            }
        }
        return finalBAC;
    }

    public void disableAllButtons(){
        Button savebutton = (Button) findViewById(R.id.saveButton);
            savebutton.setEnabled(false);
        Button addButton = (Button) findViewById(R.id.addButton);
            addButton.setEnabled(false);
        RadioGroup rg = (RadioGroup) findViewById(R.id.drinkSizeGroup);
            rg.setEnabled(false);
        Switch genderSwitch = (Switch) findViewById(R.id.genderSwitch);
            genderSwitch.setEnabled(false);
        SeekBar alcoholPercentageBar = (SeekBar) findViewById(R.id.seekBar);
            alcoholPercentageBar.setEnabled(false);
    }

    public void enableAllButtons(){
        Button savebutton = (Button) findViewById(R.id.saveButton);
            savebutton.setEnabled(true);
        Button addButton = (Button) findViewById(R.id.addButton);
            addButton.setEnabled(true);
        RadioGroup rg = (RadioGroup) findViewById(R.id.drinkSizeGroup);
            rg.setEnabled(true);
        Switch genderSwitch = (Switch) findViewById(R.id.genderSwitch);
            genderSwitch.setEnabled(true);
        SeekBar alcoholPercentageBar = (SeekBar) findViewById(R.id.seekBar);
            alcoholPercentageBar.setEnabled(true);
    }

    //------Getters and Setters---------------
    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getGender() {
        Switch genderSwitch = (Switch) findViewById(R.id.genderSwitch);

        if(genderSwitch.isChecked()){
            gender = "Male";
        }else{
            gender = "Female";
        }

        return gender;
    }


    public double getDrinkSize() {
        return drinkSize;
    }

    public void setDrinkSize(double drinkSize) {
        this.drinkSize = drinkSize;
    }

    public double getBAC() {
        return BAC;
    }

    public void setBAC(double BAC) {
        this.BAC = BAC;
    }
}
