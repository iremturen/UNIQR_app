package com.example.attendancesystem.transfer;

public class ReportTransfer {

    private String creation_time , creation_date, report_name,staff_id;
    private Integer id;

    private byte[] report;

    public ReportTransfer(String creation_time, String creation_date, String report_name, String staff_id, Integer id, byte[] report) {
        this.creation_time = creation_time;
        this.creation_date = creation_date;
        this.report_name = report_name;
        this.staff_id = staff_id;
        this.id = id;
        this.report = report;
    }

    public String getCreation_time() {
        return creation_time;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public String getReport_name() {
        return report_name;
    }

    public String getStaff_id() {
        return staff_id;
    }

    public Integer getId() {
        return id;
    }

    public byte[] getReport() {
        return report;
    }
}
