package com.logicdesigns.mohammed.mal3bklast.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Duration;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.google.gson.Gson;
import com.logicdesigns.mohammed.mal3bklast.ExceptionHandling.ExceptionHandling;
import com.logicdesigns.mohammed.mal3bklast.Models.NotificationModel;
import com.logicdesigns.mohammed.mal3bklast.R;
import com.logicdesigns.mohammed.mal3bklast.adapters.NotificationReyclerViewAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import BusinessClasses.User;


public class NotficationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    SharedPreferences sharedPref;
    String language = null;

    RecyclerView recyclerView;

    private SwipeRefreshLayout swipeRefreshLayout;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;
    int page = 0;
    ArrayList<NotificationModel> list =new ArrayList<NotificationModel>();
    RecyclerView.Adapter adapter;
    public NotficationFragment() {
        // Required empty public constructor
    }

    ProgressBar progressBar;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotficationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotficationFragment newInstance(String param1, String param2) {
        NotficationFragment fragment = new NotficationFragment();
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
        page = 0;
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_notfication, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar) ;
       TextView title_header_fragment = (TextView) getActivity().findViewById(R.id.title_header_fragment);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());

        language = sharedPref.getString("language", "ar");
        if (language.equals("en")) {
            swipeRefreshLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        recyclerView  = (RecyclerView)view.findViewById(R.id.notification_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new NotificationReyclerViewAdapter(new ArrayList<NotificationModel>(),getActivity()));
     //   RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
      //  recyclerView.setLayoutManager(layoutManager);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        if (language.equals("en")) {
            title_header_fragment.setText("Noitifcation");
        } else {
            title_header_fragment.setText("إخطارات");
        }


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 0;
                if (recyclerView!=null)
                    recyclerView.invalidate();
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



        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
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
                            fetchResults();
                            progressBar.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });

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
            //throw new RuntimeException(context.toString()
                   // + " must implement OnFragmentInteractionListener");
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

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Gson gson = new Gson();
        String json1 = mPrefs.getString("userObject", "");
        User obj = gson.fromJson(json1, User.class);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url ="https://logic-host.com/work/kora/beta/phpFiles/notificationAPI.php?key=logic123&id="+obj.getId()+
                "&page="+(++page) + "&limit=7";
        Log.d("urlPagingnotifation", url);

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (Integer.parseInt(jsonObject.getString("success"))==1 ) {
                                swipeRefreshLayout.setRefreshing(false);

                                NotificationModel model = new NotificationModel(getActivity());

                                if (page==1)
                                {   list = new ArrayList<>();
                                    list .addAll( model.parseJson(response));
                                    adapter = new NotificationReyclerViewAdapter(list,getActivity());
                                    recyclerView.setAdapter(adapter);
                                }
                                if (page>1) {
                                    list .addAll( model.parseJson(response));
                                    adapter.notifyDataSetChanged();
                                }
                                progressBar.setVisibility(View.GONE);
                               loading =true;
                            }
                            else if (Integer.parseInt(jsonObject.getString("success"))==-1)
                            {
                                progressBar.setVisibility(View.GONE);
                                loading =false;
                                swipeRefreshLayout.setRefreshing(false);

                            }
                            else {
                                // user has No Notifiacation
                                swipeRefreshLayout.setRefreshing(false);
                                loading =false;

                                ExceptionHandling exceptionHandling = new ExceptionHandling(getActivity());
                                exceptionHandling.showErrorDialog("لا توجد اشعارات","No notifications found",1);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            swipeRefreshLayout.setRefreshing(false);
                            ExceptionHandling exceptionHandling =new ExceptionHandling(getActivity());
                            exceptionHandling.showErrorDialog();
                            progressBar.setVisibility(View.GONE);
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
