package com.logicdesigns.mohammed.mal3bklast.JSON_PARSER;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.logicdesigns.mohammed.mal3bklast.Models.City;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by logicDesigns on 7/3/2017.
 */

public class Cities_Parser {
    ArrayList<City> cityArrayList =new ArrayList<City>();
    SharedPreferences sharedPref;
    String language;

   Context mContext;
    public Cities_Parser(Context context)
    {
        mContext = context;
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        language = sharedPref.getString("language", "ar");
    }

    public ArrayList<City> parse(String response) {
        JSONArray jsonArray = null;


        try {
            JSONObject json =new JSONObject(response);
            jsonArray = json.getJSONArray("cities");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject c = jsonArray.getJSONObject(i);
                City city;
                if (language.equals("ar"))
                {  city = new City(c.getString("id"), c.getString("city"));}
                else {
                     city = new City(c.getString("id"), c.getString("cityEn"));
                }
                cityArrayList.add(city);

            }
            return cityArrayList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }



    }

}
