package com.example.attendancesystem.models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;
import java.util.ArrayList;

public class Courses implements Serializable {

    private String COURSE_CODE , COURSE_NAME, GRADE, SEMESTER,CLASSROOM, TIME,TOTAL_TIME,STAFF_ID,STAFF;
    private Integer ID;

    public Courses(){

    }

    public Courses(String COURSE_CODE, String COURSE_NAME, String GRADE, String SEMESTER, String CLASSROOM, String TIME, String TOTAL_TIME, String STAFF_ID,String STAFF, Integer ID) {
        this.COURSE_CODE = COURSE_CODE;
        this.COURSE_NAME = COURSE_NAME;
        this.GRADE = GRADE;
        this.SEMESTER = SEMESTER;
        this.CLASSROOM = CLASSROOM;
        this.TIME = TIME;
        this.TOTAL_TIME = TOTAL_TIME;
        this.STAFF_ID = STAFF_ID;
        this.STAFF = STAFF;
        this.ID = ID;
    }

    public String getSTAFF() {
        return STAFF;
    }

    public void setSTAFF(String STAFF) {
        this.STAFF = STAFF;
    }

    public String getCOURSE_CODE() {
        return COURSE_CODE;
    }

    public void setCOURSE_CODE(String COURSE_CODE) {
        this.COURSE_CODE = COURSE_CODE;
    }

    public String getCOURSE_NAME() {
        return COURSE_NAME;
    }

    public void setCOURSE_NAME(String COURSE_NAME) {
        this.COURSE_NAME = COURSE_NAME;
    }

    public String getGRADE() {
        return GRADE;
    }

    public void setGRADE(String GRADE) {
        this.GRADE = GRADE;
    }

    public String getSEMESTER() {
        return SEMESTER;
    }

    public void setSEMESTER(String SEMESTER) {
        this.SEMESTER = SEMESTER;
    }

    public String getCLASSROOM() {
        return CLASSROOM;
    }

    public void setCLASSROOM(String CLASSROOM) {
        this.CLASSROOM = CLASSROOM;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    public String getTOTAL_TIME() {
        return TOTAL_TIME;
    }

    public void setTOTAL_TIME(String TOTAL_TIME) {
        this.TOTAL_TIME = TOTAL_TIME;
    }

    public String getSTAFF_ID() {
        return STAFF_ID;
    }

    public void setSTAFF_ID(String STAFF_ID) {
        this.STAFF_ID = STAFF_ID;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }


    @Override
    public String toString() {
        return COURSE_CODE + " - " + COURSE_NAME;
    }

    static public ArrayList<Courses> getData(Context context ,String staff_id){


        ArrayList <Courses> list = new ArrayList<Courses>();

        ArrayList<Integer> IdList = new ArrayList<>();
        ArrayList<String> courseNameList = new ArrayList<>();
        ArrayList<String> courseCodeList = new ArrayList<>();
        ArrayList<String> gradeList = new ArrayList<>();
        ArrayList<String> semesterList = new ArrayList<>();
        ArrayList<String> timeList = new ArrayList<>();
        ArrayList<String> totalTimeList = new ArrayList<>();
        ArrayList<String> clasroomList = new ArrayList<>();
        ArrayList<String> staffIdList = new ArrayList<>();

        try {
            SQLiteDatabase db = context.openOrCreateDatabase("AttendanceDatabase.sqlite", Context.MODE_PRIVATE, null);
            Cursor cursor = db.rawQuery("SELECT * FROM COURSES WHERE STAFF_ID = ? ", new String[]{staff_id});
           int idIndex=cursor.getColumnIndex("ID");
            int courseNameIndex=cursor.getColumnIndex("COURSE_CODE");
            int courseCodeIndex = cursor.getColumnIndex("COURSE_NAME");
            int courseGradeIndex = cursor.getColumnIndex("GRADE");
           int courseSemesterIndex = cursor.getColumnIndex("SEMESTER");
           int courseClassroomIndex = cursor.getColumnIndex("CLASSROOM");
           int courseTimeIndex = cursor.getColumnIndex("TIME");
           int courseTotalTimeIndex = cursor.getColumnIndex("TOTAL_TIME");
            int staffIdIndex = cursor.getColumnIndex("STAFF_ID");

                while (cursor.moveToNext()) {
                   IdList.add(cursor.getInt(idIndex));
                    courseNameList.add(cursor.getString(courseNameIndex));
                    courseCodeList.add(cursor.getString(courseCodeIndex));
                    gradeList.add(cursor.getString(courseGradeIndex));
                    semesterList.add(cursor.getString(courseSemesterIndex));
                    clasroomList.add(cursor.getString(courseClassroomIndex));
                    timeList.add(cursor.getString(courseTimeIndex));
                    totalTimeList.add(cursor.getString(courseTotalTimeIndex));
                    staffIdList.add(cursor.getString(staffIdIndex));

                }

            cursor.close();

            for (int i = 0; i < IdList.size(); i++) {
                Courses h = new Courses();
                h.setID(IdList.get(i));
                h.setCLASSROOM(clasroomList.get(i));
                h.setCOURSE_CODE(courseCodeList.get(i));
                h.setCOURSE_NAME(courseNameList.get(i));
                h.setGRADE(gradeList.get(i));
                  h.setSEMESTER(semesterList.get(i));
                 h.setTIME(timeList.get(i));
                h.setTOTAL_TIME(totalTimeList.get(i));
                h.setSTAFF_ID(staffIdList.get(i));

                list.add(h);
            }

        }
        catch (Exception e) {

        }
        return list;

    }

    static public Courses getDataByID(Context context, int id) {
        Courses course = null;

        try {
            SQLiteDatabase db = context.openOrCreateDatabase("AttendanceDatabase.sqlite", Context.MODE_PRIVATE, null);
            Cursor cursor = db.rawQuery("SELECT * FROM COURSES WHERE ID = ?", new String[]{String.valueOf(id)});

            if (cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex("ID");
                int courseCodeIndex = cursor.getColumnIndex("COURSE_CODE");
                int courseNameIndex = cursor.getColumnIndex("COURSE_NAME");
                int gradeIndex = cursor.getColumnIndex("GRADE");
                int semesterIndex = cursor.getColumnIndex("SEMESTER");
                int classroomIndex = cursor.getColumnIndex("CLASSROOM");
                int timeIndex = cursor.getColumnIndex("TIME");
                int totalTimeIndex = cursor.getColumnIndex("TOTAL_TIME");

                if (idIndex != -1 && courseCodeIndex != -1 && courseNameIndex != -1 && gradeIndex != -1 &&
                        semesterIndex != -1 && classroomIndex != -1 && timeIndex != -1 && totalTimeIndex != -1
                        ) {

                    course = new Courses();
                    course.setID(cursor.getInt(idIndex));
                    course.setCOURSE_CODE(cursor.getString(courseCodeIndex));
                    course.setCOURSE_NAME(cursor.getString(courseNameIndex));
                    course.setGRADE(cursor.getString(gradeIndex));
                    course.setSEMESTER(cursor.getString(semesterIndex));
                    course.setCLASSROOM(cursor.getString(classroomIndex));
                    course.setTIME(cursor.getString(timeIndex));
                    course.setTOTAL_TIME(cursor.getString(totalTimeIndex));

                } else {
                    // Log an error if any column is missing
                    System.out.println("One or more columns are missing from the database query result.");
                }
            }

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return course;
    }
}
