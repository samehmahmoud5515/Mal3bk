package com.logicdesigns.mohammed.mal3bklast;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import BusinessClasses.ExpandableListAdapter;

public class RegisterGroundActivity extends Activity {
    private DrawerLayout mDrawerLayout;
    LinearLayout mDockLayout;
    ImageView toggle;
    ActionBar actionBar;
    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> laptopCollection;
    ExpandableListView expListView;

    ////
    Spinner timeSpinner ;
    Spinner townSpinner ;
    ///
    ////////calender
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView calenderTXT;
    private int year, month, day;
    TextView first_prompt_regidter_ground,second_prompt_regidter_ground,third_prompt_regidter_ground,fourth_prompt_regidter_ground,fifth_prompt_regidter_ground;
    ///////end calender
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_register_ground_copywith_expand_list);
        //populate spinner
        timeSpinner = (Spinner)findViewById(R.id.timeSpinner);
        townSpinner = (Spinner)findViewById(R.id.townSpinner);
        String[] towns=new String[]{"cairo","alex","aswan"};
        String[] time=new String[]{"1AM","2AM","3Am","4AM","5AM","6AM","7AM","8AM","9AM","10AM","11AM","12AM"};
        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, towns);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        townSpinner.setAdapter(arrayAdapter);
        ArrayAdapter<String> arrayAdapterTime= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, time);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(arrayAdapterTime);
        ///ens populaate spinner

        Typeface custom_fontawesome = Typeface.createFromAsset(getAssets(),  "fonts/fontawesome-webfont.ttf");
        Typeface custom_DIN_Next = Typeface.createFromAsset(getAssets(),  "fonts/DIN Next LT W23 Regular.ttf");
        first_prompt_regidter_ground=(TextView) findViewById(R.id.first_prompt_regidter_ground);
        first_prompt_regidter_ground.setTypeface(custom_DIN_Next);
        second_prompt_regidter_ground=(TextView) findViewById(R.id.second_prompt_regidter_ground);
        second_prompt_regidter_ground.setTypeface(custom_DIN_Next);
        third_prompt_regidter_ground=(TextView) findViewById(R.id.third_prompt_regidter_ground);
        third_prompt_regidter_ground.setTypeface(custom_DIN_Next);
        fourth_prompt_regidter_ground=(TextView) findViewById(R.id.fourth_prompt_regidter_ground);
        fourth_prompt_regidter_ground.setTypeface(custom_DIN_Next);
        fifth_prompt_regidter_ground=(TextView) findViewById(R.id.fifth_prompt_regidter_ground);
        fifth_prompt_regidter_ground.setTypeface(custom_DIN_Next);


        //////end set fonts

        toggle = (ImageView) findViewById(R.id.toggleNav);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDockLayout = (LinearLayout) findViewById(R.id.dockedLayout);
        //////////////////set calender
        calenderTXT = (TextView) findViewById(R.id.calenderTXT);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);


        calenderTXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialog(999);
                Toast.makeText(getApplicationContext(), "ca",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        //////////////////


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
                Toast.makeText(getBaseContext(), selected, Toast.LENGTH_LONG)
                        .show();

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
        Toast toast = Toast.makeText(this,"please",Toast.LENGTH_LONG);
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

    /*calender function
     */
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        calenderTXT.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }
    /*end calender function*/
    // view results  btn click
    public void viewResult(View v){
        Intent intent = new Intent(this, MainContainer.class);
        startActivity(intent);

    }
}
