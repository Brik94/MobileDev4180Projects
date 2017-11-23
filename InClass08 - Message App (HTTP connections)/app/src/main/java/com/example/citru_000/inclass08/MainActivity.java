package com.example.citru_000.inclass08;

/*
*
* Scott Schreiber
* Brianna Kirkpatrick
*
*  Group5_InClass08.zip
*
* */



import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    EditText emailField;
    EditText passwordField;
    TokenInfo tokenInfo;
    ProgressBar progressBar;
    public static final String TOKEN_KEY = "token";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);


        emailField = findViewById(R.id.emailLoginEditText);
        passwordField = findViewById(R.id.passLoginEditText);

        findViewById(R.id.signupButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signupIntent = new Intent(MainActivity.this, SignupActivity.class);
                Log.d("test", "signup intent");
                startActivity(signupIntent);

            }
        });


        findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser(emailField.getText().toString(), passwordField.getText().toString());
                progressBar.setVisibility(View.VISIBLE);
               // Log.d("test", "email: " + emailField.getText().toString());
            }
        });




    }

    @Override
    protected void onResume() {
        super.onResume();
        emailField.setText("");
        passwordField.setText("");
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void loginUser(String username, String password){

        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("email", username)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url("http://ec2-54-164-74-55.compute-1.amazonaws.com/api/login")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("test", "Failure");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){

                    String res = response.body().string();
                    //Log.d("test", "onResponse: " + res);

                    Gson gson = new Gson();
                    tokenInfo = gson.fromJson(res, TokenInfo.class);
                    Log.d("demo", "onResponse: " + tokenInfo.toString());

                    Intent threadIntent = new Intent(MainActivity.this, ThreadsActivity.class);
                    threadIntent.putExtra(TOKEN_KEY, tokenInfo);
                    startActivity(threadIntent);
                }
                else{
                    Log.d("test", "onResponse else: " + response.body().string());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(MainActivity.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                        }
                    });


                }

            }
        });




    }




}
