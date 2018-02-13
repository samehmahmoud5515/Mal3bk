package com.logicdesigns.mohammed.mal3bklast.Models;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by logicDesigns on 7/3/2017.
 */

public class City {
    String CityID;
    String CityName;


    public City(String cityID, String cityName) {
        CityID = cityID;
        CityName = cityName;

    }
    public City() {

    }


    public String getCityID() {
        return CityID;
    }

    public void setCityID(String cityID) {
        CityID = cityID;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getCityIdbyName(ArrayList<City> list, String cityName)
    {
        for (int i=0;i<list.size();i++)
        {
            if (cityName.equals(list.get(i).getCityName()))
            {
                return list.get(i).getCityID();
            }
        }
        return "";
    }

    public  String getCityNameById(ArrayList<City> list, String cityID)
    {
        for (int i=0; i<list.size();i++)
        {
            if (cityID.equals(list.get(i).getCityID()))
            {
                return  list.get(i).getCityName();
            }
        }
        return "";
    }


}
