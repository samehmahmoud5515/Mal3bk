package BusinessClasses;


import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.logicdesigns.mohammed.mal3bklast.GetPlayerFragment;
import com.logicdesigns.mohammed.mal3bklast.MainContainer;
import com.logicdesigns.mohammed.mal3bklast.R;
import com.logicdesigns.mohammed.mal3bklast.common.URLs;
import com.logicdesigns.mohammed.mal3bklast.header.HeaderFragment_Inner;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

import static com.facebook.FacebookSdk.getApplicationContext;


public class ListViewAsapterSearchAboutPlayer extends ArrayAdapter<FormationData> {


    Context context;
    int layoutResourceId;
    ArrayList<FormationData> data = new ArrayList<FormationData>();
    Typeface custom_font_Awesome;
    Typeface custom_Din;

    LinearLayout childLayout;

    //ACProgressFlower flower_dialog;

    public ListViewAsapterSearchAboutPlayer(Context context, int layoutResourceId, ArrayList<FormationData> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
//        flower_dialog = new ACProgressFlower.Builder(context)
//                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
//                .themeColor(Color.WHITE)
//                .fadeColor(Color.GREEN).build();
        //   flower_dialog.show();
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View row = convertView;
        UserHolder holder = null;
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String language = sharedPref.getString("language", "ar");

        if (row == null) {
            custom_font_Awesome = Typeface.createFromAsset(context.getAssets(), "fonts/fontawesome-webfont.ttf");
            if (language.equals("en")) {
                custom_Din = Typeface.createFromAsset(context.getAssets(), "fonts/OpenSans-Light.ttf");
            } else {
                custom_Din = Typeface.createFromAsset(context.getAssets(), "fonts/DIN Next LT W23 Regular.ttf");
            }

            LayoutInflater inflater = ((MainContainer) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new UserHolder();
            holder.formation_palyer_photo = (ImageView) row.findViewById(R.id.formation_palyer_photo);
            holder.formation_player_name = (TextView) row.findViewById(R.id.formation_player_name);
            holder.formation_ratingBar = (RatingBar) row.findViewById(R.id.formation_ratingBar);
            holder.formation_text_rating = (TextView) row.findViewById(R.id.formation_text_rating);
            holder.formation_textview_addplayer = (TextView) row.findViewById(R.id.formation_textview_addplayer);
            row.setTag(holder);
        } else {
            holder = (UserHolder) row.getTag();
        }
        final FormationData formationData = data.get(position);
        holder.formation_player_name.setText(formationData.getPlayerName());
        holder.formation_player_name.setTypeface(custom_Din);
        holder.formation_text_rating.setText(formationData.getRatingText());
        holder.formation_text_rating.setTypeface(custom_Din);
//        holder.formation_textview_addplayer.setTypeface(custom_font_Awesome);
        int num = (int) Float.parseFloat(formationData.getRatingBar());
        Log.d("searchhh  ",String.valueOf(num));
        holder.formation_ratingBar.setNumStars(num);
        holder.formation_ratingBar.setRating(num);



        if (language.equals("en")) {
            childLayout = (LinearLayout) row.findViewById(R.id.childLayout);
            childLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
        ////

//        LayerDrawable stars = (LayerDrawable) holder.formation_ratingBar.getProgressDrawable();
//        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
//        stars.getDrawable(0).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
//        stars.getDrawable(1).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        ////
 if (!formationData.getPlayerPhoto().isEmpty()) {
     Picasso.
             with(context).
             load(formationData.getPlayerPhoto())
             .error(R.drawable.placeholder_user_photo)
             .placeholder(R.drawable.placeholder_user_photo)
             .into(holder.formation_palyer_photo);
 }
        else {
     holder.formation_palyer_photo.setImageResource(R.drawable.placeholder_user_photo);
 }


        row.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.i("Edit Button Clicked", "**********");
//                LinearLayout searchPlayerLayout = (LinearLayout)((FragmentActivity)context).findViewById(R.id.searchPlayerLayout);
//                searchPlayerLayout.setVisibility(View.GONE);
                //   flower_dialog.show();

                // holder.formation_textview_addplayer.setText("&#xf068;");
                // Fragment payFragment = new PayFragment();
                //FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                // ft.replace(R.id.placeholder2, payFragment).commit();
                // Instantiate the RequestQueue.
               SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
                Gson gson = new Gson();
                String json1 = mPrefs.getString("userObject", "");
                User obj = gson.fromJson(json1, User.class);
                RequestQueue queue = Volley.newRequestQueue(context);
                String url = URLs.GET_Player_properties_by_id + "?key=logic123&id="
                        + formationData.getId()+"&myId="+obj.getId();
// Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                Activity activity = (Activity) context;
                                //   flower_dialog.dismiss();
                                Bundle bundle = new Bundle();
                                bundle.putString("playersJson", response);
                                Fragment getPlayerFragment = new GetPlayerFragment();
                                getPlayerFragment.setArguments(bundle);
                                FragmentTransaction ft = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.search_about_player_fragment, getPlayerFragment).addToBackStack(null).commit();
                                FragmentTransaction ft1 = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                                ft1.replace(R.id.placeholder1, new HeaderFragment_Inner())
                                        .commit();
                                MainContainer.headerNum = 3;
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


        return row;

    }

    static class UserHolder {
        ImageView formation_palyer_photo;
        TextView formation_player_name;
        RatingBar formation_ratingBar;
        TextView formation_text_rating;
        TextView formation_textview_addplayer;


    }
}
