package com.logicdesigns.mohammed.mal3bklast.Models;

/**
 * Created by logicDesigns on 6/21/2017.
 */

public class Time_reserv {
  public static   String Start_Index="start";
    public static   String End_Index="end";
    String Start_value;
    String End_value;

    public Time_reserv(String start_value, String end_value) {
        Start_value = start_value;
        End_value = end_value;
    }

    public String getStart_value() {
        return Start_value;
    }

    public String getEnd_value() {
        return End_value;
    }
}
