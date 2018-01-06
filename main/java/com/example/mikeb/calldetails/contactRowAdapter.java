package com.example.mikeb.calldetails;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Mikeb on 11/2/2017.
 */


public class contactRowAdapter extends ArrayAdapter<String>  {
    private ArrayList<String>names;
    private Activity context;
    public contactRowAdapter(@NonNull Activity context,ArrayList<String>names ) {
        super(context,R.layout.contact_row_frag,names);

        this.names = names;
        this.context = context;
    }

    public View getView(int position, View view, ViewGroup Parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View rowView = view;
        if (rowView == null) {
            rowView = inflater.inflate(R.layout.contact_row_frag, Parent, false);
        }
        TextView textView = rowView.findViewById(R.id.nameText);
        TextView date = rowView.findViewById(R.id.dateInfo);
        date.setText("");
        ImageView callStatus = rowView.findViewById(R.id.callStatus);
        callStatus.setImageResource(0);

        textView.setText(names.get(position));

        return rowView;
    }
}
