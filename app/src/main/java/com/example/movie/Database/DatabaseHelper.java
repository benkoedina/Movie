package com.example.movie.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.movie.Model.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "datamanager";

    //table names
    private static final String TABLE_USER = "user";

    //common column names
    private static final String USER_ID = "user_id";

    //user column names
    private static final String USER_USERNAME="user_username";
    private static final String USER_PW="user_pw";
    private static final String USER_PHOTO="user_photo";

    //create table statements
    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + "(" + USER_ID + " INTEGER PRIMARY KEY," + USER_USERNAME
            + " TEXT," + USER_PW + " TEXT," + USER_PHOTO + " TEXT" +")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // create new tables
        onCreate(db);

    }

    public long createUser(User user) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USER_ID, user.getUser_id());
        values.put(USER_USERNAME, user.getName());
        values.put(USER_PW, user.getPassword());
        values.put(USER_PHOTO,user.getImage_path());

        // insert row
        long id = db.insert(TABLE_USER, null, values);
        return id;
    }

    //get a User
    public User getUser(long user_id) {

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE "
                + USER_ID + " = " + user_id;

        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null)
            c.moveToFirst();

        User u = new User();
        u.setUser_id(c.getInt(c.getColumnIndex(USER_ID)));
        u.setName((c.getString(c.getColumnIndex(USER_USERNAME))));
        u.setPassword(c.getString(c.getColumnIndex(USER_PW)));
        u.setImage_path(c.getString(c.getColumnIndex(USER_PHOTO)));

        return u;
    }
    public void deleteAllUser()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_USER );
    }
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                User u = new User();
                u.setUser_id(c.getInt(c.getColumnIndex(USER_ID)));
                u.setName((c.getString(c.getColumnIndex(USER_USERNAME))));
                u.setPassword(c.getString(c.getColumnIndex(USER_PW)));
                u.setImage_path(c.getString(c.getColumnIndex(USER_PHOTO)));

                // adding to todo list
                users.add(u);
            } while (c.moveToNext());
        }
        return users;
    }


    //update user : email+pw
    public int updateUser(User user) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USER_PHOTO,user.getImage_path());
        values.put(USER_PW, user.getPassword());

        // updating row
        return db.update(TABLE_USER, values, USER_ID + " = ?",
                new String[] { String.valueOf(user.getUser_id()) });
    }

    public int updateUserPhoto(User user) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USER_PHOTO,user.getImage_path());

        // updating row
        return db.update(TABLE_USER, values, USER_ID + " = ?",
                new String[] { String.valueOf(user.getUser_id()) });
    }
    public int updateUserPassword(User user) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(USER_PW, user.getPassword());

        // updating row
        return db.update(TABLE_USER, values, USER_ID + " = ?",
                new String[] { String.valueOf(user.getUser_id()) });
    }

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
