package com.logicdesigns.mohammed.mal3bklast.fragments;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;
import com.logicdesigns.mohammed.mal3bklast.Main2Activity;
import com.logicdesigns.mohammed.mal3bklast.MainContainer;
import com.logicdesigns.mohammed.mal3bklast.Models.Message;
import com.logicdesigns.mohammed.mal3bklast.Models.NotificationModel;
import com.logicdesigns.mohammed.mal3bklast.PushNotification.Config;
import com.logicdesigns.mohammed.mal3bklast.R;
import com.logicdesigns.mohammed.mal3bklast.adapters.ChatAdapter;
import com.logicdesigns.mohammed.mal3bklast.adapters.NotificationReyclerViewAdapter;
import com.logicdesigns.mohammed.mal3bklast.common.EndlessScrollListener;
import com.logicdesigns.mohammed.mal3bklast.common.FragmentsTags;
import com.logicdesigns.mohammed.mal3bklast.common.SampleRecyclerAdapter;
import com.paging.listview.PagingListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChatDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChatDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    RecyclerView msgListView;
    ArrayList<Message> messagesList;
    ChatAdapter chatAdapter ;
    String myId,anotherId;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;
    int page = 1;
    ProgressBar progressBar_chat;
    private boolean isUserScrolling = false;
    private boolean isListGoingUp = true;
    AbsListView.OnScrollListener listner;

    public ChatDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatDetailsFragment newInstance(String param1, String param2) {
        ChatDetailsFragment fragment = new ChatDetailsFragment();
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
        View view = inflater.inflate(R.layout.fragment_chat_details, container, false);
        msgListView = (RecyclerView) view.findViewById(R.id.msgListView);
        progressBar_chat = (ProgressBar) view.findViewById(R.id.progressBar_chat) ;

        msgListView.setHasFixedSize(true);
        msgListView.setAdapter(new SampleRecyclerAdapter());
        mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setStackFromEnd(true);

        msgListView.setLayoutManager(mLayoutManager);

        // ----Set autoscroll of listview when a new message arrives----//
       // msgListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
     //  msgListView.setStackFromBottom(true);

        String jsonString = null;
        String user_name = "";
        try {
             jsonString = getArguments().getString("jsonString");
            myId = getArguments().getString("myId");
            anotherId = getArguments().getString("anotherId");
            user_name =  getArguments().getString("name");
        }catch (Exception e)
        {
            getActivity().onBackPressed();
        }
        if (jsonString!=null) {
            Message message = new Message();
            try {
                messagesList = message.parseJson(jsonString);
                chatAdapter = new ChatAdapter(getActivity(), messagesList);
                msgListView.setAdapter(chatAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        final EditText messageEditText = (EditText) view.findViewById(R.id.messageEditText);
        final ImageButton sendMessageButton = (ImageButton) view.findViewById(R.id.sendMessageButton);

        messageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>0)
                    sendMessageButton.setEnabled(true);
                else sendMessageButton.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
            sendMessageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!messageEditText.getText().toString().equals("")) {
                        final String message = messageEditText.getText().toString().replace(" ", "%20");
                        // Instantiate the RequestQueue.
                        RequestQueue queue = Volley.newRequestQueue(getActivity());
                        String url = "https://logic-host.com/work/kora/beta/phpFiles/sendChatAPI.php?key=logic123&id="+myId+"&pid="+anotherId+"&chat=" + message;

// Request a string response from the provided URL.
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                     new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        // Display the first 500 characters of the response string.
                                        Message chatMessage = new Message();
                                        ArrayList<Message> tempList = new ArrayList<Message>();
                                        try {
                                            tempList = chatMessage .parseJson(response);
                                            chatAdapter.add(tempList.get(0));
                                            chatAdapter.notifyDataSetChanged();
                                            messageEditText.setText("");
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
            });


        final String url = getArguments().getString("url");


        msgListView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {


            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //detect is the topmost item visible and is user scrolling? if true then only execute
                if(newState ==  RecyclerView.SCROLL_STATE_DRAGGING){
                    isUserScrolling = true;
                    if(isListGoingUp){
                        //my recycler view is actually inverted so I have to write this condition instead

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if(isListGoingUp) {
                                        int pastVisibleItems = mLayoutManager.findFirstCompletelyVisibleItemPosition();
                                        if (pastVisibleItems == 0) {
                                            if (loading) {
                                                loading = false;
                                                callApi(url, "&limit=10&page=" + String.valueOf(++page));
                                                progressBar_chat.setVisibility(View.VISIBLE);
                                            }

                                        }
                                    }
                                }
                            },50);
                            //waiting for 50ms because when scrolling down from top, the variable isListGoingUp is still true until the onScrolled method is executed

                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(isUserScrolling){
                    if(dy > 0){
                        //means user finger is moving up but the list is going down
                        isListGoingUp = false;
                    }
                    else{
                        //means user finger is moving down but the list is going up
                        isListGoingUp = true;
                    }
                }
            }
        });

        TextView title_textView =(TextView) getActivity().findViewById(R.id.title_header_fragment);
        title_textView.setText(user_name);

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
           // throw new RuntimeException(context.toString()
               //     + " must implement OnFragmentInteractionListener");
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


    public void callApi(final String url, String limit)
    {
        Log.d("infinteScroll" ,url+limit);
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url+limit,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Message m = new Message();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("success").equals("1")) {
                                ArrayList<Message> list = new ArrayList<>();
                                list =  m.parseJson(response);
                             //   messagesList.addAll(list);
                             //   Collections.reverse(messagesList);
                                for(int i =list.size()-1;i>=0;i--) {
                                    messagesList.add(0, list.get(i));
                                }
                                progressBar_chat.setVisibility(View.GONE);
                                chatAdapter.notifyDataSetChanged();
                                loading =true;
                            }
                            else {
                                loading =false;
                                progressBar_chat.setVisibility(View.GONE);
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
