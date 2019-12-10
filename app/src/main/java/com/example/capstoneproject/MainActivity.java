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
        /*@Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.home, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }

        @SuppressWarnings("StatementWithEmptyBody")

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            // Handle navigation view item clicks here.
            int id = item.getItemId();

            if (id == R.id.nav_location) {
                //Intent profileIntent = new Intent(MainActivity.this,locActivity.class);
                startActivity(profileIntent);

            }   else if (id == R.id.nav_profile) {
                Intent profileIntent = new Intent(MainActivity.this,profileActivity.class);
                profileIntent.putExtra("name",name);
                startActivity(profileIntent);

            } else if (id == R.id.nav_msg) {
                Intent profileIntent = new Intent(MainActivity.this,messageActivity.class);
                profileIntent.putExtra("name",name);
                startActivity(profileIntent);

            }else if (id == R.id.nav_support) {
                Intent profileIntent = new Intent(MainActivity.this,supportActivity.class);
                profileIntent.putExtra("name",name);
                startActivity(profileIntent);

            } else if (id == R.id.nav_logout) {
                finish();
                Intent loginIntent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(loginIntent);

            }*/


        }

    }

