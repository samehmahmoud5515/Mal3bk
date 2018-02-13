package com.logicdesigns.mohammed.mal3bklast.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by logicDesigns on 10/8/2017.
 */





        import android.content.Context;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;

/**
 * Created by logicDesigns on 10/7/2017.
 */

public class MatchHistory {


    String matchId,playgroundName,playgroundImage,from,to,teamId2,teamName2,teamId1,
            teamName1,points1,points2,city,address,matchDate
    ;

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public String getTeamId1() {
        return teamId1;
    }

    public void setTeamId1(String teamId1) {
        this.teamId1 = teamId1;
    }

    public String getPoints1() {
        return points1;
    }

    public void setPoints1(String points1) {
        this.points1 = points1;
    }

    public String getTeamName1() {
        return teamName1;
    }

    public void setTeamName1(String teamName1) {
        this.teamName1 = teamName1;
    }

    public String getPoints2() {
        return points2;
    }

    public void setPoints2(String points2) {
        this.points2 = points2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public  MatchHistory()
    {

    }
    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getPlaygroundName() {
        return playgroundName;
    }

    public void setPlaygroundName(String playgroundName) {
        this.playgroundName = playgroundName;
    }

    public String getPlaygroundImage() {
        return playgroundImage;
    }

    public void setPlaygroundImage(String playgroundImage) {
        this.playgroundImage = playgroundImage;
    }



    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }



    public String getTeamId2() {
        return teamId2;
    }

    public void setTeamId2(String teamId2) {
        this.teamId2 = teamId2;
    }

    public String getTeamName2() {
        return teamName2;
    }

    public void setTeamName2(String teamName2) {
        this.teamName2 = teamName2;
    }

    public ArrayList<MatchHistory> parseJson(String jsonString) throws JSONException {
        ArrayList<MatchHistory> matches = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray array = jsonObject.getJSONArray("matches");
        for (int i =0;i<array.length();i++)
        {
            JSONObject innerObj = array.getJSONObject(i);
            MatchHistory match = new MatchHistory();
            if (! innerObj.isNull("reservationId")&& innerObj.has("reservationId"))
            {
                match.setMatchId(innerObj.getString("reservationId"));
            }
            else  match.setMatchId("");
            if (! innerObj.isNull("playground")&& innerObj.has("playground"))
            {
                match.setPlaygroundName((innerObj.getString("playground")));
            }
            else  match.setPlaygroundName("");

            if (! innerObj.isNull("playgroundImage")&& innerObj.has("playgroundImage"))
            {
                match.setPlaygroundImage((innerObj.getString("playgroundImage")));
            }
            else  match.setPlaygroundImage("");



            if (! innerObj.isNull("start")&& innerObj.has("start"))
            {
                match.setFrom((innerObj.getString("start")));
            }
            else  match.setFrom("");

            if (! innerObj.isNull("end")&& innerObj.has("end"))
            {
                match.setTo((innerObj.getString("end")));
            }
            else  match.setTo("");



            if (! innerObj.isNull("teamId2")&& innerObj.has("teamId2"))
            {
                match.setTeamId2((innerObj.getString("teamId2")));
            }
            else  match.setTeamId2("");

            if (! innerObj.isNull("teamName2")&& innerObj.has("teamName2"))
            {
                match.setTeamName2((innerObj.getString("teamName2")));
            }
            else  match.setTeamName2("");

            if (! innerObj.isNull("teamId1")&& innerObj.has("teamId1"))
            {
                match.setTeamId1((innerObj.getString("teamId1")));
            }
            else  match.setTeamId1("");

            if (! innerObj.isNull("teamName1")&& innerObj.has("teamName1"))
            {
                match.setTeamName1((innerObj.getString("teamName1")));
            }
            else  match.setTeamName1("");

            if (! innerObj.isNull("points1")&& innerObj.has("points1"))
            {
                match.setPoints1((innerObj.getString("points1")));
            }
            else  match.setPoints1("");

            if (! innerObj.isNull("points2")&& innerObj.has("points2"))
            {
                match.setPoints2((innerObj.getString("points2")));
            }
            else  match.setPoints2("");

            if (! innerObj.isNull("city")&& innerObj.has("city"))
            {
                match.setCity((innerObj.getString("city")));
            }
            else  match.setCity("");

            if (! innerObj.isNull("address")&& innerObj.has("address"))
            {
                match.setAddress((innerObj.getString("address")));
            }
            else  match.setAddress("");

            //
            if (! innerObj.isNull("matchDate")&& innerObj.has("matchDate"))
            {
                match.setMatchDate((innerObj.getString("matchDate")));
            }
            else  match.setMatchDate("");


            matches.add(match);

        }

        return  matches;
    }
}
