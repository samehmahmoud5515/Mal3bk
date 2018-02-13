package com.logicdesigns.mohammed.mal3bklast.adapters;

/**
 * Created by logicDesigns on 9/27/2017.
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Duration;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.logicdesigns.mohammed.mal3bklast.Dialogs.InfoDialog;
import com.logicdesigns.mohammed.mal3bklast.ExceptionHandling.ExceptionHandling;
import com.logicdesigns.mohammed.mal3bklast.GetPlayerFragment;
import com.logicdesigns.mohammed.mal3bklast.Main2Activity;
import com.logicdesigns.mohammed.mal3bklast.Models.NotificationModel;
import com.logicdesigns.mohammed.mal3bklast.R;
import com.logicdesigns.mohammed.mal3bklast.common.FragmentsTags;
import com.logicdesigns.mohammed.mal3bklast.common.URLs;
import com.logicdesigns.mohammed.mal3bklast.tasks.SetNotificationSeenTask;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

/**
 * Created by Sameh on 4/21/2017.
 */

public class NotificationReyclerViewAdapter extends RecyclerView.Adapter<NotificationReyclerViewAdapter.ViewHolder>
{

    ArrayList<NotificationModel>  notificationList;
    SharedPreferences sharedPref;
    String language = null;
    Typeface fontAwesome, dinNext;
    Context mContext;
    String allString = "";
     Dialog dialog;

    public NotificationReyclerViewAdapter(ArrayList<NotificationModel> model, Context context) {
        this.notificationList = model;
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        language = sharedPref.getString("language", "ar");
        fontAwesome = Typeface.createFromAsset(context.getAssets(), "fonts/fontawesome-webfont.ttf");
        dinNext = Typeface.createFromAsset(context.getAssets(), "fonts/DIN Next LT W23 Regular.ttf");
        mContext = context;
    }

    @Override
    public NotificationReyclerViewAdapter.ViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_row, viewGroup, false);



        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotificationReyclerViewAdapter.ViewHolder viewHolder, final int i) {


        allString = "";
      //



            if (language.equals("en")) {
                // viewHolder.title_message_textView.setText(notificationList.get(i).getTitle());
                allString += notificationList.get(i).getTitle() + " from: ";
            } else {
           /* viewHolder.title_message_textView.setText(notificationList.get(i).getTitlear());
            viewHolder.title_message_textView.setTypeface(dinNext);
            viewHolder.player_name_textView.setTypeface(dinNext);*/
                allString += notificationList.get(i).getTitlear() + " ";

            }
            if (notificationList.get(i).getType().equals("8") || notificationList.get(i).getType().equals("7")
                    || notificationList.get(i).getType().equals("12")) {

            } else
                allString += notificationList.get(i).getPlayerName() + " ";

            //viewHolder.player_name_textView.setText(notificationList.get(i).getPlayerName());
            if (notificationList.get(i).getTeamName().equals("")) {
                //viewHolder.withTeamTextView_name.setVisibility(View.GONE);
                // viewHolder.withTeamTextView.setVisibility(View.GONE);

            } else {
                //  viewHolder.withTeamTextView_name.setVisibility(View.VISIBLE);
                // viewHolder.withTeamTextView.setVisibility(View.VISIBLE);
                if (language.equals("en")) {
                    // viewHolder.withTeamTextView.setText("with team");
                    allString += "with team:" + " ";

                } else {
             /*   viewHolder.withTeamTextView.setText("في فريق");
                viewHolder.withTeamTextView_name.setTypeface(dinNext);
                viewHolder.withTeamTextView.setTypeface(dinNext);*/
                    allString += " فريق: " + " ";
                }
                // viewHolder.withTeamTextView_name.setText(notificationList.get(i).getTeamName());
                allString += " " + notificationList.get(i).getTeamName() + " ";
            }
            if (notificationList.get(i).getTeamName2().equals("")) {
                // viewHolder.againstTeamTextView.setVisibility(View.GONE);
                // viewHolder.againstTeamTextView_name.setVisibility(View.GONE);

            } else {
          /*  viewHolder.againstTeamTextView.setVisibility(View.VISIBLE);
            viewHolder.againstTeamTextView_name.setVisibility(View.VISIBLE);
            viewHolder.againstTeamTextView_name.setText(notificationList.get(i).getTeamName2());
*/
                if (language.equals("en")) {
                    //  viewHolder.againstTeamTextView.setText("against team");
                    allString += "against team:" + " ";

                } else {
                    // viewHolder.againstTeamTextView.setText("ضد فريق");
                    //  viewHolder.againstTeamTextView_name.setTypeface(dinNext);
                    // viewHolder.againstTeamTextView.setTypeface(dinNext);
                    allString += "ضد فريق: " + " ";
                }
                allString += " " + notificationList.get(i).getTeamName2() + " ";
            }
            if (!notificationList.get(i).getMatchDate().equals("")) {
                //  viewHolder.matchDate.setVisibility(View.VISIBLE);

                //  viewHolder.matchDateValue.setVisibility(View.VISIBLE);
                //  viewHolder.matchDateValue.setText(notificationList.get(i).getMatchDate());
                if (language.equals("en")) {
                    //  viewHolder.matchDate.setText("match date");
                    allString += "match date:" + " ";
                } else {
                    //  viewHolder.matchDate.setText("تاريخ المباراة");
                    //  viewHolder.matchDateValue.setTypeface(dinNext);
                    // viewHolder.matchDate.setTypeface(dinNext);
                    allString += "تاريخ المباراة: " + " ";
                }
                allString += notificationList.get(i).getMatchDate() + " ";
            } else {
                // viewHolder.matchDate.setVisibility(View.GONE);
                // viewHolder.matchDateValue.setVisibility(View.GONE);
            }
            if (!notificationList.get(i).getMatchFrom().equals("") && !notificationList.get(i).getMatchTo().equals("")) {
                //   viewHolder.time_textView.setVisibility(View.VISIBLE);
                InfoDialog infoDialog = new InfoDialog(mContext);
                if (language.equals("en")) {
                    // viewHolder.time_textView.setText("from "+notificationList.get(i).getMatchFrom()+"to" +notificationList.get(i).getMatchTo());
                    allString += "from: " +  infoDialog.convertTime_12hFormat(notificationList.get(i).getMatchFrom() )+ " to: " +  infoDialog.convertTime_12hFormat(notificationList.get(i).getMatchTo()) + " ";
                } else {
                    //viewHolder.time_textView.setText("من"+notificationList.get(i).getMatchFrom()+"الي"+notificationList.get(i).getMatchTo());
                    //viewHolder.time_textView.setTypeface(dinNext);
                    allString += " من: " + infoDialog.convertTime_12hFormat(notificationList.get(i).getMatchFrom()) + " الي: " + infoDialog.convertTime_12hFormat( notificationList.get(i).getMatchTo()) + " ";
                }
            } else {
                // viewHolder.time_textView.setVisibility(View.GONE);

            }
            if (!notificationList.get(i).getPlaygroundName().equals("")) {
                if (language.equals("en")) {
                    //   viewHolder.playground_name.setText("playground");
                    allString += "playground:" + " ";
                } else {
                    allString += "\nالملعب:" + " ";
                    //viewHolder.playground_name.setText("الملعب ");
                    // viewHolder.playground_name.setTypeface(dinNext);
                    // viewHolder.playground_name_value.setTypeface(dinNext);
                }
                //  viewHolder.playground_name.setVisibility(View.VISIBLE);
                allString += notificationList.get(i).getPlaygroundName() + " ";
                //viewHolder.playground_name_value.setText(notificationList.get(i).getPlaygroundName());
            } else {
                //viewHolder.playground_name.setVisibility(View.GONE);
                //  viewHolder.playground_name_value.setVisibility(View.GONE);
            }
            if (!notificationList.get(i).getPlaygroundCity().equals("")) {
                //font awsome set
           /* viewHolder. tv_city.setVisibility(View.VISIBLE);
            viewHolder. ic_city.setVisibility(View.VISIBLE);
            viewHolder. tv_city.setText(notificationList.get(i).getPlaygroundCity());
            viewHolder.ic_city.setTypeface(fontAwesome);
            viewHolder. tv_city.setTypeface(dinNext);*/
                if (language.equals("en")) {
                    allString += " city: " + " ";
                } else {
                    allString += " مدينة: " + " ";

                }
                allString += notificationList.get(i).getPlaygroundCity();
            } else {
          /* viewHolder. tv_city.setVisibility(View.GONE);
            viewHolder. ic_city.setVisibility(View.GONE);*/

            }
            if (!notificationList.get(i).getPlayerImage().equals("")) {
                viewHolder.playerImage_imageView.setVisibility(View.VISIBLE);

                //picasso
                Picasso.
                        with(this.mContext).
                        load(notificationList.get(i).getPlayerImage())
                        //.memoryPolicy(MemoryPolicy.NO_CACHE)
                        // .networkPolicy(NetworkPolicy.NO_CACHE)
                        .error(R.drawable.placeholder_user_photo)
                        .placeholder(R.drawable.placeholder_user_photo)
                        .into(viewHolder.playerImage_imageView);
            } else {
                viewHolder.playerImage_imageView.setImageResource(R.drawable.substitution_ic);
                // viewHolder.playerImage_imageView.setVisibility(View.GONE);
            }

            viewHolder.allString_tv.setText(allString);
            final String s = allString;
            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setTaskSeen(notificationList.get(i).getRequestId(),notificationList.get(i)
                    .getType());

                    Log.d("allstring ", s);
                    final Activity mActivity = (Activity) mContext;

                    dialog = new Dialog(mActivity, R.style.CustomDialog);
                    dialog.setContentView(R.layout.notification_dialog);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


                    dialog.setCancelable(true);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                    ImageView imageView = (ImageView) dialog.findViewById(R.id._notificationDetails_imageView);
                    if (!notificationList.get(i).getPlayerImage().equals("")) {
                        imageView.setVisibility(View.VISIBLE);

                        Picasso.
                                with(mContext).
                                load(notificationList.get(i).getPlayerImage())
                                //.memoryPolicy(MemoryPolicy.NO_CACHE)
                                // .networkPolicy(NetworkPolicy.NO_CACHE)
                                .error(R.drawable.placeholder_user_photo)
                                .placeholder(R.drawable.placeholder_user_photo)
                                .into(imageView);
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                RequestQueue queue = Volley.newRequestQueue(mContext);
                                String url = URLs.GET_Player_properties_by_id + "?key=logic123&id="
                                        +notificationList.get(i).getPlayer1IdSender();
// Request a string response from the provided URL.
                                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                dialog.dismiss();
                                                // Display the first 500 characters of the response string.

                                                //  flower_dialog.dismiss();
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
                    } else {
                        imageView.setImageResource(R.drawable.substitution_ic);
                    }
                    TextView textView = (TextView) dialog.findViewById(R.id.notification_details_tv);
                    Button confirm_btn = (Button) dialog.findViewById(R.id.confirm_btn);
                    Button cancel_btn = (Button) dialog.findViewById(R.id.cancel_btn);

                    if (notificationList.get(i).getRespondable().equals("1")) {
                        confirm_btn.setVisibility(View.VISIBLE);
                        cancel_btn.setVisibility(View.VISIBLE);

                    } else {
                        confirm_btn.setVisibility(View.GONE);
                        cancel_btn.setVisibility(View.GONE);
                    }
                    if (language.equals("ar")) {
                        textView.setTypeface(dinNext);

                        confirm_btn.setTypeface(dinNext);
                        cancel_btn.setTypeface(dinNext);
                        confirm_btn.setText("موافق");
                        cancel_btn.setText("غير موافق");
                    } else {
                        confirm_btn.setText("Confirm");
                        cancel_btn.setText("Ignore");
                    }
                    textView.setText(s);


                    confirm_btn.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            //dialog.cancel();
                            callApi(notificationList.get(i).getRequestId(), notificationList.get(i).getType(), "1");
                        }
                    });
                    cancel_btn.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // dialog.cancel();
                            callApi(notificationList.get(i).getRequestId(), notificationList.get(i).getType(), "0");

                        }
                    });

                    dialog.show();
                }


               });
            allString = "";


    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
      /*
        private  TextView player_name_textView;
        private  TextView title_message_textView;
        private TextView withTeamTextView;
        private TextView withTeamTextView_name;
        private TextView againstTeamTextView;
        private TextView againstTeamTextView_name;
        private TextView matchDate;
        private TextView matchDateValue;
        private TextView time_textView;
        private TextView playground_name;
        private TextView playground_name_value;
        private TextView ic_city;
        private TextView tv_city;*/
      private ImageView playerImage_imageView;
        private  TextView allString_tv;
        CardView cardView;
        public ViewHolder(final View view) {
            super(view);
            playerImage_imageView = (ImageView) view.findViewById(R.id.playerImage_imageView);
            allString_tv= (TextView)view.findViewById(R.id.allString);

           /*
            player_name_textView= (TextView)view.findViewById(R.id.player_name_textView);
            title_message_textView= (TextView)view.findViewById(R.id.title_message_textView);
            withTeamTextView= (TextView)view.findViewById(R.id.withTeamTextView);
            withTeamTextView_name= (TextView)view.findViewById(R.id.withTeamTextView_name);
            againstTeamTextView= (TextView)view.findViewById(R.id.againstTeamTextView);
            againstTeamTextView_name= (TextView)view.findViewById(R.id.againstTeamTextView_name);
            matchDate= (TextView)view.findViewById(R.id.matchDate);
            matchDateValue= (TextView)view.findViewById(R.id.matchDateValue);
            time_textView= (TextView)view.findViewById(R.id.time_textView);
            playground_name= (TextView)view.findViewById(R.id.playground_name);
            playground_name_value= (TextView)view.findViewById(R.id.playground_name_value);
            ic_city= (TextView)view.findViewById(R.id.ic_city);
            tv_city= (TextView)view.findViewById(R.id.tv_city);*/
            LinearLayout notification_relative =(LinearLayout) view.findViewById(R.id.notification_relative);
             cardView = (CardView) view.findViewById(R.id.notiification_card);

            if (language.equals("en")) {
                view.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                cardView.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                notification_relative.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
             /*   player_name_textView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                withTeamTextView_name.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                againstTeamTextView_name.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                matchDateValue.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
                playground_name_value.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);*/
            }
            else {
                allString_tv.setTypeface(dinNext);

            }


        }
    }
    public  void callApi(String id , String type , final String responsd)
    {
        final ACProgressFlower     flower_dialog = new ACProgressFlower.Builder(mContext)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.GREEN).build();
        flower_dialog.show();
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(mContext);
        String url ="https://logic-host.com/work/kora/beta/phpFiles/respondAPI.php?key=logic123&id="+id+"&type="+type+"&respond="+responsd;
Log.d("nnnnnnnnn",url);
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                             flower_dialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (! jsonObject.isNull("success")&& jsonObject.has("success"))
                            {
                                if (Integer.parseInt(jsonObject.getString("success"))==1)
                                {
                                    dialog.dismiss();
                                    if (language.equals("ar")) {
                                        Toast.makeText(mContext, "تم تنفيذ طلبك", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(mContext, "Your request has been done ", Toast.LENGTH_SHORT).show();

                                    }
                                    Intent intent = new Intent(mContext, Main2Activity.class);
                                    intent.putExtra(FragmentsTags.FragemntTag, FragmentsTags.NotificationFragment);
                                    mContext.startActivity(intent);
                                    ((Main2Activity)mContext).finish();
                                }
                                else {
                                    dialog.dismiss();
                                    if (language.equals("ar")) {
                                        Toast.makeText(mContext, "حدث خطأ", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(mContext, "Something Wrong ", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            dialog.dismiss();
                            ExceptionHandling exceptionHandling = new ExceptionHandling(mContext);
                            exceptionHandling.showErrorDialog();
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

    private  void setTaskSeen(String id, String type)
    {
        SetNotificationSeenTask task = new SetNotificationSeenTask(mContext);
        task.SetNotificationSeen(type,id);
    }

}