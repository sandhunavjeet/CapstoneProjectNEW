package com.example.capstoneproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

public class UserMainActivity extends AppCompatActivity {

    Button btpostedjobs, btappliedjobs, btchat, btchangepwd, btlogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        btpostedjobs = findViewById(R.id.UserMain_bt_View);
        btappliedjobs = findViewById(R.id.UserMain_bt_ViewApplied);
        btchat = findViewById(R.id.UserMain_bt_chat);
        btchangepwd = findViewById(R.id.UserMain_bt_changepwd);
        btlogout = findViewById(R.id.UserMain_bt_logout);

        btpostedjobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserMainActivity.this, UserViewPostedJobs.class);
                startActivity(i);
            }
        });

        btappliedjobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserMainActivity.this, UserViewJobApply.class);
                startActivity(i);
            }
        });
        btchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btchangepwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(UserMainActivity.this, UserChangePwd.class);
                startActivity(a);
            }
        });
        btlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent a = new Intent(UserMainActivity.this, UserLoginActivity.class);
                startActivity(a);
            }
        });
    }


    public void onBackPressed() {
        super.onBackPressed();
        FirebaseAuth.getInstance().signOut();
        Intent a = new Intent(UserMainActivity.this, UserLoginActivity.class);
        startActivity(a);
    }
}