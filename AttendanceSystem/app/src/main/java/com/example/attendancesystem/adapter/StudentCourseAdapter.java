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
import com.example.attendancesystem.models.StudentCourses;

import java.util.ArrayList;

public class StudentCourseAdapter extends  RecyclerView.Adapter<StudentCourseAdapter.StudentCourseHolder>{

    private ArrayList<StudentCourses> coursesList;
    private Context context;

    private OnItemClickLİstener listener;


    public StudentCourseAdapter(ArrayList<StudentCourses> coursesList, Context context) {
        this.coursesList = coursesList;
        this.context = context;
    }

    @NonNull
    @Override
    public StudentCourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.student_course_item, parent, false);
        return new StudentCourseHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentCourseHolder holder, int position) {

        StudentCourses course = coursesList.get(position);
        holder.setData(course);
    }

    @Override
    public int getItemCount() {
        return coursesList.size();

    }

    class StudentCourseHolder extends  RecyclerView.ViewHolder{

        TextView course_name, course_code,instructor;

        public StudentCourseHolder(@NonNull View itemView){
            super(itemView);
            course_code = (TextView) itemView.findViewById(R.id.course_name);
            course_name =(TextView) itemView.findViewById(R.id.course_code);
        }

        public void setData(StudentCourses course) {
            if (course != null) {
                course_name.setText(course.getCourse_name());
                course_code.setText(course.getCourse_code());
            }

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
        void onItemClick(StudentCourses courses);
    }
    public void setOnItemClickListener(OnItemClickLİstener listener) {
        this.listener = listener;
    }
}
