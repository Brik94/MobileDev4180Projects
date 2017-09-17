package com.example.briannak.inclass02;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Shape Imageviews & Calculate Button
        ImageView triangle = (ImageView) findViewById(R.id.triangle);
        ImageView square = (ImageView) findViewById(R.id.square);
        ImageView circle = (ImageView) findViewById(R.id.circle);
        Button calculate = (Button) findViewById(R.id.calculate);
        Button clear = (Button) findViewById(R.id.clear);

        //Event Handler
        triangle.setOnClickListener(this);
        square.setOnClickListener(this);
        circle.setOnClickListener(this);
        calculate.setOnClickListener(this);
        clear.setOnClickListener(this);



    }

    //Area = (b * h) / 2
    private void triangleArea(){
        final TextView result = (TextView) findViewById(R.id.result);
        Button calculate = (Button) findViewById(R.id.calculate);
        EditText input1 = (EditText) findViewById(R.id.editLength1);
        EditText input2 = (EditText) findViewById(R.id.editLength2);
        double base = Double.parseDouble(input1.getText().toString());
        double height = Double.parseDouble(input2.getText().toString());


        final double area = (base * height) / 2;
        Log.d("triangleArea", Double.toString(area));

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("inclass02", "Triangle calculate clicked");
                result.setText(Double.toString(area));
            }
        });
    }

    // Area = l * l
    private void squareArea(){
        final TextView result = (TextView) findViewById(R.id.result);
        Button calculate = (Button) findViewById(R.id.calculate);
        EditText input1 = (EditText) findViewById(R.id.editLength1);
        double length1 = Double.parseDouble(input1.getText().toString());

        final double area = length1 * length1;
        Log.d("SquareArea", Double.toString(area));

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("inclass02", "Square calculate clicked");
                result.setText(Double.toString(area));
            }
        });
    }

    //Area =  3.1416 x Length 1 x Length 1
    private void circleArea(){
        final TextView result = (TextView) findViewById(R.id.result);
        Button calculate = (Button) findViewById(R.id.calculate);
        EditText input1 = (EditText) findViewById(R.id.editLength1);
        double length1 = Double.parseDouble(input1.getText().toString());

        final double area = 3.1416 * length1 * length1;
        Log.d("CircleArea", Double.toString(area));

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("inclass02", "Circle calcuate clicked");

                result.setText(Double.toString(area));
            }
        });
    }

    @Override
    public void onClick(View view) {
        EditText input1 = (EditText) findViewById(R.id.editLength1);
        EditText input2 = (EditText) findViewById(R.id.editLength2);
        TextView length2 = (TextView) findViewById(R.id.length2);
        TextView shapeIndicator = (TextView) findViewById(R.id.shapeIndicator);



        //On Clicks for Shapes.
        if(input1.getText().toString().isEmpty() || (input2.getText().toString().isEmpty())){
            CharSequence text = "Missing input in 1 or both boxes!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(MainActivity.this, text, duration);
            toast.show();
        }else if(view.getId() == R.id.triangle){
            triangleArea();
            shapeIndicator.setText("Triangle");
        }else if (view.getId() == R.id.square){
            input2.setVisibility(View.INVISIBLE);
            length2.setVisibility(View.INVISIBLE);
            shapeIndicator.setText("Square");
            squareArea();

        }else if (view.getId() == R.id.circle){
            input2.setVisibility(View.INVISIBLE);
            length2.setVisibility(View.INVISIBLE);
            shapeIndicator.setText("Circle");
            circleArea();
        }

        if(view.getId() == R.id.clear){
            input1.setText("");
            input2.setText("");
        }

    }
}
