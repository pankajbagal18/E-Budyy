package com.example.shubham.myapplication;
import android.content.Context;
import android.icu.text.AlphabeticIndex;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

public class RecordAdapter extends ArrayAdapter<Record> {
    Context cntx;
    List<Record> record;
    int resource;

    public RecordAdapter(@NonNull Context context, int resource, @NonNull List<Record> record) {
        super(context, resource, record);
        this.resource = resource;
        this.record = record;
        this.cntx = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(cntx);
        View view = inflater.inflate(R.layout.adapterview, null);
        TextView tv1 = view.findViewById(R.id.tv1);
        TextView tv2 = view.findViewById(R.id.tv2);
        Record myrecord = record.get(position);
        tv1.setText(myrecord.getSubject());
        tv2.setText(myrecord.getAttendance());
        return view;
    }
}