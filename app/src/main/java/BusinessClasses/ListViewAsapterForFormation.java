package BusinessClasses;


import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.logicdesigns.mohammed.mal3bklast.GetPlayerFragment;
import com.logicdesigns.mohammed.mal3bklast.MainContainer;
import com.logicdesigns.mohammed.mal3bklast.PayFragment;
import com.logicdesigns.mohammed.mal3bklast.R;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class ListViewAsapterForFormation extends ArrayAdapter<FormationData> {


    Context context;
    int layoutResourceId;
    ArrayList<FormationData> data = new ArrayList<FormationData>();
    Typeface custom_font_Awesome ;
    Typeface custom_Din;

    public ListViewAsapterForFormation(Context context, int layoutResourceId,
                                       ArrayList<FormationData> data) {
        super(context, layoutResourceId , data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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
            holder.formation_palyer_photo = (ImageView) row.findViewById(R.id.formation_palyer_photo);
            holder.formation_player_name = (TextView) row.findViewById(R.id.formation_player_name);
            holder.formation_ratingBar = (RatingBar) row.findViewById(R.id.formation_ratingBar);
            holder.formation_text_rating = (TextView) row.findViewById(R.id.formation_text_rating);
            holder.formation_textview_addplayer=(TextView) row.findViewById(R.id.formation_textview_addplayer);


            row.setTag(holder);
        }
        else {
            holder = (UserHolder) row.getTag();
        }
        FormationData  formationData = data.get(position);
        holder.formation_player_name.setText(formationData.getPlayerName());
        holder.formation_player_name.setTypeface(custom_Din);
        holder.formation_text_rating.setText(formationData.getRatingText());
        holder.formation_text_rating.setTypeface(custom_Din);
        holder.formation_textview_addplayer.setTypeface(custom_font_Awesome);

        holder.formation_ratingBar.setRating(Float.parseFloat("4.5"));
        ////

        LayerDrawable stars = (LayerDrawable) holder.formation_ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(0).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
        ////


        holder.formation_textview_addplayer.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.i("Edit Button Clicked", "**********");
                Toast.makeText(context, "Edit button Clicked",
                        Toast.LENGTH_LONG).show();


               // holder.formation_textview_addplayer.setText("&#xf068;");
               // Fragment payFragment = new PayFragment();
                //FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
               // ft.replace(R.id.placeholder2, payFragment).commit();
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
