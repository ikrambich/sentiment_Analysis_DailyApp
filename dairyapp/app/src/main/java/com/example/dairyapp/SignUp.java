package com.example.dairyapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {
    FirebaseAuth auth;
    EditText password,email;
    Button btnSignup;
    TextView tv2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);

        auth=FirebaseAuth.getInstance();
        password=findViewById(R.id.edPassword);
        email=findViewById(R.id.edEmail);
        btnSignup=findViewById(R.id.btnSignUp);
        tv2=findViewById(R.id.tv2);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwordSignup=password.getText().toString().trim();
                String emailSignup=email.getText().toString().trim();

                if (emailSignup.isEmpty()){
                    email.setError("Please Enter Your Email");
                }
                if (passwordSignup.isEmpty()){
                    password.setError("Please Enter Your Password");
                }
                else {
                    auth.createUserWithEmailAndPassword(emailSignup,passwordSignup).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(SignUp.this,"You have successfully Signed Up",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUp.this, Login.class));
                            }
                            else {
                                Toast.makeText(SignUp.this,"Something went wrong, Try again!"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }


            }
        });

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SignUp.this,Login.class);
                startActivity(i);
            }
        });






    }
}