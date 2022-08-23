package com.example.hoplyapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PostDBHelper extends SQLiteOpenHelper {
    static final String POST_TABLE = "POST_TABLE";
    static final String COL_POST_ID = "POST_ID";
    static final String COL_POST_USER_ID = "POST_USER_ID";
    static final String COL_POST_CONTEXT = "POST_CONTEXT";
    static final String COL_POST_TS = "POST_TS";


    public PostDBHelper(@Nullable Context context) {
        super(context, "posts.db", null, 1);
    }

    //called when the db is accessed for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createPostTable = "CREATE TABLE " + POST_TABLE +
                " (" + COL_POST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_POST_USER_ID + " TEXT, " +
                COL_POST_CONTEXT + " TEXT, " +
                COL_POST_TS + " TEXT," +
                "FOREIGN KEY(" + COL_POST_USER_ID + ") REFERENCES USER_TABLE(USER_USERNAME))";
        db.execSQL(createPostTable);
    }

    //called when version num changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) { }

    public boolean createPost(Posts post) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_POST_USER_ID, post.getUser_id());
        cv.put(COL_POST_CONTEXT, post.getContext());
        cv.put(COL_POST_TS, post.getStamp());
        long insert = db.insert(POST_TABLE, null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    //gathers all posts
    public List<Posts> getAllPosts() {
        List<Posts> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM " + POST_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString,null);

        if (cursor.moveToFirst()){
            do {
                int post_id = cursor.getInt(0);
                String post_user = cursor.getString(1);
                String post_context = cursor.getString(2);
                String stamp = cursor.getString(3);

                Posts newPost = new Posts (post_id,post_user,post_context,stamp);
                returnList.add(newPost);

            } while (cursor.moveToNext());
        } else {
            //nothing
        }
        cursor.close();
        db.close();
        return returnList;
    }

    //deletes selected post using post ID
    public boolean deletePost(Posts post){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + POST_TABLE + " WHERE " +
                                COL_POST_ID + " = ?";
        Cursor cursor = db.rawQuery(queryString, new String[]{String.valueOf(post.getId())});
        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }

    }
    public boolean deletePostbyUser(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM " + POST_TABLE + " WHERE " +
                COL_POST_USER_ID + " = ?";
        Cursor cursor = db.rawQuery(queryString, new String[]{username});
        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }

    }

    //edits post using post ID, replaces old context with updated context
    public boolean editPost(Posts post, String context){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "UPDATE " + POST_TABLE + " SET " + COL_POST_CONTEXT +
                                " = ?" + " WHERE " + COL_POST_ID + " = ?";
        Cursor cursor = db.rawQuery(queryString, new String[]{context, String.valueOf(post.getId())});
        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }

    }
}
