package com.logicdesigns.mohammed.mal3bklast.tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.logicdesigns.mohammed.mal3bklast.player.PlayerStoredData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import BusinessClasses.User;
import cc.cloudist.acplibrary.ACProgressFlower;

/**
 * Created by logicDesigns on 9/28/2017.
 */

public class RefreshUserInforamtion {

    Context mContext;
    User user;
    Button select_city_button;
    Button select_playerPostion_button;
    Button select_birthDate_btn ;
    Button select_RorL_button;
    ACProgressFlower progressFlower;

    public RefreshUserInforamtion(Context context)
    {
        mContext = context;
        try {
            callAPI();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public RefreshUserInforamtion(Context context, Button select_city_button
    , Button select_playerPostion_button, Button select_birthDate_btn ,Button
                                          select_RorL_button,ACProgressFlower    progressFlower
                                  )
    {
        mContext = context;
        this.select_city_button = select_city_button;
        this.select_playerPostion_button=select_playerPostion_button;
        this.select_birthDate_btn = select_birthDate_btn;
        this.select_RorL_button = select_RorL_button;
        this.progressFlower = progressFlower;
        try {
            callAPIWithLoadButtons();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    void callAPIWithLoadButtons()
    {
        this.progressFlower.show();
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        final Gson gson = new Gson();
        final String language = mPrefs.getString("language", "ar");


        String json1 = mPrefs.getString("userObject", "");
        User obj = gson.fromJson(json1, User.class);
        RequestQueue queue = Volley.newRequestQueue(mContext);
        String url ="https://logic-host.com/work/kora/beta/phpFiles/getPlayerByIdAPI.php?key=logic123&id="+obj.getId();

        Log.d("Refresh",url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                         progressFlower.dismiss();
                        try {
                            JSONObject jsonObjectc = new JSONObject(response);
                            int success = jsonObjectc.getInt("success");
                            if (success == 1) {
                                user = new User();
                                JSONArray jsonArray = jsonObjectc.getJSONArray("user");
                                JSONObject c = jsonArray.getJSONObject(0);


                                if (language.equals("ar"))
                                {
                                    if (!c.isNull( "city" )&& c.has("city"))
                                           select_city_button.setText(c.getString("city"));
                                    else   select_city_button.setText("اختار");

                                    if (!c.isNull( "position" )&& c.has("position"))
                                        select_playerPostion_button.setText(c.getString("position"));
                                    else   select_playerPostion_button.setText("اختار");
                                    //select_RorL_button
                                    //rorl
                                    if (!c.isNull( "rorl" )&& c.has("rorl")) {
                                        if (c.getString("rorl").equals("yes"))
                                            select_RorL_button.setText("يسار");
                                        else if (c.getString("rorl").equals("no"))
                                            select_RorL_button.setText("يمين");
                                        else if (c.getString("rorl").equals("") )
                                            select_RorL_button.setText("اختار");
                                    }
                                    else      select_RorL_button.setText("اختار");

                                }
                                else {
                                      //cityEn
                                    if (!c.isNull( "cityEn" )&& c.has("cityEn"))
                                        select_city_button.setText(c.getString("cityEn"));
                                    else   select_city_button.setText("Choose");

                                    if (!c.isNull( "positionEn" )&& c.has("positionEn"))
                                        select_playerPostion_button.setText(c.getString("positionEn"));
                                    else   select_playerPostion_button.setText("Choose");

                                    if (!c.isNull( "rorl" )&& c.has("rorl")) {
                                        if (c.getString("rorl").equals("yes"))
                                            select_RorL_button.setText("Left");
                                        else if (c.getString("rorl").equals("no"))
                                            select_RorL_button.setText("Right");
                                        else if (c.getString("rorl").equals(""))
                                            select_RorL_button.setText("Choose");
                                    }
                                    else select_RorL_button.setText("Choose");
                                }
                                //select_birthDate_btn
                                if (!c.isNull( "birthdate" )&& c.has("birthdate"))
                                    select_birthDate_btn.setText(c.getString("birthdate"));
                                else {
                                    if (language.equals("ar"))
                                    select_birthDate_btn.setText("اختار");
                                    else select_birthDate_btn.setText("Choose");

                                }


                            } else {



                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

      void callAPI()
    {

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        final SharedPreferences.Editor prefsEditor = mPrefs.edit();
        final Gson gson = new Gson();
        final String language = mPrefs.getString("language", "ar");


        String json1 = mPrefs.getString("userObject", "");
        User obj = gson.fromJson(json1, User.class);
        RequestQueue queue = Volley.newRequestQueue(mContext);
        String url ="https://logic-host.com/work/kora/beta/phpFiles/getPlayerByIdAPI.php?key=logic123&id="+obj.getId();

        Log.d("Refresh",url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObjectc = new JSONObject(response);
                            int success = jsonObjectc.getInt("success");
                            if (success == 1) {
                                user = new User();
                                JSONArray jsonArray = jsonObjectc.getJSONArray("user");
                                JSONObject c = jsonArray.getJSONObject(0);
                                Log.d("REfresh", c.toString());
                                if (!c.isNull( "id" ))
                                user.setId(Integer.parseInt(c.getString("id")));

                                if (!c.isNull( "name" ))
                                    user.setName(c.getString("name"));

                                if (!c.isNull( "password" ))
                                    user.setPassword(c.getString("password"));

                                if (!c.isNull( "email" )&& c.has("email"))
                                    user.setEmail(c.getString("email"));

                                if (!c.isNull( "cityId" )&& c.has("cityId"))
                                    user.setCityId(Integer.parseInt(c.getString("cityId")));

                                if (! c.isNull("teamId")&& c.has("teamId"))
                                    user.setTeamId(Integer.parseInt(c.getString("teamId")));

                                if (!c.isNull( "image" )&& c.has("image"))
                                    user.setImage(c.getString("image"));

                                if (!c.isNull( "address" )&& c.has("address"))
                                    user.setAddress(c.getString("address"));


                                if (!c.isNull( "fId" )&& c.has("fId"))
                                     user.setFbId(c.getString("fId"));

                                if (!c.isNull( "googleId" )&& c.has("googleId"))
                                           user.setGoogleId(c.getString("googleId"));
                                PlayerStoredData player_data = new PlayerStoredData(mContext);

                                if (language.equals("ar")) {
                                    if (!c.isNull("position") && c.has("position")) {
                                        user.setPostionID(c.getString("position"));
                                        player_data.setPosition(c.getString("position"));
                                    }
                                }
                                else if (language.equals("en")) {
                                    if (!c.isNull("positionEn") && c.has("positionEn")) {
                                        user.setPostionID(c.getString("positionEn"));
                                        player_data.setPosition(c.getString("positionEn"));
                                    }
                                }
                                //mobile
                                if (!c.isNull( "mobile" )&& c.has("mobile"))
                                    user.setMobile(c.getString("mobile"));

                                //store data of user in  shared prefrence

                                if (!c.isNull( "birthdate" )&& c.has("birthdate"))
                                    player_data.setBirthdate(c.getString("birthdate"));

                                if (!c.isNull( "rorl" )&& c.has("rorl"))
                                    player_data.setRorl(c.getString("rorl"));

                                if (!c.isNull( "speed" )&& c.has("speed"))
                                    player_data.setSpeed(c.getString("speed"));

                                if (!c.isNull( "shooting" )&& c.has("shooting"))
                                    player_data.setSHOOT(c.getString("shooting"));

                                if (!c.isNull( "skill" )&& c.has("skill"))
                                    player_data.setSKILL(c.getString("skill"));

                                String jsonObject = gson.toJson(user);
                                prefsEditor.putString("userObject", jsonObject);
                                Log.d("preflogin ", jsonObject);
                                prefsEditor.apply();


                            } else {



                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
