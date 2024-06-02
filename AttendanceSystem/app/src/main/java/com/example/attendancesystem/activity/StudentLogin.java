package com.example.attendancesystem.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.attendancesystem.DatabaseHelper;
import com.example.attendancesystem.PasswordHasher;
import com.example.attendancesystem.R;
import com.example.attendancesystem.dao.StaffDao;
import com.example.attendancesystem.dao.StudentDao;

public class StudentLogin extends AppCompatActivity {

    Button login;
    EditText id, password;
    DatabaseHelper db;



    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_login);
        StudentDao studentDao = new StudentDao(this);
        login = (Button) findViewById(R.id.std_login_btn);
        id = (EditText) findViewById(R.id.editText_id);
        password = (EditText) findViewById(R.id.stu_pass);
        db = new DatabaseHelper(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String student_id = id.getText().toString();
                String pass = password.getText().toString();

                if (student_id.equals("") || pass.equals(""))
                    Toast.makeText(StudentLogin.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else {
                    String hashedPassword = PasswordHasher.hashPassword(pass);
                    Boolean checkuserpass = db.checkusernamepassword2(student_id, hashedPassword);

                    if (checkuserpass == true) {
                        Toast.makeText(StudentLogin.this, "Sign in successfull", Toast.LENGTH_SHORT).show();
                        Cursor userCursor = studentDao.getUserById(student_id);
                        if (userCursor != null && userCursor.moveToFirst()) {
                            int index =userCursor.getColumnIndex("STUDENT_ID");
                            String studentId = userCursor.getString(index);
                            Intent switchPage = new Intent(StudentLogin.this, StudentHomepage.class);
                            switchPage.putExtra("STUDENT_ID", studentId);
                            startActivity(switchPage);
                        }
                    } else {
                        Toast.makeText(StudentLogin.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }
}
