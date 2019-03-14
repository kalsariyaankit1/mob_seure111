package com.example.admin.mob_seure111;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class TrackActivity extends AppCompatActivity {
    ListView listView;
    DatabaseHelper dbHelper;
    List<String> contact,code,date_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        listView = (ListView) findViewById(R.id.listview_track);

        contact = new ArrayList<String>();
        code = new ArrayList<String>();
        date_time = new ArrayList<String>();

        dbHelper = new DatabaseHelper(getApplicationContext());
        dbHelper.open();
        Cursor c =dbHelper.select_track();
        if(c != null) {
            c.moveToFirst();
            for(int i=0;i<c.getCount();i++) {
                //name.add(c.getString(0));
                contact.add(c.getString(2) + "    " + c.getString(1));
                code.add(c.getString(2));
                date_time.add(c.getString(3));
                c.moveToNext();
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1,contact);
        listView.setAdapter(adapter);
    }
}
