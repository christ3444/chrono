package com.example.chrono.View;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chrono.Controller.MyDatabaseHelper;
import com.example.chrono.R;

import java.util.Calendar;
import java.util.Date;

public class AddActivity extends AppCompatActivity {


    public AlertDialog.Builder dialogBuilder;
    public AlertDialog dialog;
    private Button pop_cancel, pop_save;
    EditText objectif_txt;
    EditText nb_heure_txt, nb_minute_txt, date_butoire;
    Button add_btn;
    MyDatabaseHelper mydata;

    int mYear, mMonth, mDay;
   // int mHour, mMinute;
    //TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        pop_save = findViewById(R.id.save);
        objectif_txt = findViewById(R.id.objectif);
        nb_heure_txt = findViewById(R.id.editTextTime);
        nb_minute_txt = findViewById(R.id.editTextTimeMinute);
        date_butoire = findViewById(R.id.date_prevu);


        date_butoire.setOnTouchListener((view, motionEvent) -> {
            if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(this,
                        (view1, year, monthOfYear, dayOfMonth) -> {
                            date_butoire.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            datePickerDialog.dismiss();
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
            return true;
        });


      /*  nb_heure_txt.setOnTouchListener((view, motionEvent) -> {
            if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                timePickerDialog = new TimePickerDialog(this,
                        (view12, hourOfDay, minute) -> {
                            nb_heure_txt.setText(hourOfDay + ":" + minute);
                            timePickerDialog.dismiss();
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
            return true;
        });*/

        pop_save.setOnClickListener(new View.OnClickListener() {
            Long all_Time;

            @Override
            public void onClick(View v) {
                if (!nb_heure_txt.getText().toString().equals("") && !nb_minute_txt.getText().toString().equals(""))
                 {
                    all_Time = Long.valueOf(nb_heure_txt.getText().toString())* 3600000 + Long.valueOf(nb_minute_txt.getText().toString()) * 60000;

                } else {
                    if (!nb_minute_txt.getText().toString().equals("")) {
                        all_Time = Long.valueOf(nb_minute_txt.getText().toString()) * 60000;
                    } else {
                        if (!nb_heure_txt.getText().toString().equals("")) {
                            all_Time = Long.valueOf(nb_heure_txt.getText().toString()) * 3600000;
                        } else {
                            all_Time = 0L;
                        }
                    }
                }

                String date_butoire_save, objectif_save;
                if (objectif_txt.getText().toString().trim().equals("")) {
                    objectif_save = "non defini";
                } else {
                    objectif_save = objectif_txt.getText().toString().trim();
                }
                String la_date = new Date().toString().substring(0, 10);
                if (date_butoire.getText().toString().equals("")) {
                    date_butoire_save = la_date;
                } else {
                    date_butoire_save = date_butoire.getText().toString();
                }
                /// code save

                MyDatabaseHelper mydata = new MyDatabaseHelper(AddActivity.this);
                mydata.addBook(objectif_save, all_Time, date_butoire_save, la_date.trim());
                Intent reload = new Intent(AddActivity.this, Objectif.class);
                startActivity(reload);


            }
        });
    }
}

