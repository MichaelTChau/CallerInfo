package com.example.mikeb.calldetails;


import android.Manifest;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Mikeb on 11/4/2017.
 */

public class CallLogHistoryFrag extends Fragment {
    private static final int PERMISSIONS_REQUEST_READ_LOG = 100;
    private View currentView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.call_history_tab,container,false);
        currentView = view;
        Log.d("what","started");
        requestPermissions(new String[]{Manifest.permission.READ_CALL_LOG}, PERMISSIONS_REQUEST_READ_LOG);
        return view;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        getNumbers();
    }

    void getNumbers(){
        Cursor managedCursor = getActivity().managedQuery(CallLog.Calls.CONTENT_URI, null,
                null, null, null);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);


        List <String> listOfPhone = new ArrayList<String>();
        List <String> listOfDate = new ArrayList<String>();
        List <String> listOfType = new ArrayList<String>();
        // Move the cursor to first. Also check whether the cursor is empty or not.
        if (managedCursor.moveToFirst()) {
            // Iterate through the cursor
            do {
                // Get the contacts name
                String phNumber = managedCursor.getString(number);
                String location =  managedCursor.getString(managedCursor.getColumnIndex(CallLog.Calls.GEOCODED_LOCATION));
                String type = ""+managedCursor.getString(managedCursor.getColumnIndex(CallLog.Calls.TYPE));
                String date  =   managedCursor.getString(managedCursor.getColumnIndex(android.provider.CallLog.Calls.DATE));
                long seconds=Long.parseLong(date);
                SimpleDateFormat formatter = new SimpleDateFormat("MMM dd,yyyy HH:mm");
                String dateString = formatter.format(new Date(seconds));
                if(!(phNumber.matches(".*\\d+.*"))){
                    phNumber ="Unknown";
                }
                Log.d("date",dateString);
                Log.d("type",type);
                listOfType.add(type);
                listOfPhone.add(phNumber);
                listOfDate.add(dateString);
            } while (managedCursor.moveToNext());
        }
        ExpandableListView listView = currentView.findViewById(R.id.CallHistoryList);
        listView.setDivider(null);
        listView.setDividerHeight(0);
        CallLogHistoryAdapter listAdapter = new CallLogHistoryAdapter(listOfPhone,listOfDate,listOfType,getActivity());
        listView.setAdapter(listAdapter);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        listView.setIndicatorBounds(width-GetDipsFromPixel(35), width-GetDipsFromPixel(5));

        // Close

    }
    public int GetDipsFromPixel(float pixels)
    {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }
}
