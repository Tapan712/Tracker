package com.example.tracker;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.util.Objects;

public class SmsReceiveAlert extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "message received", Toast.LENGTH_SHORT).show();
        Bundle bundle = intent.getExtras();
        String format = bundle.getString("format");
        Object[] smsObjects = (Object[]) bundle.get("pdus");
        if(Objects.nonNull(smsObjects)){
            for (Object obj : smsObjects){
                SmsMessage message = SmsMessage.createFromPdu((byte[]) obj,format);
                String msg = message.getDisplayMessageBody();
                Toast.makeText(context, "message Text: "+msg, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
