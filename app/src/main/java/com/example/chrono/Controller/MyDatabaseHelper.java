package com.example.chrono.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME="chrono_new.db";
    private static final int DATABASE_VERSION=2;

    private static final String TABLE_NAME="my_library_db";
    private static final String COLUMN_ID="_id";
    private static final String COLUMN_OBJECTIF="Objectif";
    private static final String COLUMN_TIME="Time";
    private static final String COLUMN_TIME_ECOULE="Time_Ecoule";
    private static final String COLUMN_TIME_RESTANT="Time_Restant";
    private static final String COLUMN_DAT_PREVU="La_Date_Prevu";
    private static final String COLUMN_DAT_FIN="La_Date_Fin";
    private static final String COLUMN_ETAPE="Etape";
    private static final String COLUMN_DAT_INTERVENTION="La_Date_Intervention";
    private static final String COLUMN_DAT="La_Date";


    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context= context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query= "CREATE TABLE "+ TABLE_NAME+
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_OBJECTIF+ " TEXT, "+
                COLUMN_TIME+ " INTEGER,"+
                COLUMN_TIME_ECOULE+ " INTEGER,"+
                COLUMN_TIME_RESTANT+ " INTEGER,"+
                COLUMN_DAT_PREVU+ " VARCHAR,"+
                COLUMN_DAT_FIN+ " VARCHAR,"+
                COLUMN_ETAPE+ " VARCHAR,"+
                COLUMN_DAT_INTERVENTION+ " VARCHAR,"+
                COLUMN_DAT+ " VARCHAR);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public void addBook(String objectif, Long time,String la_date_prevu, String la_date){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv= new ContentValues();

        cv.put(COLUMN_OBJECTIF,objectif);
        cv.put(COLUMN_TIME,time);
        cv.put(COLUMN_TIME_ECOULE,0);
        cv.put(COLUMN_TIME_RESTANT,time);
        cv.put(COLUMN_DAT_PREVU,la_date_prevu);
        cv.put(COLUMN_DAT_FIN,"-");
        cv.put(COLUMN_ETAPE,"en attente");
        cv.put(COLUMN_DAT_INTERVENTION,la_date);
        cv.put(COLUMN_DAT,la_date);

        long result = db.insert(TABLE_NAME,null,cv);
        if(result == -1){
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Added Successfully !",Toast.LENGTH_SHORT).show();

        }

    }

    public Cursor readAllData(){
        String query= "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db= this.getReadableDatabase();

        Cursor cursor= null;
        if(db!= null){
            cursor= db.rawQuery(query, null);
        }
        return  cursor;

    }

    public void UpdateRemainTime(String id, Long time){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv= new ContentValues();

        cv.put(COLUMN_TIME,time);

        long result = db.update(TABLE_NAME,cv,"_id=?",new String[]{id});
        if(result == -1){
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
        }else{
           // Toast.makeText(context,"Added Successfully !",Toast.LENGTH_SHORT).show();

        }

    }

    public void delete_tache(String id){
        SQLiteDatabase db= this.getWritableDatabase();
        long result = db.delete(TABLE_NAME,"_id=?",new String[]{id});
        if(result == -1){
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
        }else{
             Toast.makeText(context,"Suppression reussie !",Toast.LENGTH_SHORT).show();

        }

    }

    public Cursor searchData( String data){
        String query= "SELECT * FROM " + TABLE_NAME +" WHERE " + COLUMN_OBJECTIF + " LIKE %" + data + "%" ;
        SQLiteDatabase db= this.getReadableDatabase();

        Cursor cursor= null;
        if(db!= null){
            cursor= db.rawQuery(query, null);
        }
        return  cursor;

    }


}
