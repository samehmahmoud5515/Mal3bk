package com.logicdesigns.mohammed.mal3bklast.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.logicdesigns.mohammed.mal3bklast.ExceptionHandling.ExceptionHandling;
import com.logicdesigns.mohammed.mal3bklast.Models.NotificationModel;
import com.logicdesigns.mohammed.mal3bklast.Models.PlayerComingMatches;
import com.logicdesigns.mohammed.mal3bklast.R;
import com.logicdesigns.mohammed.mal3bklast.adapters.NotificationReyclerViewAdapter;
import com.logicdesigns.mohammed.mal3bklast.adapters.PlayerComingMatches_Adapter;
import com.logicdesigns.mohammed.mal3bklast.common.SampleRecyclerAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import BusinessClasses.User;
import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

/**
 * A simple {@link } subclass.
 * Activities that contain this fragment must implement the
 * {@link PlayerComingMatchesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PlayerComingMatchesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayerComingMatchesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<PlayerComingMatches> matches_arr;
    private SwipeRefreshLayout swipeRefreshLayout;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;


    private OnFragmentInteractionListener mListener;

    public PlayerComingMatchesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlayerComingMatchesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayerComingMatchesFragment newInstance(String param1, String param2) {
        PlayerComingMatchesFragment fragment = new PlayerComingMatchesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      SharedPreferences  sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
      String  language = sharedPref.getString("language", "ar");
        TextView title_header_fragment= (TextView) getActivity().findViewById(R.id.title_header_fragment);
       if (language.equals("ar"))
        title_header_fragment.setText("  المباريات القادمة  ");
        else
        title_header_fragment.setText("Coming Matches");
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_player_coming_matches, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_matches);

        recyclerView  = (RecyclerView)view.findViewById(R.id.coming_matches_recycleView);
        recyclerView.setAdapter(new SampleRecyclerAdapter());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchResults();
            }
        });
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);
                                        fetchResults();


                                    }
                                }
        );

        return  view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            //throw new RuntimeException(context.toString()
                    //+ " must implement OnFragmentInteractionListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public void fetchResults()
    {
        /*final ACProgressFlower   flower_dialog = new ACProgressFlower.Builder(getActivity())
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.GREEN).build();
        flower_dialog.show();*/
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Gson gson = new Gson();
        String json1 = mPrefs.getString("userObject", "");
        User user_obj = gson.fromJson(json1, User.class);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url ="https://logic-host.com/work/kora/beta/phpFiles/upcomingMatchesAPI.php?key=logic123&id="+user_obj.getId();
        final ExceptionHandling exceptionHandling = new ExceptionHandling(getActivity());

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        swipeRefreshLayout.setRefreshing(false);
                      //  flower_dialog.dismiss();
                        PlayerComingMatches playerComingMatches = new PlayerComingMatches(getActivity());
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (Integer.parseInt(jsonObject.getString("success"))==1) {
                                matches_arr = playerComingMatches.parseJson(response);
                                RecyclerView.Adapter adapter = new PlayerComingMatches_Adapter(matches_arr,getActivity());
                                recyclerView.setAdapter(adapter);
                            }
                            else {
                                exceptionHandling.showErrorDialog("لا توجد مباريات","no matches found",1);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            exceptionHandling.showErrorDialog();
                            swipeRefreshLayout.setRefreshing(false);
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
