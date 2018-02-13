package com.logicdesigns.mohammed.mal3bklast.adapters;

/**
 * Created by logicDesigns on 10/22/2017.
 */

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.logicdesigns.mohammed.mal3bklast.R;

import com.logicdesigns.mohammed.mal3bklast.Models.Message;
import com.paging.listview.PagingBaseAdapter;
import com.squareup.picasso.Picasso;

import BusinessClasses.ListViewAdapterForSearchTraining;
import BusinessClasses.User;

public class ChatAdapter  extends RecyclerView.Adapter<ChatAdapter.Viewholder> {

    ArrayList <Message> chatMessageList;
    Typeface dinNext;
    String userId;
    SharedPreferences mPrefs;
    Context mContext;

    public ChatAdapter(Activity activity, ArrayList<Message> list) {
        chatMessageList = list;

        mPrefs = PreferenceManager.getDefaultSharedPreferences(activity);
        dinNext = Typeface.createFromAsset(activity.getAssets(), "fonts/DIN Next LT W23 Regular.ttf");
        Gson gson = new Gson();
        String json1 = mPrefs.getString("userObject", "");
        User user_obj = gson.fromJson(json1, User.class);
        userId = String.valueOf(user_obj.getId());
        mContext = activity;
    }




    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatbubble, parent, false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {
        Message message =  chatMessageList.get(position);

        if (userId.equals(message.getRecieverId()))
        {
            holder.another_layout.setVisibility(View.VISIBLE);
            holder.myLayout.setVisibility(View.GONE);
            holder.innerAnotherLayout.setBackgroundResource(R.drawable.bubble1);

            holder.anotherText.setText(message.getMessage());
            if (!TextUtils.isEmpty(message.getSenderImage()) || !message.getSenderImage().equals("")) {

                Picasso.with(mContext)
                        .load(message.getSenderImage())
                        .placeholder(R.drawable.placeholder_user_photo)
                        .error(R.drawable.placeholder_user_photo)
                        .into(holder.anotherImage);
            } else {
                holder.anotherImage.setImageResource(R.drawable.placeholder_user_photo);
            }
        }

        else if (userId.equals(message.getSenderId()))
        {
            holder.another_layout.setVisibility(View.GONE);
            holder.myLayout.setVisibility(View.VISIBLE);
            holder.myText.setText(message.getMessage());
            holder.innerMylayout.setBackgroundResource(R.drawable.bubble2);

            if (!TextUtils.isEmpty(message.getSenderImage()) || !message.getSenderImage().equals("")) {

                Picasso.with(mContext)
                        .load(message.getSenderImage())
                        .placeholder(R.drawable.placeholder_user_photo)
                        .error(R.drawable.placeholder_user_photo)
                        .into(holder.myImage);
            } else {
                holder.myImage.setImageResource(R.drawable.placeholder_user_photo);
            }
        }

    }

    @Override
    public int getItemCount() {
        return chatMessageList.size();
    }

    public void add(Message object) {
        chatMessageList.add(object);
    }

    class Viewholder extends RecyclerView.ViewHolder  {

        LinearLayout another_layout,myLayout,innerAnotherLayout,innerMylayout;
        TextView myText, anotherText;
        ImageView myImage,anotherImage;
         Viewholder(final View vi) {
            super(vi);
            myImage = (ImageView) vi.findViewById(R.id.myImage);
            myLayout = (LinearLayout) vi.findViewById(R.id.myLayout);
            myText   = (TextView) vi.findViewById(R.id.myText);
            anotherImage = (ImageView) vi.findViewById(R.id.anotherImage);
            another_layout = (LinearLayout) vi.findViewById(R.id.another_layout);
            anotherText   = (TextView) vi.findViewById(R.id.anotherText);
            innerAnotherLayout = (LinearLayout) vi.findViewById(R.id.innerAnotherLayout);
            innerMylayout = (LinearLayout)  vi.findViewById(R.id.innerMylayout);
        }


    }
}
