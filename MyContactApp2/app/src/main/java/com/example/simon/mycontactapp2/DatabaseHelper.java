package com.example.simon.mycontactapp2;

/**
 * Created by Simon on 5/17/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Simon on 5/11/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "contact.db";
    public static final String TABLE_NAME = "contact_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";

    public static final String COL_3 = "EMAIL";
    public static final String COL_4 = "ADDRESS";




    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME_TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name) {

        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);


        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    public Cursor getAllData() {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from" + TABLE_NAME, null);
        return res;
    }

}
