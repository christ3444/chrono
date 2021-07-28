package com.example.chrono;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;

public class Objectif extends AppCompatActivity {

    public AlertDialog.Builder dialogBuilder;
    public AlertDialog dialog;
    private Button pop_cancel, pop_save;
    Button add_btn;
    RecyclerView recyclerView;
    CustomAdapter customAdapter;
    MyDatabaseHelper mydata;
    ArrayList<String> tache_id,tache_objectif,tache_time,la_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objectif);

        add_btn = findViewById(R.id.add_btn);
        recyclerView= findViewById(R.id.recycler);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              // Intent intent = new Intent(Objectif.this,Add_Objectif.class );
               //startActivity(intent);

                pop_add();
            }
        });

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Objectif.this,Timer.class );
                startActivity(intent);
            }
        });

       mydata= new MyDatabaseHelper(Objectif.this);
        tache_id= new ArrayList<>();
        tache_objectif= new ArrayList<>();
        tache_time= new ArrayList<>();
        la_date= new ArrayList<>();

        stroreDataInArray();
        customAdapter= new CustomAdapter(Objectif.this,tache_id,tache_objectif,tache_time,la_date);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Objectif.this));


        // CustomAdapter customAdapter = new CustomAdapter(Objectif.this,"1","java","100","12/03/2021","99h","14/03/3021");
        customAdapter = new CustomAdapter(Objectif.this,tache_id,tache_objectif,tache_time,la_date);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Objectif.this));


    }

    public void pop_add(){

        TextView objectif_txt, nb_heure_txt;

        dialogBuilder = new AlertDialog.Builder(this);
        final View pop_add_view= getLayoutInflater().inflate(R.layout.add_popup,null);
        pop_cancel= pop_add_view.findViewById(R.id.save2);
        pop_save= pop_add_view.findViewById(R.id.save);
        objectif_txt = pop_add_view.findViewById(R.id.editTextTextPersonName);
        nb_heure_txt = pop_add_view.findViewById(R.id.editTextTime);

        dialogBuilder.setView(pop_add_view);
        dialog= dialogBuilder.create();
        dialog.show();

        pop_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        pop_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /// code save
                String la_date = new Date().toString().substring(0, 10);
                MyDatabaseHelper mydata  = new MyDatabaseHelper(Objectif.this);
                mydata.addBook(objectif_txt.getText().toString().trim(),Integer.parseInt(nb_heure_txt.getText().toString()),la_date.trim() );
                Intent reload = new Intent(Objectif.this, Objectif.class);
                startActivity(reload);
                dialog.dismiss();
                // Intent intent= new Intent(Objectif.this, Timer.class);
               // startActivity(intent);
            }
        });
    }

    void stroreDataInArray(){
        Cursor cursor= mydata.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this,"no Data !",Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                tache_id.add(cursor.getString(0));
                tache_objectif.add(cursor.getString(1));
                tache_time.add(cursor.getString(2));
                la_date.add(cursor.getString(3));

            }
        }
    }

}