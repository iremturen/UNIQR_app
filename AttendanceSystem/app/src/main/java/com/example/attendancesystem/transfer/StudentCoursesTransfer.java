package com.example.attendancesystem.transfer;

public class StudentCoursesTransfer {

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

    public StudentCoursesTransfer(Integer id, String student_id, String course_code, String course_name, String grade, Integer attendance, String semester, String classroom, String time, Integer total_time, String staff_id) {
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
}
