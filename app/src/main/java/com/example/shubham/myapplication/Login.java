package com.example.shubham.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
    private Button button3;              //button3 is for shifting to register activity using intent
    private Button login;
    private EditText userName;
    private EditText password;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth=FirebaseAuth.getInstance();
        addListenerOnClick();

        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null)
        {
            Intent i=new Intent(Login.this,MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }

    }
    void userLogin()
    {
        final String email = userName.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (pass.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        //create user
       mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful())
               {
                   progressBar.setVisibility(View.GONE);
                   Toast.makeText(getApplicationContext(), "Login Succesful", Toast.LENGTH_SHORT).show();
                   Intent in=new Intent(Login.this,MainActivity.class);
                   startActivity(in);
               }
               else
               {
                   progressBar.setVisibility(View.GONE);
                   Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
               }
           }
       });
    }
    void addListenerOnClick()
    {
        login=(Button)findViewById(R.id.login);
        button3=(Button)findViewById(R.id.button3);
        userName=(EditText)findViewById(R.id.userName);
        password=(EditText)findViewById(R.id.password);
        progressBar= (ProgressBar)findViewById(R.id.progressBar);
        //shifting activity
        button3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Login.this,registerActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });

    }
}
