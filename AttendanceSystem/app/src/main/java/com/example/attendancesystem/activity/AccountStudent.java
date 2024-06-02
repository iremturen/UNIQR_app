package com.example.attendancesystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.attendancesystem.DatabaseHelper;
import com.example.attendancesystem.R;
import com.example.attendancesystem.models.Staff;
import com.example.attendancesystem.models.Student;

import java.util.ArrayList;

public class AccountStudent extends AppCompatActivity {

    ImageView homepage;

    TextView txt_name;
    TextView txt_surname;
    TextView txt_email;
    TextView txt_grade;
    TextView txt_std_id;

    String str_name, str_surname, str_email, str_grade, str_id;
    private DatabaseHelper database;

    private void init() {
        homepage = (ImageView) findViewById(R.id.homepage_btn_std);
        txt_name = (TextView) findViewById(R.id.txt_name);
        txt_surname = (TextView) findViewById(R.id.txt_surname);
        txt_email = (TextView) findViewById(R.id.txt_email);
        txt_grade = (TextView) findViewById(R.id.txt_grade);
        txt_std_id = (TextView) findViewById(R.id.txt_std_id);

    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account_student);
        database = new DatabaseHelper(this);
        init();
        Intent intent = getIntent();
        ArrayList<Student> stdList = intent.getParcelableArrayListExtra("STUDENT");
        String std_id =intent.getStringExtra("STUDENT_ID");
        if (stdList != null && !stdList.isEmpty()) {
            for (Student student : stdList) {
                str_name = student.getNAME();
                str_surname = student.getSURNAME();
                str_email = student.getEMAIL();
                str_id = student.getSTUDENT_ID();
                str_grade = student.getGRADE();

            }
        }

        if (!TextUtils.isEmpty(str_name) && !TextUtils.isEmpty(str_surname) && !TextUtils.isEmpty(str_email) && !TextUtils.isEmpty(str_id) && !TextUtils.isEmpty(str_grade)) {
            txt_name.setText(str_name);
            txt_surname.setText(str_surname);
            txt_email.setText(str_email);
            txt_std_id.setText(str_id);
            txt_grade.setText(str_grade);




            homepage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent switchPage = new Intent(AccountStudent.this, StudentHomepage.class);
                  switchPage.putExtra("STUDENT_ID",std_id);
                    startActivity(switchPage);
                }
            });
        }
    }
}
