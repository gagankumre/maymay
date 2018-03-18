package com.example.gagan.maymay;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.app.ProgressDialog;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class Login_page extends AppCompatActivity {
    String EmailHolder, PasswordHolder;
    boolean EditTextEmptyCheck;
    private static ImageButton tempbtn;
    private static Button signup,loginbtn;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private TextView eml,pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar3);
        eml = findViewById(R.id.email);
        pwd = findViewById(R.id.editText);
        if (mAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), com.example.gagan.maymay.Home.class));
        }
        onbuttonclick();
    }

    public void onbuttonclick() {
        loginbtn = findViewById(R.id.loginbutton);
        loginbtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CheckEditTextIsEmptyOrNot();
                        if(EditTextEmptyCheck)
                        {

                            // If  EditTextEmptyCheck == true then login function called.
                            LoginFunction();

                        }
                        else {

                            // If  EditTextEmptyCheck == false then toast display on screen.
                            Toast.makeText(com.example.gagan.maymay.Login_page.this, "Please Fill All the Fields", Toast.LENGTH_LONG).show();
                        }


                    }
                }
        );
        signup = (Button) findViewById(R.id.signupbutton);
        signup.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent second = new Intent("com.example.gagan.maymay.Signup");
                        second.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(second);
                    }
                });
    }
    public void LoginFunction(){

        // Setting up message in progressDialog.
        progressBar.setVisibility(View.VISIBLE);

        // Showing progressDialog.
        //progressDialog.show();

        // Calling  signInWithEmailAndPassword function with firebase object and passing EmailHolder and PasswordHolder inside it.
        mAuth.signInWithEmailAndPassword(EmailHolder, PasswordHolder)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // If task done Successful.
                        if(task.isSuccessful()){

                            // Hiding the progress dialog.
                            progressBar.setVisibility(View.GONE);

                            // Closing the current Login Activity.
                            finish();


                            // Opening the UserProfileActivity.
                            Intent intent = new Intent(Login_page.this, com.example.gagan.maymay.Home.class);
                            startActivity(intent);
                        }
                        else {

                            // Hiding the progress dialog.
                            progressBar.setVisibility(View.GONE);

                            // Showing toast message when email or password not found in Firebase Online database.
                            Toast.makeText(Login_page.this, "Email or Password Not found, Please Try Again", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    public void CheckEditTextIsEmptyOrNot(){

        // Getting value form Email's EditText and fill into EmailHolder string variable.
        EmailHolder = eml.getText().toString().trim();

        // Getting value form Password's EditText and fill into PasswordHolder string variable.
        PasswordHolder = pwd.getText().toString().trim();

        // Checking Both EditText is empty or not.
        if(TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder))
        {

            // If any of EditText is empty then set value as false.
            EditTextEmptyCheck = false;

        }
        else {

            // If any of EditText is empty then set value as true.
            EditTextEmptyCheck = true ;

        }

    }
}


