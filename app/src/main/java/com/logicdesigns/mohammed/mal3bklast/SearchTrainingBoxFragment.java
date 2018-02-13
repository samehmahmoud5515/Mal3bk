package com.logicdesigns.mohammed.mal3bklast;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.logicdesigns.mohammed.mal3bklast.Interface.OnSwipeListener;
import com.logicdesigns.mohammed.mal3bklast.JSON_PARSER.Cities_Parser;
import com.logicdesigns.mohammed.mal3bklast.JSON_PARSER.PlaygroundFacilities_Parser;
import com.logicdesigns.mohammed.mal3bklast.JSON_PARSER.PlaygroundTypeParser;
import com.logicdesigns.mohammed.mal3bklast.Models.City;
import com.logicdesigns.mohammed.mal3bklast.Models.PlayGroundType;
import com.logicdesigns.mohammed.mal3bklast.Models.PlaygroundFacilities;
import com.logicdesigns.mohammed.mal3bklast.SharedPref.PrefManager;
import com.logicdesigns.mohammed.mal3bklast.common.FragmentsTags;
import com.logicdesigns.mohammed.mal3bklast.common.URLs;
import com.logicdesigns.mohammed.mal3bklast.header.HeaderFragment_Inner;
import com.logicdesigns.mohammed.mal3bklast.utility.DateComparing;
import com.logicdesigns.mohammed.mal3bklast.utility.TimeComparing;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.StringTokenizer;

import BusinessClasses.ListViewAdapterForSearchTraining;
import BusinessClasses.OnSwipeTouchListener.OnSwipeTouchListener;
import BusinessClasses.SearchTrainingData;
import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SearchTrainingBoxFragment extends Fragment implements TimePickerDialog.OnTimeSetListener, com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener, DialogInterface.OnDismissListener {

    TextView playground_type_icon_fragment_search_training, playground_type_header_fragment_search_training, players_number_icon_fragment_search_training, players_number_header_fragment_search_training, facilities_icon_fragment_search_training, facilities_header_fragment_search_training, date_tv, ic_date, from_time_icon_fragment_register_fround, from_time_prompt_fragment_register_fround, to_time_icon_fragment_register_fround, to_time_header_fragment_register_fround;
    Button btn_fragment_search_training;
    Typeface customFontAwesome, custom2;
    Button ground_type_btn, choose_Player_numbers_button, choose_5admat_btn, select_date_button, select_startTime_btn, select_endTime_btn, select_city_button;
    TextView ic_city, city_tv;
    ACProgressFlower flower_dialog;
    OnSwipeListener onSwipeListener;
    ArrayList<City> cityArrayList;
    ArrayList<PlaygroundFacilities> playgroundFacilities;
    ArrayList<PlayGroundType> PlayGroundTypeList;
    String selected_date = "", selected_start_time = "", selected_end_time = "";
    String _5admat[] = {"الكل", "حمام", "دش"};
    String _5admatEn[] = {"All", "Bathroom", "Shower"};
    String choose = null;
    SharedPreferences sharedPref;
    String language = null;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        language = sharedPref.getString("language", "");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_search_training_box, container, false);
        customFontAwesome = Typeface.createFromAsset(getActivity().getAssets(), "fonts/fontawesome-webfont.ttf");
        custom2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/DIN Next LT W23 Regular.ttf");
        if (language.equals("en")) {
            ScrollView scrollLayout = (ScrollView) view.findViewById(R.id.scrollLayout);
            scrollLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        select_city_button = (Button) view.findViewById(R.id.select_city_button);
        if (savedInstanceState != null) {
            // Restore last state for checked position.
            String checked = savedInstanceState.getString("select_city_button", " ");
            select_city_button.setText(checked);
        }


        ground_type_btn = (Button) view.findViewById(R.id.choose_playground_button);
        ground_type_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String s []= {"نجيلة طبيعية","نجيلة صناعية","اسفلت"};
                PrefManager prefManager = new PrefManager(getActivity());
                String facilities_json_string = prefManager.getPlayground_Facilities();
                PlaygroundFacilities_Parser parser = new PlaygroundFacilities_Parser();
                playgroundFacilities = parser.parse(facilities_json_string,getActivity());
                String[] namesArr = new String[playgroundFacilities.size()+1];
                for (int i = 1; i <= playgroundFacilities.size(); i++) {
                    namesArr[i] = playgroundFacilities.get(i-1).getPlayground_Facility_String();
                }
                if (language.equals("en")) {
                    String type = getResources().getString(R.string.playgroundtype);
                    namesArr[0] = "All";
                    drawDialog(type, namesArr, 1);
                } else {
                    namesArr[0] = "الكل";
                    drawDialog("نوع الملعب", namesArr, 1);
                }
            }
        });
        choose_Player_numbers_button = (Button) view.findViewById(R.id.choose_Player_numbers_button);
        choose_Player_numbers_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefManager prefManager = new PrefManager(getActivity());
                String types = prefManager.getPlayground_Types();
                PlaygroundTypeParser parser = new PlaygroundTypeParser();
                PlayGroundTypeList = parser.parse(types);

                String[] types_arr = new String[PlayGroundTypeList.size() +1];
                for (int i = 1; i <= PlayGroundTypeList.size(); i++) {
                    types_arr[i] = PlayGroundTypeList.get(i-1).getPlayground_Type_Number() + " * "
                            + PlayGroundTypeList.get(i-1).getPlayground_Type_Number();
                }
                if (language.equals("en")) {
                    String type = getResources().getString(R.string.playgroundtype);
                    types_arr[0] = "All";
                    drawDialog(type, types_arr, 2);
                } else {
                    types_arr[0] = "الكل";
                    drawDialog("نوع الملعب", types_arr, 2);
                }
            }
        });

        choose_5admat_btn = (Button) view.findViewById(R.id.choose_5admat_btn);
        choose_5admat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (language.equals("en")) {
                    String services = getResources().getString(R.string.services);
                    drawDialog(services, _5admatEn, 3);
                } else {
                    drawDialog("الخدمات", _5admat, 3);
                }
            }
        });
        ground_type_btn.setTypeface(custom2);
        choose_5admat_btn.setTypeface(custom2);
        choose_Player_numbers_button.setTypeface(custom2);
        select_date_button = (Button) view.findViewById(R.id.select_date_button);
        select_startTime_btn = (Button) view.findViewById(R.id.select_startTime_btn);
        select_endTime_btn = (Button) view.findViewById(R.id.select_endTime_btn);

        select_date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar now = Calendar.getInstance();

                com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                        SearchTrainingBoxFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                if (language.equals("en")) {
                    String choose = getResources().getString(R.string.chooseEn);
                    String cancel = getResources().getString(R.string.cancel);
                    dpd.setOkText(choose);
                    dpd.setCancelText(cancel);
                } else {
                    dpd.setOkText("اختار");
                    dpd.setCancelText("إلغاء");
                }
                dpd.show(getActivity().getFragmentManager(), "date");
            }
        });
        select_startTime_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar now = Calendar.getInstance();

                TimePickerDialog dpd = TimePickerDialog.newInstance(
                        SearchTrainingBoxFragment.this,
                        false
                );
                dpd.setStartTime(now.get(Calendar.HOUR), 00, 00);
                dpd.enableMinutes(false);

                if (language.equals("en")) {
                    String choose = getResources().getString(R.string.chooseEn);
                    String cancel = getResources().getString(R.string.cancel);
                    dpd.setOkText(choose);
                    dpd.setCancelText(cancel);
                } else {
                    dpd.setOkText("اختار");
                    dpd.setCancelText("إلغاء");
                }

                dpd.show(getActivity().getFragmentManager(), "start_time");
            }
        });

        select_endTime_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar now = Calendar.getInstance();

                TimePickerDialog dpd = TimePickerDialog.newInstance(
                        SearchTrainingBoxFragment.this,
                        false
                );
                dpd.setStartTime(now.get(Calendar.HOUR), 00, 00);
                dpd.enableMinutes(false);

                if (language.equals("en")) {
                    String choose = getResources().getString(R.string.chooseEn);
                    String cancel = getResources().getString(R.string.cancel);
                    dpd.setOkText(choose);
                    dpd.setCancelText(cancel);
                } else {
                    dpd.setOkText("اختار");
                    dpd.setCancelText("إلغاء");
                }

                dpd.show(getActivity().getFragmentManager(), "end_time");
            }
        });

        select_city_button = (Button) view.findViewById(R.id.select_city_button);
        select_city_button.setTypeface(custom2);
        select_city_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefManager prefManager = new PrefManager(getActivity());
                String cities = prefManager.getCITIES();
                Cities_Parser parser = new Cities_Parser(getActivity());
                cityArrayList = new ArrayList<City>();
                cityArrayList = parser.parse(cities);

                String[] city_arr = new String[cityArrayList.size()+1];
                for (int i = 1; i <= cityArrayList.size(); i++) {
                    city_arr[i] = cityArrayList.get(i-1).getCityName();
                }
                if (language.equals("en")) {
                    String city = getResources().getString(R.string.city);
                    city_arr[0] = "All";
                    drawDialog(city, city_arr, 4);
                } else {
                    city_arr[0] = "الكل";

                    drawDialog("الموقع", city_arr, 4);
                }

            }
        });


        return view;

    }

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
        setIDs();
        setFonts();
        if (language.equals("en")) {
            English();
        }
        // TextView title_header_fragment= (TextView) getActivity().findViewById(R.id.title_header_fragment);

        //  title_header_fragment.setText("البحث عن تمرين");
        btn_fragment_search_training.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (language.equals("en")) {
                    choose = "Choose";
                } else {
                    choose = "اختار";
                }
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

                if (!ground_type_btn.getText().toString().equals(choose) && !choose_Player_numbers_button.getText().toString().equals(choose)
                        && !choose_5admat_btn.getText().toString().equals(choose) && !select_date_button.getText().toString().equals(choose)
                        && !select_startTime_btn.getText().toString().equals(choose) && !select_endTime_btn.getText().toString().equals(choose)
                        && !select_city_button.getText().toString().equals(choose)) {
                    flower_dialog = new ACProgressFlower.Builder(getActivity())
                            .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                            .themeColor(Color.WHITE)
                            .fadeColor(Color.GREEN).build();
                    flower_dialog.show();
                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    String all;
                    if (language.equals("en")) {
                        all = "All";
                    } else {
                        all = "الكل";
                    }
                    String cityName = select_city_button
                            .getText().toString();
                    City city = new City();
                    String cityId;
                    if (cityName.equals(all))
                        cityId ="-1";
                    else
                     cityId = city.getCityIdbyName(cityArrayList, cityName);
                    String CurrentString = choose_Player_numbers_button.getText().toString();

                    StringTokenizer tokens = new StringTokenizer(CurrentString, " *");
                    String Playground_String_Number = tokens.nextToken();

                    PlayGroundType playGroundType = new PlayGroundType();
                    String typeIdNumber;
                    if (CurrentString.equals(all)||Playground_String_Number.equals(all))
                        typeIdNumber ="-1";
                    else
                     typeIdNumber = playGroundType.getPlaygroundIdbyNumber(PlayGroundTypeList
                            , Playground_String_Number);
                    PlaygroundFacilities facilities = new PlaygroundFacilities();
                    String facilyId;
                    if (ground_type_btn.getText().toString().equals(all))
                        facilyId ="-1";
                    else
                     facilyId = facilities.getPlaygroundIdbyString(playgroundFacilities, ground_type_btn.getText().toString());
                    int choos_5aedmat_btnInt = Arrays.asList(_5admat).indexOf(choose_5admat_btn.getText().toString());
                    if (choos_5aedmat_btnInt==0)
                        choos_5aedmat_btnInt = -1;
                    else choos_5aedmat_btnInt =Arrays.asList(_5admat).indexOf(choos_5aedmat_btnInt);

                    String url = URLs.Training_link + "?key=logic123&bookdate=" + selected_date +
                            "&from=" + selected_start_time + ":00:00&to=" + selected_end_time
                            + ":00:00&city=" + cityId + "&type=" + typeIdNumber + "&floor=" + facilyId + "&facilities=" +
                           choos_5aedmat_btnInt;

                      Log.d("UUN1 ",url);
// Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        flower_dialog.dismiss();
                                        JSONObject object = new JSONObject(response);
                                        if (Integer.parseInt(object.getString("success")) == 1) {
                                            Fragment trainingFragment = new SearchTrainingFragment();
                                            Bundle bundle = new Bundle();
                                            bundle.putString("trainingJson", response);
                                            bundle.putString("selected_date", selected_date);
                                            bundle.putString("selected_start_time", selected_start_time);
                                            bundle.putString("selected_end_time", selected_end_time);
                                            trainingFragment.setArguments(bundle);
                                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                            ft.replace(R.id.searchTraining_framelayout, trainingFragment).addToBackStack(null).commit();
                                            FragmentTransaction ft1 = getActivity().getSupportFragmentManager().beginTransaction();
                                            ft1.replace(R.id.placeholder1, new HeaderFragment_Inner())
                                                    .commit();
                                            MainContainer.headerNum = 4;
                                        } else {


                                            // No Playgrounds after search
                                            flower_dialog.dismiss();
                                            final Activity mActivity = getActivity();
                                            final Dialog dialog = new Dialog(mActivity, R.style.CustomDialogTheme);
                                            dialog.setContentView(R.layout.no_playgrounds_custom_dialog);
                                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                            dialog.setCancelable(true);
                                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                            TextView no_message_text = (TextView) dialog.findViewById(R.id.no_message_text);
                                            no_message_text.setTypeface(custom2);

                                            Button close_button = (Button) dialog.findViewById(R.id.btn_close_no_playground);
                                            if (language.equals("en")) {
                                                close_button.setText(R.string.close);
                                                no_message_text.setText(R.string.noStadiums);
                                            }
                                            close_button.setTypeface(custom2);
                                            close_button.setOnClickListener(new View.OnClickListener() {

                                                @Override
                                                public void onClick(View v) {
                                                    dialog.cancel();


                                                }
                                            });
                                            dialog.show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    });
// Add the request to the RequestQueue.
                    queue.add(stringRequest);

                } else {
                    if (language.equals("en")) {
                        String enter = getResources().getString(R.string.fields);
                        Toast.makeText(getActivity(), enter, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "ادخل البيانات المطلوبة", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });


    }

    public void setIDs() {
        playground_type_icon_fragment_search_training = (TextView) getView().findViewById(R.id.playground_type_icon_fragment_search_training);
        playground_type_header_fragment_search_training = (TextView) getView().findViewById(R.id.playground_type_header_fragment_search_training);
        players_number_icon_fragment_search_training = (TextView) getView().findViewById(R.id.players_number_icon_fragment_search_training);
        players_number_header_fragment_search_training = (TextView) getView().findViewById(R.id.players_number_header_fragment_search_training);
        facilities_icon_fragment_search_training = (TextView) getView().findViewById(R.id.facilities_icon_fragment_search_training);
        facilities_header_fragment_search_training = (TextView) getView().findViewById(R.id.facilities_header_fragment_search_training);
        btn_fragment_search_training = (Button) getView().findViewById(R.id.btn_fragment_search_training);
        date_tv = (TextView) getView().findViewById(R.id.date_tv);
        ic_date = (TextView) getView().findViewById(R.id.ic_date);
        from_time_icon_fragment_register_fround = (TextView) getView().findViewById(R.id.from_time_icon_fragment_register_fround);
        from_time_prompt_fragment_register_fround = (TextView) getView().findViewById(R.id.from_time_prompt_fragment_register_fround);
        to_time_icon_fragment_register_fround = (TextView) getView().findViewById(R.id.to_time_icon_fragment_register_fround);
        to_time_header_fragment_register_fround = (TextView) getView().findViewById(R.id.to_time_header_fragment_register_fround);
        ic_city = (TextView) getView().findViewById(R.id.ic_city);
        city_tv = (TextView) getView().findViewById(R.id.city_tv);
        select_city_button = (Button) getView().findViewById(R.id.select_city_button);
        select_date_button = (Button) getView().findViewById(R.id.select_date_button);
        select_startTime_btn = (Button) getView().findViewById(R.id.select_startTime_btn);
        select_endTime_btn = (Button) getView().findViewById(R.id.select_endTime_btn);
        ground_type_btn = (Button) getView().findViewById(R.id.choose_playground_button);
        choose_Player_numbers_button = (Button) getView().findViewById(R.id.choose_Player_numbers_button);
        choose_5admat_btn = (Button) getView().findViewById(R.id.choose_5admat_btn);
    }

    public void English() {
        select_city_button.setText(R.string.chooseEn);
        select_date_button.setText(R.string.chooseEn);
        select_startTime_btn.setText(R.string.chooseEn);
        select_endTime_btn.setText(R.string.chooseEn);
        ground_type_btn.setText(R.string.chooseEn);
        choose_Player_numbers_button.setText(R.string.chooseEn);
        choose_5admat_btn.setText(R.string.chooseEn);
        from_time_prompt_fragment_register_fround.setText(R.string.from);
        to_time_header_fragment_register_fround.setText(R.string.to);
        date_tv.setText(R.string.Date1);
        city_tv.setText(R.string.city);
        playground_type_header_fragment_search_training.setText(R.string.playgroundtype);
        players_number_header_fragment_search_training.setText(R.string.playgroundtype);
        facilities_header_fragment_search_training.setText(R.string.services);
        btn_fragment_search_training.setText(R.string.search);
        LinearLayout.LayoutParams para = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        para.gravity = Gravity.CENTER | Gravity.LEFT;
        from_time_icon_fragment_register_fround.setLayoutParams(para);
        ic_date.setLayoutParams(para);
        ic_city.setLayoutParams(para);
        playground_type_icon_fragment_search_training.setLayoutParams(para);
        players_number_icon_fragment_search_training.setLayoutParams(para);
        facilities_icon_fragment_search_training.setLayoutParams(para);
        from_time_prompt_fragment_register_fround.setLayoutParams(para);
        date_tv.setLayoutParams(para);
        city_tv.setLayoutParams(para);
        playground_type_header_fragment_search_training.setLayoutParams(para);
        players_number_header_fragment_search_training.setLayoutParams(para);
        facilities_header_fragment_search_training.setLayoutParams(para);
    }

    public void setFonts() {
        playground_type_icon_fragment_search_training.setTypeface(customFontAwesome);
        playground_type_header_fragment_search_training.setTypeface(custom2);
        players_number_icon_fragment_search_training.setTypeface(customFontAwesome);
        players_number_header_fragment_search_training.setTypeface(custom2);
        date_tv.setTypeface(custom2);
        ic_date.setTypeface(customFontAwesome);
        facilities_icon_fragment_search_training.setTypeface(customFontAwesome);
        facilities_header_fragment_search_training.setTypeface(custom2);
        btn_fragment_search_training.setTypeface(custom2);
        from_time_icon_fragment_register_fround.setTypeface(customFontAwesome);
        from_time_prompt_fragment_register_fround.setTypeface(custom2);
        to_time_icon_fragment_register_fround.setTypeface(customFontAwesome);
        to_time_header_fragment_register_fround.setTypeface(custom2);
        select_date_button.setTypeface(custom2);
        select_startTime_btn.setTypeface(custom2);
        select_endTime_btn.setTypeface(custom2);
        ic_city.setTypeface(customFontAwesome);
        city_tv.setTypeface(custom2);
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
        title.setTypeface(custom2);

        dialog.setCustomTitle(title);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.choice_item, R.id.text_view_choice, s) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(R.id.text_view_choice);
                text.setTypeface(custom2);
                return view;
            }
        };
        dialog.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (button_num == 1)
                    ground_type_btn.setText(s[which]);
                if (button_num == 2)
                    choose_Player_numbers_button.setText(s[which]);
                if (button_num == 3)
                    choose_5admat_btn.setText(s[which]);
                if (button_num == 4)
                    select_city_button.setText(s[which]);
            }
        });


        AlertDialog alert = dialog.create();
        alert.show();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        selected_date = "";
        selected_start_time = "";
        selected_end_time = "";
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date;
        if ((monthOfYear + 1) > 9)
            date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        else date = year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth;

        select_date_button.setText(date);
        selected_date = date;
        int mon = monthOfYear+1;
        rawDate = dayOfMonth+"/"+mon+"/"+year;
    }
    String rawDate;
    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

        String time = String.valueOf(hourOfDay);
        if (view.getTag().equals("start_time")) {
            if (Integer.parseInt(time) > 12) {
                int _24format = Integer.parseInt(time);
                int _12format = _24format - 12;
                select_startTime_btn.setText(_12format + "PM");
            }   else if (Integer.parseInt(time) == 12)
                select_startTime_btn.setText(time + "PM");
            else select_startTime_btn.setText(time + "AM");

            selected_start_time = time;
            if  (hourOfDay == 0){
                select_startTime_btn.setText("12AM");
                selected_start_time = "0";
            }
        }
        if (view.getTag().equals("end_time")) {
            if (Integer.parseInt(time) > 12) {
                int _24format = Integer.parseInt(time);
                int _12format = _24format - 12;
                select_endTime_btn.setText(_12format + "PM");
            } else if (Integer.parseInt(time) == 12)
                select_endTime_btn.setText(time + "PM");
            else select_endTime_btn.setText(time + "AM");

            selected_end_time = time;
            if  (hourOfDay == 0){
                select_endTime_btn.setText("12AM");
                selected_end_time = "0";
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("select_city_button", select_city_button.getText().toString());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            String checked = savedInstanceState.getString("select_city_button", " ");
            select_city_button.setText(checked);
        }
    }

}
