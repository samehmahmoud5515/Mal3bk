package com.logicdesigns.mohammed.mal3bklast.tasks;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.vision.text.Text;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.leolin.shortcutbadger.ShortcutBadger;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * Created by logicDesigns on 12/12/2017.
 */

public class NotificationBadgeTask {

    Context mContext;
    String user_id;
    TextView notification_tv;
    int count=0;
    int type ;
    public NotificationBadgeTask(Context context , String user_id, TextView notification_tv,int type )
    {
        this.mContext =context ;
        this.user_id = user_id;
        this.notification_tv =notification_tv;
        this.type = type;
        getNotifications();

    }
    public  void  getNotifications()
    {
        RequestQueue queue = Volley.newRequestQueue(mContext);
        String url ="https://logic-host.com/work/kora/beta/phpFiles/notificationAPI.php?key=logic123&id=" + user_id;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.isNull("success") && jsonObject.has("success"))
                            {
                                if (jsonObject.getString("success").equals("1"))
                                {
                                    JSONArray jsonArray = jsonObject.getJSONArray("request");
                                    for (int i =0;i<jsonArray.length();i++)
                                    {
                                        JSONObject innerObj = jsonArray.getJSONObject(i);
                                        if (! innerObj.isNull("seen")&& innerObj.has("seen"))
                                        {
                                            try {
                                                if (Integer.parseInt(innerObj.getString("seen")) == 0)
                                                { count++; }
                                            }catch (Exception e)
                                            {
                                                e.printStackTrace();
                                            }
                                        }

                                    }
                                    if (count >0) {
                                        if (type!= 2) {
                                            notification_tv.setVisibility(View.VISIBLE);
                                            notification_tv.setText(String.valueOf(count));
                                        }
                                        if (type == 2)
                                            ShortcutBadger.applyCount(mContext, count);

                                    }
                                    else if (count == 0) {
                                        if (type == 2)
                                            ShortcutBadger.removeCount(mContext);
                                        if (type != 2)
                                            notification_tv.setVisibility(View.GONE);
                                    }
                                }
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
