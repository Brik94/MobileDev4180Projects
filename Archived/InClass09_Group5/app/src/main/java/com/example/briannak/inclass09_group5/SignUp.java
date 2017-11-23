package com.example.briannak.inclass09_group5;

import android.app.Activity;
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
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUp extends AppCompatActivity {
    Button cancel, signup;
    EditText fname, lname, email, password;
    String lnameInput, emailInput, passwordInput, fnameInput;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        cancel = (Button) findViewById(R.id.cancelButton);
        signup = (Button) findViewById(R.id.signSignupButton);

        fname = (EditText) findViewById(R.id.fnameET);
        lname = (EditText) findViewById(R.id.lnameET);
        email = (EditText) findViewById(R.id.emailET);
        password = (EditText) findViewById(R.id.choosePassET);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        // Clicking the “Sign Up” button should submit the user’s
        //information to firebase signup.
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fnameInput = fname.getText().toString();
                lnameInput = lname.getText().toString();
                emailInput = email.getText().toString();
                passwordInput = password.getText().toString();
                Log.d("test", "creating user");

                mAuth.createUserWithEmailAndPassword(emailInput, passwordInput).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("test", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            /*UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(fnameInput).build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                                Log.d("test", "User profile updated.");
                                        }
                                    });*/

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("test", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        }
        });

    }
}




//    public void createUser(){
//
//
//}
