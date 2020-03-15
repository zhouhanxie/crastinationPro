package com.example.crastinationpro;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class MainActivity extends AppCompatActivity {

    private SqliteDTO sqliteDTO;
    private ArrayList<RecyclerItem> eventItemList;
    private RecyclerView eventRecycler;
    private RecyclerView.Adapter eventAdapter;
    private RecyclerView.LayoutManager eventLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        toolbar.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.Linen));
        toolbar.setTitleTextColor(Color.BLACK);
        setSupportActionBar(toolbar);


        eventItemList = new ArrayList<>();
        //SQLiteDatabase DTO
        sqliteDTO = new SqliteDTO(this);

        FloatingActionButton fab = findViewById(R.id.fab_main);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddEventActivity.class);
                startActivity(intent);

            }
        });

        populateRecyclerList();
        eventRecycler = findViewById(R.id.event_recycler);
        eventRecycler.setHasFixedSize(true);
        //Log.i("populate Recycler: ", "creating event adapter");
        eventAdapter = new EventAdapter(eventItemList,this);
        //Log.i("populate Recycler: ", "event adapter created");
        //Log.i("populate Recycler: ", "start binding items to view");
        eventRecycler.setAdapter(eventAdapter);
        eventLayoutManager = new LinearLayoutManager(this);
        //Log.i("populate Recycler: ", "linear manager is set");
        eventRecycler.setLayoutManager(eventLayoutManager);
        //Log.i("populate Recycler: ", "recyclerView rendering done");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void populateRecyclerList()
    {
        //Log.i("populate Recycler: ", "starting to populate buffer");
        Cursor data = sqliteDTO.getData();
        LinkedList<Event> tmp = new LinkedList<Event>();
        while(data.moveToNext())
        {
            //Log.i("populate Recycler: ", data.getString(1));
            tmp.add(new Event(data.getString(1), data.getInt(0)));
            //eventItemList.add(new EventRecyclerItem(new Event(data.getString(1))));
        }
        Collections.sort(tmp, Collections.reverseOrder());
        for (Event e: tmp)
        {
            eventItemList.add(new EventRecyclerItem(e));
        }



        //Log.i("done, eventlist size: ", Integer.toString(eventItemList.size()));

    }

    public void refreshRecycler()
    {
        //Log.i("Main Activity ", "refresh recycler called");
        //Log.i("refresh recycler","eventItemList now has"+String.valueOf(eventItemList.size()));
        eventItemList.clear();
        //Log.i("refresh recycler","eventItemList now has"+String.valueOf(eventItemList.size()));
        eventAdapter.notifyDataSetChanged();
        populateRecyclerList();
        //Log.i("refresh recycler","eventItemList now has"+String.valueOf(eventItemList.size()));
        eventAdapter.notifyDataSetChanged();
    }

}
