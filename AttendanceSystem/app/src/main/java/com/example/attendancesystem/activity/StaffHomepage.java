package com.example.attendancesystem.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancesystem.R;
import com.example.attendancesystem.dao.StaffDao;
import com.example.attendancesystem.models.Courses;

import com.example.attendancesystem.adapter.CourseAdapter;
import com.example.attendancesystem.models.Staff;
import com.example.attendancesystem.transfer.CourseTransfer;

import java.util.ArrayList;

public class StaffHomepage extends AppCompatActivity {

    ArrayList<Courses> course;
    ArrayList<Staff> staff;
    TextView staff_name;

    ImageView logout;
    LinearLayout account,report,notes,lms,stix;
    RecyclerView mRecyclerView;
    private CourseAdapter adapter;

    static public CourseTransfer transfer;
    //static public StaffTransfer staff_transfer;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_homepage);
        logout = findViewById(R.id.logout);
        account=(LinearLayout) findViewById(R.id.account_lin);
        lms=(LinearLayout) findViewById(R.id.lms);
        stix=(LinearLayout) findViewById(R.id.stix);
        notes=(LinearLayout) findViewById(R.id.notes_lin);
        report=(LinearLayout) findViewById(R.id.report_lin);
        staff_name=findViewById(R.id.staff_name);
        mRecyclerView = findViewById(R.id.staff_courses);

        String staffId = getIntent().getStringExtra("staff_id");
        StaffDao staffDAO = new StaffDao(this);
        staff=Staff.getData(this,staffId);
        course = Courses.getData(this,staffId);

        adapter = new CourseAdapter(course,this);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);


        Cursor cursor = staffDAO.getUserById(staffId);

            if (cursor.moveToFirst()) {

                int nameIndex = cursor.getColumnIndex("NAME");
                String name = cursor.getString(nameIndex);
                staff_name.setText(name);

        }
        try {
            adapter.setOnItemClickListener(new CourseAdapter.OnItemClickLİstener() {
                @Override
                public void onItemClick(Courses courses) {
                    transfer = new CourseTransfer(courses.getCOURSE_CODE(), courses.getCOURSE_NAME(), courses.getGRADE(), courses.getSEMESTER(),courses.getCLASSROOM(),courses.getTOTAL_TIME(),courses.getTIME(),courses.getSTAFF_ID(),courses.getSTAFF(),courses.getID());
                    Intent intent = new Intent(StaffHomepage.this, StaffCourseDetail.class);
                    intent.putExtra("staff_id",staffId);
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchPage = new Intent(StaffHomepage.this, NotesPage.class);
                switchPage.putExtra("user_id",staffId);
                startActivity(switchPage);


            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchPage = new Intent(StaffHomepage.this, AccountStaff.class);
                switchPage.putExtra("staff_key",staff);
                startActivity(switchPage);


            }
        });

        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchPage = new Intent(StaffHomepage.this, StaffReports.class);
                switchPage.putExtra("staff_key",staffId);
                startActivity(switchPage);


            }
        });

        lms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri link= Uri.parse("https://lms.uskudar.edu.tr/Account/LoginBefore");
                Intent web =new Intent(Intent.ACTION_VIEW,link);
                startActivity(web);
            }
        });
        stix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri link= Uri.parse("https://stix.uskudar.edu.tr/login");
                Intent web =new Intent(Intent.ACTION_VIEW,link);
                startActivity(web);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchPage= new Intent(StaffHomepage.this, SettingPage.class);
                startActivity(switchPage);
            }
        });
    }
}


