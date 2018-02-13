package com.logicdesigns.mohammed.mal3bklast;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.logicdesigns.mohammed.mal3bklast.JSON_PARSER.Player_Properties;
import com.logicdesigns.mohammed.mal3bklast.Models.NotificationModel;
import com.logicdesigns.mohammed.mal3bklast.SharedPref.PrefManager;
import com.logicdesigns.mohammed.mal3bklast.adapters.NotificationReyclerViewAdapter;
import com.logicdesigns.mohammed.mal3bklast.common.SampleRecyclerAdapter;
import com.paging.listview.PagingListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import BusinessClasses.FormationData;
import BusinessClasses.GetPlayerData;
import BusinessClasses.ListViewAsapterForFormation;
import BusinessClasses.ListViewAsapterForGetPlayer;
import BusinessClasses.RegisterPlaygroundData;

import static com.facebook.FacebookSdk.getApplicationContext;


public class GetPlayerFragment extends Fragment {
    RecyclerView userList;
    TextView filter;
    ListViewAsapterForGetPlayer userAdapter;
    ArrayList<GetPlayerData> getPlayerDatas = new ArrayList<GetPlayerData>();
    ArrayList<Player_Properties> playersList = new ArrayList<>();
    TextView formation_add_new_player, formation_btn_add_new_player, formation_icon_main_page, formation_icon_share, title_header_fragment;
    Button formation_btn_main_page, formation_btn_share;
    Typeface custom_Din;
    SharedPreferences sharedPref;
    String language = null;
    Context context;
    int page;
    LinearLayoutManager mLayoutManager;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    ProgressBar progressBar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        language = sharedPref.getString("language", "");
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_get_player, container, false);
        page=1;
        if (language.equals("en")) {
            custom_Din = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Light.ttf");
        } else {
            custom_Din = Typeface.createFromAsset(getActivity().getAssets(), "fonts/DIN Next LT W23 Regular.ttf");
        }

        LinearLayout ll = (LinearLayout) getActivity().findViewById(R.id.profile_photo);
        if (ll != null)
            ll.setVisibility(View.GONE);
        TextView title_header_fragment = (TextView) getActivity().findViewById(R.id.title_header_fragment);
        if (language.equals("en")) {
            title_header_fragment.setText(R.string.SearchPlayer);
        } else {
            title_header_fragment.setText("البحث عن لاعب");
        }
        if (getArguments().getString("hideFiler_")== null)
            try {
                filter = (TextView) getActivity().findViewById(R.id.filter_header_fragment);
                // filter.setVisibility(View.VISIBLE);
                if (getArguments().getString("hideFiler") != null) {
                    if (getArguments().getString("hideFiler").equals("1")) {
                        filter.setVisibility(View.GONE);
                    }
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        String playersJson = null;


        if (getArguments().getString("playersJson") != null) {
            playersJson = getArguments().getString("playersJson");
           // prefManager.setFilered_Players_json(getArguments().getString("playersJson"));
        }


            getPlayerDatas = new ArrayList<GetPlayerData>();
            Player_Properties player_properties = new Player_Properties(getActivity());

            try {
                playersList = player_properties.ParsePlayer(playersJson);
                for (Player_Properties player : playersList) {
                    GetPlayerData data = new GetPlayerData();
                    data.setNamePlayer(player.getName());
                    data.setPositionPlayer(player.getPosition());
                    data.setTownPlayer(player.getCity());
                    data.setPlayerImage(player.getImage());
                    data.setRatingPlayer(player.getStars());
                    data.setShootingPlayer(player.getShooting());
                    data.setSkillPlayer(player.getSkill());
                    data.setPlayerId(player.getId());
                    data.setInMyTeam(player.getInMyTeam());
                    data.setSpeedPlayer(player.getSpeed());
                    data.setTeamId(player.getTeamId());
                    data.setRateable(player.getRateable());
                    data.setCanchangePosition(player.getCanchangePosition());
                    getPlayerDatas.add(data);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        //  title_header_fragment= (TextView) getActivity().findViewById(R.id.title_header_fragment);

//        title_header_fragment.setTypeface(custom_Din);







        userList = (RecyclerView) view.findViewById(R.id.get_player_list);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBarGetPlayer) ;

        userList.setHasFixedSize(true);
        userList.setAdapter(new SampleRecyclerAdapter());

        mLayoutManager = new LinearLayoutManager(getActivity());
        userList.setLayoutManager(mLayoutManager);

        if (getPlayerDatas.size() > 0) {
            userAdapter = new ListViewAsapterForGetPlayer(getActivity(), R.layout.get_player_child, getPlayerDatas);

            final String url = getArguments().getString("url");
            if (url != null) {
            /*    userList.setOnScrollListener(new EndlessScrollListener() {

                    @Override
                    public void onLoadMore(int page, int totalItemsCount) {
                        // TODO Auto-generated method stub
                        callAPI(page, getArguments().getString("url"));
                    }
                });*/


              final String urlget = getArguments().getString("url");
                userList.addOnScrollListener(new RecyclerView.OnScrollListener()
                {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy)
                    {
                        if(dy > 0) //check for scroll down
                        {
                            visibleItemCount = mLayoutManager.getChildCount();
                            totalItemCount = mLayoutManager.getItemCount();
                            pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                            if (loading)
                            {
                                if ( (visibleItemCount + pastVisiblesItems) >= totalItemCount)
                                {
                                    loading = false;
                                    Log.v("...", "Last Item Wow !");
                                    //Do pagination.. i.e. fetch new data
                                    callAPI(++page, urlget);
                                    progressBar.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }
                });

            }

        } else {
            final Activity mActivity = getActivity();

            final Dialog dialog = new Dialog(mActivity, R.style.CustomDialogTheme);
            dialog.setContentView(R.layout.no_playgrounds_custom_dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            dialog.setCancelable(true);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

            TextView no_message_text = (TextView) dialog.findViewById(R.id.no_message_text);
            no_message_text.setTypeface(custom_Din);

            Button close_button = (Button) dialog.findViewById(R.id.btn_close_no_playground);
            close_button.setTypeface(custom_Din);

            if (language.equals("en")) {
                no_message_text.setText(R.string.noPlayer);
                close_button.setText(R.string.close);
            } else {
                no_message_text.setText("لا توجد لاعبين");
            }

            close_button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog.cancel();

                }
            });
            dialog.show();
        }
        userList.setAdapter(userAdapter);
        return view;
    }

    @Override
    public void onStart() {

        super.onStart();


    }

    @Override
    public void onPause() {
        super.onPause();

    }

    public void callAPI(int page, String passedURL) {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = passedURL + "&page=" + page;
        Log.d("getPlayerURL ",url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        Player_Properties player_properties = new Player_Properties(getActivity());

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("success").equals("1")) {
                                playersList =new ArrayList<>();
                                playersList = player_properties.ParsePlayer(response);

                                for (Player_Properties player : playersList) {
                                    GetPlayerData data = new GetPlayerData();
                                    data.setNamePlayer(player.getName());
                                    data.setPositionPlayer(player.getPosition());
                                    data.setTownPlayer(player.getCity());
                                    data.setPlayerImage(player.getImage());
                                    data.setRatingPlayer(player.getStars());
                                    data.setShootingPlayer(player.getShooting());
                                    data.setSkillPlayer(player.getSkill());
                                    data.setPlayerId(player.getId());
                                    data.setInMyTeam(player.getInMyTeam());
                                    data.setSpeedPlayer(player.getSpeed());
                                    data.setTeamId(player.getTeamId());
                                    data.setRateable(player.getRateable());
                                    getPlayerDatas.add(data);
                                }
                                userAdapter.notifyDataSetChanged();
                                loading = true;
                                progressBar.setVisibility(View.GONE);
                            }
                            else{
                                loading = false;
                                progressBar.setVisibility(View.GONE);
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
        queue.add(stringRequest);
    }

    @Override
    public void onDestroyView() {
        if (!Main2Activity.active && MainContainer.active) {
            try {
                MainContainer.headerNum = 3;
                FragmentTransaction ft1 = getActivity().getSupportFragmentManager().beginTransaction();
                ft1.replace(R.id.placeholder1, new HeaderFragment())
                        .commit();
            }catch ( Exception e)
            {
                e.printStackTrace();
            }
        }
        super.onDestroyView();

    }

}
