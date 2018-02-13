package com.logicdesigns.mohammed.mal3bklast.Models;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.logicdesigns.mohammed.mal3bklast.adapters.ExpandableListAdapter;
import com.logicdesigns.mohammed.mal3bklast.common.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import BusinessClasses.FormationData;
import BusinessClasses.User;

/**
 * Created by logicDesigns on 7/20/2017.
 */

public class Team {
   String  CaptainId,id,name,dateOfCreation,stars,captainName;
    int NumberofPlayers;
    Context _Context;

    public String getCaptainName() {
        return captainName;
    }

    public void setCaptainName(String captainName) {
        this.captainName = captainName;
    }

    ArrayList <String> myTeamList;

    public int getNumberofPlayers() {
        return NumberofPlayers;
    }

    public void setNumberofPlayers(int numberofPlayers) {
        NumberofPlayers = numberofPlayers;
    }

    public Team (Context context)
    {
        _Context=context;
    }
    public Team ( )
    {

    }


    public String getCaptainId() {
        return CaptainId;
    }

    public void setCaptainId(String captainId) {
        CaptainId = captainId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public ArrayList<Team> ParseTeams(String JsonString) throws JSONException {
    //    getMyteamList();

        JSONObject jsonObject = new JSONObject(JsonString);
        JSONArray jsonArray = jsonObject.getJSONArray("team");
        ArrayList<Team> teamArrayList = new ArrayList<>();
        for (int i=0;i<jsonArray.length();i++)
        {
            JSONObject innerObj = jsonArray.getJSONObject(i);
            Team  team =new Team();
            if (!innerObj.isNull("CaptainId") && innerObj.has("CaptainId"))
              team.setCaptainId(innerObj.getString("CaptainId"));
            else team.setCaptainId("");
            if (!innerObj.isNull("CaptainName") && innerObj.has("CaptainName"))
              team.setCaptainName(innerObj.getString("CaptainName"));
            else  team.setCaptainName("");
            if (!innerObj.isNull("id") && innerObj.has("id"))
               team.setId(innerObj.getString("id"));
            else team.setId("");
            if (!innerObj.isNull("name") && innerObj.has("name"))
                team.setName(innerObj.getString("name"));
            else team.setName("");
            if (!innerObj.isNull("dateOfCreation") && innerObj.has("dateOfCreation"))
               team.setDateOfCreation(innerObj.getString("dateOfCreation"));
            else team.setDateOfCreation("");
            if (!innerObj.isNull("stars") && innerObj.has("stars"))
               team.setStars(innerObj.getString("stars"));
            JSONArray playersArray;
            if (!innerObj.isNull("players") && innerObj.has("players"))
            { playersArray = innerObj.getJSONArray("players");
            team.setNumberofPlayers(playersArray.length());}
            else team.setNumberofPlayers(0);

//           for (i=0;i<myTeamList.size();i++)
//                if (!myTeamList.get(i).equals(innerObj.getString("id")))
                  teamArrayList.add(team);
        }
        return  teamArrayList;
    }

    public void getMyteamList()
    {

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(_Context);
        final String json1 = mPrefs.getString("userObject", "");
        Gson gson =new Gson();
        User obj = gson.fromJson(json1, User.class);

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(_Context);
        String url = URLs.getTeamofAplayerAPI+"id="+obj.getId();


// Request a string response from the provided URL.
        myTeamList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            FormationData formationData = new FormationData();
                            if (Integer.parseInt(jsonObject.getString("success"))==1)
                            {ArrayList<String> myTeamList =new ArrayList<>() ;
                                myTeamList = formationData.getMyTeams(response);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}

