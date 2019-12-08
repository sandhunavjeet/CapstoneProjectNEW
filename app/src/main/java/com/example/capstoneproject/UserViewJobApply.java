package com.example.capstoneproject;

import android.app.ProgressDialog;
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

public class UserViewJobApply extends AppCompatActivity {

    String uid;
    Query dbAppliedjobs;
    RecyclerView rv;
    private List<AppliedJob> applyjobList;
    AppliedJobUserAdapter pja;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_job_apply);

        setTitle("All Applied Jobs");
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        dbAppliedjobs = FirebaseDatabase.getInstance().getReference("ApplyJobs")
                .orderByChild("userid").equalTo(uid);;
        applyjobList=new ArrayList<>();

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("WAIT");
        progressDialog.setMessage("Please wait while we are getting all applied jobs records");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(true);
        progressDialog.show();

        rv = findViewById(R.id.User_recyclerViewApplyJobs);

        applyjobList.clear();
        dbAppliedjobs.addListenerForSingleValueEvent(valueEventListener);
    }
    ValueEventListener valueEventListener= new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()){
                applyjobList.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                     AppliedJob obj=data.getValue(AppliedJob.class);
                    applyjobList.add(obj);
                 }
                Collections.reverse(applyjobList);
                pja=new AppliedJobUserAdapter(UserViewJobApply.this,applyjobList);
                rv.setLayoutManager(new LinearLayoutManager(UserViewJobApply.this));
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
