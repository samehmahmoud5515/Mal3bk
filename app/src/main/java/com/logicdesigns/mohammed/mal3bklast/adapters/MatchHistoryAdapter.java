package com.logicdesigns.mohammed.mal3bklast.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.logicdesigns.mohammed.mal3bklast.Dialogs.InfoDialog;
import com.logicdesigns.mohammed.mal3bklast.ExceptionHandling.ExceptionHandling;
import com.logicdesigns.mohammed.mal3bklast.JSON_PARSER.Cities_Parser;
import com.logicdesigns.mohammed.mal3bklast.Models.City;
import com.logicdesigns.mohammed.mal3bklast.Models.MatchHistory;
import com.logicdesigns.mohammed.mal3bklast.Models.TeamComingMatches;
import com.logicdesigns.mohammed.mal3bklast.R;
import com.logicdesigns.mohammed.mal3bklast.SharedPref.PrefManager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import BusinessClasses.User;
import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

/**
 * Created by logicDesigns on 10/8/2017.
 */






        import android.app.Activity;
        import android.app.Dialog;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.graphics.Color;
        import android.graphics.Typeface;
        import android.graphics.drawable.ColorDrawable;
        import android.preference.PreferenceManager;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.view.ContextThemeWrapper;
        import android.support.v7.widget.CardView;
        import android.support.v7.widget.RecyclerView;
        import android.view.Gravity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;

        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.StringRequest;
        import com.android.volley.toolbox.Volley;
        import com.google.gson.Gson;
        import com.logicdesigns.mohammed.mal3bklast.ExceptionHandling.ExceptionHandling;
        import com.logicdesigns.mohammed.mal3bklast.JSON_PARSER.Cities_Parser;
        import com.logicdesigns.mohammed.mal3bklast.Main2Activity;
        import com.logicdesigns.mohammed.mal3bklast.Models.City;
        import com.logicdesigns.mohammed.mal3bklast.Models.PlayerComingMatches;
        import com.logicdesigns.mohammed.mal3bklast.Models.TeamComingMatches;
        import com.logicdesigns.mohammed.mal3bklast.R;
        import com.logicdesigns.mohammed.mal3bklast.SharedPref.PrefManager;
        import com.logicdesigns.mohammed.mal3bklast.common.FragmentsTags;
        import com.squareup.picasso.Picasso;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;

        import BusinessClasses.User;
        import cc.cloudist.acplibrary.ACProgressConstant;
        import cc.cloudist.acplibrary.ACProgressFlower;

/**
 * Created by logicDesigns on 10/7/2017.
 */



        import android.app.Activity;
        import android.app.Dialog;
        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.graphics.Color;
        import android.graphics.Typeface;
        import android.graphics.drawable.ColorDrawable;
        import android.preference.PreferenceManager;
        import android.support.v7.widget.CardView;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import java.util.ArrayList;
        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.StringRequest;
        import com.android.volley.toolbox.Volley;
        import com.google.gson.Gson;
        import com.logicdesigns.mohammed.mal3bklast.ExceptionHandling.ExceptionHandling;
        import com.logicdesigns.mohammed.mal3bklast.Main2Activity;
        import com.logicdesigns.mohammed.mal3bklast.Models.PlayerComingMatches;
        import com.logicdesigns.mohammed.mal3bklast.R;
        import com.logicdesigns.mohammed.mal3bklast.common.FragmentsTags;
        import com.logicdesigns.mohammed.mal3bklast.fragments.PlayerComingMatchesFragment;
        import com.squareup.picasso.Picasso;

        import org.json.JSONException;
        import org.json.JSONObject;

        import BusinessClasses.User;
        import cc.cloudist.acplibrary.ACProgressConstant;
        import cc.cloudist.acplibrary.ACProgressFlower;

/**
 * Created by logicDesigns on 10/3/2017.
 */


public class MatchHistoryAdapter extends RecyclerView.Adapter<MatchHistoryAdapter.ViewHolder>
{

    ArrayList<MatchHistory> matchesList;
    Context mContext;
    SharedPreferences sharedPref;
    String language = null;
    Typeface fontAwesome, dinNext;

    public MatchHistoryAdapter(ArrayList<MatchHistory> model,Context context) {
        try {
            this.matchesList = model;
            this.mContext = context;
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
    public MatchHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.match_history_row, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MatchHistoryAdapter.ViewHolder viewHolder, final int i) {

        if (matchesList.get(i).getPlaygroundImage().equals(""))
        {viewHolder.playground_img.setImageResource(R.drawable.playgroundphoto);}
        else {
            Picasso.
                    with(this.mContext).
                    load(matchesList.get(i).getPlaygroundImage())
                    //.memoryPolicy(MemoryPolicy.NO_CACHE)
                    // .networkPolicy(NetworkPolicy.NO_CACHE)
                    .error(R.drawable.placeholder_user_photo)
                    .placeholder(R.drawable.placeholder_user_photo)
                    .into(viewHolder.playground_img);
        }

        viewHolder.playground_name_tv.setText(matchesList.get(i).getPlaygroundName());



        viewHolder.playground_name_tv.setTypeface(dinNext);
        viewHolder.date_ic.setTypeface(fontAwesome);
        if (language.equals("ar"))
            viewHolder.date_tv.setText("  التاريخ:  "+matchesList.get(i).getMatchDate() );
        else viewHolder.date_tv.setText("  Date:  "+matchesList.get(i).getMatchDate());
        viewHolder.date_tv.setTypeface(dinNext);
        viewHolder.time_ic.setTypeface(fontAwesome);
        if (language.equals("ar"))
            viewHolder.time_tv.setText("  من:  "+convertTimeto_12(matchesList.get(i).getFrom()) + "  إلي:  " +convertTimeto_12(matchesList.get(i).getTo()));
        else   viewHolder.time_tv.setText( "  From:  "+convertTimeto_12(matchesList.get(i).getFrom())+"  To:  " +convertTimeto_12(matchesList.get(i).getTo()) );

        viewHolder.time_tv.setTypeface(dinNext);

        viewHolder.teamaganist_ic.setTypeface(fontAwesome);
        viewHolder.teamaganist_tv.setTypeface(dinNext);
        if (language.equals("ar"))
            viewHolder.teamaganist_tv.setText(matchesList.get(i).getTeamName2()+"  :الفريق المنافس  ");
        else  viewHolder.teamaganist_tv.setText("  Aganist Team:  "+matchesList.get(i).getTeamName2());





        if(matchesList.get(i).getTeamName2().equals(""))
        {
            viewHolder.teamaganist_tv.setVisibility(View.GONE);
            viewHolder.teamaganist_ic.setVisibility(View.GONE);
        }
        else {
            viewHolder.teamaganist_tv.setVisibility(View.VISIBLE);
            viewHolder.teamaganist_ic.setVisibility(View.VISIBLE);

        }

        viewHolder.teamwith_ic.setTypeface(fontAwesome);
        if (language.equals("ar"))
            viewHolder.teamwith_tv.setText(matchesList.get(i).getTeamName1()+"  :مع الفريق  ");
        else   viewHolder.teamwith_tv.setText("  With team:  "+matchesList.get(i).getTeamName1());

        viewHolder.teamwith_tv.setTypeface(dinNext);

        viewHolder.result_tv.setTypeface(dinNext);
        if (language.equals("ar"))
            viewHolder.result_tv.setText("النتيجة");
       else     viewHolder.result_tv.setText("Result");

        //   result+=matchesList.get(i).getTeamName1()+"               "+matchesList.get(i).getTeamName2()+"\n";
    //    result+=matchesList.get(i).getPoints1()+"               "+matchesList.get(i).getPoints2();

        viewHolder.team1_tv_table.setText(matchesList.get(i).getTeamName1());
        viewHolder.team2_tv_table.setText(matchesList.get(i).getTeamName2());
        viewHolder.team1Points_tv_table.setText(matchesList.get(i).getPoints1());
        viewHolder.team2Points_tv_table.setText(matchesList.get(i).getPoints2());

        if (matchesList.get(i).getPoints2().equals("")&&matchesList.get(i).getPoints1().equals(""))
        {
          viewHolder.tableLayout_res.setVisibility(View.GONE);
        }
        else viewHolder.tableLayout_res.setVisibility(View.VISIBLE);

        viewHolder.playground_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoDialog infoDialog = new InfoDialog(mContext);

              infoDialog.showPlaygroundDetails(matchesList.get(i).getPlaygroundImage(),matchesList.get(i)
                .getPlaygroundName(),matchesList.get(i).getCity(),
                        matchesList.get(i).getAddress());
            }
        });
        viewHolder.playground_name_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoDialog infoDialog = new InfoDialog(mContext);
                infoDialog. showPlaygroundDetails(matchesList.get(i).getPlaygroundImage(),matchesList.get(i)
                                .getPlaygroundName(),matchesList.get(i).getCity(),
                        matchesList.get(i).getAddress());
            }
        });

    }

    @Override
    public int getItemCount() {
        return matchesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView playground_img;
        private TextView playground_name_tv,date_ic
                ,date_tv,time_ic,time_tv,teamaganist_ic
                ,teamaganist_tv,teamwith_ic,teamwith_tv,result_tv;
        TextView team1_tv_table,team2_tv_table,team1Points_tv_table,team2Points_tv_table;
        TableLayout tableLayout_res;

        public ViewHolder(View view) {
            super(view);
            playground_img = (ImageView) view.findViewById(R.id.playground_img);
            playground_name_tv = (TextView) view.findViewById(R.id.playground_name_tv);
            date_ic = (TextView) view.findViewById(R.id.date_ic);
            date_tv = (TextView) view.findViewById(R.id.date_tv);
            time_ic = (TextView) view.findViewById(R.id.time_ic);
            time_tv = (TextView) view.findViewById(R.id.time_tv);
            teamaganist_ic = (TextView) view.findViewById(R.id.teamaganist_ic);
            teamaganist_tv = (TextView) view.findViewById(R.id.teamaganist_tv);

            teamwith_ic = (TextView) view.findViewById(R.id.teamwith_ic);
            teamwith_tv = (TextView) view.findViewById(R.id.teamwith_tv);
            result_tv = (TextView) view.findViewById(R.id.result_tv);
            team1_tv_table =(TextView) view.findViewById(R.id.team1_tv_table) ;
            team2_tv_table =(TextView) view.findViewById(R.id.team2_tv_table) ;
            team1Points_tv_table =(TextView) view.findViewById(R.id.team1Points_tv_table) ;
            team2Points_tv_table =(TextView) view.findViewById(R.id.team2Points_tv_table) ;
            tableLayout_res =(TableLayout) view.findViewById(R.id.tableLayout_res);

            LinearLayout notification_relative =(LinearLayout) view.findViewById(R.id.matches_coming);
            CardView cardView = (CardView) view.findViewById(R.id.matches_card);
            if (language.equals("en")) {
                view.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                cardView.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                notification_relative.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                playground_name_tv.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

            }
            else {

                playground_name_tv.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                result_tv.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);

            }

        }
    }



    String convertTimeto_12(String _time_)
    {
        int time  = Integer.parseInt(_time_);
        String convertedTime;
        if (time>12) {
            time -= 12;
            convertedTime=String.valueOf(time) + "PM";
        }
        else convertedTime = String.valueOf(time)+"AM";


        return convertedTime;
    }





}