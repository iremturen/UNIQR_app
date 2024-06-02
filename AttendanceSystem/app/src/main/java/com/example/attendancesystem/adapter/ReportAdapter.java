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

import java.util.ArrayList;

public class ReportAdapter extends  RecyclerView.Adapter<ReportAdapter.ReportHolder>{

    private ArrayList<Reports> reportsList;
    private Context context;

    private OnItemClickLİstener listener;


    public ReportAdapter(ArrayList<Reports> reportsList, Context context) {
        this.reportsList = reportsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ReportHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.staff_reports_item, parent, false);
        return new ReportHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportHolder holder, int position) {

        Reports report = reportsList.get(position);
        holder.setData(report);
    }

    @Override
    public int getItemCount() {
        return reportsList.size();

    }

    class ReportHolder extends  RecyclerView.ViewHolder{

        TextView report_name;

        public ReportHolder(@NonNull View itemView){
            super(itemView);
            report_name = (TextView) itemView.findViewById(R.id.report_name);

        }

        public void setData(Reports report) {
            this.report_name.setText(report.getReport_name());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemClick(reportsList.get(position));
                }
            });
        }


    }

    public interface OnItemClickLİstener {
        void onItemClick(Reports reports);
    }
    public void setOnItemClickListener(OnItemClickLİstener listener) {
        this.listener = listener;
    }
}
