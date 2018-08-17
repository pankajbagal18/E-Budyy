package com.example.shubham.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Attendance extends AppCompatActivity{
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    ListView lst;
    TextView tv11,tv12,tv21,tv22,tv31,tv32,tv41,tv42,tv51,tv52,roll_update;
    String[][] subjects = new String[][]{{"Maths","90"},{"science","78"},{"physics","88"}};
    List<Record> recordList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
     //   lst=(ListView)findViewById(R.id.lst);
        firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();

        tv11=(TextView)findViewById(R.id.tv11);
        tv12=(TextView)findViewById(R.id.tv12);

        tv21=(TextView)findViewById(R.id.tv21);

        tv22=(TextView)findViewById(R.id.tv22);

        tv31=(TextView)findViewById(R.id.tv31);
        tv32=(TextView)findViewById(R.id.tv32);
        tv41=(TextView)findViewById(R.id.tv41);

        tv42=(TextView)findViewById(R.id.tv42);

        tv51=(TextView)findViewById(R.id.tv51);

        tv52=(TextView)findViewById(R.id.tv52);

        roll_update=(TextView)findViewById(R.id.roll_update);


        databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                String roll=dataSnapshot.child("roll").getValue().toString();

               roll_update.setText(roll);

            }


            //  UserData  user1=new UserData("email","3957","123");
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT ).show();
            }
        });
        databaseReference= FirebaseDatabase.getInstance().getReference("Attendance").child(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                String os=dataSnapshot.child("OS").getValue().toString();
                String dbms=dataSnapshot.child("DBMS").getValue().toString();
                String sepm=dataSnapshot.child("SEPM").getValue().toString();
                String toc=dataSnapshot.child("TOC").getValue().toString();
                String hci=dataSnapshot.child("HCI").getValue().toString();


                tv11.setText("OS");
                tv12.setText(os);

                tv21.setText("DBMS");
                tv22.setText(dbms);

                tv31.setText("SEPM");
                tv32.setText(sepm);

                tv41.setText("TOC");
                tv42.setText(toc);

                tv51.setText("HCI");
                tv52.setText(hci);
             //   Toast.makeText(getApplicationContext(),os+dbms+sepm+toc+hci,Toast.LENGTH_SHORT).show();

            }


            //  UserData  user1=new UserData("email","3957","123");
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT ).show();
            }
        });




    }


}
