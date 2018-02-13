package com.logicdesigns.mohammed.mal3bklast.pager;

/**
 * Created by logicDesigns on 6/21/2017.
 */


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.logicdesigns.mohammed.mal3bklast.MainPageFragment;
import com.logicdesigns.mohammed.mal3bklast.ProfileFragment;
import com.logicdesigns.mohammed.mal3bklast.R;
import com.logicdesigns.mohammed.mal3bklast.RegisterGroundFragment;
import com.logicdesigns.mohammed.mal3bklast.SearchAboutPlayerFragment;
import com.logicdesigns.mohammed.mal3bklast.SearchTrainingBoxFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {
   Context mContext;
//    TextView get_player_footer,search_training_footer,home_footer,profile_footer;
//    ImageView register_playground_footer;
//    LinearLayout ll_get_player_footer,ll_search_training_footer
//            ,ll_home_footer,ll_profile_footer,ll_register_playground_footer;

    public TabsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext=context;
        Typeface custom_fontawesome= Typeface.createFromAsset((mContext).getAssets(),  "fontawesome-webfont.ttf");
//        register_playground_footer=(ImageView) ((Activity)mContext).findViewById(R.id.register_playground_footer);
//
//
//        ll_get_player_footer=(LinearLayout) ((Activity)mContext).findViewById(R.id.ll_get_player_footer);
//        ll_search_training_footer=(LinearLayout) ((Activity)mContext).findViewById(R.id.ll_search_training_footer);
//        ll_home_footer=(LinearLayout) ((Activity)mContext).findViewById(R.id.ll_home_footer);
//        ll_profile_footer=(LinearLayout) ((Activity)mContext).findViewById(R.id.ll_profile_footer);
//        ll_register_playground_footer=(LinearLayout) ((Activity)mContext).findViewById(R.id.ll_register_playground_footer);
//
//        home_footer=(TextView) ((Activity)mContext).findViewById(R.id.home_footer);
//        home_footer.setTypeface(custom_fontawesome);
//
//
//        profile_footer=(TextView) ((Activity)mContext).findViewById(R.id.profile_footer);
//        profile_footer.setTypeface(custom_fontawesome);
//
//        get_player_footer=(TextView) ((Activity)mContext).findViewById(R.id.get_player_footer);
//        get_player_footer.setTypeface(custom_fontawesome);
//
//
//        search_training_footer=(TextView) ((Activity)mContext).findViewById(R.id.search_training_footer);
//        search_training_footer.setTypeface(custom_fontawesome);

    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // fragment activity
               return  new MainPageFragment();


//                register_playground_footer.setBackgroundResource(R.drawable.goal2);
//                get_player_footer.setTextColor(Color.parseColor("#dddddd"));
//                search_training_footer.setTextColor(Color.parseColor("#dddddd"));
//                home_footer.setTextColor(Color.parseColor("#dddddd"));
//                profile_footer.setTextColor(Color.parseColor("#dddddd"));

            case 1:
                // Games fragment activity
                return new RegisterGroundFragment();


            case 2:
                // Movies fragment activity
               return new SearchAboutPlayerFragment();

            case 3:
                return  new SearchTrainingBoxFragment();

//                register_playground_footer.setBackgroundResource(R.drawable.shabka);
//                get_player_footer.setTextColor(Color.parseColor("#dddddd"));
//                search_training_footer.setTextColor(Color.parseColor("#dddddd"));
//                home_footer.setTextColor(Color.parseColor("#dddddd"));
//                profile_footer.setTextColor(Color.parseColor("#008542"));

           case 4 :
              return  new ProfileFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 5;
    }

}