package com.example.attendancesystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.attendancesystem.DatabaseHelper;
import com.example.attendancesystem.R;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StaffCourseDetail extends AppCompatActivity {

    TextView detail_course_title,detail_course_code,detail_course_time,detail_course_classroom,detail_course_total_time;

    LinearLayout generate_btn, sessions,homepage;
    private String course_title, course_code, course_time, course_class,course_total_time;


    private void init() {
        homepage =(LinearLayout) findViewById(R.id.homepage);
        sessions =(LinearLayout) findViewById(R.id.sessions);
        generate_btn =(LinearLayout) findViewById(R.id.generate_qr);
        detail_course_classroom=(TextView) findViewById(R.id.detail_course_classroom);
        detail_course_code=(TextView) findViewById(R.id.detail_course_code);
        detail_course_time=(TextView) findViewById(R.id.detail_course_time);
        detail_course_title=(TextView) findViewById(R.id.detail_course_title);
        detail_course_total_time=(TextView) findViewById(R.id.detail_course_total_time);


        course_title = StaffHomepage.transfer.getCourse_name();
        course_class = StaffHomepage.transfer.getClassroom();
        course_code = StaffHomepage.transfer.getCourse_code();
        course_time = StaffHomepage.transfer.getTime();
        course_total_time=StaffHomepage.transfer.getTotal_time();

    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_course_detail);
        init();
        Intent intent = getIntent();
        String staff_id=intent.getStringExtra("staff_id");
        DatabaseHelper database = new DatabaseHelper(this);

        if (!TextUtils.isEmpty(course_title) && !TextUtils.isEmpty(course_class) && !TextUtils.isEmpty(course_code) && !TextUtils.isEmpty(course_time) && !TextUtils.isEmpty(course_total_time)) {
            detail_course_classroom.setText(course_class);
            detail_course_title.setText(course_code);
            detail_course_time.setText(course_total_time);
            detail_course_code.setText(course_title);
            detail_course_total_time.setText(course_time);

        }
        generate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isExistSession= true;
                String sessionID="2324";
               while(isExistSession) {
                    String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                    SecureRandom random = new SecureRandom();
                    int randomNumber = random.nextInt(99999 - 100 + 1) + 100;
                    char char1 = CHARACTERS.charAt(random.nextInt(CHARACTERS.length()));
                    char char2 = CHARACTERS.charAt(random.nextInt(CHARACTERS.length()));
                    sessionID = "SS" + char1 + char2 + randomNumber;
                   isExistSession=database.isExistSession(sessionID);
                    if (isExistSession) {
                    continue;
                    }
               }

              SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
              String creationDate = sdf.format(new Date());
              database.createSession(sessionID,staff_id,creationDate,course_title);

              Intent switchPage = new Intent(StaffCourseDetail.this, GenerateQR.class);
              switchPage.putExtra("course_name",detail_course_title.getText().toString());
              switchPage.putExtra("course_code",detail_course_code.getText().toString());
              switchPage.putExtra("staff_id",staff_id);
              startActivity(switchPage);
            }
        });

        sessions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchPage = new Intent(StaffCourseDetail.this, StaffSessions.class);
                switchPage.putExtra("course_id",detail_course_code.getText().toString());
                switchPage.putExtra("course_name",detail_course_title.getText().toString());
                switchPage.putExtra("staff_id",staff_id);
                startActivity(switchPage);
            }
        });

        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchPage = new Intent(StaffCourseDetail.this, StaffHomepage.class);
                switchPage.putExtra("staff_id",staff_id);
                startActivity(switchPage);
            }
        });
    }

}
