package com.logicdesigns.mohammed.mal3bklast.SharedPref;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Sameh on 6/7/2017.
 */

public class PrefManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "_Mal3bk_";

    private static final String UserImage = "USER_IMAGE";

    private static final String UserName ="USER_NAME";
    private static final String USER_MAIL ="USER_MAIL";


    private static final String CITIES="CITIES";

    private static final String Playground_Types="PLAYGROUNDS_TYPE";

    private  static final String Playground_Facilities ="Playground_Facilities";

    private static final String filered_Players_json ="Filered_Players";

    private static final String Players_Postions="Players_Postions";

    private  static  final String language = "language";

    private  static  final  String isCaptain = "isCaptain";

    private static  final  String firstTime = "FirstTimeAppIsOpened";



    public  int getIsCaptain() {
        return pref.getInt(isCaptain, -1);
    }

    public void setIsCaptain(int s)
    {
        editor.putInt(isCaptain, s);
        editor.commit();
    }

    public  String getFirstTime() {
        return pref.getString(firstTime, "1");
    }

    public void setFirstTime(String s)
    {
        editor.putString(firstTime, s);
        editor.commit();
    }

    public  String getLanguage() {
        return pref.getString(language, "");
    }

    public void setLanguage(String s)
    {
        editor.putString(language, s);
        editor.commit();
    }


    public  String getCITIES() {
        return pref.getString(CITIES, "");
    }

    public void setCities( String s)
    {
        editor.putString(CITIES, s);
        editor.commit();
    }

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setUserImage(String isFirstTime) {
        editor.putString(UserImage, isFirstTime);
        editor.commit();
    }

    public String getUserImage() {
        return pref.getString(UserImage, "");
    }

    public  String getUsername() {
        return pref.getString(UserName, "");
    }
    public void setUserName(String Username)
    {
        editor.putString(UserName, Username);
        editor.commit();
    }

    public  String getUserMail() {
        return pref.getString(USER_MAIL, "");    }
    public void setUSERMAIL(String mail)
    {
        editor.putString(USER_MAIL, mail);
        editor.commit();
    }

    public void setPlayground_Types (String t)
    {
        editor.putString(Playground_Types, t);
        editor.commit();
    }
    public String getPlayground_Types()
    {
        return pref.getString(Playground_Types, "");
    }

    public void setPlayground_Facilities (String facilities)
    {
        editor.putString(Playground_Facilities, facilities);
        editor.commit();
    }


    public String getPlayground_Facilities ()
    {
        return pref.getString(Playground_Facilities, "");
    }
    public void setFilered_Players_json (String json)
    {
        editor.putString(filered_Players_json, json);
        editor.commit();
    }
    public String getFilered_Players_json()
    {
        return pref.getString(filered_Players_json, "");
    }


    public void setPlayers_Postions (String t)
    {
        editor.putString(Players_Postions, t);
        editor.commit();
    }
    public String getPlayers_Postions()
    {
        return pref.getString(Players_Postions, "");
    }
}