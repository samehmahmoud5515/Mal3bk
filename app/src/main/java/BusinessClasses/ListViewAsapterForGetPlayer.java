package BusinessClasses;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
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
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.logicdesigns.mohammed.mal3bklast.ClubFragment;
import com.logicdesigns.mohammed.mal3bklast.Dialogs.InfoDialog;
import com.logicdesigns.mohammed.mal3bklast.ExceptionHandling.ExceptionHandling;
import com.logicdesigns.mohammed.mal3bklast.Main2Activity;
import com.logicdesigns.mohammed.mal3bklast.MainContainer;
import com.logicdesigns.mohammed.mal3bklast.Models.UpcomingMatches;
import com.logicdesigns.mohammed.mal3bklast.R;
import com.logicdesigns.mohammed.mal3bklast.adapters.ComingMatchesAdapter;
import com.logicdesigns.mohammed.mal3bklast.adapters.NotificationReyclerViewAdapter;
import com.logicdesigns.mohammed.mal3bklast.common.FragmentsTags;
import com.logicdesigns.mohammed.mal3bklast.common.URLs;
import com.logicdesigns.mohammed.mal3bklast.fragments.ChatDetailsFragment;
import com.logicdesigns.mohammed.mal3bklast.tasks.ChangePlayerPostion;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.paging.listview.PagingBaseAdapter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class ListViewAsapterForGetPlayer extends RecyclerView.Adapter<ListViewAsapterForGetPlayer.UserHolder> {


    Context context;
    int layoutResourceId;
    ArrayList<GetPlayerData> data = new ArrayList<GetPlayerData>();
    Typeface custom_font_Awesome;
    Typeface custom_Din, custom_Droid;
      ACProgressFlower flower_dialog;
    RadioButton radioButton;
    ListAdapter adapter;
    ArrayList<UpcomingMatches> matchesList;
    SharedPreferences mPrefs;
    User obj;

    public ListViewAsapterForGetPlayer(Context context, int layoutResourceId, ArrayList<GetPlayerData> data) {
        //super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        flower_dialog = new ACProgressFlower.Builder(context)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.GREEN).build();
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json1 = mPrefs.getString("userObject", "");
        obj = gson.fromJson(json1, User.class);
    }



    @Override
    public ListViewAsapterForGetPlayer.UserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutResourceId, parent, false);



        return new ListViewAsapterForGetPlayer.UserHolder(view);
    }


    @Override
    public void onBindViewHolder(final UserHolder holder, int position) {
        position = holder.getAdapterPosition();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        final String language = sharedPref.getString("language", "ar");

            if (language.equals("en")) {

                custom_Din = Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-Light.ttf");
                custom_Droid = Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-Light.ttf");
            } else {
                custom_Din = Typeface.createFromAsset(context.getAssets(), "fonts/DIN Next LT W23 Regular.ttf");
                custom_Droid = Typeface.createFromAsset(context.getAssets(), "fonts/DroidKufi-Regular.ttf");
            }
            custom_font_Awesome = Typeface.createFromAsset(context.getAssets(), "fonts/fontawesome-webfont.ttf");
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();

        final GetPlayerData formationData = data.get(position);
        holder.linear1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //finalHolder1.rate_tv_new.performClick();
                openRating(formationData, language);

            }
        });
        holder.linear2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //finalHolder1.rate_tv_new.performClick();
                openRating(formationData, language);

            }
        });
        if(formationData.getCanchangePosition().equals("true"))
        {
            //sameh
            holder.change_position_btn.setVisibility(View.VISIBLE);
            holder.change_position_btn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ChangePlayerPostion changePlayerPostion = new ChangePlayerPostion(context);
                    changePlayerPostion.changePosition(String.valueOf(obj.getId()),formationData.getPlayerId());
                }
            });
        }
        else{
            holder.change_position_btn.setVisibility(View.GONE);
        }

        holder.name_player__get_player.setText(formationData.getNamePlayer());
        holder.name_player__get_player.setTypeface(custom_Din);
        if (language.equals("en")) {
           // holder.name_player__get_player.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        } else {
           // holder.name_player__get_player.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        }
        holder.town_player_get_player.setTypeface(custom_Din);
        holder.town_player_get_player.setText(formationData.getTownPlayer());
        // holder.rating_palyer_get_player.setText(formationData.getRatingPlayer());
        holder.btn_get_player.setTypeface(custom_Din);

        holder.ratingBar__get_player.setRating(Float.parseFloat(formationData.getRatingPlayer()));

        final UserHolder finalHolder = holder;
        holder.ratingBar__get_player.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // TODO perform your action here
                    openRating(formationData, language);
                }

                return true;
            }


        });


        holder.position_player__get_player.setText(formationData.getPositionPlayer());
        holder.position_player__get_player.setTypeface(custom_Din);




        if (formationData.getPlayerImage().isEmpty()) {
            holder.photo_player_get_player.setImageResource(R.drawable.placeholder_user_photo);
        } else{
            Picasso.
                    with(context).
                    load(formationData.getPlayerImage())
                    //.resize(100,100)
                    //  .memoryPolicy(MemoryPolicy.NO_CACHE)
                    //  .networkPolicy(NetworkPolicy.NO_CACHE)
                    .error(R.drawable.placeholder_user_photo)
                    .placeholder(R.drawable.placeholder_user_photo)

                    .into(holder.photo_player_get_player,
                            new Callback() {
                                @Override
                                public void onSuccess() {

                                    // flower_dialog.dismiss();
                                }

                                @Override
                                public void onError() {
                                    ImageLoader imageLoader = ImageLoader.getInstance();
                                    imageLoader.displayImage(formationData.getPlayerImage(), holder.photo_player_get_player);

                                }
                            });
        }

//        if (flower_dialog!=null&&flower_dialog.isShowing())
//             flower_dialog.dismiss();

        if (formationData.getInMyTeam() == 1)
            holder.estiqtab_ll.setVisibility(View.GONE);
        else if (formationData.getInMyTeam() == 0)
            holder.estiqtab_ll.setVisibility(View.VISIBLE);
        if (language.equals("en")) {
            holder.btn_get_player.setText(R.string.addPlayer);
        }

        final int finalPosition = position;
        holder.btn_get_player.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context, R.style.CustomDialogTheme);
                dialog.setContentView(R.layout.get_player_popup_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                dialog.setCancelable(true);
                RadioButton rd1 = (RadioButton) dialog.findViewById(R.id.add_player_to_team_get_player_popup_dialog);
                RadioButton rd2 = (RadioButton) dialog.findViewById(R.id.borrow_get_player_popup_dialog);
                final RadioGroup radioGroup_getPlayer = (RadioGroup) dialog.findViewById(R.id.radioGroup_getPlayer);
                Button btn_execute_get_player_popup_dialog = (Button) dialog.findViewById(R.id.btn_execute_get_player_popup_dialog);
                btn_execute_get_player_popup_dialog.setTypeface(custom_Din);
                rd1.setTypeface(custom_Din);
                rd2.setTypeface(custom_Din);
                if (language.equals("en")) {
                    LinearLayout popUpPlayerLayout = (LinearLayout) dialog.findViewById(R.id.popUpPlayerLayout);
                    popUpPlayerLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                    rd1.setText(R.string.addToTeam);
                    rd2.setText(R.string.borrowed);
                    btn_execute_get_player_popup_dialog.setText(R.string.get);
                    LinearLayout.LayoutParams para = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    para.gravity = Gravity.LEFT | Gravity.CENTER;
                    rd1.setLayoutParams(para);
                    rd2.setLayoutParams(para);

                }
                btn_execute_get_player_popup_dialog.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        try {
                            //get radio button id
                            int selectedId = radioGroup_getPlayer.getCheckedRadioButtonId();
// get user data


                            radioButton = (RadioButton) dialog.findViewById(selectedId);
                            if (radioButton.getId() == R.id.add_player_to_team_get_player_popup_dialog) {
                                //get user id + current player id
                                 flower_dialog.show();
                                // Instantiate the RequestQueue.
                                RequestQueue queue = Volley.newRequestQueue(context);
                                String url = URLs.Edafa_ll_Fariq + "?key=logic123&id="
                                        + obj.getId() + "&pid=" + data.get(finalPosition).getPlayerId();
                                Log.d("AddToTeam",url);
                               // Toast.makeText(context, data.get(position).getNamePlayer(), Toast.LENGTH_SHORT).show();
                                // Request a string response from the provided URL.
                                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                       flower_dialog.dismiss();
                                                    final Dialog message_dialog = new Dialog(context, R.style.CustomDialogTheme);
                                                    message_dialog.setContentView(R.layout.no_playgrounds_custom_dialog);
                                                    message_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                                    message_dialog.setCancelable(true);
                                                    message_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                                    TextView no_message_text = (TextView) message_dialog.findViewById(R.id.no_message_text);
                                                    no_message_text.setTypeface(custom_Din);
                                                    Button close_button = (Button) message_dialog.findViewById(R.id.btn_close_no_playground);
                                                    close_button.setTypeface(custom_Din);
                                                    close_button.setOnClickListener(new View.OnClickListener() {

                                                        @Override
                                                        public void onClick(View v) {
                                                            message_dialog.cancel();


                                                        }
                                                    });

                                                    JSONObject jsonObject = new JSONObject(response);
                                                    if (Integer.parseInt(jsonObject.getString("success")) == 1) {
                                                        //Player is added to my team
                                                      //  Toast.makeText(context,jsonObject.getString("success") , Toast.LENGTH_SHORT).show();
                                                        if (language.equals("en")) {
                                                            no_message_text.setText(R.string.added);
                                                            close_button.setText(R.string.close);
                                                        } else {
                                                            no_message_text.setText("تمت الاضافة");
                                                        }
                                                        message_dialog.show();
                                                    } else if (Integer.parseInt(jsonObject.getString("success")) == 6) {
                                                        //when Id is wrong
                                                        if (language.equals("en")) {
                                                            no_message_text.setText(R.string.sent);
                                                            close_button.setText(R.string.close);
                                                        } else {
                                                            no_message_text.setText("لقد ارسلت طلب من قبل");
                                                        }
                                                        message_dialog.show();
                                                    } else if (Integer.parseInt(jsonObject.getString("success")) == 7) {
                                                        if (language.equals("en")) {
                                                            no_message_text.setText(R.string.theGame);
                                                            close_button.setText(R.string.close);
                                                        } else {
                                                            no_message_text.setText("المبارة يجب ان تكون في المستقبل");
                                                        }
                                                        message_dialog.show();
                                                    } else if (Integer.parseInt(jsonObject.getString("success")) == 5) {
                                                        if (language.equals("en")) {
                                                            no_message_text.setText(R.string.onTeam);
                                                            close_button.setText(R.string.close);
                                                        } else {
                                                            no_message_text.setText("الاعب متواجد في الفريق");
                                                        }
                                                        message_dialog.show();
                                                    } else if (Integer.parseInt(jsonObject.getString("success")) == 4) {
                                                        if (language.equals("en")) {
                                                            no_message_text.setText(R.string.noTeam);
                                                            close_button.setText(R.string.close);
                                                        } else {
                                                            no_message_text.setText("ليس لديك فريق");
                                                        }
                                                        message_dialog.show();
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
                                stringRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                                queue.add(stringRequest);
                            }

                            if (radioButton.getId() == R.id.borrow_get_player_popup_dialog) {
                                //  flower_dialog.show();
                                final ACProgressFlower  flower_dialog_borrow = new ACProgressFlower.Builder(context)
                                        .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                                        .themeColor(Color.WHITE)
                                        .fadeColor(Color.GREEN).build();
                                flower_dialog_borrow.show();
                                // Instantiate the RequestQueue.
                                RequestQueue queue = Volley.newRequestQueue(context);
                                String url = URLs.GetUpcomingReservationsaAPI + obj.getId();
                                Log.d("aaaaaaaaaaa",url);
                               // Toast.makeText(context, data.get(position).getNamePlayer(), Toast.LENGTH_SHORT).show();

// Request a string response from the provided URL.
                                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    flower_dialog_borrow.dismiss();
                                                    //    flower_dialog.dismiss();

                                                    JSONObject jsonObject = new JSONObject(response);
                                                    if (Integer.parseInt(jsonObject.getString("success10")) == 1) {
                                                        matchesList = new ArrayList<UpcomingMatches>();
                                                        UpcomingMatches upcomingMatches = new UpcomingMatches(context);
                                                        matchesList = upcomingMatches.ParseUpcomingMatches(response);
                                                        if (language.equals("en")) {
                                                            drawDialog("Choose Match", formationData.getPlayerId());
                                                        } else {
                                                            drawDialog("اختار المبارة", formationData.getPlayerId());
                                                        }
                                                    } else {
                                                        // No reservation found
                                                        ExceptionHandling exceptionHandling = new ExceptionHandling(context);
                                                        exceptionHandling.showErrorDialog("لا يوجد مباريات","no matches found");

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

                            dialog.cancel();

                        } catch (Exception e) {
                            if (language.equals("en")) {
                                Toast.makeText(context, "Choose", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "اختار", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
                // now that the dialog is set up, it's time to show it
                dialog.show();
                // holder.formation_textview_addplayer.setText("&#xf068;");
                // Fragment payFragment = new PayFragment();
                //FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                // ft.replace(R.id.placeholder2, payFragment).commit();
            }
        });


        if (language.equals("en")) {
           holder. getPlayerLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)  holder.playerPopup.getLayoutParams();
            params.gravity = Gravity.LEFT;
            holder. playerPopup.setLayoutParams(params);
        }

        if (language.equals("en"))
            holder.sendMessage_btn.setText("Send Message");
        holder.sendMessage_btn.setTypeface(custom_Din);
        holder.sendMessage_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog alert_createTeam;

                @SuppressLint("RestrictedApi") AlertDialog.Builder builder_createTeam = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.CustomDialog));

                TextView title = new TextView(context);

                if (language.equals("ar"))
                    title.setText("ارسال الرسالة ");
                else title.setText("Send a Message");
                title.setBackgroundColor(Color.parseColor("#008542"));


                title.setPadding(10, 10, 10, 10);
                title.setGravity(Gravity.CENTER);
                title.setTextColor(Color.WHITE);
                title.setTextSize(20);
                title.setTypeface(custom_Din);

                builder_createTeam.setCustomTitle(title);

                final EditText input = new EditText(context);

                input.setGravity(Gravity.CENTER);
                input.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                input.setTextColor(Color.parseColor("#2E8B57"));
                input.setHintTextColor(Color.parseColor("#2E8B57"));
                input.setTypeface(custom_Din);
                String okString,cancelString;
                if (language.equals("ar"))
                {  input.setHint("نص الرسالة");
                    okString = "ارسال";
                    cancelString = "إلغاء";
                }
                else
                {input.setHint("Message Text");
                    okString = "Send";
                    cancelString = "Cancel";
                }

                builder_createTeam.setView(input);


                builder_createTeam.setPositiveButton(okString, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if (input.getText().toString().length()>0)
                            sendMessage(input.getText().toString(), formationData.getPlayerId());
                        else {
                            if (language.equals("ar"))
                            {
                                Toast.makeText(context, "ادخل رسالة من فضلك", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(context, "Please enter a message", Toast.LENGTH_SHORT).show();

                            }
                        }

                    }
                });

                builder_createTeam.setNegativeButton(cancelString,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alert_createTeam = builder_createTeam.create();
                alert_createTeam.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        Button btnPositive = alert_createTeam.getButton(Dialog.BUTTON_POSITIVE);
                        btnPositive.setTypeface(custom_Din);

                        Button btnNegative = alert_createTeam.getButton(Dialog.BUTTON_NEGATIVE);
                        btnNegative.setTypeface(custom_Din);
                    }
                });
                alert_createTeam.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                alert_createTeam.getWindow().setWindowAnimations(R.style.CustomDialog);
                alert_createTeam.getWindow().getAttributes().windowAnimations = R.style.CustomDialog;
                alert_createTeam.show();

            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    static class UserHolder extends RecyclerView.ViewHolder {
        ImageView photo_player_get_player;
        TextView name_player__get_player;
        TextView town_player_get_player;
        RatingBar ratingBar__get_player;
        TextView rating_palyer_get_player;
        Button btn_get_player;
        TextView position_player__get_player;
        Button sendMessage_btn;
        LinearLayout getPlayerLayout;
        LinearLayout playerPopup;
        TextView position_title_player__get_player;
        LinearLayout estiqtab_ll;
        ImageButton change_position_btn;

        LinearLayout getPlayerLinearInner,linear2,linear1;

        public UserHolder(View row) {
            super(row);
            change_position_btn = (ImageButton) row.findViewById(R.id.change_position_btn) ;
            photo_player_get_player = (ImageView) row.findViewById(R.id.photo_player_get_player);
            name_player__get_player = (TextView) row.findViewById(R.id.name_player__get_player);
           town_player_get_player = (TextView) row.findViewById(R.id.town_player_get_player);
          ratingBar__get_player = (RatingBar) row.findViewById(R.id.ratingBar__get_player);
           rating_palyer_get_player = (TextView) row.findViewById(R.id.rating_palyer_get_player);
            btn_get_player = (Button) row.findViewById(R.id.btn_get_player);
            sendMessage_btn = (Button) row.findViewById(R.id.sendMessage_btn);
          position_player__get_player = (TextView) row.findViewById(R.id.position_player__get_player);
           position_title_player__get_player = (TextView) row.findViewById(R.id.position_title_player__get_player);
            estiqtab_ll = (LinearLayout) row.findViewById(R.id.estiqtab_ll);
            getPlayerLinearInner = (LinearLayout) row.findViewById(R.id.getPlayerLinearInner) ;
            linear1 = (LinearLayout) row.findViewById(R.id.linear1) ;
           linear2= (LinearLayout) row.findViewById(R.id.linear2) ;
             getPlayerLayout = (LinearLayout) row.findViewById(R.id.getPlayerLayout);
             playerPopup = (LinearLayout) row.findViewById(R.id.playerPopup);
        }
    }

    void drawDialog(String Tiltle, String PlayerId) {

        @SuppressLint("RestrictedApi") AlertDialog.Builder dialog = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.CustomDialogTheme));
        TextView title = new TextView(context);

        title.setText(Tiltle);

        title.setBackgroundColor(Color.parseColor("#008542"));
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(20);
        title.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-Light.ttf"));

        dialog.setCustomTitle(title);


        ComingMatchesAdapter comingMatchesAdapter = new ComingMatchesAdapter(context, matchesList, PlayerId);
        dialog.setAdapter(comingMatchesAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

//       handle clicks at adapter
                dialog.dismiss();
            }
        });


        AlertDialog alert = dialog.create();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        alert.show();
    }

    public void openRating(final GetPlayerData formationData, final String language)
    {
        Log.d("formation Data : ",formationData.getRateable());
        final Activity mActivity = (Activity) context;

        final Dialog dialog_rating_details = new Dialog(mActivity, R.style.CustomDialogTheme);
        dialog_rating_details.setContentView(R.layout.rating_details_dialog);
        dialog_rating_details.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog_rating_details.setCancelable(true);
        dialog_rating_details.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView speed_tv_tittle = (TextView) dialog_rating_details.findViewById(R.id.speed_tv_tittle);
        speed_tv_tittle.setTypeface(custom_Din);
        TextView skill_tv_tittle = (TextView) dialog_rating_details.findViewById(R.id.skill_tv_tittle);
        skill_tv_tittle.setTypeface(custom_Din);
        TextView shooting_tv_tittle = (TextView) dialog_rating_details.findViewById(R.id.shooting_tv_tittle);
        shooting_tv_tittle.setTypeface(custom_Din);
        RatingBar speed_rb = (RatingBar) dialog_rating_details.findViewById(R.id.speed_rb);
        RatingBar skill_rb = (RatingBar) dialog_rating_details.findViewById(R.id.skill_rb);
        RatingBar shooting_rb = (RatingBar) dialog_rating_details.findViewById(R.id.shooting_rb);
        LinearLayout ratingDailog_linearLayout = (LinearLayout) dialog_rating_details.findViewById(R.id.ratingDailog_linearLayout);

        if (language.equals("en")) {
            ratingDailog_linearLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            speed_tv_tittle.setText("Speed");
            skill_tv_tittle.setText("Skill");
            shooting_tv_tittle.setText("Shooting");
        }
        speed_rb.setRating(Float.parseFloat(formationData.getSpeedPlayer()));
        //  int speed = (int) Float.parseFloat(formationData.getSpeedPlayer());
        //  speed_rb.setNumStars(speed);
        skill_rb.setRating(Float.parseFloat(formationData.getSkillPlayer()));
        // int skill = (int) Float.parseFloat(formationData.getSkillPlayer());
        // skill_rb.setNumStars(skill);
        shooting_rb.setRating(Float.parseFloat(formationData.getShootingPlayer()));
        // int shoot = (int) Float.parseFloat(formationData.getShootingPlayer());
        // shooting_rb.setNumStars(shoot);


        Button close_button = (Button) dialog_rating_details.findViewById(R.id.btn_close_dialog);
        Button ratePlayer = (Button) dialog_rating_details.findViewById(R.id.ratePlayer);
        ratePlayer.setTypeface(custom_Din);
       if (formationData.getRateable().equals( "true"))
        {
            ratePlayer.setVisibility(View.VISIBLE);
            ratePlayer.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    showRatePlayerDialog(language, formationData);
                }
            });
        }
        else {
           ratePlayer.setVisibility(View.GONE);

       }
        close_button.setTypeface(custom_Din);
        if (language.equals("en")) {
            close_button.setText("Close");
            ratePlayer.setText("Rate Player");
        }
        close_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_rating_details.dismiss();
            }
        });
        dialog_rating_details.show();
    }

    public  void showRatePlayerDialog(String language, final GetPlayerData formationData)
    {
        final Activity mActivity = (Activity) context;

        final Dialog dialog_rating_details = new Dialog(mActivity, R.style.CustomDialogTheme);
        dialog_rating_details.setContentView(R.layout.rate_player_dialog);
        dialog_rating_details.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        dialog_rating_details.setCancelable(true);
        dialog_rating_details.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView speed_tv = (TextView) dialog_rating_details.findViewById(R.id.speed_tv);
        TextView skill_tv = (TextView) dialog_rating_details.findViewById(R.id.skill_tv);
        TextView shooting_tv = (TextView) dialog_rating_details.findViewById(R.id.shooting_tv);
        final com.iarcuschin.simpleratingbar.SimpleRatingBar speed_rating = (com.iarcuschin.simpleratingbar.SimpleRatingBar)
                dialog_rating_details.findViewById(R.id.speed_rating);
        final com.iarcuschin.simpleratingbar.SimpleRatingBar skill_rating = (com.iarcuschin.simpleratingbar.SimpleRatingBar)
                dialog_rating_details.findViewById(R.id.skill_rating);
        final com.iarcuschin.simpleratingbar.SimpleRatingBar shooting_rating = (com.iarcuschin.simpleratingbar.SimpleRatingBar)
                dialog_rating_details.findViewById(R.id.shooting_rating);
        Button close_button = (Button) dialog_rating_details.findViewById(R.id.btn_close_dialog);
        Button saveRating = (Button) dialog_rating_details.findViewById(R.id.saveRating);

        if (language.equals("ar"))
        {
            speed_tv.setTypeface(custom_Din);
            skill_tv.setTypeface(custom_Din);
            shooting_tv.setTypeface(custom_Din);
            close_button.setTypeface(custom_Din);
            saveRating.setTypeface(custom_Din);
        }
        else {
            speed_tv.setText("Speed");
            skill_tv.setText("Skill");
            shooting_tv.setText("Shooting");
            close_button.setText("Cancel");
            saveRating.setText("Save");
        }
        close_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_rating_details.dismiss();
            }
        });
        saveRating.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(mActivity);
                //String url ="https://logic-host.com/work/kora/beta/phpFiles/setPlayershootingAPI.php?key=logic123&s="+shooting_rating.getRating()+"&pid="
                   //     +formationData.getPlayerId()+"&myId="+obj.getId();
                String url = "https://logic-host.com/work/kora/beta/phpFiles/setPlayerStarsAPI.php?key=logic123&pid=" +
                        formationData.getPlayerId()+
                        "&myId="+obj.getId()+"&shooting="+shooting_rating.getRating()+"&skill="+
                        skill_rating.getRating()+"&speed="+speed_rating.getRating();

                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                ExceptionHandling ex = new ExceptionHandling(mActivity);

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getString("success").equals("1"))
                                    {
                                        ex.showErrorDialog("تم التقيم","Rated");
                                        dialog_rating_details.dismiss();
                                    }
                                    else {
                                        ex.showErrorDialog("لايمكنك","you cannot do this");
                                        dialog_rating_details.dismiss();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                // Display the first 500 characters of the response string.
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




        dialog_rating_details.show();

    }

   public void  sendMessage (String message, String playerId)
   {
      message = message.replace(" ", "%20");
       SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
       final String language = sharedPref.getString("language", "");
       // Instantiate the RequestQueue.
       RequestQueue queue = Volley.newRequestQueue(context);
       String url ="https://logic-host.com/work/kora/beta/phpFiles/sendChatAPI.php?key=logic123&id="+obj.getId()+"&pid="+playerId+"&chat="+message;

// Request a string response from the provided URL.
       StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
               new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {
                       // Display the first 500 characters of the response string.
                       try {
                           JSONObject jsonObject = new JSONObject(response);
                           if (jsonObject.getString("success").equals("1"))
                           {
                               if (language.equals("en")) {
                                   Toast.makeText(context, "Message Sent", Toast.LENGTH_SHORT).show();
                               }
                               else {
                                   Toast.makeText(context, "تم الارسال", Toast.LENGTH_SHORT).show();
                               }

                           }
                           else{
                               if (language.equals("en")) {
                                   Toast.makeText(context, "Message Not Sent", Toast.LENGTH_SHORT).show();
                               }
                               else {
                                   Toast.makeText(context, "تعزر الارسال", Toast.LENGTH_SHORT).show();
                               }
                           }
                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
                   }
               }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               if (language.equals("en")) {
                   Toast.makeText(context, "Message Not Sent", Toast.LENGTH_SHORT).show();
               }
               else {
                   Toast.makeText(context, "تعزر الارسال", Toast.LENGTH_SHORT).show();
               }
           }
       });
// Add the request to the RequestQueue.
       queue.add(stringRequest);
   }
}
