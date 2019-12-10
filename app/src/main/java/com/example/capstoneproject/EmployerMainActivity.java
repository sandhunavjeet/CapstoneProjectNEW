package com.example.capstoneproject;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

public class EmployerMainActivity extends AppCompatActivity {

    Button btnpostnew, btnviewposted, btnlogout, btnchangepwd,
            btnrequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_main);

        btnpostnew = findViewById(R.id.emplrMain_bt_Post);
        btnviewposted = findViewById(R.id.emplrMain_bt_ViewAll);
        btnlogout = findViewById(R.id.emplrMain_bt_logout);
        btnchangepwd = findViewById(R.id.emplrMain_bt_changepwd);
        btnrequests = findViewById(R.id.emplrMain_bt_New);

        btnpostnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EmployerMainActivity.this,
                        EmplrPostNewJob.class);
                startActivity(i);
            }
        });

        btnviewposted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EmployerMainActivity.this,
                        Emplr_ViewPostedJobs.class);
                startActivity(i);
            }
        });

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(EmployerMainActivity.this, EmployerLoginActivity.class);
                //
                startActivity(i);

            }
        });

        btnchangepwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EmployerMainActivity.this, EmplrChangePwd.class);
                startActivity(i);

            }
        });

        btnrequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
    public void onBackPressed() {
        super.onBackPressed();
        FirebaseAuth.getInstance().signOut();
        Intent a = new Intent(EmployerMainActivity.this, EmployerLoginActivity.class);
        startActivity(a);
    }
}
