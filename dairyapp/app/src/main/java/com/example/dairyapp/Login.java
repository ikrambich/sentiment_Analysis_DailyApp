package com.example.dairyapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class Login extends AppCompatActivity {

    Button btnLogin;
    TextView tv4;
    EditText password, email;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        password = findViewById(R.id.edPasswordLogin);
        email = findViewById(R.id.edEmailLogin);
        btnLogin = findViewById(R.id.btnLogin);
        tv4 = findViewById(R.id.tv4);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwordLogin = password.getText().toString().trim();
                String emailLogin = email.getText().toString().trim();

                if (!emailLogin.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailLogin).matches()) {
                    if (!passwordLogin.isEmpty()) {
                        auth.signInWithEmailAndPassword(emailLogin, passwordLogin)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(Login.this, "You have Logged In Successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(Login.this, displaying_diaries.class));
                                            finish();
                                        } else {
                                            // Handle different Firebase authentication exceptions
                                            if (task.getException() != null) {
                                                try {
                                                    throw task.getException();
                                                } catch (FirebaseAuthInvalidUserException e) {
                                                    email.setError("No account found with this email. Please sign up.");
                                                } catch (FirebaseAuthInvalidCredentialsException e) {
                                                    password.setError("Incorrect password. Try again.");
                                                } catch (FirebaseAuthException e) {
                                                    Toast.makeText(Login.this, "Authentication Failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                                } catch (Exception e) {
                                                    Toast.makeText(Login.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        }
                                    }
                                });

                    } else {
                        password.setError("Please Enter Your Password");
                    }
                } else if (emailLogin.isEmpty()) {
                    email.setError("Please Enter Your Email");
                } else {
                    email.setError("Please Enter a Valid Email");
                }
            }
        });

        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, SignUp.class));
                finish();
            }
        });
    }
}
