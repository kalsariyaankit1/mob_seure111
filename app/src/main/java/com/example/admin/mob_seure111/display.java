package com.example.admin.mob_seure111;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class display extends AppCompatActivity {
    ListView l1;
    List<String> cname;
    List<String> code;

    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        l1=(ListView)findViewById(R.id.listview1);
        databaseHelper= new DatabaseHelper(getApplicationContext());
        cname = new ArrayList<String>();
        code = new ArrayList<String>();
        databaseHelper.open();
        Cursor c = databaseHelper.select();
        if(c != null) {
            c.moveToFirst();
            for(int i=0;i<c.getCount();i++) {
                //name.add(c.getString(0));
                cname.add(c.getString(1) + " :  " + c.getString(2) );
                code.add(c.getString(2));
                c.moveToNext();
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,cname);
        l1.setAdapter(adapter);


    }
}
