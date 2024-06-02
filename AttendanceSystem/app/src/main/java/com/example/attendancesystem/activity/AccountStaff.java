package com.example.attendancesystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.attendancesystem.DatabaseHelper;
import com.example.attendancesystem.R;
import com.example.attendancesystem.models.Staff;

import java.util.ArrayList;

public class AccountStaff extends AppCompatActivity {

TextView name,surname,email,staff_id;
String str_name,str_surname,str_email,str_staff_id;

    ImageView homepage_btn;

    private DatabaseHelper database;

    private void init() {
        name=(TextView) findViewById(R.id.name);
        surname=(TextView) findViewById(R.id.surname);
        email=(TextView) findViewById(R.id.email);
        staff_id=(TextView) findViewById(R.id.staffId);
        homepage_btn=(ImageView) findViewById(R.id.homepage_btn);


    }


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_account_staff);
        database = new DatabaseHelper(this);
        init();

        Intent intent = getIntent();
        ArrayList<Staff> staffList = intent.getParcelableArrayListExtra("staff_key");

        if (staffList != null && !staffList.isEmpty()) {
            for (Staff staff : staffList) {
                str_name = staff.getNAME();
                str_surname=staff.getSURNAME();
                str_email=staff.getEMAIL();
                str_staff_id=staff.getSTAFF_ID();
            }
        }

        if (!TextUtils.isEmpty(str_name) && !TextUtils.isEmpty(str_surname) && !TextUtils.isEmpty(str_email) && !TextUtils.isEmpty(str_staff_id)) {
            name.setText(str_name);
            surname.setText(str_surname);
            email.setText(str_email);
            staff_id.setText(str_staff_id);




        }

        homepage_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchPage = new Intent(AccountStaff.this, StaffHomepage.class);
                switchPage.putExtra("staff_id",str_staff_id);
                startActivity(switchPage);


            }
        });
    }
}

