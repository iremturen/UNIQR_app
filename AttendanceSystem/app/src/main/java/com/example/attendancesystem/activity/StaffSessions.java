package com.example.attendancesystem.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancesystem.R;
import com.example.attendancesystem.adapter.ReportAdapter;
import com.example.attendancesystem.adapter.SessionsAdapter;
import com.example.attendancesystem.models.Reports;
import com.example.attendancesystem.models.Sessions;
import com.example.attendancesystem.transfer.ReportTransfer;
import com.example.attendancesystem.transfer.SessionTransfer;

import java.util.ArrayList;

public class StaffSessions extends AppCompatActivity {

        RecyclerView recyclerView;
        ImageView session_back;
        ArrayList<Sessions> sessions;
        private SessionsAdapter adapter;
        static public SessionTransfer transfer;


        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.staff_sessions);
            recyclerView=findViewById(R.id.sessions);
            session_back=(ImageView) findViewById(R.id.session_bac);
            String courseId = getIntent().getStringExtra("course_id");
            String staff_id = getIntent().getStringExtra("staff_id");
            String courseName = getIntent().getStringExtra("course_name");
            sessions= Sessions.getSessionsByCourseId(this,courseId);
            adapter = new SessionsAdapter(sessions,this);
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(adapter);


            try {
                adapter.setOnItemClickListener(new SessionsAdapter.OnItemClickLİstener() {
                    @Override
                    public void onItemClick(Sessions sessions) {
                        transfer = new SessionTransfer(sessions.getCreation_time(),sessions.getCourse_id(),sessions.getSession_id(), sessions.getStaff_id(), sessions.getId());
                        Intent intent = new Intent(com.example.attendancesystem.activity.StaffSessions.this, AbsenceCheck.class);
                        intent.putExtra("session_id",transfer.getSession_id());
                        intent.putExtra("course_name",courseName);
                        intent.putExtra("staff_id",staff_id);
                        startActivity(intent);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }


            session_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent switchPage= new Intent(StaffSessions.this, StudentCourseDetail.class);
                    switchPage.putExtra("staff_id",staff_id);
                    switchPage.putExtra("course_name",courseName);
                    startActivity(switchPage);
                }
            });
        }
    }
