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
public class UserOperations {

    // Database fields

    private DatbaseHelper dbHelper;
    private String[] USER_TABLE_COLUMNS = { DatbaseHelper.USER_ID, DatbaseHelper.USER_NAME, DatbaseHelper.USER_REG_ID };
    private SQLiteDatabase database;

    public UserOperations(Context context) {
        dbHelper = new DatbaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public UserDetails addUser(String name,String regId) {

        ContentValues values = new ContentValues();

        values.put(DatbaseHelper.USER_NAME, name);
        values.put(DatbaseHelper.USER_REG_ID,regId);

        long userId = database.insert(DatbaseHelper.USERS, null, values);

        // now that the student is created return it ...
        Cursor cursor = database.query(DatbaseHelper.USERS,
                USER_TABLE_COLUMNS, DatbaseHelper.USER_ID + " = "
                        + userId, null, null, null, null);

        cursor.moveToFirst();

        UserDetails newComment = parseUser(cursor);
        cursor.close();
        return newComment;
    }

    public void deleteStudent(UserDetails comment) {
        long id = comment.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(DatbaseHelper.USERS, DatbaseHelper.USER_ID
                + " = " + id, null);
    }

    public List getAllUsers() {
        List users = new ArrayList();

        Cursor cursor = database.query(DatbaseHelper.USERS,
                USER_TABLE_COLUMNS, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            UserDetails student = parseUser(cursor);
            users.add(student);
            cursor.moveToNext();
        }

        cursor.close();
        return users;
    }

    private UserDetails parseUser(Cursor cursor) {
        UserDetails student = new UserDetails();
        student.setId((cursor.getInt(0)));
        student.setName(cursor.getString(1));
        student.setRegId(cursor.getString(2));
        return student;
    }
}
