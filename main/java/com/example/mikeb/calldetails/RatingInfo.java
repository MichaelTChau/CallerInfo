package com.example.mikeb.calldetails;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mikeb.calldetails.Utils.ConnectToDB;
import com.example.mikeb.calldetails.Utils.ContactUtils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mikeb on 11/10/2017.
 */

public class RatingInfo extends Activity {

    private String phoneNumber;
    private  ProgressDialog dialog;
    private String ratingInfo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog=new ProgressDialog(this);
        dialog.setMessage("Loading");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();
        setContentView(R.layout.rating_info);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width =  dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout(width,(int) (height*0.7));
        getWindow().setGravity(Gravity.BOTTOM);
        phoneNumber = getIntent().getStringExtra("number");
        TextView numberText = findViewById(R.id.infoNumber);
        numberText.setText(phoneNumber);
        getRating();
    }

    @SuppressLint("StaticFieldLeak")
    private void getRating(){
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                ContactUtils contactUtils = new ContactUtils();
                ConnectToDB connectToDB = new ConnectToDB();
                JSONObject jsonPhone = new JSONObject();
                try {
                    jsonPhone.put("phone",phoneNumber);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                String lookupResult = connectToDB.lookupNumber(jsonPhone.toString());
                ratingInfo = lookupResult;
                return lookupResult;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s.contains("null")){
                    DisplayMetrics dm = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(dm);
                    int width =  dm.widthPixels;
                    int height = dm.heightPixels;
                    getWindow().setLayout(width,(int) (height*0.55));
                    TextView status = findViewById(R.id.statusComment);
                    status.setText("No Ratings");
                    setupNoInfo();
                }
                else {
                    setupComments();
                    setupChart();
                }
            }
        }.execute();

    }

    private void setupNoInfo(){
        List<BarEntry> data = new ArrayList<BarEntry>();

        float [] values = new float[]{0.1f,0.1f,0.1f};


        //setup count
        TextView safeCount1 = findViewById(R.id.safeOne);
        TextView safeCount2 = findViewById(R.id.safeTwo);
        TextView neutralCount1 = findViewById(R.id.neutralOne);
        TextView neutralCount2 = findViewById(R.id.neutralTwo);
        TextView dangerCount1 = findViewById(R.id.dangerOne);
        TextView dangerCount2 = findViewById(R.id.dangerTwo);
        safeCount1.setText("N\\A");
        safeCount2.setText("");
        neutralCount1.setText("N\\A");
        neutralCount2.setText("");
        dangerCount1.setText("N\\A");
        dangerCount2.setText("");

        for(int x =0;x<values.length;x++){
            data.add(new BarEntry(x,values[x]));
        }
        BarDataSet dataSet = new BarDataSet(data,"Ratings");
        dataSet.setDrawValues(false);
        int [] color = new int[]{Color.rgb(0,191,140),Color.rgb(251,176,66),Color.rgb(191,61,39)};
        dataSet.setColors(color);
        BarData pieData = new BarData(dataSet);
        HorizontalBarChart pieChart = findViewById(R.id.barChart);
        pieChart.getAxisRight().setStartAtZero(true);
        pieChart.getAxisLeft().setStartAtZero(true);
        pieChart.getLegend().setEnabled(false);
        pieChart.getXAxis().setDrawLabels(false);
        pieChart.getAxisLeft().setDrawLabels(false);

        pieChart.getAxisRight().setDrawLabels(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawGridBackground(false);
        pieChart.getAxisLeft().setDrawGridLines(false);
        pieChart.getAxisRight().setDrawGridLines(false);
        pieChart.getXAxis().setDrawGridLines(false);
        pieChart.getXAxis().setEnabled(false);
        pieChart.getXAxis().setDrawLabels(false);
        pieChart.getAxisLeft().setDrawAxisLine(false);
        pieChart.getAxisRight().setDrawAxisLine(false);
        pieChart.invalidate();
        pieChart.setData(pieData);
        dialog.hide();
    }

    private void setupChart(){

        List<BarEntry> data = new ArrayList<BarEntry>();

        float [] values = new float[]{0.1f,0.1f,0.1f};
        try{
            JSONObject document = new JSONObject(ratingInfo);
            JSONArray ratingArray = document.getJSONArray("rating");
            for(int x = 0;x<ratingArray.length();x++){
                int temp =  ratingArray.getInt(x);
                if(temp ==0)continue;
                values[x]= temp;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        //setup count
        TextView safeCount1 = findViewById(R.id.safeOne);
        TextView safeCount2 = findViewById(R.id.safeTwo);
        TextView neutralCount1 = findViewById(R.id.neutralOne);
        TextView neutralCount2 = findViewById(R.id.neutralTwo);
        TextView dangerCount1 = findViewById(R.id.dangerOne);
        TextView dangerCount2 = findViewById(R.id.dangerTwo);
        safeCount1.setText(""+(int)(values[0]));
        safeCount2.setText(""+(int)(values[0]));
        neutralCount1.setText(""+(int)(values[1]));
        neutralCount2.setText(""+(int)(values[1]));
        dangerCount1.setText(""+(int)(values[2]));
        dangerCount2.setText(""+(int)(values[2]));

        for(int x =0;x<values.length;x++){
            data.add(new BarEntry(x,values[x]));
        }
        BarDataSet dataSet = new BarDataSet(data,"Ratings");
        dataSet.setDrawValues(false);
        int [] color = new int[]{Color.rgb(0,191,140),Color.rgb(251,176,66),Color.rgb(191,61,39)};
        dataSet.setColors(color);
        BarData pieData = new BarData(dataSet);
        HorizontalBarChart pieChart = findViewById(R.id.barChart);
        pieChart.getAxisRight().setStartAtZero(true);
        pieChart.getAxisLeft().setStartAtZero(true);
        pieChart.getLegend().setEnabled(false);
        pieChart.getXAxis().setDrawLabels(false);
        pieChart.getAxisLeft().setDrawLabels(false);

        pieChart.getAxisRight().setDrawLabels(false);
        pieChart.getDescription().setEnabled(false);
        pieChart.setDrawGridBackground(false);
        pieChart.getAxisLeft().setDrawGridLines(false);
        pieChart.getAxisRight().setDrawGridLines(false);
        pieChart.getXAxis().setDrawGridLines(false);
        pieChart.getXAxis().setEnabled(false);
        pieChart.getXAxis().setDrawLabels(false);
        pieChart.getAxisLeft().setDrawAxisLine(false);
        pieChart.getAxisRight().setDrawAxisLine(false);
        pieChart.invalidate();
        pieChart.setData(pieData);
        dialog.hide();
    }

    private void setupComments(){
        try{
            JSONObject jsonObject = new JSONObject(ratingInfo);
            JSONArray commentJson = jsonObject.getJSONArray("comment");
            List <String> commentText = new ArrayList<String>();
            List <String> commentRating = new ArrayList<String>();
            for(int x =0;x<commentJson.length();x++){
                commentText.add(x,commentJson.getJSONObject(x).getString("note"));
                commentRating.add(x,commentJson.getJSONObject(x).getString("rating"));
            }
            CommentRowAdapter adapter = new CommentRowAdapter(commentText,commentRating,this);
            ListView listView = findViewById(R.id.listOfComment);
            listView.setAdapter(adapter);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
