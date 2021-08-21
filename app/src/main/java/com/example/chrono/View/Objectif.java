package com.example.chrono.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chrono.Model.CustomAdapter;
import com.example.chrono.Controller.MyDatabaseHelper;
import com.example.chrono.R;

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
                pop_add();
            }
        });

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Objectif.this, Timer.class );
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

        TextView objectif_txt;
               EditText nb_heure_txt, nb_minute_txt, date_butoire;

        dialogBuilder = new AlertDialog.Builder(this);
        final View pop_add_view= getLayoutInflater().inflate(R.layout.add_popup,null);
        pop_cancel= pop_add_view.findViewById(R.id.save2);
        pop_save= pop_add_view.findViewById(R.id.save);
        objectif_txt = pop_add_view.findViewById(R.id.objectif);
        nb_heure_txt = pop_add_view.findViewById(R.id.editTextTime);
        nb_minute_txt=  pop_add_view.findViewById(R.id.editTextTimeMinute);
        date_butoire = pop_add_view.findViewById(R.id.date_prevu);

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
            Long all_Time;
            @Override
            public void onClick(View v) {
                if(!nb_heure_txt.getText().toString().equals("") && !nb_minute_txt.getText().toString().equals("")){
                    all_Time= Long.valueOf(nb_heure_txt.getText().toString())*3600000+
                            Long.valueOf(nb_minute_txt.getText().toString())*60000;
                }else{
                    if(!nb_minute_txt.getText().toString().equals("")){
                    all_Time= Long.valueOf(nb_minute_txt.getText().toString())*60000;}
                    else{
                        if (!nb_heure_txt.getText().toString().equals("")){
                            all_Time= Long.valueOf(nb_heure_txt.getText().toString())*3600000;
                        }
                        else{all_Time=Long.valueOf(0); }
                    }
                }
                String date_butoire_save ,objectif_save;
                if(objectif_txt.getText().toString().trim().equals("")){
                    objectif_save="non defini";
                }else {
                    objectif_save = objectif_txt.getText().toString().trim();
                }
                String la_date = new Date().toString().substring(0, 10);
                if(date_butoire.getText().toString().equals("")){
                    date_butoire_save=la_date;
                }else{date_butoire_save=date_butoire.getText().toString();}
                /// code save

                MyDatabaseHelper mydata  = new MyDatabaseHelper(Objectif.this);
                mydata.addBook(objectif_save,all_Time,date_butoire_save,la_date.trim() );
                Intent reload = new Intent(Objectif.this, Objectif.class);
                startActivity(reload);
                dialog.dismiss();
                // Intent intent= new Intent(Objectif.this, Timer.class);
               // startActivity(intent);
            }
        });
    }


    public void pop_detail(Context ct,String _dtache_id){

        Button pop_update,pop_delete;
        dialogBuilder = new AlertDialog.Builder(ct);
        LayoutInflater li = (LayoutInflater) ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View pop_detail_view= li.inflate(R.layout.detail_popup,null);
                //.getLayoutInflater().inflate(R.layout.detail_popup,null);
        pop_update= pop_detail_view.findViewById(R.id.update);
        pop_delete= pop_detail_view.findViewById(R.id.delete);


        dialogBuilder.setView(pop_detail_view);
        dialog= dialogBuilder.create();
        dialog.show();

        String id_for_delete = _dtache_id;
        Toast.makeText(ct.getApplicationContext(), id_for_delete, Toast.LENGTH_SHORT).show();
        pop_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        pop_delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                MyDatabaseHelper mydata  = new MyDatabaseHelper(ct);
                mydata.delete_tache(id_for_delete);
                dialog.dismiss();
            }
        });
    }

    void stroreDataInArray(){
        MyDatabaseHelper mydata  = new MyDatabaseHelper(Objectif.this);
        Cursor cursor= mydata.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this,"no Data !",Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
                tache_id.add(cursor.getString(0));
                tache_objectif.add(cursor.getString(1));
                tache_time.add(cursor.getString(2));
                la_date.add(cursor.getString(9));

            }
        }
    }

    public void hello(Context context){
        Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show();
    }

}