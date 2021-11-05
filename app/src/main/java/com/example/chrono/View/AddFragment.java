package com.example.chrono.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.chrono.Controller.*;
import com.example.chrono.R;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {
    public AlertDialog.Builder dialogBuilder;
    public AlertDialog dialog;
    private Button pop_cancel, pop_save;
    Button add_btn;
    MyDatabaseHelper mydata;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add, container, false);


        EditText objectif_txt;
        EditText nb_heure_txt, nb_minute_txt, date_butoire;
        Button pop_save, pop_cancel;


        pop_cancel= view.findViewById(R.id.save2);
        pop_save= view.findViewById(R.id.save);
        objectif_txt = view.findViewById(R.id.objectif);
        nb_heure_txt = view.findViewById(R.id.editTextTime);
        nb_minute_txt=  view.findViewById(R.id.editTextTimeMinute);
        date_butoire = view.findViewById(R.id.date_prevu);

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

                MyDatabaseHelper mydata  = new MyDatabaseHelper(getContext());
                mydata.addBook( objectif_save,all_Time,date_butoire_save,la_date.trim() );
                Intent reload = new Intent(getContext(), Objectif.class);
                startActivity(reload);

                //dialog.dismiss();
                // Intent intent= new Intent(Objectif.this, Timer.class);
                // startActivity(intent);
            }
        });

        return  view;

    }
}