package com.example.capstoneproject;

import android.app.DatePickerDialog;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class EmplrPostNewJob extends AppCompatActivity {

    EditText et_title, et_pay, et_date, et_time, et_desc;
    Button  btn_post;
    String appdate, apptime;

    Query getname;
    DatabaseReference databaseJobs;
    String cmpid,cmpname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emplr_post_new_job);

        et_title = findViewById(R.id.emplr_post_edtTitle);
        et_pay = findViewById(R.id.emplr_post_edtPay);
        et_date = findViewById(R.id.emplr_post_date);
        et_time = findViewById(R.id.emplr_post_time);
        et_desc = findViewById(R.id.emplr_post_edtDescription);
        btn_post = findViewById(R.id.emplr_post_btnJob);

        final Calendar c = Calendar.getInstance();
        final int currday = c.get(Calendar.DAY_OF_MONTH);
        final int currmonth = c.get(Calendar.MONTH);
        final int curryear = c.get(Calendar.YEAR);

        final int curhour = c.get(Calendar.HOUR_OF_DAY);
        final int curmin = c.get(Calendar.MINUTE);

        cmpid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseJobs = FirebaseDatabase.getInstance().getReference("Jobs");
        getname = FirebaseDatabase.getInstance().getReference("Employers").orderByChild("cmpid").equalTo(cmpid);
        getname.addListenerForSingleValueEvent(valueEventListener);


        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(EmplrPostNewJob.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        if (year - curryear == 0) {
                            Log.d("Date", month + " " + (currmonth + 1) + "");
                            if (month - (currmonth + 1) == 0) {
                                Log.d("Day 0", dayOfMonth + " " + currday);
                                if (dayOfMonth - currday >= 0) {
                                    appdate = dayOfMonth + "-" + month + "-" + year;
                                    et_date.setText(appdate);
                                } else {
                                    Toast.makeText(EmplrPostNewJob.this, "Not allowed", Toast.LENGTH_SHORT).show();
                                }
                            } else if (month - (currmonth + 1) == 1) {
                                Log.d("Day 1", dayOfMonth + " " + currday);
                                appdate = dayOfMonth + "-" + month + "-" + year;
                                et_date.setText(appdate);

                            } else {
                                Toast.makeText(EmplrPostNewJob.this, "Select appropriate Month", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(EmplrPostNewJob.this, "Select appropriate Year", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, curryear, currmonth, currday);
                datePickerDialog.show();
            }
        });
//////////////////////////////////////

        et_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(EmplrPostNewJob.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        String time;
                        if (hourOfDay < 10) {
                            if (minute < 10) {
                                time = "0" + hourOfDay + ":" + "0" + minute;
                            } else {
                                time = "0" + hourOfDay + ":" + minute;
                            }
                        } else {
                            if (minute < 10) {
                                time = hourOfDay + ":" + "0" + minute;
                            } else {
                                time = hourOfDay + ":" + minute;
                            }
                        }
                        et_time.setText(time);
                        apptime = time;
                    }
                }, curhour, curmin, true);
                timePickerDialog.show();
            }
        });


        btn_post.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String jid = databaseJobs.push().getKey();
                String title = et_title.getText().toString().trim();
                String pay = et_pay.getText().toString().trim();
                String dt = et_date.getText().toString().trim();
                String tm = et_time.getText().toString().trim();
                String desc = et_desc.getText().toString().trim();

                if (title.isEmpty()||pay.isEmpty()||dt.isEmpty()||tm.isEmpty()||desc.isEmpty() ) {
                    Toast.makeText(EmplrPostNewJob.this, "Enter all fields", Toast.LENGTH_LONG).show();
                }
                else
                {

                    Job jobobj = new Job(jid,cmpid,cmpname,title,pay,dt,tm,desc);
                    databaseJobs.child(jid).setValue(jobobj);

                    Toast.makeText(EmplrPostNewJob.this,"Job Posted Successfully",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(EmplrPostNewJob.this, EmployerMainActivity.class);
                    startActivity(i);
                }
            }
        });

    }
        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Company obj = data.getValue(Company.class);
                        cmpname = obj.getName();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


}
