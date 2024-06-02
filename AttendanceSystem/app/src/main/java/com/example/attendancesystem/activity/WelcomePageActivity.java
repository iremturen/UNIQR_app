package com.example.attendancesystem.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.attendancesystem.DatabaseHelper;
import com.example.attendancesystem.DatabaseManager;
import com.example.attendancesystem.R;

public class WelcomePageActivity extends AppCompatActivity {

    Button studentLogin,staffLogin;
    ImageView twt,ig,face;

    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseManager.initialize(getApplicationContext());
        setContentView(R.layout.main_page);

        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        studentLogin=(Button) findViewById(R.id.btnStudentLogin);
        staffLogin=(Button) findViewById(R.id.btnStaffLogin);
        twt= (ImageView) findViewById(R.id.imgTwt);
        ig= (ImageView) findViewById(R.id.imgInsta);
        face= (ImageView) findViewById(R.id.imgFacebook);



        studentLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchPage = new Intent(WelcomePageActivity.this, StudentLogin.class);
                startActivity(switchPage);
            }
        });

        staffLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchPage = new Intent(WelcomePageActivity.this, StaffLogin.class);
                startActivity(switchPage);
            }
        });


        twt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri link= Uri.parse("https://twitter.com/uskudaruni");
                Intent twt_yon =new Intent(Intent.ACTION_VIEW,link);
                startActivity(twt_yon);
            }
        });


        ig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri link= Uri.parse("https://www.instagram.com/uskudaruni/");
                Intent twt_yon =new Intent(Intent.ACTION_VIEW,link);
                startActivity(twt_yon);
            }
        });

        face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri link= Uri.parse("https://www.facebook.com/UskudarUniversitesi/?locale=tr_TR");
                Intent twt_yon =new Intent(Intent.ACTION_VIEW,link);
                startActivity(twt_yon);
            }
        });
    }
}
