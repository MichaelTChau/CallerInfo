package com.example.mikeb.calldetails;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Mikeb on 10/25/2017.
 */

public class InterceptCall extends BroadcastReceiver{
    private String incomingNumber = "";
    @Override
    public void onReceive(Context context, Intent intent) {
        String phoneState = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        try{
            if (phoneState.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                Toast.makeText(context,"Pick up",Toast.LENGTH_SHORT).show();
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    String state = extras.getString(TelephonyManager.EXTRA_STATE);
                    incomingNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
                    incomingNumber = incomingNumber.replace("+","");
                    Activity act = (Activity)context;
                    ((Userdata) act.getApplication()).setSomeVariable(incomingNumber);
                    Intent newIntent = new Intent(context, RatingInfo.class);
                    newIntent.putExtra("phone",incomingNumber);
                    context.startActivity(newIntent);
                }

            }
            if (phoneState.equals(TelephonyManager.EXTRA_STATE_IDLE)) {

                Activity act = (Activity)context;
                incomingNumber= ((Userdata) act.getApplication()).getSomeVariable();

                Log.d("number is:",incomingNumber);
                incomingNumber = incomingNumber.replace("+","");
                Intent newIntent = new Intent(context, RateNumber.class);
                newIntent.putExtra("phone",incomingNumber);
                context.startActivity(newIntent);

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }






}

