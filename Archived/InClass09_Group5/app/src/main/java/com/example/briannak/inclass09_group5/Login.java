package com.example.briannak.inclass09_group5;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    Button login, signup;
    EditText username, password;
    String usernameInput;
    String passwordInput;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        Button login = (Button) findViewById(R.id.loginButton);


        login = (Button) findViewById(R.id.loginButton);
        signup = (Button) findViewById(R.id.signupButton);
        username = (EditText) findViewById(R.id.usernameET);
        password = (EditText) findViewById(R.id.passwordET);


        //If the user is successfully logged in then start the Chat Screen, and finish the
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usernameInput = username.getText().toString();
                passwordInput = password.getText().toString();
                Log.d("test", usernameInput + passwordInput);


                mAuth.signInWithEmailAndPassword(usernameInput, passwordInput)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("test", "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();

                                    Intent myIntent = new Intent(Login.this, ContactsList.class);
                                    startActivity(myIntent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("test", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(Login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                                // ...
                            }
                        });
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Login.this, SignUp.class);
                startActivity(myIntent);
            }
        });
    }
    }

