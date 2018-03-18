package com.example.gagan.maymay;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class activity_profile extends AppCompatActivity {
    private ImageButton homebtn2,notificationbtn2,createbtn2,bookmarkbtn;
    private Button editprofilebtn;
    private TextView useremail,username;
    private ImageView userImage;
    private FirebaseAuth firebase;
    String name,image;
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference myRef=database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        firebase = FirebaseAuth.getInstance();
        FirebaseUser user = firebase.getCurrentUser();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        onbuttonclick();
        useremail = findViewById(R.id.useremailid);
        username = findViewById(R.id.usernameid);
        userImage=findViewById(R.id.ivUser);
        useremail.setText(user.getEmail());

        LoadUserInfo();
    }

    public void LoadUserInfo(){
        myRef.child("User Info").child(mAuth.getCurrentUser().getUid()).
                addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.child("name").getValue()!=null){
                            name = dataSnapshot.child("name").getValue().toString();
                            image = dataSnapshot.child("imageUrl").getValue().toString();
                            SetUserIfo();

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }

    public void  SetUserIfo(){
        username.setText(name);
        Picasso.with(this).load(image).into(userImage);
    }

    public void onbuttonclick(){
        homebtn2 = (ImageButton) findViewById(R.id.homebtn2);
        homebtn2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent second = new Intent("com.example.gagan.maymay.Home");
                        second.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(second);
                    }
                });
        notificationbtn2 = (ImageButton) findViewById(R.id.notificationbtn2);
        notificationbtn2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent second = new Intent("com.example.gagan.maymay.Notification");
                        second.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(second);
                    }
                });
        createbtn2 = (ImageButton) findViewById(R.id.createbtn2);
        createbtn2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent second = new Intent("com.example.gagan.maymay.PostMeme");
                        second.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(second);
                    }
                });
        bookmarkbtn = (ImageButton) findViewById(R.id.bookmarkbtn);
        bookmarkbtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent second = new Intent("com.example.gagan.maymay.bookmark");
                        second.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(second);
                    }
                });
        editprofilebtn = (Button) findViewById(R.id.editprofilebtn);
        editprofilebtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent second = new Intent("com.example.gagan.maymay.editprofile");
                        second.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(second);
                    }
                });
    }
}
