package com.logicdesigns.mohammed.mal3bklast.tasks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.logicdesigns.mohammed.mal3bklast.ClubFragment;
import com.logicdesigns.mohammed.mal3bklast.ExceptionHandling.ExceptionHandling;
import com.logicdesigns.mohammed.mal3bklast.Main2Activity;
import com.logicdesigns.mohammed.mal3bklast.MainContainer;
import com.logicdesigns.mohammed.mal3bklast.PayFragment;
import com.logicdesigns.mohammed.mal3bklast.R;
import com.logicdesigns.mohammed.mal3bklast.common.FragmentsTags;
import com.logicdesigns.mohammed.mal3bklast.common.URLs;
import com.logicdesigns.mohammed.mal3bklast.header.HeaderFragment_Inner;

import org.json.JSONException;
import org.json.JSONObject;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

/**
 * Created by logicDesigns on 1/29/2018.
 */

public class ReservationPlaygroundTask {
    Context context;
    public  ReservationPlaygroundTask(Context context)
    {
        this.context = context;
    }

    public void reserveGround(String playground_id , String user_Id, String bookdate, String startTime,  String endTime, String teamId1  )
    {
        final ACProgressFlower   flower_dialog = new ACProgressFlower.Builder(context).direction(ACProgressConstant.DIRECT_CLOCKWISE).themeColor(Color.WHITE).fadeColor(Color.GREEN).build();
        flower_dialog.show();
        RequestQueue queue = Volley.newRequestQueue(context);
        final ExceptionHandling dialogShow = new ExceptionHandling(context);
        //&id=19&pid=1&bookdate=2018-01-23&from=13&to=15&teamId1=1&teamId2=2
        startTime+= ":00:00";
        endTime +=":00:00";
        String url = URLs.RESERVE_PLAYGRound+"&id="+playground_id+
               "&pid="+ user_Id +  "&bookdate=" + bookdate +
                "&from=" + startTime + "&to=" +endTime +
                "&teamId1=" + teamId1;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        flower_dialog.dismiss();
                        try {
                            JSONObject jsonObject  = new JSONObject(response);
                            if (jsonObject.getString("success").equals("1"))
                            {
                                dialogShow.showErrorDialog("تم حجز الملعب بنجاح","Playground reserved successfully");
                                Intent intent = new Intent(context,Main2Activity.class);
                                intent.putExtra(FragmentsTags.FragemntTag, FragmentsTags.PayFragment);
                               context. startActivity(intent);
                                MainContainer.headerNum = 99;
                                FragmentTransaction ft1 = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                                ft1.replace(R.id.placeholder1, new HeaderFragment_Inner())
                                        .commit();

                            }
                            else  if (jsonObject.getString("success").equals("-1"))
                            {
                              dialogShow.showErrorDialog("لديك مبارة في هذا التوقيت","Your team has already match at that time.");
                            }
                            else if(jsonObject.getString("success").equals("-2"))
                            {
                               dialogShow.showErrorDialog("لديك مبارة في هذا التوقيت مع الفريق المنافس","the other team has already match at that time.");
                            }
                            else if(jsonObject.getString("success").equals("-3"))
                            {
                                dialogShow.showErrorDialog("الملعب لديه مباراة بالفعل في ذلك الوقت","The playground has a match already at that time");
                            }
                            else{
                                dialogShow.showErrorDialog();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialogShow.showErrorDialog();
                flower_dialog.dismiss();
            }
        });
        queue.add(stringRequest);

    }
    /*
       ReservationPlaygroundTask task = new ReservationPlaygroundTask(getActivity());
                task.reserveGround(getArguments().getString("playgroundId"),
                        String.valueOf(user_obj.getId()),selected_date,receivedStartTime,
                        receivedEndTime,String.valueOf(user_obj.getTeamId()));
     */
}
