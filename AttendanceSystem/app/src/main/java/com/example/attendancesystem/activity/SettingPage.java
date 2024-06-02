package com.example.attendancesystem.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.attendancesystem.DatabaseHelper;
import com.example.attendancesystem.PasswordHasher;
import com.example.attendancesystem.R;
import com.example.attendancesystem.dao.StaffDao;

public class SettingPage extends AppCompatActivity {

    LinearLayout supp, web_site,logout;

    private void init() {
        supp=(LinearLayout) findViewById(R.id.support);
        web_site=(LinearLayout)findViewById(R.id.web_site);
        logout=(LinearLayout) findViewById(R.id.logout);
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_page);
        init();

        supp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri link= Uri.parse("https://uskudar.edu.tr/tr/iletisim-bilgileri");
                Intent twt_yon =new Intent(Intent.ACTION_VIEW,link);
                startActivity(twt_yon);


            }
        });

        web_site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri link= Uri.parse("https://uskudar.edu.tr/");
                Intent twt_yon =new Intent(Intent.ACTION_VIEW,link);
                startActivity(twt_yon);


            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchPage= new Intent(SettingPage.this, WelcomePageActivity.class);
                startActivity(switchPage);
            }
        });
    }
}
