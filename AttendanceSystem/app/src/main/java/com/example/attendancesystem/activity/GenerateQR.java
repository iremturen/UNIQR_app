package com.example.attendancesystem.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.attendancesystem.DatabaseHelper;
import com.example.attendancesystem.PasswordHasher;
import com.example.attendancesystem.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.Timer;
import java.util.TimerTask;

public class GenerateQR extends AppCompatActivity {
    private static final long QR_CODE_UPDATE_INTERVAL = 3000;
    private static final long PROGRESS_BAR_UPDATE_INTERVAL = 3000;

    DatabaseHelper database;
    private Timer progressBarTimer;
    private Timer timer;


    TextView course_name;
    TextView course_code,lock_button_txt;

    LinearLayout lock_button,homepage;
    ImageView img_qr;
    ProgressBar progressBar;
    int progressTimer = 0;
    String staff_id;


    private void init() {

        course_name = (TextView)findViewById(R.id.course_name);
        course_code=(TextView)findViewById(R.id.course_code);
        lock_button = (LinearLayout) findViewById(R.id.lock_button);
        img_qr =(ImageView) findViewById(R.id.img_qr);
        progressBar = findViewById(R.id.progressBar);
        lock_button_txt=findViewById(R.id.lock_button_txt);
        homepage=findViewById(R.id.gen_homepage);

    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_qr);
        database = new DatabaseHelper(this);
        String staffId = getIntent().getStringExtra("staff_id");
        staff_id=staffId;
        init();
        Intent intent = getIntent();

        if (intent != null) {
            String name = intent.getStringExtra("course_name");
            String code = intent.getStringExtra("course_code");
            course_name.setText(name);
            course_code.setText((code));

        }

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String courseId = "course_id";
                            String sessionId = "session_id";
                            String studentId = "student_id";

                            String content = courseId + "," + studentId + "," + sessionId;
                            int width = 1000;
                            int height = 1000;

                            Bitmap qrCodeBitmap = generateQRCode(content, width, height);
                            img_qr.setImageBitmap(qrCodeBitmap);
                        }
                    });
            }
        },0, QR_CODE_UPDATE_INTERVAL);

        progressBarTimer = new Timer();
        progressBarTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resetProgressBar();
                    }
                });
            }
        }, 0, PROGRESS_BAR_UPDATE_INTERVAL);


        lock_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lock_button.setClickable(false);
                homepage.setClickable(false);
                lock_button_txt.setText("unlock page");
                Toast.makeText(getApplicationContext(), "Page is locked!", Toast.LENGTH_SHORT).show();


            }
        });


        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchPage = new Intent(GenerateQR.this, StaffHomepage.class);
                switchPage.putExtra("STAFF_ID", staffId);
                switchPage.putExtra("staff_id", staffId);
                startActivity(switchPage);
            }
        });


    }

    public boolean onTouchEvent(MotionEvent event) {

        Toast.makeText(getApplicationContext(), "Page is locked!", Toast.LENGTH_SHORT).show();

        if (lock_button.isClickable()==false){
            showDialog();
        }
        return super.onTouchEvent(event);
    }

    private void showDialog(){
        Dialog dialog = new Dialog(this, R.style.DialogStyle);
        dialog.setContentView(R.layout.lock_popup);
        EditText staff_id_txt = dialog.findViewById(R.id.staff_id_noneditable);
        EditText password_txt = dialog.findViewById(R.id.staff_pass);
        ImageView close =(ImageView) dialog.findViewById(R.id.close_popup);
        LinearLayout unlock =(LinearLayout) dialog.findViewById(R.id.unlock);
        staff_id_txt.setText(staff_id);
        dialog.show();


        unlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String staff_id = staff_id_txt.getText().toString();
                String pass = password_txt.getText().toString();

                String hashedPassword = PasswordHasher.hashPassword(pass);
                Boolean check = database.checkusernamepassword(staff_id, hashedPassword);

                if (check==true){
                    lock_button.setClickable(true);
                    lock_button_txt.setText("lock page");
                    Toast.makeText(getApplicationContext(), "Page is unlocked!", Toast.LENGTH_SHORT).show();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                        }
                    });
                }else if(password_txt.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Please enter all the fields!", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(getApplicationContext(), "Invalid password!", Toast.LENGTH_SHORT).show();

                }




            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void resetProgressBar() {
        progressTimer = 0;
        progressBar.setProgress(0);

        final int totalDuration = 5000;
        final int updateInterval = 100;
        final int steps = totalDuration / updateInterval;

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            int currentStep = 0;

            @Override
            public void run() {
                if (currentStep < steps) {
                    progressTimer += (100 * updateInterval) / totalDuration;
                    progressBar.setProgress(progressTimer);
                    currentStep++;
                    handler.postDelayed(this, updateInterval);
                }
            }
        }, updateInterval);
    }


    private Bitmap generateQRCode(String content, int width, int height) {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            com.google.zxing.common.BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, width, height);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            return barcodeEncoder.createBitmap(bitMatrix);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }


}
