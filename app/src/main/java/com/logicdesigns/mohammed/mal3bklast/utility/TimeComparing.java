package com.logicdesigns.mohammed.mal3bklast.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by logicDesigns on 12/10/2017.
 */

public class TimeComparing {

    public boolean checkTime(String strStartTime,String strEndTime)
    {
        int startTime = Integer.parseInt(strStartTime);
        int endTime = Integer.parseInt(strEndTime);
        //AM
        if ( startTime<=12 && endTime<=12)
        {
            if (startTime<endTime)
                return true;
            else return false;
        }
        //PM
        else if ( startTime>=13 && endTime>=13 ) {
             if  (startTime<endTime)
                 return true;
             else return false;
        }

       return true;
    }

   /* boolean isTimeAfter(Date inTime, Date outTime) {
        int dateDelta = inTime.compareTo(outTime);
        switch (dateDelta) {
            case 0:
                //startTime and endTime not **Equal**
                return  false;

            case 1:
                //endTime is **Greater** then startTime
                return true;

            case -1:
                //startTime is **Greater** then endTime
                return false;

            default: return false;
        }
    }*/
}
