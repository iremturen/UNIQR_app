package com.example.attendancesystem;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {

    private static DatabaseHelper dbHelper;
    private static SQLiteDatabase database;

    public static void initialize(Context context) {
        if (dbHelper == null) {
            dbHelper = new DatabaseHelper(context);
            database = dbHelper.getWritableDatabase();
        }
    }

    public static SQLiteDatabase getDatabase() {
        return database;
    }

    public static void closeDatabase() {
        if (dbHelper != null) {
            dbHelper.close();
            dbHelper = null;
        }
    }
}
