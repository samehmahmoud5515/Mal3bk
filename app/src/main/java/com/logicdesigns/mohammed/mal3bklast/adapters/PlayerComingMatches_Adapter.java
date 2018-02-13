package com.logicdesigns.mohammed.mal3bklast.adapters;
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
import com.logicdesigns.mohammed.mal3bklast.Dialogs.InfoDialog;
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


public class PlayerComingMatches_Adapter extends RecyclerView.Adapter<PlayerComingMatches_Adapter.ViewHolder>
{

    ArrayList<PlayerComingMatches>  matchesList;
    Context mContext;
    SharedPreferences sharedPref;
    String language = null;
    Typeface fontAwesome, dinNext;

    public PlayerComingMatches_Adapter(ArrayList<PlayerComingMatches> model,Context context) {
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
    public PlayerComingMatches_Adapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.player_coming_matches_row, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlayerComingMatches_Adapter.ViewHolder viewHolder, final int i) {

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


        viewHolder.alert_conflict_textview.setTypeface(dinNext);

        viewHolder.playground_name_tv.setTypeface(dinNext);
        viewHolder.date_ic.setTypeface(fontAwesome);
        if (language.equals("ar"))
        viewHolder.date_tv.setText("  التاريخ:  "+matchesList.get(i).getBookdate() );
        else viewHolder.date_tv.setText("  Date:  "+matchesList.get(i).getBookdate());
        viewHolder.date_tv.setTypeface(dinNext);
        viewHolder.time_ic.setTypeface(fontAwesome);
        if (language.equals("ar"))
        viewHolder.time_tv.setText("  من:  "+convertTimeto_12(matchesList.get(i).getFrom()) + "  إلي:  " + convertTimeto_12(matchesList.get(i).getTo()));
       else   viewHolder.time_tv.setText( "  From:  "+ convertTimeto_12(matchesList.get(i).getFrom())+"  To:  " + convertTimeto_12(matchesList.get(i).getTo() ));

        viewHolder.time_tv.setTypeface(dinNext);
        viewHolder.teamwith_ic.setTypeface(fontAwesome);
        if (language.equals("ar"))
            viewHolder.teamwith_tv.setText(matchesList.get(i).getTeamName1()+"  :مع الفريق  ");
        else   viewHolder.teamwith_tv.setText("  With team:  "+matchesList.get(i).getTeamName1());

        viewHolder.teamwith_tv.setTypeface(dinNext);
        viewHolder.teamaganist_ic.setTypeface(fontAwesome);
        viewHolder.teamaganist_tv.setTypeface(dinNext);
        if (language.equals("ar"))
        viewHolder.teamaganist_tv.setText(matchesList.get(i).getTeamName2()+"  :ضدد الفريق  ");
        else  viewHolder.teamaganist_tv.setText("  Aganist Team:  "+matchesList.get(i).getTeamName2());

        viewHolder.teamwith_btn.setTypeface(dinNext);
        viewHolder.teamaganist_btn.setTypeface(dinNext);
        if (matchesList.get(i).getPlayerintheTwoteams().equals("0")&&
                matchesList.get(i).getHasOnthermatchAlreadyAtthattime().equals("0")&&
                matchesList.get(i).getConfirmedThisMatch().equals("0"))
        {
            viewHolder.rightOrWrong.setVisibility(View.GONE);
            viewHolder.teamaganist_btn.setVisibility(View.GONE);
            viewHolder.teamwith_btn.setVisibility(View.VISIBLE);
            if (language.equals("ar"))
            viewHolder.teamwith_btn.setText("تأكيد اللعب في المبارة");
            else
                viewHolder.teamwith_btn.setText("Confirm playing in this match");

            viewHolder.alert_conflict_textview.setVisibility(View.GONE);
        }
        else if(matchesList.get(i).getPlayerintheTwoteams().equals("1")&&
                matchesList.get(i).getHasOnthermatchAlreadyAtthattime().equals("0")
                &&matchesList.get(i).getConfirmedThisMatch().equals("0"))
        {
            viewHolder.rightOrWrong.setVisibility(View.GONE);
            viewHolder.teamaganist_btn.setVisibility(View.VISIBLE);
            viewHolder.teamwith_btn.setVisibility(View.VISIBLE);
            if (language.equals("ar"))
            {viewHolder.teamwith_btn.setText("تأكيد اللعب مع الفريق الاول");
                viewHolder.teamwith_tv.setText(matchesList.get(i).getTeamName1()+"  :الفريق الاول  ");
                viewHolder.teamaganist_tv.setText(matchesList.get(i).getTeamName2()+"  :الفريق الثاني  ");
                viewHolder.teamaganist_btn.setText("تأكيد اللعب مع الفريق الثاني");

            }
            else { viewHolder.teamwith_btn.setText("Confirm playing with First Team");
                viewHolder.teamwith_tv.setText("  First Team:  "+matchesList.get(i).getTeamName1());
                viewHolder.teamaganist_tv.setText(" Second Team: "+matchesList.get(i).getTeamName2());
                viewHolder.teamaganist_btn.setText("Confirm playing with second team");

            }



            viewHolder.alert_conflict_textview.setVisibility(View.GONE);
        }
        else  if (matchesList.get(i).getConfirmedThisMatch().equals("1"))
        {
            viewHolder.rightOrWrong.setVisibility(View.VISIBLE);
            viewHolder.rightOrWrong.setImageResource(R.drawable.ic_right);
            viewHolder.teamaganist_btn.setVisibility(View.GONE);
            viewHolder.teamwith_btn.setVisibility(View.GONE);
            viewHolder.alert_conflict_textview.setVisibility(View.GONE);

        }
        else if (matchesList.get(i).getHasOnthermatchAlreadyAtthattime().equals("1")&&
                matchesList.get(i).getConfirmedThisMatch().equals("2"))
        {
            viewHolder.rightOrWrong.setVisibility(View.VISIBLE);
            viewHolder.teamaganist_btn.setVisibility(View.GONE);
            viewHolder.teamwith_btn.setVisibility(View.GONE);
            viewHolder.rightOrWrong.setImageResource(R.drawable.ic_wrong);
            viewHolder.alert_conflict_textview.setVisibility(View.VISIBLE);
            if (language.equals("ar"))
                viewHolder.alert_conflict_textview.setText("لديك مباراة في نفس الوقت لا يمكنك اللعب في المباراة");
            else
                viewHolder.alert_conflict_textview.setText("You cannot play in this match as you've another match at the same time");

        }
        if(matchesList.get(i).getTeamName2().equals(""))
        {
            viewHolder.teamaganist_tv.setVisibility(View.GONE);
            viewHolder.teamaganist_ic.setVisibility(View.GONE);
        }
        else {
            viewHolder.teamaganist_tv.setVisibility(View.VISIBLE);
            viewHolder.teamaganist_ic.setVisibility(View.VISIBLE);

        }
        if (matchesList.get(i).getHasTwoMatchesInsameTime().equals("1")&&
                matchesList.get(i).getHasOnthermatchAlreadyAtthattime().equals("0")
                )
        {
            viewHolder.alert_conflict_textview.setVisibility(View.VISIBLE);
            if (language.equals("ar"))
            viewHolder.alert_conflict_textview.setText("لديك مبارتان في نفس الوقت");
            else viewHolder.alert_conflict_textview.setText("You've another match at the same time");
        }
       /* else {
            viewHolder.alert_conflict_textview.setVisibility(View.GONE);

        }*/
        viewHolder.teamwith_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmMatch(matchesList.get(i).getMatchId(),matchesList.get(i).getTeamId1());
            }
        });
        viewHolder.teamaganist_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmMatch(matchesList.get(i).getMatchId(),matchesList.get(i).getTeamId2());

            }
        });
        viewHolder.playground_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InfoDialog infoDialog = new InfoDialog(mContext);
                infoDialog. showPlaygroundDetails(matchesList.get(i).getPlaygroundImage(),matchesList.get(i)
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
        private  TextView alert_conflict_textview,playground_name_tv,date_ic
                ,date_tv,time_ic,time_tv,teamwith_ic,teamwith_tv,teamaganist_ic
                ,teamaganist_tv;
        private Button teamwith_btn,teamaganist_btn;
        private ImageButton rightOrWrong;
        public ViewHolder(View view) {
            super(view);
            playground_img = (ImageView) view.findViewById(R.id.playground_img);
            alert_conflict_textview = (TextView) view.findViewById(R.id.alert_conflict_textview);
            playground_name_tv = (TextView) view.findViewById(R.id.playground_name_tv);
            date_ic = (TextView) view.findViewById(R.id.date_ic);
            date_tv = (TextView) view.findViewById(R.id.date_tv);
            time_ic = (TextView) view.findViewById(R.id.time_ic);
            time_tv = (TextView) view.findViewById(R.id.time_tv);
            teamwith_ic = (TextView) view.findViewById(R.id.teamwith_ic);
            teamwith_tv = (TextView) view.findViewById(R.id.teamwith_tv);
            teamaganist_ic = (TextView) view.findViewById(R.id.teamaganist_ic);
            teamaganist_tv = (TextView) view.findViewById(R.id.teamaganist_tv);
            teamwith_btn =(Button) view.findViewById(R.id.teamwith_btn);
            teamaganist_btn =(Button) view.findViewById(R.id.teamaganist_btn);
            rightOrWrong = (ImageButton) view.findViewById(R.id.rightOrWrong);

            LinearLayout notification_relative =(LinearLayout) view.findViewById(R.id.matches_coming);
          CardView  cardView = (CardView) view.findViewById(R.id.matches_card);
            if (language.equals("en")) {
                view.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                cardView.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                notification_relative.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                playground_name_tv.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

            }
            else {

                playground_name_tv.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            }

        }
    }

    public void confirmMatch(final String matchId, String teamId)
    {
        final ACProgressFlower flower_dialog = new ACProgressFlower.Builder(mContext)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.GREEN).build();
        flower_dialog.show();
        Gson gson = new Gson();
        String json1 = sharedPref.getString("userObject", "");
        User user_obj = gson.fromJson(json1, User.class);
        // Instantiate the RequestQueue.
        final RequestQueue queue = Volley.newRequestQueue(mContext);
        String url ="https://logic-host.com/work/kora/beta/phpFiles/confirmMatchAPI.php?key=logic123&id="+user_obj.getId()+"&matchId="+matchId+"&teamId="+teamId;
        final ExceptionHandling dialoggenerator = new ExceptionHandling(mContext);

// Request a string response from the provided URL.
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        flower_dialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                         if(! jsonObject.isNull("success")&& jsonObject.has("success"))
                         {
                             if (Integer.parseInt(jsonObject.getString("success"))==1)
                             {

                                 showDialog("تم تأكيد العب في المبارة","You've confirmed playing in the match");
                             }
                         }
                            else {
                             dialoggenerator.showErrorDialog();
                         }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            dialoggenerator.showErrorDialog();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialoggenerator.showErrorDialog();
                flower_dialog.dismiss();
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void showDialog(String arString,String enString)
    {
        final Activity mActivity = (Activity) mContext;

        final Dialog dialog = new Dialog(mActivity, R.style.CustomDialogTheme);
        dialog.setContentView(R.layout.no_playgrounds_custom_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView no_message_text = (TextView) dialog.findViewById(R.id.no_message_text);
        no_message_text.setTypeface(dinNext);

        Button close_button = (Button) dialog.findViewById(R.id.btn_close_no_playground);
        close_button.setTypeface(dinNext);

        if (language.equals("en")) {
            close_button.setText(R.string.close);
            no_message_text.setText(enString);

        }
        else{
            close_button.setText(R.string.close_ar);
            no_message_text.setText(arString);
        }
        close_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.cancel();
                //  mActivity.onBackPressed();
                mActivity.finish();
                Intent intent = new Intent(mActivity, Main2Activity.class);
                intent.putExtra(FragmentsTags.FragemntTag, FragmentsTags.ComingMatchesFragment);
                mActivity.startActivity(intent);


            }
        });
        dialog.show();
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