package com.example.mikeb.calldetails;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Mikeb on 11/4/2017.
 */

public class CallLogHistoryAdapter extends BaseExpandableListAdapter {
    private List<String> names;
    private List<String>dates;
    private List<String>types;
    Activity context;
    @Override
    public int getGroupCount() {
        return names.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return names.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String headerTitle = (String)getGroup(i);
        if(view == null)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.contact_row_frag,null);
        }
        TextView lblListHeader = view.findViewById(R.id.nameText);
        TextView dateText = view.findViewById(R.id.dateInfo);
        dateText.setText(dates.get(i));
        lblListHeader.setText(headerTitle);
        ImageView callStatus = view.findViewById(R.id.callStatus);
        if(types.get(i).equals("1")) callStatus.setImageResource(R.drawable.called_success);
        else if(types.get(i).equals("2"))callStatus.setImageResource(R.drawable.call_success);
        else callStatus.setImageResource(R.drawable.called_failed);

        return view;

    }

    @Override
    public View getChildView(final int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final String childText = (String)getChild(i,i1);
        if(view == null)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.call_history_row,null);
        }

        //TextView txtListChild = (TextView)view.findViewById(R.id.lblListItem);
        ImageButton getInfo = view.findViewById(R.id.moreInfoButton);
        getInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RatingInfo.class);
                intent.putExtra("number",names.get(i));
                context.startActivity(intent);
            }
        });
        ImageButton rate = view.findViewById(R.id.rate);
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RateNumber.class);
                intent.putExtra("number",names.get(i));
                context.startActivity(intent);
            }
        });
        //txtListChild.setText(childText);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    public CallLogHistoryAdapter(List<String> names,List<String> dates,List<String> types , Activity context) {
        this.names = names;
        this.dates = dates;
        this.types = types;
        this.context = context;
    }
}
