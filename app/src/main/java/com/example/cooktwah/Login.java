package com.example.cooktwah;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private EditText et_ea;
    private EditText et_pw;
    private TextView tv1;
    private TextView tv2;
    private Button sb;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        et_ea = findViewById(R.id.edt_ea);
        et_pw = findViewById(R.id.edt_pw);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        sb = findViewById(R.id.submit);
        mAuth = FirebaseAuth.getInstance();
        sb.setOnClickListener(view -> {
            String email = et_ea.getText().toString();
            String password = et_pw.getText().toString();
            if(email.isEmpty() || password.isEmpty())
            {
                Toast.makeText(this,"Please fill in all fields",Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task->
            {
                FirebaseUser user = mAuth.getCurrentUser();
                if(user!=null && !user.isEmailVerified())
                {
                    Toast.makeText(this, "Please verify your email before logging in.", Toast.LENGTH_SHORT).show();
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
                else if(task.isSuccessful() && user.isEmailVerified())
                {
                    Toast.makeText(this, "SUCCESSFULLY LOGGED IN!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, Homepage.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(this, "LOGIN FAILED!", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}