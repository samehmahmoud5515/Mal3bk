package com.logicdesigns.mohammed.mal3bklast.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.logicdesigns.mohammed.mal3bklast.ClubFragment;
import com.logicdesigns.mohammed.mal3bklast.MainContainer;
import com.logicdesigns.mohammed.mal3bklast.MainPageFragment;
import com.logicdesigns.mohammed.mal3bklast.R;
import com.logicdesigns.mohammed.mal3bklast.appFonts.AppFonts;
import com.logicdesigns.mohammed.mal3bklast.header.HeaderFragment_Inner;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by logicDesigns on 9/25/2017.
 */

public class InfoDialog {
    Context _context;
    Typeface DINNext;
    String language = null;
    public InfoDialog(Context context)
    {
        _context = context;
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);;
        language = sharedPref.getString("language", "ar");
    }
    public void showDialog()
    {
        final Activity mActivity = (Activity) _context;
        DINNext = Typeface.createFromAsset(mActivity.getAssets(), "fonts/DIN Next LT W23 Regular.ttf");

        final Dialog dialog = new Dialog(mActivity, R.style.CustomDialogTheme);
        dialog.setContentView(R.layout.no_playgrounds_custom_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView no_message_text = (TextView) dialog.findViewById(R.id.no_message_text);
        no_message_text.setTypeface(DINNext);

        Button close_button = (Button) dialog.findViewById(R.id.btn_close_no_playground);
        close_button.setTypeface(DINNext);

        if (language.equals("en")) {
            close_button.setText(R.string.close);
            no_message_text.setText("The play ground isn't available at that time");

        }
        else{
            close_button.setText(R.string.close_ar);
            no_message_text.setText("الملعب غير متاح في هذا الوقت");
        }
        close_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.cancel();


            }
        });
        dialog.show();
    }
    int selectedStart,selectedEnd;

    public class TimeSlots{
        int startTime=0,endTime=0;
    }

    public void showDialogForPlayground(String jsonArray, final String playgroundId, final String date, final boolean isMainFragment)
    {
        String timeString;
        if (language.equals("ar"))
            timeString= "الوقت المتاح في ";
        else timeString = "Time available in ";
        timeString += date;

        final ArrayList<TimeSlots> timeSlotsArrayList = new ArrayList<>();
        try {
            JSONArray slotsArray = new JSONArray(jsonArray);

            for (int i = 0 ; i<slotsArray.length();i++)
            {
                timeString+= "\n";
                JSONObject timeSlotObj = slotsArray.getJSONObject(i);
                TimeSlots timeSlots=  new TimeSlots();
                timeSlots.startTime =Integer.parseInt( timeSlotObj.getString("start"));
                if (timeSlotObj.getString("end").equals("00"))
                    timeSlots.endTime = 24;
                else
                    timeSlots.endTime =Integer.parseInt( timeSlotObj.getString("end"));
                timeSlotsArrayList.add(timeSlots);

                timeString += convertTime_12hFormat(String.valueOf(timeSlots.startTime)) + " - " + convertTime_12hFormat(String.valueOf(timeSlots.endTime));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final Activity mActivity = (Activity) _context;
        DINNext = Typeface.createFromAsset(mActivity.getAssets(), "fonts/DIN Next LT W23 Regular.ttf");

        final Dialog dialog = new Dialog(_context);
        dialog.setContentView(R.layout.playground_available_time);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);


        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView message_text = (TextView) dialog.findViewById(R.id.message_text);
        message_text.setTypeface(DINNext);

        TextView time_icon = dialog.findViewById(R.id.time_icon);
        TextView time_tv = dialog.findViewById(R.id.time_tv);
        final Button choose_start_btn = dialog.findViewById(R.id.choose_start_btn);
        final Button choose_time_end_btn = dialog.findViewById(R.id.choose_time_end_btn);
        TextView time_end_icon = dialog.findViewById(R.id.time_end_icon);
        TextView time_end_tv = dialog.findViewById(R.id.time_end_tv);
        Button ok_button = (Button) dialog.findViewById(R.id.ok_button);
        Button close_btn = (Button) dialog.findViewById(R.id.close_btn);
        AppFonts appFonts = AppFonts.getInstance(_context);
        time_icon.setTypeface(appFonts.getFontawsome());
        time_end_icon.setTypeface(appFonts.getFontawsome());
        ok_button.setTypeface(DINNext);
        time_tv.setTypeface(appFonts.getDIN_NEXT());
        choose_time_end_btn.setTypeface(appFonts.getDIN_NEXT());
        time_end_tv.setTypeface(appFonts.getDIN_NEXT());
        close_btn.setTypeface(appFonts.getDIN_NEXT());
        if (language.equals("en"))
        {
            ok_button.setText("Ok");
            close_btn.setText("Close");
            time_tv.setText("Start time");
            time_end_tv.setText("End time  ");
            choose_start_btn.setText("Choose");
            choose_time_end_btn.setText("Choose");
        }
        if (timeSlotsArrayList.size()==1)
        {
            if (timeSlotsArrayList.get(0).startTime== timeSlotsArrayList.get(0).endTime)
            {
               if(language.equals("ar")) {
                   timeString = "لا يوجد وقت متاح في ";
                   timeString += date;
                   timeString+="\n";
                   timeString +="اختار تاريخ اخر";
               }
               else {
                   timeString = "No time available for reservation ";
                   timeString += date;
                   timeString+="\n";
                   timeString +="choose another date";
               }
               choose_start_btn.setVisibility(View.GONE);
               choose_time_end_btn.setVisibility(View.GONE);
               time_end_icon.setVisibility(View.GONE);
               time_end_tv.setVisibility(View.GONE);
               time_icon.setVisibility(View.GONE);
               time_tv.setVisibility(View.GONE);
               ok_button.setVisibility(View.GONE);
            }
        }
        message_text.setText(timeString);
        choose_start_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Calendar now = Calendar.getInstance();

                TimePickerDialog dpd = TimePickerDialog.newInstance(
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                                String time = String.valueOf(hourOfDay);
                                String converted_time = String.valueOf(hourOfDay);
                                if (Integer.parseInt(converted_time) > 12) {
                                    int _24format = Integer.parseInt(converted_time);
                                    int _12format = _24format - 12;
                                    choose_start_btn .setText(_12format + "PM");
                                }
                                else if (Integer.parseInt(converted_time) == 12)
                                    choose_start_btn.setText(converted_time + "PM");
                                else choose_start_btn.setText(converted_time + "AM");

                                selectedStart = hourOfDay;
                                if  (hourOfDay == 0){
                                    choose_start_btn.setText("12AM");
                                    selectedStart = 0;
                                }
                                Log.d("TIMEStart->>>>>",String.valueOf(selectedStart));

                            }
                        },
                        false
                );
                dpd.setStartTime(now.get(Calendar.HOUR), 00, 00);
                dpd.enableMinutes(false);
                if (language.equals("en")) {
                    dpd.setOkText(R.string.chooseEn);
                    dpd.setCancelText(R.string.cancel);
                } else {
                    dpd.setOkText("اختار");
                    dpd.setCancelText("إلغاء");
                }

                dpd.show(((MainContainer)_context).getFragmentManager(), "time_start");

            }
        });

        choose_time_end_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();

                TimePickerDialog dpd = TimePickerDialog.newInstance(
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                                String converted_time = String.valueOf(hourOfDay);
                                if (Integer.parseInt(converted_time) > 12) {
                                    int _24format = Integer.parseInt(converted_time);
                                    int _12format = _24format - 12;
                                    choose_time_end_btn .setText(_12format + "PM");
                                }
                                else if (Integer.parseInt(converted_time) == 12)
                                    choose_time_end_btn.setText(converted_time + "PM");
                                else choose_time_end_btn.setText(converted_time + "AM");

                                selectedEnd = hourOfDay;
                                if  (hourOfDay == 0){
                                    choose_time_end_btn.setText("12AM");
                                    selectedEnd= 12;
                                }
                                if (hourOfDay == 12)
                                    selectedEnd= 24;
                                Log.d("TIMEEnd->>>>>",String.valueOf(selectedEnd));

                            }
                        },
                        false
                );
                dpd.setStartTime(now.get(Calendar.HOUR), 00, 00);
                dpd.enableMinutes(false);
                if (language.equals("en")) {
                    dpd.setOkText(R.string.chooseEn);
                    dpd.setCancelText(R.string.cancel);
                } else {
                    dpd.setOkText("اختار");
                    dpd.setCancelText("إلغاء");
                }

                dpd.show(((MainContainer)_context).getFragmentManager(), "time_end");

            }
        });
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean condition= true;
                for (int i =0;i<timeSlotsArrayList.size();i++)
                {
                    TimeSlots timeSlots = timeSlotsArrayList.get(i);
                    if (selectedStart>=timeSlots.startTime&&selectedEnd<=timeSlots.endTime)
                    {
                        dialog.dismiss();
                        Bundle bundle = new Bundle();
                        Fragment clubFragment = new ClubFragment();
                        String bundle_start_time = convertTime_12hFormat(String.valueOf(selectedStart));
                        String bundle_end_time = convertTime_12hFormat(String.valueOf(selectedEnd));
                        bundle.putString("start_time", bundle_start_time);
                        bundle.putString("end_time", bundle_end_time);
                        bundle.putString("date", date);
                        bundle.putString("playgroundId", playgroundId);
                        clubFragment.setArguments(bundle);

                        FragmentTransaction ft =((FragmentActivity) _context).getSupportFragmentManager().beginTransaction();
                       if (!isMainFragment) {
                           ft.add(R.id.frame_container, clubFragment).addToBackStack("ClubFragment")
                                   .commit();
                           MainContainer.headerNum = 2;
                           MainContainer.pageNumber = 2.3;
                       }
                       else {
                           ft.replace(R.id.mainPage_framelayout, clubFragment).addToBackStack(null)
                                   .commit();
                           MainContainer.pageNumber = 1.3;
                           MainContainer.headerNum = 1;
                           FragmentTransaction ft1 = ((FragmentActivity) _context).getSupportFragmentManager().beginTransaction();
                           ft1.replace(R.id.placeholder1, new HeaderFragment_Inner())
                                   .commit();
                       }
                        condition = false;
                    }
                }
                if (condition) {
                    if (language.equals("ar"))
                        Toast.makeText(_context, "الوقت الذي ادخلته غير متاح", Toast.LENGTH_SHORT).show();
                    else Toast.makeText(_context, "Time isn't available", Toast.LENGTH_SHORT).show();

                }
            }
        });
        dialog.show();
    }

    public  void showPlaygroundDetails(String imageURL,String playgroundName,String city,String address)
    {
        Typeface    fontAwesome = Typeface.createFromAsset(_context.getAssets(), "fonts/fontawesome-webfont.ttf");
        Typeface  dinNext = Typeface.createFromAsset(_context.getAssets(), "fonts/DIN Next LT W23 Regular.ttf");
        Dialog dialog;
        final Activity mActivity = (Activity) _context;

        dialog = new Dialog(mActivity, R.style.CustomDialog);
        dialog.setContentView(R.layout.playground_details_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ImageView playground_imageView = (ImageView) dialog.findViewById(R.id.playground_imageView);
        TextView playground_name_tv = (TextView) dialog.findViewById(R.id.playground_name_tv);
        TextView city_ic = (TextView) dialog.findViewById(R.id.city_ic);
        TextView city_tv = (TextView) dialog.findViewById(R.id.city_tv);
        TextView address_tv = (TextView) dialog.findViewById(R.id.address_tv);

        playground_name_tv.setTypeface(dinNext);
        city_ic.setTypeface(fontAwesome);
        city_tv.setTypeface(dinNext);
        city_tv.setTypeface(dinNext);
        address_tv.setTypeface(dinNext);

        if (!imageURL.equals("")) {

            Picasso.
                    with(mActivity).
                    load(imageURL)
                    //.memoryPolicy(MemoryPolicy.NO_CACHE)
                    // .networkPolicy(NetworkPolicy.NO_CACHE)
                    .error(R.drawable.placeholder_user_photo)
                    .placeholder(R.drawable.placeholder_user_photo)
                    .into(playground_imageView);
        } else {
            playground_imageView.setImageResource(R.drawable.playgroundphoto);
        }

        if (!playgroundName.equals(""))
        {
            playground_name_tv.setText(playgroundName);
            playground_name_tv.setVisibility(View.VISIBLE);
        }
        else {
            playground_name_tv.setVisibility(View.GONE);

        }
        if (!city.equals(""))
        {
            city_tv.setText(city);
            city_tv.setVisibility(View.VISIBLE);
            city_ic.setVisibility(View.VISIBLE);
        }
        else {
            city_tv.setVisibility(View.GONE);
            city_ic.setVisibility(View.GONE);
        }
        if (!address.equals(""))
        {
            address_tv.setText(address);
            address_tv.setVisibility(View.VISIBLE);
        }
        else {
            address_tv.setVisibility(View.GONE);
        }
        dialog.show();
    }

    public String convertTime_12hFormat(String time) {
        String covertedTime;
        if (Integer.parseInt(time) > 12) {
            int _24format = Integer.parseInt(time);
            int _12format = _24format - 12;
            covertedTime = _12format + "PM";
        } else covertedTime = time + "AM";
        if (Integer.parseInt(time) == 0)
        {
            covertedTime = "12AM";
        }
        return covertedTime;
    }
}
