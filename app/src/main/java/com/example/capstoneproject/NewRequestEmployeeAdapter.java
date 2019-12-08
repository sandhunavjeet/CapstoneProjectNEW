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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;

public class NewRequestEmployeeAdapter
        extends
        RecyclerView.Adapter<NewRequestEmployeeAdapter.JobViewHolder> {
    private Context mctx;
    private List<AppliedJob> newrequestlist;

    public NewRequestEmployeeAdapter(Context mctx, List<AppliedJob> newrequestlist) {
        this.mctx = mctx;
        this.newrequestlist = newrequestlist;
    }

    @NonNull
    @Override
    public NewRequestEmployeeAdapter.JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                      int viewType) {
        View view = LayoutInflater.from(mctx).inflate(R.layout.employer_view_new_requests_card,
                parent, false);

        NewRequestEmployeeAdapter.JobViewHolder holder = new
                NewRequestEmployeeAdapter.JobViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final NewRequestEmployeeAdapter.JobViewHolder holder,
                                 int position) {

        AppliedJob obj = newrequestlist.get(position);
        final String id = obj.getId();
        final String jobid = obj.getJobid();
        final String cmpid = obj.getCmpid();
        final String uid = obj.getUserid();




/////////////////////////////////////////////////////////////////
        Query jobdetails = FirebaseDatabase.getInstance().
                getReference("Jobs").orderByChild("jobid").equalTo(jobid);

        jobdetails.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Job obj = data.getValue(Job.class);
                        holder.title.setText(obj.getTitle());
                        holder.desc.setText(obj.getDesc());
                        holder.date.setText(obj.getAppdate());
                        holder.time.setText(obj.getApptime());
                        holder.pay.setText(obj.getPay());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    ////////////////////
        Query usrdetails = FirebaseDatabase.getInstance().
                getReference("Users").orderByChild("usrid").equalTo(uid);
        usrdetails.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        User obj = data.getValue(User.class);
                        holder.usrname.setText(obj.getName());
                        holder.usremail.setText(obj.getEmail());
                        holder.usrph.setText(obj.getPhone());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

/////////////////////////////////////////////////
        holder.acceptbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                HashMap<String, Object> result = new HashMap<>();
                result.put("status", "Approved");

               FirebaseDatabase.getInstance().
                       getReference("ApplyJobs").child(id).updateChildren(result);

                Toast.makeText(mctx,"Approved Successfully",Toast.LENGTH_LONG).show();
                mctx.startActivity(new Intent(mctx,EmployerMainActivity.class));
            }
        });
        ///////////////////////
        holder.rejectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                HashMap<String, Object> result = new HashMap<>();
                result.put("status", "Rejected");

                FirebaseDatabase.getInstance().
                        getReference("ApplyJobs").child(id).updateChildren(result);

                Toast.makeText(mctx,"Rejected Successfully",Toast.LENGTH_LONG).show();
                mctx.startActivity(new Intent(mctx,EmployerMainActivity.class));
            }
        });


        ////////
    }

    @Override
    public int getItemCount() {
        return newrequestlist.size();
    }

    class JobViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView pay;
        TextView date;
        TextView time;
        TextView desc;
        TextView usrname;
        TextView usremail;
        TextView usrph;
        Button acceptbtn,rejectbtn;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.emp_new_request_jobs_title);
            pay = itemView.findViewById(R.id.emp_new_request_jobs_pay);
            date = itemView.findViewById(R.id.emp_new_request_jobs_date);
            time = itemView.findViewById(R.id.emp_new_request_jobs_time);
            desc = itemView.findViewById(R.id.emp_new_request_jobs_desc);
            usrname = itemView.findViewById(R.id.emp_new_request_jobs_name);
            usremail = itemView.findViewById(R.id.emp_new_request_jobs_email);
            usrph = itemView.findViewById(R.id.emp_new_request_jobs_contact);
            acceptbtn = itemView.findViewById(R.id.emp_new_request_jobs_btn_accept);
            rejectbtn = itemView.findViewById(R.id.emp_new_request_jobs_btn_reject);
        }
    }
}