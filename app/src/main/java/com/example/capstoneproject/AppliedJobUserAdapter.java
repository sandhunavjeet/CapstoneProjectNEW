package com.example.capstoneproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class AppliedJobUserAdapter
        extends
        RecyclerView.Adapter<AppliedJobUserAdapter.JobViewHolder> {
    private Context mctx;
    private List<AppliedJob> appliedJobList;

    public AppliedJobUserAdapter(Context mctx, List<AppliedJob> joblist) {
        this.mctx = mctx;
        this.appliedJobList = joblist;
    }

    @NonNull
    @Override
    public AppliedJobUserAdapter.JobViewHolder
    onCreateViewHolder(@NonNull ViewGroup parent,
                                                                  int viewType) {
        View view = LayoutInflater.from(mctx).inflate(R.layout.user_view_applied_jobs_card,
                parent, false);
        AppliedJobUserAdapter.JobViewHolder holder = new
                AppliedJobUserAdapter.JobViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AppliedJobUserAdapter.JobViewHolder holder,
                                 int position) {

        AppliedJob obj = appliedJobList.get(position);
        final String jobid = obj.getJobid();
        final String cmpid = obj.getCmpid();
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
          String status = obj.getStatus();
          holder.status.setText(status);

        Query jobdetails = FirebaseDatabase.getInstance().
                getReference("Jobs").orderByChild("jobid").equalTo(jobid);

        jobdetails.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Job jobobj = data.getValue(Job.class);
                        holder.title.setText(jobobj.getTitle());
                        holder.desc.setText(jobobj.getDesc());
                        holder.date.setText(jobobj.getAppdate());
                        holder.time.setText(jobobj.getApptime());
                        holder.pay.setText(jobobj.getPay());

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Query cmpdetails = FirebaseDatabase.getInstance().
                getReference("Employers").orderByChild("cmpid").equalTo(cmpid);

        cmpdetails.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Company cmpbj = data.getValue(Company.class);

                        holder.cmpname.setText(cmpbj.getName());
                        holder.cmpemail.setText(cmpbj.getEmail());
                        holder.cmpph.setText(cmpbj.getPhone());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });




    }

    @Override
    public int getItemCount() {
        return appliedJobList.size();
    }

    class JobViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView pay;
        TextView date;
        TextView time;
        TextView desc;
        TextView cmpname;
        TextView cmpemail;
        TextView cmpph;
        TextView status;
        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.user_jobs_title_apply);
            pay = itemView.findViewById(R.id.user_jobs_pay_apply);
            date = itemView.findViewById(R.id.user_jobs_date_apply);
            time = itemView.findViewById(R.id.user_jobs_time_apply);
            desc = itemView.findViewById(R.id.user_jobs_desc_apply);
            cmpname = itemView.findViewById(R.id.user_jobs_name_apply);
            cmpemail = itemView.findViewById(R.id.user_jobs_email_apply);
            cmpph = itemView.findViewById(R.id.user_jobs_contact_apply);
            status = itemView.findViewById(R.id.user_jobs_status_apply);
        }
    }
}