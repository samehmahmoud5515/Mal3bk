package com.logicdesigns.mohammed.mal3bklast.adapters;

import android.annotation.SuppressLint;
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
import android.util.Log;
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
import com.logicdesigns.mohammed.mal3bklast.Dialogs.InfoDialog;
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


public class TeamComingMatchesAdapter extends RecyclerView.Adapter<TeamComingMatchesAdapter.ViewHolder>
{

    ArrayList<TeamComingMatches> matchesList;
    Context mContext;
    SharedPreferences sharedPref;
    String language = null;
    Typeface fontAwesome, dinNext;

    public TeamComingMatchesAdapter(ArrayList<TeamComingMatches> model,Context context) {
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
    public TeamComingMatchesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.teamcomingmatches_row, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TeamComingMatchesAdapter.ViewHolder viewHolder, final int i) {

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
            viewHolder.date_tv.setText("  التاريخ:  "+matchesList.get(i).getBookdate() );
        else viewHolder.date_tv.setText("  Date:  "+matchesList.get(i).getBookdate());
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

        viewHolder.teamaganist_btn.setTypeface(dinNext);




        if(matchesList.get(i).getTeamName2().equals(""))
        {
            viewHolder.teamaganist_tv.setVisibility(View.GONE);
            viewHolder.teamaganist_ic.setVisibility(View.GONE);
        }
        else {
            viewHolder.teamaganist_tv.setVisibility(View.VISIBLE);
            viewHolder.teamaganist_ic.setVisibility(View.VISIBLE);

        }
        if (matchesList.get(i).getChooseTeamButton().equals("1"))
        {
            viewHolder.teamaganist_btn.setVisibility(View.VISIBLE);

        }
        else {
            viewHolder.teamaganist_btn.setVisibility(View.GONE);

        }

        viewHolder.teamaganist_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCities(matchesList.get(i).getMatchId());
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
        private TextView playground_name_tv,date_ic
                ,date_tv,time_ic,time_tv,teamaganist_ic
                ,teamaganist_tv;
        private Button teamaganist_btn;

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
            teamaganist_btn =(Button) view.findViewById(R.id.teamaganist_btn);

            LinearLayout notification_relative =(LinearLayout) view.findViewById(R.id.matches_coming);
            CardView cardView = (CardView) view.findViewById(R.id.matches_card);
            if (language.equals("en")) {
                view.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                cardView.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                notification_relative.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                playground_name_tv.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                teamaganist_btn.setText("Add opponent Team");

            }
            else {

                playground_name_tv.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            }

        }
    }


    public void showCities(String matchId)
    {
        PrefManager prefManager = new PrefManager(mContext);
        String cities = prefManager.getCITIES();
        Cities_Parser parser = new Cities_Parser(mContext);
        ArrayList<City>  cityList = new ArrayList<City>();

        cityList = parser.parse(cities);

        String[] city_arr = new String[cityList.size() + 1];
        String[] cityIds = new String[cityList.size() + 1];
        cityIds[0] = "-1";

        for (int i = 0; i < cityList.size(); i++) {
            city_arr[i+1] = cityList.get(i).getCityName();
            cityIds[i+1] = cityList.get(i).getCityID();
        }
        if (language.equals("en")) {
            city_arr[0] = "All";
            drawDialog_Cities("City", city_arr,cityIds,matchId);
        } else {
            city_arr[0] = "الكل";
            drawDialog_Cities("الموقع", city_arr,cityIds,matchId);
        }
    }

    public void addTeamAgainst(final String matchId, String cityId)
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
        String url ="https://logic-host.com/work/kora/beta/phpFiles/getAvailableTeamAPI.php?key=logic123&reserveId="+matchId
                +"&city="+cityId;
        final ExceptionHandling dialoggenerator = new ExceptionHandling(mContext);

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
                                  Teams4matches teams4matches = new Teams4matches();
                                   ArrayList<Teams4matches>   teamList = new ArrayList<>();
                                   teamList = teams4matches.parseJson(response);
                                    String [] teams_arr  = new String[teamList.size()];
                                    String [] teams_id_arr  = new String[teamList.size()];

                                    for(int i=0;i<teamList.size();i++)
                                    {
                                        if (language.equals("ar"))
                                       teams_arr [i]= "فريق: "+teamList.get(i).getName() +" - الكابتن: "+
                                               teamList.get(i).getCaptain()
                                               //+ " عدد اللاعبين: " + teamList.get(i).getPlayersNumber()
                                       ;
                                        else
                                            teams_arr [i]= "Team: "+teamList.get(i).getName() +" - Captain: "+
                                                    teamList.get(i).getCaptain()
                                                    //+ " عدد اللاعبين: " + teamList.get(i).getPlayersNumber()
                                                    ;
                                        teams_id_arr[i] = teamList.get(i).getId();
                                    }
                                    if (language.equals("ar"))
                                    drawDialog_Teams("الفرق المتاحة",teams_arr,teams_id_arr,matchId);
                                    else
                                        drawDialog_Teams("Available Teams",teams_arr,teams_id_arr,matchId);

                                }
                                else {
                                    ExceptionHandling exceptionHandling = new ExceptionHandling(mContext);
                                    exceptionHandling.showErrorDialog("لا توجد فرق في هذه المدينة","No Teams found in that country");
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

    void drawDialog_Teams(String Tiltle, final String s[] , final String teamids[], final String matchId) {
        @SuppressLint("RestrictedApi") AlertDialog.Builder dialog = new AlertDialog.Builder(new ContextThemeWrapper(mContext, R.style.CustomDialogTheme));
        TextView title = new TextView(mContext);

        title.setText(Tiltle);
        title.setBackgroundColor(Color.parseColor("#008542"));


        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(20);
        title.setTypeface(dinNext);

        dialog.setCustomTitle(title);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                R.layout.choice_item, R.id.text_view_choice, s) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(R.id.text_view_choice);
                text.setTypeface(dinNext);
                return view;
            }
        };
        dialog.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

             //https://logic-host.com/work/kora/beta/phpFiles/chooseTeamForMatchAPI.php?key=logic123&teamId2=2&reserveId=79

                Gson gson = new Gson();
                String json1 = sharedPref.getString("userObject", "");
                User user_obj = gson.fromJson(json1, User.class);
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(mContext);
                String url ="https://logic-host.com/work/kora/beta/phpFiles/chooseTeamForMatchAPI.php?key=logic123&teamId2="+teamids[which]+"&reserveId="+matchId+
                        "&id="+user_obj.getId();
                Log.d("chooseTeamForMatchAPI",url);

// Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    ExceptionHandling exceptionHandling = new ExceptionHandling(mContext);
                                    if (Integer.parseInt(jsonObject.getString("success"))==1)
                                    {
                                       exceptionHandling.showErrorDialog("تم ارسال طلبك","Your request has been sent");
                                    }
                                    else if (Integer.parseInt(jsonObject.getString("success"))==0)
                                    {
                                        exceptionHandling.showErrorDialog("حدث خطأ","Error Happened");

                                    }
                                    else if (Integer.parseInt(jsonObject.getString("success"))==-1)
                                    {
                                        exceptionHandling.showErrorDialog("الفريق لديه مبارة في هذا الوقت","This team has a match at the same time");

                                    }
                                    else if (Integer.parseInt(jsonObject.getString("success"))==-4)
                                    {
                                      exceptionHandling.showErrorDialog("لقد ارسلت طلب من قبل","You've sent request before");
                                    }
                                    else if (Integer.parseInt(jsonObject.getString("success"))==-3)
                                    {
                                        exceptionHandling.showErrorDialog("لا يوجد ماتش في هذا الوقت","You don't have match at that time");
                                    }
                                    else if (Integer.parseInt(jsonObject.getString("success"))==-3)
                                    {
                                        exceptionHandling.showErrorDialog("لا يوجد فريق انشئ فريق اولا","You don't have a team, Create it first");
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    try{
                                    ExceptionHandling exceptionHandling = new ExceptionHandling(mContext);
                                    exceptionHandling.showErrorDialog();}catch (Exception e1){e1.printStackTrace();}
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
        });


        AlertDialog alert = dialog.create();

        alert.show();
    }

    void drawDialog_Cities(String Tiltle, final String s[] , final String [] cityIds, final String matchId) {
        @SuppressLint("RestrictedApi") AlertDialog.Builder dialog = new AlertDialog.Builder(new ContextThemeWrapper(mContext, R.style.CustomDialogTheme));
        TextView title = new TextView(mContext);

        title.setText(Tiltle);
        title.setBackgroundColor(Color.parseColor("#008542"));


        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(20);
        title.setTypeface(dinNext);

        dialog.setCustomTitle(title);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                R.layout.choice_item, R.id.text_view_choice, s) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(R.id.text_view_choice);
                text.setTypeface(dinNext);
                return view;
            }
        };
        dialog.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                addTeamAgainst(matchId,cityIds[which]);
            }
        });


        AlertDialog alert = dialog.create();

        alert.show();
    }

    private  class Teams4matches{

        String id, name, captain,playersNumber;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPlayersNumber() {
            return playersNumber;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCaptain() {
            return captain;
        }


        public Teams4matches(){}
        public Teams4matches(String id, String name , String captain,String playersNumber){
            this.id =id;
            this.name= name;
            this.captain = captain;
            this.playersNumber =playersNumber;
        }

        public ArrayList<Teams4matches> parseJson(String jsonString) throws JSONException {
            ArrayList<Teams4matches> list = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("team");
            for(int i=0;i<jsonArray.length();i++)
            {
                JSONObject innerObj =jsonArray.getJSONObject(i);
                if (! innerObj.isNull("id")&& innerObj.has("id")&&
                        ! innerObj.isNull("name")&& innerObj.has("name") &&
                        ! innerObj.isNull("Captain")&& innerObj.has("Captain") &&
                        !innerObj.isNull("numberOfplayer")&& innerObj.has("numberOfplayer")
                        )
                {
                    Teams4matches team = new Teams4matches(innerObj.getString("id"),
                            innerObj.getString("name"),
                            innerObj.getString("Captain"),
                            innerObj.getString("numberOfplayer")
                            );
                   list.add(team);
                }
            }
            return  list;
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