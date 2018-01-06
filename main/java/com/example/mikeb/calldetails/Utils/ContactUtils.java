package com.example.mikeb.calldetails.Utils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mikeb on 11/5/2017.
 */

public class ContactUtils {
    public static final String base_url = "https://api.mlab.com/api/1/databases/phone_number_profiles/collections/profiles?apiKey=oQ8EeSsOWTSV9AAmp6aUCQCwnaTs08Aq";
    public static JSONObject getObject(String tagName, JSONObject jsonObject) throws JSONException
    {
        JSONObject jObj = jsonObject.getJSONObject(tagName);
        return jObj;
    }
    public static String getString(String tagName,JSONObject jsonObject) throws  JSONException
    {
        return jsonObject.getString(tagName);
    }

    public static long getLong (String tagName , JSONObject jsonObject)throws  JSONException
    {
        return  jsonObject.getLong (tagName);
    }
    public static int getInt (String tagName , JSONObject jsonObject)throws  JSONException
    {
        return  jsonObject.getInt(tagName);
    }
}
