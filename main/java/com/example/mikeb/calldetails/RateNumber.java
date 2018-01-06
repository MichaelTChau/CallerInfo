package com.example.mikeb.calldetails;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mikeb.calldetails.Utils.ConnectToDB;
import com.example.mikeb.calldetails.Utils.ContactUtils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Mikeb on 11/5/2017.
 */

public class RateNumber extends Activity {
    private String result;
    private String phoneNumber = "55464532";
    private String comment = "This is a Scammer";
    private String [] ratingOptions = new String[]{"Safe","Neutral","Danger"};
    private TextView currentRating;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_popup);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        final EditText ratingText = findViewById(R.id.ratingText);
        final ImageButton neutralBtn = findViewById(R.id.neutral_btn);
        final ImageButton dangerBtn = findViewById(R.id.danger_btn);
        final ImageButton safeBtn = findViewById(R.id.safe_btn);
        currentRating = findViewById(R.id.rateText);
        phoneNumber = getIntent().getStringExtra("number");
        TextView numberText = findViewById(R.id.phone_num);
        numberText.setText(phoneNumber);
        currentRating.setTextColor(Color.rgb(251,176,66));

        int width =  dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout(width,(int) (height*0.8));
        getWindow().setGravity(Gravity.BOTTOM);
        final ImageButton submit = findViewById(R.id.submit_rating);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comment = ratingText.getText().toString();
                comment= comment.trim();
                if((comment==null)||(comment.equals(""))){
                    System.out.println("Please enter a comment");
                }
                else{
                    loading();
                    submit();
                }

            }
        });
        neutralBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentRating.setText(ratingOptions[1]);
                currentRating.setTextColor(Color.rgb(251,176,66));
            }
        });
        safeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentRating.setText(ratingOptions[0]);

                currentRating.setTextColor(Color.rgb(0,176,140));

            }
        });
        dangerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentRating.setText(ratingOptions[2]);
                currentRating.setTextColor(Color.rgb(191,61,39));
            }
        });
    }

    private void loading(){
        dialog=new ProgressDialog(this);
        dialog.setMessage("Loading");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();
    }

    @SuppressLint("StaticFieldLeak")
    private void submit(){
        new AsyncTask<Void,Void,String>(){

            @Override
            protected String doInBackground(Void... voids) {
                ConnectToDB connectToDB = new ConnectToDB();
                JSONObject jsonPhone = new JSONObject();
                try {
                    jsonPhone.put("phone",phoneNumber);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                String lookupResult = connectToDB.lookupNumber(jsonPhone.toString());
                Log.d("lookup",lookupResult);
                if(!lookupResult.contains("null")){

                    try{
                        JSONObject document = new JSONObject(lookupResult);
                        JSONArray ratingJson = document.getJSONArray("rating");
                        int [] ratingArray = toIntArray(ratingJson);
                        int position =0;
                        for(String s:ratingOptions){
                            if(currentRating.getText().equals(s))break;
                            position ++;
                        }
                        ratingArray[position]++;
                        ratingJson = new JSONArray(ratingArray);
                        document.remove("rating");
                        document.put("rating",ratingJson);
                        JSONArray commentJsonArray = document.getJSONArray("comment");
                        JSONObject commentJson = new JSONObject();
                        commentJson.put("note",comment);
                        commentJson.put("rating",ratingOptions[position]);
                        commentJsonArray.put(commentJson);
                        document.remove("comment");
                        document.put("comment",commentJsonArray);
                        connectToDB.insert(document.toString());
                    }catch (Exception e){
                    }
                }
                else{
                    JSONObject document = new JSONObject();
                    JSONObject ratingJson = new JSONObject();

                    int []ratingArray = new int[]{0,0,0};
                    int position =0;
                    for(String s:ratingOptions){
                        if(currentRating.getText().equals(s))break;
                        position ++;
                    }
                    ratingArray[position]++;
                    JSONArray ratingJsonArray;
                    try{
                        ratingJsonArray = new JSONArray(ratingArray);
                        ratingJson.put("rating",ratingJsonArray);
                        JSONArray commentJsonArray = new JSONArray();
                        JSONObject commentJson = new JSONObject();
                        commentJson.put("note",comment);
                        commentJson.put("rating",currentRating.getText());
                        commentJsonArray.put(commentJson);
                        String [] commentArray = new String[]{comment};
                        JSONArray commentJSon = new JSONArray(commentArray);
                        document.put("phone",phoneNumber);
                        document.put("rating",ratingJsonArray);
                        document.put("comment",commentJsonArray);

                        Log.d("query",document.toString());
                        connectToDB.insert(document.toString());



                    }catch (Exception e){

                    }




                }
                return "done";
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.d("This is the result",s);
                dialog.hide();
                finish();
            }
        }.execute();
    }

    public static String[] toStringArray(JSONArray array) {
        if(array==null)
            return null;

        String[] arr=new String[array.length()];
        for(int i=0; i<arr.length; i++) {
            arr[i]=array.optString(i);
        }
        return arr;
    }

    public static int[] toIntArray(JSONArray array) {
        if(array==null)
            return null;

        int[] arr=new int[array.length()];
        for(int i=0; i<arr.length; i++) {
            arr[i]=array.optInt(i);
        }
        return arr;
    }
}
