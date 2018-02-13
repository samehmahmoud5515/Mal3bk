package com.logicdesigns.mohammed.mal3bklast.Models;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by logicDesigns on 10/3/2017.
 */

public class PlayerComingMatches {
    /*
    "matchId": "8",
            "bookdate": "9-11-2017",
            "from": "13",
            "to": "15",
            "hasTwoMatchesInsameTime": 0,
            "HasOnthermatchAlreadyAtthattime": 0,
            "ConfirmedThisMatch": 1,
            "confirmButton": 0,
            "teamId1": "2",
            "teamName1": "team 2",
            "teamId2": "3",
            "teamName2": "team5",
            "playerintheTwoteams": 0,
            "isCaptain": 0,
            "borrowed": 0
     */

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

    String matchId,bookdate,from,to,hasTwoMatchesInsameTime,HasOnthermatchAlreadyAtthattime
            ,ConfirmedThisMatch,confirmButton,teamId1="",teamName1,teamId2,teamName2,
            playerintheTwoteams,borrowed,isCaptain,playgroundName,playgroundImage,city,address;
    Context mContext;

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

    public PlayerComingMatches()
    {

    }
    public PlayerComingMatches(Context context)
    {
       this.mContext = context;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
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

    public String getHasTwoMatchesInsameTime() {
        return hasTwoMatchesInsameTime;
    }

    public void setHasTwoMatchesInsameTime(String hasTwoMatchesInsameTime) {
        this.hasTwoMatchesInsameTime = hasTwoMatchesInsameTime;
    }

    public String getHasOnthermatchAlreadyAtthattime() {
        return HasOnthermatchAlreadyAtthattime;
    }

    public void setHasOnthermatchAlreadyAtthattime(String hasOnthermatchAlreadyAtthattime) {
        HasOnthermatchAlreadyAtthattime = hasOnthermatchAlreadyAtthattime;
    }

    public String getConfirmedThisMatch() {
        return ConfirmedThisMatch;
    }

    public void setConfirmedThisMatch(String confirmedThisMatch) {
        ConfirmedThisMatch = confirmedThisMatch;
    }

    public String getConfirmButton() {
        return confirmButton;
    }

    public void setConfirmButton(String confirmButton) {
        this.confirmButton = confirmButton;
    }

    public String getTeamId1() {
        return teamId1;
    }

    public void setTeamId1(String teamId1) {
        this.teamId1 = teamId1;
    }

    public String getTeamName1() {
        return teamName1;
    }

    public void setTeamName1(String teamName1) {
        this.teamName1 = teamName1;
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

    public String getPlayerintheTwoteams() {
        return playerintheTwoteams;
    }

    public void setPlayerintheTwoteams(String playerintheTwoteams) {
        this.playerintheTwoteams = playerintheTwoteams;
    }

    public String getBorrowed() {
        return borrowed;
    }

    public void setBorrowed(String borrowed) {
        this.borrowed = borrowed;
    }

    public String getIsCaptain() {
        return isCaptain;
    }

    public void setIsCaptain(String isCaptain) {
        this.isCaptain = isCaptain;
    }/*
    "": "8",
            "": "9-11-2017",
            "": "13",
            "": "15",
            "": 0,
            "": 0,
            "": 1,
            "": 0,
            "": "2",
            "": "team 2",
            "": "3",
            "": "team5",
            "": 0,
            "": 0,
            "": 0*/
    public ArrayList<PlayerComingMatches> parseJson(String jsonString) throws JSONException {
        ArrayList<PlayerComingMatches> matches = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonArray = jsonObject.getJSONArray("matches");
        for (int i =0;i<jsonArray.length();i++)
        {
            PlayerComingMatches match = new PlayerComingMatches();
            JSONObject item = jsonArray.getJSONObject(i);
            if (! item.isNull("matchId")&& item.has("matchId"))
            {
                match.setMatchId(item.getString("matchId"));
            }
            else   match.setMatchId("");

            if (! item.isNull("bookdate")&& item.has("bookdate"))
            {
                match.setBookdate(item.getString("bookdate"));
            }
            else   match.setBookdate("");

            if (! item.isNull("from")&& item.has("from"))
            {
                match.setFrom(item.getString("from"));
            }
            else   match.setFrom("");

            if (! item.isNull("to")&& item.has("to"))
            {
                match.setTo(item.getString("to"));
            }
            else    match.setTo("");

            if (! item.isNull("hasTwoMatchesInsameTime")&& item.has("hasTwoMatchesInsameTime"))
            {
                match.setHasTwoMatchesInsameTime(item.getString("hasTwoMatchesInsameTime"));
            }
            else match.setHasTwoMatchesInsameTime("");

            if (! item.isNull("HasOnthermatchAlreadyAtthattime")&& item.has("HasOnthermatchAlreadyAtthattime"))
            {
                match.setHasOnthermatchAlreadyAtthattime(item.getString("HasOnthermatchAlreadyAtthattime"));
            }
            else  match.setHasOnthermatchAlreadyAtthattime("");

            if (! item.isNull("ConfirmedThisMatch")&& item.has("ConfirmedThisMatch"))
            {
                match.setConfirmedThisMatch(item.getString("ConfirmedThisMatch"));
            }
            else     match.setConfirmedThisMatch("");

            if (! item.isNull("confirmButton")&& item.has("confirmButton"))
            {
                match.setConfirmButton(item.getString("confirmButton"));
            }
            else   match.setConfirmButton("");
            if (! item.isNull("confirmButton")&& item.has("confirmButton"))
            {
                match.setConfirmButton(item.getString("confirmButton"));
            }
            else  match.setConfirmButton("");


            if (! item.isNull("teamId1")&& item.has("teamId1"))
            {
                match.setTeamId1(item.getString("teamId1"));
            }
           // else  match.setTeamId1("");

            if (! item.isNull("teamName1")&& item.has("teamName1"))
            {
                match.setTeamName1(item.getString("teamName1"));
            }
            else match.setTeamName1("");

            if (! item.isNull("teamId2")&& item.has("teamId2"))
            {
                match.setTeamId2(item.getString("teamId2"));
            }
            else match.setTeamId2("");


            if (! item.isNull("teamName2")&& item.has("teamName2"))
            {
                match.setTeamName2(item.getString("teamName2"));
            }
            else  match.setTeamName2("");

            if (! item.isNull("playerintheTwoteams")&& item.has("playerintheTwoteams"))
            {
                match.setPlayerintheTwoteams(item.getString("playerintheTwoteams"));
            }
            else  match.setPlayerintheTwoteams("");

            if (! item.isNull("isCaptain")&& item.has("isCaptain"))
            {
                match.setIsCaptain(item.getString("isCaptain"));
            }
            else  match.setIsCaptain("");

            if (! item.isNull("borrowed")&& item.has("borrowed"))
            {
                match.setBorrowed(item.getString("borrowed"));
            }
            else  match.setBorrowed("");
            if (! item.isNull("playgroundName")&& item.has("playgroundName"))
            {
                match.setPlaygroundName(item.getString("playgroundName"));
            }
            else  match.setPlaygroundName("");
            if (! item.isNull("playgroundImage")&& item.has("playgroundImage"))
            {
                match.setPlaygroundImage(item.getString("playgroundImage"));
            }
            else  match.setPlaygroundImage("");
            if (! item.isNull("address")&& item.has("address"))
            {
                match.setAddress(item.getString("address"));
            }
            else  match.setAddress("");
            if (! item.isNull("city")&& item.has("city"))
            {
                match.setCity(item.getString("city"));
            }
            else  match.setCity("");


           matches.add(match);
        }


        return  matches;
    }
}
