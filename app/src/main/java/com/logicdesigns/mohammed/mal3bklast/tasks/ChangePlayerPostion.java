package com.logicdesigns.mohammed.mal3bklast.tasks;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.logicdesigns.mohammed.mal3bklast.ExceptionHandling.ExceptionHandling;
import com.logicdesigns.mohammed.mal3bklast.Models.PlayerPositions;
import com.logicdesigns.mohammed.mal3bklast.R;
import com.logicdesigns.mohammed.mal3bklast.SharedPref.PrefManager;
import com.logicdesigns.mohammed.mal3bklast.appFonts.AppFonts;
import com.logicdesigns.mohammed.mal3bklast.common.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

/**
 * Created by logicDesigns on 1/24/2018.
 */

public class ChangePlayerPostion {
    Context context;
    public ChangePlayerPostion(Context context)
    {
        this.context = context;
    }
    ArrayList<PlayerPositions> playerPositionsArrayList = new ArrayList<>();
    String language ;
    public void changePosition(String id,String playerId)
    {

        try {
            PrefManager prefManager = new PrefManager(context);
            language = prefManager.getLanguage();
            String postions = prefManager.getPlayers_Postions();
            PlayerPositions playerPositions = new PlayerPositions();
            playerPositionsArrayList = playerPositions.parsePlayerPostions(postions,context);
            String[] postions_arr = new String[playerPositionsArrayList.size()];
            for (int i = 0; i < playerPositionsArrayList.size(); i++) {
                postions_arr[i] = playerPositionsArrayList.get(i).getPostionName();
            }
            if (language.equals("en")) {

                drawDialog("Position", postions_arr,id,playerId);
            } else {


                drawDialog("المركز", postions_arr , id , playerId);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
   String selected_postion="";
    void drawDialog(String Tiltle, final String s[], final String id, final String p_id) {
        @SuppressLint("RestrictedApi") AlertDialog.Builder dialog = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.CustomDialogTheme));
        TextView title = new TextView(context);

        title.setText(Tiltle);
        title.setBackgroundColor(Color.parseColor("#008542"));
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(20);
        title.setTypeface(AppFonts.getInstance(context).getDIN_NEXT());

        dialog.setCustomTitle(title);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                R.layout.choice_item, R.id.text_view_choice, s) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(R.id.text_view_choice);
                text.setTypeface(AppFonts.getInstance(context).getDIN_NEXT());
                return view;
            }
        };
        dialog.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selected_postion = s[which] ;
                callApi(id,p_id);
            }
        });


        AlertDialog alert = dialog.create();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        alert.show();
    }

    void callApi(String id,String p_id )
    {
        final ACProgressFlower flower_dialog;
        flower_dialog = new ACProgressFlower.Builder(context).direction(ACProgressConstant.DIRECT_CLOCKWISE).themeColor(Color.WHITE).fadeColor(Color.GREEN).build();
         flower_dialog.show();
        ////id=38&myId=33&position=2
        final ExceptionHandling exceptionHandling = new ExceptionHandling(context);
        String posId;
        PlayerPositions pos =new PlayerPositions();
        posId = pos.getPostionIdbyName(playerPositionsArrayList,selected_postion);
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = URLs.ChangePostionAPI +"id="+p_id+"&myId="+id+"&position="+posId;

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                             flower_dialog.dismiss();
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("success").equals("1"))
                            {
                                exceptionHandling.showErrorDialog("تم التعديل","Done",1);
                            }
                            else {
                                exceptionHandling.showErrorDialog("حدث خطأ","Something wrong");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                flower_dialog.dismiss();
                exceptionHandling.showErrorDialog("حدث خطأ","Something wrong");

            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
