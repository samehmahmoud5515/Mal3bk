package com.logicdesigns.mohammed.mal3bklast.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.logicdesigns.mohammed.mal3bklast.R;

/**
 * Created by logicDesigns on 7/27/2017.
 */

public class DialogRatingAdapter extends BaseAdapter {
    Context _Context;
    String[] _arrayValues;
    String language;
     public DialogRatingAdapter(Context context, String[] array, String lang)
    {
        _Context=context;
        _arrayValues =array;
        language = lang;
    }

    @Override
    public int getCount() {
        return _arrayValues.length;
    }

    @Override
    public Object getItem(int position) {
        return _arrayValues[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
     ViewHolder holder;
        final LayoutInflater inflater = (LayoutInflater) _Context.getApplicationContext()
                .getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(
                    R.layout.dialog_rating_item, null);
            holder = new ViewHolder();
            holder.ratingBar=(RatingBar) convertView.findViewById(R.id.ratingBar_dialog);
            holder.allText = (TextView) convertView.findViewById(R.id.allText);
            holder.allText.setTypeface(Typeface.createFromAsset(_Context.getAssets(), "fonts/DIN Next LT W23 Regular.ttf"));

            convertView .setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();

        }

        if (_arrayValues[position] .equals("6"))
        {
            holder.ratingBar.setVisibility(View.GONE);
            holder.allText.setVisibility(View.VISIBLE);
            if (language.equals("ar"))
            {
                holder.allText.setText("الكل");
            }
            else {
                holder.allText.setText("All");

            }
        }
        else {
            holder.ratingBar.setVisibility(View.VISIBLE);
            holder.allText.setVisibility(View.GONE);
        }
        holder.ratingBar.setRating(Integer.parseInt(_arrayValues[position])+4);
        holder.ratingBar.setNumStars(Integer.parseInt(_arrayValues[position]));
        return convertView;
    }
    public  static  class ViewHolder{
        RatingBar ratingBar;
        TextView allText;
    }
}
