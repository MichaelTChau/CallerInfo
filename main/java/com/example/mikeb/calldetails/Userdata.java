package com.example.mikeb.calldetails;

import android.app.Application;

/**
 * Created by Mikeb on 11/15/2017.
 */

public class Userdata extends Application {
    private String phoneNumber;

    public String getSomeVariable() {
        return phoneNumber;
    }

    public void setSomeVariable(String someVariable) {
        this.phoneNumber = someVariable;
    }
}
