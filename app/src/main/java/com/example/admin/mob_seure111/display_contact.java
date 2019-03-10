package com.example.admin.mob_seure111;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class display_contact extends AppCompatActivity {
    ListView listView;
    DatabaseHelper dbHelper;
    List<String> name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);
        listView = (ListView) findViewById(R.id.listview2);

        name = new ArrayList<String>();
        dbHelper = new DatabaseHelper(getApplicationContext());
        dbHelper.open();
        Cursor c =dbHelper.select_contact();
        if(c != null) {
            c.moveToFirst();
            for (int i = 0; i < c.getCount(); i++) {
                name.add(c.getString(0));
                name.add(c.getString(1));
                name.add(c.getString(2));
                name.add(c.getString(3));
                name.add(c.getString(4));
                name.add(c.getString(5));
                c.moveToNext();
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1,name);
        listView.setAdapter(adapter);

    }
}

