package com.logicdesigns.mohammed.mal3bklast.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by logicDesigns on 10/7/2017.
 */

public class RequestsToPlayers {

    public RequestsToPlayers()
    {

    }

    /*"id": "7",
            "name": "Hegazi",
            "cityId": "7",
            "teamId": "1",
            "image": "https://logic-host.com/work/kora/images/salah.jpg",
            "address": "37 naguib mahfouz",
            "positionId": "2",
            "rorl": "",
            "stars": "",
            "shooting": "2.000000",
            "skill": "2.000000",
            "speed": "2.000000",
            "city": "جدة",
            "position": "مدافع",
            "response": "0",
            "buyOrBorrow": "buy"*/
    String id,name,image,stars,shooting,skill,speed,position,buyOrBorrow,response,matchDate
            ,fromMatch,toMatch;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getShooting() {
        return shooting;
    }

    public void setShooting(String shooting) {
        this.shooting = shooting;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getBuyOrBorrow() {
        return buyOrBorrow;
    }

    public void setBuyOrBorrow(String buyOrBorrow) {
        this.buyOrBorrow = buyOrBorrow;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public String getFromMatch() {
        return fromMatch;
    }

    public void setFromMatch(String fromMatch) {
        this.fromMatch = fromMatch;
    }

    public String getToMatch() {
        return toMatch;
    }

    public void setToMatch(String toMatch) {
        this.toMatch = toMatch;
    }

    public ArrayList<RequestsToPlayers> parseJsonForBorrowPlayer(String jsonString) throws JSONException {
        ArrayList<RequestsToPlayers> list = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray array = jsonObject.getJSONArray("user");
        for (int i =0;i<array.length();i++)
        {
            JSONObject innerObj = array.getJSONObject(i);
            RequestsToPlayers request = new RequestsToPlayers();

            if (!innerObj.isNull("buyOrBorrow") && innerObj.has("buyOrBorrow"))
            if (innerObj.get("buyOrBorrow").equals("borrow")) {
                if (!innerObj.isNull("id") && innerObj.has("id")) {
                    request.setId(innerObj.getString("id"));
                } else request.setId("");

                if (!innerObj.isNull("name") && innerObj.has("name")) {
                    request.setName(innerObj.getString("name"));
                } else request.setName("");

                if (!innerObj.isNull("image") && innerObj.has("image")) {
                    request.setImage(innerObj.getString("image"));
                } else request.setImage("");

                if (!innerObj.isNull("stars") && innerObj.has("stars")) {
                    request.setStars(innerObj.getString("stars"));
                } else request.setStars("");

                if (!innerObj.isNull("shooting") && innerObj.has("shooting")) {
                    request.setShooting(innerObj.getString("shooting"));
                } else request.setShooting("");

                if (!innerObj.isNull("skill") && innerObj.has("skill")) {
                    request.setSkill(innerObj.getString("skill"));
                } else request.setSkill("");

                if (!innerObj.isNull("speed") && innerObj.has("speed")) {
                    request.setSpeed(innerObj.getString("speed"));
                } else request.setSpeed("");

                if (!innerObj.isNull("position") && innerObj.has("position")) {
                    request.setPosition(innerObj.getString("position"));
                } else request.setPosition("");

                if (!innerObj.isNull("response") && innerObj.has("response")) {
                    request.setResponse(innerObj.getString("response"));
                } else request.setResponse("");

                if (!innerObj.isNull("buyOrBorrow") && innerObj.has("buyOrBorrow")) {
                    request.setBuyOrBorrow(innerObj.getString("buyOrBorrow"));
                } else request.setBuyOrBorrow("");

                if (!innerObj.isNull("matchDate") && innerObj.has("matchDate")) {
                    request.setMatchDate(innerObj.getString("matchDate"));
                } else request.setMatchDate("");

                if (!innerObj.isNull("fromMatch") && innerObj.has("fromMatch")) {
                    request.setFromMatch(innerObj.getString("fromMatch"));
                } else request.setFromMatch("");

                if (!innerObj.isNull("toMatch") && innerObj.has("toMatch")) {
                    request.setToMatch(innerObj.getString("toMatch"));
                } else request.setToMatch("");

                list.add(request);
            }
        }

        return list;
    }

    public ArrayList<RequestsToPlayers> parseJsonForBuyPlayer(String jsonString) throws JSONException {
        ArrayList<RequestsToPlayers> list = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray array = jsonObject.getJSONArray("user");
        for (int i =0;i<array.length();i++)
        {
            JSONObject innerObj = array.getJSONObject(i);
            RequestsToPlayers request = new RequestsToPlayers();

            if (!innerObj.isNull("buyOrBorrow") && innerObj.has("buyOrBorrow"))
                if (innerObj.get("buyOrBorrow").equals("buy")) {
                    if (!innerObj.isNull("id") && innerObj.has("id")) {
                        request.setId(innerObj.getString("id"));
                    } else request.setId("");

                    if (!innerObj.isNull("name") && innerObj.has("name")) {
                        request.setName(innerObj.getString("name"));
                    } else request.setName("");

                    if (!innerObj.isNull("image") && innerObj.has("image")) {
                        request.setImage(innerObj.getString("image"));
                    } else request.setImage("");

                    if (!innerObj.isNull("stars") && innerObj.has("stars")) {
                        request.setStars(innerObj.getString("stars"));
                    } else request.setStars("");

                    if (!innerObj.isNull("shooting") && innerObj.has("shooting")) {
                        request.setShooting(innerObj.getString("shooting"));
                    } else request.setShooting("");

                    if (!innerObj.isNull("skill") && innerObj.has("skill")) {
                        request.setSkill(innerObj.getString("skill"));
                    } else request.setSkill("");

                    if (!innerObj.isNull("speed") && innerObj.has("speed")) {
                        request.setSpeed(innerObj.getString("speed"));
                    } else request.setSpeed("");

                    if (!innerObj.isNull("position") && innerObj.has("position")) {
                        request.setPosition(innerObj.getString("position"));
                    } else request.setPosition("");

                    if (!innerObj.isNull("response") && innerObj.has("response")) {
                        request.setResponse(innerObj.getString("response"));
                    } else request.setResponse("");

                    if (!innerObj.isNull("buyOrBorrow") && innerObj.has("buyOrBorrow")) {
                        request.setBuyOrBorrow(innerObj.getString("buyOrBorrow"));
                    } else request.setBuyOrBorrow("");
                    if (!innerObj.isNull("matchDate") && innerObj.has("matchDate")) {
                        request.setMatchDate(innerObj.getString("matchDate"));
                    } else request.setMatchDate("");

                    if (!innerObj.isNull("fromMatch") && innerObj.has("fromMatch")) {
                        request.setFromMatch(innerObj.getString("fromMatch"));
                    } else request.setFromMatch("");

                    if (!innerObj.isNull("toMatch") && innerObj.has("toMatch")) {
                        request.setToMatch(innerObj.getString("toMatch"));
                    } else request.setToMatch("");

                    list.add(request);
                }
        }

        return list;
    }
}
