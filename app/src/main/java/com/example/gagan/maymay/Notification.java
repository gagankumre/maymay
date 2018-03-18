package com.example.gagan.maymay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class Notification extends AppCompatActivity {
    private ImageButton homebtn3,profilebtn3,createbtn3;

    RecyclerView recyclerView;
    Notificationadapter notificationadapter;
    List<Notificationcard> noti_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        noti_list = new ArrayList<>();
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
       //Adapter = new Notificationadapter(this, noti_list);
       // recyclerView.setAdapter(Adapter);
        onbuttonclick();

    }

    public void onbuttonclick(){
        homebtn3 = (ImageButton) findViewById(R.id.homebtn3);
        homebtn3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent second = new Intent("com.example.gagan.maymay.Home");
                        second.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(second);
                    }
                });
        profilebtn3 = (ImageButton) findViewById(R.id.profilebtn3);
        profilebtn3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent second = new Intent("com.example.gagan.maymay.activity_profile");
                        second.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(second);
                    }
                });
        createbtn3 = (ImageButton) findViewById(R.id.createbtn3);
        createbtn3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent second = new Intent("com.example.gagan.maymay.PostMeme");
                        second.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(second);
                    }
                });
    }
}
