package com.example.ruchika.corral_client.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruchika on 4/13/15.
 */
public class ChatOperations {

    // Database fields

    private DatabaseChatHelper dbHelper;
    private String[] CHAT_TABLE_COLUMNS = { DatabaseChatHelper.USER_ID, DatabaseChatHelper.USER_NAME, DatabaseChatHelper.USER_REG_ID, DatabaseChatHelper.USER_MESSAGE };
    private SQLiteDatabase database;

    public ChatOperations(Context context) {
        dbHelper = new DatabaseChatHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public ChatDetails addChat(String name,String msg) {

        ContentValues values = new ContentValues();

        values.put(DatbaseHelper.USER_NAME, name);
        values.put(DatabaseChatHelper.USER_MESSAGE,msg);

        long userId = database.insert(DatbaseHelper.USERS, null, values);

        // now that the student is created return it ...
        Cursor cursor = database.query(DatbaseHelper.USERS,
                CHAT_TABLE_COLUMNS, DatbaseHelper.USER_ID + " = "
                        + userId, null, null, null, null);

        cursor.moveToFirst();

        ChatDetails newComment = parseUser(cursor);
        cursor.close();
        return newComment;
    }

    public void deleteStudent(ChatDetails comment) {
        long id = comment.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(DatbaseHelper.USERS, DatbaseHelper.USER_ID
                + " = " + id, null);
    }

    public List getAllUsers() {
        List users = new ArrayList();

        Cursor cursor = database.query(DatbaseHelper.USERS,
                CHAT_TABLE_COLUMNS, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ChatDetails student = parseUser(cursor);
            users.add(student);
            cursor.moveToNext();
        }

        cursor.close();
        return users;
    }

    private ChatDetails parseUser(Cursor cursor) {
        ChatDetails user = new ChatDetails();
        user.setId((cursor.getInt(0)));
        user.setName(cursor.getString(1));
        user.setRegId(cursor.getString(2));
        user.setMessage(cursor.getString(3));
        return user;
    }
}
