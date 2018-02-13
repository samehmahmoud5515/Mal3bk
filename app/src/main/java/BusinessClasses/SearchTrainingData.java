package BusinessClasses;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by logic-designs on 4/30/2017.
 */

public class SearchTrainingData {
    String teamName;
    String townName;
    String playgroundName;
    String ratingBar;
    String rating;
    String start_time,end_time;
    String playgroundId;
    String address;

    int received_start_time, received_end_time;

    public SearchTrainingData (int st,int e){
        received_start_time=st;
        received_end_time=e;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPlaygroundId() {
        return playgroundId;
    }

    public void setPlaygroundId(String playgroundId) {
        this.playgroundId = playgroundId;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public SearchTrainingData (){}

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public String getPlaygroundName() {
        return playgroundName;
    }

    public void setPlaygroundName(String playgroundName) {
        this.playgroundName = playgroundName;
    }

    public String getRatingBar() {
        return ratingBar;
    }

    public void setRatingBar(String ratingBar) {
        this.ratingBar = ratingBar;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public ArrayList<SearchTrainingData> ParseJson(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        ArrayList<SearchTrainingData> PlaygroundsList = new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray("playground");
        for (int i=0;i<jsonArray.length();i++)
        {
            JSONObject innerObj = jsonArray.getJSONObject(i);
            SearchTrainingData playground = new SearchTrainingData();
            if (!innerObj.isNull("name") && innerObj.has("name"))
               playground.setPlaygroundName(innerObj.getString("name"));
            else playground.setPlaygroundName("");
            if (!innerObj.isNull("id") && innerObj.has("id"))
                 playground.setPlaygroundId(innerObj.getString("id"));
            else  playground.setPlaygroundId("");
            if (!innerObj.isNull("address") && innerObj.has("address"))
                 playground.setAddress(innerObj.getString("address"));
            else  playground.setAddress("");
            if (!innerObj.isNull("stars") && innerObj.has("stars"))
                playground.setRating(innerObj.getString("stars"));
            else  playground.setRating("");
            if (!innerObj.isNull("start") && innerObj.has("start"))
                playground.setStart_time(innerObj.getString("start"));
            else  playground.setStart_time("");
            if (!innerObj.isNull("end") && innerObj.has("end"))
                 playground.setEnd_time(innerObj.getString("end"));
            else playground.setEnd_time("");
            if (!innerObj.isNull("city") && innerObj.has("city"))
                playground.setTownName(innerObj.getString("city"));
            else playground.setTownName("");
            if (!innerObj.isNull("clubName") && innerObj.has("clubName"))
                playground.setTeamName(innerObj.getString("clubName"));
            else  playground.setTeamName("");

            if (!innerObj.isNull("start") && innerObj.has("start")&&!innerObj.isNull("end") && innerObj.has("end"))
             if(Integer.parseInt(innerObj.getString("start"))==received_start_time|| Integer.parseInt(innerObj.getString("end"))<=received_end_time
                    &&!(Integer.parseInt(innerObj.getString("start"))<received_start_time) )
              PlaygroundsList.add(playground);
        }
        return  PlaygroundsList;
    }
}
