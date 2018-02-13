package com.logicdesigns.mohammed.mal3bklast.utility;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by logicDesigns on 12/10/2017.
 */

public class DateComparing {

    Date date;
    String dateString;
    public    DateComparing(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.dateString =dateString;
    }

    public  Boolean checkDate()
    {



        if(new Date().getDay() == date.getDay()&& new Date().getMonth() == date.getMonth()
                && new Date().getYear() == date.getYear())
        {
            return true;
        }

        if (new Date().before(date)) {
            return true;
        }
        else{
            Log.d("DAtEComp",String.valueOf(new Date().compareTo(date)));
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Calendar c = Calendar.getInstance();
            String formattedDate = df.format(c.getTime());

            if (formattedDate.equals(dateString))
                return true;


            return false;
        }
    }

    private static long compareTo( java.util.Date date1, java.util.Date date2 )
    {
        //returns negative value if date1 is before date2
        //returns 0 if dates are even
        //returns positive value if date1 is after date2
        return date1.getTime() - date2.getTime();
    }

    public  long compareStringDates(String strDate1,String strDate2 )
    {
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd" );
        try {
            Date d1 = sdf.parse( strDate1 );
            Date d2 = sdf.parse( strDate2 );
            return  compareTo(d1,d2);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  1;
    }

    private Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }
    private String getYesterdayDateString() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(yesterday());
    }

}
