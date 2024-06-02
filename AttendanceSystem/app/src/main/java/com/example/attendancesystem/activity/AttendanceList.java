package com.example.attendancesystem.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancesystem.DatabaseHelper;
import com.example.attendancesystem.R;
import com.example.attendancesystem.adapter.AttendanceAdapter;
import com.example.attendancesystem.adapter.SessionsAdapter;
import com.example.attendancesystem.models.Attendance;
import com.example.attendancesystem.models.Sessions;
import com.example.attendancesystem.transfer.AttendanceTransfer;
import com.example.attendancesystem.transfer.SessionTransfer;

import java.util.ArrayList;

public class AttendanceList  extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> attendances;
    private AttendanceAdapter adapter;
    static public AttendanceTransfer transfer;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHelper database = new DatabaseHelper(this);
        setContentView(R.layout.attended_page);
        recyclerView=findViewById(R.id.att_list);
        String session_id = getIntent().getStringExtra("session_id");
        attendances= database.getAllPresentStd(session_id);
        adapter = new AttendanceAdapter(attendances,this);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);


}


}
