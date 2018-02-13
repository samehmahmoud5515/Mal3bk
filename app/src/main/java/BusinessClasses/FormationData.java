
package BusinessClasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by logic-designs on 4/27/2017.
 */

public class FormationData {
    String playerPhoto;
    String playerName;
    String ratingBar;
    String ratingText;
    String id;
    String teamName;
    String PlayerPostion;
    Context _Context;
    int withmyteam;
    String language;
    SharedPreferences sharedPref;

    public Context get_Context() {
        return _Context;
    }

    public void set_Context(Context _Context) {
        this._Context = _Context;
    }



    public int getWithmyteam() {
        return withmyteam;
    }

    public void setWithmyteam(int withmyteam) {
        this.withmyteam = withmyteam;
    }

    public FormationData(){}
    public FormationData(Context context){

        this._Context=context;
        try {
            sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
            language = sharedPref.getString("language", "ar");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public String getPlayerPostion() {
        return PlayerPostion;
    }

    public void setPlayerPostion(String playerPostion) {
        PlayerPostion = playerPostion;
    }

    String TeamId,numberOfPlayers;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamId() {
        return TeamId;
    }

    public void setTeamId(String teamId) {
        TeamId = teamId;
    }

    public String getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(String numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlayerPhoto() {
        return playerPhoto;
    }


    public void setPlayerPhoto(String playerPhoto) {
        this.playerPhoto = playerPhoto;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

   public String getRatingBar() {
        return ratingBar;
    }

    public void setRatingBar(String ratingBar) {
        this.ratingBar = ratingBar;
    }

    public String getRatingText() {
        return ratingText;
    }

    public void setRatingText(String ratingText) {
        this.ratingText = ratingText;
    }

    public List<String> ParseTeamsNames(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonArray = jsonObject.getJSONArray("team");
        List<String> TeamsNamesList = new ArrayList<String>();
        for (int i=0;i<jsonArray.length();i++)
        {
            JSONObject object =jsonArray.getJSONObject(i);
            String teamName = object.getString("name");
            TeamsNamesList.add(teamName);
        }
        return  TeamsNamesList;

    }



    public List<FormationData> ParseTeamsPlayers(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);

       // SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(_Context);
      /// String json1 = mPrefs.getString("userObject", "");
        ///Gson gson =new Gson();
      //  final User obj = gson.fromJson(json1, User.class);
       // String userid=String.valueOf( obj.getId());
        List<FormationData> playersList = new ArrayList<>();
            JSONArray innerArray=jsonObject.getJSONArray("players");
            for (int k=0;k<innerArray.length();k++)
            {
                JSONObject playerJSONOBJ = innerArray .getJSONObject(k);
                FormationData player =new FormationData();
                player.setId(playerJSONOBJ.getString("id"));
                if (! playerJSONOBJ.isNull("stars")&& playerJSONOBJ.has("stars"))
                  player.setRatingText(playerJSONOBJ.getString("stars"));
                else   player.setRatingText("");
               // String Stars =playerJSONOBJ.getString("stars");
                player.setPlayerName(playerJSONOBJ.getString("name"));

                player.setPlayerPhoto(playerJSONOBJ.getString("image"));
               // String d =playerJSONOBJ.getString("position");

                if (language.equals("ar")){
                    if (! playerJSONOBJ.isNull("position")&& playerJSONOBJ.has("position"))
                        player.setPlayerPostion(playerJSONOBJ.getString("position"));
                    else  player.setPlayerPostion("");
                }
                else {
                    if (! playerJSONOBJ.isNull("positionEn")&& playerJSONOBJ.has("positionEn"))
                        player.setPlayerPostion(playerJSONOBJ.getString("positionEn"));
                    else  player.setPlayerPostion("");

                }
               // if (!userid.equals(playerJSONOBJ.getString("id")))
                playersList.add(player);
            }
        return playersList;
    }

    public ArrayList<String> getMyTeams(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonArray = jsonObject.getJSONArray("team");
        ArrayList<String> teams = new ArrayList<>();
        for (int i =0;i<jsonArray.length();i++)
        {
            JSONObject innerObj = jsonArray.getJSONObject(i);
            String s = innerObj.getString("id");
            teams.add(s);
        }
        return teams;
    }

    public int IsACaptainfun(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonArray = jsonObject.getJSONArray("team");
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(_Context);
        String json1 = mPrefs.getString("userObject", "");
        Gson gson =new Gson();
        final User obj = gson.fromJson(json1, User.class);
        String uid=String.valueOf( obj.getId());
        for (int i =0;i<jsonArray.length();i++)
        {
            JSONObject innerObj = jsonArray.getJSONObject(i);
            String s = innerObj.getString("CaptainId");
            if (s.equals(uid))
                return 1;
        }
        return 0;
    }
}
