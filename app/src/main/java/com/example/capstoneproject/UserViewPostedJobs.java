package com.example.capstoneproject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserViewPostedJobs extends AppCompatActivity {

    String uid;
    Query dbjobs;
    RecyclerView rv;
    private List<Job> jobList;
    PostedJobUserAdapter pja;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_posted_jobs);

        setTitle("All Employers Posted Jobs");
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dbjobs = FirebaseDatabase.getInstance().getReference("Jobs");
        jobList=new ArrayList<>();

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("WAIT");
        progressDialog.setMessage("Please wait while we are getting all jobs records");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(true);
        progressDialog.show();

        rv = findViewById(R.id.User_recyclerViewAllJobs);

        jobList.clear();
        dbjobs.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener= new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()){
                jobList.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    final Job obj=data.getValue(Job.class);
                    final String jobid = obj.getJobid();
                    final String cmpid = obj.getCmpid();
                    final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    String cc = "" + jobid + "_" + uid + "_" + cmpid;

                    System.out.println("combinedid:" +cc);
                    Query checkappliedjobs = FirebaseDatabase.getInstance().
                            getReference("ApplyJobs").orderByChild("jobid_userid_cmpid").equalTo(cc);

                    checkappliedjobs.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot ds) {
                            System.out.println("inside data change");
                            if (ds.exists()) {
                                System.out.println("inside if");
                                for (DataSnapshot data : ds.getChildren()) {
                                       System.out.println("data present");
                                }
                            }
                            else
                            {
                                jobList.add(obj);
                                pja.notifyDataSetChanged();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                    });
                }
                System.out.println("size:"+jobList.size());
                Log.d("employee",""+jobList.size());
                Collections.reverse(jobList);
                pja=new PostedJobUserAdapter(UserViewPostedJobs.this,jobList);
                rv.setLayoutManager(new LinearLayoutManager(UserViewPostedJobs.this));
                progressDialog.dismiss();
                rv.setAdapter(pja);

                pja.notifyDataSetChanged();
            }
            else
            {
                progressDialog.dismiss();
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

}
