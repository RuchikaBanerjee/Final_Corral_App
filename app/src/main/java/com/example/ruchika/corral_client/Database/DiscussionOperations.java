package com.example.ruchika.corral_client.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruchika on 4/13/15.
 */
public class DiscussionOperations {

    // Database fields

    private DiscussionObjectHelper dbHelper;
    private String[] DISCUSSION_TABLE_COLUMNS = {DiscussionObjectHelper.DISC_ID, DiscussionObjectHelper.DISC_NAME, DiscussionObjectHelper.DISC_PLACE, DiscussionObjectHelper.DISC_TYPE, DiscussionObjectHelper.DISC_ORDER, DiscussionObjectHelper.DISC_TIMER };
    private SQLiteDatabase database;

    public DiscussionOperations(Context context) {
        dbHelper = new DiscussionObjectHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public DiscussionObject addDiscussion(String name,String place, String type, String orderdisc, String timername ) {

        ContentValues values = new ContentValues();

        values.put(DiscussionObjectHelper.DISC_NAME,name);
        values.put(DiscussionObjectHelper.DISC_PLACE,place);
        values.put(DiscussionObjectHelper.DISC_TYPE,type);
        values.put(DiscussionObjectHelper.DISC_ORDER,orderdisc);
        values.put(DiscussionObjectHelper.DISC_TIMER,timername);

        long discId = database.insert(DiscussionObjectHelper.DISCUSSION, null, values);
       // database.insert(DiscussionObjectHelper.DISCUSSION, null, values);

        // now that the student is created return it ...
        Cursor cursor = database.query(DiscussionObjectHelper.DISCUSSION,
                DISCUSSION_TABLE_COLUMNS, DiscussionObjectHelper.DISC_ID + " = "
                        + discId, null, null, null, null, null);

        cursor.moveToFirst();

        DiscussionObject newComment = parseDiscussion(cursor);
        cursor.close();
        return newComment;
    }

    public void deleteDiscussion(String timername) {

        //System.out.println("Comment deleted with id: " + id);
        database.delete(DiscussionObjectHelper.DISCUSSION, DiscussionObjectHelper.DISC_TIMER
                + " = " + "'" + timername + "'", null);
    }

    //public List getAllDiscussions() {
        /*List discs = new ArrayList();

        Cursor cursor = database.query(DiscussionObjectHelper.DISCUSSION,
                DISCUSSION_TABLE_COLUMNS, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            DiscussionObject discussion_obj = parseDiscussion(cursor);
            discs.add(discussion_obj);
            cursor.moveToNext();
        }*/

        //cursor.close();
        //return discs;
    //}

    public List<DiscussionObject> getAllDiscussions() {
        String query = "Select * FROM " + DiscussionObjectHelper.DISCUSSION;
        //SQLiteDatabase db = this.getWritableDatabase();

        List<DiscussionObject> list= new ArrayList<DiscussionObject>();
        Cursor cursor = database.rawQuery(query, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            DiscussionObject dbObject = new DiscussionObject();
            dbObject.setName(cursor.getString(1));
            dbObject.setPlace(cursor.getString(2));
            dbObject.setType(cursor.getString(3));
            dbObject.setOrder(cursor.getString(4));
            dbObject.setTimername(cursor.getString(5));
            list.add(dbObject);
            Log.i("Db values:", cursor.getString(1) + cursor.getString(2) + cursor.getString(3) + cursor.getString(4) + cursor.getString(5));
            cursor.moveToNext();
        }
        cursor.close();

        database.close();
        return list;
    }

    public long getNoOfRows() {


        return DatabaseUtils.queryNumEntries(database, DiscussionObjectHelper.DISCUSSION);
    }


    private DiscussionObject parseDiscussion(Cursor cursor) {
        DiscussionObject discussionObject = new DiscussionObject();
        discussionObject.setName(cursor.getString(1));
        discussionObject.setPlace(cursor.getString(2));
        discussionObject.setType(cursor.getString(3));
        discussionObject.setOrder(cursor.getString(4));
        return discussionObject;
    }
}
