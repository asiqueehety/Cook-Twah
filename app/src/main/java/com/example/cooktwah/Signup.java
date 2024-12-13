package com.example.cooktwah;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signup extends AppCompatActivity {

    EditText et_ea;
    EditText et_pw;
    EditText et_rpw;
    Button signup;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        et_ea = findViewById(R.id.et_ea);
        et_pw = findViewById(R.id.et_pw);
        et_rpw = findViewById(R.id.et_rpw);
        signup = findViewById(R.id.signup);
        mAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(v -> {
            String email = et_ea.getText().toString();
            String password = et_pw.getText().toString();
            String repassword = et_rpw.getText().toString();
            if (email.isEmpty() || password.isEmpty() || repassword.isEmpty()) {
                Toast.makeText(Signup.this, "All fields are required", Toast.LENGTH_SHORT).show();
            }
            else if(password.length()<=6)
            {
                Toast.makeText(this,"Password must be greater than 6 characters",Toast.LENGTH_SHORT).show();
            }
            else if(!password.equals(repassword))
            {
                Toast.makeText(this,"Passwords do not match", Toast.LENGTH_SHORT).show();
            }
            else{
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task->
                {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            user.sendEmailVerification().addOnCompleteListener(emailTask -> {
                                if (emailTask.isSuccessful()) {
                                    Toast.makeText(this, "Verification email sent. Please check your email.", Toast.LENGTH_LONG).show();
                                    // Redirect to login or verification activity
                                    Intent intent = new Intent(this, VerificationActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(this, "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                    else
                    {
                        Toast.makeText(this, "ACCOUNT ALREADY EXISTS", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}