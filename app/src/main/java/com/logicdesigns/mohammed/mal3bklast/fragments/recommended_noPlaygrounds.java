package com.logicdesigns.mohammed.mal3bklast.fragments;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.logicdesigns.mohammed.mal3bklast.MainPageFragment;
import com.logicdesigns.mohammed.mal3bklast.Models.PlaygroundProperties;
import com.logicdesigns.mohammed.mal3bklast.Models.Time_reserv;
import com.logicdesigns.mohammed.mal3bklast.R;
import com.logicdesigns.mohammed.mal3bklast.adapters.Playground_ListView_Adapter;
import com.logicdesigns.mohammed.mal3bklast.common.URLs;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import BusinessClasses.JSONParser;
import BusinessClasses.MainPageData;
import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link recommended_noPlaygrounds.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link recommended_noPlaygrounds#newInstance} factory method to
 * create an instance of this fragment.
 */
public class recommended_noPlaygrounds extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String selected_date,selected_from_time,selected_to_time;
    JSONParser jsonParser;
    ArrayList<PlaygroundProperties> playgroundsList = new ArrayList<PlaygroundProperties>();
    ListView list_recommended_playgrounds;

    ArrayList<PlaygroundProperties> playgroundsList_date_time = new ArrayList<PlaygroundProperties>();



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public recommended_noPlaygrounds() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        selected_date= getArguments().getString("date");
        selected_from_time=getArguments().getString("start_time");
        selected_to_time=getArguments().getString("end_time");
        list_recommended_playgrounds = (ListView) getView().findViewById(R.id.list_recommended_playgrounds) ;
        new GetPlaygroundOffer().execute();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment recommended_noPlaygrounds.
     */
    // TODO: Rename and change types and number of parameters
    public static recommended_noPlaygrounds newInstance(String param1, String param2) {
        recommended_noPlaygrounds fragment = new recommended_noPlaygrounds();
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
        return inflater.inflate(R.layout.fragment_recommended_no_playgrounds, container, false);
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
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
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

    public class GetPlaygroundOffer  extends AsyncTask<String, String, Boolean> {
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();


        }

        @Override
        protected Boolean doInBackground(String... arg) {
            // TODO Auto-generated method stub

            jsonParser=new JSONParser();


            List<NameValuePair> params = new ArrayList<NameValuePair>();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd" ,Locale.ENGLISH);
            String date = df.format(Calendar.getInstance().getTime());
            params.add(new BasicNameValuePair("key", "logic123"));
            params.add(new BasicNameValuePair("date", date));




            JSONObject json = jsonParser.makeHttpRequest("http://logic-host.com/work/kora/beta/phpFiles/getPlaygroundOffersAPI.php",
                    "GET", params);


            JSONArray jsonArray;

            // check for success tag
            try {
                playgroundsList = new ArrayList<PlaygroundProperties>();
                int success = json.getInt("success");

                if (success == 1) {

                    jsonArray =json.getJSONArray("playgroundoffers");

                    for (int i = 0; i<jsonArray.length();i++) {
                        JSONObject c = jsonArray.getJSONObject(i);
                        PlaygroundProperties playground = new PlaygroundProperties();
                        playground.setPlaygroundName(c.getString("playgroundName"));
                        playground.setPlaygroundCityName(c.getString("playgroundCityName"));
                        playground.setPlaygroundId(c.getString("playgroundId"));
                        playground.setPlaygroundImage(c.getString("playgroundImage"));
                        playground.setPlaygroundAddress(c.getString("playgroundAddress"));
                        playground.setPlaygroundType(c.getString("playgroundType")+"*"+c.getString("playgroundType"));
                            Log.d("11111111",c.getString("playgroundName"));
                        playgroundsList.add(playground);

                    }



                    return true;

                } else {
                    Log.i("test view","false");

                    return false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return false;


        }

        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if(result){
//

                for (final PlaygroundProperties playgroundProperties : playgroundsList) {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                    String date = df.format(Calendar.getInstance().getTime());
                    // Instantiate the RequestQueue.
                    RequestQueue queue = Volley.newRequestQueue(getActivity());
                    String url = URLs.Search_by_id_date +
                            "?key=logic123&bookdate=" + date + "&id="
                            + playgroundProperties.getPlaygroundId();

                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    JSONObject mainJSON = null;

                                    try {
                                        mainJSON = new JSONObject(response);
                                        JSONArray jsonMainArr = mainJSON.getJSONArray("time");
                                        for (int i = 0; i < jsonMainArr.length(); i++) {
                                            JSONObject childJSONObject = jsonMainArr.getJSONObject(i);
                                                 String date_time=
                                                    childJSONObject.getString(Time_reserv.Start_Index)+" - "+
                                                    childJSONObject.getString(Time_reserv.End_Index

                                            );
                                            playgroundProperties.setDate_time(date_time);
                                            playgroundsList_date_time.add(playgroundProperties);
                                            //playgroundsList_date_time.get(i).setDate_time(date_time);

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
//                Playground_ListView_Adapter adapter = new Playground_ListView_Adapter(getActivity()
//                ,R.layout.results_noplaygrounds,playgroundsList_date_time);
//
//                list_recommended_playgrounds.setAdapter(adapter);

            }

            //if true

        }
    }
}
