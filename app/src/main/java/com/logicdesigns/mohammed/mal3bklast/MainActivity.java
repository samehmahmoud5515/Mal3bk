package com.logicdesigns.mohammed.mal3bklast;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.logicdesigns.mohammed.mal3bklast.tasks.RefreshUserInforamtion;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import BusinessClasses.ExpandableListAdapter;

public class MainActivity extends Activity {
    private DrawerLayout mDrawerLayout;
    LinearLayout mDockLayout;
    ImageView toggle;
    ActionBar actionBar;
    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> laptopCollection;
    ExpandableListView expListView;
    TextView groupItem,childItem ;

    TextView first_Prompt_main,second_Prompt_main,third_Prompt_main,fourth_Prompt_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getString("langugae", "").equals("en")){
            setContentView(R.layout.activity_main_en);}
        else{
            setContentView(R.layout.activity_main);
        }



        /*set  fonts*/
        groupItem=(TextView)findViewById(R.id.laptop);
        childItem=(TextView)findViewById(R.id.laptop); ;
        first_Prompt_main=(TextView)findViewById(R.id.first_prompt_main);
        second_Prompt_main=(TextView)findViewById(R.id.second_Prompt_main);
        third_Prompt_main=(TextView)findViewById(R.id.third_Prompt_main);
        fourth_Prompt_main=(TextView)findViewById(R.id.fourth__Prompt_main);
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/DIN Next LT W23 Regular.ttf");
        Typeface custom_font2 = Typeface.createFromAsset(getAssets(),  "fonts/DroidKufi-Regular.ttf");
        first_Prompt_main.setTypeface(custom_font);
        second_Prompt_main.setTypeface(custom_font2);
        third_Prompt_main.setTypeface(custom_font2);

        fourth_Prompt_main.setTypeface(custom_font2);




        /*end fonts*/


        toggle = (ImageView) findViewById(R.id.toggleNav);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDockLayout = (LinearLayout) findViewById(R.id.dockedLayout);


        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDrawerLayout.openDrawer(mDockLayout);
                toggle.setImageResource(R.drawable.imagestoggle2);
            }
        });



        /*
        drawer listner

        * */

        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View view, float v) {

            }

            @Override
            public void onDrawerOpened(View view) {
                toggle.setImageResource(R.drawable.imagestoggle2);

            }

            @Override
            public void onDrawerClosed(View view) {
                toggle.setImageResource(R.drawable.toggle1icon);
            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });
       /*end drawer  listner * */

       /*expand list
       *
       * */
        createGroupList();

        createCollection();

        expListView = (ExpandableListView) findViewById(R.id.laptop_list);
        final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(
                this, groupList, laptopCollection);
        expListView.setAdapter(expListAdapter);

        //setGroupIndicatorToRight();

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                final String selected = (String) expListAdapter.getChild(
                        groupPosition, childPosition);
                if(childPosition==0){
                    Intent intent = new Intent(MainActivity.this, RegisterGroundActivity.class);
                    startActivity(intent);
                }

                return true;
            }
        });
       /*end expand list*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    public void toast(View v){
        Toast toast = Toast.makeText(MainActivity.this,"please",Toast.LENGTH_LONG);
        toast.show();

    }
    private void createGroupList() {
        groupList = new ArrayList<String>();
        groupList.add("main");

    }

    private void createCollection() {
        // preparing laptops collection(child)


        String[] hpModels = { "حجز ملعب", "استقطاب لاعب محترف",
                "البحث عن تمرين"};


        laptopCollection = new LinkedHashMap<String, List<String>>();

        for (String laptop : groupList) {
            if (laptop.equals("main")) {
                loadChild(hpModels);
            }

            laptopCollection.put(laptop, childList);
        }
    }

    private void loadChild(String[] laptopModels) {
        childList = new ArrayList<String>();
        for (String model : laptopModels)
            childList.add(model);
    }

    private void setGroupIndicatorToRight() {
		/* Get the screen width */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        expListView.setIndicatorBounds(width - getDipsFromPixel(35), width
                - getDipsFromPixel(5));
    }

    // Convert pixel to dip
    public int getDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }
    public void registerGround(View v){
        Intent intent = new Intent(this, RegisterGroundActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
