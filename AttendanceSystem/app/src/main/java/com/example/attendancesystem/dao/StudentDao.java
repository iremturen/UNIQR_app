package com.example.attendancesystem.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.attendancesystem.DatabaseHelper;

public class StudentDao {

    private SQLiteDatabase database;

    public StudentDao(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public Cursor getUserById(String student_id) {
        String[] columns = {"ID", "STUDENT_ID","NAME","SURNAME","EMAIL","GENDER","GRADE" };
        String selection = "STUDENT_ID" + "=?";
        String[] selectionArgs = {String.valueOf(student_id)};
        return database.query("STUDENT", columns, selection, selectionArgs, null, null, null);
    }
}
