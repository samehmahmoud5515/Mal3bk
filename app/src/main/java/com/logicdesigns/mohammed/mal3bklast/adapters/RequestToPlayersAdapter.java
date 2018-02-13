package com.logicdesigns.mohammed.mal3bklast.adapters;

/**
 * Created by logicDesigns on 10/7/2017.
 */



import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.logicdesigns.mohammed.mal3bklast.GetPlayerFragment;
import com.logicdesigns.mohammed.mal3bklast.Models.RequestsToPlayers;

import java.util.ArrayList;

      import com.logicdesigns.mohammed.mal3bklast.R;
import com.logicdesigns.mohammed.mal3bklast.common.URLs;
import com.squareup.picasso.Picasso;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

/**
 * Created by Sameh on 4/21/2017.
 */

public class RequestToPlayersAdapter extends RecyclerView.Adapter<RequestToPlayersAdapter.ViewHolder>
{

    ArrayList<RequestsToPlayers>  requests;
    Context mContext;
    Typeface dinNext,fontAwesome;
    SharedPreferences sharedPref;
    String language = null;

    public RequestToPlayersAdapter(ArrayList<RequestsToPlayers> model,Context context) {
        try {
            this.requests = model;
            mContext = context;
            sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
            language = sharedPref.getString("language", "ar");
            fontAwesome = Typeface.createFromAsset(context.getAssets(), "fonts/fontawesome-webfont.ttf");
            dinNext = Typeface.createFromAsset(context.getAssets(), "fonts/DIN Next LT W23 Regular.ttf");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public RequestToPlayersAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.request_to_players_row, viewGroup, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RequestToPlayersAdapter.ViewHolder viewHolder, final int i) {

        if (requests.get(i).getImage().equals(""))
        {viewHolder.playerImage.setImageResource(R.drawable.placeholder_user_photo);}
        else {
            Picasso.
                    with(this.mContext).
                    load(requests.get(i).getImage())
                    //.memoryPolicy(MemoryPolicy.NO_CACHE)
                    // .networkPolicy(NetworkPolicy.NO_CACHE)
                    .error(R.drawable.placeholder_user_photo)
                    .placeholder(R.drawable.placeholder_user_photo)
                    .into(viewHolder.playerImage);
        }

        viewHolder.player_name_tv.setText(requests.get(i).getName());
        if (!requests.get(i).getPosition().equals("")) {
            viewHolder.postion_tv.setText(requests.get(i).getPosition());
            viewHolder.postion_tv.setVisibility(View.VISIBLE);
        }
        else{
            viewHolder.postion_tv.setVisibility(View.GONE);
        }
        if (!requests.get(i).getStars().equals("")||!requests.get(i).getStars().equals("0")) {
            viewHolder.request_Player_rating.setRating(Float.parseFloat(requests.get(i).getStars()));
            viewHolder.request_Player_rating.setVisibility(View.VISIBLE);
        }
        else {
            viewHolder.request_Player_rating.setVisibility(View.GONE);
        }

        viewHolder. request_btn_ic.setTypeface(fontAwesome);

        if (requests.get(i).getResponse().equals("1"))
        {
            viewHolder.request_btn.setVisibility(View.VISIBLE);
            viewHolder.response_container.setVisibility(View.VISIBLE);
            viewHolder.request_btn.setVisibility(View.VISIBLE);

           viewHolder.response_container.setBackgroundColor(Color.parseColor("#008542"));
          viewHolder.request_btn_ic.setText(Html.fromHtml("&#xf00c;"));
            viewHolder.request_btn.setBackgroundColor(Color.parseColor("#008542"));

            viewHolder.response_container.setBackgroundResource(R.drawable.response_state_cont);
            GradientDrawable drawable = (GradientDrawable)  viewHolder.response_container.getBackground();
            drawable.setColor(Color.parseColor("#008542"));
            if (language.equals("ar"))
            viewHolder.request_btn.setText("وافق");
            else  viewHolder.request_btn.setText("Agreed");
        }
        else if (requests.get(i).getResponse().equals("0"))
        {
            viewHolder.request_btn.setVisibility(View.VISIBLE);
            viewHolder.response_container.setVisibility(View.VISIBLE);
            viewHolder.request_btn.setVisibility(View.VISIBLE);

            viewHolder.response_container.setBackgroundColor(Color.parseColor("#e82929"));
          viewHolder.request_btn_ic.setText(Html.fromHtml("&#xf00d;"));
            viewHolder.request_btn.setBackgroundColor(Color.parseColor("#e82929"));
            viewHolder.response_container.setBackgroundResource(R.drawable.response_state_cont);
            GradientDrawable drawable = (GradientDrawable)  viewHolder.response_container.getBackground();
            drawable.setColor(Color.parseColor("#e82929"));
            if (language.equals("ar"))
                viewHolder.request_btn.setText("رفض");
            else viewHolder.request_btn.setText("Refused");
        }
        else if (requests.get(i).getResponse().equals("2")) {
            viewHolder.request_btn.setVisibility(View.VISIBLE);
            viewHolder.response_container.setVisibility(View.VISIBLE);
            viewHolder.request_btn.setVisibility(View.VISIBLE);

            viewHolder.response_container.setBackgroundColor(Color.parseColor("#9c9599"));
          viewHolder.request_btn_ic.setText(Html.fromHtml("&#xf252;"));
            viewHolder.request_btn.setBackgroundColor(Color.parseColor("#9c9599"));
            viewHolder.response_container.setBackgroundResource(R.drawable.response_state_cont);
            GradientDrawable drawable = (GradientDrawable)  viewHolder.response_container.getBackground();
            drawable.setColor(Color.parseColor("#9c9599"));
            if (language.equals("ar"))
                viewHolder.request_btn.setText("انتظر الرد");
            else  viewHolder.request_btn.setText("Pending");
        }
        else {
            viewHolder.request_btn.setVisibility(View.GONE);
            viewHolder.response_container.setVisibility(View.GONE);
            viewHolder.request_btn.setVisibility(View.GONE);
        }

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ACProgressFlower   flower_dialog = new ACProgressFlower.Builder(mContext)
                        .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                        .themeColor(Color.WHITE)
                        .fadeColor(Color.GREEN).build();
                flower_dialog.show();
                RequestQueue queue = Volley.newRequestQueue(mContext);
                String url = URLs.GET_Player_properties_by_id + "?key=logic123&id="
                        + requests.get(i).getId();
// Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.

                                flower_dialog.dismiss();
                                Bundle bundle = new Bundle();
                                bundle.putString("playersJson", response);
                                bundle.putString("hideFiler_", "1");

                                Fragment getPlayerFragment = new GetPlayerFragment();
                                getPlayerFragment.setArguments(bundle);
                                FragmentTransaction ft = ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.placeholder2, getPlayerFragment).addToBackStack(null).commit();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
// Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });

        if (!requests.get(i).getMatchDate().equals("")&&
                !requests.get(i).getFromMatch().equals("")&&!requests.get(i).getToMatch().equals("")
       && !requests.get(i).getFromMatch().equals("false")&&!requests.get(i).getToMatch().equals("false")
                )
        {
            viewHolder.date_tv.setVisibility(View.VISIBLE);
            viewHolder.date_ic.setVisibility(View.VISIBLE);
            viewHolder.time_tv.setVisibility(View.VISIBLE);
            viewHolder.time_ic.setVisibility(View.VISIBLE);
            if (language.equals("ar"))
                viewHolder.date_tv.setText("  التاريخ:  "+requests.get(i).getMatchDate() );
            else viewHolder.date_tv.setText("  Date:  "+requests.get(i).getMatchDate());

            if (language.equals("ar"))
                viewHolder.time_tv.setText("  من:  "+requests.get(i).getFromMatch() + "  إلي:  " +requests.get(i).getToMatch());
            else   viewHolder.time_tv.setText( "  From:  "+requests.get(i).getFromMatch()+"  To:  " +requests.get(i).getToMatch() );
        }
        else {
            viewHolder.date_tv.setVisibility(View.GONE);
            viewHolder.date_ic.setVisibility(View.GONE);
            viewHolder.time_tv.setVisibility(View.GONE);
            viewHolder.time_ic.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return requests.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView playerImage;
        private  TextView player_name_tv,postion_tv,request_btn_ic;
        RatingBar request_Player_rating;
        Button request_btn;
        LinearLayout response_container ;
        CardView cardView;

        TextView time_ic,time_tv,date_ic,date_tv;
        public ViewHolder(View view) {
            super(view);

            playerImage = (ImageView) view.findViewById(R.id.player_image_request);
            player_name_tv= (TextView)view.findViewById(R.id.player_name_tv);
            postion_tv =(TextView) view.findViewById(R.id.postion_tv);
            request_Player_rating = (RatingBar) view.findViewById(R.id.request_Player_rating);
            request_btn =(Button) view.findViewById(R.id.request_btn);
            request_btn_ic= (TextView)view.findViewById(R.id.request_btn_ic);
            response_container = (LinearLayout) view.findViewById(R.id.response_container);
            request_btn_ic.setTypeface(fontAwesome);
            player_name_tv.setTypeface(dinNext);
            postion_tv.setTypeface(dinNext);
            request_btn.setTypeface(dinNext);

            time_ic= (TextView)view.findViewById(R.id.time_ic);
            time_tv= (TextView)view.findViewById(R.id.time_tv);
            date_ic= (TextView)view.findViewById(R.id.date_ic);
            date_tv= (TextView)view.findViewById(R.id.date_tv);
            time_ic.setTypeface(fontAwesome);
            date_ic.setTypeface(fontAwesome);
            time_tv.setTypeface(dinNext); date_tv.setTypeface(dinNext);


            LinearLayout notification_relative =(LinearLayout) view.findViewById(R.id.linear_requests);
             cardView = (CardView) view.findViewById(R.id.cardView_requests);
            if (language.equals("en")) {
                view.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                cardView.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                notification_relative.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

            }

        }
    }

}