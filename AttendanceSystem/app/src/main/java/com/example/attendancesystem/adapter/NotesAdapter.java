package com.example.attendancesystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancesystem.R;
import com.example.attendancesystem.models.Courses;
import com.example.attendancesystem.models.Notes;

import java.util.ArrayList;

public class NotesAdapter extends  RecyclerView.Adapter<NotesAdapter.NoteHolder>{


    private ArrayList<Notes> notesList;
    private Context context;

    private NotesAdapter.OnItemClickLİstener listener;


    public NotesAdapter(ArrayList<Notes> notesList, Context context) {
        this.notesList = notesList;
        this.context = context;
    }

    @NonNull
    @Override
    public NotesAdapter.NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.notes_item, parent, false);
        return new NotesAdapter.NoteHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.NoteHolder holder, int position) {

        Notes notes = notesList.get(position);
        holder.setData(notes);
    }

    @Override
    public int getItemCount() {
        return notesList.size();

    }

    class NoteHolder extends  RecyclerView.ViewHolder{

        TextView title;

        public NoteHolder(@NonNull View itemView){
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.note_title);
        }

        public void setData(Notes note) {
            this.title.setText(note.getTITLE());


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemClick(notesList.get(position));
                }
            });
        }


    }

    public interface OnItemClickLİstener {
        void onItemClick(Notes notes);
    }
    public void setOnItemClickListener(NotesAdapter.OnItemClickLİstener listener) {
        this.listener = listener;
    }
}
