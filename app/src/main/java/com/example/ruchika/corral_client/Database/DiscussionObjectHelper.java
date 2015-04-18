package com.example.ruchika.corral_client.Database;

/**
 * Created by ruchika on 4/17/15.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ruchika on 4/13/15.
 */
public class DiscussionObjectHelper extends SQLiteOpenHelper {

    public static final String DISCUSSION = "Discussion";
    public static final String DISC_ID = "disc_id";
    public static final String DISC_PLACE = "place";
    public static final String DISC_TYPE = "type";
    public static final String DISC_ORDER = "orderdisc";
    public static final String DISC_NAME = "name";
    public static final String DISC_TIMER = "timername";
    private static final String DATABASE_NAME = "Users.db";
    private static final int DATABASE_VERSION = 1;

    // creation SQLite statement
    private static final String DATABASE_CREATE = "create table " + DISCUSSION
            + "(" + DISC_ID + " integer primary key autoincrement, "
            + DISC_NAME + " text not null, "
            + DISC_PLACE + " text, "
            + DISC_TYPE + " text, "
            + DISC_ORDER + " text, "
            + DISC_TIMER + " text);";

    public DiscussionObjectHelper(Context context) {

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

        db.execSQL("DROP TABLE IF EXISTS " + DISCUSSION);
        onCreate(db);
    }

}