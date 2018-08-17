package com.example.shubham.myapplication;



import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;

public class Notes2 extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView lst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes2);
        lst = (ListView)findViewById(R.id.lst);
        Toast.makeText(this,"in 2nd ",Toast.LENGTH_LONG);
        DBHelper dbHelper = new DBHelper(this);
        Cursor res = dbHelper.getAllData();
        String[] items = new String[res.getCount()];
        int i=0;
        while(res.moveToNext()) {
            items[i] = res.getString(2);
            i++;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.displayfile,items);
        lst.setAdapter(adapter);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(),"you cliked this"+position,Toast.LENGTH_LONG).show();
                String data =(String)parent.getItemAtPosition(position);
                //File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/example.pdf");
                DBHelper db = new DBHelper(getApplicationContext());
                Cursor res = db.getData(Integer.toString(position));
                String abspath = res.getColumnName(1);
                Toast.makeText(getApplicationContext(),"you cliked this"+data+"\n"+abspath,Toast.LENGTH_LONG).show();
                File file = new File(data);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
