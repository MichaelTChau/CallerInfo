package com.example.mikeb.calldetails;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mikeb.calldetails.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mikeb on 11/11/2017.
 */

public class CommentRowAdapter extends ArrayAdapter<String> {
    private List<String> comments = new ArrayList<String>();
    private List<String> rating = new ArrayList<String>();
    private Activity context;

    public CommentRowAdapter(List<String> comments,List<String>rating, Activity context1) {
        super(context1, R.layout.comment_row,comments);
        this.comments = comments;
        this.context = context1;
        this.rating = rating;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View rowView = convertView;
        if (rowView == null) {
            rowView = inflater.inflate(R.layout.comment_row, parent, false);
        }
        TextView comment = rowView.findViewById(R.id.commentText);
        comment.setText(comments.get(position));
        ImageView rate = rowView.findViewById(R.id.commentRating);
        if(rating.get(position).equals("Safe"))rate.setImageResource(R.drawable.safe_icon);
        else if(rating.get(position).equals("Neutral"))rate.setImageResource(R.drawable.neutral_icon);
        else rate.setImageResource(R.drawable.danger_icon);
        return rowView;
    }
}
