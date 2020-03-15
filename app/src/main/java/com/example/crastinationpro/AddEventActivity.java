package com.example.crastinationpro;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Queue;

public class AddEventActivity extends AppCompatActivity {


    private EditText dueDateText;
    private EditText dueTimeText;
    private EditText priorityText;
    private EditText estimatedBurstTimeText;
    private EditText contentText;
    private SqliteDTO sqliteDTO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        //SQLiteDatabase DTO
        sqliteDTO = new SqliteDTO(this);

        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>"));

        this.dueDateText = findViewById(R.id.event_due_date);
        this.dueDateText.setHint("ddl date");
        this.dueDateText.setFocusable(false);
        this.dueDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popCalendarPicker();
            }
        });

        this.dueTimeText = findViewById(R.id.event_due_time);
        this.dueTimeText.setHint("ddl time");
        this.dueTimeText.setFocusable(false);
        this.dueTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popTimePicker();
            }
        });

        this.priorityText = findViewById(R.id.event_priority);
        this.priorityText.setHint("priority");
        this.priorityText.setFocusable(false);
        this.priorityText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popPriorityPicker();
            }
        });

        this.estimatedBurstTimeText = findViewById(R.id.event_burst_time);
        this.estimatedBurstTimeText.setHint("type your estimated burst time(hr)");

        contentText = findViewById(R.id.event_content);

        Button doneButton = findViewById(R.id.btn_add_event);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dueDate = dueDateText.getText().toString();
                String dueTime = dueTimeText.getText().toString();
                String priority = priorityText.getText().toString();
                String content = contentText.getText().toString();
                String estimatedBurstTime = estimatedBurstTimeText.getText().toString();
                String logInfo = dueDate+ " | "+dueTime+" | "+priority+" | "+content+" | "+estimatedBurstTime;
                Log.i("add event got: ",logInfo);
                if (dueDate.matches("")||dueTime.matches("")
                        ||priority.matches("")||content.matches("")
                ||estimatedBurstTime.matches(""))
                {
                    Toast t = Toast.makeText(getApplicationContext(),"be sure to fill all fields :)", Toast.LENGTH_SHORT);
                    t.show();
                }
                else
                {
                    Event e = new Event(dueDate, dueTime, content, priority, estimatedBurstTime);
                    saveEvent(e.toStringData());
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    private void popCalendarPicker()
    {
        //thanks, https://stackoverflow.com/questions/17901946/timepicker-dialog-from-clicking-edittext !
        Calendar mcurrentDate = Calendar.getInstance();
        int mYear = mcurrentDate.get(Calendar.YEAR);
        int mMonth = mcurrentDate.get(Calendar.MONTH);
        int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog mDatePicker;
        mDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                // TODO Auto-generated method stub
                /*      Your code   to get date and time    */
                selectedmonth ++; //indexed by 0... why = =
                dueDateText.setText("" + selectedmonth + "/" + selectedday + "/" + selectedyear);
            }
        }, mYear, mMonth, mDay);
        mDatePicker.setTitle("Select Date");
        mDatePicker.show();
    }

    private void popTimePicker()
    {
        //https://stackoverflow.com/questions/17901946/timepicker-dialog-from-clicking-edittext !
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,0);
        c.set(Calendar.MINUTE, 0);
        c.add(Calendar.MINUTE, -1);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                dueTimeText.setText( selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    private void popPriorityPicker()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Add the buttons
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        ArrayList<String> prioritiesArray = new ArrayList<String>();
        prioritiesArray.add("ASAP");
        prioritiesArray.add("ensure deadline");
        prioritiesArray.add("no rush");
        // Set other dialog properties
        builder.setTitle("pick a priority");
        builder.setItems(R.array.event_priorities, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            // The 'which' argument contains the index position
            // of the selected item
            priorityText.setText(getResources().getStringArray(R.array.event_priorities)[which]);
        }
    });

        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void saveEvent(String stringEvent)
    {
        boolean sendSucceed = this.sqliteDTO.addData(stringEvent);
        if(sendSucceed)
        {
            Toast t = Toast.makeText(this, "added entry to db", Toast.LENGTH_SHORT);
        }
        else
        {
            Toast t = Toast.makeText(this, "failed to add entry", Toast.LENGTH_SHORT);
        }
    }


}
