package com.example.mikeb.calldetails;

import android.Manifest;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by Mikeb on 10/27/2017.
 */

public class SpeedDialFrag extends Fragment{
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private  ArrayList <String> names;
    private View currentView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.speed_dial_tab,container,false);
        requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
        String [] names = new String[]{"Jim","Joe","Who","sds","wewe"};
        Log.d("llook", "onCreate:wew ");
        currentView = view;
        return view;

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        getContact();
    }

    void getContact(){
        ContentResolver cr = getContext().getContentResolver();
        // Get the Cursor of all the contacts
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        names = new ArrayList<String>();
        // Move the cursor to first. Also check whether the cursor is empty or not.
        if (cursor.moveToFirst()) {
            // Iterate through the cursor
            do {
                // Get the contacts name
                String tempName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                names.add(tempName);

            } while (cursor.moveToNext());
        }

        ListView listView = currentView.findViewById(R.id.listOfContacts);
        listView.setDivider(null);
        listView.setDividerHeight(0);
        contactRowAdapter contactRowAdapter = new contactRowAdapter(getActivity(),names);
        listView.setAdapter(contactRowAdapter);
        // Close
    }


}
