package com.example.chrono.View;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.example.chrono.Controller.MyDatabaseHelper;
import com.example.chrono.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    public  String tache_id;
    TextView reste_temps_txt,ecoule_temps_txt, date_fin_txt,date_save_txt;
    EditText tache_txt,time_prevu_txt;

    Button pop_update,pop_delete;
    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        pop_update= findViewById(R.id.update);
        pop_delete= findViewById(R.id.delete);
        calendarView = findViewById(R.id.id_calendarView);

        tache_txt      = findViewById(R.id.id_detail_pop_objectif);
        reste_temps_txt   = findViewById(R.id.id_time_restant);
        ecoule_temps_txt =findViewById(R.id.id_time_ecoule);
        date_fin_txt   = findViewById(R.id.id_date_fin_prevu);
        time_prevu_txt = findViewById(R.id.id_time_prevu);
        date_save_txt  = findViewById(R.id.id_date_save);



        Bundle bundle = getIntent().getExtras();

        if(bundle.getString("tache_id")!= null)
        {
            tache_id= bundle.getString("tache_id");
        }


        MyDatabaseHelper mydata  = new MyDatabaseHelper(this);
        Cursor cursor= mydata.detail_one_tache(tache_id);
        if(cursor.getCount() == 0){
            Toast.makeText(this,"no Data !",Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){
               // tache_id.add(cursor.getString(0));
                tache_txt.setText(cursor.getString(1));

                Long time_left_convert_prevu=Long.valueOf(String.valueOf(cursor.getString(2)));
                int heure = (int) (time_left_convert_prevu / 1000)/3600;
                int minutes = (int) ((time_left_convert_prevu / 1000) % 3600)/60;
                int second = (int) (((time_left_convert_prevu / 1000) % 3600)) % 60;

                String time_left_txt_prevu = String.format(Locale.getDefault(),"%02dh:%02dm:%02ds",heure, minutes,second);

                time_prevu_txt.setText(time_left_txt_prevu);

                Long time_left_convert_reste=Long.valueOf(String.valueOf(cursor.getString(4)));
                int heure1 = (int) (time_left_convert_reste / 1000)/3600;
                int minutes1 = (int) ((time_left_convert_reste / 1000) % 3600)/60;
                int second1 = (int) (((time_left_convert_reste / 1000) % 3600)) % 60;

                String time_left_txt_reste = String.format(Locale.getDefault(),"%02dh:%02dm:%02ds",heure1, minutes1,second1);

                reste_temps_txt.setText(time_left_txt_reste);

                Long time_left_convert_ecoule=Long.valueOf(String.valueOf(cursor.getString(4)))-Long.valueOf(String.valueOf(cursor.getString(2)));
                int heure2 = (int) (time_left_convert_ecoule / 1000)/3600;
                int minutes2 = (int) ((time_left_convert_ecoule / 1000) % 3600)/60;
                int second2 = (int) (((time_left_convert_ecoule / 1000) % 3600)) % 60;

                String time_left_txt_ecoule = String.format(Locale.getDefault(),"%02dh:%02dm:%02ds",heure2, minutes2,second2);


                ecoule_temps_txt.setText(time_left_txt_ecoule);

                date_fin_txt.setText(cursor.getString(5));


                ///////////////////////////////////////////

                List<EventDay> events = new ArrayList<>();

                Calendar calendar = Calendar.getInstance();


                String[] items1 = cursor.getString(5).split("-");
                String dd = items1[0];
                String month = items1[1];
                String year = items1[2];
                calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dd));
                calendar.set(Calendar.MONTH, Integer.parseInt(month) - 1);
                calendar.set(Calendar.YEAR, Integer.parseInt(year));
                events.add(new EventDay(calendar, R.drawable.dot));

                // Toast.makeText(getApplicationContext(),dd+"-"+month+"-"+year,Toast.LENGTH_LONG).show();
                calendarView.setEvents(events);
                ///////////////////////////////////////////////////////////

             /*   calendarView.setOnDayClickListener(new OnDayClickListener() {
                    @Override
                    public void onDayClick(EventDay eventDay) {
                        Calendar clickedDayCalendar = eventDay.getCalendar();
                    }
                });*/




                /////////////////////////////////////////////////


                date_save_txt.setText(cursor.getString(9));

            }
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