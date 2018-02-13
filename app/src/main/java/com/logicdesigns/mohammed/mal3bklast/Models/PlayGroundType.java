package com.logicdesigns.mohammed.mal3bklast.Models;

import java.util.ArrayList;

/**
 * Created by logicDesigns on 7/4/2017.
 */

public class PlayGroundType {

    String PlaygroundId;
    String Playground_Type_String;
    String Playground_Type_Number;

    public PlayGroundType(String playgroundId, String playground_Type_String, String playground_Type_Number) {
        PlaygroundId = playgroundId;
        Playground_Type_String = playground_Type_String;
        Playground_Type_Number = playground_Type_Number;
    }

    public String getPlayground_Type_Number() {
        return Playground_Type_Number;
    }

    public void setPlayground_Type_Number(String playground_Type_Number) {
        Playground_Type_Number = playground_Type_Number;
    }



    public PlayGroundType() {
    }

    public String getPlaygroundId() {
        return PlaygroundId;
    }

    public void setPlaygroundId(String playgroundId) {
        PlaygroundId = playgroundId;
    }

    public String getPlayground_Type_String() {
        return Playground_Type_String;
    }

    public void setPlayground_Type_String(String playground_Type_String) {
        Playground_Type_String = playground_Type_String;
    }

    public String getPlaygroundIdbyNumber(ArrayList<PlayGroundType> list, String number)
    {
        for (int i=0;i<list.size();i++)
        {
            if (number.equals(list.get(i).getPlayground_Type_Number()))
            {
                return list.get(i).getPlaygroundId();
            }
        }
        return "";
    }
}
