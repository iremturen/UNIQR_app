package com.example.attendancesystem.models;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Arrays;

public class Reports {

    Integer id;
    byte[] report;
    String creation_time;
    String creation_date;
    String staff_id,report_name;

    public Reports(){

    }
    public Reports(Integer id,  byte[] report, String creation_time, String creation_date, String staff_id,String report_name) {
        this.id = id;
        this.report = report;
        this.creation_time = creation_time;
        this.creation_date = creation_date;
        this.staff_id = staff_id;
        this.report_name=report_name;
    }

    @Override
    public String toString() {
        return "Reports{" +
                "id=" + id +
                ", report=" + Arrays.toString(report) +
                ", creation_time='" + creation_time + '\'' +
                ", creation_date='" + creation_date + '\'' +
                ", staff_id='" + staff_id + '\'' +
                ", report_name='" + report_name + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public  byte[] getReport() {
        return report;
    }

    public void setReport(byte[] report) {
        this.report = report;
    }

    public String getCreation_time() {
        return creation_time;
    }

    public void setCreation_time(String creation_time) {
        this.creation_time = creation_time;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }

    public String getReport_name() {
        return report_name;
    }

    public void setReport_name(String report_name) {
        this.report_name = report_name;
    }

    static public ArrayList<Reports> getReportsByStaffId(Context context , String staff_id){


        ArrayList <Reports> list = new ArrayList<Reports>();

        ArrayList<Integer> IdList = new ArrayList<>();
        //******
        ArrayList<byte[]> reportList = new ArrayList<>();
        ArrayList<String> cTimeList = new ArrayList<>();
        ArrayList<String> cDateList = new ArrayList<>();
        ArrayList<String> reportNameList = new ArrayList<>();
        ArrayList<String> staffIdList = new ArrayList<>();

        try {
            SQLiteDatabase db = context.openOrCreateDatabase("AttendanceDatabase.sqlite", Context.MODE_PRIVATE, null);
            Cursor cursor = db.rawQuery("SELECT * FROM REPORTS WHERE STAFF_ID = ? ", new String[]{staff_id});
            int idIndex=cursor.getColumnIndex("ID");
            int reportIndex=cursor.getColumnIndex("REPORT");
            int nameIndex=cursor.getColumnIndex("REPORT_NAME");
            int cTimeIndex = cursor.getColumnIndex("CREATION_TIME");
            int cDateIndex = cursor.getColumnIndex("CREATION_DATE");
            int staffIdIndex = cursor.getColumnIndex("STAFF_ID");


            while (cursor.moveToNext()) {
                IdList.add(cursor.getInt(idIndex));
                //******
                reportList.add(cursor.getBlob(reportIndex));
                cTimeList.add(cursor.getString(cTimeIndex));
                cDateList.add(cursor.getString(cDateIndex));
                staffIdList.add(cursor.getString(staffIdIndex));
                reportNameList.add(cursor.getString(nameIndex));


            }

            cursor.close();

            for (int i = 0; i < IdList.size(); i++) {
                Reports h = new Reports();
                h.setId(IdList.get(i));
                h.setCreation_time(cTimeList.get(i));
                h.setReport(reportList.get(i));
                h.setCreation_date(cDateList.get(i));
                h.setStaff_id(staffIdList.get(i));
                h.setReport_name(reportNameList.get(i));

                list.add(h);
            }

        }
        catch (Exception e) {

        }
        return list;

    }


    }



