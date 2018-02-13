package com.logicdesigns.mohammed.mal3bklast.JSON_PARSER;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.logicdesigns.mohammed.mal3bklast.Models.PlayGroundType;
import com.logicdesigns.mohammed.mal3bklast.Models.PlaygroundFacilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by logicDesigns on 7/10/2017.
 */

public class PlaygroundFacilities_Parser {

    ArrayList<PlaygroundFacilities> playgroundsArrayList =new ArrayList<PlaygroundFacilities>();

    Context mContext;
    SharedPreferences sharedPref;
    String language;
    public ArrayList<PlaygroundFacilities> parse(String response,Context context) {
        mContext = context;
        JSONArray jsonArray = null;

        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        language = sharedPref.getString("language", "ar");

        try {
            JSONObject json =new JSONObject(response);
            jsonArray = json.getJSONArray("proprties");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject c = jsonArray.getJSONObject(i);
                PlaygroundFacilities facilities;
                if (language .equals("ar")){
                 facilities = new PlaygroundFacilities(c.getString("groundId")
                        , c.getString("groundType")
                     );}
                else {
                    //groundTypeEn
                    facilities = new PlaygroundFacilities(c.getString("groundId")
                            , c.getString("groundTypeEn")
                    );
                }
                playgroundsArrayList.add(facilities);

            }
            return playgroundsArrayList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }



    }
}
