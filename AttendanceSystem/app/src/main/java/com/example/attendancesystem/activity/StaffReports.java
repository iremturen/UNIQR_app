package com.example.attendancesystem.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancesystem.R;
import com.example.attendancesystem.adapter.CourseAdapter;
import com.example.attendancesystem.adapter.ReportAdapter;
import com.example.attendancesystem.dao.StaffDao;
import com.example.attendancesystem.models.Courses;
import com.example.attendancesystem.models.Reports;
import com.example.attendancesystem.models.Staff;
import com.example.attendancesystem.transfer.CourseTransfer;
import com.example.attendancesystem.transfer.ReportTransfer;
import com.google.zxing.integration.android.IntentIntegrator;

import java.util.ArrayList;

public class StaffReports extends AppCompatActivity {

    SearchView searchView;
    RecyclerView recyclerView;
    ArrayList<Reports> reports;
    ArrayList<Staff> staff;
    private ReportAdapter adapter;
    static public ReportTransfer transfer;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_reports);
        searchView= findViewById(R.id.searchView);
        recyclerView=findViewById(R.id.reports);
        String staffId = getIntent().getStringExtra("staff_key");
        StaffDao staffDAO = new StaffDao(this);
        staff= Staff.getData(this,staffId);
        reports= Reports.getReportsByStaffId(this,staffId);

        adapter = new ReportAdapter(reports,this);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        Cursor cursor = staffDAO.getUserById(staffId);


        try {
            adapter.setOnItemClickListener(new ReportAdapter.OnItemClickLİstener() {
                @Override
                public void onItemClick(Reports reports) {
                    transfer = new ReportTransfer(reports.getCreation_time(),reports.getCreation_date(),reports.getReport_name(), reports.getStaff_id(), reports.getId(), reports.getReport());
                    Intent intent = new Intent(StaffReports.this, PdfCreator.class);
                    intent.putExtra("report",transfer.getReport_name());
                     startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
}
