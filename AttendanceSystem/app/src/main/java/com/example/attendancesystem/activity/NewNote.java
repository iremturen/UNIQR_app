package com.example.attendancesystem.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.attendancesystem.DatabaseHelper;
import com.example.attendancesystem.PasswordHasher;
import com.example.attendancesystem.R;
import com.example.attendancesystem.dao.StaffDao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewNote extends AppCompatActivity {


    LinearLayout save_note;
    EditText note_content,title;
    ImageView back_img;
    TextView edit_text;
    private DatabaseHelper database;

    private void init() {
        save_note = (LinearLayout) findViewById(R.id.save_note_ll);
        note_content = (EditText) findViewById(R.id.note_content);
        note_content.setMovementMethod(new ScrollingMovementMethod());
        title = (EditText) findViewById(R.id.title_edit_text);
        back_img = (ImageView) findViewById(R.id.back_note_page);
        edit_text = (TextView) findViewById(R.id.edit_text);
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = new DatabaseHelper(this);
        setContentView(R.layout.new_note);
        String user_id = getIntent().getStringExtra("user_id");

        init();

        save_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noteTitle = title.getText().toString();
                String noteContent = note_content.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                String creationDate = sdf.format(new Date());

                if (noteTitle.isEmpty() || noteContent.isEmpty()) {
                    Toast.makeText(NewNote.this, "Title or content cannot be empty.", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isInserted = database.insertNote(noteTitle, noteContent, creationDate, user_id);
                    if (isInserted) {
                        Toast.makeText(NewNote.this, "Note saved successfully!", Toast.LENGTH_SHORT).show();
                        Intent switchPage = new Intent(NewNote.this, NotesPage.class);
                        switchPage.putExtra("user_id", user_id);
                        startActivity(switchPage);
                    } else {
                        Toast.makeText(NewNote.this, "Failed to save the note.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent switchPage = new Intent(NewNote.this, NotesPage.class);
                switchPage.putExtra("user_id", user_id);
                 startActivity(switchPage);
            }
        });
    }
}
