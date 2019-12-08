package com.example.capstoneproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PostedJobAdapter extends
        RecyclerView.Adapter<PostedJobAdapter.JobViewHolder>
{
    private Context mctx;
    private List<Job> joblist;

    public PostedJobAdapter(Context mctx, List<Job> joblist) {
        this.mctx = mctx;
        this.joblist = joblist;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                             int viewType) {
        View view= LayoutInflater.from(mctx).inflate(R.layout.emplr_jobs_card,
                parent,false);
        JobViewHolder holder=new JobViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {

        Job obj= joblist.get(position);
        holder.title.setText(obj.getTitle());
        holder.desc.setText(obj.getDesc());
        holder.date.setText(obj.getAppdate());
        holder.time.setText(obj.getApptime());
        holder.pay.setText(obj.getPay());
    }
    @Override
    public int getItemCount() {
        return joblist.size();
    }

    class JobViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView pay;
        TextView date;
        TextView time;
        TextView desc;
        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.emplr_jobs_title);
            pay=itemView.findViewById(R.id.emplr_jobs_pay);
            date=itemView.findViewById(R.id.emplr_jobs_date);
            time=itemView.findViewById(R.id.emplr_jobs_time);
            desc=itemView.findViewById(R.id.emplr_jobs_desc);
        }
    }
}