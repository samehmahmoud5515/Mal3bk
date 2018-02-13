package com.logicdesigns.mohammed.mal3bklast.fragments;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.logicdesigns.mohammed.mal3bklast.ExceptionHandling.ExceptionHandling;
import com.logicdesigns.mohammed.mal3bklast.Models.Message;
import com.logicdesigns.mohammed.mal3bklast.R;
import com.logicdesigns.mohammed.mal3bklast.adapters.MessagesAdapter;
import com.squareup.picasso.Picasso;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import BusinessClasses.User;

/**
 * A simple {@link } subclass.
 * Activities that contain this fragment must implement the
 * {@link inbox_fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link inbox_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class inbox_fragment extends Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    SharedPreferences sharedPref;
    String language = null;

    private OnFragmentInteractionListener mListener;
    SwipeRefreshLayout swipe_refresh_layout_inbox;
    RecyclerView recycler_view_inbox;
    ArrayList<Message> messagesList;
    MessagesAdapter mAdapter ;

    public inbox_fragment() {
        // Required empty public constructor
    }


    public static inbox_fragment newInstance(String param1, String param2) {
        inbox_fragment fragment = new inbox_fragment();
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
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_inbox_fragment, container, false);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        language = sharedPref.getString("language", "ar");

        recycler_view_inbox = (RecyclerView ) view.
                findViewById(R.id.recycler_view_inbox);
        swipe_refresh_layout_inbox = (SwipeRefreshLayout) view.
                findViewById(R.id.swipe_refresh_layout_inbox);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recycler_view_inbox.setLayoutManager(mLayoutManager);
        swipe_refresh_layout_inbox.post(
                new Runnable() {
                    @Override
                    public void run() {
                        getInbox();
                    }
                }
        );
        swipe_refresh_layout_inbox.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getInbox();
            }
        });

        TextView title_textView =(TextView) getActivity().findViewById(R.id.title_header_fragment);

        if (language.equals("ar"))
            title_textView.setText("الرسائل");
       else title_textView.setText("Messages");

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
          //  throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }




    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

   public void getInbox()
    {
         swipe_refresh_layout_inbox.setRefreshing(true);
        SharedPreferences mPrefs =  PreferenceManager.getDefaultSharedPreferences(getActivity());
        Gson gson = new Gson();
        String json1 = mPrefs.getString("userObject", "");
        User user_obj = gson.fromJson(json1, User.class);
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url ="https://logic-host.com/work/kora/beta/phpFiles/getAllchatsAPI.php?key=logic123&id="+
                user_obj.getId();
        String paging_url ="https://logic-host.com/work/kora/beta/phpFiles/getAllchatsAPI.php?key=logic123&id="+
                user_obj.getId();

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        swipe_refresh_layout_inbox.setRefreshing(false);
                        // Display the first 500 characters of the response string.
                        Message message = new Message();
                        try {
                            String success = "1";
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.has("success")) {
                                      success = "1";
                            }
                            else success = "0";

                              if (success.equals("1"))
                              {
                                messagesList = message.parseJson(response);
                                mAdapter = new MessagesAdapter(getActivity(), messagesList);

                                recycler_view_inbox.setAdapter(mAdapter);
                              }
                            else if (success.equals("0"))
                              {
                                  ExceptionHandling exceptionHandling = new ExceptionHandling(getActivity());
                                  exceptionHandling.showErrorDialog("لاتوجد رسائل","No messages",1);
                              }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                       // mAdapter.notifyDataSetChanged();
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
