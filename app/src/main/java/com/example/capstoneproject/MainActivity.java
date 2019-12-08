package com.example.capstoneproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button empbtn = findViewById(R.id.btnEmployee);
        Button emplyrbtn = findViewById(R.id.btnEmployer);

        empbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, UserLoginActivity.class);
                startActivity(i);
            }
        });

        emplyrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, EmployerLoginActivity.class);
                startActivity(i);
            }
        });
    }
}

