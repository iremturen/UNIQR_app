package com.example.attendancesystem.models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Notes {

    private String NOTES , TITLE, CREATION_DATE, USER_ID;
    private Integer ID;

    public Notes() {
    }

    public Notes(String NOTES, String CREATION_DATE, String TITLE, String USER_ID, Integer ID) {
        this.NOTES = NOTES;
        this.CREATION_DATE = CREATION_DATE;
        this.USER_ID = USER_ID;
        this.ID = ID;
        this.TITLE = TITLE;
    }

    public String getNOTES() {
        return NOTES;
    }

    public void setNOTES(String NOTES) {
        this.NOTES = NOTES;
    }

    public String getCREATION_DATE() {
        return CREATION_DATE;
    }

    public void setCREATION_DATE(String CREATION_DATE) {
        this.CREATION_DATE = CREATION_DATE;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    static public ArrayList<Notes> getNotesData(Context context , String user_id){


        ArrayList <Notes> list = new ArrayList<Notes>();

        ArrayList<Integer> IdList = new ArrayList<>();
        ArrayList<String> userIdList = new ArrayList<>();
        ArrayList<String> notesList = new ArrayList<>();
        ArrayList<String> titleList = new ArrayList<>();
        ArrayList<String> creationDateList = new ArrayList<>();



        try {
            SQLiteDatabase db = context.openOrCreateDatabase("AttendanceDatabase.sqlite", Context.MODE_PRIVATE, null);
            Cursor cursor = db.rawQuery("SELECT * FROM NOTES WHERE USER_ID = ?", new String[]{user_id});
            int idIndex=cursor.getColumnIndex("ID");
            int userIdIndex=cursor.getColumnIndex("USER_ID");
            int creationDateIndex = cursor.getColumnIndex("CREATION_DATE");
            int titleIndex = cursor.getColumnIndex("TITLE");
            int noteIndex = cursor.getColumnIndex("NOTES");


            while (cursor.moveToNext()) {
                IdList.add(cursor.getInt(idIndex));
                userIdList.add(cursor.getString(userIdIndex));
                creationDateList.add(cursor.getString(creationDateIndex));
                titleList.add(cursor.getString(titleIndex));
                notesList.add(cursor.getString(noteIndex));


            }

            cursor.close();

            for (int i = 0; i < IdList.size(); i++) {
                Notes h = new Notes();
                h.setID(IdList.get(i));
                h.setNOTES(notesList.get(i));
                h.setCREATION_DATE(creationDateList.get(i));
                h.setTITLE(titleList.get(i));
                h.setUSER_ID(userIdList.get(i));


                list.add(h);
            }

        }
        catch (Exception e) {

        }
        return list;

    }
}
