package com.example.crastinationpro;

import android.graphics.Color;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EventRecyclerItem extends RecyclerItem{
    private String title;
    private String remainingTime;
    private String dueTime;
    private String priority;
    private int urgency;
    private String content;
    private int eventSqlId; //the sqllite unique id passed in


    EventRecyclerItem(Event e) {
        //Log.i("Event recycler Item ", "spawned");
        //Log.i("recycler Item content", e.toStringData());
        title = e.getTitle();
        content = e.getContent();
        remainingTime = e.getDate() + " " + e.getTime();
        dueTime = e.getDate() + " " + e.getTime();
        priority = e.getPriority();
        eventSqlId = e.getId();

        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        Date mcurrentDatewOffset = c.getTime();
        //stupid java util use 0 based month index, like why?


        SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy HH:mm");

        //Log.i("event recycler item: ","getting urgency info");

        try {
            //Log.i("compare cur<>due", mcurrentDatewOffset.toString()+" and "+sdf.parse(dueTime).toString());
            Date dueDate = sdf.parse(dueTime);
            if (mcurrentDatewOffset.after(dueDate)) {
                Log.i("setting urgency info ","-1");
                urgency = -1;
            }
            //Log.i("setting urgency info ","default");
        }
        catch(ParseException pe)
        {
            Log.e("Event REcycler Item: ","error when parsing date");
            urgency = -2;
        }

    }

    public String getTitle()
    {
        if (title == null)
        {
            return "void";
        }
        return title;
    }
    public String getRemainingTime()
    {
        if (title == null)
        {
            return "void";
        }
        return remainingTime;
    }
    public String getPriority()
    {
        if (title == null)
        {
            return "void";
        }
        return priority;
    }
    public int getUrgencyByColor()
    {
        if (urgency == -1)
        {
            return Color.GRAY;
        }
        return Color.GREEN;
    }
    public int getUrgency()
    {
        return urgency;
    }
    public String getContent()
    {
        return content;
    }

    public int getEventSqlId(){return eventSqlId;}
}
