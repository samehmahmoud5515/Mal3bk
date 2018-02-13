package com.logicdesigns.mohammed.mal3bklast.Request_Volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by logicDesigns on 6/21/2017.
 */

public class RequestVolley {

    Context mContext;
    String RESPonse;

    public RequestVolley(Context context)
    {
        this.mContext=context;
    }


    public String makeNewRequest(String url){
    // Instantiate the RequestQueue.
    RequestQueue queue = Volley.newRequestQueue(mContext);

    // Request a string response from the provided URL.
    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                RESPonse=response;
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    });
// Add the request to the RequestQueue.
    queue.add(stringRequest);

        return RESPonse;

    }
}
