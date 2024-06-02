package com.example.attendancesystem.transfer;

public class CourseTransfer {

    private String course_code , course_name, grade, semester,classroom, time,total_time,staff_id,staff;
    private Integer id;

    public CourseTransfer(String course_code, String course_name, String grade, String semester, String classroom, String time, String total_time, String staff_id,String staff, Integer id) {
        this.course_code = course_code;
        this.course_name = course_name;
        this.grade = grade;
        this.semester = semester;
        this.classroom = classroom;
        this.time = time;
        this.total_time = total_time;
        this.staff_id = staff_id;
        this.staff=staff;
        this.id = id;
    }

    public String getCourse_code() {
        return course_code;
    }

    public String getCourse_name() {
        return course_name;
    }

    public String getGrade() {
        return grade;
    }

    public String getSemester() {
        return semester;
    }

    public String getClassroom() {
        return classroom;
    }

    public String getTime() {
        return time;
    }

    public String getTotal_time() {
        return total_time;
    }

    public String getStaff_id() {
        return staff_id;
    }

    public Integer getId() {
        return id;
    }

    public String getStaff() {
        return staff;
    }
}
