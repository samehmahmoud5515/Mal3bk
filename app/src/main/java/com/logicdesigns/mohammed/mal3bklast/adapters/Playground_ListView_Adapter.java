package com.logicdesigns.mohammed.mal3bklast.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.logicdesigns.mohammed.mal3bklast.ClubFragment;
import com.logicdesigns.mohammed.mal3bklast.JSON_PARSER.Playground_Search_reservation;
import com.logicdesigns.mohammed.mal3bklast.MainContainer;
import com.logicdesigns.mohammed.mal3bklast.Models.PlaygroundProperties;
import com.logicdesigns.mohammed.mal3bklast.R;
import com.logicdesigns.mohammed.mal3bklast.common.URLs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import BusinessClasses.ListViewAsapterForGroundRegister;
import BusinessClasses.RegisterPlaygroundData;
import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

/**
 * Created by logicDesigns on 7/5/2017.
 */

public class Playground_ListView_Adapter extends BaseAdapter {

    Context context;
    ArrayList<Playground_Search_reservation> playgroundsList=new ArrayList<Playground_Search_reservation>();

    Typeface custom_font_Awesome ;
    Typeface custom_Din;
    int layoutResourceId;
  //  ACProgressFlower flower_dialog;
    String receivedDate;



    public Playground_ListView_Adapter(Context context, int resource,ArrayList<Playground_Search_reservation> list,
                                       String ReceivedDate) {
        this.context=context;
        this.playgroundsList=list;
       /* flower_dialog = new ACProgressFlower.Builder(context)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.GREEN).build();*/
        this.layoutResourceId=resource;
        receivedDate = ReceivedDate;

      //  flower_dialog.show();
    }
    static class UserHolder {
        TextView textName;
      //  RatingBar ratingBar;
        TextView textLocation;
        Button btnAdd;
        ImageView imageView;
        //TextView icon1,icon2;
        TextView date_time_textview;
      //  TextView ic_clock;


    }
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View row = convertView;
        UserHolder holder = null;

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String language = sharedPref.getString("language", "ar");

        if (row == null) {
            custom_font_Awesome= Typeface.createFromAsset(context.getAssets(),  "fonts/fontawesome-webfont.ttf");

            if (PreferenceManager.getDefaultSharedPreferences(context).getString("langugae", "").equals("en")){
                custom_Din=Typeface.createFromAsset(context.getAssets(),  "fonts/OpenSans-Light.ttf");
            }
            else{
                custom_Din=Typeface.createFromAsset(context.getAssets(),  "fonts/DIN Next LT W23 Regular.ttf");
            }
            LayoutInflater inflater = ((MainContainer) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new Playground_ListView_Adapter.UserHolder();
            holder.textName = (TextView) row.findViewById(R.id.prompt_name_result_playground_content_list);
            holder.textLocation = (TextView) row.findViewById(R.id.prompt_location_result_playground_content_list);
           // holder.ratingBar = (RatingBar) row.findViewById(R.id.ratingBar__result_playground_content_list);
            holder.btnAdd = (Button) row.findViewById(R.id.btn_add_result_playground_content_list);
            holder.imageView=(ImageView) row.findViewById(R.id.playgroundimage_imageview);
          //  holder.icon1=(TextView) row.findViewById(R.id.icon1_result_playground_content_list);
            holder.date_time_textview=(TextView) row.findViewById(R.id.date_time_textview);
        //    holder.ic_clock=(TextView) row.findViewById(R.id.ic_clock);
            row.setTag(holder);
        }
        else {
            holder = (Playground_ListView_Adapter.UserHolder) row.getTag();
        }
        final Playground_Search_reservation playgroundProperties = playgroundsList.get(position);
        holder.textName.setText(playgroundProperties.getName());
        holder.textName.setTypeface(custom_Din);
        holder.textLocation.setText(playgroundProperties.getAddress());
        holder.textLocation.setTypeface(custom_Din);
      //  holder.icon1.setTypeface(custom_font_Awesome);
      //  holder.ic_clock.setTypeface(custom_font_Awesome);
       // holder.ratingBar.setRating(Float.parseFloat(playgroundProperties.getStars()));
        int num = (int) Float.parseFloat(playgroundProperties.getStars());
     //   holder.ratingBar.setNumStars(num);
      //  holder.ratingBar.setMax(num);
        holder.date_time_textview.setText(playgroundProperties.getS_time()+"-" +playgroundProperties.getE_time());
        if(language.equals("en")){
            LinearLayout resultLayout = (LinearLayout) row.findViewById(R.id.resultLayout);
            resultLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            holder.btnAdd.setText(R.string.Reservation);
            LinearLayout.LayoutParams para=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT );
            para.gravity = Gravity.LEFT;
            LinearLayout.LayoutParams para2=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT );
            para2.gravity = Gravity.RIGHT;
         //   holder.icon1.setLayoutParams(para);
            holder.textName.setLayoutParams(para);
            holder.textLocation.setLayoutParams(para);
          //  holder.date_time_textview.setLayoutParams(para2);
        //    holder.icon1.setPadding(0,30,0,0);

        }
        ////
        Log.d("stars111",playgroundProperties.getStars());

//        LayerDrawable stars = (LayerDrawable) holder.ratingBar.getProgressDrawable();
//        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
//        stars.getDrawable(0).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
//        stars.getDrawable(1).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        ////

//        holder.imageView.setImageResource(R.drawable.playgroundphoto);
        holder.btnAdd.setTypeface(custom_Din);
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Fragment clubFragment = new ClubFragment();
                FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                Bundle bundle = new Bundle();



                bundle.putString("date",receivedDate);
                bundle.putString("start_time",playgroundProperties.getS_time());
                bundle.putString("end_time",playgroundProperties.getE_time());
                bundle.putString("playgroundId",String.valueOf(playgroundsList.get(position).getId()));
                //bundle.putString("playgroundId",registerPlaygroundData.getPlaygroundId()+"");
                clubFragment.setArguments(bundle);
                ft.replace(R.id.registerGround_framelayout, clubFragment).addToBackStack(null).commit();
            }
        });



        if (!playgroundProperties.getImage().isEmpty())
        Picasso.
                with(context).
                load(playgroundProperties.getImage())
                //.resize(200,120)
               .into(holder.imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                       // if (position==(playgroundsList.size()-1))
                           //  flower_dialog.dismiss();

                    }

                    @Override
                    public void onError() {

                    }
                });

        return row;

    }

    @Override
    public int getCount() {
        return playgroundsList.size();
    }

    @Override
    public Object getItem(int position) {
        return playgroundsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



}
