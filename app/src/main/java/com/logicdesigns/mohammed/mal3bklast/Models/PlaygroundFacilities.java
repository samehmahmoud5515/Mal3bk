package com.logicdesigns.mohammed.mal3bklast.Models;

import java.util.ArrayList;

/**
 * Created by logicDesigns on 7/10/2017.
 */

public class PlaygroundFacilities {

    String PlaygroundId;
    String Playground_Facility_String;

    public PlaygroundFacilities(String playgroundId, String playground_Facility_String) {
        PlaygroundId = playgroundId;
        Playground_Facility_String = playground_Facility_String;
    }
    public PlaygroundFacilities() {

    }

    public String getPlaygroundId() {
        return PlaygroundId;
    }

    public void setPlaygroundId(String playgroundId) {
        PlaygroundId = playgroundId;
    }

    public String getPlayground_Facility_String() {
        return Playground_Facility_String;
    }

    public void setPlayground_Facility_String(String playground_Facility_String) {
        Playground_Facility_String = playground_Facility_String;
    }

    public String getPlaygroundIdbyString(ArrayList<PlaygroundFacilities> list, String type)
    {
        for (int i=0;i<list.size();i++)
        {
            if (type.equals(list.get(i).getPlayground_Facility_String()))
            {
                return list.get(i).getPlaygroundId();
            }
        }
        return "";
    }
}
