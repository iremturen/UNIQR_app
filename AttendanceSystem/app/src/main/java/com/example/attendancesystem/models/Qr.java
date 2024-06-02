package com.example.attendancesystem.models;

public class Qr {
    private Integer ID;
    private String QR_DATA;
    private String COURSE_ID;
    private String STUDENT_ID;
    private  String SESSION_ID;

    public Qr() {
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getQR_DATA() {
        return QR_DATA;
    }

    public void setQR_DATA(String QR_DATA) {
        this.QR_DATA = QR_DATA;
    }

    public String getCOURSE_ID() {
        return COURSE_ID;
    }

    public void setCOURSE_ID(String COURSE_ID) {
        this.COURSE_ID = COURSE_ID;
    }

    public String getSTUDENT_ID() {
        return STUDENT_ID;
    }

    public void setSTUDENT_ID(String STUDENT_ID) {
        this.STUDENT_ID = STUDENT_ID;
    }

    public String getSESSION_ID() {
        return SESSION_ID;
    }

    public void setSESSION_ID(String SESSION_ID) {
        this.SESSION_ID = SESSION_ID;
    }

    public Qr(Integer ID, String QR_DATA, String COURSE_ID, String STUDENT_ID, String SESSION_ID) {
        this.ID = ID;
        this.QR_DATA = QR_DATA;
        this.COURSE_ID = COURSE_ID;
        this.STUDENT_ID = STUDENT_ID;
        this.SESSION_ID = SESSION_ID;
    }
}
