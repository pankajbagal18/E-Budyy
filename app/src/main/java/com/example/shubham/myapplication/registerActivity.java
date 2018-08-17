package com.example.shubham.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class registerActivity extends AppCompatActivity {
    private EditText userName;
    private EditText password;
    private EditText academicRoll;
    private EditText contactNo;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        addListenerOnClick();
    }

    void addListenerOnClick()
    {

        userName=(EditText)findViewById(R.id.userName);
        password=(EditText)findViewById(R.id.password);
        academicRoll=(EditText)findViewById(R.id.academicRoll);
        contactNo=(EditText)findViewById(R.id.contactNo);
        register=(Button)findViewById(R.id.register);
        progressBar= (ProgressBar)findViewById(R.id.progressBar);
        firebaseAuth=FirebaseAuth.getInstance();


        //     Toast.makeText(this,email,Toast.LENGTH_SHORT).show();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                registerUser();

            }
        });


    }
    void registerUser()
    {
        final String email = userName.getText().toString().trim();
        String pass = password.getText().toString().trim();
        final String phone= contactNo.getText().toString().trim();
        final String rolll= academicRoll.getText().toString().trim();
        final int roll=Integer.parseInt(rolll);

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
        firebaseAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(registerActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        Toast.makeText(registerActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(registerActivity.this, "Authentication failed." + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        } else {


                            User user=new User(
                                    email,roll,phone,0,0,0
                            );
                            attendanceData ad=new attendanceData(0,0,0,0,0);


                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(registerActivity.this, "Authentication Successful." + task.getException(),
                                                Toast.LENGTH_SHORT).show();

                                    }
                                    else
                                    {

                                        Toast.makeText(registerActivity.this, "Authentication Unsuccessful." + task.getException(),
                                                Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                            FirebaseDatabase.getInstance().getReference("Attendance")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(ad).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(registerActivity.this, "Authentication Successful." + task.getException(),
                                                Toast.LENGTH_SHORT).show();

                                    }
                                    else
                                    {

                                        Toast.makeText(registerActivity.this, "Authentication Unsuccessful." + task.getException(),
                                                Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                            // finish();
                            Intent intent =new Intent(getApplicationContext(),Login.class);
                            startActivity(intent);

                        }//else ends here
                    }
                });


    }
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}
