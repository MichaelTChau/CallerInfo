package com.example.mikeb.calldetails;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;

import com.example.mikeb.calldetails.R;

/**
 * Created by Mikeb on 11/12/2017.
 */

public class SearchContacts extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
    }
}
