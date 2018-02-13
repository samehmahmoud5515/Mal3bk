package com.logicdesigns.mohammed.mal3bklast.tasks;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by logicDesigns on 12/12/2017.
 */

public class SetNotificationSeenTask {

Context mContext;

   public  SetNotificationSeenTask(Context context)
   {
      this.mContext= context;
   }

    public void SetNotificationSeen(String type, String id)
    {
        RequestQueue queue = Volley.newRequestQueue(mContext);
        String url ="https://logic-host.com/work/kora/beta/phpFiles/seeNotificationAPI.php?key=logic123&type="+type+"&id="+id;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


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
