package com.example.attendancesystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancesystem.R;
import com.example.attendancesystem.models.Reports;
import com.example.attendancesystem.models.Sessions;

import java.util.ArrayList;

public class SessionsAdapter extends  RecyclerView.Adapter<SessionsAdapter.SessionHolder>{

    private ArrayList<Sessions> sessionsList;
    private Context context;

    private SessionsAdapter.OnItemClickLİstener listener;


    public SessionsAdapter(ArrayList<Sessions> sessionsList, Context context) {
        this.sessionsList = sessionsList;
        this.context = context;
    }

    @NonNull
    @Override
    public SessionsAdapter.SessionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.session_item, parent, false);
        return new SessionsAdapter.SessionHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SessionsAdapter.SessionHolder holder, int position) {

        Sessions session = sessionsList.get(position);
        holder.setData(session);
    }

    @Override
    public int getItemCount() {
        return sessionsList.size();

    }

    class SessionHolder extends  RecyclerView.ViewHolder{

        TextView session_id,session_date;

        public SessionHolder(@NonNull View itemView){
            super(itemView);
            session_id = (TextView) itemView.findViewById(R.id.session_id);
            session_date = (TextView) itemView.findViewById(R.id.session_date);

        }

        public void setData(Sessions session) {
            this.session_id.setText(session.getSession_id());
            this.session_date.setText(session.getCreation_time());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemClick(sessionsList.get(position));
                }
            });
        }


    }

    public interface OnItemClickLİstener {
        void onItemClick(Sessions sessions);
    }
    public void setOnItemClickListener(SessionsAdapter.OnItemClickLİstener listener) {
        this.listener = listener;
    }

}
