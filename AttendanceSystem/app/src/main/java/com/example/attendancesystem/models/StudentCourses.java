package com.example.attendancesystem.models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class StudentCourses {

    private Integer id;
    private String student_id;
    private String course_code;
    private String course_name;
    private String grade;
    private Integer attendance;
    private String semester;
    private String classroom;
    private String time;
    private Integer total_time;
    private String staff_id;

    public StudentCourses() {
    }

    public StudentCourses(Integer id, String student_id, String course_code, String course_name, String grade, Integer attendance, String semester, String classroom, String time, Integer total_time, String staff_id) {
        this.id = id;
        this.student_id = student_id;
        this.course_code = course_code;
        this.course_name = course_name;
        this.grade = grade;
        this.attendance = attendance;
        this.semester = semester;
        this.classroom = classroom;
        this.time = time;
        this.total_time = total_time;
        this.staff_id = staff_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    public String getCourse_code() {
        return course_code;
    }

    public void setCourse_code(String course_code) {
        this.course_code = course_code;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Integer getAttendance() {
        return attendance;
    }

    public void setAttendance(Integer attendance) {
        this.attendance = attendance;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getTotal_time() {
        return total_time;
    }

    public void setTotal_time(Integer total_time) {
        this.total_time = total_time;
    }

    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }

    public static ArrayList<StudentCourses> getStudentCoursesByStudentId(Context context, String studentId) {
        ArrayList<StudentCourses> list = new ArrayList<>();

        try {
            SQLiteDatabase db = context.openOrCreateDatabase("AttendanceDatabase.sqlite", Context.MODE_PRIVATE, null);
            Cursor cursor = db.rawQuery("SELECT * FROM STUDENT_COURSES WHERE STUDENT_ID = ?", new String[]{studentId});

            int idIndex = cursor.getColumnIndex("ID");
            int studentIdIndex = cursor.getColumnIndex("STUDENT_ID");
            int courseCodeIndex = cursor.getColumnIndex("COURSE_CODE");
            int courseNameIndex = cursor.getColumnIndex("COURSE_NAME");
            int gradeIndex = cursor.getColumnIndex("GRADE");
            int attendanceIndex = cursor.getColumnIndex("ATTENDANCE");
            int semesterIndex = cursor.getColumnIndex("SEMESTER");
            int classroomIndex = cursor.getColumnIndex("CLASSROOM");
            int timeIndex = cursor.getColumnIndex("TIME");
            int totalTimeIndex = cursor.getColumnIndex("TOTAL_TIME");
            int staffIdIndex = cursor.getColumnIndex("STAFF_ID");

            while (cursor.moveToNext()) {
                StudentCourses studentCourse = new StudentCourses();
                studentCourse.setId(cursor.getInt(idIndex));
                studentCourse.setStudent_id(cursor.getString(studentIdIndex));
                studentCourse.setCourse_code(cursor.getString(courseCodeIndex));
                studentCourse.setCourse_name(cursor.getString(courseNameIndex));
                studentCourse.setGrade(cursor.getString(gradeIndex));
                studentCourse.setAttendance(cursor.getInt(attendanceIndex));
                studentCourse.setSemester(cursor.getString(semesterIndex));
                studentCourse.setClassroom(cursor.getString(classroomIndex));
                studentCourse.setTime(cursor.getString(timeIndex));
                studentCourse.setTotal_time(cursor.getInt(totalTimeIndex));
                studentCourse.setStaff_id(cursor.getString(staffIdIndex));

                list.add(studentCourse);
            }

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
