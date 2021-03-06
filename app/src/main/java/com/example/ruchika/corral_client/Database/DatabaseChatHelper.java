package com.example.ruchika.corral_client.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ruchika on 4/13/15.
 */
public class DatabaseChatHelper extends SQLiteOpenHelper{

    public static final String CHATS = "CHATS";
    public static final String USER_ID = "_id";
    public static final String USER_NAME = "_name";
    public static final String USER_REG_ID = "reg_id";
    public static final String USER_MESSAGE = "msg";

    private static final String DATABASE_NAME = "Chats.db";
    private static final int DATABASE_VERSION = 1;

    // creation SQLite statement
    private static final String DATABASE_CREATE = "create table " + CHATS
            + "(" + USER_ID + " integer primary key autoincrement, "
            + USER_NAME + " text not null, " + USER_REG_ID + " text, " + USER_MESSAGE + " text);";

    public DatabaseChatHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you should do some logging in here
        // ..

        db.execSQL("DROP TABLE IF EXISTS " + CHATS);
        onCreate(db);
    }


}
