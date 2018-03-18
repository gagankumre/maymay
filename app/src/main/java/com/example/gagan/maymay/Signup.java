package com.example.gagan.maymay;

import android.content.Intent;
import android.os.PatternMatcher;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {
    private EditText emails1, passworss1;
    private Button signups1,back;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        emails1 = findViewById(R.id.emails1);
        passworss1 = findViewById(R.id.passwords1);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar2);
        back = findViewById(R.id.signupbuttons1);
        onbuttonclick();
    }


    public void onbuttonclick() {
        signups1 = (Button) findViewById(R.id.signups1);

        signups1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressBar.setVisibility(View.VISIBLE);
                        registeruser();

                    }
                });
        back.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in = new Intent("com.example.gagan.maymay.Login_page");
                        startActivity(in);
                    }
                }
        );
    }

    private void registeruser() {
        String email = emails1.getText().toString().trim();
        String password = passworss1.getText().toString().trim();

        if (email.isEmpty()) {
            emails1.setError("email is required");
            emails1.requestFocus();
            progressBar.setVisibility(View.GONE);
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emails1.setError("valid email is required");
            emails1.requestFocus();
            progressBar.setVisibility(View.GONE);
            return;

        }
        if (password.isEmpty()) {
            passworss1.setError("password is required");
            passworss1.requestFocus();
            progressBar.setVisibility(View.GONE);
            return;
        }
        if (password.length() < 7) {
            passworss1.setError("password size should be more than 7");
            passworss1.requestFocus();
            progressBar.setVisibility(View.GONE);
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              if (task.isSuccessful()){

                  progressBar.setVisibility(View.GONE);
                  Toast.makeText( getApplicationContext(), "username registered successfully & please register your profile details before posting memes ", Toast.LENGTH_SHORT).show();
              }
              else {
                  Log.e("createUserWithEmail", String.valueOf(task.getException()));
                  //FirebaseAuthException e = (FirebaseAuthException )task.getException();
                  progressBar.setVisibility(View.GONE);
                  //Log.e("LoginActivity", "Failed Registration", e);
                  Toast.makeText( getApplicationContext(), "some error occurred ", Toast.LENGTH_SHORT).show();
              }
            }
        });

    }
}
