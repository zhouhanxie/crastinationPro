package com.example.crastinationpro;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    private ArrayList<RecyclerItem> mEventList;
    private Context mcontext;
    public static class EventViewHolder extends RecyclerView.ViewHolder
    {
        public TextView meventTitle;
        public TextView meventRemainingTime;
        public Button meventStateButton;
        public CardView meventRecyclerCard;

        public EventViewHolder(View itemView)
        {
            super(itemView);
            meventTitle = itemView.findViewById(R.id.event_title);
            meventRemainingTime = itemView.findViewById(R.id.event_remaining_time);
            meventStateButton = itemView.findViewById(R.id.event_state_button);
            meventRecyclerCard = itemView.findViewById(R.id.event_recycler_card);
        }
    }

    public EventAdapter(ArrayList<RecyclerItem> eventList, Context ct)
    {
        Log.i("Event adapter", "just spawned");
        mEventList = eventList;
        mcontext = ct;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Log.i("Event adapter", "on craete view holder");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_recycler_item, parent, false);
        EventViewHolder eventViewHolder = new EventViewHolder(v);
        return eventViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        final EventRecyclerItem curItem = (EventRecyclerItem)(mEventList.get(position));
        //Log.i("Event adapter setting:", curItem.getTitle());

        holder.meventTitle.setText(curItem.getTitle());
        holder.meventRemainingTime.setText(curItem.getRemainingTime());
        int urgency = curItem.getUrgency();
        if (urgency == -1)
        {
            holder.meventStateButton.setBackgroundColor(Color.GRAY);
        }
        else
        {
            holder.meventStateButton.setBackgroundColor(mcontext.getResources().getColor(R.color.DarkRain));
        }

        holder.meventRecyclerCard.setClickable(true);
        holder.meventRecyclerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEventDetails(curItem.getRemainingTime(), curItem.getContent(),curItem.getEventSqlId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEventList.size();
    }

    private void checkNull(String s, String varName)
    {
        if (s.isEmpty()||s==null)
        {
            String info = varName + " is null!";
            //Log.i("Event Adapter: ",info);
        }
        else
        {
            String info = varName + " is not null!: " + s;
            //Log.i("Event Adapter: ",info);
        }
    }

    private void showEventDetails(String eventRemainingTime, String eventContent, int eid)
    {
        //Log.i("Recycleritem clicked","//TODO: show Event Details");
        EventDialog eventDialog = new EventDialog(eventRemainingTime,eventContent, eid,mcontext);
        FragmentManager mfragmentManager = ((FragmentActivity)mcontext).getSupportFragmentManager();
        eventDialog.show(mfragmentManager, "event details");
    }

}
