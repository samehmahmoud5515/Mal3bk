package com.logicdesigns.mohammed.mal3bklast;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.logicdesigns.mohammed.mal3bklast.ExceptionHandling.ExceptionHandling;
import com.logicdesigns.mohammed.mal3bklast.Interface.OnSwipeListener;
import com.logicdesigns.mohammed.mal3bklast.JSON_PARSER.Cities_Parser;
import com.logicdesigns.mohammed.mal3bklast.JSON_PARSER.PlaygroundTypeParser;
import com.logicdesigns.mohammed.mal3bklast.JSON_PARSER.Playground_Search_reservation;
import com.logicdesigns.mohammed.mal3bklast.Models.City;
import com.logicdesigns.mohammed.mal3bklast.Models.PlayGroundType;
import com.logicdesigns.mohammed.mal3bklast.SharedPref.PrefManager;
import com.logicdesigns.mohammed.mal3bklast.common.FragmentsTags;
import com.logicdesigns.mohammed.mal3bklast.common.URLs;
import com.logicdesigns.mohammed.mal3bklast.fragments.recommended_noPlaygrounds;
import com.logicdesigns.mohammed.mal3bklast.header.HeaderFragment_Inner;
import com.logicdesigns.mohammed.mal3bklast.utility.DateComparing;
import com.logicdesigns.mohammed.mal3bklast.utility.TimeComparing;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import BusinessClasses.JSONParser;
import BusinessClasses.MainPageData;
import BusinessClasses.OnSwipeTouchListener.OnSwipeTouchListener;
import BusinessClasses.User;
import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

import static com.facebook.FacebookSdk.getApplicationContext;

public class RegisterGroundFragment extends Fragment implements TimePickerDialog.OnTimeSetListener,
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener, DialogInterface.OnDismissListener {
    boolean checkTown, checkFromTime, checkToTime, checkPlaygroundType;
    static boolean checkDate;
    Typeface custom_fontawesome, custom_DIN_Next;
    JSONParser jsonParser;
    ACProgressFlower flower_dialog;
    Typeface custom_Din;
    OnSwipeListener onSwipeListener;
    ArrayList<City> cityArrayList;
    String Playground_String_Number;
    ArrayList<PlayGroundType> PlayGroundTypeList;
    String selected_start_time = "", selected_end_time = "", selected_date = "";
    Button select_date_resv_btn, select_town_resv_btn, select_fromTime_resv_btn, select_toTime_resv_btn, select_playgroundType_resv_btn;
    TextView second_prompt_regidter_ground, third_prompt_regidter_ground, fifth_prompt_regidter_ground, from_time_icon_fragment_register_fround, from_time_prompt_fragment_register_fround, to_time_icon_fragment_register_fround, to_time_header_fragment_register_fround;
    TextView playground_type_icon_fragment_register_fround, playground_type_header_fragment_register_fround, date_icon_fragment_register_fround, location_icon_fragment_register_fround;
    static TextView calenderTXT_registerGround;
    SharedPreferences sharedPref;
    String language = null;
    String all = null, choose = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        language = sharedPref.getString("language", "");
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;

        if (language.equals("en")) {
            custom_fontawesome = Typeface.createFromAsset(getActivity().getAssets(), "fonts/fontawesome-webfont.ttf");
            custom_DIN_Next = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Light.ttf");
            view = inflater.inflate(R.layout.fragment_register_ground_en, container, false);
        } else {
            custom_fontawesome = Typeface.createFromAsset(getActivity().getAssets(), "fonts/fontawesome-webfont.ttf");
            custom_DIN_Next = Typeface.createFromAsset(getActivity().getAssets(), "fonts/DIN Next LT W23 Regular.ttf");
            view = inflater.inflate(R.layout.fragment_register_ground, container, false);

        }
        select_date_resv_btn = (Button) view.findViewById(R.id.select_date_resv_btn);
        select_date_resv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();

                com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                        RegisterGroundFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                if (language.equals("en")) {
                    dpd.setOkText(R.string.chooseEn);
                    dpd.setCancelText(R.string.cancel);
                } else {
                    dpd.setOkText("اختار");
                    dpd.setCancelText("إلغاء");
                }
                dpd.show(getActivity().getFragmentManager(), "date");

            }
        });

        select_town_resv_btn = (Button) view.findViewById(R.id.select_town_resv_btn);
        select_town_resv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //el mawka3
                PrefManager prefManager = new PrefManager(getActivity());
                String cities = prefManager.getCITIES();
                Cities_Parser parser = new Cities_Parser(getActivity());
                cityArrayList = new ArrayList<City>();
                cityArrayList = parser.parse(cities);

                String[] city_arr = new String[cityArrayList.size() + 1];
                for (int i = 1; i <= cityArrayList.size(); i++) {
                    city_arr[i] = cityArrayList.get(i-1).getCityName();
                }
                if (language.equals("en")) {
                    city_arr[0] = "All";
                    drawDialog("City", city_arr, 1);
                } else {
                    city_arr[0] = "الكل";
                    drawDialog("الموقع", city_arr, 1);
                }

            }
        });
        select_fromTime_resv_btn = (Button) view.findViewById(R.id.select_fromTime_resv_btn);
        select_fromTime_resv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();

                TimePickerDialog dpd = TimePickerDialog.newInstance(
                        RegisterGroundFragment.this,
                        false
                );
                dpd.setStartTime(now.get(Calendar.HOUR), 00, 00);
                dpd.enableMinutes(false);

                if (language.equals("en")) {
                    dpd.setOkText(R.string.chooseEn);
                    dpd.setCancelText(R.string.cancel);
                } else {
                    dpd.setOkText("اختار");
                    dpd.setCancelText("إلغاء");
                }

                dpd.show(getActivity().getFragmentManager(), "time");
            }
        });
        select_toTime_resv_btn = (Button) view.findViewById(R.id.select_toTime_resv_btn);
        select_toTime_resv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();

                TimePickerDialog dpd = TimePickerDialog.newInstance(
                        RegisterGroundFragment.this,
                        false
                );
                dpd.setStartTime(now.get(Calendar.HOUR), 00, 00);
                dpd.enableMinutes(false);

                if (language.equals("en")) {
                    dpd.setOkText(R.string.chooseEn);
                    dpd.setCancelText(R.string.cancel);
                } else {
                    dpd.setOkText("اختار");
                    dpd.setCancelText("إلغاء");
                }

                dpd.show(getActivity().getFragmentManager(), "time_end");
            }
        });
        select_playgroundType_resv_btn = (Button) view.findViewById(R.id.select_playgroundType_resv_btn);
        select_playgroundType_resv_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                PrefManager prefManager = new PrefManager(getActivity());
                String types = prefManager.getPlayground_Types();
                PlaygroundTypeParser parser = new PlaygroundTypeParser();
                PlayGroundTypeList = parser.parse(types);

                String[] types_arr = new String[PlayGroundTypeList.size()+1];
                for (int i = 1; i < PlayGroundTypeList.size()+1; i++) {
                    types_arr[i] = PlayGroundTypeList.get(i-1).getPlayground_Type_Number() + " * "
                            + PlayGroundTypeList.get(i-1).getPlayground_Type_Number();
                }
                if (language.equals("en")) {
                    types_arr[0] = "All";

                    drawDialog("Playground Type", types_arr, 2);
                } else {
                    types_arr[0] = "الكل";

                    drawDialog("نوع الملعب", types_arr, 2);
                }
            }
        });
        select_playgroundType_resv_btn.setTypeface(custom_DIN_Next);
        select_toTime_resv_btn.setTypeface(custom_DIN_Next);
        select_fromTime_resv_btn.setTypeface(custom_DIN_Next);
        select_town_resv_btn.setTypeface(custom_DIN_Next);
        select_date_resv_btn.setTypeface(custom_DIN_Next);
        if (language.equals("en")) {
            select_playgroundType_resv_btn.setText(R.string.chooseEn);
            select_toTime_resv_btn.setText(R.string.chooseEn);
            select_fromTime_resv_btn.setText(R.string.chooseEn);
            select_town_resv_btn.setText(R.string.chooseEn);
            select_date_resv_btn.setText(R.string.chooseEn);
        } else {
            select_playgroundType_resv_btn.setText("اختار");
            select_toTime_resv_btn.setText("اختار");
            select_fromTime_resv_btn.setText("اختار");
            select_town_resv_btn.setText("اختار");
            select_date_resv_btn.setText("اختار");
        }
        return view;
    }


//    @Override
//    public void onSaveInstanceState(final Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putString("date",selected_date);
//        outState.putString("from_time",selected_start_time);
//        outState.putString("to_time",selected_end_time);
//
//    }
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        if (savedInstanceState != null) {
//            // Restore last state for checked position.
//            String date = savedInstanceState.getString("date", "");
//            select_date_resv_btn.setText(date);
//        }
//    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            onSwipeListener = (OnSwipeListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnButtonClickedListener ");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        jsonParser = new JSONParser();
        //   TextView title_header_fragment= (TextView) getActivity().findViewById(R.id.title_header_fragment);
        //  title_header_fragment.setText("حجز ملعب");
        checkDate = false;
        checkTown = false;
        checkFromTime = false;
        checkToTime = false;
        checkPlaygroundType = false;
        //calenderTXT_registerGround = (TextView) getActivity().findViewById(R.id.calenderTXT_registerGround);
        from_time_icon_fragment_register_fround = (TextView) getActivity().findViewById(R.id.from_time_icon_fragment_register_fround);
        from_time_prompt_fragment_register_fround = (TextView) getActivity().findViewById(R.id.from_time_prompt_fragment_register_fround);
        to_time_icon_fragment_register_fround = (TextView) getActivity().findViewById(R.id.to_time_icon_fragment_register_fround);
        to_time_header_fragment_register_fround = (TextView) getActivity().findViewById(R.id.to_time_header_fragment_register_fround);
        playground_type_icon_fragment_register_fround = (TextView) getActivity().findViewById(R.id.playground_type_icon_fragment_register_fround);
        ;
        playground_type_header_fragment_register_fround = (TextView) getActivity().findViewById(R.id.playground_type_header_fragment_register_fround);
        //  playground_type_icon_inner_fragment_register_fround=(TextView)getActivity().findViewById(R.id.playground_type_icon_inner_fragment_register_fround);
        date_icon_fragment_register_fround = (TextView) getActivity().findViewById(R.id.date_icon_fragment_register_fround);

        location_icon_fragment_register_fround = (TextView) getActivity().findViewById(R.id.location_icon_fragment_register_fround);
        custom_Din = Typeface.createFromAsset(getActivity().getApplication().getAssets(), "fonts/DIN Next LT W23 Regular.ttf");


        second_prompt_regidter_ground = (TextView) getActivity().findViewById(R.id.second_prompt_regidter_ground);
        second_prompt_regidter_ground.setTypeface(custom_DIN_Next);
        third_prompt_regidter_ground = (TextView) getActivity().findViewById(R.id.third_prompt_regidter_ground);
        third_prompt_regidter_ground.setTypeface(custom_DIN_Next);

        fifth_prompt_regidter_ground = (TextView) getActivity().findViewById(R.id.fifth_prompt_regidter_ground);
        fifth_prompt_regidter_ground.setTypeface(custom_DIN_Next);


        from_time_icon_fragment_register_fround.setTypeface(custom_fontawesome);
        from_time_prompt_fragment_register_fround.setTypeface(custom_DIN_Next);
        to_time_icon_fragment_register_fround.setTypeface(custom_fontawesome);
        to_time_header_fragment_register_fround.setTypeface(custom_DIN_Next);
        playground_type_icon_fragment_register_fround.setTypeface(custom_fontawesome);
        playground_type_header_fragment_register_fround.setTypeface(custom_DIN_Next);
        // playground_type_icon_inner_fragment_register_fround.setTypeface(custom_fontawesome);
        date_icon_fragment_register_fround.setTypeface(custom_fontawesome);
        ;
        location_icon_fragment_register_fround.setTypeface(custom_fontawesome);

        LinearLayout regitration_ground_content = (LinearLayout) getActivity().findViewById(R.id.regitration_ground_content);

        if (language.equals("en")) {
            regitration_ground_content.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            second_prompt_regidter_ground.setText(R.string.Date1);
            third_prompt_regidter_ground.setText(R.string.city);
            from_time_prompt_fragment_register_fround.setText(R.string.from);
            to_time_header_fragment_register_fround.setText(R.string.to);
            playground_type_header_fragment_register_fround.setText(R.string.playgroundtype);
            fifth_prompt_regidter_ground.setText(R.string.search);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.WRAP_CONTENT,
//                    65
//            );
//            params.setMargins(12,0,4,0);
//            to_time_icon_fragment_register_fround.setLayoutParams(params);
//            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.WRAP_CONTENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT
//            );
//            params2.setMargins(0,0,10,0);
//            to_time_header_fragment_register_fround.setLayoutParams(params2);
//            LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.WRAP_CONTENT,
//                    65
//            );
//            params3.setMargins(4,0,22,0);
//            select_toTime_resv_btn.setLayoutParams(params3);

//            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) select_fromTime_resv_btn.getLayoutParams();
//            layoutParams.setMargins(4,0,12,0);
//            select_fromTime_resv_btn.setLayoutParams(layoutParams);


//            LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) to_time_header_fragment_register_fround.getLayoutParams();
//            layoutParams1.setMargins(0,0,10,0);
//            to_time_header_fragment_register_fround.setLayoutParams(layoutParams1);
//
//            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) select_toTime_resv_btn.getLayoutParams();
//            layoutParams2.setMargins(4,0,22,0);
//            select_toTime_resv_btn.setLayoutParams(layoutParams2);

        }

        if (language.equals("en")) {
            choose = "Choose";
            all = "All";
        } else {
            choose = "اختار";
            all = "الكل";
        }
        fifth_prompt_regidter_ground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeComparing timeComparing = new TimeComparing();
                if (selected_start_time!=""&&selected_end_time!= "") {
                    boolean timeRes = timeComparing.checkTime(selected_start_time, selected_end_time);
                    Log.d("TimeRaw", String.valueOf(timeRes));
                    if (!timeRes) {
                        if (language.equals("en")) {
                            Toast.makeText(getActivity(), R.string.time, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "وقت النهاية  يجب ان يكون اكبر من البداية", Toast.LENGTH_SHORT).show();
                        }
                        return;
                    }
                }
                if (selected_date == null|| rawDate ==null)
                {
                    if (language.equals("en"))
                        Toast.makeText(getActivity(), "Please select date", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getActivity(), "ادخل التاريخ من فضلك", Toast.LENGTH_SHORT).show();
                    return;
                }

                DateComparing dateComparing = new DateComparing(rawDate);
                if (!dateComparing.checkDate())
                {
                    if (language.equals("en"))
                        Toast.makeText(getActivity(), "Enter a valid Date", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getActivity(), "ادخل تاريخ صالح", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!selected_start_time.equals("") && !selected_end_time.equals("") && !select_playgroundType_resv_btn.getText().equals(choose) && !select_town_resv_btn.getText().toString().equals(choose) && !select_date_resv_btn.getText().toString().equals(choose)) {
                    if (Integer.parseInt(selected_start_time) == Integer.parseInt(selected_end_time)) {
                        if (language.equals("en")) {
                            Toast.makeText(getActivity(), R.string.time, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "وقت النهاية يجب ان يكون اكبر من البداية", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        flower_dialog = new ACProgressFlower.Builder(getActivity())
                                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                                .themeColor(Color.WHITE)
                                .fadeColor(Color.GREEN).build();
                        flower_dialog.show();
                        //my code is here
                        //first we call api the send json result to fragment result
                        City city = new City();
//                        String cityId = city.getCityIdbyName(cityArrayList,
//                                select_town_resv_btn.getText().toString());
                        String cityId;
                        if (!select_town_resv_btn.getText().toString().equals(all))
                            cityId = city.getCityIdbyName(cityArrayList,
                                    select_town_resv_btn.getText().toString());
                        else cityId = "-1";
                        String typeId;
                        String CurrentString = select_playgroundType_resv_btn.getText().toString();

                        StringTokenizer tokens = new StringTokenizer(CurrentString, " *");
                        Playground_String_Number = tokens.nextToken();

                        PlayGroundType playGroundType = new PlayGroundType();
                        if (CurrentString.equals(all))
                            typeId="-1";
                        else
                         typeId = playGroundType.getPlaygroundIdbyNumber(PlayGroundTypeList
                                , Playground_String_Number);
                        Log.d("allllllll ",typeId);


                        // Instantiate the RequestQueue.
                        RequestQueue queue = Volley.newRequestQueue(getActivity());
                        String url = "https://logic-host.com/work/kora/beta/phpFiles/getAllAvailablePlaygroundsAPI.php?key=logic123" +
                                "&bookdate=" + select_date_resv_btn.getText().toString() +
                                "&from=" + selected_start_time + ":00:00&to=" + selected_end_time + ":00:00" +
                                "&city=" + cityId +
                                "&type=" + typeId;
                        Log.d("uuuu111", url);
                        // Request a string response from the provided URL.
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        JSONObject json = null;
                                        try {
                                            json = new JSONObject(response);
                                            int success = json.getInt("success");
                                            if (success == 0) {// No Playgrounds after search
                                                flower_dialog.dismiss();
//                                                AlertDialog alertDialog = new AlertDialog.Builder(
//                                                        getActivity()).create();
//                                                alertDialog.setTitle("TITLE"); // your dialog title
//                                                alertDialog.setMessage("Your message"); // a message above the buttons
//                                                alertDialog.setIcon(R.drawable.ic_wrong); // the icon besides the title you have to change it to the icon/image you have.
//
//                                                alertDialog.show();
                                                final Activity mActivity = getActivity();

                                                final Dialog dialog = new Dialog(mActivity, R.style.CustomDialogTheme);
                                                dialog.setContentView(R.layout.no_playgrounds_custom_dialog);
                                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                                dialog.setCancelable(true);
                                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                                TextView no_message_text = (TextView) dialog.findViewById(R.id.no_message_text);
                                                no_message_text.setTypeface(custom_DIN_Next);

                                                Button close_button = (Button) dialog.findViewById(R.id.btn_close_no_playground);
                                                close_button.setTypeface(custom_DIN_Next);

                                                if (language.equals("en")) {
                                                    close_button.setText(R.string.close);
                                                    no_message_text.setText(R.string.noStadiums);
                                                }
                                                close_button.setOnClickListener(new View.OnClickListener() {

                                                    @Override
                                                    public void onClick(View v) {
                                                        dialog.cancel();
                                                    }
                                                });
                                                dialog.show();

                                            } else if (success == 1) {
                                                flower_dialog.dismiss();
                                                Playground_Search_reservation playgroundSearchReservation = new Playground_Search_reservation(getActivity(), selected_start_time, selected_end_time);

                                                ArrayList<Playground_Search_reservation> playgroundsList =
                                                        playgroundSearchReservation.Parse(response);
                                                if (playgroundsList == null) {
                                                    Typeface custom_DIN_Next = Typeface.createFromAsset(getActivity().getAssets(), "fonts/DIN Next LT W23 Regular.ttf");
                                                    final Dialog dialog = new Dialog(getActivity(), R.style.CustomDialogTheme);
                                                    dialog.setContentView(R.layout.no_playgrounds_custom_dialog);
                                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                                    dialog.setCancelable(true);
                                                    TextView no_message_text = (TextView) dialog.findViewById(R.id.no_message_text);
                                                    no_message_text.setTypeface(custom_DIN_Next);

                                                    Button close_button = (Button) dialog.findViewById(R.id.btn_close_no_playground);
                                                    close_button.setTypeface(custom_DIN_Next);
                                                    if (language.equals("en")) {
                                                        close_button.setText(R.string.close);
                                                        no_message_text.setText(R.string.noStadiums);
                                                    }

                                                    close_button.setOnClickListener(new View.OnClickListener() {

                                                        @Override
                                                        public void onClick(View v) {
                                                            dialog.cancel();

                                                        }
                                                    });
                                                    dialog.show();
                                                } else {

                                                    Fragment viewResultPlaygroundFragment = new ViewResultPlaygroundFragment();

                                                    Bundle args = new Bundle();
                                                    args.putString("playgroundJsonResults", response);
                                                    Log.d("jjjj11", response);

                                                    flower_dialog.dismiss();
                                                    args.putString("date", select_date_resv_btn.getText().toString());
                                                    args.putString("start_time", selected_start_time);
                                                    args.putString("end_time", selected_end_time);

                                                    viewResultPlaygroundFragment.setArguments(args);

                                                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();

                                                    ft.replace(R.id.registerGround_framelayout, viewResultPlaygroundFragment).
                                                            addToBackStack("viewResultPlaygroundFragment").commit();
                                                    FragmentTransaction ft1 = getActivity().getSupportFragmentManager().beginTransaction();
                                                    ft1.replace(R.id.placeholder1, new HeaderFragment_Inner())
                                                            .commit();
                                                    MainContainer.headerNum = 2;
                                                }
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                            ExceptionHandling exceptionHandling = new ExceptionHandling(getActivity());
                                            exceptionHandling.showErrorDialog();
                                        }


                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        // Add the request to the RequestQueue.
                        queue.add(stringRequest);


                    }

                } else {
                    if (language.equals("en")) {
                        Toast.makeText(getActivity(), R.string.fields, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "ادخل جميع البيانات المطلوبة", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
        LinearLayout dateLinearLayout = (LinearLayout) getView().findViewById(R.id.dateLinearLayout);
        dateLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_date_resv_btn.performClick();
            }
        });
        LinearLayout cityLinearLayout = (LinearLayout) getView().findViewById(R.id.cityLinearLayout);
        cityLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_town_resv_btn.performClick();
            }
        });
        from_time_prompt_fragment_register_fround.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_fromTime_resv_btn.performClick();
            }
        });
        to_time_header_fragment_register_fround.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_toTime_resv_btn.performClick();
            }
        });
        LinearLayout ptypeLinearLayout = (LinearLayout) getView().findViewById(R.id.ptypeLinearLayout);
        ptypeLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_playgroundType_resv_btn.performClick();
            }
        });
    }

    @Override
    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        String date;
        if ((monthOfYear + 1) > 9)
            date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        else date = year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth;

        select_date_resv_btn.setText(date);
        selected_date = date;
        //dd/MM/yyyy
        int mon = monthOfYear+1;
        rawDate = dayOfMonth+"/"+mon+"/"+year;
    }
    String rawDate;
    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String time = String.valueOf(hourOfDay);
        if (view.getTag().equals("time")) {
            if (Integer.parseInt(time) > 12) {
                int _24format = Integer.parseInt(time);
                int _12format = _24format - 12;
                select_fromTime_resv_btn.setText(_12format + "PM");
            }
            //else select_fromTime_resv_btn.setText(time + "AM");

             else if (Integer.parseInt(time) == 12)
                select_fromTime_resv_btn.setText(time + "PM");
            else select_fromTime_resv_btn.setText(time + "AM");

            selected_start_time = time;
            if  (hourOfDay == 0){
                select_fromTime_resv_btn.setText("12AM");
                selected_start_time = "0";
            }
        }
        if (view.getTag().equals("time_end")) {
            if (Integer.parseInt(time) > 12) {
                int _24format = Integer.parseInt(time);
                int _12format = _24format - 12;
                select_toTime_resv_btn.setText(_12format + "PM");
            }  else if (Integer.parseInt(time) == 12)
                select_toTime_resv_btn.setText(time + "PM");
            else select_toTime_resv_btn.setText(time + "AM");

            selected_end_time = time;
            if  (hourOfDay == 0){
                select_toTime_resv_btn.setText("12AM");
                selected_end_time = "0";
            }
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        selected_end_time = "";
        selected_start_time = "";
        selected_date = "";
    }


    void drawDialog(String Tiltle, final String s[], final int button_num) {
        @SuppressLint("RestrictedApi") AlertDialog.Builder dialog = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.CustomDialogTheme));
        TextView title = new TextView(getActivity());

        title.setText(Tiltle);
        title.setBackgroundColor(Color.parseColor("#008542"));


        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(20);
        title.setTypeface(custom_DIN_Next);

        dialog.setCustomTitle(title);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.choice_item, R.id.text_view_choice, s) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(R.id.text_view_choice);
                text.setTypeface(custom_DIN_Next);
                return view;
            }
        };
        dialog.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (button_num == 1)
                    select_town_resv_btn.setText(s[which]);
                if (button_num == 2)
                    select_playgroundType_resv_btn.setText(s[which]);

            }
        });


        AlertDialog alert = dialog.create();

        alert.show();
    }


}
