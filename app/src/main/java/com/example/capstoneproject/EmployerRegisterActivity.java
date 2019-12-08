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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EmployerRegisterActivity extends AppCompatActivity {
    EditText etcmpname, etemail, etpwd, etcpwd, etph;
    Button btnlogin, btnreg;
    FirebaseAuth mAuth;
    DatabaseReference databaseDrivers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_register);
        etcmpname = findViewById(R.id.emplrreg_edtCmpName);
        etpwd = findViewById(R.id.emplrreg_edtCmpPassword);
        etemail = findViewById(R.id.emplrreg_edtCmpEmail);
        etcpwd = findViewById(R.id.emplrreg_edtconfirmPassword);
        etph = findViewById(R.id.emplrreg_edtcontact);
        btnlogin = findViewById(R.id.emplrreg_btnLogin);
        btnreg = findViewById(R.id.emplrreg_btnRegister);
        databaseDrivers = FirebaseDatabase.getInstance().getReference("Employers");
        mAuth = FirebaseAuth.getInstance();
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EmployerRegisterActivity.this, EmployerLoginActivity.class);
                startActivity(i);
            }
        });

        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Name, Email, Phn, Password, cPassword;

                Name = etcmpname.getText().toString().trim(); // also pass name and phone details to database after viewing costing
                Email = etemail.getText().toString().trim();
                Phn = etph.getText().toString().trim();
                Password = etpwd.getText().toString().trim();
                cPassword = etcpwd.getText().toString().trim();
                if (Name.isEmpty() || Email.isEmpty() || Phn.isEmpty() || (Phn.length() < 10) || Password.isEmpty() || cPassword.isEmpty() || (!Password.equals(cPassword)))
                    Toast.makeText(EmployerRegisterActivity.this, "Enter all fields and make sure password and confirm password are same", Toast.LENGTH_LONG).show();
                else if (!isValidEmail(Email)) {
                    Toast.makeText(EmployerRegisterActivity.this, "Email should be in correct format", Toast.LENGTH_LONG).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                sendEmailver();
                                addCredentials();
                            } else {
                                Toast.makeText(EmployerRegisterActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public void addCredentials() {
        String cid = databaseDrivers.push().getKey();
        String cmpEmail, cmpPhn, cmpName = new String();

        cmpEmail = etemail.getText().toString().trim();
        cmpPhn = etph.getText().toString().trim();
        cmpName = etcmpname.getText().toString().trim();

        String cmpid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Company cmpobj = new Company(cid, cmpid, cmpName, cmpEmail, cmpPhn);
        databaseDrivers.child(cid).setValue(cmpobj);

        Intent i = new Intent(EmployerRegisterActivity.this, EmployerLoginActivity.class);
        startActivity(i);

    }

    private void sendEmailver() {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(EmployerRegisterActivity.this, "Successfully Registered, Verification mail sent!", Toast.LENGTH_SHORT).show();
                        mAuth.signOut();

                    } else {
                        Toast.makeText(EmployerRegisterActivity.this, "Verification mail has not been send", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}
