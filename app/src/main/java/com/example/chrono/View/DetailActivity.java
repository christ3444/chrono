package com.example.chrono.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chrono.Controller.*;
import com.example.chrono.R;

public class DetailActivity extends AppCompatActivity {

    public  String tache_id;

    Button pop_update,pop_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        pop_update= findViewById(R.id.update);
        pop_delete= findViewById(R.id.delete);

        Bundle bundle = getIntent().getExtras();

        if(bundle.getString("tache_id")!= null)
        {
            tache_id= bundle.getString("tache_id");
        }

        pop_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 Intent reload_intent = new Intent(DetailActivity.this, Objectif.class);
                 startActivity(reload_intent);
                 finish();
            }
        });

        pop_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper mydata  = new MyDatabaseHelper(DetailActivity.this);
                mydata.delete_tache(tache_id);
                Intent reload_intent = new Intent(DetailActivity.this, Objectif.class);
                startActivity(reload_intent);
                finish();



            }
        });
    }
}