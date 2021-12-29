package com.example.chrono.Model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chrono.R;
import com.example.chrono.View.DetailActivity;
import com.example.chrono.View.Objectif;
import com.example.chrono.View.Timer;

import java.util.ArrayList;
import java.util.Locale;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    public AlertDialog.Builder dialogBuilder;
    public AlertDialog dialog;
   private Context context;
    private ArrayList tache_id, tache, time,date,remain_time,last_date;
    Long time_left_convert;

    public CustomAdapter(Context context,ArrayList tache_id,ArrayList tache, ArrayList remain_time, ArrayList last_date) {

        this.context = context;
        this.tache_id = tache_id;
        this.tache = tache;
        this.remain_time = remain_time;
        this.last_date = last_date;
    }
/* public  CustomAdapter(Context context, ArrayList tache_id, ArrayList tache, ArrayList time, ArrayList date, ArrayList remain_time, ArrayList last_date) {
        this.context = context;
        this.tache_id = tache_id;
        this.tache = tache;
        this.time = time;
        this.date = date;
        this.remain_time = remain_time;
        this.last_date = last_date;
    }*/

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row2,parent,false);

        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tache.setText(String.valueOf(tache.get(position)));
        holder.last_dat.setText(String.valueOf(last_date.get(position)));
                //String.valueOf(last_date.get(position)));
        time_left_convert=Long.valueOf(String.valueOf(remain_time.get(position)));
        int heure = (int) (time_left_convert / 1000)/3600;
        int minutes = (int) ((time_left_convert / 1000) % 3600)/60;
        int second = (int) (((time_left_convert / 1000) % 3600)) % 60;

        String time_left_txt = String.format(Locale.getDefault(),"%02dh:%02dm:%02ds",heure, minutes,second);
        holder.time.setText(time_left_txt);

       // holder.my_recycler_objectif.setOnClickListener(new View.OnClickListener() {
        holder.tache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent timer_intent = new Intent(v.getContext(), Timer.class);
                    timer_intent.putExtra("tache_id",String.valueOf(tache_id.get(position)));
                    timer_intent.putExtra("time",String.valueOf(remain_time.get(position)));
                    timer_intent.putExtra("objectif",String.valueOf(tache.get(position)));
                    context.startActivity(timer_intent);
            }
        });
        holder.time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent timer_intent = new Intent(v.getContext(), Timer.class);
                timer_intent.putExtra("tache_id",String.valueOf(tache_id.get(position)));
                timer_intent.putExtra("time",String.valueOf(remain_time.get(position)));
                timer_intent.putExtra("objectif",String.valueOf(tache.get(position)));
                context.startActivity(timer_intent);
            }
        });
        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Objectif objectif = new Objectif();
               // objectif.hello(context);
                String delete_id = String.valueOf(tache_id.get(position));
                //objectif.pop_detail(context,delete_id);

                Intent detail_intent = new Intent(view.getContext(), DetailActivity.class);
                detail_intent.putExtra("tache_id",String.valueOf(tache_id.get(position)));
                context.startActivity(detail_intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return tache_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

         TextView tache_id,tache, last_dat,time;
         ImageView more;
        LinearLayout my_recycler_objectif;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tache= itemView.findViewById(R.id.objectif);
            last_dat = itemView.findViewById(R.id.last_dat);
            time = itemView.findViewById(R.id.time);
            more = itemView.findViewById(R.id.options);
            my_recycler_objectif = (LinearLayout)itemView.findViewById(R.id.recycler_objectif);
        }
    }
}

