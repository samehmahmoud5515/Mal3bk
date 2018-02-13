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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
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
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.logicdesigns.mohammed.mal3bklast.ExceptionHandling.ExceptionHandling;
import com.logicdesigns.mohammed.mal3bklast.Interface.OnSwipeListener;
import com.logicdesigns.mohammed.mal3bklast.JSON_PARSER.Cities_Parser;
import com.logicdesigns.mohammed.mal3bklast.JSON_PARSER.Player_Properties;
import com.logicdesigns.mohammed.mal3bklast.Models.City;
import com.logicdesigns.mohammed.mal3bklast.Models.PlayerPositions;
import com.logicdesigns.mohammed.mal3bklast.SharedPref.PrefManager;
import com.logicdesigns.mohammed.mal3bklast.adapters.DialogRatingAdapter;
import com.logicdesigns.mohammed.mal3bklast.common.FragmentsTags;
import com.logicdesigns.mohammed.mal3bklast.common.URLs;
import com.logicdesigns.mohammed.mal3bklast.header.HeaderFragment_Inner;
import com.logicdesigns.mohammed.mal3bklast.pager.ViewPagerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import BusinessClasses.FormationData;
import BusinessClasses.ListViewAsapterForFormation;
import BusinessClasses.ListViewAsapterSearchAboutPlayer;
import BusinessClasses.OnSwipeTouchListener.OnSwipeTouchListener;
import BusinessClasses.RegisterPlaygroundData;
import BusinessClasses.User;
import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;


import static com.facebook.FacebookSdk.getApplicationContext;

public class SearchAboutPlayerFragment extends Fragment {
    ListView userList;
    ListViewAsapterSearchAboutPlayer userAdapter;
    ArrayList<FormationData> FormationDatas = new ArrayList<FormationData>();
    TextView position_icon_fragment_search_about_player, position_header_fragment_search_about_player, location_icon_fragment_search_about_player, location_header_fragment_search_about_player, rate_icon_fragment_search_about_player, rate_header_fragment_search_about_player, listView_header_fragment_search_about_player;
    Button btn_fragment_search_about_player;
    //TextView filter;
    ArrayList<City> cityArrayList = new ArrayList<City>();
    ArrayList<PlayerPositions> playerPositionsArrayList = new ArrayList<>();
    ACProgressFlower flower_dialog;
    //  Spinner position_spinner_fragment_search_about_player;
    //   Spinner location_spinner_fragment_search_about_player,rate_spinner_fragment_search_about_player;
    OnSwipeListener onSwipeListener;
    Typeface custom_font_Awesome;
    Typeface custom_Din;
    Button position_btn_fragment_search_about_player, location_btn_fragment_search_about_player, rate_btn_fragment_search_about_player;
    String choose = null;
    LinearLayout searchPlayerLayout;
    SharedPreferences sharedPref;
    String language = null;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        language = sharedPref.getString("language", "ar");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_about_player, container, false);
        searchPlayerLayout = (LinearLayout) view.findViewById(R.id.searchPlayerLayout);
        if (language.equals("en")) {
//            TextView title_header_fragment = (TextView) getActivity().findViewById(R.id.title_header_fragment);
//            title_header_fragment.setText("Search about Player");
            custom_font_Awesome = Typeface.createFromAsset(getActivity().getAssets(), "fonts/fontawesome-webfont.ttf");
            custom_Din = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Light.ttf");
            searchPlayerLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        } else {
            custom_font_Awesome = Typeface.createFromAsset(getActivity().getAssets(), "fonts/fontawesome-webfont.ttf");
            custom_Din = Typeface.createFromAsset(getActivity().getAssets(), "fonts/DIN Next LT W23 Regular.ttf");
//            TextView title_header_fragment = (TextView) getActivity().findViewById(R.id.title_header_fragment);
//            title_header_fragment.setText("البحث عن لاعب");
        }

        position_btn_fragment_search_about_player = (Button) view.findViewById(R.id.position_btn_fragment_search_about_player);
        position_btn_fragment_search_about_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  String[] positions = new String[]{"CB", "SW", "FB", "RB", "LB", "WB", "RWB", "LWB", "CM", "DM", "AM", "RW", "LW", "RM", "LM", "CF", "ST", "SS"};
                if (language.equals("en")) {
                    drawDialog("Positions", positions, 1);
                } else {
                    drawDialog("المركز", positions, 1);
                }*/

                try {
                    PrefManager prefManager = new PrefManager(getActivity());
                    String postions = prefManager.getPlayers_Postions();
                    PlayerPositions playerPositions = new PlayerPositions();
                    playerPositionsArrayList = playerPositions.parsePlayerPostions(postions,getActivity());
                    String[] postions_arr = new String[playerPositionsArrayList.size()+1];
                    for (int i = 1; i <= playerPositionsArrayList.size(); i++) {
                        postions_arr[i] = playerPositionsArrayList.get(i-1).getPostionName();
                    }
                    if (language.equals("en")) {
                        postions_arr[0] = "All";
                        drawDialog("Position", postions_arr, 1);
                    } else {
                        postions_arr[0] = "الكل";

                        drawDialog("المركز", postions_arr, 1);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
        location_btn_fragment_search_about_player = (Button) view.findViewById(R.id.location_btn_fragment_search_about_player);
        location_btn_fragment_search_about_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    String[] location = new String[]{"cairo","alex","sharqia"};
                PrefManager prefManager = new PrefManager(getActivity());
                String cities = prefManager.getCITIES();
                Cities_Parser parser = new Cities_Parser(getActivity());
                cityArrayList = parser.parse(cities);
                String[] city_arr = new String[cityArrayList.size()+1];
                for (int i = 1; i <= cityArrayList.size(); i++) {
                    city_arr[i] = cityArrayList.get(i-1).getCityName();
                }
                if (language.equals("en")) {
                    city_arr[0] = "All";
                    drawDialog("City", city_arr, 2);
                } else {
                    city_arr[0] = "الكل";

                    drawDialog("الموقع", city_arr, 2);
                }
            }
        });
        rate_btn_fragment_search_about_player = (Button) view.findViewById(R.id
                .rate_btn_fragment_search_about_player);
        rate_btn_fragment_search_about_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] rate = new String[]{"1", "2", "3", "4", "5","6"};
                if (language.equals("en")) {
                    drawDialogRating("Rate", rate, 3);
                } else {
                    drawDialogRating("التقيم", rate, 3);
                }
            }
        });
        rate_btn_fragment_search_about_player.setTypeface(custom_Din);
        location_btn_fragment_search_about_player.setTypeface(custom_Din);
        position_btn_fragment_search_about_player.setTypeface(custom_Din);

       /* position_btn_fragment_search_about_player.setEnabled(true);
        location_btn_fragment_search_about_player.setEnabled(true);
        rate_btn_fragment_search_about_player.setEnabled(true);
        btn_fragment_search_about_player.setEnabled(true);*/
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
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onStart() {
        super.onStart();
        setIDs();
        setFonts();
        initializeItemsWithData();
        //  TextView title_header_fragment= (TextView) getActivity().findViewById(R.id.title_header_fragment);
        //   title_header_fragment.setText("البحث عن لاعب");
        //   filter.setTextColor(Color.parseColor("#dddddd"));


        FormationData formationData = new FormationData();
        if (PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("langugae", "").equals("en")) {

            formationData.setPlayerName("Ramdan Sobhy");
        } else {
            // loading players on start  (La3bin motwafrien)
// Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            Gson gson = new Gson();
            String json1 = mPrefs.getString("userObject", "");
            User obj = gson.fromJson(json1, User.class);


//            flower_dialog1 = new ACProgressFlower.Builder(getActivity())
//                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
//                    .themeColor(Color.WHITE)
//                    .fadeColor(Color.GREEN).build();
//            flower_dialog1.show();
            String url = "https://logic-host.com/work/kora/beta/phpFiles/getAvailablePlayerAPI.php"
                    + "?key=logic123" + "&limit=5"+"&id="+obj.getId();
            // + "&cityId=" + String.valueOf(obj.getCityId()) ;
            Log.d("u1111", url);
// Request a string response from the provided URL.
            try {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                //mTextView.setText("Response is: "+ response.substring(0,500));
                                //   flower_dialog1.dismiss();
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (Integer.parseInt(jsonObject.getString("success")) == 1) {
                                        FormationDatas = new ArrayList<>();
                                        Player_Properties player_properties = new Player_Properties(getActivity());
                                        ArrayList<Player_Properties> playersList = new ArrayList<>();
                                        try {
                                            playersList = player_properties.ParsePlayer(response);
                                            for (Player_Properties player : playersList) {
                                                FormationData data = new FormationData();
                                                data.setPlayerName(player.getName());
                                                data.setPlayerPhoto(player.getImage());
                                                data.setRatingBar(player.getStars());
                                                data.setRatingText(player.getPosition());
                                                data.setId(player.getId());

                                                FormationDatas.add(data);

                                            }
                                            try {
                                                userAdapter = new ListViewAsapterSearchAboutPlayer(getActivity(), R.layout.formation_child,
                                                        FormationDatas);
                                                userList = (ListView) getView().findViewById(R.id.search_about_player_list);
                                                userList.setItemsCanFocus(false);
                                                userList.setAdapter(userAdapter);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        listView_header_fragment_search_about_player = (TextView) getActivity().findViewById(R.id.listView_header_fragment_search_about_player);
                                        listView_header_fragment_search_about_player.setVisibility(View.GONE);
                                        userList = (ListView) getView().findViewById(R.id.search_about_player_list);
                                        userList.setVisibility(View.GONE);
                                     //   ExceptionHandling exceptionHandling = new ExceptionHandling(getActivity());

                                       // exceptionHandling.showErrorDialog();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                  //  ExceptionHandling exceptionHandling = new ExceptionHandling(getActivity());

                                   // exceptionHandling.showErrorDialog();

                                }


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // mTextView.setText("That didn't work!");
                    }
                });
// Add the request to the RequestQueue.
                queue.add(stringRequest);
            }catch (Exception e)
            {
                e.printStackTrace();
            }


            // formationData.setPlayerName("رمضان صبحي");
        }


        flower_dialog = new ACProgressFlower.Builder(getActivity())
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.GREEN).build();


        if (PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("langugae", "").equals("en")) {
            userAdapter = new ListViewAsapterSearchAboutPlayer(getActivity(), R.layout.formation_child_en,
                    FormationDatas);
        } else {
//            userAdapter = new ListViewAsapterSearchAboutPlayer(getActivity(), R.layout.formation_child,
//                    FormationDatas);
        }

//        userList = (ListView) getView().findViewById(R.id.search_about_player_list);
//        userList.setItemsCanFocus(false);
//        userList.setAdapter(userAdapter);

        if (language.equals("en")) {
            choose = "Choose";
        } else {
            choose = "اختار";
        }

        btn_fragment_search_about_player.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (location_btn_fragment_search_about_player.getText().toString().equals(choose)
                        ||  position_btn_fragment_search_about_player.getText().toString().equals(choose)
                        ||rate_btn_fragment_search_about_player.getText().toString().equals(choose)) {
                    if (language.equals("en")) {
                        Toast.makeText(getActivity(), R.string.fields, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "ادخل جميع البيانات المطلوبة", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    //if (rate_btn_fragment_search_about_player.getText().toString().equals(choose))
                      //  rate_btn_fragment_search_about_player.setText("0");
                    flower_dialog.show();

                    String all;
                    if (language.equals("en")) {
                        all = "All";
                    } else {
                        all = "الكل";
                    }
                    String cityName = location_btn_fragment_search_about_player
                            .getText().toString();
                    String cityId;
                    City city = new City();
                    if (cityName.equals(all))
                        cityId = "-1";
                    else
                     cityId = city.getCityIdbyName(cityArrayList, cityName);
                    PlayerPositions pos =new PlayerPositions();
                    String posName = position_btn_fragment_search_about_player.getText().toString();
                    String posId;
                    if (posName.equals(all))
                        posId = "-1";
                    else
                     posId = pos.getPostionIdbyName(playerPositionsArrayList,posName);
                    // Instantiate the RequestQueue.
                    SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    Gson gson = new Gson();
                    String json1 = mPrefs.getString("userObject", "");
                    User obj = gson.fromJson(json1, User.class);
                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    String rateVal = rate_btn_fragment_search_about_player.getText().toString();
                    if (rateVal.equals(all))
                    {
                        rateVal = "0";
                    }
                    final String url = URLs.Search_Player_URL + "?key=logic123" + "&city="
                            + cityId + "&stars=" + rate_btn_fragment_search_about_player.getText().toString()
                            + "&position=" + posId + "&page=1"+"&myId="+obj.getId();

                    final String sent_url = URLs.Search_Player_URL + "?key=logic123" + "&city="
                            + cityId + "&stars=" + rateVal
                            + "&position=" + posId+"&myId="+obj.getId();

                Log.d("sentURL ",sent_url);
                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        JSONObject object = new JSONObject(response);
                                        if (Integer.parseInt(object.getString("success")) == 1) {
                                            //sameh
                                         //   position_btn_fragment_search_about_player.setEnabled(false);
                                        //    location_btn_fragment_search_about_player.setEnabled(false);
                                           // rate_btn_fragment_search_about_player.setEnabled(false);
                                          //  btn_fragment_search_about_player.setEnabled(false);

                                            Bundle bundle = new Bundle();
                                            flower_dialog.dismiss();
                                            bundle.putString("playersJson", response);

                                            bundle.putString("url", sent_url);
                                            Fragment getPlayerFragment = new GetPlayerFragment();
                                            getPlayerFragment.setArguments(bundle);
                                            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                            ft.replace(R.id.search_about_player_fragment, getPlayerFragment).addToBackStack(null).commit();
                                            FragmentTransaction ft1 = getActivity().getSupportFragmentManager().beginTransaction();
                                            ft1.replace(R.id.placeholder1, new HeaderFragment_Inner())
                                                    .commit();
                                            MainContainer.headerNum = 3;
                                        } else if (Integer.parseInt(object.getString("success")) == 0) {// No Players after search
                                            flower_dialog.dismiss();
//
//
//                                                alertDialog.show();
                                            final Activity mActivity = getActivity();

                                            final Dialog dialog = new Dialog(mActivity, R.style.CustomDialogTheme);
                                            dialog.setContentView(R.layout.no_playgrounds_custom_dialog);

                                            dialog.setCancelable(true);
                                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                            TextView no_message_text = (TextView) dialog.findViewById(R.id.no_message_text);
                                            no_message_text.setTypeface(custom_Din);


                                            Button close_button = (Button) dialog.findViewById(R.id.btn_close_no_playground);
                                            close_button.setTypeface(custom_Din);

                                            if (language.equals("en")) {
                                                close_button.setText(R.string.close);
                                                no_message_text.setText(R.string.noPlayers);
                                            } else {
                                                no_message_text.setText(mActivity.getResources().getString(R.string.No_Players_found));
                                            }
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
                                      //  ExceptionHandling exceptionHandling = new ExceptionHandling(getActivity());
                                     //   exceptionHandling.showErrorDialog();
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
            }
        });


       /*  FragmentManager fm = getActivity().getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount()-1; ++i) {
            fm.popBackStack();
        }*/
        if (language.equals("en")) {
            English();
        }

    }

    public void English() {
        position_header_fragment_search_about_player.setText(R.string.Position);
        position_btn_fragment_search_about_player.setText(R.string.chooseEn);
        location_btn_fragment_search_about_player.setText(R.string.chooseEn);
        rate_btn_fragment_search_about_player.setText(R.string.chooseEn);
        btn_fragment_search_about_player.setText(R.string.search);
        location_header_fragment_search_about_player.setText(R.string.city);
        rate_header_fragment_search_about_player.setText(R.string.Rating);
        listView_header_fragment_search_about_player.setText(R.string.playerAvailable);
        LinearLayout.LayoutParams para=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT );
        para.gravity = Gravity.LEFT | Gravity.CENTER;
        position_header_fragment_search_about_player.setLayoutParams(para);
        location_header_fragment_search_about_player.setLayoutParams(para);
                rate_header_fragment_search_about_player.setLayoutParams(para);
    }

    public void setIDs() {
        // filter=(TextView) getActivity().findViewById(R.id.filter_header_fragment);
        position_icon_fragment_search_about_player = (TextView) getActivity().findViewById(R.id.position_icon_fragment_search_about_player);
        position_header_fragment_search_about_player = (TextView) getActivity().findViewById(R.id.position_header_fragment_search_about_player);
        location_icon_fragment_search_about_player = (TextView) getActivity().findViewById(R.id.location_icon_fragment_search_about_player);
        location_header_fragment_search_about_player = (TextView) getActivity().findViewById(R.id.location_header_fragment_search_about_player);
        rate_icon_fragment_search_about_player = (TextView) getActivity().findViewById(R.id.rate_icon_fragment_search_about_player);
        rate_header_fragment_search_about_player = (TextView) getActivity().findViewById(R.id.rate_header_fragment_search_about_player);
        listView_header_fragment_search_about_player = (TextView) getActivity().findViewById(R.id.listView_header_fragment_search_about_player);
        btn_fragment_search_about_player = (Button) getActivity().findViewById(R.id.btn_fragment_search_about_player);
        //  position_spinner_fragment_search_about_player=(Spinner) getActivity().findViewById(R.id.position_spinner_fragment_search_about_player);
        //  location_spinner_fragment_search_about_player=(Spinner) getActivity().findViewById(R.id.location_spinner_fragment_search_about_player);
        //  rate_spinner_fragment_search_about_player=(Spinner) getActivity().findViewById(R.id.rate_spinner_fragment_search_about_player);
    }

    public void setFonts() {
        position_icon_fragment_search_about_player.setTypeface(custom_font_Awesome);
        position_header_fragment_search_about_player.setTypeface(custom_Din);
        location_icon_fragment_search_about_player.setTypeface(custom_font_Awesome);
        location_header_fragment_search_about_player.setTypeface(custom_Din);
        rate_icon_fragment_search_about_player.setTypeface(custom_font_Awesome);
        rate_header_fragment_search_about_player.setTypeface(custom_Din);
        listView_header_fragment_search_about_player.setTypeface(custom_Din);
        btn_fragment_search_about_player.setTypeface(custom_Din);
        position_header_fragment_search_about_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position_btn_fragment_search_about_player.performClick();
            }
        });
        location_header_fragment_search_about_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location_btn_fragment_search_about_player.performClick();
            }
        });
        rate_header_fragment_search_about_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rate_btn_fragment_search_about_player.performClick();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
       // position_btn_fragment_search_about_player.setEnabled(true);
     //   location_btn_fragment_search_about_player.setEnabled(true);
     //   rate_btn_fragment_search_about_player.setEnabled(true);
     //   btn_fragment_search_about_player.setEnabled(true);
    }


    public void initializeItemsWithData() {
        String[] positions = new String[]{"CB", "SW", "FB", "RB", "LB", "WB", "RWB", "LWB", "CM", "DM", "AM", "RW", "LW", "RM", "LM", "CF", "ST", "SS"};
        ArrayAdapter<String> arrayAdapterForPositions = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, positions);
        arrayAdapterForPositions.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //    position_spinner_fragment_search_about_player.setAdapter(arrayAdapterForPositions);
        String[] location = new String[]{"cairo", "alex", "sharqia"};
        ArrayAdapter<String> arrayAdapterForLocations = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, location);
        arrayAdapterForLocations.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //  location_spinner_fragment_search_about_player.setAdapter(arrayAdapterForLocations);
        String[] rate = new String[]{".5", "1", "1.5", "2", "2.5", "3", "3.5", "4", "4.5", "5"};
        ArrayAdapter<String> arrayAdapterForRate = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_spinner_dropdown_item, rate);
        arrayAdapterForRate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //    rate_spinner_fragment_search_about_player.setAdapter(arrayAdapterForRate);
    }

    void drawDialog(String Tiltle, final String s[], final int button_num) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.CustomDialogTheme));
        TextView title = new TextView(getActivity());

        title.setText(Tiltle);
        title.setBackgroundColor(Color.parseColor("#008542"));
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(20);
        title.setTypeface(custom_Din);

        dialog.setCustomTitle(title);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.choice_item, R.id.text_view_choice, s) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(R.id.text_view_choice);
                text.setTypeface(custom_Din);
                return view;
            }
        };
        dialog.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (button_num == 1)
                    position_btn_fragment_search_about_player.setText(s[which]);
                if (button_num == 2)
                    location_btn_fragment_search_about_player.setText(s[which]);
                if (button_num == 3)
                    rate_btn_fragment_search_about_player.setText(s[which]);
            }
        });


        AlertDialog alert = dialog.create();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        alert.show();
    }

    public void drawDialogRating(String Tiltle, final String s[], final int button_num) {
        @SuppressLint("RestrictedApi") AlertDialog.Builder dialog = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.CustomDialogTheme));
        TextView title = new TextView(getActivity());

        title.setText(Tiltle);
        title.setBackgroundColor(Color.parseColor("#008542"));
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(20);
        title.setTypeface(custom_Din);

        dialog.setCustomTitle(title);

        DialogRatingAdapter dialogRatingAdapter = new DialogRatingAdapter(getActivity(), s,language);
        dialog.setAdapter(dialogRatingAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

//       handle clicks at adapter
                if (s[which] .equals("6")) {
                    if (language.equals("ar"))
                    rate_btn_fragment_search_about_player.setText("الكل");
                    else rate_btn_fragment_search_about_player.setText("All");
                }
                else
                rate_btn_fragment_search_about_player.setText(s[which]);

            }
        });

        AlertDialog alert = dialog.create();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        alert.show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
