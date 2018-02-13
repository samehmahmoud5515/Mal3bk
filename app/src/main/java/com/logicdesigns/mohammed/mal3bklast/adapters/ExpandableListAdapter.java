package com.logicdesigns.mohammed.mal3bklast.adapters;


/**
 * Created by logicDesigns on 7/18/2017.
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.logicdesigns.mohammed.mal3bklast.GetPlayerFragment;
import com.logicdesigns.mohammed.mal3bklast.R;
import com.logicdesigns.mohammed.mal3bklast.common.URLs;
import com.logicdesigns.mohammed.mal3bklast.fragments.MyTeamPlayersFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import BusinessClasses.FormationData;
import BusinessClasses.User;
import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

import static com.facebook.FacebookSdk.getApplicationContext;


public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<FormationData>> _listDataChild;
    ViewHolder holder;
    ViewHolderGroup viewholder;

    int iscaptain;

    int index;

    Typeface custom_font_Awesome;
    Typeface custom_Din;

    String JsonString;
 //   ACProgressFlower flower_dialog;

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<FormationData>> listChildData, String json) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        custom_font_Awesome = Typeface.createFromAsset(_context.getAssets(), "fonts/fontawesome-webfont.ttf");
        custom_Din = Typeface.createFromAsset(_context.getAssets(), "fonts/DIN Next LT W23 Regular.ttf");
        this.JsonString = json;
       /* flower_dialog = new ACProgressFlower.Builder(context)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.GREEN).build();
        flower_dialog.show();*/

    }

    private static class ViewHolder {
        TextView PlayerName;
        TextView playerPostion;
        ImageView playerImage;
        ImageButton remove_from_team_btn;
        RatingBar ratingBar;
        TextView ic_team;
    }


    @Override
    public Object getChild(int groupPosition, int childPosititon) {

        return this._listDataChild
                .get(_listDataHeader.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {


        final FormationData formationData = (FormationData) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.edit_myteam, null);
            holder = new ViewHolder();

            holder.playerImage = (ImageView) convertView.findViewById(R.id.formation_palyer_photo);
            holder.PlayerName = (TextView) convertView.findViewById(R.id.formation_player_name);
            holder.ratingBar = (RatingBar) convertView.findViewById(R.id.formation_ratingBar);
            holder.playerPostion = (TextView) convertView.findViewById(R.id.playerPostion);
            holder.remove_from_team_btn = (ImageButton) convertView.findViewById(R.id.remove_from_team_btn);
            holder.ic_team = (TextView) convertView.findViewById(R.id.ic_team);
            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();
        }
        if (childPosition == 0) {
            convertView.setBackgroundDrawable(_context.getResources().getDrawable(R.drawable.boarderforexpandlelist));

        } else {
            convertView.setBackgroundDrawable(null);

        }
      TextView  filter = (TextView) ((Activity)_context).findViewById(R.id.filter_header_fragment);
        filter.setVisibility(View.GONE);
        LinearLayout playersLayout = (LinearLayout) convertView.findViewById(R.id.playersLayout);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(_context);
        final String language = sharedPref.getString("language", "ar");
        if (language.equals("en")) {
            playersLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

        }
        index = groupPosition;

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(_context);
        String json1 = mPrefs.getString("userObject", "");
        Gson gson = new Gson();
        final User obj = gson.fromJson(json1, User.class);
        Team team = new Team();
        try {
            final ArrayList<Team> teamList = team.ParseTeam(this.JsonString);
            final String userId = String.valueOf(obj.getId());
            String d = teamList.get(groupPosition).getCaptinId();
            String g = String.valueOf(groupPosition);

            //captain
            if (formationData.getId().equals(teamList.get(groupPosition).getCaptinId())) {
                holder.ic_team.setTypeface(custom_font_Awesome);
                holder.ic_team.setText(Html.fromHtml("&#xf1f9;"));
                holder.ic_team.setVisibility(View.VISIBLE);
            } else {
                holder.ic_team.setVisibility(View.INVISIBLE);

            }

            if (!String.valueOf(obj.getId()).equals(teamList.get(groupPosition).getCaptinId())) {
                holder.remove_from_team_btn.setVisibility(View.GONE);
            } else {

                if (String.valueOf(obj.getId()).equals(formationData.getId()))

                {
                    holder.remove_from_team_btn.setVisibility(View.GONE);
                    holder.ic_team.setTypeface(custom_font_Awesome);
                    holder.ic_team.setText(Html.fromHtml("&#xf1f9;"));
                    holder.ic_team.setVisibility(View.VISIBLE);
                } else {
                    holder.remove_from_team_btn.setVisibility(View.VISIBLE);
                    holder.ic_team.setVisibility(View.GONE);

                }

                holder.remove_from_team_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Activity mActivity = (Activity) _context;

                        final Dialog dialog = new Dialog(mActivity, R.style.CustomDialogTheme);
                        dialog.setContentView(R.layout.confirm_delete_dialog);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                        dialog.setCancelable(true);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                        TextView no_message_text = (TextView) dialog.findViewById(R.id.message_test);
                        no_message_text.setTypeface(custom_Din);

                        Button close_button = (Button) dialog.findViewById(R.id.btn_close_dialog);
                        Button ok_btn = (Button) dialog.findViewById(R.id.ok_btn);
                        if (language.equals("en")) {
                            no_message_text.setText("Are you sure?");
                            close_button.setText("close");
                            ok_btn.setText("ok");
                        }
                        close_button.setTypeface(custom_Din);
                        ok_btn.setTypeface(custom_Din);


                        close_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();

                            }
                        });
                        ok_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                               // flower_dialog.show();
                                // Instantiate the RequestQueue.
                                RequestQueue queue = Volley.newRequestQueue(_context);
                                String url = URLs.Remove_Player_from_team + "id=" + userId + "&pid="
                                        + _listDataChild.get(teamList.get(groupPosition).getTeamName()).get(childPosition).getId();

                                // Log.d("urlrr ",url);
// Request a string response from the provided URL.
                                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    dialog.dismiss();
                                                  //  flower_dialog.dismiss();

                                                    JSONObject jsonObject = new JSONObject(response);
                                                    if (Integer.parseInt(jsonObject.getString("success")) == 1) {

                                                        Fragment Itemfragment = new MyTeamPlayersFragment();
                                                        FragmentTransaction ft = ((FragmentActivity) _context).getSupportFragmentManager().beginTransaction();
                                                        ft.replace(R.id.placeholder2, Itemfragment).commit();
                                                    } else {

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
                        });

                        dialog.show();


                    }
                });
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



        if (formationData.getPlayerPhoto().isEmpty()) {
            holder.playerImage.setImageResource(R.drawable.placeholder_user_photo);
        } else{
            Picasso.
                    with(_context).
                    load(formationData.getPlayerPhoto())
                    .placeholder(R.drawable.placeholder_user_photo)
                    .error(R.drawable.placeholder_user_photo).
                    into(holder.playerImage, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                          //  flower_dialog.dismiss();
                        }

                        @Override
                        public void onError() {


                        }
                    });
        }
        holder.playerPostion.setTypeface(custom_Din);
        holder.PlayerName.setTypeface(custom_Din);
        holder.PlayerName.setText(formationData.getPlayerName());
        holder.ratingBar.setRating(Float.parseFloat(formationData.getRatingText()));
        if (formationData.getPlayerPostion()!=null||formationData.getPlayerPostion()!="null")
        holder.playerPostion.setText(formationData.getPlayerPostion());
        else {
            if (language.equals("ar")) {
                holder.playerPostion.setText("غير معروف");

            }
        }
      if (!String.valueOf(obj.getId()).equals(formationData.getId()))
      {  convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   flower_dialog = new ACProgressFlower.Builder(_context)
                        .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                        .themeColor(Color.WHITE)
                        .fadeColor(Color.GREEN).build();
                flower_dialog.show();*/
                RequestQueue queue = Volley.newRequestQueue(_context);
                String url = URLs.GET_Player_properties_by_id + "?key=logic123&id="
                        + formationData.getId();
                Log.d("uuuuuu",url);
// Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.

                              //  flower_dialog.dismiss();
                                Bundle bundle = new Bundle();
                                bundle.putString("playersJson", response);
                                bundle.putString("hideFiler_", "1");

                                Fragment getPlayerFragment = new GetPlayerFragment();
                                getPlayerFragment.setArguments(bundle);
                                FragmentTransaction ft = ((FragmentActivity) _context).getSupportFragmentManager().beginTransaction();
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
        });}

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
//        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
//                .size();


        return _listDataChild.get(_listDataHeader.get(groupPosition)).size();


    }

    @Override
    public Object getGroup(int groupPosition) {

        return _listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {

        return _listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {

        return groupPosition;
    }

    private static class ViewHolderGroup {
        TextView lblListHeader;
        TextView ic_team;
        TextView ic_captain;

    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.team_name_group_exp_lv, null);
            viewholder = new ViewHolderGroup();
            ExpandableListView mExpandableListView = (ExpandableListView) parent;
            mExpandableListView.expandGroup(groupPosition);

            viewholder.lblListHeader = (TextView) convertView
                    .findViewById(R.id.teamName_lblListHeader);
            viewholder.ic_team = (TextView) convertView
                    .findViewById(R.id.ic_team);
            viewholder.ic_captain = (TextView) convertView.findViewById(R.id.ic_captain);
            convertView.setTag(viewholder);
        } else {
            viewholder = (ViewHolderGroup) convertView.getTag();
        }
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(_context);
        String json1 = mPrefs.getString("userObject", "");
        Gson gson = new Gson();
        final User obj = gson.fromJson(json1, User.class);
        String uid = String.valueOf(obj.getId());
        Team team = new Team();
        try {
            final ArrayList<Team> teamList = team.ParseTeam(this.JsonString);
            final String userId = String.valueOf(obj.getId());
            String d = teamList.get(groupPosition).getCaptinId();
            if (uid.equals(d)) {
                viewholder.ic_captain.setTypeface(custom_font_Awesome);
                viewholder.ic_captain.setVisibility(View.GONE);
            } else {
                viewholder.ic_captain.setVisibility(View.GONE);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        viewholder.ic_team.setTypeface(custom_font_Awesome);
        viewholder.lblListHeader.setTypeface(custom_Din);
        viewholder.lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public class Team {
        public Team() {
        }

        String teamName, TeamId, CaptinId;

        public String getTeamId() {
            return TeamId;
        }

        public void setTeamId(String teamId) {
            TeamId = teamId;
        }

        public String getTeamName() {
            return teamName;
        }

        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }

        public String getCaptinId() {
            return CaptinId;
        }

        public void setCaptinId(String captinId) {
            CaptinId = captinId;
        }

        public ArrayList<Team> ParseTeam(String jsonString) throws JSONException {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("team");
            ArrayList<Team> TeamList = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject innerObj = jsonArray.getJSONObject(i);
                Team team = new Team();
                team.setTeamId(innerObj.getString("id"));
                team.setCaptinId(innerObj.getString("CaptainId"));
                team.setTeamName(innerObj.getString("name"));
                TeamList.add(team);
            }
            return TeamList;
        }
    }


}
