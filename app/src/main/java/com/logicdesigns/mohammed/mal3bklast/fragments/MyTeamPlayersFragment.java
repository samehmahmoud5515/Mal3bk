package com.logicdesigns.mohammed.mal3bklast.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.logicdesigns.mohammed.mal3bklast.ExceptionHandling.ExceptionHandling;
import com.logicdesigns.mohammed.mal3bklast.Main2Activity;
import com.logicdesigns.mohammed.mal3bklast.R;
import com.logicdesigns.mohammed.mal3bklast.SharedPref.PrefManager;
import com.logicdesigns.mohammed.mal3bklast.adapters.ExpandableListAdapter;
import com.logicdesigns.mohammed.mal3bklast.common.FragmentsTags;
import com.logicdesigns.mohammed.mal3bklast.common.URLs;
import com.logicdesigns.mohammed.mal3bklast.dummy.DummyContent.DummyItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import BusinessClasses.FormationData;
import BusinessClasses.User;
import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class MyTeamPlayersFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    ArrayList<FormationData> FormationDatas = new ArrayList<FormationData>();
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    SharedPreferences sharedPref;
    String language = null;

    RecyclerView recyclerView;

    ACProgressFlower flower_dialog;


    ListView list;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MyTeamPlayersFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MyTeamPlayersFragment newInstance(int columnCount) {
        MyTeamPlayersFragment fragment = new MyTeamPlayersFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        TextView title_header_fragment= (TextView) getActivity().findViewById(R.id.title_header_fragment);
        title_header_fragment.setText("  الاعبين  ");
      //  LinearLayout ll = (LinearLayout)getActivity().findViewById(R.id.profile_photo);
       // ll.setVisibility(View.GONE);
        ImageButton logoutHeader1 =(ImageButton) getActivity().findViewById(R.id.logoutHeader1);
        logoutHeader1.setVisibility(View.VISIBLE);

        flower_dialog = new ACProgressFlower.Builder(getActivity())
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.GREEN).build();
        flower_dialog.show();

        // get the listview
        expListView = (ExpandableListView)view.findViewById(R.id.lvExp);
        //ImageView backgroundEmpty = (ImageView) view.findViewById(R.id.backgroundEmpty);
       // expListView.setEmptyView(backgroundEmpty);
      //  expListView.setSelectedChild(0,0,true);

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        final String json1 = mPrefs.getString("userObject", "");
        Gson gson =new Gson();
        final User obj = gson.fromJson(json1, User.class);

// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = URLs.getTeamofAplayerAPI+"id="+obj.getId();
   Log.d("TeamofAplayerAPI ", url);
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        flower_dialog.dismiss();
                        try {
                            if (getArguments().getString("one_team") != null)
                                response = getArguments().getString("one_team");
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(Integer.parseInt(jsonObject.getString("success"))==1)
                            {
                                FormationData parserOpertaion = new FormationData(getActivity());
                                List<String> teamsNamesList = new ArrayList<>();
                                teamsNamesList= parserOpertaion.ParseTeamsNames(response);

                                JSONArray jsonArray = jsonObject.getJSONArray("team");
                                HashMap<String,List<FormationData>> hashMap =new HashMap<>();

                                for (int i=0;i<jsonArray.length();i++)
                                {
                                    JSONObject innerJsonObj = jsonArray.getJSONObject(i);
                                    List<FormationData> playersList ;
                                    playersList = parserOpertaion.ParseTeamsPlayers(innerJsonObj.toString());
                                    hashMap.put(teamsNamesList.get(i),playersList);
                                }
                                listAdapter = new ExpandableListAdapter(getActivity(), teamsNamesList, hashMap,response);

                                // setting list adapter
                                expListView.setAdapter(listAdapter);
                                for(int i=0; i < listAdapter.getGroupCount(); i++)
                                    expListView.expandGroup(i);
                            }
                            else if(Integer.parseInt(jsonObject.getString("success"))==0)
                            {
                                ExceptionHandling exceptionHandling = new ExceptionHandling(getActivity());
                                exceptionHandling.showErrorDialog("انت غير مشترك في فريق","You haven't enrolled in any team",1);
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

      /*  Button comingTeamMatches = (Button) view.findViewById(R.id.team_coming_matches_btn);
        comingTeamMatches.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/fontawesome-webfont.ttf"));

        comingTeamMatches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Main2Activity.class);
                intent.putExtra(FragmentsTags.FragemntTag, FragmentsTags.TeamComingMatchesFragment);
                getActivity().startActivity(intent);
            }
        });

        PrefManager prefManager = new PrefManager(getActivity());
        int isCaptain = prefManager.getIsCaptain();
        if (isCaptain==0)
        {
            comingTeamMatches.setVisibility(View.GONE);
        }
        else  if (isCaptain ==1)
        {
            comingTeamMatches.setVisibility(View.VISIBLE);

        }*/

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        language = sharedPref.getString("language", "ar");
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }




    @Override
    public void onStart() {
        super.onStart();
        TextView title_header_fragment = (TextView) getActivity().findViewById(R.id.title_header_fragment);
        if (language.equals("en")) {
            title_header_fragment.setText(R.string.Players);
        } else {
            title_header_fragment.setText("الاعبين");
        }
    }
}
