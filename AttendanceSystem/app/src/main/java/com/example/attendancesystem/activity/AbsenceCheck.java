package com.example.attendancesystem.activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.attendancesystem.DatabaseHelper;
import com.example.attendancesystem.R;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AbsenceCheck extends AppCompatActivity {

    private DatabaseHelper databaseHelper = new DatabaseHelper(this);

    Button attended,not_attended;
    LinearLayout report;
    ProgressBar progressBar;

    TextView percent,sessions_id;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.absence_check);
        DatabaseHelper database = new DatabaseHelper(this);
        String staff_id = getIntent().getStringExtra("staff_id");
        String session_id = getIntent().getStringExtra("session_id");
        String course_name = getIntent().getStringExtra("course_name");

        report = (LinearLayout) findViewById(R.id.report);
        attended=(Button) findViewById(R.id.button_green);
        not_attended =(Button) findViewById(R.id.button_red);
        progressBar =(ProgressBar) findViewById(R.id.progressBar);
        percent=(TextView) findViewById(R.id.per);
        sessions_id=(TextView) findViewById(R.id.course_name);
        sessions_id.setText(course_name);
        Double per=database.percentageParticipation(session_id);

        if (per != null) {
            int progress = (int) Math.round(per);
            progressBar.setProgress(progress);
            percent.setText(String.valueOf(per)+"%");
        } else {
            percent.setText("N/A");
        }




        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchPage = new Intent(AbsenceCheck.this, PdfCreator.class);
                createAttendancePdf(session_id);
                startActivity(switchPage);
            }
        });

        attended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchPage = new Intent(AbsenceCheck.this, AttendanceList.class);
                switchPage.putExtra("session_id",session_id);
                startActivity(switchPage);
            }
        });

        not_attended.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchPage = new Intent(AbsenceCheck.this, NotAttendanceList.class);
                switchPage.putExtra("session_id",session_id);
                startActivity(switchPage);
            }
        });

    }

    public void createAttendancePdf(String session_id) {
        Cursor cursor = databaseHelper.getAllAttandance(session_id);

        Document document = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String creationTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        String creationDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String staffId = getIntent().getStringExtra("staff_id");;
        String fileName = "attendance_report_" + session_id + "_" + creationTime + ".pdf";

        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            while (cursor.moveToNext()) {
                String studentId = cursor.getString(0);
                String attendanceStatus = cursor.getString(1);
                Font redFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.RED);

                document.add(new Paragraph(session_id + " / " + creationDate + " / " + creationTime,redFont));
                document.add(new Paragraph("Student ID: " + studentId + " / "+attendanceStatus));
                document.add(new Paragraph("\n"));
            }

            cursor.close();
            document.close();

            byte[] pdfBytes = byteArrayOutputStream.toByteArray();


            boolean isInserted = databaseHelper.insertReport(fileName, pdfBytes, creationTime, creationDate, staffId);
            if (isInserted) {
                Toast.makeText(this, "Attendance report PDF created and saved to database successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to save attendance report PDF to database", Toast.LENGTH_SHORT).show();
            }

        } catch (DocumentException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to create attendance report PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
