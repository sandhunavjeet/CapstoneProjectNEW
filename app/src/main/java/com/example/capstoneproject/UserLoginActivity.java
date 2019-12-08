package com.example.capstoneproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserLoginActivity extends AppCompatActivity {

    EditText etusremail, etpwd;
    Button btnlogin, btnreg;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        etusremail = findViewById(R.id.emplogin_edtUserEmail);
        etpwd = findViewById(R.id.emplogin_edtPassword);
        btnlogin = findViewById(R.id.emplogin_btnLogin);
        btnreg = findViewById(R.id.emplogin_btnRegistration);
        fAuth=FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser()!=null)
        {
            Intent intent= new Intent(this,UserMainActivity.class);
            startActivity(intent);
        }
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,password;
                email=etusremail.getText().toString().trim();
                password=etpwd.getText().toString().trim();
                if(email.isEmpty()||password.isEmpty())
                {
                    Toast.makeText(UserLoginActivity.this,"Valid email and password required",Toast.LENGTH_LONG).show();
                }
                else if( ! isValidEmail(email))
                {
                    Toast.makeText(UserLoginActivity.this, "Email should be in correct format",Toast.LENGTH_LONG).show();
                }
                else
                {
                    btnlogin.setEnabled(false);
                    fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                checkEmailver();
                            }
                            else
                            {
                                Toast.makeText(UserLoginActivity.this,"Invalid Credentials",Toast.LENGTH_LONG).show();
                                etusremail.setText("");
                                etpwd.setText("");
                                btnlogin.setEnabled(true);
                            }
                        }
                    });
                }
            }
        });

        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent i = new Intent(UserLoginActivity.this, UserRegisterActivity.class);
             startActivity(i);
            }
        });

    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public void checkEmailver() {
        FirebaseUser firebaseUser = fAuth.getCurrentUser();
        Boolean emailflag = firebaseUser.isEmailVerified();

        if (emailflag) {
            startActivity(new Intent(UserLoginActivity.this,UserMainActivity.class));
        }
        else {
            Toast.makeText(UserLoginActivity.this,"Verify your email",Toast.LENGTH_SHORT).show();
            fAuth.signOut();
            btnlogin.setEnabled(true);
            etusremail.setText("");
            etpwd.setText("");
        }
    }
}
