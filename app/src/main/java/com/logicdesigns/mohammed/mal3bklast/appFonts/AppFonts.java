package com.logicdesigns.mohammed.mal3bklast.appFonts;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by logicDesigns on 11/6/2017.
 */

public class AppFonts {
    Context mContext;
    Typeface DIN_NEXT,OpenSans;
    Typeface Fontawsome;

    private static AppFonts objAppFonts;

    public static AppFonts getInstance(Context context)
    {
        if (objAppFonts == null)
        {
            objAppFonts = new AppFonts(context);
        }
        return objAppFonts;
    }
    private AppFonts(Context context)
    {
        mContext = context;
         DIN_NEXT = Typeface.createFromAsset(mContext.getAssets(),  "fonts/DIN Next LT W23 Regular.ttf");
         Fontawsome = Typeface.createFromAsset(mContext.getAssets(),  "fonts/fontawesome-webfont.ttf");
        OpenSans = Typeface.createFromAsset(mContext.getAssets(), "fonts/OpenSans-Light.ttf");

    }
    public Typeface getDIN_NEXT()
    {
        return DIN_NEXT;
    }
    public Typeface getFontawsome(){return  Fontawsome;}

    public Typeface getOpenSans() {
        return OpenSans;
    }

    public void setOpenSans(Typeface openSans) {
        OpenSans = openSans;
    }
}
