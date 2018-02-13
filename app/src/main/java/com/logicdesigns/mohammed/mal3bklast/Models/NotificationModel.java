package com.logicdesigns.mohammed.mal3bklast.Models;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.logicdesigns.mohammed.mal3bklast.ExceptionHandling.ExceptionHandling;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by logicDesigns on 9/27/2017.
 */

public class NotificationModel {
   // String tittle,details;
    /*
     "id": "54",
            "player1Id": "2",
            "seen": "1",
            "requestDate": "2017-09-27",
            "type": 1,
            "title": "Buy request",
            "titlear": "طلب استقطاب",
            "playerName": "Dunk",
            "playerImage": "https://platform-static-files.s3.amazonaws.com/premierleague/photos/players/110x140/p83299.png",
            "teamId": "6",
            "teamName": "team1",
            "matchDate": "",
            "matchFrom": "",
            "matchTo": "",
            "teamId2": 0,
            "teamName2": "",
            "playgroundId": "",
            "playgroundName": "",
            "response": "2",
            "respondable": 1

    */
   SharedPreferences sharedPref;
    String language = null;
  Context mContext;
    public NotificationModel()
    {

    }
    public NotificationModel(Context context)
    {
        mContext = context ;
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        language = sharedPref.getString("language", "ar");
    }
   String requestId,player1IdSender,seen,requestDate,type,title,titlear,playerName,playerImage,
           teamName,matchDate,matchFrom,matchTo,teamId2,teamName2,playgroundName,respondable,playgroundCity,response;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }


  /*  public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }*/

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getPlayer1IdSender() {
        return player1IdSender;
    }

    public void setPlayer1IdSender(String player1IdSender) {
        this.player1IdSender = player1IdSender;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitlear() {
        return titlear;
    }

    public void setTitlear(String titlear) {
        this.titlear = titlear;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerImage() {
        return playerImage;
    }

    public void setPlayerImage(String playerImage) {
        this.playerImage = playerImage;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public String getMatchFrom() {
        return matchFrom;
    }

    public void setMatchFrom(String matchFrom) {
        this.matchFrom = matchFrom;
    }

    public String getMatchTo() {
        return matchTo;
    }

    public void setMatchTo(String matchTo) {
        this.matchTo = matchTo;
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

    public String getPlaygroundName() {
        return playgroundName;
    }

    public void setPlaygroundName(String playgroundName) {
        this.playgroundName = playgroundName;
    }

    public String getRespondable() {
        return respondable;
    }

    public void setRespondable(String respondable) {
        this.respondable = respondable;
    }

    public String getPlaygroundCity() {
        return playgroundCity;
    }

    public void setPlaygroundCity(String playgroundCity) {
        this.playgroundCity = playgroundCity;
    }

    public ArrayList<NotificationModel> parseJson(String jsonString)
    {
        ArrayList <NotificationModel> notifications = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("request");
            for (int i=0;i<jsonArray.length(); i++)
            {

                NotificationModel noti = new NotificationModel();
                JSONObject item = jsonArray.getJSONObject(i);
               if (! item.isNull("id")&& item.has("id"))
               {
                  noti.setRequestId(item.getString("id"));
               }
                else    noti.setRequestId("");

                if (! item.isNull("player1Id")&& item.has("player1Id"))
                {
                     noti.setPlayer1IdSender(item.getString("player1Id"));
                }
                else    noti.setPlayer1IdSender("");

                if (! item.isNull("seen")&& item.has("seen"))
                {
                    noti.setSeen(item.getString("seen"));
                }
                else    noti.setSeen("");

                if (! item.isNull("title")&& item.has("title"))
                {
                    noti.setTitle(item.getString("title"));
                }
                else noti.setTitle("");

                if (! item.isNull("titlear")&& item.has("titlear"))
                {
                    noti.setTitlear(item.getString("titlear"));
                }
                else noti.setTitlear("");
                if (! item.isNull("playerName")&& item.has("playerName"))
                {
                   noti.setPlayerName(item.getString("playerName"));
                }
                else noti.setPlayerName("");

                if (! item.isNull("matchDate")&& item.has("matchDate"))
                {
                   noti.setMatchDate(item.getString("matchDate"));
                }
                else noti.setMatchDate("");

                if (! item.isNull("matchFrom")&& item.has("matchFrom"))
                {
                   noti.setMatchFrom(item.getString("matchFrom"));
                }
                else noti.setMatchFrom("");

                if (! item.isNull("matchTo")&& item.has("matchTo"))
                {
                   noti.setMatchTo(item.getString("matchTo"));
                }
                else noti.setMatchTo("");

                if (! item.isNull("teamName2")&& item.has("teamName2"))
                {
                     noti.setTeamName2(item.getString("teamName2"));
                }
                else noti.setTeamName2("");


                if (! item.isNull("playgroundName")&& item.has("playgroundName"))
                {
                   noti.setPlaygroundName(item.getString("playgroundName"));
                }
                else noti.setPlaygroundName("");

                //respondable
                if (! item.isNull("respondable")&& item.has("respondable"))
                {
                   noti.setRespondable(item.getString("respondable"));
                }
                else noti.setRespondable("");

                if (! item.isNull("teamName")&& item.has("teamName"))
                {
                    noti.setTeamName(item.getString("teamName"));
                }
                else noti.setTeamName("");

//playgroundCity
                if (language.equals("ar")) {
                    if (!item.isNull("playgroundCity") && item.has("playgroundCity")) {
                        noti.setPlaygroundCity(item.getString("playgroundCity"));
                    } else noti.setPlaygroundCity("");
                }
                else {
                    if (!item.isNull("playgroundCityEn") && item.has("playgroundCityEn")) {
                        noti.setPlaygroundCity(item.getString("playgroundCityEn"));
                    } else noti.setPlaygroundCity("");
                }
                //playerImage
                if (! item.isNull("playerImage")&& item.has("playerImage"))
                {
                    noti.setPlayerImage(item.getString("playerImage"));
                }
                else noti.setPlaygroundCity("");
                if (! item.isNull("type")&& item.has("type"))
                {
                    noti.setType(item.getString("type"));
                }
                else noti.setType("");
                if (! item.isNull("response")&& item.has("response"))
                {
                    noti.setResponse(item.getString("response"));
                }
                else noti.setResponse("");
                if ((noti.getResponse().equals("0") && noti.getRespondable().equals("1") )||  noti.getRespondable().equals("0")
                        ) {
                notifications.add(noti);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
            ExceptionHandling exceptionHandling = new ExceptionHandling(mContext);
            exceptionHandling.showErrorDialog();
        }


        return  notifications;
    }
}
