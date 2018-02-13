package com.logicdesigns.mohammed.mal3bklast.JSON_PARSER;

import com.logicdesigns.mohammed.mal3bklast.Models.City;
import com.logicdesigns.mohammed.mal3bklast.Models.PlayGroundType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by logicDesigns on 7/4/2017.
 */

public class PlaygroundTypeParser {

    ArrayList<PlayGroundType> playgroundsArrayList =new ArrayList<PlayGroundType>();



    public ArrayList<PlayGroundType> parse(String response) {
        JSONArray jsonArray = null;


        try {
            JSONObject json =new JSONObject(response);
            jsonArray = json.getJSONArray("playgroundTypes");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject c = jsonArray.getJSONObject(i);
                PlayGroundType playGroundType = new PlayGroundType(c.getString("id"), c.getString("type"),
                        c.getString("number"));
                playgroundsArrayList.add(playGroundType);

            }
            return playgroundsArrayList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }



    }

}
