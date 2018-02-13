package com.logicdesigns.mohammed.mal3bklast.Models;

import com.logicdesigns.mohammed.mal3bklast.JSON_PARSER.Player_Properties;

/**
 * Created by logicDesigns on 7/5/2017.
 */

public class PlaygroundProperties {

    String playgroundId,playgroundName,playgroundAddress,playgroundImage
            ,playgroundClub
            ,playgroundType,playgroundCityName,date_time,start_time,end_time,
    stars,doush,panio,floor;

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getDoush() {
        return doush;
    }

    public void setDoush(String doush) {
        this.doush = doush;
    }

    public String getPanio() {
        return panio;
    }

    public void setPanio(String panio) {
        this.panio = panio;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public PlaygroundProperties(String playgroundId, String playgroundName, String playgroundAddress, String playgroundImage, String playgroundClub, String playgroundType, String playgroundCityName) {
        this.playgroundId = playgroundId;
        this.playgroundName = playgroundName;
        this.playgroundAddress = playgroundAddress;
        this.playgroundImage = playgroundImage;
        this.playgroundClub = playgroundClub;
        this.playgroundType = playgroundType;
        this.playgroundCityName = playgroundCityName;
    }


    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public PlaygroundProperties() {
    }

    public String getPlaygroundId() {
        return playgroundId;
    }

    public void setPlaygroundId(String playgroundId) {
        this.playgroundId = playgroundId;
    }

    public String getPlaygroundName() {
        return playgroundName;
    }

    public void setPlaygroundName(String playgroundName) {
        this.playgroundName = playgroundName;
    }

    public String getPlaygroundAddress() {
        return playgroundAddress;
    }

    public void setPlaygroundAddress(String playgroundAddress) {
        this.playgroundAddress = playgroundAddress;
    }

    public String getPlaygroundImage() {
        return playgroundImage;
    }

    public void setPlaygroundImage(String playgroundImage) {
        this.playgroundImage = playgroundImage;
    }

    public String getPlaygroundClub() {
        return playgroundClub;
    }

    public void setPlaygroundClub(String playgroundClub) {
        this.playgroundClub = playgroundClub;
    }



    public String getPlaygroundType() {
        return playgroundType;
    }

    public void setPlaygroundType(String playgroundType) {
        this.playgroundType = playgroundType;
    }

    public String getPlaygroundCityName() {
        return playgroundCityName;
    }

    public void setPlaygroundCityName(String playgroundCityName) {
        this.playgroundCityName = playgroundCityName;
    }
}
