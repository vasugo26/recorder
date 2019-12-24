package com.hfad.recorder;
import android.content.BroadcastReceiver;
import java.util.Date;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;




public  class receiver_broadcast extends BroadcastReceiver {

    private static int lastState = TelephonyManager.CALL_STATE_IDLE;
     private static String savedNumber;
    private static Date callStartTime;
    private static boolean isIncoming;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {

            savedNumber = intent.getExtras().getString("android.intent.extra.PHONE_NUMBER");

        }
        else {
            int state =0;
            String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
            String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
            if (stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                state = TelephonyManager.CALL_STATE_IDLE;
             }
             else if (stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                state = TelephonyManager.CALL_STATE_OFFHOOK;
            } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                state = TelephonyManager.CALL_STATE_RINGING;
            }

           onCallStateChanged(context, state, number);
        }

    }


    public void onCallStateChanged(Context context, int state, String number) {
        if(lastState == state){
            //No change, debounce extras
            return;
        }

        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                isIncoming = true;
                callStartTime = new Date();
                savedNumber = number;
                Intent intent1 = new Intent(context, Pop_window.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
                Toast.makeText(context, "Incoming Call Ringing" , Toast.LENGTH_SHORT).show();
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:

                if(lastState != TelephonyManager.CALL_STATE_RINGING){
                    isIncoming = false;
                    callStartTime = new Date();
                    Intent intent = new Intent(context, Pop_window.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    Toast.makeText(context, "Outgoing Call" , Toast.LENGTH_SHORT).show();
                }
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                //Went to idle-  this is the end of a call.  What type depends on previous state(s)
                if(lastState == TelephonyManager.CALL_STATE_RINGING){
                    //Ring but no pickup-  a miss
                    Toast.makeText(context, "Ringing but no pickup :" + savedNumber + "\n Call time :" + callStartTime +"\n Date :" + new Date() , Toast.LENGTH_SHORT).show();
                }
                else if(isIncoming){
                    recording_page.onRecord(false);
                    Toast.makeText(context, "Incoming call ended with :" + savedNumber + "\n Call time: " + callStartTime +" \nDate: " + new Date()  , Toast.LENGTH_SHORT).show();
                    Intent WP1 = new Intent();
                    WP1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(WP1);
                }
                else{
                     recording_page.onRecord(false);
                    Toast.makeText(context, "Outgoing call ended with : " + savedNumber + "\n Call time: " + callStartTime +"\n Date: " + new Date() , Toast.LENGTH_SHORT).show();
                    Intent WP1 = new Intent();
                    WP1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(WP1);
                }
                break;
        }
        lastState = state;
    }
}


