package com.example.gagan.maymay;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.auth.data.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.app.ShareCompat.EXTRA_CALLING_ACTIVITY;

public class Home extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private NavigationView navigation;
    private ImageButton profilebtn1;
    private ImageButton notificationbtn1,createbtn1;
    private DrawerLayout drawer;
    private FirebaseAuth firebase;

    MemeAdapter adapter;

    private FirebaseDatabase database=FirebaseDatabase.getInstance();
    private DatabaseReference myRef=database.getReference();

    ArrayList<Memecard> memeList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mAuth = FirebaseAuth.getInstance();
        firebase = FirebaseAuth.getInstance();
        FirebaseUser user = firebase.getCurrentUser();
        memeList=new ArrayList<Memecard>();
        RecyclerView recycleList = (RecyclerView) findViewById(R.id.rvMeme);
        recycleList.setHasFixedSize(true);
        LinearLayoutManager myLayoutManager = new LinearLayoutManager(this);
        myLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleList.setLayoutManager(myLayoutManager);

        adapter= new MemeAdapter(memeList,this);
        recycleList.setAdapter(adapter);


        final ImageView toolbar1 = (ImageView) findViewById(R.id.menu);
                drawer = findViewById(R.id.drawer);
        toolbar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        navigation_click();
        onbuttonclick();
        Loadpost();


    }
    public void Loadpost(){
        myRef.child("Meme").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                memeList.clear();

                for (DataSnapshot postSnapshot:dataSnapshot.getChildren()) {
                    String image = (String) postSnapshot.child("image").getValue();
                    String text = (String) postSnapshot.child("text").getValue();
                    //String personname = (String) postSnapshot.child("personname").getValue();
                    String username=(String) postSnapshot.child("userName").getValue();


                     if(image!=""&&text!=""){   memeList.add(new Memecard(image,text,username));
                }}

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Toast.makeText(Home.this, "error", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void navigation_click() {
        navigation = (NavigationView) findViewById(R.id.nav_view);
        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_home) {

                    drawer.closeDrawer(GravityCompat.START);
                }
                if (id == R.id.nav_profile) {
                    Intent intent4 = new Intent("com.example.gagan.maymay.Home");
                    intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent4);

                    Intent intent2 = new Intent("com.example.gagan.maymay.activity_profile");
                    intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent2);
                    return true;
                }
                if (id == R.id.nav_settings) {
                    Intent intent4 = new Intent("com.example.gagan.maymay.editprofile");
                    intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent4);

                    Intent intent2 = new Intent("com.example.gagan.maymay.editprofile");
                    intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent2);
                    return true;
                }

                    if (id == R.id.nav_bookmarks) {
                        Intent intent4 = new Intent("com.example.gagan.maymay.Home");
                        intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent4);

                        Intent intent3 = new Intent("com.example.gagan.maymay.bookmark");
                        intent3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent3);
                        return true;
                    }

                    if (id == R.id.nav_signout) {
                        mAuth.signOut();
                        finish();
                         Intent intent5 = new Intent("com.example.gagan.maymay.Login_page");
                            intent5.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent5);
                        Toast.makeText(Home.this, "Logged Out Successfully.", Toast.LENGTH_LONG).show();
                        return true;

                    }
                return false;
            }
        });
    }

    public void onbuttonclick(){
        profilebtn1 = (ImageButton) findViewById(R.id.profilebtn1);
        profilebtn1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent second = new Intent("com.example.gagan.maymay.activity_profile");
                        second.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(second);
                    }
                });
        notificationbtn1 = (ImageButton) findViewById(R.id.notificationbtn1);
        notificationbtn1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent second = new Intent("com.example.gagan.maymay.Notification");
                        second.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(second);
                    }
                });


        createbtn1 = (ImageButton) findViewById(R.id.createbtn1);
        createbtn1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        firebase = FirebaseAuth.getInstance();
                        FirebaseUser user = firebase.getCurrentUser();
                            Intent second = new Intent("com.example.gagan.maymay.PostMeme");
                            second.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(second);
                    }

                });
    }

}
