package com.example.attendancesystem.transfer;

public class AttendanceTransfer {

    private String attendance_id,course_id,session_id,attendance_date,status,student_id;
    private Integer id;

    public AttendanceTransfer(String attendance_id, String course_id, String session_id, String attendance_date, String status, String student_id, Integer id) {
        this.attendance_id = attendance_id;
        this.course_id = course_id;
        this.session_id = session_id;
        this.attendance_date = attendance_date;
        this.status = status;
        this.student_id = student_id;
        this.id = id;
    }

    public String getAttendance_id() {
        return attendance_id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public String getSession_id() {
        return session_id;
    }

    public String getAttendance_date() {
        return attendance_date;
    }

    public String getStatus() {
        return status;
    }

    public String getStudent_id() {
        return student_id;
    }

    public Integer getId() {
        return id;
    }
}
