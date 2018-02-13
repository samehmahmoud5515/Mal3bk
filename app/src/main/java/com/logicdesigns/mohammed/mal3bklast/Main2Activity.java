package com.logicdesigns.mohammed.mal3bklast;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.logicdesigns.mohammed.mal3bklast.ExceptionHandling.ExceptionHandler;
import com.logicdesigns.mohammed.mal3bklast.adapters.ComingMatchesAdapter;
import com.logicdesigns.mohammed.mal3bklast.common.FragmentsTags;
import com.logicdesigns.mohammed.mal3bklast.fragments.AddClubFragment;
import com.logicdesigns.mohammed.mal3bklast.fragments.MatchHistoryFragmnet;
import com.logicdesigns.mohammed.mal3bklast.fragments.MyTeamPlayersFragment;
import com.logicdesigns.mohammed.mal3bklast.fragments.NotficationFragment;
import com.logicdesigns.mohammed.mal3bklast.fragments.PlayerComingMatchesFragment;
import com.logicdesigns.mohammed.mal3bklast.fragments.RequestsToPlayersFragment;
import com.logicdesigns.mohammed.mal3bklast.fragments.TeamComingMatchesFragment;
import com.logicdesigns.mohammed.mal3bklast.fragments.TeamsFragment;
import com.logicdesigns.mohammed.mal3bklast.fragments.inbox_fragment;
import com.logicdesigns.mohammed.mal3bklast.header.HeaderFragment_Inner;


import BusinessClasses.ExpandableListAdapter;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class Main2Activity extends AppCompatActivity {

    public  static  boolean active = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        active = true;
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/DIN Next LT W23 Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        Intent intent = getIntent();
        String TAG = intent.getExtras().getString(FragmentsTags.FragemntTag);
        Fragment header = getSupportFragmentManager().findFragmentByTag("header");


// If fragment doesn't exist yet, create one
        if (header == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.placeholder1, new HeaderFragment_Inner(), "header")

                    .commit();
        } else { // re-use the old fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.placeholder1, header, "header")

                    .commit();
        }


        if (TAG.equals(FragmentsTags.MyTeamFragment)) {
            Fragment teamFragment = getSupportFragmentManager().findFragmentByTag(FragmentsTags.MyTeamFragment);


// If fragment doesn't exist yet, create one
            if (teamFragment == null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.placeholder2, new MyTeamPlayersFragment(), FragmentsTags.MyTeamFragment)
                        .commit();


            } else { // re-use the old fragment
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.placeholder2, teamFragment, FragmentsTags.MyTeamFragment)
                        .commit();


            }


        } else if (TAG.equals(FragmentsTags.EditMyDataFragment)) {
            Fragment editFragment = getSupportFragmentManager().findFragmentByTag(FragmentsTags.MyTeamFragment);


// If fragment doesn't exist yet, create one
            if (editFragment == null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.placeholder2, new EditPlayerDataFragment(), FragmentsTags.EditMyDataFragment)
                        .commit();
            } else { // re-use the old fragment
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.placeholder2, editFragment, FragmentsTags.EditMyDataFragment)
                        .commit();
            }
        } else if (TAG.equals(FragmentsTags.getPlayerFragment)) {
            String jsonString = intent.getStringExtra("jsonString");
            Bundle bundle = new Bundle();
            bundle.putString("playersJson", jsonString);
            bundle.putString("hideFiler", "1");
            Fragment getPlayerFragment = new GetPlayerFragment();
            getPlayerFragment.setArguments(bundle);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.placeholder2, getPlayerFragment).commit();
        } else if (TAG.equals(FragmentsTags.TeamsFragment)) {
            String jsonString = intent.getStringExtra("TeamsJsonString");
            Fragment teamsFragment = new TeamsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("TeamsJsonString", jsonString);
            teamsFragment.setArguments(bundle);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.placeholder2, teamsFragment).commit();
        }
        else if (TAG.equals(FragmentsTags.PayFragment)) {
            PayFragment payFragment = new PayFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.placeholder2, payFragment).commit();
        }
        else  if (TAG.equals(FragmentsTags.NotificationFragment)){
            NotficationFragment notficationFragment = new NotficationFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.placeholder2, notficationFragment).commit();
        }
        else  if (TAG.equals(FragmentsTags.ComingMatchesFragment))
        {
            PlayerComingMatchesFragment fragment = new PlayerComingMatchesFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.placeholder2, fragment).commit();
        }
        else  if (TAG.equals(FragmentsTags.TeamComingMatchesFragment))
        {
            TeamComingMatchesFragment fragment = new TeamComingMatchesFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.placeholder2, fragment).commit();
        }
        else  if (TAG.equals(FragmentsTags.RequestsToPlayersFragment))
        {
            RequestsToPlayersFragment fragment = new RequestsToPlayersFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.placeholder2, fragment).commit();
        }
        else  if (TAG.equals(FragmentsTags.MatchHistoryFragment))
        {
            MatchHistoryFragmnet fragment = new MatchHistoryFragmnet();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.placeholder2, fragment).commit();
        }
        else  if (TAG.equals(FragmentsTags.inboxFragment))
        {
            inbox_fragment fragment = new inbox_fragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.placeholder2, fragment).commit();
        }
        else if(TAG.equals(FragmentsTags.Add_Playground))
        {
            AddClubFragment fragment = new AddClubFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.placeholder2, fragment).commit();
        }



    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
            fm.popBackStack();


        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            try {
                //super.onBackPressed();
                super.onBackPressed();
              /*  Intent intent = new Intent(this, MainContainer.class);
                finish();
                startActivity(intent);*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        active = false;
        super.onDestroy();
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}
