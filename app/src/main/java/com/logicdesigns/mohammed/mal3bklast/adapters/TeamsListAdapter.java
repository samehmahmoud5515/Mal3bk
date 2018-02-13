package com.logicdesigns.mohammed.mal3bklast.adapters;

import android.app.Activity;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.logicdesigns.mohammed.mal3bklast.Models.Team;
import com.logicdesigns.mohammed.mal3bklast.R;
import com.logicdesigns.mohammed.mal3bklast.common.URLs;
import com.logicdesigns.mohammed.mal3bklast.fragments.MyTeamPlayersFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import BusinessClasses.FormationData;
import BusinessClasses.User;
import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class TeamsListAdapter extends BaseAdapter {

    private Activity _context;
    private ArrayList<Team> TeamsList;
    private Typeface custom_font_Awesome, custom_Din;
    ArrayList<String> myTeamList;

    public TeamsListAdapter(Activity context, ArrayList<Team> players) {

        try {
            this._context = context;
            this.TeamsList = players;
            custom_font_Awesome = Typeface.createFromAsset(_context.getAssets(), "fonts/fontawesome-webfont.ttf");
            custom_Din = Typeface.createFromAsset(_context.getAssets(), "fonts/DIN Next LT W23 Regular.ttf");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return TeamsList.size();
    }

    @Override
    public Object getItem(int position) {
        return TeamsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertedview, ViewGroup parent) {
        Holder holder;

        if (convertedview == null) {
            LayoutInflater inflater = _context.getLayoutInflater();
            convertedview = inflater.inflate(R.layout.team_properties, null, true);
            holder = new Holder();
            holder.captain_ic = (TextView) convertedview.findViewById(R.id.captain_ic);
            holder.captin_value_tv = (TextView) convertedview.findViewById(R.id.captin_value_tv);
            holder.date_ic = (TextView) convertedview.findViewById(R.id.date_ic);
            holder.date_tv = (TextView) convertedview.findViewById(R.id.date_tv);
            holder.date_value_tv = (TextView) convertedview.findViewById(R.id.date_value_tv);
            holder.ic_num_players = (TextView) convertedview.findViewById(R.id.ic_num_players);
            holder.teamname_value_tv = (TextView) convertedview.findViewById(R.id.teamname_value_tv);
            holder.teamname_tv = (TextView) convertedview.findViewById(R.id.teamname_tv);
            holder.team_ic = (TextView) convertedview.findViewById(R.id.team_ic);
            holder.numberOfPlayer = (TextView) convertedview.findViewById(R.id.numberOfPlayer);
            holder.num_players_tv = (TextView) convertedview.findViewById(R.id.num_players_tv);
            holder.joinTeam_btn = (Button) convertedview.findViewById(R.id.joinTeam_btn);
            convertedview.setTag(holder);

        } else {
            //getTag
            holder = (Holder) convertedview.getTag();
        }
        final Team team = TeamsList.get(position);


        holder.captain_ic.setTypeface(custom_font_Awesome);
        holder.team_ic.setTypeface(custom_font_Awesome);
        holder.numberOfPlayer.setTypeface(custom_font_Awesome);
        holder.date_ic.setTypeface(custom_font_Awesome);
        holder.ic_num_players.setTypeface(custom_Din);

        holder.teamname_value_tv.setTypeface(custom_Din);
        holder.teamname_tv.setTypeface(custom_Din);
        holder.captin_value_tv.setTypeface(custom_Din);
        holder.date_value_tv.setTypeface(custom_Din);
        holder.date_tv.setTypeface(custom_Din);
        holder.num_players_tv.setTypeface(custom_Din);
        holder.joinTeam_btn.setTypeface(custom_Din);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(_context);
        final String language = sharedPref.getString("language", "");
        if (language.equals("en")) {
            holder.teamname_tv.setText(R.string.teamName);
            holder.date_tv.setText(R.string.date);
            holder.ic_num_players.setText(R.string.numberOfPlayer);
            holder.joinTeam_btn.setText(R.string.join);
            LinearLayout teamsLayout = (LinearLayout) convertedview.findViewById(R.id.teamsLayout);
            teamsLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }


        holder.teamname_value_tv.setText(team.getName());
        holder.captin_value_tv.setText(team.getCaptainName());
        holder.date_value_tv.setText(team.getDateOfCreation());
        holder.num_players_tv.setText(String.valueOf(team.getNumberofPlayers()));
        holder.joinTeam_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   Toast.makeText(_context,position,Toast.LENGTH_SHORT).show();
                final ACProgressFlower flower_dialog1;
                flower_dialog1 = new ACProgressFlower.Builder(_context)
                        .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                        .themeColor(Color.WHITE)
                        .fadeColor(Color.GREEN).build();
                flower_dialog1.show();
                SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(_context);
                final String json1 = mPrefs.getString("userObject", "");
                Gson gson = new Gson();
                User obj = gson.fromJson(json1, User.class);
                // Instantiate the RequestQueue.
                final RequestQueue queue = Volley.newRequestQueue(_context);
                String url = URLs.JoinTeam + "id=" + obj.getId() + "&tid=" + team.getCaptainId();
                Log.d("urlTeam", url);
// Request a string response from the provided URL.
                final StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    flower_dialog1.dismiss();
                                    final Dialog dialog = new Dialog(_context, R.style.CustomDialogTheme);
                                    dialog.setContentView(R.layout.no_playgrounds_custom_dialog);
                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                    dialog.setCancelable(true);
                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                    TextView no_message_text = (TextView) dialog.findViewById(R.id.no_message_text);
                                    no_message_text.setTypeface(custom_Din);

                                    Button ok_btn = (Button) dialog.findViewById(R.id.btn_close_no_playground);
                                    if (language.equals("en")) {
                                        ok_btn.setText(R.string.close);
                                    }
                                    ok_btn.setTypeface(custom_Din);
                                    ok_btn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.dismiss();
                                        }
                                    });
                                    JSONObject jsonObject = new JSONObject(response);
                                    String sssss  = jsonObject.getString("success");
                                    if (Integer.parseInt(jsonObject.getString("success")) == 1) {
                                        if (language.equals("en")) {
                                            no_message_text.setText(R.string.request);
                                        } else {
                                            no_message_text.setText("تم ارسال طلبك");
                                        }
                                    } else  if (Integer.parseInt(jsonObject.getString("success")) == 2) {
                                        Log.d("sucess " , jsonObject.getString("success"));
                                        if (language.equals("en")) {
                                            no_message_text.setText("You can't request your team");
                                        } else {
                                            no_message_text.setText("لا يمكنك الانضمام الي فريقك");
                                        }
                                    }
                                    else if (Integer.parseInt(jsonObject.getString("success")) == 3) {
                                        if (language.equals("en")) {
                                            no_message_text.setText("You can't");
                                        } else {
                                            no_message_text.setText("لا يمكنك");
                                        }
                                    }
                                    else if (Integer.parseInt(jsonObject.getString("success")) == 4) {
                                        if (language.equals("en")) {
                                            no_message_text.setText("You can't");
                                        } else {
                                            no_message_text.setText("لا يمكنك");
                                        }
                                    }
                                    else if (Integer.parseInt(jsonObject.getString("success")) == 5) {
                                        if (language.equals("en")) {
                                            no_message_text.setText("You can't request your team");
                                        } else {
                                            no_message_text.setText("لا يمكنك الانضمام الي فريقك");
                                        }
                                    }
                                    else if (Integer.parseInt(jsonObject.getString("success")) == 6) {
                                        if (language.equals("en")) {
                                            no_message_text.setText("You've sent the request before");
                                        } else {
                                            no_message_text.setText("لقد ارسلت طلب الانضمام من قبل");
                                        }
                                    }
                                    else if (Integer.parseInt(jsonObject.getString("success")) == 0){
                                        if (language.equals("en")) {
                                            no_message_text.setText("something wrong");
                                        } else {
                                            no_message_text.setText("حدث خطأ");
                                        }
                                    }
                                    dialog.show();

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
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                queue.add(stringRequest);

            }
        });
        convertedview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(_context);
                String url = "https://logic-host.com/work/kora/beta/phpFiles/getTeambyIdAPI.php?key=logic123&id="
                        + team.getId();

// Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Fragment myteamFragment = new MyTeamPlayersFragment();
                                Bundle bundle = new Bundle();

                                bundle.putString("one_team", response);
                                myteamFragment.setArguments(bundle);
                                FragmentTransaction ft = ((FragmentActivity) _context).getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.placeholder2, myteamFragment).addToBackStack(null).commit();

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

        return convertedview;

    }

    ;

    class Holder {
        // icons
        TextView team_ic, captain_ic, date_ic, ic_num_players,numberOfPlayer;
        // tv
        TextView teamname_tv, captin_value_tv, date_tv;
        //values
        TextView teamname_value_tv, date_value_tv, num_players_tv;

        Button joinTeam_btn;

    }
}