package com.example.hoplyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ReactionsDBHelper extends SQLiteOpenHelper {
    static final String REACTION_TABLE = "REACTION_TABLE";
    static final String COL_REACTION_POST_ID = "REACTION_POST_ID";
    static final String COL_REACTION_USER_ID = "REACTION_USER_ID";
    static final String COL_REACTION_TYPE = "REACTION_TYPE";
    static final String COL_REACTION_TS = "REACTION_TS";


    public ReactionsDBHelper(@Nullable Context context) {
        super(context, "reactions.db", null, 1);
    }

    //called when the db is accessed for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createPostTable = "CREATE TABLE " + REACTION_TABLE +
                " (" + COL_REACTION_USER_ID + " TEXT, " +
                COL_REACTION_POST_ID + " TEXT, " +
                COL_REACTION_TYPE + " INTEGER, " +
                COL_REACTION_TS + " TEXT, " +
                "FOREIGN KEY(" + COL_REACTION_USER_ID + ", " + COL_REACTION_POST_ID  + ") REFERENCES USER_TABLE(USER_USERNAME, POST_ID), " +
                "PRIMARY KEY(" + COL_REACTION_POST_ID + ", " + COL_REACTION_USER_ID + "))";
        db.execSQL(createPostTable);

    }

    //called when version num changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) { }

    //adds a reaction into the db
    public boolean createReaction(Reactions reaction) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_REACTION_USER_ID, reaction.getUser_id());
        cv.put(COL_REACTION_POST_ID, reaction.getPost_id());
        cv.put(COL_REACTION_TYPE, reaction.getType());
        cv.put(COL_REACTION_TS, reaction.getStamp());
        long insert = db.insert(REACTION_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    //deletes reaction from db
    public boolean deleteReaction(String username, int post_id){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + REACTION_TABLE + " WHERE " +
                COL_REACTION_USER_ID + " = ? AND " + COL_REACTION_POST_ID + " = ?";
        Cursor cursor = db.rawQuery(queryString, new String[]{username, String.valueOf(post_id)});
        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }

    }

    public boolean deleteReactionByUser(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + REACTION_TABLE + " WHERE " +
                COL_REACTION_USER_ID + " = ?";
        Cursor cursor = db.rawQuery(queryString, new String[]{username});
        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }

    }
    //updates reaction in db
    public boolean editReaction(int reaction_update, String username, int post_id){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "UPDATE " + REACTION_TABLE + " SET " + COL_REACTION_TYPE +
                " = ?" + " WHERE " + COL_REACTION_USER_ID + " = ? AND " + COL_REACTION_POST_ID + " = ?";
        Cursor cursor = db.rawQuery(queryString, new String[]{String.valueOf(reaction_update), username, String.valueOf(post_id)});
        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }

    }

    //checks if reaction already exists using user id and post id
    public boolean checkExistingReaction(String username, int post_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "SELECT * FROM " + REACTION_TABLE + " WHERE " +
                COL_REACTION_USER_ID + " = ? AND " + COL_REACTION_POST_ID + " = ?";
        Cursor cursor = db.rawQuery(queryString, new String[]{username, String.valueOf(post_id)});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    //gets all reactions into a list
    public List<Reactions> getAllReactions(int post_id) {
        List<Reactions> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + REACTION_TABLE +
                            " WHERE " +  COL_REACTION_POST_ID + " = ?";;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString,new String[] {String.valueOf(post_id)});

        if (cursor.moveToFirst()){
            do {
                String r_user_id = cursor.getString(0);
                int r_post_id = cursor.getInt(1);
                int r_type = cursor.getInt(2);
                String stamp = cursor.getString(3);

                Reactions newReaction = new Reactions (r_user_id,r_post_id,r_type,stamp);
                returnList.add(newReaction);

            } while (cursor.moveToNext());
        } else {
            //nothing
        }
        cursor.close();
        db.close();
        return returnList;
    }

}