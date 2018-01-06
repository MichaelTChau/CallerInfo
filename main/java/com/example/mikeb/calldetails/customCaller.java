package com.example.mikeb.calldetails;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.Toast;

/**
 * Created by Mikeb on 10/26/2017.
 */

public class customCaller extends FragmentActivity {
    private  SectionPageAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("llook", "onCreate: ");
        setContentView(R.layout.custom_caller_layout);



        adapter = new SectionPageAdapter(getSupportFragmentManager());
        ViewPager pager = findViewById(R.id.pager);

        TabLayout tabLayout = findViewById(R.id.call_tab_layout);
        setupViewPager(pager);
        tabLayout.setupWithViewPager(pager);
        tabLayout.setTabTextColors(
                ContextCompat.getColor(this, R.color.grey),
                ContextCompat.getColor(this, R.color.white)
        );
        final ImageButton search = findViewById(R.id.searchBtn);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });

        ImageButton dial = findViewById(R.id.dialBtn);
        dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("dialing","dialing");
            }
        });

        if(ContextCompat.checkSelfPermission(customCaller.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(customCaller.this,Manifest.permission.READ_PHONE_STATE)){
                ActivityCompat.requestPermissions(customCaller.this,new String[]{Manifest.permission.READ_PHONE_STATE},1);
            }
            else{
                ActivityCompat.requestPermissions(customCaller.this,new String[]{Manifest.permission.READ_PHONE_STATE},1);
            }
        }



    }

    private void search(){
        Intent intent = new Intent(this, SearchContacts.class);
        this.startActivity(intent);
    }
    private  void  setupViewPager(ViewPager viewPager){

        adapter.addFragment(new SpeedDialFrag(),"Contacts");
        adapter.addFragment(new CallLogHistoryFrag(),"Log");
        viewPager.setAdapter(adapter);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length >0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(customCaller.this,Manifest.permission.READ_PHONE_STATE)== PackageManager.PERMISSION_GRANTED){
                Log.d("","Granted");
            }
            else{
                Log.d("","Failed");
            }
        }
    }
}
