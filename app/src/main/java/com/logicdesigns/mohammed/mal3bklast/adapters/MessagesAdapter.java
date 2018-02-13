package com.logicdesigns.mohammed.mal3bklast.adapters;

/**
 * Created by Sameh on 10/21/2017.
 */

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.logicdesigns.mohammed.mal3bklast.Models.Message;

import java.util.List;
import com.logicdesigns.mohammed.mal3bklast.R;
import com.logicdesigns.mohammed.mal3bklast.fragments.ChatDetailsFragment;
import com.logicdesigns.mohammed.mal3bklast.fragments.inbox_fragment;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import BusinessClasses.User;


public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyViewHolder> {
    private Context mContext;
    private List<Message> messages;
    private SparseBooleanArray selectedItems;
    SharedPreferences mPrefs;
    Typeface dinNext;
    String userId;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        public TextView senderName_tv, message_tv, timestamp;
        public ImageView  imgProfile;
        public LinearLayout messageContainer;
        public RelativeLayout iconContainer;
        public CardView chat_cardView;

        public MyViewHolder(View view) {
            super(view);
            senderName_tv = (TextView) view.findViewById(R.id.senderName_tv);
            message_tv = (TextView) view.findViewById(R.id.message_tv);
            timestamp = (TextView) view.findViewById(R.id.timestamp);
            imgProfile = (ImageView) view.findViewById(R.id.icon_profile);
            messageContainer = (LinearLayout) view.findViewById(R.id.message_container);
            iconContainer = (RelativeLayout) view.findViewById(R.id.icon_container);
            chat_cardView = (CardView) view.findViewById(R.id.chat_cardView);
        }

        @Override
        public boolean onLongClick(View view) {
            view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
            return true;
        }
    }


    public MessagesAdapter(Context mContext, List<Message> messages) {
        this.mContext = mContext;
        this.messages = messages;
        try {
            mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
            dinNext = Typeface.createFromAsset(mContext.getAssets(), "fonts/DIN Next LT W23 Regular.ttf");
            Gson gson = new Gson();
            String json1 = mPrefs.getString("userObject", "");
            User user_obj = gson.fromJson(json1, User.class);
            userId = String.valueOf(user_obj.getId());
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Message message = messages.get(position);



        if (userId.equals(message.getSenderId()))
        {
           if (!TextUtils.isEmpty(message.getSenderImage()) || !message.getSenderImage().equals("")) {

                Picasso.with(mContext)
                        .load(message.getRecieverImage())
                        .placeholder(R.drawable.placeholder_user_photo)
                        .error(R.drawable.placeholder_user_photo)
                        .into(holder.imgProfile);
           } else {
               holder.imgProfile.setImageResource(R.drawable.placeholder_user_photo);
            }
            holder.senderName_tv.setText(message.getRecieverName());
        }
        else if (userId.equals(message.getRecieverId())){
            if (!TextUtils.isEmpty(message.getRecieverImage()) || !message.getRecieverImage().equals("")) {

                Picasso.with(mContext)
                        .load(message.getSenderImage())
                        .placeholder(R.drawable.placeholder_user_photo)
                        .error(R.drawable.placeholder_user_photo)
                        .into(holder.imgProfile);
            } else {
              holder.imgProfile.setImageResource(R.drawable.placeholder_user_photo);
            }
            holder.senderName_tv.setText(message.getSenderName());
        }
        else {
            holder.imgProfile.setImageResource(R.drawable.placeholder_user_photo);
        }

        holder.message_tv.setText(message.getMessage());
        holder.timestamp.setText(message.getTime_date());
        holder.senderName_tv.setTypeface(dinNext);
        holder.message_tv.setTypeface(dinNext);
        holder.timestamp.setTypeface(dinNext);

        holder.chat_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if (userId.equals(message.getSenderId()))
                  getMessageThread(userId,message.getRecieverId(),message.getRecieverName());
             else if (userId.equals(message.getRecieverId()))
                   getMessageThread(userId,message.getSenderId(),message.getSenderName());



            }
        });
    }



    private void applyProfilePicture(MyViewHolder holder, Message message) {
       /* if (!TextUtils.isEmpty(message.getPicture())) {

            Picasso.with(mContext)
                    .load(message.getPicture())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(holder.imgProfile);
        } else {
            holder.imgProfile.setImageResource(R.drawable.bg_circle);
            holder.imgProfile.setColorFilter(message.getColor());
            holder.iconText.setVisibility(View.VISIBLE);
        }
        */
    }




    @Override
    public int getItemCount() {
        return messages.size();
    }


    public void getMessageThread(final String myId, final String anotherId, final String name)
    {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(mContext);
        final String url ="https://logic-host.com/work/kora/beta/phpFiles/getChatWithPlayerAPI.php?key=logic123" +
                "&id="+myId+"&pid="+anotherId+"&page=1&limit=10";
        Log.d("chatURL",url);
        final String paged_url = "https://logic-host.com/work/kora/beta/phpFiles/getChatWithPlayerAPI.php?key=logic123" +
                "&id="+myId+"&pid="+anotherId;

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("success").equals("1"))
                            {
                                ChatDetailsFragment fragment = new ChatDetailsFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("jsonString",response);
                                bundle.putString("url",paged_url);
                                bundle.putString("myId",myId);
                                bundle.putString("anotherId",anotherId);
                                bundle.putString("name",name);
                                fragment.setArguments(bundle);
                                FragmentTransaction ft =((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.placeholder2, fragment).addToBackStack(null).commit();
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




}