package com.example.attendancesystem.models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Attendance {

    Integer id;
    String session_id;
    String attendance_id;
    String status;
    String student_id;
    String course_id;
    String attendance_date;

    public Attendance() {
    }

    public Attendance(Integer id, String session_id, String attendance_id, String status, String student_id, String course_id, String attendance_date) {
        this.id = id;
        this.session_id = session_id;
        this.attendance_id = attendance_id;
        this.status = status;
        this.student_id = student_id;
        this.course_id = course_id;
        this.attendance_date = attendance_date;
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

    public String getAttendance_id() {
        return attendance_id;
    }

    public void setAttendance_id(String attendance_id) {
        this.attendance_id = attendance_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getAttendance_date() {
        return attendance_date;
    }

    public void setAttendance_date(String attendance_date) {
        this.attendance_date = attendance_date;
    }

}
