package com.logicdesigns.mohammed.mal3bklast.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Sameh on 10/21/2017.
 */


public class Message {
    private String id;
    private String message;
    private String time_date;
    private String recieverId;
    private String senderId;
    private String senderName,senderImage,recieverImage,recieverName;
    private String time ,date;

    public String getTime_date() {
        return time_date;
    }

    public String getRecieverName() {
        return recieverName;
    }

    public void setRecieverName(String recieverName) {
        this.recieverName = recieverName;
    }

    public void setTime_date(String timesent) {
        this.time_date = timesent;
    }

    public String getRecieverId() {
        return recieverId;
    }

    public void setRecieverId(String recieverId) {
        this.recieverId = recieverId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderImage() {
        return senderImage;
    }

    public void setSenderImage(String senderImage) {
        this.senderImage = senderImage;
    }

    public String getRecieverImage() {
        return recieverImage;
    }

    public void setRecieverImage(String recieverImage) {
        this.recieverImage = recieverImage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Message() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }







    public ArrayList<Message> parseJson(String jsonString) throws JSONException {
        ArrayList<Message> messageList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonArray = jsonObject.getJSONArray("chats");
        for (int i =0; i< jsonArray.length(); i++)
        {
            JSONObject innerObj = jsonArray.getJSONObject(i);

            Message message = new Message();
            if (!innerObj.isNull("id") && innerObj.has("id"))
                message.setId(innerObj.getString("id"));
            else   message.setId("");
            if (!innerObj.isNull("text") && innerObj.has("text"))
                message.setMessage(innerObj.getString("text"));
            else message.setMessage("");
            if (!innerObj.isNull("senderName") && innerObj.has("senderName"))
                message.setSenderName(innerObj.getString("senderName"));
            else message.setSenderName("");

            if (!innerObj.isNull("reciever") && innerObj.has("reciever"))
                message.setRecieverId(innerObj.getString("reciever"));
            else message.setRecieverId("");
            if (!innerObj.isNull("sender") && innerObj.has("sender"))
                message.setSenderId(innerObj.getString("sender"));
            else message.setSenderId("");
            if (!innerObj.isNull("timesent") && innerObj.has("timesent"))
                 message.setTime_date(innerObj.getString("timesent"));
            else message.setTime_date("");
            if (!innerObj.isNull("recieverName") && innerObj.has("recieverName"))
                message.setRecieverName(innerObj.getString("recieverName"));
            else message.setRecieverName("");
            if (!innerObj.isNull("recieverImage") && innerObj.has("recieverImage"))
               message.setRecieverImage(innerObj.getString("recieverImage"));
            else message.setRecieverImage("");
            if (!innerObj.isNull("senderImage") && innerObj.has("senderImage"))
                message.setSenderImage(innerObj.getString("senderImage"));
            else message.setSenderImage("");

            messageList.add(message);
        }

        return messageList;
    }


}