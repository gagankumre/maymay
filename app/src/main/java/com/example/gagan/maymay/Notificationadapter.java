package com.example.gagan.maymay;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by GAGAN on 05-02-2018.
 */

public class Notificationadapter extends RecyclerView.Adapter<Notificationadapter.MyViewHolder> {

    private List<Notificationcard> noticard;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, date, time;
        public ImageView profilepic;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.notiname);
            date = (TextView) view.findViewById(R.id.notidate);
            time = (TextView) view.findViewById(R.id.notitime);
            profilepic = (ImageView) view.findViewById(R.id.profilepic);

        }
    }


    public Notificationadapter(List<Notificationcard> noticard) {
        this.noticard = noticard;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notificationcard, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Notificationcard mynoti = noticard.get(position);
        holder.name.setText(mynoti.getName());
        holder.date.setText(mynoti.getDate());
        holder.time.setText(mynoti.getTime());
    }

    @Override
    public int getItemCount() {
        return noticard.size();
    }
}