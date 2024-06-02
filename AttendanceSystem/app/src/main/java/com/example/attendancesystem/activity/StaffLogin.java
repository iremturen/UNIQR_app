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

public class StaffLogin extends AppCompatActivity {

    Button login;
    EditText id;
    EditText password;

    DatabaseHelper db;

    private void init() {
        login = (Button) findViewById(R.id.staff_login_btn);
       id = (EditText) findViewById(R.id.editText_id);
      password = (EditText) findViewById(R.id.staff_pass);
        db = new DatabaseHelper(this);

    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_login);
        StaffDao staffDAO = new StaffDao(this);

        init();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String staff_id = id.getText().toString();
                String pass = password.getText().toString();

                if (staff_id.equals("") || pass.equals(""))
                    Toast.makeText(StaffLogin.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else {
                    String hashedPassword = PasswordHasher.hashPassword(pass);
                    Boolean checkuserpass = db.checkusernamepassword(staff_id, hashedPassword);

                    if (checkuserpass == true) {
                        Toast.makeText(StaffLogin.this, "Sign in successfull", Toast.LENGTH_SHORT).show();
                        Cursor userCursor = staffDAO.getUserById(staff_id);
                        if (userCursor != null && userCursor.moveToFirst()) {
                            int index =userCursor.getColumnIndex("STAFF_ID");
                            String staffId = userCursor.getString(index);
                            Intent switchPage = new Intent(StaffLogin.this, StaffHomepage.class);
                            switchPage.putExtra("staff_id", staffId);
                            startActivity(switchPage);
                        }

                    } else {
                        Toast.makeText(StaffLogin.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
    }
}
