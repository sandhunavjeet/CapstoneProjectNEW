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

public class Emplr_New_Requests extends AppCompatActivity {
    String cid;
    Query dbappliedjobs;
    RecyclerView rv;
    private List<AppliedJob> newRequestList;
    NewRequestEmployeeAdapter pja;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emplr__new__requests);

        setTitle("New Job Requests");
        cid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dbappliedjobs = FirebaseDatabase.getInstance().
                getReference("ApplyJobs").orderByChild("cmpid").equalTo(cid);;
        newRequestList=new ArrayList<>();

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("WAIT");
        progressDialog.setMessage("Please wait while we are getting new requests");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setProgress(0);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(true);
        progressDialog.show();

        rv = findViewById(R.id.emplr_recycler_new_requests);
        newRequestList.clear();
        dbappliedjobs.addListenerForSingleValueEvent(valueEventListener);

    }

    ValueEventListener valueEventListener= new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()){
                newRequestList.clear();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    AppliedJob obj=data.getValue(AppliedJob.class);
                    if(obj.getStatus().equals("Pending")) {
                        newRequestList.add(obj);
                    }
                }
                Collections.reverse(newRequestList);
                pja=new NewRequestEmployeeAdapter(Emplr_New_Requests.this,newRequestList);
                rv.setLayoutManager(new LinearLayoutManager(Emplr_New_Requests.this));
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
