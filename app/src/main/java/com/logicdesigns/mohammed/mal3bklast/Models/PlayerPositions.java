package com.logicdesigns.mohammed.mal3bklast.Models;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by logicDesigns on 9/24/2017.
 */

public class PlayerPositions {

    String postionId,postionName;
    Context mContext;
    SharedPreferences sharedPref;
    String language;
    public  PlayerPositions()
    {

    }
    public  PlayerPositions(String posId ,String posName)
    {
       postionId = posId;
        postionName=posName;
    }

    public String getPostionName() {
        return postionName;
    }

    public void setPostionName(String postionName) {
        this.postionName = postionName;
    }

    public String getPostionId() {
        return postionId;
    }

    public void setPostionId(String postionId) {
        this.postionId = postionId;
    }
    public String getPostionIdbyName(ArrayList<PlayerPositions> list, String type)
    {
        for (int i=0;i<list.size();i++)
        {
            if (type.equals(list.get(i).getPostionName()))
            {
                return list.get(i).getPostionId();
            }
        }
        return "";
    }

    public String getPostionNamebyId(ArrayList<PlayerPositions> list, String id)
    {
        for (int i=0;i<list.size();i++)
        {
            if (id.equals(list.get(i).getPostionId()))
            {
                return list.get(i).getPostionName();
            }
        }
        return "";
    }
    public ArrayList<PlayerPositions> parsePlayerPostions(String jsonString,Context context) throws JSONException {
        mContext =context;
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        language = sharedPref.getString("language", "ar");
        ArrayList<PlayerPositions> playerPositionsArrayList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonArray = jsonObject.getJSONArray("Position");
        for (int i=0;i<jsonArray.length();i++)
        {
          JSONObject innerObj = jsonArray.getJSONObject(i);
            PlayerPositions pos;
            if (language.equals("ar")) {
                pos = new PlayerPositions(innerObj.getString("id"), innerObj.getString("name"));
            }
            else {
                pos = new PlayerPositions(innerObj.getString("id"), innerObj.getString("nameEn"));

            }
            playerPositionsArrayList.add(pos);
        }
        return  playerPositionsArrayList;
    }
}
