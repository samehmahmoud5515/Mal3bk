package com.logicdesigns.mohammed.mal3bklast.tasks;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.logicdesigns.mohammed.mal3bklast.SharedPref.PrefManager;
import com.logicdesigns.mohammed.mal3bklast.common.URLs;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by logicDesigns on 9/24/2017.
 */

public class GetPlayerPostions {
    Context mContext;

    public GetPlayerPostions(Context mContext) {
        this.mContext = mContext;
        callAPI();
    }

    public void callAPI()
    {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(mContext);
        String url = URLs.player_postions;

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject json =new JSONObject(response);
                            int success = json.getInt("success");

                            if (success == 1) {
                                PrefManager prefManager =new PrefManager(mContext);

                                prefManager.setPlayers_Postions(response);
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
