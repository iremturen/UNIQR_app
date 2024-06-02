package com.example.attendancesystem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendancesystem.R;
import com.example.attendancesystem.models.Attendance;

import java.util.ArrayList;

public class AttendanceAdapter extends  RecyclerView.Adapter<AttendanceAdapter.AttendanceHolder>{

    private ArrayList<String> attendanceList;
    private Context context;

    private AttendanceAdapter.OnItemClickLİstener listener;


    public AttendanceAdapter(ArrayList<String> attendanceList, Context context) {
        this.attendanceList = attendanceList;
        this.context = context;
    }

    @NonNull
    @Override
    public AttendanceAdapter.AttendanceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.attandance_item, parent, false);
        return new AttendanceAdapter.AttendanceHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceAdapter.AttendanceHolder holder, int position) {

        String attendance = attendanceList.get(position);
        holder.setData(attendance);
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();

    }


    class AttendanceHolder extends  RecyclerView.ViewHolder{

        TextView student_id;

        public AttendanceHolder(@NonNull View itemView){
            super(itemView);
            student_id = (TextView) itemView.findViewById(R.id.student_id);

        }

        public void setData(String std_id) {
            this.student_id.setText(std_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemClick(attendanceList.get(position));
                }
            });
        }


    }

    public interface OnItemClickLİstener {
        void onItemClick(String attendance);
    }
    public void setOnItemClickListener(AttendanceAdapter.OnItemClickLİstener listener) {
        this.listener = listener;
    }

}
