package com.logicdesigns.mohammed.mal3bklast.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
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
import com.logicdesigns.mohammed.mal3bklast.BuildConfig;
import com.logicdesigns.mohammed.mal3bklast.Models.UpcomingMatches;
import com.logicdesigns.mohammed.mal3bklast.R;
import com.logicdesigns.mohammed.mal3bklast.common.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import BusinessClasses.User;
import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

/**
 * Created by logicDesigns on 7/13/2017.
 */

public class ComingMatchesAdapter extends BaseAdapter {
    Context context;
    ArrayList<UpcomingMatches> matchesArrayList = new ArrayList<>();
    Typeface custom_font_Awesome;
    Typeface custom_Din;
    String PlayerId;
    ACProgressFlower flower_dialog;

    public ComingMatchesAdapter(Context c, ArrayList<UpcomingMatches> lst, String Playerid) {
        this.context = c;
        this.matchesArrayList = lst;
        custom_font_Awesome = Typeface.createFromAsset(context.getAssets(), "fonts/fontawesome-webfont.ttf");
        custom_Din = Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-Light.ttf");
        this.PlayerId = Playerid;

    }

    @Override
    public int getCount() {
        return matchesArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return matchesArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView,
                        ViewGroup parent) {
        ViewHolder holder;
        final LayoutInflater inflater = (LayoutInflater) context.getApplicationContext()
                .getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        final String language = sharedPref.getString("language", "ar");

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.select_reservation_layout, null);

            holder = new ViewHolder();
         //   holder.ic_city = (TextView) convertView.findViewById(R.id.ic_city);
        //    holder.team2_text_view_flag = (TextView) convertView.findViewById(R.id.team2_text_view_flag);
            holder.team_ic = (TextView) convertView.findViewById(R.id.team_ic);
            holder.team1_textview = (TextView) convertView.findViewById(R.id.team1_textview);
            holder.times_ic = (TextView) convertView.findViewById(R.id.times_ic);
            holder.team2_textview = (TextView) convertView.findViewById(R.id.team2_textview);
            holder.ic_clock = (TextView) convertView.findViewById(R.id.ic_clock);
            holder.start_time_textview = (TextView) convertView.findViewById(R.id.start_time_textview);
            holder.end_time_textview = (TextView) convertView.findViewById(R.id.end_time_textview);
            holder.ic_ground_type = (TextView) convertView.findViewById(R.id.ic_ground_type);
            holder.ground_type_textview = (TextView) convertView.findViewById(R.id.ground_type_textview);
            holder.ic_calender = (TextView) convertView.findViewById(R.id.ic_calender);
            holder.date_value_textview = (TextView) convertView.findViewById(R.id.date_value_textview);

            convertView.setTag(holder);
        } else {
            // view already defined, retrieve view holder
            holder = (ViewHolder) convertView.getTag();
        }

        final UpcomingMatches match = matchesArrayList.get(position);
       // holder.ic_city.setTypeface(custom_font_Awesome);
        holder.team_ic.setTypeface(custom_font_Awesome);
        holder.times_ic.setTypeface(custom_font_Awesome);
        holder.ic_clock.setTypeface(custom_font_Awesome);
        holder.ic_ground_type.setTypeface(custom_font_Awesome);
        holder.ic_calender.setTypeface(custom_font_Awesome);
      //  holder.team2_text_view_flag.setTypeface(custom_Din);
        holder.team1_textview.setTypeface(custom_Din);
        holder.team2_textview.setTypeface(custom_Din);
        holder.start_time_textview.setTypeface(custom_Din);
        holder.end_time_textview.setTypeface(custom_Din);
        holder.ground_type_textview.setTypeface(custom_Din);
        holder.date_value_textview.setTypeface(custom_Din);

      //  holder.team2_text_view_flag.setText(match.getTeam2_name());
        holder.team1_textview.setText(match.getTeam1_name());
        holder.team2_textview.setText(match.getTeam2_name());
        holder.start_time_textview.setText(convertTimeto_12(match.getStart()));
        holder.end_time_textview.setText(convertTimeto_12(match.getEnd()));
        String match_type = match.getType() + "*" + match.getType();
        holder.ground_type_textview.setText(match_type);
        holder.date_value_textview.setText(match.getBookdate());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
                Gson gson = new Gson();
                String json1 = mPrefs.getString("userObject", "");
                User obj = gson.fromJson(json1, User.class);
                // Toast.makeText(context,match.getId(),Toast.LENGTH_LONG).show();
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(context);
                String url = URLs.Est3ara_La3eb_fi_mobara + "id=" + obj.getId() + "&pid=" +
                        PlayerId + "&matchId=" + match.getId();
                flower_dialog = new ACProgressFlower.Builder(context)
                        .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                        .themeColor(Color.WHITE)
                        .fadeColor(Color.GREEN).build();
                flower_dialog.show();
                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                flower_dialog.dismiss();

                                final Activity mActivity = (Activity) context;

                                final Dialog dialog = new Dialog(mActivity, R.style.CustomDialogTheme);
                                dialog.setContentView(R.layout.no_playgrounds_custom_dialog);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                dialog.setCancelable(true);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                TextView no_message_text = (TextView) dialog.findViewById(R.id.no_message_text);

                                Button close_button = (Button) dialog.findViewById(R.id.btn_close_no_playground);
                                close_button.setTypeface(custom_Din);

                                close_button.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {
                                        dialog.cancel();

                                    }
                                });
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (Integer.parseInt(jsonObject.getString("success")) == 0) {
                                        if (language.equals("en")) {
                                            no_message_text.setText(R.string.error);
                                            close_button.setText(R.string.close);
                                        } else {
                                            no_message_text.setText("حدث خطأ");
                                        }
                                    } else if (Integer.parseInt(jsonObject.getString("success")) == 1) {
                                        if (language.equals("en")) {
                                            no_message_text.setText(R.string.request);
                                            close_button.setText(R.string.close);
                                        } else {
                                            no_message_text.setText("تم الارسال");
                                        }
                                    } else if (Integer.parseInt(jsonObject.getString("success")) == 2) {
                                        if (language.equals("en")) {
                                            no_message_text.setText(R.string.notPoss);
                                            close_button.setText(R.string.close);
                                        } else {
                                            no_message_text.setText("لا يمكن");
                                        }
                                    } else if (Integer.parseInt(jsonObject.getString("success")) == 4) {
                                        if (language.equals("en")) {
                                            no_message_text.setText(R.string.createTeam);
                                            close_button.setText(R.string.close);
                                        } else {
                                            no_message_text.setText("انشئ فريق اولا");
                                        }
                                    } else if (Integer.parseInt(jsonObject.getString("success")) == 5) {
                                        if (language.equals("en")) {
                                            no_message_text.setText(R.string.onTeam);
                                            close_button.setText(R.string.close);
                                        } else {
                                            no_message_text.setText("الاعب متواجد في الفريق");
                                        }
                                    } else if (Integer.parseInt(jsonObject.getString("success")) == 6) {
                                        if (language.equals("en")) {
                                            no_message_text.setText(R.string.sent);
                                            close_button.setText(R.string.close);
                                        } else {
                                            no_message_text.setText("لقد ارسلت طلب من قبل ");
                                        }
                                    }

                                    no_message_text.setTypeface(custom_Din);

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

        return convertView;
    }


    class ViewHolder {

        TextView  team_ic, team1_textview, times_ic, team2_textview, ic_clock, start_time_textview, end_time_textview, ic_ground_type, ground_type_textview, ic_calender, date_value_textview;
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

