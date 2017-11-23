package com.example.citru_000.inclass08;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class SignupActivity extends AppCompatActivity {
    TokenInfo tokenInfo;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        progressBar = findViewById(R.id.progressBar4);
        progressBar.setVisibility(View.INVISIBLE);

        findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.realSignupButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });
    }

    public void signUp(){

        progressBar.setVisibility(View.VISIBLE);
        if(((EditText)findViewById(R.id.passEditText)).getText().toString().equals(((EditText)findViewById(R.id.confirmPassEditText)).getText().toString())) {

            OkHttpClient client = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("email", ((EditText) findViewById(R.id.emailEditText)).getText().toString())
                    .add("password", ((EditText) findViewById(R.id.passEditText)).getText().toString())
                    .add("fname", ((EditText) findViewById(R.id.fnameEditText)).getText().toString())
                    .add("lname", ((EditText) findViewById(R.id.lnameEditText)).getText().toString())
                    .build();

            Request request = new Request.Builder()
                    .url("http://ec2-54-164-74-55.compute-1.amazonaws.com/api/signup")
                    .post(formBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d("test", "Failure");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {

                        String res = response.body().string();
                        //Log.d("test", "onResponse: " + res);

                        Gson gson = new Gson();
                        tokenInfo = gson.fromJson(res, TokenInfo.class);
                        Log.d("demo", "onResponse: " + tokenInfo.toString());
                        finish();
                    } else {
                        Log.d("test", "onResponse else: " + response.body().string());

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(SignupActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                            }
                        });

                       // Toast.makeText(SignupActivity.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();

                    }

                }
            });
        }
        else{
            Log.d("test", "Passwords don't match");
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
        }


    }

}
