package com.example.shubham.myapplication;

public class Record {
    public String subject,attendance;

    public Record(String subject,String attendance) {

        this.subject=subject;
        this.attendance=attendance;
    }
    public Record() {


    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }
}
