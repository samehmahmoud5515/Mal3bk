package com.logicdesigns.mohammed.mal3bklast.Models;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by logicDesigns on 7/13/2017.
 */

public class UpcomingMatches {

    String id,bookdate,start,playgroundId,end,team1Id,team2Id,type,team1_name,team2_name;
    Context mContext;

    public UpcomingMatches(){}
    public UpcomingMatches(Context c){this.mContext=c;}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookdate() {
        return bookdate;
    }

    public void setBookdate(String bookdate) {
        this.bookdate = bookdate;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getPlaygroundId() {
        return playgroundId;
    }

    public void setPlaygroundId(String playgroundId) {
        this.playgroundId = playgroundId;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getTeam1Id() {
        return team1Id;
    }

    public void setTeam1Id(String team1Id) {
        this.team1Id = team1Id;
    }

    public String getTeam2Id() {
        return team2Id;
    }

    public void setTeam2Id(String team2Id) {
        this.team2Id = team2Id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getTeam2_name() {
        return team2_name;
    }

    public void setTeam2_name(String team2_name) {
        this.team2_name = team2_name;
    }

    public String getTeam1_name() {
        return team1_name;
    }

    public void setTeam1_name(String team1_name) {
        this.team1_name = team1_name;
    }

   public ArrayList<UpcomingMatches> ParseUpcomingMatches (String json) throws JSONException {
       JSONObject jsonObject = new JSONObject(json);
       ArrayList<UpcomingMatches> matchesList = new ArrayList<>();
       JSONArray jsonArray = jsonObject.getJSONArray("upcomingMatches");
       for ( int i =0 ;i <jsonArray.length();i++)
       {
           JSONObject innerObj = jsonArray.getJSONObject(i);
           UpcomingMatches match = new UpcomingMatches();
           if (! innerObj.isNull("id")&& innerObj.has("id"))
               match.setId(innerObj.getString("id"));

           else setId(innerObj.getString("id"));
           if (! innerObj.isNull("bookdate")&& innerObj.has("bookdate"))
           match.setBookdate(innerObj.getString("bookdate"));
           else  match.setBookdate("");
           if (! innerObj.isNull("start")&& innerObj.has("start"))
           match.setStart(innerObj.getString("start"));
           else   match.setStart("");
           if (! innerObj.isNull("playgroundId")&& innerObj.has("playgroundId"))
           match.setPlaygroundId(innerObj.getString("playgroundId"));
           else  match.setPlaygroundId("");
           if (! innerObj.isNull("end")&& innerObj.has("end"))
           match.setEnd(innerObj.getString("end"));
           else match.setEnd("");
           if (! innerObj.isNull("team1Id")&& innerObj.has("team1Id"))
           match.setTeam1Id(innerObj.getString("team1Id"));
           else match.setTeam1Id("");
           if (! innerObj.isNull("team2Id")&& innerObj.has("team2Id"))
           match.setTeam2Id(innerObj.getString("team2Id"));
           else match.setTeam2Id("");
           if (! innerObj.isNull("team1")&& innerObj.has("team1"))
           match.setTeam1_name(innerObj.getString("team1"));
           else match.setTeam1_name("");
           if (! innerObj.isNull("team2")&& innerObj.has("team2"))
           match.setTeam2_name(innerObj.getString("team2"));
           else match.setTeam2_name("");
           if (! innerObj.isNull("type")&& innerObj.has("type"))
           match.setType(innerObj.getString("type"));
           else   match.setType("");

           matchesList.add(match);
       }
       return matchesList;
   }


}
