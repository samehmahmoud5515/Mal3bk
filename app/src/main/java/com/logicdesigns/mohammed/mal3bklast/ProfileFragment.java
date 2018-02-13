package com.logicdesigns.mohammed.mal3bklast;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.gson.Gson;
import com.logicdesigns.mohammed.mal3bklast.Interface.OnSwipeListener;
import com.logicdesigns.mohammed.mal3bklast.JSON_PARSER.Cities_Parser;
import com.logicdesigns.mohammed.mal3bklast.Models.City;
import com.logicdesigns.mohammed.mal3bklast.SharedPref.PrefManager;
import com.logicdesigns.mohammed.mal3bklast.common.FragmentsTags;
import com.logicdesigns.mohammed.mal3bklast.common.SampleRecyclerAdapter;
import com.logicdesigns.mohammed.mal3bklast.common.URLs;
import com.logicdesigns.mohammed.mal3bklast.player.PlayerStoredData;
import com.logicdesigns.mohammed.mal3bklast.tasks.ProfileTasks;
import com.logicdesigns.mohammed.mal3bklast.tasks.RefreshUserInforamtion;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.SimpleMonthAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import BusinessClasses.JSONParser;
import BusinessClasses.MembersChildData;
import BusinessClasses.User;
import de.hdodenhof.circleimageview.CircleImageView;



public class ProfileFragment extends Fragment {
    ArrayList<MembersChildData> listitems = new ArrayList<>();
    RecyclerView MyRecyclerView;
    LinearLayout ll;
    User user;
    Double speed;
    Double shooting;
    Double skill;
    Typeface dinNext, fontAwesome;
    ProgressBar player_speed_progressBar_fragment_profile, player_skill_progressBar_fragment_profile, player_shooting_progressBar_fragment_profile;
    JSONParser jsonParser;
    TextView city_icon, city_tv;
    Button city_btn, search_teams_btn;
    OnSwipeListener onSwipeListener;
    //  ACProgressFlower flower_dialog;
    ArrayList<City> cityArrayList;
    Button edit_data_fragment_profile, team_btn_fragment_profile, btn_submit_fragment_profile;
    TextView statisitics_icon_fragment_profile, matches_win_icon_fragment_profile, matcches_tie_icon_fragment_profile, matches_lost_icon_fragment_profile, members_icon_fragment_profile;
    TextView statisitics_fragment_profile, matches_win_header_fragment_profile, matches_tie_header_fragment_profile, matches_lost_header_fragment_profile, members_icon_header_fragment_profile, player_speed_value_fragment_profile, player_skill_value_fragment_profile, player_shooting_value_fragment_profile;
    Button logoutHeader;
    SharedPreferences sharedPref;
    String language = null;
    String choose = null, all = null;
    int isCaptian=0;
    com.github.clans.fab.FloatingActionButton fab_addTeam, fab_teammatches
            ,fab_messages,fab_comingmatches,fab_historymatches,fab_addPlayground;
    int user_Id;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        language = sharedPref.getString("language", "ar");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        team_btn_fragment_profile = (Button) view.findViewById(R.id.team_btn_fragment_profile);
        edit_data_fragment_profile = (Button) view.findViewById(R.id.edit_data_fragment_profile);
        fab_messages = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.fab_messages);
        fab_comingmatches = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.fab_comingmatches);
        fab_historymatches= (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.fab_historymatches);
        fab_addPlayground =  (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.fab_addPlayground);
        loadProfilePicture();


        if (language.equals("en")) {
            dinNext = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Light.ttf");
            edit_data_fragment_profile.setText("Edit Data");
            fab_messages.setLabelText("Messages");
            fab_comingmatches.setLabelText("Coming Matches");
            fab_historymatches.setLabelText("Pervious Matches");
            fab_addPlayground.setLabelText("Add Playground");
        } else {
            dinNext = Typeface.createFromAsset(getActivity().getAssets(), "fonts/DIN Next LT W23 Regular.ttf");
            fab_messages.setLabelText("الرسائل");
            fab_comingmatches.setLabelText("مبارياتك القادمة");
            fab_historymatches.setLabelText("مبارياتك السابقة");
            fab_addPlayground.setLabelText("اضافة ملعب");
        }
        fontAwesome = Typeface.createFromAsset(getActivity().getAssets(), "fonts/fontawesome-webfont.ttf");
        team_btn_fragment_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Main2Activity.class);
                intent.putExtra(FragmentsTags.FragemntTag, FragmentsTags.MyTeamFragment);
                getActivity().startActivity(intent);
                MainContainer.headerNum = -1;
            }
        });
        fab_addPlayground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Main2Activity.class);
                intent.putExtra(FragmentsTags.FragemntTag, FragmentsTags.Add_Playground);
                getActivity().startActivity(intent);
            }
        });

        edit_data_fragment_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Main2Activity.class);
                intent.putExtra(FragmentsTags.FragemntTag, FragmentsTags.EditMyDataFragment);
                getActivity().startActivity(intent);
                MainContainer.headerNum = -1;
                //  getActivity().finish();
            }
        });

        fab_messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), Main2Activity.class);
                intent.putExtra(FragmentsTags.FragemntTag, FragmentsTags.inboxFragment);
                getActivity().startActivity(intent);
            }
        });
        fab_comingmatches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Main2Activity.class);
                intent.putExtra(FragmentsTags.FragemntTag, FragmentsTags.ComingMatchesFragment);
                getActivity().startActivity(intent);
                MainContainer.headerNum = -1;
            }
        });
        fab_historymatches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Main2Activity.class);
                intent.putExtra(FragmentsTags.FragemntTag, FragmentsTags.MatchHistoryFragment);
                getActivity().startActivity(intent);
                MainContainer.headerNum = -1;
            }
        });
        return view;
    }

    private void setMargins(View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        RefreshUserInforamtion refreshUserInforamtion = new RefreshUserInforamtion(getActivity());
        ll = (LinearLayout) getActivity().findViewById(R.id.profile_photo);
        MyRecyclerView = (RecyclerView) getActivity().findViewById(R.id.membersRecyclerView);
        MyRecyclerView.setAdapter(new SampleRecyclerAdapter());
        fab_addTeam = (com.github.clans.fab.FloatingActionButton) getView().findViewById(R.id.fab_addTeam);
        fab_teammatches  = (com.github.clans.fab.FloatingActionButton) getView().findViewById(R.id.fab_teammatches);

        if (language.equals("ar")) {
            fab_addTeam.setLabelText("فريقك");
            fab_teammatches.setLabelText("مباريات الفريق");
        }
        else {
            fab_addTeam.setLabelText("Your Team");
            fab_teammatches.setLabelText("Team Matches");
        }

        if (language.equals("en")) {
            fab_teammatches.setLabelText("Add Match Competitor");
            com.github.clans.fab.FloatingActionMenu fab_menu = (com.github.clans.fab.FloatingActionMenu)
                    getView().findViewById(R.id.fab_menu);
            LinearLayout linearLayout = (LinearLayout) getView().findViewById(R.id.fab_linearlayout);


            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.BOTTOM | Gravity.END;
            linearLayout.setLayoutParams(params);
        }

        else{
            fab_teammatches.setLabelText("اضافة منافس لمبارياتك");
        }

        fab_teammatches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), Main2Activity.class);
                intent.putExtra(FragmentsTags.FragemntTag, FragmentsTags.TeamComingMatchesFragment);
                getActivity().startActivity(intent);


            }
        });


        SharedPreferences mPrefss = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String json11 = mPrefss.getString("userObject", "");
        Gson gsonn = new Gson();
        final User user_obj = gsonn.fromJson(json11, User.class);
        user_Id = user_obj.getId();

        getMyTeam();



        MyRecyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return null;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            }

            @Override
            public int getItemCount() {
                return 0;
            }

        });

        listitems = new ArrayList<>();
        setIDObjects();
        setFonts();

       /* flower_dialog = new ACProgressFlower.Builder(getActivity())
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.GREEN).build();*/

        btn_submit_fragment_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialog_search_teams);
                city_icon = (TextView) dialog.findViewById(R.id.city_icon);
                city_tv = (TextView) dialog.findViewById(R.id.city_tv);
                city_btn = (Button) dialog.findViewById(R.id.city_btn);
                LinearLayout searchPopup = (LinearLayout) dialog.findViewById(R.id.searchPopup);
                search_teams_btn = (Button) dialog.findViewById(R.id.search_teams_btn);
                search_teams_btn.setTypeface(dinNext);
                city_btn.setTypeface(dinNext);
                city_tv.setTypeface(dinNext);
                city_icon.setTypeface(fontAwesome);
                if (language.equals("en")) {
                    searchPopup.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                    city_tv.setText(R.string.city);
                    city_btn.setText(R.string.chooseEn);
                    search_teams_btn.setText(R.string.search);
                }
                city_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                            drawDialog("City", city_arr, 1);
                            city_arr[0] = "All";
                        } else {
                            drawDialog("الموقع", city_arr, 1);
                            city_arr[0] = "الكل";
                        }
                    }
                });
                search_teams_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (language.equals("en")) {
                            choose = "Choose";
                            all = "All";
                        } else {
                            choose = "اختار";
                            all = "الكل";
                        }
                        if (!city_btn.getText().toString().equals(choose)) {
                            SharedPreferences mPrefs_ = PreferenceManager.getDefaultSharedPreferences(getActivity());
                            final String json1 = mPrefs_.getString("userObject", "");
                            Gson gson = new Gson();
                            final User obj = gson.fromJson(json1, User.class);
                            String uid = String.valueOf(obj.getId());
                            //flower_dialog.show();
                            City city = new City();
                            String cityId;
                            if (!city_btn.getText().toString().equals(all))
                                cityId = city.getCityIdbyName(cityArrayList,
                                        city_btn.getText().toString());
                            else cityId = "-1";
                            // Instantiate the RequestQueue.
                            RequestQueue queue = Volley.newRequestQueue(getActivity());
                            String url = URLs.serarchTeamsByCity + "cityId=" + cityId + "&id=" + uid;


// Request a string response from the provided URL.
                            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject object = new JSONObject(response);
                                                if (Integer.parseInt(object.getString("success")) == 1) {
                                                    dialog.dismiss();
                                                    //   flower_dialog
                                                    //     .dismiss();
                                                    Intent intent = new Intent(getActivity(), Main2Activity.class);
                                                    intent.putExtra(FragmentsTags.FragemntTag, FragmentsTags.TeamsFragment);
                                                    intent.putExtra("TeamsJsonString", response);
                                                    getActivity().startActivity(intent);
                                                    MainContainer.headerNum = -1;
//                                                    Fragment teamsFragment = new TeamsFragment();
//                                                    Bundle bundle = new Bundle();
//                                                    bundle.putString("TeamsJsonString",response);
//                                                    teamsFragment.setArguments(bundle);
//                                                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                                                    ft.replace(R.id.framelayoutProfile, teamsFragment).addToBackStack("TeamsFragment").commit();
                                                }
                                                else{
                                                    final Activity mActivity = getActivity();

                                                    final Dialog dialog = new Dialog(mActivity, R.style.CustomDialogTheme);
                                                    dialog.setContentView(R.layout.no_playgrounds_custom_dialog);
                                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                                    dialog.setCancelable(true);
                                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                                    TextView no_message_text = (TextView) dialog.findViewById(R.id.no_message_text);
                                                    no_message_text.setTypeface(dinNext);

                                                    Button close_button = (Button) dialog.findViewById(R.id.btn_close_no_playground);
                                                    close_button.setTypeface(dinNext);

                                                    if (language.equals("en")) {
                                                        close_button.setText(R.string.close);
                                                        no_message_text.setText(R.string.noStadiums);
                                                        no_message_text.setText("No Teams Found ");
                                                    }
                                                    else{
                                                        no_message_text.setText("لا يوجد فرق");
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
                                Toast.makeText(getActivity(), "Please Choose The City", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "اختار المدينة من فضلك", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });


                dialog.show();

            }
        });

        jsonParser = new JSONParser();


        ///get the data from od user from shared prefrence\
        user = new User();
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        if (mPrefs.getString("userObject", "").length() > 0) {
            Gson gson = new Gson();
            user = gson.fromJson(mPrefs.getString("userObject", ""), User.class);

//            Log.i("gsonnnnnn",user.getId()+" ");
            try {
                callAPI();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        members_icon_header_fragment_profile = (TextView) getActivity().findViewById(R.id.members_icon_header_fragment_profile);
        members_icon_fragment_profile = (TextView) getActivity().findViewById(R.id.members_icon_fragment_profile);

        FrameLayout frameLayout = (FrameLayout) getActivity().findViewById(R.id.framelayoutProfile);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String language = sharedPref.getString("language", "");
        if (language.equals("en")) {
            dinNext = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Light.ttf");
            fontAwesome = Typeface.createFromAsset(getActivity().getAssets(), "fonts/fontawesome-webfont.ttf");
            frameLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            English();
        } else {
            dinNext = Typeface.createFromAsset(getActivity().getAssets(), "fonts/DIN Next LT W23 Regular.ttf");
            fontAwesome = Typeface.createFromAsset(getActivity().getAssets(), "fonts/fontawesome-webfont.ttf");
        }

      /*request_to_Players.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Main2Activity.class);
                intent.putExtra(FragmentsTags.FragemntTag, FragmentsTags.RequestsToPlayersFragment);
                getActivity().startActivity(intent);
                MainContainer.headerNum = -1;
            }
        });*/


    }

    private void English() {
        // edit_data_fragment_profile.setText(R.string.Modify);
        statisitics_fragment_profile.setText(R.string.statistics);
        matches_win_header_fragment_profile.setText(R.string.speed);
        matches_tie_header_fragment_profile.setText(R.string.Skill);
        matches_lost_header_fragment_profile.setText(R.string.shot);
        members_icon_header_fragment_profile.setText(R.string.members);
        btn_submit_fragment_profile.setText(R.string.join);

        statisitics_icon_fragment_profile.setText(fromHtml("&#xf105;"));
        members_icon_fragment_profile.setText(fromHtml("&#xf105;"));
        matcches_tie_icon_fragment_profile.setText(fromHtml("&#xf0a9;"));
        matches_win_icon_fragment_profile.setText(fromHtml("&#xf0a9;"));
        matches_lost_icon_fragment_profile.setText(fromHtml("&#xf0a9;"));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.LEFT | Gravity.CENTER;

        statisitics_fragment_profile.setLayoutParams(params);
        members_icon_header_fragment_profile.setLayoutParams(params);
        matches_win_header_fragment_profile.setLayoutParams(params);
        matches_tie_header_fragment_profile.setLayoutParams(params);
        matches_lost_header_fragment_profile.setLayoutParams(params);
        members_icon_fragment_profile.setLayoutParams(params);
        statisitics_icon_fragment_profile.setLayoutParams(params);
        matcches_tie_icon_fragment_profile.setLayoutParams(params);
        matches_win_icon_fragment_profile.setLayoutParams(params);
        matches_lost_icon_fragment_profile.setLayoutParams(params);
    }


    public void setIDObjects() {
        //set buttons
        edit_data_fragment_profile = (Button) getActivity().findViewById(R.id.edit_data_fragment_profile);
        team_btn_fragment_profile = (Button) getActivity().findViewById(R.id.team_btn_fragment_profile);
        // button10=(Button)getActivity().findViewById(R.id.button10);
        btn_submit_fragment_profile = (Button) getActivity().findViewById(R.id.btn_submit_fragment_profile);
        //set TextView  icons
        statisitics_icon_fragment_profile = (TextView) getActivity().findViewById(R.id.statisitics_icon_fragment_profile);
        matches_win_icon_fragment_profile = (TextView) getActivity().findViewById(R.id.matches_win_icon_fragment_profile);
        matcches_tie_icon_fragment_profile = (TextView) getActivity().findViewById(R.id.matcches_tie_icon_fragment_profile);
        matches_lost_icon_fragment_profile = (TextView) getActivity().findViewById(R.id.matches_lost_icon_fragment_profile);
        members_icon_fragment_profile = (TextView) getActivity().findViewById(R.id.members_icon_fragment_profile);
        //set TextView Headers
        statisitics_fragment_profile = (TextView) getActivity().findViewById(R.id.statisitics_fragment_profile);
        ;
        matches_win_header_fragment_profile = (TextView) getActivity().findViewById(R.id.matches_win_header_fragment_profile);
        matches_tie_header_fragment_profile = (TextView) getActivity().findViewById(R.id.matches_tie_header_fragment_profile);
        matches_lost_header_fragment_profile = (TextView) getActivity().findViewById(R.id.matches_lost_header_fragment_profile);
        members_icon_header_fragment_profile = (TextView) getActivity().findViewById(R.id.members_icon_header_fragment_profile);
        player_speed_value_fragment_profile = (TextView) getActivity().findViewById(R.id.player_speed_value_fragment_profile);
        player_skill_value_fragment_profile = (TextView) getActivity().findViewById(R.id.player_skill_value_fragment_profile);
        player_shooting_value_fragment_profile = (TextView) getActivity().findViewById(R.id.player_shooting_value_fragment_profile);
        player_speed_progressBar_fragment_profile = (ProgressBar) getActivity().findViewById(R.id.player_speed_progressBar_fragment_profile);
        player_skill_progressBar_fragment_profile = (ProgressBar) getActivity().findViewById(R.id.player_skill_progressBar_fragment_profile);
        player_shooting_progressBar_fragment_profile = (ProgressBar) getActivity().findViewById(R.id.player_shooting_progressBar_fragment_profile);
        // request_to_Players = (Button) getActivity().findViewById(R.id.request_to_Players);

    }

    public void setFonts() {

        //set buttons
        edit_data_fragment_profile.setTypeface(dinNext);
        team_btn_fragment_profile.setTypeface(fontAwesome);
        //   button10.setTypeface(fontAwesome);
        btn_submit_fragment_profile.setTypeface(dinNext);


        //set TextView  icons
        statisitics_icon_fragment_profile.setTypeface(fontAwesome);
        matches_win_icon_fragment_profile.setTypeface(fontAwesome);
        matcches_tie_icon_fragment_profile.setTypeface(fontAwesome);
        matches_lost_icon_fragment_profile.setTypeface(fontAwesome);
        members_icon_fragment_profile.setTypeface(fontAwesome);
        //set TextView Headers
        statisitics_fragment_profile.setTypeface(dinNext);
        matches_win_header_fragment_profile.setTypeface(dinNext);
        matches_tie_header_fragment_profile.setTypeface(dinNext);
        matches_lost_header_fragment_profile.setTypeface(dinNext);
        members_icon_header_fragment_profile.setTypeface(dinNext);
        player_speed_value_fragment_profile.setTypeface(dinNext);
        player_skill_value_fragment_profile.setTypeface(dinNext);
        player_shooting_value_fragment_profile.setTypeface(dinNext);
        //  request_to_Players.setTypeface(fontAwesome);





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

    public void loadProfilePicture()
    {
        //set image
        LinearLayout ll = (LinearLayout) getActivity().findViewById(R.id.profile_photo);
        ImageView ii = new ImageView(getActivity());

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String json1 = mPrefs.getString("userObject", "");
        Gson gson = new Gson();
        User obj = gson.fromJson(json1, User.class);
        if (obj.getImage() == null
                ||String.valueOf(obj.getImage()) == null||String.valueOf(obj.getImage()) == ""||obj.getImage().isEmpty()) {
            ii.setImageResource(R.drawable.placeholder_user_photo);
        }
        else {

            Log.d("pppp",String.valueOf(obj.getImage()));
            if(!obj.getImage().isEmpty())
                if (obj.getImage() != null) {
                    if (String.valueOf(obj.getImage()) != null) {

                        Picasso.
                                with(getActivity()).
                                load(String.valueOf(obj.getImage()))
                                .resize(80, 100)
                                .memoryPolicy(MemoryPolicy.NO_CACHE)
                                .networkPolicy(NetworkPolicy.NO_CACHE)
                                .error(R.drawable.placeholder_user_photo)
                                .placeholder(R.drawable.placeholder_user_photo)
                                .into(ii);



                    }
                }

        }

        ll.addView(ii);
    }

    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

    @Override
    public void onPause() {
        super.onPause();
        // ll.removeAllViews();
        //  logoutHeader.setVisibility(View.VISIBLE);

    }

    public class MyAdapter extends RecyclerView.Adapter<ProfileFragment.MyViewHolder> {
        private ArrayList<MembersChildData> list;


        public MyAdapter(ArrayList<MembersChildData> Data) {
            list = Data;
        }

        @Override
        public ProfileFragment.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.members_profile_fragment_child, parent, false);
            ProfileFragment.MyViewHolder holder = new ProfileFragment.MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ProfileFragment.MyViewHolder holder, final int position) {

            Log.d("immm", list.get(position).getImage());
            if (list.get(position).getImage().isEmpty())
            {
                holder.memberChild.setImageResource(R.drawable.placeholder_user_photo);
            }
            else {
                Picasso.
                        with(getActivity()).
                        load(list.get(position).getImage())
                        .error(R.drawable.placeholder_user_photo)
                        .placeholder(R.drawable.placeholder_user_photo)
                        .into(holder.memberChild);
            }

            // holder.memberChild.setImageDrawable(bitmapDrawable);

            holder.memberChild.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //  flower_dialog.show();
                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    String url = URLs.GET_Player_properties_by_id + "?key=logic123&id="
                            + list.get(position).getId()+"&myId="+user.getId();
// Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // Display the first 500 characters of the response string.

                                    // flower_dialog.dismiss();
                                    Intent intent = new Intent(getActivity(), Main2Activity.class);
                                    intent.putExtra(FragmentsTags.FragemntTag, FragmentsTags.getPlayerFragment);
                                    intent.putExtra("jsonString", response);
                                    getActivity().startActivity(intent);
                                    MainContainer.headerNum = -1;
//                                    Bundle bundle =new Bundle();
//                                    bundle.putString("playersJson", response);
//                                    Fragment getPlayerFragment = new GetPlayerFragment();
//                                    getPlayerFragment.setArguments(bundle);
//                                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                                    ft.replace(R.id.framelayoutProfile, getPlayerFragment).addToBackStack(null).commit();
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

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        public CircleImageView memberChild;


        public MyViewHolder(View v) {
            super(v);
            memberChild = (CircleImageView) v.findViewById(R.id.memberChild);


        }
    }


    public void callAPI()
    {
        //PlayerStoredData playerStoredData = new PlayerStoredData(getActivity());


        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url ="https://logic-host.com/work/kora/beta/phpFiles/getPlayerByIdAPI.php?key=logic123&id="+user.getId();

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("user");
                            JSONObject c= jsonArray.getJSONObject(0);
                            int success = jsonObject.getInt("success");
                            if (success == 1) {
                                if (!c.isNull( "speed" )&& c.has("speed"))
                                    speed=c.getDouble("speed");

                                if (!c.isNull( "shooting" )&& c.has("shooting"))
                                    shooting=c.getDouble("shooting");

                                if (!c.isNull( "skill" )&& c.has("skill"))
                                    skill=c.getDouble("skill");


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        player_skill_progressBar_fragment_profile.setProgress(skill.intValue());
                        player_skill_value_fragment_profile.setText(new DecimalFormat("##.##").format(((skill.floatValue() / 5.0) * 100)) + "%");
                        player_speed_progressBar_fragment_profile.setProgress(speed.intValue());
                        player_speed_value_fragment_profile.setText(new DecimalFormat("##.##").format(((speed.floatValue() / 5.0) * 100)) + "%");
                        player_shooting_progressBar_fragment_profile.setProgress((shooting.intValue()));
                        player_shooting_value_fragment_profile.setText(new DecimalFormat("##.##").format(((shooting.floatValue() / 5.0) * 100)) + "%");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);





    }


    ////////////get the properties of the player



    void drawDialog(String Tiltle, final String s[], final int button_num) {
        @SuppressLint("RestrictedApi") AlertDialog.Builder dialog = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.CustomDialogTheme));
        TextView title = new TextView(getActivity());

        title.setText(Tiltle);
        title.setBackgroundColor(Color.parseColor("#008542"));


        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(20);
        title.setTypeface(dinNext);

        dialog.setCustomTitle(title);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.choice_item, R.id.text_view_choice, s) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(R.id.text_view_choice);
                text.setTypeface(dinNext);
                return view;
            }
        };
        dialog.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (button_num == 1)
                    city_btn.setText(s[which]);


            }
        });


        AlertDialog alert = dialog.create();

        alert.show();
    }



    public  void getMyTeam()
    {
        listitems = new ArrayList<>();
        SharedPreferences mPrefs_ = PreferenceManager.getDefaultSharedPreferences(getActivity());
        final String json1 = mPrefs_.getString("userObject", "");
        Gson gson = new Gson();
        final User obj = gson.fromJson(json1, User.class);
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = URLs.getTeamofAplayerAPI + "id=" + obj.getId();

        Log.d("up", url);
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("res", response);
                        try {
                            PrefManager prefManager = new PrefManager(getActivity());
                            JSONObject jsonObject = new JSONObject(response);
                            if (Integer.parseInt(jsonObject.getString("success")) == 1) {

                                JSONArray jsonArray = jsonObject.getJSONArray("team");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject innerObj = jsonArray.getJSONObject(i);
                                    //  if (String.valueOf(obj.getId()).equals(innerObj.getString("CaptainId")))
                                    if (Integer.parseInt(innerObj.getString("Captain")) == 1) {
                                        final String captainTeamId = innerObj.getString("id");
                                        final String teamName = innerObj.getString("name");
                                        isCaptian = 1;
                                        if (language.equals("ar"))
                                            fab_addTeam.setLabelText("تعديل اسم الفريق");
                                        else  fab_addTeam.setLabelText("Edit Team Name");
                                        fab_addTeam.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                // Click action
                                                ProfileTasks profileTasks = new ProfileTasks(getActivity(),String.valueOf(user_Id),language,dinNext);
                                                profileTasks.openDialogEditTeamName(captainTeamId,teamName);
                                                getMyTeam();
                                            }
                                        });
                                        // fab_addTeam.setVisibility(View.GONE);
                                        prefManager.setIsCaptain(1);
                                        fab_teammatches.setVisibility(View.VISIBLE);

                                        MyRecyclerView.setVisibility(View.VISIBLE);
                                        members_icon_header_fragment_profile.setVisibility(View.VISIBLE);

                                        members_icon_fragment_profile.setVisibility(View.VISIBLE);
                                        JSONArray innerAray = innerObj.getJSONArray("players");
                                        for (int k = 0; k < innerAray.length(); k++) {
                                            JSONObject playerObj = innerAray.getJSONObject(k);
                                            MembersChildData player = new MembersChildData();
                                            player.setId(playerObj.getString("id"));
                                            player.setImage(playerObj.getString("image"));
                                            if (!String.valueOf(obj.getId()) .equals( player.getId()))
                                                listitems.add(player);

                                        }
                                        MyRecyclerView.setHasFixedSize(true);
                                        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
                                        MyLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                                        // SnapHelper snapHelper = new PagerSnapHelper();
                                            /*try {
                                                snapHelper.attachToRecyclerView(MyRecyclerView);
                                            }catch(Exception e) {e.printStackTrace();}*/
                                        MyRecyclerView.setLayoutManager(MyLayoutManager);

                                        if (listitems.size() > 0 & MyRecyclerView != null) {
                                            MyRecyclerView.setAdapter(new MyAdapter(listitems));
                                        }

                                    }
                                    else {
                                        if(isCaptian==0){
                                            //  fab_addTeam.setVisibility(View.VISIBLE);
                                            MyRecyclerView.setVisibility(View.GONE);
                                            members_icon_header_fragment_profile.setVisibility(View.GONE);
                                            members_icon_fragment_profile.setVisibility(View.GONE);
                                            prefManager.setIsCaptain(0);
                                            fab_teammatches.setVisibility(View.GONE);
                                            //  fab_addTeam.setVisibility(View.VISIBLE);
                                            if (language.equals("ar"))
                                                fab_addTeam.setLabelText("اضافة فريق");
                                            else fab_addTeam.setLabelText("Make a Team");
                                            fab_addTeam.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                    ProfileTasks profileTasks = new ProfileTasks(getActivity(),String.valueOf(user_Id),language,dinNext);
                                                    profileTasks.openDialogcreateTeam();
                                                    getMyTeam();
                                                }
                                            });

                                        }
                                    }
                                }
                            } else {

                                //   ImageView backgroundEmptyPro = (ImageView) getActivity().findViewById(R.id.backgroundEmptyPro);
                                // backgroundEmptyPro.setVisibility(View.VISIBLE);
                                if(isCaptian==0){

                                    MyRecyclerView.setVisibility(View.GONE);
                                    members_icon_header_fragment_profile.setVisibility(View.GONE);
                                    members_icon_fragment_profile.setVisibility(View.GONE);
                                    prefManager.setIsCaptain(0);
                                    fab_teammatches.setVisibility(View.GONE);
                                    //  fab_addTeam.setVisibility(View.VISIBLE);
                                    if (language.equals("ar"))
                                        fab_addTeam.setLabelText("اضافة فريق");
                                    else fab_addTeam.setLabelText("Make a Team");
                                    fab_addTeam.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            ProfileTasks profileTasks = new ProfileTasks(getActivity(),String.valueOf(user_Id),language,dinNext);
                                            profileTasks.openDialogcreateTeam();
                                            getMyTeam();
                                        }
                                    });
                                }
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


    }



}

