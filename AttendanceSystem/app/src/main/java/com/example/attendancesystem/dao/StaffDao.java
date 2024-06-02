package com.example.attendancesystem.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.attendancesystem.DatabaseHelper;

public class StaffDao {

    private SQLiteDatabase database;

    public StaffDao(Context context) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public Cursor getUserById(String staff_id) {
        String[] columns = {"ID", "STAFF_ID","NAME","SURNAME","EMAIL","GENDER","DEPARTMENT" };
        String selection = "STAFF_ID" + "=?";
        String[] selectionArgs = {String.valueOf(staff_id)};
        return database.query("STAFF", columns, selection, selectionArgs, null, null, null);
    }
}
