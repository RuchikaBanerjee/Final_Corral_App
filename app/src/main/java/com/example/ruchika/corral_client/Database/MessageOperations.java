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
public class MessageOperations {

    // Database fields

    private MessageObjectHelper dbHelper;
    private String[] MESSAGE_TABLE_COLUMNS = {MessageObjectHelper.MSG_ID,MessageObjectHelper.MSG_SENDER,MessageObjectHelper.MSG_TIMER,MessageObjectHelper.MSG_VAL,MessageObjectHelper.MSG_TIMESTAMP};
    private SQLiteDatabase database;

    public MessageOperations(Context context) {
        dbHelper = new MessageObjectHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public MessageObject addResponse(String sender, String timer, String val, long timestamp) {

        ContentValues values = new ContentValues();

        values.put(MessageObjectHelper.MSG_SENDER, sender);
        values.put(MessageObjectHelper.MSG_TIMER,timer);
        values.put(MessageObjectHelper.MSG_VAL,val);
        values.put(MessageObjectHelper.MSG_TIMESTAMP,timestamp);

        long msgId = database.insert(MessageObjectHelper.MSG_TABLE, null, values);
        // database.insert(DiscussionObjectHelper.DISCUSSION, null, values);

        // now that the student is created return it ...
        Cursor cursor = database.query(MessageObjectHelper.MSG_TABLE,
                MESSAGE_TABLE_COLUMNS, MessageObjectHelper.MSG_ID + " = "
                        + msgId, null, null, null, null, null);

        cursor.moveToFirst();

        MessageObject newComment = parseMessage(cursor);
        cursor.close();
        return newComment;
    }

    public void deleteResponse(String timer) {

        //System.out.println("Comment deleted with id: " + id);
        database.delete(MessageObjectHelper.MSG_TABLE, MessageObjectHelper.MSG_TIMER
                + " = " + "'" + timer + "'", null);
    }

    public List<MessageObject> getAllResponses(String timer) {
        String query = "Select * FROM " + MessageObjectHelper.MSG_TABLE + " WHERE " + MessageObjectHelper.MSG_TIMER + "='" + timer + "'" + " ORDER BY " + MessageObjectHelper.MSG_TIMESTAMP;
        //SQLiteDatabase database = this.getWritableDatabase();

        List<MessageObject> list= new ArrayList<MessageObject>();
        Cursor cursor = database.rawQuery(query, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            MessageObject msgObject = new MessageObject();
            msgObject.setSender_name(cursor.getString(1));
            msgObject.setTimer_value(cursor.getString(2));
            msgObject.setMsg_value(cursor.getString(3));
            msgObject.setTimestamp(cursor.getLong(4));
            list.add(msgObject);
            Log.i("Db values:", cursor.getString(1) + cursor.getString(2) + cursor.getString(3) + cursor.getLong(4));
            cursor.moveToNext();
        }
        cursor.close();

        database.close();
        return list;
    }

    public long getNoOfRows() {


        return DatabaseUtils.queryNumEntries(database, DiscussionObjectHelper.DISCUSSION);
    }


    private MessageObject parseMessage(Cursor cursor) {

        MessageObject msgObject = new MessageObject();
        msgObject.setSender_name(cursor.getString(1));
        msgObject.setTimer_value(cursor.getString(2));
        msgObject.setMsg_value(cursor.getString(3));
        msgObject.setTimestamp(cursor.getLong(4));
        return msgObject;
    }
}
