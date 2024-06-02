package com.example.attendancesystem.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.attendancesystem.DatabaseHelper;
import com.example.attendancesystem.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NoteDetailPage extends AppCompatActivity {


    EditText title_et,note_content_et;
    ImageView delete_img,back_img;
    LinearLayout edit_button;
    TextView edit_text;

    private String title, note_content;

    private DatabaseHelper database;

    private void init() {

        title_et=(EditText) findViewById(R.id.title_edit_text);
        note_content_et=(EditText) findViewById(R.id.note_content);
        delete_img=(ImageView) findViewById(R.id.delete_button);
        back_img=(ImageView) findViewById(R.id.back_notes_page);
        edit_button=(LinearLayout) findViewById(R.id.edit_button);
        edit_text=(TextView) findViewById(R.id.edit_text);

        title = NotesPage.transfer.getTitle();
        note_content = NotesPage.transfer.getNotes();

    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = new DatabaseHelper(this);
        setContentView(R.layout.note_detail);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String creationDate = sdf.format(new Date());
        init();
        title_et.setEnabled(false);
        note_content_et.setEnabled(false);

        Intent intent = getIntent();

        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(note_content) ) {
            title_et.setText(title);
            note_content_et.setText(note_content);
        }

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent switchPage = new Intent(NoteDetailPage.this, NotesPage.class);
                switchPage.putExtra("user_id", NotesPage.transfer.getUser_id());
                 startActivity(switchPage);
            }
        });

        delete_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int noteId = NotesPage.transfer.getId();
                boolean deleteSuccess = database.deleteNote(noteId);

                if (deleteSuccess) {
                    Toast.makeText(NoteDetailPage.this, "Silme işlemi başarılı", Toast.LENGTH_SHORT).show();
                    Intent switchPage = new Intent(NoteDetailPage.this, NotesPage.class);
                    switchPage.putExtra("user_id", NotesPage.transfer.getUser_id()); //
                    startActivity(switchPage);
                    finish();
                } else {
                    Toast.makeText(NoteDetailPage.this, "Silme işlemi başarısız. Lütfen tekrar deneyin.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edit_text.getText().equals("Edit")) {
                    edit_text.setText("Save");
                    title_et.setEnabled(true);
                    note_content_et.setEnabled(true);
                } else if (edit_text.getText().equals("Save")) {
                    title_et.setEnabled(false);
                    note_content_et.setEnabled(false);
                    edit_text.setText("Edit");

                    String title = title_et.getText().toString();
                    String content = note_content_et.getText().toString();
                    int noteId = NotesPage.transfer.getId();
                    boolean result = database.updateNote(noteId, title, content, creationDate, NotesPage.transfer.getUser_id());

                    if (result) {
                        Toast.makeText(NoteDetailPage.this, "Note updated successfully", Toast.LENGTH_SHORT).show();
                        Intent switchPage = new Intent(NoteDetailPage.this, NotesPage.class);
                        switchPage.putExtra("user_id", NotesPage.transfer.getUser_id()); //
                        startActivity(switchPage);
                    } else {
                        Toast.makeText(NoteDetailPage.this, "Update failed", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


    }
    }


