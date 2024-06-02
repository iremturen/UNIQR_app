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
import com.example.attendancesystem.models.Attendance;
import com.example.attendancesystem.models.Courses;
import com.example.attendancesystem.models.Qr;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class StudentCourseDetail extends AppCompatActivity {

    TextView detail_course_title,detail_course_code,detail_course_time,detail_course_classroom,detail_course_total_time,absence_count;
    LinearLayout join_class;
    private String course_title, course_code, course_time, course_class;
    private Integer course_total_time;

    DatabaseHelper database =new DatabaseHelper(this);



    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Courses courses = new Courses();
        DatabaseHelper database =new DatabaseHelper(this);
        setContentView(R.layout.student_course_detail);
        join_class=(LinearLayout)findViewById(R.id.join_class);
        detail_course_classroom=(TextView) findViewById(R.id.detail_course_classroom);
        detail_course_code=(TextView) findViewById(R.id.detail_course_code);
        detail_course_time=(TextView) findViewById(R.id.detail_course_time);
        detail_course_title=(TextView) findViewById(R.id.detail_course_title);
        detail_course_total_time=(TextView) findViewById(R.id.detail_course_total_time);
        absence_count=(TextView) findViewById(R.id.absence_count);

        course_title = StudentHomepage.transfer.getCourse_name();
        course_class = StudentHomepage.transfer.getClassroom();
        course_code = StudentHomepage.transfer.getCourse_code();
        course_time = StudentHomepage.transfer.getTime();
        course_total_time=StudentHomepage.transfer.getTotal_time();
        Intent intent = getIntent();
        int courseId = getIntent().getIntExtra("course",-1);
        String st_id = getIntent().getStringExtra("STUDENT_ID");

        if (courseId != -1) {
            Courses course = Courses.getDataByID(this, courseId);
            if (course != null) {
                detail_course_classroom.setText(course.getCLASSROOM());
                detail_course_title.setText(course.getCOURSE_NAME());
                detail_course_code.setText(course.getCOURSE_CODE());
                detail_course_time.setText(course.getTIME());
                detail_course_total_time.setText(course.getTOTAL_TIME());
                int count=database.countAbsences(st_id,course.getCOURSE_CODE());
                absence_count.setText(String.valueOf(count));
                checkAndNotifyAbsences(st_id, course.getCOURSE_CODE());
            }
        }

        join_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(StudentCourseDetail.this);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setPrompt("SCAN QR");
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                intentIntegrator.initiateScan();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "QR code scan cancelled", Toast.LENGTH_SHORT).show();
            } else {
                String qrData = result.getContents();

                // Retrieve course details from Intent
                String courseId = data.getStringExtra("COURSE_ID");
                String courseCode = data.getStringExtra("COURSE_CODE");
                String courseTitle = data.getStringExtra("COURSE_NAME");
                String courseTime = data.getStringExtra("COURSE_TIME");
                String courseClass = data.getStringExtra("COURSE_CLASS");
                String studentId = data.getStringExtra("STUDENT_ID");

                handleQRData(qrData, courseId, courseCode, courseTitle, courseTime, courseClass, studentId);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleQRData(String qrData, String courseId,String courseCode,String courseTitle,String courseTime,String courseClass,String studentId) {
        try {
            Qr qr = parseQrData(qrData);
            if (qr != null) {
                recordAttendance(qr, courseId, courseCode, courseTitle, courseTime, courseClass, studentId);
            } else {
                Toast.makeText(this, "Invalid QR data", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error processing QR data", Toast.LENGTH_SHORT).show();
        }
    }

    private Qr parseQrData(String qrData) {
        String[] parts = qrData.split(",");
        if (parts.length == 3) {
            return new Qr(null, qrData, parts[0], parts[1], parts[2]);
        }
        return null;
    }

    private void recordAttendance(Qr qr,String courseId,String courseCode,String courseTitle,String courseTime,String courseClass,String studentId) {
        Random random = new Random();
        int randomAttendanceId = random.nextInt(11);
        String randomSessionId = generateRandomAlphanumericString(10);

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Attendance attendance = new Attendance(randomAttendanceId, "CS101", randomSessionId, "P", studentId, courseCode, date);
        boolean isInserted = database.insertAttendance(attendance);

        if (isInserted) {
            Toast.makeText(this, "Attendance recorded", Toast.LENGTH_SHORT).show();
            markAbsentees(qr.getSESSION_ID(), qr.getCOURSE_ID(), date);
        } else {
            Toast.makeText(this, "Failed to record attendance", Toast.LENGTH_SHORT).show();
        }
    }


    private String generateRandomAlphanumericString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            stringBuilder.append(characters.charAt(randomIndex));
        }
        return stringBuilder.toString();
    }
    private void markAbsentees(String sessionId, String courseId, String date) {
        ArrayList<String> absentStudentIds = database.getStudentsNotScanned(sessionId);

        for (String studentId : absentStudentIds) {
            Attendance attendance = new Attendance(null, sessionId, null, "A", studentId, courseId, date);
            database.insertAttendance(attendance);
        }
    }

    private void checkAndNotifyAbsences(String studentId, String courseId) {
        int absenceCount = database.countAbsences(studentId, courseId);
        if (absenceCount >= 5) {
            Toast.makeText(this, "You have missed this course " + absenceCount + " times.", Toast.LENGTH_LONG).show();
        }
    }
           }


