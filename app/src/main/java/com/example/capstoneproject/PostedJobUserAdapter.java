package com.example.capstoneproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class PostedJobUserAdapter
        extends
        RecyclerView.Adapter<PostedJobUserAdapter.JobViewHolder> {
    private Context mctx;
    private List<Job> joblist;

    public PostedJobUserAdapter(Context mctx, List<Job> joblist) {
        this.mctx = mctx;
        this.joblist = joblist;
    }

    @NonNull
    @Override
    public PostedJobUserAdapter.JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                 int viewType) {
        View view = LayoutInflater.from(mctx).inflate(R.layout.user_view_all_jobs_card,
                parent, false);
        PostedJobUserAdapter.JobViewHolder holder = new
                PostedJobUserAdapter.JobViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PostedJobUserAdapter.JobViewHolder holder, int position) {

        Job obj = joblist.get(position);
        final String jobid = obj.getJobid();
        final String cmpid = obj.getCmpid();
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String cc = ""+jobid+"_"+uid+"_"+cmpid;

        holder.title.setText(obj.getTitle());
        holder.desc.setText(obj.getDesc());
        holder.date.setText(obj.getAppdate());
        holder.time.setText(obj.getApptime());
        holder.pay.setText(obj.getPay());


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

        holder.applybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               DatabaseReference dbAppliedJobs = FirebaseDatabase.getInstance().getReference("ApplyJobs");
                String id = dbAppliedJobs.push().getKey();
                String cc = ""+jobid+"_"+uid+"_"+cmpid;
                AppliedJob aj = new AppliedJob(id, jobid, cmpid, uid,cc,"Pending");
                dbAppliedJobs.child(id).setValue(aj);
                Toast.makeText(mctx,"Applied Successfully",Toast.LENGTH_LONG).show();

                mctx.startActivity(new Intent(mctx,UserMainActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return joblist.size();
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
        Button applybtn;
        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.user_jobs_title);
            pay = itemView.findViewById(R.id.user_jobs_pay);
            date = itemView.findViewById(R.id.user_jobs_date);
            time = itemView.findViewById(R.id.user_jobs_time);
            desc = itemView.findViewById(R.id.user_jobs_desc);
            cmpname = itemView.findViewById(R.id.user_jobs_name);
            cmpemail = itemView.findViewById(R.id.user_jobs_email);
            cmpph = itemView.findViewById(R.id.user_jobs_contact);
            applybtn = itemView.findViewById(R.id.user_jobs_btn_apply);
        }
    }
}