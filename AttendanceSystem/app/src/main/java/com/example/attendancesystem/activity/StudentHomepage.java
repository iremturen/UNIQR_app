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
import com.example.attendancesystem.adapter.StudentCourseAdapter;
import com.example.attendancesystem.dao.StudentDao;
import com.example.attendancesystem.models.Courses;
import com.example.attendancesystem.models.Student;
import com.example.attendancesystem.models.StudentCourses;
import com.example.attendancesystem.transfer.CourseTransfer;
import com.example.attendancesystem.transfer.StudentCoursesTransfer;

import java.util.ArrayList;

public class StudentHomepage extends AppCompatActivity {

    TextView student_name;
    ImageView logout;
    LinearLayout account,notes,lms,stix;

    ArrayList<Student> studentList;
    ArrayList<StudentCourses> course;
    RecyclerView mRecyclerView;
    private StudentCourseAdapter adapter;

    static public StudentCoursesTransfer transfer;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_homepage);
        student_name=(TextView) findViewById(R.id.student_name);
        logout = (ImageView) findViewById(R.id.logout);
        account=(LinearLayout) findViewById(R.id.account_lo);
        notes=(LinearLayout) findViewById(R.id.notes);
        lms = (LinearLayout) findViewById(R.id.lms);
        stix = (LinearLayout) findViewById(R.id.stix);
        mRecyclerView = findViewById(R.id.student_courses);


        String studentId = getIntent().getStringExtra("STUDENT_ID");
        StudentDao studentDAO = new StudentDao(this);
        course = StudentCourses.getStudentCoursesByStudentId(this,studentId);
        studentList= Student.getData(this,studentId);
        adapter = new StudentCourseAdapter(course,this);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);

        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);

        Cursor cursor = studentDAO.getUserById(studentId);

        if (cursor.moveToFirst()) {

            int nameIndex = cursor.getColumnIndex("NAME");
            String name = cursor.getString(nameIndex);

            student_name.setText(name);

        }

        try {
            adapter.setOnItemClickListener(new StudentCourseAdapter.OnItemClickLİstener() {
                @Override
                public void onItemClick(StudentCourses courses) {
                    transfer = new StudentCoursesTransfer(
                            courses.getId(),
                            courses.getStudent_id(),
                            courses.getCourse_code(),
                            courses.getCourse_name(),
                            courses.getGrade(),
                            courses.getAttendance(),
                            courses.getSemester(),
                            courses.getClassroom(),
                            courses.getTime(),
                            courses.getTotal_time(),
                            courses.getStaff_id()
                    );
                    Intent intent = new Intent(StudentHomepage.this, StudentCourseDetail.class);
                    intent.putExtra("STUDENT_ID",studentId);
                    intent.putExtra("course", courses.getId());
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent switchPage = new Intent(StudentHomepage.this, AccountStudent.class);
                switchPage.putExtra("STUDENT",studentList);
                switchPage.putExtra("STUDENT_ID",studentId);
                startActivity(switchPage);


            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchPage= new Intent(StudentHomepage.this, SettingPage.class);
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

        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchPage= new Intent(StudentHomepage.this, NotesPage.class);
                switchPage.putExtra("user_id",studentId);
                startActivity(switchPage);
            }
        });
    }
}
