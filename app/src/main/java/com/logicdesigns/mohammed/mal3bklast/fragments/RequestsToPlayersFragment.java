package com.logicdesigns.mohammed.mal3bklast.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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
import com.logicdesigns.mohammed.mal3bklast.Models.RequestsToPlayers;
import com.logicdesigns.mohammed.mal3bklast.R;
import com.logicdesigns.mohammed.mal3bklast.adapters.RequestToPlayersAdapter;
import com.logicdesigns.mohammed.mal3bklast.common.SampleRecyclerAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import BusinessClasses.User;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RequestsToPlayersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RequestsToPlayersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RequestsToPlayersFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView_buy,recyclerView_borrow;
    ArrayList<RequestsToPlayers> buyPlayers,borrowPlayers;
    TextView title_borrow;
    TextView title_buy;
    SharedPreferences sharedPref;
    String language = null;

    public RequestsToPlayersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RequestsToPlayersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RequestsToPlayersFragment newInstance(String param1, String param2) {
        RequestsToPlayersFragment fragment = new RequestsToPlayersFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        language = sharedPref.getString("language", "ar");
        TextView title_header_fragment= (TextView) getActivity().findViewById(R.id.title_header_fragment);
        if (language.equals("ar"))
            title_header_fragment.setText(" الاضافات ");
        else    title_header_fragment.setText(" Requests ");

        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_requests_to_players, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_players);

        recyclerView_buy  = (RecyclerView)view.findViewById(R.id.playerRequest_recycler_view_buy);
        recyclerView_buy.setAdapter(new SampleRecyclerAdapter());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView_buy.setLayoutManager(layoutManager);

        recyclerView_borrow  = (RecyclerView)view.findViewById(R.id.playerRequest_recycler_view_borrow);
        recyclerView_borrow.setAdapter(new SampleRecyclerAdapter());
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getActivity().getApplicationContext());

        recyclerView_borrow.setLayoutManager(layoutManager2);

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

        Typeface dinNext = Typeface.createFromAsset(getActivity().getAssets(), "fonts/DIN Next LT W23 Regular.ttf");
         title_buy = (TextView) view.findViewById(R.id.title_buy);
        title_buy.setTypeface(dinNext);
        title_borrow = (TextView) view.findViewById(R.id.title_borrow);
        title_borrow.setTypeface(dinNext);
        title_buy.setVisibility(View.GONE);
        title_borrow.setVisibility(View.GONE);
        if (language.equals("en"))
        {
            title_buy.setText("Players you've sent to be in the team");
            title_borrow.setText("Players you've added to only one match");
        }

        return view;
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
         //   throw new RuntimeException(context.toString()
          //          + " must implement OnFragmentInteractionListener");
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

    public void fetchResults()
    {

        final ExceptionHandling exceptionHandling = new ExceptionHandling(getActivity());
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Gson gson = new Gson();
        final String json1 = mPrefs.getString("userObject", "");
        User user_obj = gson.fromJson(json1, User.class);
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url ="https://logic-host.com/work/kora/beta/phpFiles/getRequestedPlayersAPI.php?key=logic123&id="
                +user_obj.getId();

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                     swipeRefreshLayout.setRefreshing(false);
                        try {
                            JSONObject jsonObject =new JSONObject(response);
                            if (Integer.parseInt(jsonObject.getString("success"))==1)
                            {
                                buyPlayers = new ArrayList<>();
                                borrowPlayers = new ArrayList<>();
                                RequestsToPlayers requestsToPlayers = new RequestsToPlayers();
                                buyPlayers =  requestsToPlayers.parseJsonForBuyPlayer(response);
                                borrowPlayers = requestsToPlayers.parseJsonForBorrowPlayer(response);

                                if (buyPlayers.size()==0)
                                {
                                    title_buy.setVisibility(View.GONE);
                                    recyclerView_buy.setVisibility(View.GONE);
                                }
                                else {
                                    RequestToPlayersAdapter buyAdapter = new RequestToPlayersAdapter(buyPlayers,getActivity());
                                    recyclerView_buy.setAdapter(buyAdapter);
                                    title_buy.setVisibility(View.VISIBLE);
                                    recyclerView_buy.setVisibility(View.VISIBLE);
                                }
                                if(borrowPlayers.size()==0)
                                {
                                    title_borrow.setVisibility(View.GONE);
                                    recyclerView_borrow.setVisibility(View.GONE);
                                }
                                else {
                                    RequestToPlayersAdapter borrowAdapter = new RequestToPlayersAdapter(borrowPlayers,getActivity());
                                    recyclerView_borrow.setAdapter(borrowAdapter);
                                    title_borrow.setVisibility(View.VISIBLE);
                                    recyclerView_borrow.setVisibility(View.VISIBLE);

                                }


                            }

                            else {
                                exceptionHandling.showErrorDialog("لا يوجد لاعبيين","No Requests found",1);
                                title_borrow.setVisibility(View.GONE);
                                title_buy.setVisibility(View.GONE);
                                recyclerView_borrow.setVisibility(View.GONE);
                                recyclerView_buy.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            title_borrow.setVisibility(View.GONE);
                            title_buy.setVisibility(View.GONE);
                            exceptionHandling.showErrorDialog();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                exceptionHandling.showErrorDialog();


            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
