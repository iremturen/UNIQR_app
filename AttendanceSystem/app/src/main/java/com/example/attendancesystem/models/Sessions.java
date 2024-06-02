package com.example.attendancesystem.models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Sessions {

    Integer id;
    String session_id;
    String creation_time;
    String staff_id;
    String course_id;

    public Sessions(){

    }

    public Sessions(Integer id, String session_id, String creation_time, String staff_id, String course_id) {
        this.id = id;
        this.session_id = session_id;
        this.creation_time = creation_time;
        this.staff_id = staff_id;
        this.course_id = course_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getCreation_time() {
        return creation_time;
    }

    public void setCreation_time(String creation_time) {
        this.creation_time = creation_time;
    }

    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    static public ArrayList<Sessions> getSessionsByCourseId(Context context , String course_id){
        ArrayList <Sessions> list = new ArrayList<Sessions>();

        ArrayList<Integer> IdList = new ArrayList<>();
        ArrayList<String> sessionIdList = new ArrayList<>();
        ArrayList<String> staffIdList = new ArrayList<>();
        ArrayList<String> courseIdList = new ArrayList<>();
        ArrayList<String> creationTimeList = new ArrayList<>();

        try {
            SQLiteDatabase db = context.openOrCreateDatabase("AttendanceDatabase.sqlite", Context.MODE_PRIVATE, null);
            Cursor cursor = db.rawQuery("SELECT * FROM SESSIONS WHERE COURSE_ID = ? ", new String[]{course_id});
            int idIndex=cursor.getColumnIndex("ID");
            int sessionIdIndex=cursor.getColumnIndex("SESSION_ID");
            int courseIdIndex=cursor.getColumnIndex("COURSE_ID");
            int creationTimeIndex = cursor.getColumnIndex("CREATION_TIME");
            int staffIdIndex = cursor.getColumnIndex("STAFF_ID");

            while (cursor.moveToNext()) {
                IdList.add(cursor.getInt(idIndex));
                sessionIdList.add(cursor.getString(sessionIdIndex));
                courseIdList.add(cursor.getString(courseIdIndex));
                creationTimeList.add(cursor.getString(creationTimeIndex));
                staffIdList.add(cursor.getString(staffIdIndex));

            }

            cursor.close();

            for (int i = 0; i < IdList.size(); i++) {
                Sessions h = new Sessions();
                h.setId(IdList.get(i));
                h.setCreation_time(creationTimeList.get(i));
                h.setSession_id(sessionIdList.get(i));
                h.setCourse_id(courseIdList.get(i));
                h.setStaff_id(staffIdList.get(i));

                list.add(h);
            }

        }
        catch (Exception e) {

        }
        return list;

    }

}
