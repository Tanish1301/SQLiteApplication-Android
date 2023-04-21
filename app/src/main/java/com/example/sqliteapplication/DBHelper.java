package com.example.sqliteapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(MainActivity Context) {
        super(Context,"MyUserDatabase.db", null,1);
    }



    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table UserRecord ( SN INT primary key, NAME Text , CONTACT Text, DOB Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists UserRecord");
    }

    public Boolean insertUserData(String name, String contact, String Dob ){
        //to get database connection
        SQLiteDatabase DB = this.getWritableDatabase();
        //to write content in database
        ContentValues contentvalue = new ContentValues();
        //assign values to content variable
        contentvalue.put("NAME", name);
        contentvalue.put("CONTACT", contact);
        contentvalue.put("DOB", Dob);

        //execute insert query
        Long result = DB.insert("UserRecord",null,contentvalue);

        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    public Boolean updateUserData(String name, String contact, String Dob ){
        //to get database connection
        SQLiteDatabase DB = this.getWritableDatabase();
        //to write content in database
        ContentValues contentvalue = new ContentValues();
        //assign values to content variable
        //contentvalue.put("NAME", name);
        contentvalue.put("CONTACT", contact);
        contentvalue.put("DOB", Dob);

        //find current user record
        Cursor currentRecord = DB.rawQuery("select * from UserRecord where NAME=?", new String[]{name});

        if(currentRecord.getCount()>0){
            int result = DB.update("UserRecord", contentvalue, "NAME=?", new String[]{name});
            if(result == -1){
                return false;
            }else {
                return true;
            }
        }else{
            return false;
        }

    }

    public Boolean DeleteUserData(String name ) {
        //to get database connection
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor findRecord = DB.rawQuery("select * from UserRecord where NAME=?",new String[]{name});

        if (findRecord.getCount()>0){
            int result = DB.delete("UserRecord","NAME=?", new String[]{name});
            if(result == -1){
                return false;
            }
            else return true;
        }
        else{
            return false;
        }

    }

    public Cursor ViewUserData() {
        //to get database connection
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor findAllRecords = DB.rawQuery("select * from UserRecord ",null);
        return findAllRecords;
    }

}
