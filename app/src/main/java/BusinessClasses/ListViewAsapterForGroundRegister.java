package BusinessClasses;


import java.util.ArrayList;

import android.content.Context;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.logicdesigns.mohammed.mal3bklast.ClubFragment;
import com.logicdesigns.mohammed.mal3bklast.HeaderFragment;
import com.logicdesigns.mohammed.mal3bklast.MainContainer;
import com.logicdesigns.mohammed.mal3bklast.PayFragment;
import com.logicdesigns.mohammed.mal3bklast.R;
import com.squareup.picasso.Picasso;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;


public class ListViewAsapterForGroundRegister extends ArrayAdapter<RegisterPlaygroundData> {


    Context context;
    int layoutResourceId;
    ArrayList<RegisterPlaygroundData> data = new ArrayList<RegisterPlaygroundData>();
    Typeface custom_font_Awesome ;
    Typeface custom_Din;
    ACProgressFlower flower_dialog;

    String date,start_time,endtime;


    public ListViewAsapterForGroundRegister(Context context, int layoutResourceId,
                                            ArrayList<RegisterPlaygroundData> data,String date, String s_time,String e_time) {
        super(context, layoutResourceId , data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.date=date;
        this.start_time=s_time;
        this.endtime=e_time;
        flower_dialog = new ACProgressFlower.Builder(context)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.GREEN).build();
        flower_dialog.show();
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View row = convertView;
        UserHolder holder = null;



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
            holder = new UserHolder();
            holder.textName = (TextView) row.findViewById(R.id.prompt_name_result_playground_content_list);
            holder.textLocation = (TextView) row.findViewById(R.id.prompt_location_result_playground_content_list);
            holder.ratingBar = (RatingBar) row.findViewById(R.id.ratingBar__result_playground_content_list);
            holder.btnAdd = (Button) row.findViewById(R.id.btn_add_result_playground_content_list);
            holder.imageView=(ImageView) row.findViewById(R.id.playgroundimage_imageview);
            holder.icon1=(TextView) row.findViewById(R.id.icon1_result_playground_content_list);
            holder.icon2=(TextView) row.findViewById(R.id.icon2_result_playground_content_list);

            row.setTag(holder);
        }
        else {
            holder = (UserHolder) row.getTag();
        }
        final RegisterPlaygroundData registerPlaygroundData = data.get(position);
        holder.textName.setText(registerPlaygroundData.getPlaygroundName());
        holder.textName.setTypeface(custom_Din);
        holder.textLocation.setText(registerPlaygroundData.getPlaygroundLocation());
        holder.textLocation.setTypeface(custom_Din);
        holder.icon1.setTypeface(custom_font_Awesome);
        holder.icon2.setTypeface(custom_font_Awesome);
        holder.ratingBar.setRating(Float.parseFloat("5.0"));
        ////

        LayerDrawable stars = (LayerDrawable) holder.ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        ////

//        holder.imageView.setImageResource(R.drawable.playgroundphoto);
        holder.btnAdd.setTypeface(custom_Din);
        holder.btnAdd.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                MainContainer.headerNum = 2;
                MainContainer.pageNumber = 2.8;
                Fragment clubFragment = new ClubFragment();
                Bundle bundle = new Bundle();
                bundle.putString("date",date);
                bundle.putString("start_time",start_time);
                bundle.putString("end_time",endtime);
                bundle.putString("playgroundId",String.valueOf(data.get(position).getPlaygroundId()));
                //bundle.putString("playgroundId",registerPlaygroundData.getPlaygroundId()+"");
                clubFragment.setArguments(bundle);
                FragmentTransaction ft =((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                ft.add(R.id.frame_container, clubFragment).addToBackStack("ClubFragment")
                        .commit();
            }
        });


//        holder.imageView.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                Log.i("image Clicked", "**********");
//                Toast.makeText(context, "image Clicked",
//                        Toast.LENGTH_LONG).show();
//            }
//        });
        Picasso.
                with(context).
                load(data.get(position).getPlaygroundImage()).
                into(holder.imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        if (position==(data.size()-1))
                            flower_dialog.dismiss();

                    }

                    @Override
                    public void onError() {

                    }
                });

        return row;

    }

    static class UserHolder {
        TextView textName;
        RatingBar ratingBar;
        TextView textLocation;
        Button btnAdd;
        ImageView imageView;
        TextView icon1,icon2;


    }
}
