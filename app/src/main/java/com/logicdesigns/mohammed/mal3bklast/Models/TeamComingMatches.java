package com.logicdesigns.mohammed.mal3bklast.Models;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by logicDesigns on 10/7/2017.
 */

public class TeamComingMatches {


    String matchId,playgroundName,playgroundImage,bookdate,from,to,chooseTeamButton,teamId2,teamName2
         ,city,address;

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

    public  TeamComingMatches()
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

    public String getBookdate() {
        return bookdate;
    }

    public void setBookdate(String bookdate) {
        this.bookdate = bookdate;
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

    public String getChooseTeamButton() {
        return chooseTeamButton;
    }

    public void setChooseTeamButton(String chooseTeamButton) {
        this.chooseTeamButton = chooseTeamButton;
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

    public ArrayList<TeamComingMatches> parseJson(String jsonString) throws JSONException {
        ArrayList<TeamComingMatches> matches = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray array = jsonObject.getJSONArray("matches");
        for (int i =0;i<array.length();i++)
        {
            JSONObject innerObj = array.getJSONObject(i);
            TeamComingMatches match = new TeamComingMatches();
            if (! innerObj.isNull("matchId")&& innerObj.has("matchId"))
            {
                match.setMatchId(innerObj.getString("matchId"));
            }
            else  match.setMatchId("");
            if (! innerObj.isNull("playgroundName")&& innerObj.has("playgroundName"))
            {
                match.setPlaygroundName((innerObj.getString("playgroundName")));
            }
            else  match.setPlaygroundName("");

            if (! innerObj.isNull("playgroundImage")&& innerObj.has("playgroundImage"))
            {
                match.setPlaygroundImage((innerObj.getString("playgroundImage")));
            }
            else  match.setPlaygroundImage("");

            if (! innerObj.isNull("bookdate")&& innerObj.has("bookdate"))
            {
                match.setBookdate((innerObj.getString("bookdate")));
            }
            else  match.setBookdate("");

            if (! innerObj.isNull("from")&& innerObj.has("from"))
            {
                match.setFrom((innerObj.getString("from")));
            }
            else  match.setFrom("");

            if (! innerObj.isNull("to")&& innerObj.has("to"))
            {
                match.setTo((innerObj.getString("to")));
            }
            else  match.setTo("");

            if (! innerObj.isNull("chooseTeamButton")&& innerObj.has("chooseTeamButton"))
            {
                match.setChooseTeamButton((innerObj.getString("chooseTeamButton")));
            }
            else  match.setChooseTeamButton("");

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

            matches.add(match);

        }

        return  matches;
    }
}
