package com.example.ruchika.corral_client.Database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ruchika on 4/13/15.
 */
public class MessageObjectHelper extends SQLiteOpenHelper {

    public static final String MSG_TABLE = "msg_table";
    public static final String MSG_ID = "msg_id";
    public static final String MSG_SENDER = "sender";
    public static final String MSG_TIMER = "timer";
    public static final String MSG_VAL = "msg_value";
    public static final String MSG_TIMESTAMP = "timestamp";

    private static final String DATABASE_NAME = "msg.db";
    private static final int DATABASE_VERSION = 1;

    // creation SQLite statement
    private static final String DATABASE_CREATE = "create table " + MSG_TABLE
            + "(" + MSG_ID + " integer primary key autoincrement, "
            + MSG_SENDER + " text not null, "
            + MSG_TIMER + " text, "
            + MSG_VAL + " text, "
            + MSG_TIMESTAMP + " text);";



    public MessageObjectHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
        System.out.println(DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you should do some logging in here
        // ..

        db.execSQL("DROP TABLE IF EXISTS " + MSG_TABLE);
        onCreate(db);
    }

}