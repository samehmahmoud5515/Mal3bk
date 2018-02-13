package com.logicdesigns.mohammed.mal3bklast.JSON_PARSER;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.logicdesigns.mohammed.mal3bklast.ExceptionHandling.ExceptionHandling;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import BusinessClasses.User;

/**
 * Created by logicDesigns on 7/9/2017.
 */

public class Player_Properties {


    Context mContext;
    ArrayList<Player_Properties> Players_List=new ArrayList<Player_Properties>();
    String id,name,password,email,cityId,birthdate,mobile,teamId,image,rorl,
            address,city,position,speed,skill,shooting,stars,rateable,canchangePosition="";
    SharedPreferences mPrefs;
    User USER_obj ;

    public String getRateable() {
        return rateable;
    }

    public void setRateable(String rateable) {
        this.rateable = rateable;
    }

    String language;
    public Player_Properties(Context c) {
        this.mContext=c;
        try{
        mPrefs = PreferenceManager.getDefaultSharedPreferences(c);
            language = mPrefs.getString("language", "ar");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try {
            Gson gson = new Gson();
            String json1 = mPrefs.getString("userObject", "");
            USER_obj = gson.fromJson(json1, User.class);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public int getInMyTeam() {
        return inMyTeam;
    }

    public void setInMyTeam(int inMyTeam) {
        this.inMyTeam = inMyTeam;
    }

    int inMyTeam;

    public String getShooting() {
        return shooting;
    }

    public void setShooting(String shooting) {
        this.shooting = shooting;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
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


    public Player_Properties(){}
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCityId() {
        if (cityId ==null)
            return  "";
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getMobile() {
        if (mobile ==null)
            return  "";
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTeamId() {
        if (teamId ==null)
            return  "";

        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRorl() {
        return rorl;
    }

    public void setRorl(String rorl) {
        this.rorl = rorl;
    }

    public String getAddress() {
        if (address ==null)
            return  "";
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        if (city ==null)
            return  "";
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPosition() {
        if (position ==null)
            return  "";
        return position;
    }

    public String getCanchangePosition() {
        return canchangePosition;
    }

    public void setCanchangePosition(String canchangePosition) {
        this.canchangePosition = canchangePosition;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public  ArrayList<Player_Properties> ParsePlayer(String jsonString) throws JSONException {
        ArrayList<Player_Properties> player_propertiesList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonArray = jsonObject.getJSONArray("user");
        for (int i=0 ;i< jsonArray.length();i++)
        {
            JSONObject obj = jsonArray.getJSONObject(i);
            Player_Properties player = new Player_Properties();
                   player.setId(obj.getString("id"));
            if (! obj.isNull("rateable")&& obj.has("rateable"))
                player.setRateable(obj.getString("rateable"));
            else player.setRateable("false");

                player.setName(obj.getString("name"));
            if (! obj.isNull("address")&& obj.has("address"))
            player.setAddress(obj.getString("address"));
            if (language !=null) {
            if (language.equals("ar")) {
                if (!obj.isNull("city") && obj.has("city"))
                    player.setCity(obj.getString("city"));
                if (!obj.isNull("position") && obj.has("position"))
                    player.setPosition(obj.getString("position"));
            } else {
                if (!obj.isNull("cityEn") && obj.has("cityEn"))
                    player.setCity(obj.getString("cityEn"));
                if (!obj.isNull("positionEn") && obj.has("positionEn"))
                    player.setPosition(obj.getString("positionEn"));
            }
        }
            else {
                if (!obj.isNull("city") && obj.has("city"))
                    player.setCity(obj.getString("city"));
                if (!obj.isNull("position") && obj.has("position"))
                    player.setPosition(obj.getString("position"));
        }
            if (! obj.isNull("mobile")&& obj.has("mobile"))
                player.setMobile(obj.getString("mobile"));
            player.setImage(obj.getString("image"));
            if (! obj.isNull("rorl")&& obj.has("rorl"))
                player.setRorl(obj.getString("rorl"));
            player.setEmail(obj.getString("email"));
            if (! obj.isNull("birthdate")&& obj.has("birthdate"))

                player.setBirthdate(obj.getString("birthdate"));
            if (! obj.isNull("speed")&& obj.has("speed"))
                player.setSpeed(obj.getString("speed"));
            if (! obj.isNull("skill")&& obj.has("skill"))
               player.setSkill(obj.getString("skill"));
            if (! obj.isNull("shooting")&& obj.has("shooting"))
                  player.setShooting(obj.getString("shooting"));
            if (! obj.isNull("stars")&& obj.has("stars"))
                   player.setStars(obj.getString("stars"));

            //canchangePosition
            if (! obj.isNull("canchangePosition")&& obj.has("canchangePosition"))
                player.setCanchangePosition(obj.getString("canchangePosition"));
           // String j = obj.getJSONArray("teams").toString();

                //player.setTeamId(obj.getJSONArray("teams").toString());
                JSONArray array = obj.getJSONArray("teams");
                if (array.length()==0)
                    player.setInMyTeam(0);

                for (int k=0;k<array.length();k++) {
                      String team = array.getString(k);
                    // teamsList.add(team);
                     if (team.equals(String.valueOf(USER_obj.getTeamId())))
                     {
                         player.setInMyTeam(1);
                         break;
                     }
                    else  player.setInMyTeam(0);
            }

            if (!String.valueOf(USER_obj.getId()).equals(player.getId()))

               player_propertiesList.add(player);
        }
        return player_propertiesList;
    }


}
