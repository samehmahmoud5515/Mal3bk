package com.logicdesigns.mohammed.mal3bklast.player;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by logicDesigns on 11/13/2017.
 */

public class PlayerStoredData {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;
    // Shared preferences file name
    private static final String PREF_NAME = "_Mal3bk";

    public PlayerStoredData(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }



    private static final String SPEED = "Speed";
    private static final String SKILL = "SKILL";
    private static final String SHOOT = "SHOOT";
    private static final String birthdate = "birthdate";
    private static final String rorl = "rorl";
    private static final String Position = "position";



    public  String getPosition() {
        return pref.getString(Position, "0");
    }

    public void setPosition(String s)
    {
        editor.putString(Position, s);
        editor.commit();
    }

    public  String getRorl() {
        return pref.getString(rorl, "0");
    }

    public void setRorl(String s)
    {
        editor.putString(rorl, s);
        editor.commit();
    }

    public  String getBirthdate() {
        return pref.getString(birthdate, "0");
    }

    public void setBirthdate(String s)
    {
        editor.putString(birthdate, s);
        editor.commit();
    }


    public  String getSpeed() {
        return pref.getString(SPEED, "0");
    }

    public void setSpeed(String s)
    {
        editor.putString(SPEED, s);
        editor.commit();
    }

    public  String getSKILL() {
        return pref.getString(SKILL, "0");
    }

    public void setSKILL(String s)
    {
        editor.putString(SKILL, s);
        editor.commit();
    }

    public  String getSHOOT() {
        return pref.getString(SHOOT, "0");
    }

    public void setSHOOT(String s)
    {
        editor.putString(SHOOT, s);
        editor.commit();
    }
}
