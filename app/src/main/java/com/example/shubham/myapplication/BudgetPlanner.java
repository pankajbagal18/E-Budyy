package com.example.shubham.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BudgetPlanner extends AppCompatActivity {
    LineGraphSeries<DataPoint> series;
    SimpleDateFormat dateFormat=new SimpleDateFormat("dd MMM yyyy");
    TextView date;
    DatabaseReference databaseReference;
    Button add,spend;
    EditText addMoney;
    EditText spendMoney;
    UserData userData,user1;
    FirebaseAuth firebaseAuth;
    TextView totalAmt,available,expense;
    int grandTotal;
    static  int totalSpend,tt;
   // private float[] yData = {25.3f, 10.6f, 66.76f, 44.32f, 46.01f, 16.89f, 23.9f};
   // private String[] xData = {"Mitch", "Jessica" , "Mohammad" , "Kelsey", "Sam", "Robert", "Ashley"};
    private float[] yData={100,80,20};
    private String[] xData={"Grand Total","Available","Expense"};
    PieChart pieChart;

     int totalAmount,finalAmount,finalAmount1,finalRoll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget_planner);
        graphFunction();
        displayDate();


      databaseReference=FirebaseDatabase.getInstance().getReference();
      addMoney=(EditText)findViewById(R.id.addMoney);
      spendMoney=(EditText)findViewById(R.id.spendMoney);
      totalAmt=(TextView) findViewById(R.id.totalAmt);
      available=(TextView) findViewById(R.id.available);
      expense=(TextView)findViewById(R.id.expense);

        totalAmt.setText(Integer.toString(grandTotal));         //totalAmt is Textview

        available.setText(Integer.toString(totalAmount));

        expense.setText(Integer.toString(0));


       // totalAmt.setText("-");
      /*  totalAmt.setText(Integer.toString(grandTotal));
        available.setText(Integer.toString(totalAmount));
        expense.setText(Integer.toString(totalSpend));*/

      firebaseAuth=FirebaseAuth.getInstance();
      FirebaseUser user=firebaseAuth.getCurrentUser();
        Toast.makeText(this,"Welcome"+user.getEmail(),Toast.LENGTH_SHORT).show();
      add=(Button)findViewById(R.id.add);
       spend=(Button)findViewById(R.id.spend);

      add.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              saveUserInformation1();
          }
      });
      spend.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              saveUserInformation2();
          }
      });

        databaseReference=FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String phone=dataSnapshot.child("phone").getValue().toString();
                String email=dataSnapshot.child("email").getValue().toString();
                String roll=dataSnapshot.child("roll").getValue().toString();
                String ttAmt=dataSnapshot.child("totalAmount").getValue().toString();
                String ttAmt1=dataSnapshot.child("grandTotal").getValue().toString();
                finalAmount = Integer.parseInt(ttAmt);
                finalAmount1=Integer.parseInt(ttAmt1);
                finalRoll=Integer.parseInt(roll);
                Toast.makeText(getApplicationContext(),phone+email+finalRoll+ttAmt,Toast.LENGTH_SHORT).show();

            }


            //  UserData  user1=new UserData("email","3957","123");
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT ).show();
            }
        });

      grandTotal=finalAmount1;
      totalAmount=finalAmount;

        totalAmt.setText(Integer.toString(grandTotal));         //totalAmt is Textview

        available.setText(Integer.toString(totalAmount));

    }




    void saveUserInformation1()
    {

        String name=addMoney.getText().toString().trim();
        int tAmount=Integer.parseInt(name);
     //   final String[] phoneNo = new String[1];
    //    final String[] rollNo = new String[1];



       // String phone="a";
      //  String phone="a";
        //String phone="a";

      //  int finalAmt=Integer.parseInt(amt);
      //  UserData userData=new UserData(name);
        String uid;
        firebaseAuth=FirebaseAuth.getInstance();
        final FirebaseUser user=firebaseAuth.getCurrentUser();
        uid=user.getUid();
        Toast.makeText(this,"Information Saved.."+user.getUid(),Toast.LENGTH_SHORT).show();

       /* FirebaseUser user=firebaseAuth.getCurrentUser();

        databaseReference.child(user.getUid()).setValue(userData);

        Toast.makeText(this,"Information Saved.."+user.getUid(),Toast.LENGTH_SHORT).show();*/
      //  databaseReference=FirebaseDatabase.getInstance().getReference();
       databaseReference=FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
      databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               String phone=dataSnapshot.child("phone").getValue().toString();
               String email=dataSnapshot.child("email").getValue().toString();
              String roll=dataSnapshot.child("roll").getValue().toString();
              String ttAmt=dataSnapshot.child("totalAmount").getValue().toString();
               String ttAmt1=dataSnapshot.child("grandTotal").getValue().toString();
               finalAmount = Integer.parseInt(ttAmt);
               finalAmount1=Integer.parseInt(ttAmt1);
               finalRoll=Integer.parseInt(roll);
              Toast.makeText(getApplicationContext(),phone+email+roll+ttAmt,Toast.LENGTH_SHORT).show();

             //  rollNo[0] =roll;
              // phoneNo[0] =phone;
           }


       //  UserData  user1=new UserData("email","3957","123");
           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {
               Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT ).show();
           }
       });
      totalAmount=finalAmount;
        if(totalAmount==0)
        {
            totalAmount=tAmount;
            grandTotal=totalAmount;

        }
        else
        {
            totalAmount=totalAmount+tAmount;
            grandTotal=finalAmount1+tAmount;
        }
      //  String str=totalAmount.toString();
        totalAmt.setText(Integer.toString(grandTotal));         //totalAmt is Textview

        available.setText(Integer.toString(totalAmount));
        UserData  userData=new UserData(user.getEmail(),"3957",finalRoll,totalAmount,grandTotal);
        FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
             //   progressBar.setVisibility(View.GONE);
                if(task.isSuccessful())
                {
                    Toast.makeText(BudgetPlanner.this, "Modification Successful." + task.getException(),
                            Toast.LENGTH_SHORT).show();

                }
                else
                {

                    Toast.makeText(BudgetPlanner.this, "Modification Unsuccessful." + task.getException(),
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
        yData[0]=grandTotal;
        yData[1]=totalAmount;
        yData[2]=totalSpend;
        graphFunction();

    }
    void saveUserInformation2()
    {

        String name=spendMoney.getText().toString().trim();
        int tAmount=Integer.parseInt(name);
       // final String[] phoneNo = new String[1];
       // final String[] rollNo = new String[1];



        // String phone="a";
        //  String phone="a";
        //String phone="a";

        //  int finalAmt=Integer.parseInt(amt);
        //  UserData userData=new UserData(name);
        String uid;
        firebaseAuth=FirebaseAuth.getInstance();
        final FirebaseUser user=firebaseAuth.getCurrentUser();
        uid=user.getUid();
        Toast.makeText(this,"Information Saved.."+user.getUid(),Toast.LENGTH_SHORT).show();
       /* FirebaseUser user=firebaseAuth.getCurrentUser();

        databaseReference.child(user.getUid()).setValue(userData);

        Toast.makeText(this,"Information Saved.."+user.getUid(),Toast.LENGTH_SHORT).show();*/
        //  databaseReference=FirebaseDatabase.getInstance().getReference();
        databaseReference=FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String phone=dataSnapshot.child("phone").getValue().toString();
                String email=dataSnapshot.child("email").getValue().toString();
                String roll=dataSnapshot.child("roll").getValue().toString();
                String ttAmt=dataSnapshot.child("totalAmount").getValue().toString();
                finalAmount = Integer.parseInt(ttAmt);
                finalRoll=Integer.parseInt(roll);
                Toast.makeText(getApplicationContext(),phone+email+roll,Toast.LENGTH_SHORT).show();
            //   rollNo[0] =roll;
            //   phoneNo[0] =phone;

            }
            //  UserData  user1=new UserData("email","3957","123");
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),databaseError.getMessage(),Toast.LENGTH_SHORT ).show();
            }
        });
        totalAmount=finalAmount;
        if(totalAmount==0 && totalAmount<0)
        {
            totalAmount=0;

            Toast.makeText(getApplicationContext(),"You do not have enough Money",Toast.LENGTH_SHORT).show();

        }
        else
        {   int t=totalAmount;
            totalAmount=totalAmount-tAmount;

         //   totalSpend=tAmount;
            if(totalAmount<0)
            {
                totalAmount=t;
                Toast.makeText(getApplicationContext(),"You do not have enough Money",Toast.LENGTH_SHORT).show();

            }
            else
            {
                expense.setText(Integer.toString(tAmount));
                tt=tAmount;
            }
        }
        available.setText(Integer.toString(totalAmount));
      //  expense.setText(Integer.toString(0));
        UserData  userData=new UserData(user.getEmail(),"12345",finalRoll,totalAmount,grandTotal);
        FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //   progressBar.setVisibility(View.GONE);
                if(task.isSuccessful())
                {
                    Toast.makeText(BudgetPlanner.this, "Modification Successful." + task.getException(),
                            Toast.LENGTH_SHORT).show();

                }
                else
                {

                    Toast.makeText(BudgetPlanner.this, "Modification Unsuccessful." + task.getException(),
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        yData[0]=grandTotal;
        yData[1]=totalAmount;
        yData[2]=tt;
        graphFunction();
    }

    void graphFunction()
    {
       //
        pieChart = (PieChart) findViewById(R.id.idPieChart);

     //   pieChart.setDescription("");
        pieChart.setRotationEnabled(true);
        //pieChart.setUsePercentValues(true);
        //pieChart.setHoleColor(Color.BLUE);
        //pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Budget");
        pieChart.setCenterTextSize(10);



        addDataSet();

       /* pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                int pos1 = e.toString().indexOf("(sum): ");
                String sales = e.toString().substring(pos1 + 7);

                for(int i = 0; i < yData.length; i++){
                    if(yData[i] == Float.parseFloat(sales)){
                        pos1 = i;
                        break;
                    }
                }
                String employee = xData[pos1 + 1];
                Toast.makeText(BudgetPlanner.this, "Employee " + employee + "\n" + "Sales: $" + sales + "K", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });*/

    }


    private void addDataSet() {
       // Log.d(TAG, "addDataSet started");
        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for(int i = 0; i < yData.length; i++){
            yEntrys.add(new PieEntry(yData[i] , i));
        }

        for(int i = 1; i < xData.length; i++){
            xEntrys.add(xData[i]);
        }

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Money(RS)");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GRAY);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
        colors.add(Color.CYAN);
        colors.add(Color.YELLOW);
        colors.add(Color.MAGENTA);

        pieDataSet.setColors(colors);

        //add legend to chart
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
    void displayDate()
    {
        date=(TextView)findViewById(R.id.date);
        date.setText(dateFormat.format(new Date()));

    }

}
