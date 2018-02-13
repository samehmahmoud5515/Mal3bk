package com.logicdesigns.mohammed.mal3bklast.adapters;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.logicdesigns.mohammed.mal3bklast.Dialogs.InfoDialog;
import com.logicdesigns.mohammed.mal3bklast.ExceptionHandling.ExceptionHandling;
import com.logicdesigns.mohammed.mal3bklast.JSON_PARSER.Playground_Search_reservation;
import com.logicdesigns.mohammed.mal3bklast.MainContainer;
import com.logicdesigns.mohammed.mal3bklast.R;
import com.logicdesigns.mohammed.mal3bklast.SharedPref.PrefManager;
import com.logicdesigns.mohammed.mal3bklast.common.URLs;
import com.logicdesigns.mohammed.mal3bklast.utility.DateComparing;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

/**
 * Created by Sameh on 4/21/2017.
 */

public class ChoosePlaygroundAdapter extends RecyclerView.Adapter<ChoosePlaygroundAdapter.ViewHolder>
{

    ArrayList<Playground_Search_reservation>  playgroundsList;

    Context context;
    public ChoosePlaygroundAdapter(ArrayList<Playground_Search_reservation> model, Context context) {
        this.playgroundsList = model;
        this.context = context;
    }


    @Override
    public ChoosePlaygroundAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.choose_playground_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChoosePlaygroundAdapter.ViewHolder viewHolder, final int position) {
        Playground_Search_reservation playground = playgroundsList.get(position);
        viewClick(viewHolder.view_choose_playground_item, playground.getId());
        viewHolder.playground_name_tv_.setText(playground.getName() + "\n" + playground.getClubName());
        viewHolder.playground_address_tv_.setText(playground.getAddress());
        if (!playground.getImage().equals(""))
        {
            Picasso.with(context).load(playground.getImage()).fit()
                    .placeholder(R.drawable.playgroundphoto).error(R.drawable.playgroundphoto)
            .into(viewHolder.playgroundimage_iv_);
        }

    }

    @Override
    public int getItemCount() {
        return playgroundsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout view_choose_playground_item;
        TextView playground_name_tv_, playground_address_tv_;
        ImageView playgroundimage_iv_;
        public ViewHolder(View view) {
            super(view);
            view_choose_playground_item = (LinearLayout) view.findViewById(R.id.view_choose_playground_item);
            playground_name_tv_ = (TextView) view.findViewById(R.id.playground_name_tv_);
            playground_address_tv_ = (TextView) view.findViewById(R.id.playground_address_tv_);
            playgroundimage_iv_ = (ImageView) view.findViewById(R.id.playgroundimage_iv_);
        }
    }

    void viewClick(LinearLayout linearLayout, final String playgroundId)
    {
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                                //2018-01-23
                                String date,rawDate;
                                if ((monthOfYear + 1) > 9)
                                    date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                                else date = year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth;
                                //dd/MM/yyyy
                                int mon = monthOfYear+1;
                                rawDate = dayOfMonth+"/"+mon+"/"+year;
                                PrefManager prefManager = new PrefManager(context);
                                String language = prefManager.getLanguage();
                                if (date == null|| rawDate ==null)
                                {
                                    if (language.equals("en"))
                                        Toast.makeText(context, "Please select date", Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(context, "ادخل التاريخ من فضلك", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                DateComparing dateComparing = new DateComparing(rawDate);
                                if (!dateComparing.checkDate())
                                {
                                    if (language.equals("en"))
                                        Toast.makeText(context, "Enter a valid Date", Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(context, "ادخل تاريخ صالح", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                getTimes(date,playgroundId);
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)

                );
                PrefManager prefManager = new PrefManager(context);
                String language = prefManager.getLanguage();
                if (language.equals("en")) {
                    dpd.setOkText(R.string.chooseEn);
                    dpd.setCancelText(R.string.cancel);
                } else {
                    dpd.setOkText("اختار");
                    dpd.setCancelText("إلغاء");
                }
                dpd.show(((MainContainer)context).getFragmentManager(), "date");
            }
        });
    }

    void getTimes(final String date, final String playgroundId)
    {
        final ACProgressFlower flower_dialog;
        flower_dialog = new ACProgressFlower.Builder(context).direction(ACProgressConstant.DIRECT_CLOCKWISE).themeColor(Color.WHITE).fadeColor(Color.GREEN).build();
        flower_dialog.show();
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = URLs.GET_Available_Times+playgroundId+ "&bookdate="+date;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        flower_dialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("success").equals("1")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("playground");
                                JSONObject playgroundObject = jsonArray.getJSONObject(0);
                                JSONArray slotArray = playgroundObject.getJSONArray("Slot");
                                InfoDialog infoDialog = new InfoDialog(context);
                                infoDialog.showDialogForPlayground(slotArray.toString(),playgroundId,date,false);
                            }
                            else {
                                // No times available
                                ExceptionHandling exceptionHandling = new ExceptionHandling(context);
                                String dialogStringAr = "لا توجد اوقات متاحة في";
                                dialogStringAr += "\n"; dialogStringAr+= date;
                                exceptionHandling.showErrorDialog(dialogStringAr,"No times available in\n"+ date);
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
        queue.add(stringRequest);
    }

}