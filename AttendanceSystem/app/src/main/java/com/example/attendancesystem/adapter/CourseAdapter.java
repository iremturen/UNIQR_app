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

import java.util.ArrayList;

public class CourseAdapter extends  RecyclerView.Adapter<CourseAdapter.CourseHolder>{

    private ArrayList<Courses> coursesList;
    private Context context;

    private OnItemClickLİstener listener;


    public CourseAdapter(ArrayList<Courses> coursesList, Context context) {
        this.coursesList = coursesList;
        this.context = context;
    }

    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.staff_course_item, parent, false);
        return new CourseHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseHolder holder, int position) {

        Courses course = coursesList.get(position);
        holder.setData(course);
    }

    @Override
    public int getItemCount() {
        return coursesList.size();

    }

    class CourseHolder extends  RecyclerView.ViewHolder{

        TextView course_name, course_code,grade;

        public CourseHolder(@NonNull View itemView){
            super(itemView);
            course_name = (TextView) itemView.findViewById(R.id.item_course_name);
            course_code =(TextView) itemView.findViewById(R.id.item_course_code);
            grade= (TextView) itemView.findViewById(R.id.item_grade);
        }

        public void setData(Courses course) {
            this.course_code.setText(course.getCOURSE_NAME());
            this.course_name.setText(course.getCOURSE_CODE());
            this.grade.setText(course.getGRADE());


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemClick(coursesList.get(position));
                }
            });
        }


    }

    public interface OnItemClickLİstener {
        void onItemClick(Courses courses);
    }
    public void setOnItemClickListener(OnItemClickLİstener listener) {
        this.listener = listener;
    }
}
