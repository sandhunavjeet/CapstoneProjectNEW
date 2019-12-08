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

public class EmployerLoginActivity extends AppCompatActivity {
    EditText etempemail, etpwd;
    Button btnlogin, btnreg;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_login);
        etempemail = findViewById(R.id.emplrlogin_edtUserEmail);
        etpwd = findViewById(R.id.emplrlogin_edtPassword);
        btnlogin = findViewById(R.id.emplrlogin_btnLogin);
        btnreg = findViewById(R.id.emplrlogin_btnRegistration);
        fAuth=FirebaseAuth.getInstance();


        if (fAuth.getCurrentUser()!=null)
        {
            Intent intent= new Intent(this,EmployerMainActivity.class);
            startActivity(intent);
        }
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,password;
                email=etempemail.getText().toString().trim();
                password=etpwd.getText().toString().trim();
                if(email.isEmpty()||password.isEmpty())
                {
                    Toast.makeText(EmployerLoginActivity.this,"Valid email and password required",Toast.LENGTH_LONG).show();
                }
                else if( ! isValidEmail(email))
                {
                    Toast.makeText(EmployerLoginActivity.this, "Email should be in correct format",Toast.LENGTH_LONG).show();
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
                                Toast.makeText(EmployerLoginActivity.this,"Invalid Credentials",Toast.LENGTH_LONG).show();
                                etempemail.setText("");
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
                Intent i = new Intent(EmployerLoginActivity.this, EmployerRegisterActivity.class);
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
            startActivity(new Intent(EmployerLoginActivity.this,EmployerMainActivity.class));
        }
        else {
            Toast.makeText(EmployerLoginActivity.this,"Verify your email",Toast.LENGTH_SHORT).show();
            fAuth.signOut();
            btnlogin.setEnabled(true);
            etempemail.setText("");
            etpwd.setText("");
        }
    }
}
