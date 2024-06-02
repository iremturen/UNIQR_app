package com.example.attendancesystem.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancesystem.R;
import com.example.attendancesystem.adapter.NotesAdapter;
import com.example.attendancesystem.adapter.StudentCourseAdapter;
import com.example.attendancesystem.models.Courses;
import com.example.attendancesystem.models.Notes;
import com.example.attendancesystem.models.Student;
import com.example.attendancesystem.transfer.CourseTransfer;
import com.example.attendancesystem.transfer.NotesTransfer;

import java.util.ArrayList;

public class NotesPage extends AppCompatActivity {


    ImageView add_new_notes,back_home;
    ArrayList<Notes> notes;
    RecyclerView mRecyclerView;
    private NotesAdapter adapter;

    static public NotesTransfer transfer;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes);
        add_new_notes = (ImageView) findViewById(R.id.new_note);
        back_home = (ImageView) findViewById(R.id.back_home);
        mRecyclerView = findViewById(R.id.notes_rv_layout);

        String user_id = getIntent().getStringExtra("user_id");
        notes = Notes.getNotesData(this, user_id);
        adapter = new NotesAdapter(notes,this);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);

        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);

        try {
            adapter.setOnItemClickListener(new NotesAdapter.OnItemClickLİstener() {
                @Override
                public void onItemClick(Notes notes) {
                    transfer = new NotesTransfer(notes.getNOTES(), notes.getTITLE(), notes.getCREATION_DATE(), notes.getUSER_ID(),notes.getID());
                    Intent intent = new Intent(NotesPage.this, NoteDetailPage.class);
                    intent.putExtra("USER_ID",notes.getID());
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        add_new_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent switchPage = new Intent(NotesPage.this, NewNote.class);
                switchPage.putExtra("user_id",user_id);
                startActivity(switchPage);


            }
        });
        back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent switchPage = new Intent(NotesPage.this, StudentHomepage.class);
                switchPage.putExtra("STUDENT_ID",user_id);
                startActivity(switchPage);


            }
        });

    }
}
