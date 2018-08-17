package com.example.shubham.myapplication;


import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Reminder extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener{
    String title,location,detail,comment,date,time;
    Calendar dateCalender,timeCalender;
    private Button saveButton;
  //  DatabaseHelper reminderDB;
    private Button cancelButton;
    private EditText etitle,elocation,edetails,ecomment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        cancelButton = (Button)findViewById(R.id.cancel);
      //  reminderDB = new DatabaseHelper(this);
        dateCalender = Calendar.getInstance();
        timeCalender = Calendar.getInstance();
        etitle = (EditText)findViewById(R.id.etEventTitle);
        elocation = (EditText)findViewById(R.id.etEventLocation);
        edetails = (EditText)findViewById(R.id.etEventDetails);
        ecomment = (EditText)findViewById(R.id.comment);
        TextView dateView = (TextView)findViewById(R.id.dateView);
        //When clicked on SELECT DATE text box Calender appears as fragment
        //after selecting date it will be shown in the same text box or
        //today's date will be selected
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.DialogFragment datePicker = new DataPickerClass();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });
        //When clicked on SELECT TIME text box, Clock appears as fragment
        //after selecting time it will be shown in the same text box or
        //current time will be selected
        final TextView timeView = (TextView)findViewById(R.id.timeView);
        timeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"date picker");
            }
        });

        saveButton = (Button)findViewById(R.id.saveButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReminderData rd = new ReminderData(getApplicationContext());
                Cursor res = rd.getAllData();
                String str = "Reminder";
                while(res.moveToNext()){
                    str = str+res.getString(1);
                }
                Toast.makeText(getApplicationContext(),str,Toast.LENGTH_LONG);
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count=0;
                title = etitle.getText().toString();
                if(title.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Title cannot be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                location = elocation.getText().toString();
                detail = edetails.getText().toString();
                comment = ecomment.getText().toString();
                if(date.isEmpty()||time.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Enter time and date", Toast.LENGTH_LONG).show();
                    return;
                }
                //send this data for storing in database

                String insertThis = title+location+detail+comment+date+time;
                ReminderData reminderData = new ReminderData(getApplicationContext());
                if(reminderData.insertData(insertThis))
                {
                    Toast.makeText(getApplicationContext(),"REMINDER_ADDED",Toast.LENGTH_LONG);
                }
                else {
                    Toast.makeText(getApplicationContext(),"REMINDER_ADDED_NOT_ADDED",Toast.LENGTH_LONG);

                }
                //go back to parent intent
                //set Alarm
                //We need a calendar object to get the specified time in millis
                //as the alarm manager method takes time in millis to setup the alarm
                Calendar calendar = Calendar.getInstance();
                calendar.set(dateCalender.get(Calendar.YEAR), dateCalender.get(Calendar.MONTH), dateCalender.get(Calendar.DAY_OF_MONTH),
                        timeCalender.get(Calendar.HOUR_OF_DAY), timeCalender.get(Calendar.MINUTE), 0);
                setAlarm(calendar.getTimeInMillis());
            }
        });
    }

    //onTimeSet method from TimePickerDialog.OnTimeSetListener interface to set time
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        int hour = hourOfDay % 12;
        if (hour == 0) hour = 12;
        String _AM_PM = (hourOfDay > 12) ? "PM" : "AM";
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
        c.set(Calendar.MINUTE,minute);
        timeCalender = c;
        String currentString = String.format(Locale.getDefault(), "%02d:%02d %s", hour, minute, _AM_PM);
        TextView tv = (TextView)findViewById(R.id.timeView);
        tv.setText(currentString);
        time = currentString;
    }

    //onDateSet method from DatePickerDialog.OnDateSetListener interface to set date
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        dateCalender = c;
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        TextView tv = (TextView)findViewById(R.id.dateView);
        tv.setText(currentDate);
        date = currentDate;
        Toast.makeText(getApplicationContext(),currentDate,Toast.LENGTH_LONG);
    }

    //store reminder is user defined function to store the data locally
    //Here the data is stored online on firebase


    public void setAlarm(long time)
    {
        //getting the alarm manager
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        //creating a new intent specifying the broadcast receiver
        Intent i = new Intent(this, MyAlarm.class);


        //creating a pending intent using the intent
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);

        //setting the repeating alarm that will be fired every day
        am.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, pi);
        Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show();
    }
}
