package BusinessClasses;


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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.logicdesigns.mohammed.mal3bklast.ClubFragment;
import com.logicdesigns.mohammed.mal3bklast.MainContainer;
import com.logicdesigns.mohammed.mal3bklast.R;

import java.util.ArrayList;


public class ListViewAdapterForSearchTraining extends ArrayAdapter<SearchTrainingData> {


    Context context;
    int layoutResourceId;
    ArrayList<SearchTrainingData> data = new ArrayList<SearchTrainingData>();
    Typeface custom_font_Awesome ;
    Typeface custom_Din,custom_Droid;
    String selected_date;
    LinearLayout searchTrainingLayout;

    public ListViewAdapterForSearchTraining(Context context, int layoutResourceId,
                                            ArrayList<SearchTrainingData> data, String selected_date) {
        super(context, layoutResourceId , data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
        this.selected_date=selected_date;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        UserHolder holder = null;
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String language = sharedPref.getString("language", "");

        if (row == null) {
            if (PreferenceManager.getDefaultSharedPreferences(context).getString("langugae", "").equals("en")){
                custom_font_Awesome= Typeface.createFromAsset(context.getAssets(),  "fonts/fontawesome-webfont.ttf");
                custom_Din=Typeface.createFromAsset(context.getAssets(),  "fonts/OpenSans-Light.ttf");
                custom_Droid=Typeface.createFromAsset(context.getAssets(),  "fonts/OpenSans-Light.ttf");
            }
            else{
                custom_font_Awesome= Typeface.createFromAsset(context.getAssets(),  "fonts/fontawesome-webfont.ttf");
                custom_Din=Typeface.createFromAsset(context.getAssets(),  "fonts/DIN Next LT W23 Regular.ttf");
                custom_Droid=Typeface.createFromAsset(context.getAssets(),  "fonts/DroidKufi-Regular.ttf");
            }

            LayoutInflater inflater = ((MainContainer) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
             holder = new UserHolder();




            holder.teamicon_search_taining=(TextView) row.findViewById(R.id.teamicon_search_taining);
            holder.teamname_search_trining=(TextView) row.findViewById(R.id.teamname_search_trining);
            holder.townicon_search_taining=(TextView) row.findViewById(R.id.townicon_search_taining);
            holder.townname_search_taining=(TextView) row.findViewById(R.id.townname_search_taining);
            holder.playgroundicon_search_taining=(TextView) row.findViewById(R.id.playgroundicon_search_taining);
            holder.playgroundname_search_taining=(TextView) row.findViewById(R.id.playgroundname_search_taining);
            holder.ratingBar_search_taining=(RatingBar) row.findViewById(R.id.ratingBar_search_taining);
          //  holder.rating_search_taining=(TextView) row.findViewById(R.id.rating_search_taining);
            holder.btn_search_taining=(Button) row.findViewById(R.id.btn_search_taining);
            holder. date_value_textview=(TextView) row.findViewById(R.id.date_value_textview);
            holder.ic_calender=(TextView) row.findViewById(R.id.ic_calender);
            holder.ic_clock=(TextView) row.findViewById(R.id.ic_clock);
            holder.start_time_textview=(TextView) row.findViewById(R.id.start_time_textview);
            holder.end_time_textview=(TextView) row.findViewById(R.id.end_time_textview);



            row.setTag(holder);
        }
        else {
            holder = (UserHolder) row.getTag();
        }
        final SearchTrainingData  searchTrainingData = data.get(position);

        holder.teamicon_search_taining.setTypeface(custom_font_Awesome);
        holder.teamname_search_trining.setText(searchTrainingData.getTeamName());
        holder.teamname_search_trining.setTypeface(custom_Din);
        holder.townicon_search_taining.setTypeface(custom_font_Awesome);
        holder.townname_search_taining.setText(searchTrainingData.getTownName());
        holder.townname_search_taining.setTypeface(custom_Din);
        holder.playgroundicon_search_taining.setTypeface(custom_font_Awesome);
        holder.playgroundname_search_taining.setText(searchTrainingData.getPlaygroundName());
        holder.playgroundname_search_taining.setTypeface(custom_Din);
        holder.ratingBar_search_taining.setRating(Float.parseFloat(searchTrainingData.getRating()));
     //   holder.rating_search_taining.setText(searchTrainingData.getRating());
      //  holder.rating_search_taining.setTypeface(custom_Din);
        holder.btn_search_taining.setTypeface(custom_Droid);
        holder.ic_calender.setTypeface(custom_font_Awesome);
        holder.ic_clock.setTypeface(custom_font_Awesome);
        holder.date_value_textview.setText(selected_date);
        String converted_start,converted_end;
        if (Integer.parseInt(searchTrainingData.getStart_time())>12)
        {
            int _24format=Integer.parseInt(searchTrainingData.getStart_time());
            int _12format = _24format-12;
            converted_start=_12format+"PM";
        }
        else  converted_start=searchTrainingData.getStart_time()+"AM";
        holder.start_time_textview.setText(converted_start);

        if (Integer.parseInt(searchTrainingData.getEnd_time())>12)
        {
            int _24format=Integer.parseInt(searchTrainingData.getEnd_time());
            int _12format = _24format-12;
            converted_end=_12format+"PM";
        }
        else  converted_end=searchTrainingData.getEnd_time()+"AM";

        holder.end_time_textview.setText(converted_end);



        ////

//        LayerDrawable stars = (LayerDrawable) holder.ratingBar_search_taining.getProgressDrawable();
//        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
//        stars.getDrawable(0).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
//        stars.getDrawable(1).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        ////
        if (language.equals("en")) {
            holder.btn_search_taining.setText(R.string.Reservation);
        }

        holder.btn_search_taining.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.i("Edit Button Clicked", "**********");
//                Toast.makeText(context, "Edit button Clicked",
//                        Toast.LENGTH_LONG).show();
                Bundle bundle =new Bundle();
                bundle.putString("date", selected_date);
                String converted_start_time,convert_end_time;
                if (Integer.parseInt( data.get(position).getStart_time())>12)
                {
                    int _24format=Integer.parseInt( data.get(position).getStart_time());
                    int _12format = _24format-12;
                    converted_start_time=_12format+"PM";
                }
                else  converted_start_time=data.get(position).getStart_time()+"AM";

                if (Integer.parseInt( data.get(position).getEnd_time())>12)
                {
                    int _24format=Integer.parseInt( data.get(position).getEnd_time());
                    int _12format = _24format-12;
                    convert_end_time=_12format+"PM";
                }
                else  convert_end_time=data.get(position).getEnd_time()+"AM";

                bundle.putString("start_time",converted_start_time);
                bundle.putString("end_time", convert_end_time);
                bundle.putString("playgroundId",data.get(position).getPlaygroundId());

                // holder.formation_textview_addplayer.setText("&#xf068;");
                 Fragment clubFragment = new ClubFragment();
                clubFragment.setArguments(bundle);
                FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.searchTraining_framelayout, clubFragment).addToBackStack(null).commit();
            }
        });

        searchTrainingLayout = (LinearLayout) row.findViewById(R.id.searchTrainingLayout);
        if (language.equals("en")) {
            searchTrainingLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        return row;

    }

    static class UserHolder {
        TextView teamicon_search_taining;
        TextView       teamname_search_trining;
        TextView townicon_search_taining;
        TextView     townname_search_taining;
        TextView playgroundicon_search_taining;
        TextView      playgroundname_search_taining;
        RatingBar ratingBar_search_taining;
      //  TextView        rating_search_taining;
        Button  btn_search_taining;
        TextView date_value_textview,ic_calender,ic_clock,start_time_textview,end_time_textview;



    }
}
