package com.example.attendancesystem.activity;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.Manifest;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.attendancesystem.DatabaseHelper;
import com.example.attendancesystem.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PdfCreator extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private PDFView pdfView;
    LinearLayout share;
    LinearLayout download;
    private File pdfFile;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdf);

        share=findViewById(R.id.share_btn);
        download=findViewById(R.id.download_btn);
        pdfView = findViewById(R.id.pdfView);
        databaseHelper = new DatabaseHelper(this);
        Intent intent = getIntent();
        String report_name=intent.getStringExtra("report");

        byte[] pdfBytes = databaseHelper.getPdfFile(report_name);
        if (pdfBytes != null) {
            pdfFile = createPdfFile(pdfBytes);
            if (pdfFile != null) {
                showPdf(pdfFile);
            }
        }

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharePdf(pdfFile);
            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadPdf(pdfFile);
            }
        });


    }



    private File createPdfFile(byte[] pdfBytes) {
        String fileName = "tempPdfFile_" + System.currentTimeMillis() + ".pdf";
        File pdfFile = new File(getExternalFilesDir(null), fileName);

        try (FileOutputStream fos = new FileOutputStream(pdfFile)) {
            fos.write(pdfBytes);
            return pdfFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private void sharePdf(File pdfFile) {
        Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".provider", pdfFile);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("application/pdf");
        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareIntent, "Share"));
    }

    private void showPdf(File pdfFile) {
        pdfView.fromFile(pdfFile)
                .defaultPage(0)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .onPageError((page, t) -> {})
                .scrollHandle(new DefaultScrollHandle(this))
                .pageFitPolicy(FitPolicy.WIDTH)
                .load();
    }

    private void downloadPdf(File sourceFile) {
        if (sourceFile == null) {
            Toast.makeText(this, "No PDF file available to download.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            savePdfInMediaStore(sourceFile);
        } else {
            // For older Android versions, use direct file system access
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return;
            }

            File destinationFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "MyPDFs");
            if (!destinationFolder.exists()) {
                destinationFolder.mkdirs();
            }
            File destinationFile = new File(destinationFolder, sourceFile.getName());
            copyFile(sourceFile, destinationFile);
        }
    }

    private void savePdfInMediaStore(File sourceFile) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, sourceFile.getName());
        values.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");
        values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

        try {
            Uri uri = getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);
            if (uri != null) {
                try (InputStream is = new FileInputStream(sourceFile);
                     OutputStream os = getContentResolver().openOutputStream(uri)) {
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = is.read(buffer)) > 0) {
                        os.write(buffer, 0, len);
                    }
                    Toast.makeText(this, "PDF saved to Downloads", Toast.LENGTH_LONG).show();
                }
            }
        } catch (IOException e) {
            Toast.makeText(this, "Error saving file: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void copyFile(File sourceFile, File destinationFile) {
        try (InputStream in = new FileInputStream(sourceFile);
             OutputStream out = new FileOutputStream(destinationFile)) {
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            Toast.makeText(this, "PDF downloaded to " + destinationFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this, "Failed to download PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}
