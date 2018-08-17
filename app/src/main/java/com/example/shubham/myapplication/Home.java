package com.example.shubham.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Home extends Fragment {
    Button navigateToBudget,navigateToAttendance,navigateToNotes,navigateToReminder;
    Activity context;
    Context c;
    TextView uName,attend,budget,getNoteCount;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    static float attt;
    int noteCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context=getActivity();
        return inflater.inflate(R.layout.fragment_home, container, false);



    }
    public void onStart()
    {
        uName=(TextView)context.findViewById(R.id.uName);
       attend=(TextView)context.findViewById(R.id.attend);
       budget=(TextView)context.findViewById(R.id.budget);
       getNoteCount=(TextView)context.findViewById(R.id.getNoteCount);
        super.onStart();

        navigateToBudget=(Button)context.findViewById(R.id.navigateToBudget);
        navigateToBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,BudgetPlanner.class);
                startActivity(intent);


            }
        });
        navigateToAttendance=(Button)context.findViewById(R.id.navigateToAttendance);
        navigateToAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(context,Attendance.class);
                startActivity(intent1);
            }
        });
        navigateToNotes=(Button)context.findViewById(R.id.navigateToNotes);
        navigateToNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(context,Notes.class);
                startActivity(intent1);
            }
        });
        navigateToReminder=(Button)context.findViewById(R.id.navigateToReminder);
        navigateToReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(context,Reminder.class);
                startActivity(intent1);
            }
        });
        firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();



        uName.setText(user.getEmail());


        databaseReference= FirebaseDatabase.getInstance().getReference("Attendance").child(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                String os=dataSnapshot.child("OS").getValue().toString();
                String dbms=dataSnapshot.child("DBMS").getValue().toString();
                String sepm=dataSnapshot.child("SEPM").getValue().toString();
                String toc=dataSnapshot.child("TOC").getValue().toString();
                String hci=dataSnapshot.child("HCI").getValue().toString();

                int oss=Integer.parseInt(os);

                int dbmss=Integer.parseInt(dbms);

                int sepmm=Integer.parseInt(sepm);

                int tocc=Integer.parseInt(toc);

                int hcii=Integer.parseInt(hci);

                attt=(oss+dbmss+tocc+sepmm+tocc+hcii)/5;

                attend.setText(Float.toString(attt));


            }


            //  UserData  user1=new UserData("email","3957","123");
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT ).show();
            }
        });


        databaseReference=FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              //  String phone=dataSnapshot.child("phone").getValue().toString();
              //  String email=dataSnapshot.child("email").getValue().toString();
               // String roll=dataSnapshot.child("roll").getValue().toString();
                String ttAmt=dataSnapshot.child("totalAmount").getValue().toString();
               int finalAmount = Integer.parseInt(ttAmt);
             //   finalRoll=Integer.parseInt(roll);
             //   Toast.makeText(getApplicationContext(),phone+email+roll,Toast.LENGTH_SHORT).show();
                //   rollNo[0] =roll;
                //   phoneNo[0] =phone;
                budget.setText(Integer.toString(finalAmount));

            }
            //  UserData  user1=new UserData("email","3957","123");
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT ).show();
            }
        });

        DBHelper db=new DBHelper(context);
        noteCount=db.getAllData().getCount();

        getNoteCount.setText(Integer.toString(noteCount));



    }
   public  void shiftToAttendance(View view)
    {


    }



}