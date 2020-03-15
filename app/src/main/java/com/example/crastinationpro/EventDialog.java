package com.example.crastinationpro;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialogFragment;

public class EventDialog extends AppCompatDialogFragment {

    private TextView dialogEventRemainingTimeText;
    private TextView dialogEventContentText;
    private String rmTimeString;
    private String contentString;
    private int eventId;
    private SqliteDTO sqliteDTO;
    private Context mContext;

    EventDialog(String rmTime, String content, int eid, Context rootContext)
    {
        rmTimeString = rmTime;
        contentString = content;
        eventId = eid;
        mContext = rootContext;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_show_event, null);
        sqliteDTO = new SqliteDTO(mContext);
        builder.setView(view)
                .setNegativeButton("remove", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        //Log.i("pos button drop id",String.valueOf(eventId));
                        sqliteDTO.dropData(eventId);
                        ((MainActivity)mContext).refreshRecycler();
                    }
                })
                .setNegativeButton("kk", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        sqliteDTO.updateDataContent(eventId, dialogEventContentText.getText().toString());
                        ((MainActivity)mContext).refreshRecycler();
                    }
                });

        dialogEventRemainingTimeText = view.findViewById(R.id.dialog_event_remaining_time);
        dialogEventContentText = view.findViewById(R.id.dialog_event_content);

        dialogEventRemainingTimeText.setText(rmTimeString);
        dialogEventContentText.setText(contentString);


        return builder.create();
    }

    public interface OnDialogCloseListener
    {
        public void onDialogClose();
    }


}
