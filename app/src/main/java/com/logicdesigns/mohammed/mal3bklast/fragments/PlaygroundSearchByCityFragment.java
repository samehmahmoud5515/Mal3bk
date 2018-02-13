package com.logicdesigns.mohammed.mal3bklast.fragments;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.logicdesigns.mohammed.mal3bklast.ExceptionHandling.ExceptionHandling;
import com.logicdesigns.mohammed.mal3bklast.JSON_PARSER.Cities_Parser;
import com.logicdesigns.mohammed.mal3bklast.JSON_PARSER.Playground_Search_reservation;
import com.logicdesigns.mohammed.mal3bklast.MainContainer;
import com.logicdesigns.mohammed.mal3bklast.Models.City;
import com.logicdesigns.mohammed.mal3bklast.R;
import com.logicdesigns.mohammed.mal3bklast.SharedPref.PrefManager;
import com.logicdesigns.mohammed.mal3bklast.appFonts.AppFonts;
import com.logicdesigns.mohammed.mal3bklast.common.FragmentsTags;
import com.logicdesigns.mohammed.mal3bklast.common.URLs;
import com.logicdesigns.mohammed.mal3bklast.header.HeaderFragment_Inner;
import com.logicdesigns.mohammed.mal3bklast.utility.TimeComparing;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlaygroundSearchByCityFragment extends Fragment {

    Button simple_search_btn, select_town_resv_btn_simple;
    TextView cityText_simple, city_ic_simple;
    String language="";
    ArrayList<City> cityArrayList;
    LinearLayout cityLinearLayout_simple;
    PrefManager prefManager ;
    public PlaygroundSearchByCityFragment() {
        // Required empty public constructor
    }
    Typeface font;
    String allText,chooseText ,errorMessageText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_playground_search_by_city, container, false);
        prefManager = new PrefManager(getActivity());
        initilizeViews( view);
        setFonts();
        setTextinLanguageChange();
        setCities();
        search();


        return  view;
    }


    void initilizeViews(View view)
    {
        simple_search_btn = (Button) view.findViewById(R.id.simple_search_btn);
        select_town_resv_btn_simple = (Button) view.findViewById(R.id.select_town_resv_btn_simple);
        cityText_simple = (TextView) view.findViewById(R.id.cityText_simple);
        city_ic_simple = (TextView) view.findViewById(R.id.city_ic_simple);
        cityLinearLayout_simple = (LinearLayout) view.findViewById(R.id.cityLinearLayout_simple);
    }
    void setFonts()
    {

        AppFonts appFonts = AppFonts.getInstance(getActivity());
        if (prefManager.getLanguage().equals("ar")) {
            font = appFonts.getDIN_NEXT();
            allText = "الكل";
            chooseText = "اختار";
            errorMessageText= "اختار المدينة اولا";
        }
        else {
            font = appFonts.getOpenSans();
            allText = "All";
            chooseText = "Choose";
            errorMessageText = "Choose City first";
        }
        select_town_resv_btn_simple.setText(chooseText);
        simple_search_btn.setTypeface(appFonts.getDIN_NEXT());
        select_town_resv_btn_simple.setTypeface(appFonts.getDIN_NEXT());
        cityText_simple.setTypeface(appFonts.getDIN_NEXT());
        city_ic_simple.setTypeface(appFonts.getFontawsome());

    }
    void setTextinLanguageChange()
    {
        language = prefManager.getLanguage();
        if (prefManager.getLanguage().equals("en"))
        {
            city_ic_simple.setTypeface(AppFonts.getInstance(getActivity()).getFontawsome());
            simple_search_btn.setText("Search");
            select_town_resv_btn_simple.setText("Choose");
            cityText_simple.setText("City");
            cityLinearLayout_simple.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            cityText_simple.setGravity(Gravity.LEFT|Gravity.CENTER);
        }
        else {
            cityText_simple.setGravity(Gravity.RIGHT|Gravity.CENTER);
        }

    }

    void setCities()
    {
        select_town_resv_btn_simple.setOnClickListener(new View.OnClickListener() {
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
        title.setTypeface(font);

        dialog.setCustomTitle(title);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.choice_item, R.id.text_view_choice, s) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(R.id.text_view_choice);
                text.setTypeface(font);
                return view;
            }
        };
        dialog.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (button_num == 1)
                    select_town_resv_btn_simple.setText(s[which]);
            }
        });


        AlertDialog alert = dialog.create();

        alert.show();
    }
    ACProgressFlower flower_dialog ;


    void search()
    {
        simple_search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select_town_resv_btn_simple.getText().equals(chooseText))
                {
                    Toast.makeText(getActivity(), errorMessageText, Toast.LENGTH_SHORT).show();
                    return;
                }
                TimeComparing timeComparing = new TimeComparing();

                City city = new City();
                String cityId;
                if (!select_town_resv_btn_simple.getText().toString().equals(allText))
                    cityId = city.getCityIdbyName(cityArrayList,
                            select_town_resv_btn_simple.getText().toString());
                else cityId = "-1";

                flower_dialog = new ACProgressFlower.Builder(getActivity())
                                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                                .themeColor(Color.WHITE)
                                .fadeColor(Color.GREEN).build();
                        flower_dialog.show();

                        RequestQueue queue = Volley.newRequestQueue(getActivity());
                        String url = URLs.Search_Playground_by_city+cityId;


                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {

                                        JSONObject json = null;
                                        try {
                                            json = new JSONObject(response);
                                            int success = json.getInt("success");
                                            if (success == 0) {
                                                flower_dialog.dismiss();

                                                final Activity mActivity = getActivity();

                                                final Dialog dialog = new Dialog(mActivity, R.style.CustomDialogTheme);
                                                dialog.setContentView(R.layout.no_playgrounds_custom_dialog);
                                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                                dialog.setCancelable(true);
                                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                                                TextView no_message_text = (TextView) dialog.findViewById(R.id.no_message_text);
                                                no_message_text.setTypeface(font);

                                                Button close_button = (Button) dialog.findViewById(R.id.btn_close_no_playground);
                                                close_button.setTypeface(font);

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
                                                Playground_Search_reservation playgroundSearchReservation = new Playground_Search_reservation(getActivity(), "13", "15");

                                                ArrayList<Playground_Search_reservation> playgroundsList =
                                                        playgroundSearchReservation.Parse(response,false);
                                                if (playgroundsList == null) {
                                                    Typeface custom_DIN_Next = Typeface.createFromAsset(getActivity().getAssets(), "fonts/DIN Next LT W23 Regular.ttf");
                                                    final Dialog dialog = new Dialog(getActivity(), R.style.CustomDialogTheme);
                                                    dialog.setContentView(R.layout.no_playgrounds_custom_dialog);
                                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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

                                                    Fragment viewResultPlaygroundFragment = new ChoosePlaygroudFragment();
                                                    Bundle args = new Bundle();
                                                    args.putString(FragmentsTags.playgroundJsonResults, response);
                                                   // args.putString("start_time", "13");
                                                  //  args.putString("end_time", "15");
                                                    viewResultPlaygroundFragment.setArguments(args);

                                                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                                    ft.add(R.id.frame_container, viewResultPlaygroundFragment).addToBackStack("PlaygroundSearchByCityFragment")
                                                            .commit();
                                                    MainContainer.headerNum = 2;

                                                    FragmentTransaction ft1 = getActivity().getSupportFragmentManager().beginTransaction();
                                                    ft1.replace(R.id.placeholder1, new HeaderFragment_Inner())
                                                            .commit();

                                                    flower_dialog.dismiss();
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




        });
    }


}
