package BusinessClasses;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by logic-designs on 4/24/2017.
 */

public class Fonts {
    Typeface dinNext;
    Typeface droidKufi;
    Typeface fontAwesome;
    Typeface openSans;

    public Fonts(Context context) {
        this.dinNext =Typeface.createFromAsset(context.getAssets(),  "fonts/DIN Next LT W23 Regular.ttf");
        this.droidKufi = Typeface.createFromAsset(context.getAssets(),  "fonts/DroidKufi-Regular.ttf");
        this.fontAwesome = Typeface.createFromAsset(context.getAssets(),  "fonts/fontawesome-webfont.ttf");
        this.openSans = Typeface.createFromAsset(context.getAssets(),  "fonts/OpenSans-Light.ttf");
    }

    public Typeface getDinNext() {
        return dinNext;
    }

    public Typeface getDroidKufi() {
        return droidKufi;
    }

    public Typeface getFontAwesome() {
        return fontAwesome;
    }

    public Typeface getOpenSans() {
        return openSans;
    }
}
