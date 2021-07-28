package com.example.chrono;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME="chrono_new.db";
    private static final int DATABASE_VERSION=2;

    private static final String TABLE_NAME="my_library_db";
    private static final String COLUMN_ID="_id";
    private static final String COLUMN_OBJECTIF="Objectif";
    private static final String COLUMN_TIME="Time";
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
                COLUMN_DAT+ " VARCHAR);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    void addBook(String objectif,int time,String la_date){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv= new ContentValues();

        cv.put(COLUMN_OBJECTIF,objectif);
        cv.put(COLUMN_TIME,time);
        cv.put(COLUMN_DAT,la_date);

        long result = db.insert(TABLE_NAME,null,cv);
        if(result == -1){
            Toast.makeText(context,"Failed",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"Added Successfully !",Toast.LENGTH_SHORT).show();

        }

    }
    Cursor readAllData(){
        String query= "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db= this.getReadableDatabase();

        Cursor cursor= null;
        if(db!= null){
            cursor= db.rawQuery(query, null);
        }
        return  cursor;

    }
}
