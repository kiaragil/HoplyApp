package com.example.hoplyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class UserDBHelper extends SQLiteOpenHelper {
    static final String USER_TABLE = "USER_TABLE";
    static final String COL_USER_USERNAME = "USER_USERNAME"; //aka ID
    static final String COL_USER_NAME = "USER_NAME";
    static final String COL_USER_RPW = "USER_RPW";
    static final String COL_USER_PW = "USER_PW";
    static final String COL_USER_TS = "USER_TS";


    public UserDBHelper(@Nullable Context context) {
        super(context, "users.db", null, 1);
    }

    //called when the db is accessed for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTable = "CREATE TABLE " + USER_TABLE +
                " (" + COL_USER_USERNAME + " TEXT PRIMARY KEY, " +
                COL_USER_NAME + " TEXT, " +
                COL_USER_PW + " TEXT, " +
                COL_USER_RPW + " TEXT, " +
                COL_USER_TS + " TEXT)";
        db.execSQL(createUserTable);

    }

    //called when version num changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    }

    //adds user into db
    public boolean createUser(Users user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_USER_USERNAME, user.getUsername());
        cv.put(COL_USER_NAME, user.getName());
        cv.put(COL_USER_PW, user.getPassword());
        cv.put(COL_USER_RPW, user.getRepassword());
        cv.put(COL_USER_TS, user.getStamp());
        long insert = db.insert(USER_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    //deletes user from db
    public boolean deleteUser(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + USER_TABLE + " WHERE " +
                COL_USER_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(queryString, new String[]{username});
        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }

    }

    // checks if user already exists
    public boolean checkExistingUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "SELECT * FROM " + USER_TABLE + " WHERE " +
                                COL_USER_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(queryString, new String[]{username.toLowerCase()});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    //checks if user and password correspond
    public boolean checkUserPassword(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "SELECT * FROM " + USER_TABLE + " WHERE " +
                                COL_USER_USERNAME + " = ? AND " + COL_USER_PW + " = ?";
        Cursor cursor = db.rawQuery(queryString, new String[]{username, password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
