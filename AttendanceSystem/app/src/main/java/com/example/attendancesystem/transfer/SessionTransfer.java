package com.example.attendancesystem.transfer;

public class SessionTransfer {

    private String staff_id,course_id,session_id,creation_time;
    private Integer id;

    public SessionTransfer(String staff_id, String course_id, String session_id, String creation_time, Integer id) {
        this.staff_id = staff_id;
        this.course_id = course_id;
        this.session_id = session_id;
        this.creation_time = creation_time;
        this.id = id;
    }

    public String getStaff_id() {
        return staff_id;
    }

    public String getCourse_id() {
        return course_id;
    }

    public String getSession_id() {
        return session_id;
    }

    public String getCreation_time() {
        return creation_time;
    }

    public Integer getId() {
        return id;
    }
}
