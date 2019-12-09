package com.example.capstoneproject;


import android.os.Bundle;

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

public class Emplr_ViewPostedJobs extends AppCompatActivity {
    String cmpid;
    Query dbjobs;
    RecyclerView rv;
    private List<Job> jobList;
    PostedJobAdapter pja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emplr__view_posted_jobs);
        setTitle("Employer Posted Jobs");
        String cmpid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dbjobs = FirebaseDatabase.getInstance().getReference("Jobs").orderByChild("cmpid").equalTo(cmpid);
        jobList=new ArrayList<>();


        rv = findViewById(R.id.Emplr_recyclerViewJobs);

        jobList.clear();
        dbjobs.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener= new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()){
                jobList.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    Job obj=data.getValue(Job.class);
                    jobList.add(obj);
                }
                Collections.reverse(jobList);
                pja=new PostedJobAdapter(Emplr_ViewPostedJobs.this,jobList);
                rv.setLayoutManager(new LinearLayoutManager(Emplr_ViewPostedJobs.this));

                rv.setAdapter(pja);
                pja.notifyDataSetChanged();
            }
            else
            {

            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
