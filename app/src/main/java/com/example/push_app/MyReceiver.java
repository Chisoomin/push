package com.example.push_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    public static final String MY_ACTION = "com.example.push_app.action.ACTION_MY_BROADCAST";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if(MY_ACTION.equals(intent.getAction())){
            Toast.makeText(context,"된다",Toast.LENGTH_LONG);
        }
        //throw new UnsupportedOperationException("Not yet implemented");
    }
}