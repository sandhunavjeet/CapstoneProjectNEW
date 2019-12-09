package com.example.capstoneproject;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserChangePwd extends AppCompatActivity {
    EditText etoldpwd,etnewpwd;
    Button btnreset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_change_pwd);
        etoldpwd = findViewById(R.id.user_change_et_oldpwd);
        etnewpwd = findViewById(R.id.user_change_et_newpwd);

        btnreset = findViewById(R.id.user_change_btnReset);

        btnreset.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final String oldpwd = etoldpwd.getText().toString().trim();
                final String newpwd = etnewpwd.getText().toString().trim();

                if(oldpwd.isEmpty()||newpwd.isEmpty())
                {
                    Toast.makeText(UserChangePwd.this,"Kindly fill the password",Toast.LENGTH_LONG).show();
                }
                else {
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    final String email = user.getEmail();
                    AuthCredential credential = EmailAuthProvider.getCredential(email,oldpwd);



                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                user.updatePassword(newpwd).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(!task.isSuccessful()){

                                            Toast.makeText(UserChangePwd.this, "Something went wrong. Please try again later", Toast.LENGTH_LONG).show();
                                        }else {

                                            Toast.makeText(UserChangePwd.this, "Password Modified Successfully, Login with new Password", Toast.LENGTH_LONG).show();
                                            FirebaseAuth.getInstance().signOut();
                                            Intent i = new Intent(UserChangePwd.this, UserLoginActivity.class);                                             //
                                            startActivity(i);
                                        }
                                    }
                                });
                            }else {

                                Toast.makeText(UserChangePwd.this, "Authentication Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

    }
}
