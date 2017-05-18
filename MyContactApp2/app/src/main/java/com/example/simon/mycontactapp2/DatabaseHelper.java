package com.example.simon.mycontactapp2;

/**
 * Created by Simon on 5/17/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
    public static final String COL_5 = "NUMBER";




    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, EMAIL TEXT, ADDRESS TEXT, NUMBER TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String email, String address, String number) {

        Log.d("MyContacts", "Cant insert data1");
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues(5);
        contentValues.put(COL_2, name);

        Log.d("MyContacts", "Cant insert data2");
        //for the tasks
        contentValues.put(COL_3, email);
        Log.d("MyContacts", "Cant insert data3");
        contentValues.put(COL_4, address);
        contentValues.put(COL_5, number);


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
