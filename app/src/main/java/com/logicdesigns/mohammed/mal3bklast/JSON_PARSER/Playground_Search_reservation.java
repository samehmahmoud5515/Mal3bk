package com.logicdesigns.mohammed.mal3bklast.JSON_PARSER;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.logicdesigns.mohammed.mal3bklast.ExceptionHandling.ExceptionHandling;
import com.logicdesigns.mohammed.mal3bklast.R;
import com.logicdesigns.mohammed.mal3bklast.RegisterGroundFragment;
import com.logicdesigns.mohammed.mal3bklast.common.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import BusinessClasses.RegisterPlaygroundData;
import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

/**
 * Created by logicDesigns on 7/6/2017.
 */

public class Playground_Search_reservation {
    String id,name,address,image,clubName,type,description,stars,dosh,panio,floor,s_time,e_time,date;
    ACProgressFlower flower_dialog;
    int start_time,end_time;
    Context mContext;
    public Playground_Search_reservation(Context context,String start,String end)
    {
        this.start_time=Integer.parseInt(start);
        this.end_time=Integer.parseInt(end);
        this.mContext=context;
    }
    public Playground_Search_reservation(Context context)
    {
        this.mContext=context;
    }

    public String getS_time() {
        return s_time;
    }

    public void setS_time(String s_time) {
        this.s_time = s_time;
    }

    public String getE_time() {
        return e_time;
    }

    public void setE_time(String e_time) {
        this.e_time = e_time;
    }

    public Playground_Search_reservation()
    {

    }
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getDosh() {
        return dosh;
    }

    public void setDosh(String dosh) {
        this.dosh = dosh;
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



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public ArrayList<Playground_Search_reservation> Parse(String jsonString)
    {
        ExceptionHandling exceptionHandling = new ExceptionHandling(mContext);
        JSONObject json = null;
        try {
            json = new JSONObject(jsonString);

            int success = json.getInt("success");
            JSONArray jsonArray ;
            ArrayList<Playground_Search_reservation> plagroundsList=new ArrayList<Playground_Search_reservation>() ;
            if (success == 1) {
                jsonArray = json.getJSONArray("playground");


                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject c = jsonArray.getJSONObject(i);

                    Playground_Search_reservation playgroundData = new Playground_Search_reservation();

                    if (!c.isNull("name") && c.has("name"))
                        playgroundData.setName(c.getString("name"));
                    else playgroundData.setName("");
                    if (!c.isNull("id") && c.has("id"))
                        playgroundData.setId(c.getString("id"));
                    else playgroundData.setId("");
                    if (!c.isNull("image") && c.has("image"))
                        playgroundData.setImage(c.getString("image"));
                    else playgroundData.setImage("");
                    if (!c.isNull("clubName") && c.has("clubName"))
                        playgroundData.setClubName(c.getString("clubName"));
                    else playgroundData.setClubName("");
                    if (!c.isNull("stars") && c.has("stars"))
                        playgroundData.setStars(c.getString("stars"));
                    else playgroundData.setStars("");
                    if (!c.isNull("address") && c.has("address"))
                        playgroundData.setAddress(c.getString("address"));
                    else playgroundData.setAddress("");
                    if (!c.isNull("type") && c.has("type"))
                        playgroundData.setType(c.getString("type"));
                    else playgroundData.setType("");
                    if (!c.isNull("description") && c.has("description"))
                        playgroundData.setDescription(c.getString("description"));
                    else playgroundData.setDescription("");
                    if (!c.isNull("dosh") && c.has("dosh"))
                        playgroundData.setDosh(c.getString("dosh"));
                    else playgroundData.setDosh("");
                    if (!c.isNull("panio") && c.has("panio"))
                        playgroundData.setPanio(c.getString("panio"));
                    else playgroundData.setPanio("");
                    if (!c.isNull("floor") && c.has("floor"))
                        playgroundData.setFloor(c.getString("floor"));
                    else playgroundData.setFloor("");
                    String s_converted_time = "",e_converted_time = "";
                    // playgroundData.setTime(c.getString("start")+" - "+c.getString("end"));
                    if (!c.isNull("start") && c.has("start"))
                        if (Integer.parseInt(c.getString("start"))>12)
                        {
                            int _24format=Integer.parseInt(c.getString("start"));
                            int _12format = _24format-12;
                            s_converted_time=_12format+"PM";
                        }
                        else  s_converted_time=c.getString("start")+"AM";
                    if (!c.isNull("end") && c.has("end"))
                        if (Integer.parseInt(c.getString("end"))>12)
                        {
                            int _24format=Integer.parseInt(c.getString("end"));
                            int _12format = _24format-12;
                            e_converted_time=_12format+"PM";
                        }
                        else  e_converted_time=c.getString("end")+"AM";


                    if(Integer.parseInt(c.getString("start"))==start_time|| Integer.parseInt(c.getString("end"))<=end_time
                            &&!(Integer.parseInt(c.getString("start"))<start_time) )
                    {
                        playgroundData.setS_time(s_converted_time);
                        playgroundData.setE_time(e_converted_time);
                        plagroundsList.add(playgroundData);


                    }
//                if (start_time.equals(c.getString("start"))&& end_time.equals(c.getString("end")))
//                {
//                    playgroundData.setTime(converted_time);
//                    plagroundsList.add(playgroundData);
//
//                }

                }
                if (plagroundsList.size()==0)
                {
                    Typeface  custom_DIN_Next = Typeface.createFromAsset(mContext.getAssets(), "fonts/DIN Next LT W23 Regular.ttf");

                    final Dialog dialog = new Dialog(mContext, R.style.CustomDialogTheme);
                    dialog.setContentView(R.layout.no_playgrounds_custom_dialog);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


                    dialog.setCancelable(true);
                    TextView no_message_text = (TextView)dialog.findViewById(R.id.no_message_text);
                    no_message_text.setTypeface(custom_DIN_Next);

                    Button close_button = (Button)dialog.findViewById(R.id.btn_close_no_playground);
                    close_button.setTypeface(custom_DIN_Next);


                    close_button.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            dialog.cancel();



                        }});
                    dialog.show();
                    return null;
                }

            }
            return plagroundsList;
        } catch (JSONException e) {
            e.printStackTrace();

            exceptionHandling.showErrorDialog();
            return null;
        }

    }

    public ArrayList<Playground_Search_reservation> Parse(String jsonString,boolean b)
    {
        ExceptionHandling exceptionHandling = new ExceptionHandling(mContext);
        JSONObject json = null;
        try {
            json = new JSONObject(jsonString);

            int success = json.getInt("success");
            JSONArray jsonArray ;
            ArrayList<Playground_Search_reservation> plagroundsList=new ArrayList<Playground_Search_reservation>() ;
            if (success == 1) {
                jsonArray = json.getJSONArray("playground");


                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject c = jsonArray.getJSONObject(i);

                    Playground_Search_reservation playgroundData = new Playground_Search_reservation();

                    if (!c.isNull("name") && c.has("name"))
                        playgroundData.setName(c.getString("name"));
                    else playgroundData.setName("");
                    if (!c.isNull("id") && c.has("id"))
                        playgroundData.setId(c.getString("id"));
                    else playgroundData.setId("");
                    if (!c.isNull("image") && c.has("image"))
                        playgroundData.setImage(c.getString("image"));
                    else playgroundData.setImage("");
                    if (!c.isNull("clubName") && c.has("clubName"))
                        playgroundData.setClubName(c.getString("clubName"));
                    else playgroundData.setClubName("");
                    if (!c.isNull("stars") && c.has("stars"))
                        playgroundData.setStars(c.getString("stars"));
                    else playgroundData.setStars("");
                    if (!c.isNull("address") && c.has("address"))
                        playgroundData.setAddress(c.getString("address"));
                    else playgroundData.setAddress("");
                    if (!c.isNull("type") && c.has("type"))
                        playgroundData.setType(c.getString("type"));
                    else playgroundData.setType("");
                    if (!c.isNull("description") && c.has("description"))
                        playgroundData.setDescription(c.getString("description"));
                    else playgroundData.setDescription("");
                    if (!c.isNull("dosh") && c.has("dosh"))
                        playgroundData.setDosh(c.getString("dosh"));
                    else playgroundData.setDosh("");
                    if (!c.isNull("panio") && c.has("panio"))
                        playgroundData.setPanio(c.getString("panio"));
                    else playgroundData.setPanio("");
                    if (!c.isNull("floor") && c.has("floor"))
                        playgroundData.setFloor(c.getString("floor"));
                    else playgroundData.setFloor("");



                    plagroundsList.add(playgroundData);

                }
                if (plagroundsList.size()==0)
                {
                    Typeface  custom_DIN_Next = Typeface.createFromAsset(mContext.getAssets(), "fonts/DIN Next LT W23 Regular.ttf");

                    final Dialog dialog = new Dialog(mContext, R.style.CustomDialogTheme);
                    dialog.setContentView(R.layout.no_playgrounds_custom_dialog);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


                    dialog.setCancelable(true);
                    TextView no_message_text = (TextView)dialog.findViewById(R.id.no_message_text);
                    no_message_text.setTypeface(custom_DIN_Next);

                    Button close_button = (Button)dialog.findViewById(R.id.btn_close_no_playground);
                    close_button.setTypeface(custom_DIN_Next);


                    close_button.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            dialog.cancel();



                        }});
                    dialog.show();
                    return null;
                }

            }
            return plagroundsList;
        } catch (JSONException e) {
            e.printStackTrace();

            exceptionHandling.showErrorDialog();
            return null;
        }

    }
}
