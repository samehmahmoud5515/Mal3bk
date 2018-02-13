package com.logicdesigns.mohammed.mal3bklast;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
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
import com.logicdesigns.mohammed.mal3bklast.Models.Time_reserv;
import com.logicdesigns.mohammed.mal3bklast.SharedPref.PrefManager;
import com.logicdesigns.mohammed.mal3bklast.common.SampleRecyclerAdapter;
import com.logicdesigns.mohammed.mal3bklast.common.URLs;
import com.logicdesigns.mohammed.mal3bklast.header.HeaderFragment_Inner;
import com.logicdesigns.mohammed.mal3bklast.utility.DateComparing;
import com.logicdesigns.mohammed.mal3bklast.utility.TimeComparing;
import com.squareup.picasso.Picasso;
import com.tapadoo.alerter.Alerter;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import BusinessClasses.JSONParser;
import BusinessClasses.MainPageData;
import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import static com.facebook.FacebookSdk.getApplicationContext;


public class MainPageFragment extends Fragment implements  com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener, DialogInterface.OnDismissListener,SwipeRefreshLayout.OnRefreshListener {
    ArrayList<MainPageData> listitems = new ArrayList<>();
    RecyclerView MyRecyclerView;
    JSONParser jsonParser;
    JSONArray User = null;
    ACProgressFlower flower_dialog;
    Button  date_btn, date_search_btn;
    TextView   ic_date;
    String  selected_date = "";
    ArrayList<Time_reserv> time_reserv_arr = new ArrayList<Time_reserv>();
    SharedPreferences sharedPref;
    String language = null;
    static int noAvailableTime=1;
    TextView noOffers_textView ;
    static   int cond_is_met = 0;


    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        language = sharedPref.getString("language", "ar");
    }
    @Override
    public void onRefresh() {
        new GetPlaygroundOffer().execute();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onResume() {
        super.onResume();
         noAvailableTime=1;

         cond_is_met = 0;
    }

    @Override
    public void onPause() {
        super.onPause();
        noAvailableTime=1;

        cond_is_met = 0;
    }

    @Override
    public void onStop() {
        super.onStop();
        noAvailableTime=1;

        cond_is_met = 0;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jsonParser = new JSONParser();





    }

    @Override
    public void onStart() {
        super.onStart();
        //noAvailableTime = -1;
      /*  TextView title_header_fragment = (TextView) getActivity().findViewById(R.id.title_header_fragment);
        if (language.equals("en")) {
            title_header_fragment.setText("Last Offers");
        } else {
            title_header_fragment.setText("اخر العروض");
        }*/
        flower_dialog = new ACProgressFlower.Builder(getActivity()).direction(ACProgressConstant.DIRECT_CLOCKWISE).themeColor(Color.WHITE).fadeColor(Color.GREEN).build();
        try {
           // new GetPlaygroundOffer().execute();
        }catch (Exception e)
        {
         /*   if (flower_dialog.isShowing())
            {
                flower_dialog.dismiss();
            }*/
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_mainpage, container, false);
        FrameLayout mainPage_framelayout = (FrameLayout) view.findViewById(R.id.mainPage_framelayout);
        TextView title_header_fragment = (TextView) getActivity().findViewById(R.id.title_header_fragment);

        if (language.equals("en")) {
            mainPage_framelayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            title_header_fragment.setText("Latest Offers");

        }
        else {
            title_header_fragment.setText("اخر العروض");

        }
        noOffers_textView = (TextView) view.findViewById(R.id.noOffers_textView);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        new GetPlaygroundOffer().execute();
                                        swipeRefreshLayout.setRefreshing(false);
                                    }
                                }
        );

        MyRecyclerView = (RecyclerView) view.findViewById(R.id.offersRecyclerView);
        MyRecyclerView.setAdapter(new SampleRecyclerAdapter());
        MyRecyclerView.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
        MyLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(MyRecyclerView);
        MyRecyclerView.setLayoutManager(MyLayoutManager);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date;
        if ((monthOfYear + 1) > 9)
            date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        else date = year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth;
        date_btn.setText(date);
        selected_date = date;
        //dd/MM/yyyy
        int mon = monthOfYear+1;
        rawDate = dayOfMonth+"/"+mon+"/"+year;
    }
     String rawDate;


    @Override
    public void onDismiss(DialogInterface dialog) {

        selected_date = "";
    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private ArrayList<MainPageData> list;
        Typeface font_awesome = Typeface.createFromAsset(getActivity().getAssets(), "fonts/fontawesome-webfont.ttf");
        Typeface dinNext;
        Typeface droidKufi;

        public MyAdapter(ArrayList<MainPageData> Data) {
            list = Data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_page_fragment_child, parent, false);
            LinearLayout mainLayout = (LinearLayout) view.findViewById(R.id.mainLayout);
            if (language.equals("en")) {
                dinNext = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Light.ttf");
                droidKufi = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Light.ttf");
                mainLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);

            } else {
                dinNext = Typeface.createFromAsset(getActivity().getAssets(), "fonts/DIN Next LT W23 Regular.ttf");
                droidKufi = Typeface.createFromAsset(getActivity().getAssets(), "fonts/DroidKufi-Regular.ttf");
            }
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @SuppressWarnings("deprecation")
        public Spanned fromHtml(String html) {
            Spanned result;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
            } else {
                result = Html.fromHtml(html);
            }
            return result;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.town_playground_main_page.setText(list.get(position).getTown());
            holder.town_playground_main_page.setTypeface(droidKufi);
            holder.playground_name_main_page.setText(list.get(position).getplaygroundName());
            if (list.get(position).getPlayGroundImage().equals(""))
            {
                holder.image_playground_main_page.setImageResource(R.drawable.playgroundmain);
            }
            else {
                Picasso.
                        with(getActivity()).
                        load(list.get(position).getPlayGroundImage())
                        .error(R.drawable.playgroundmain)
                        .placeholder(R.drawable.playgroundmain)
                        .into(holder.image_playground_main_page);
            }
            holder.playground_name_main_page.setTypeface(dinNext);
            holder.town_icon_main_page.setTypeface(font_awesome);
            holder.moneyIcon.setTypeface(font_awesome);
            holder.btn_main_page.setTypeface(dinNext);
            holder.calender_ic.setTypeface(font_awesome);
            holder.price_tv.setTypeface(dinNext);
            holder.tv_date.setTypeface(dinNext);
            holder.price_tv.setTypeface(dinNext);
            holder.to_tv.setTypeface(dinNext);
            holder.tv_date_end_value.setText(list.get(position).getEndOfOffer());
            holder.tv_date_start_value.setText(list.get(position).getStartOfOffer());
            holder.price_value_tv.setText(list.get(position).getPrice());

            if (language.equals("en")) {
                holder.tv_date.setText(R.string.from);
                holder.tv_date.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                holder.calender_ic.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
                holder.to_tv.setText(R.string.to);
                holder.price_tv.setText(R.string.price);
                holder.btn_main_page.setText(R.string.Reservation);
                holder.town_icon_main_page.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            }


            holder.btn_main_page.setOnClickListener(new View.OnClickListener() {

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
                                    PrefManager prefManager = new PrefManager(getActivity());
                                    String language = prefManager.getLanguage();
                                    if (date == null|| rawDate ==null)
                                    {
                                        if (language.equals("en"))
                                            Toast.makeText(getActivity(), "Please select date", Toast.LENGTH_SHORT).show();
                                        else
                                            Toast.makeText(getActivity(), "ادخل التاريخ من فضلك", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    DateComparing dateComparing = new DateComparing(rawDate);
                                    if (!dateComparing.checkDate())
                                    {
                                        if (language.equals("en"))
                                            Toast.makeText(getActivity(), "Enter a valid Date", Toast.LENGTH_SHORT).show();
                                        else
                                            Toast.makeText(getActivity(), "ادخل تاريخ صالح", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    if (dateComparing.compareStringDates(date,list.get(position).getEndOfOffer())>0)
                                    {
                                        if (language.equals("en"))
                                            Toast.makeText(getActivity(), "Date outside offer", Toast.LENGTH_SHORT).show();
                                        else
                                            Toast.makeText(getActivity(), "التاريخ خارج العرض", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    getTimes(date,listitems.get(position).getPlaygroundID());
                                }
                            },
                            now.get(Calendar.YEAR),
                            now.get(Calendar.MONTH),
                            now.get(Calendar.DAY_OF_MONTH)

                    );
                    PrefManager prefManager = new PrefManager(getActivity());
                    String language = prefManager.getLanguage();
                    if (language.equals("en")) {
                        dpd.setOkText(R.string.chooseEn);
                        dpd.setCancelText(R.string.cancel);
                    } else {
                        dpd.setOkText("اختار");
                        dpd.setCancelText("إلغاء");
                    }
                    dpd.show(getActivity().getFragmentManager(), "date");

                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }



    }

    public class GetPlaygroundOffer extends AsyncTask<String, String, Boolean> {
        ACProgressFlower flower_dialog_new1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            flower_dialog_new1 = new ACProgressFlower.Builder(getActivity())
                    .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                    .themeColor(Color.WHITE)
                    .fadeColor(Color.GREEN).build();
            flower_dialog_new1.show();
            noOffers_textView.setVisibility(View.GONE);
            MyRecyclerView.setVisibility(View.VISIBLE);
        }

        @Override
        protected Boolean doInBackground(String... arg) {
            // TODO Auto-generated method stub


            List<NameValuePair> params = new ArrayList<NameValuePair>();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            String date = df.format(Calendar.getInstance().getTime());
            params.add(new BasicNameValuePair("key", "logic123"));
            params.add(new BasicNameValuePair("date", date));


            JSONObject json = jsonParser.makeHttpRequest("http://logic-host.com/work/kora/beta/phpFiles/getPlaygroundOffersAPI.php",
                    "GET", params);


            // check for success tag
            try {
                listitems = new ArrayList<>();
                int success = -1;
                try {
                    success = json.getInt("success");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (success == 1) {

                    User = json.getJSONArray("playgroundoffers");

                    for (int i = 0; i < User.length(); i++) {
                        JSONObject c = User.getJSONObject(i);
                        MainPageData mainPageData = new MainPageData();
                        if (!c.isNull("playgroundDescription") && c.has("playgroundDescription"))
                           mainPageData.setDescription(c.getString("playgroundDescription"));
                        else  mainPageData.setDescription("");
                        if (!c.isNull("playgroundName") && c.has("playgroundName"))
                           mainPageData.setplaygroundName(c.getString("playgroundName"));
                        else  mainPageData.setplaygroundName("");
                        if (language.equals("ar"))
                        { if (!c.isNull("playgroundCityName") && c.has("playgroundCityName"))
                           mainPageData.setTown(c.getString("playgroundCityName"));
                        else  mainPageData.setTown("");}
                        else { if (!c.isNull("playgroundCityNameEn") && c.has("playgroundCityNameEn"))
                            mainPageData.setTown(c.getString("playgroundCityNameEn"));
                        else  mainPageData.setTown("");}
                        if (!c.isNull("playgroundId") && c.has("playgroundId"))
                             mainPageData.setPlaygroundID(c.getString("playgroundId"));
                        else mainPageData.setPlaygroundID("");
                        if (!c.isNull("playgroundImage") && c.has("playgroundImage"))
                           mainPageData.setPlayGroundImage(c.getString("playgroundImage"));
                        else mainPageData.setPlayGroundImage("");
                        if (!c.isNull("startOfOffer") && c.has("startOfOffer"))
                           mainPageData.setStartOfOffer(c.getString("startOfOffer"));
                        else mainPageData.setStartOfOffer("");
                        if (!c.isNull("endOfOffer") && c.has("endOfOffer"))
                            mainPageData.setEndOfOffer(c.getString("endOfOffer"));
                        else mainPageData.setEndOfOffer("");
                        if (!c.isNull("price") && c.has("price"))
                            mainPageData.setPrice(c.getString("price"));
                        else mainPageData.setPrice("");
                      /*  JSONArray timeSlotJsonArray = c.getJSONArray("slots");
                        for (int j = 0 ; j<timeSlotJsonArray.length();j++)
                        {
                            timeString+= "\n";
                            JSONObject timeSlotObj = timeSlotJsonArray.getJSONObject(j);
                            TimeSlots timeSlots = new TimeSlots();
                            timeSlots.startTime =Integer.parseInt( timeSlotObj.getString("start"));
                            if (timeSlotObj.getString("end").equals("00"))
                                timeSlots.endTime = 24;
                            else
                                timeSlots.endTime =Integer.parseInt( timeSlotObj.getString("end"));
                            timeSlotsArrayList.add(timeSlots);
                             InfoDialog infoDialog = new InfoDialog(getActivity());
                            timeString += infoDialog.convertTime_12hFormat(String.valueOf(timeSlots.startTime)) + " - " + convertTime_12hFormat(String.valueOf(timeSlots.endTime));

                        }*/

                        listitems.add(mainPageData);

                    }


                    return true;

                } else {
                    Log.i("test view", "false");


                    return false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
                if (flower_dialog.isShowing())
                {
                    flower_dialog.dismiss();
                }


            }

            return false;


        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
                if (listitems.size() > 0 & MyRecyclerView != null) {
                    try {
                        MyRecyclerView.setAdapter(new MyAdapter(listitems));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                Log.i("test view", "good");
                flower_dialog_new1.dismiss();
            }
            else {
                if (flower_dialog.isShowing())
                {
                    flower_dialog.dismiss();
                }
                if (flower_dialog_new1.isShowing())
                {
                    flower_dialog_new1.dismiss();
                }

                noOffers_textView.setVisibility(View.VISIBLE);
                MyRecyclerView.setVisibility(View.GONE);
                Typeface dinNext = Typeface.createFromAsset(getActivity().getAssets(), "fonts/DIN Next LT W23 Regular.ttf");

                noOffers_textView.setTypeface(dinNext);
                if (language.equals("en")) {

                    noOffers_textView.setText("No Offers Today");
                }
                else {
                    noOffers_textView.setText("لا توجد عروض اليوم");

                }
            }

            //if true

        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView image_playground_main_page;
        //    public TextView description_playground_main_page;
        public TextView playground_name_main_page;
        public TextView town_playground_main_page;
        public TextView town_icon_main_page;
        public Button btn_main_page;
        public TextView price_value_tv, price_tv, tv_date_end_value, to_tv, tv_date_start_value, tv_date, calender_ic, moneyIcon;
        public LinearLayout popUpLayout;


        public MyViewHolder(View v) {
            super(v);
            image_playground_main_page = (ImageView) v.findViewById(R.id.image_playground_main_page);
            // description_playground_main_page = (TextView) v.findViewById(R.id.description_playground_main_page);
            playground_name_main_page = (TextView) v.findViewById(R.id.playground_name_main_page);
            town_playground_main_page = (TextView) v.findViewById(R.id.town_playground_main_page);
            town_icon_main_page = (TextView) v.findViewById(R.id.town_icon_main_page);
            moneyIcon = (TextView) v.findViewById(R.id.moneyIcon);
            btn_main_page = (Button) v.findViewById(R.id.btn_main_page);
            btn_main_page.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //    Toast.makeText(getActivity(),playground_name_main_page.getText()+" added to favourites",Toast.LENGTH_SHORT).show();
                }
            });
            price_value_tv = (TextView) v.findViewById(R.id.price_value_tv);
            price_tv = (TextView) v.findViewById(R.id.price_tv);
            tv_date_end_value = (TextView) v.findViewById(R.id.tv_date_end_value);
            to_tv = (TextView) v.findViewById(R.id.to_tv);
            tv_date_start_value = (TextView) v.findViewById(R.id.tv_date_start_value);
            tv_date = (TextView) v.findViewById(R.id.tv_date);
            calender_ic = (TextView) v.findViewById(R.id.calender_ic);

        }
    }

    void getTimes(final String date, final String playgroundId)
    {
        final ACProgressFlower flower_dialog;
        flower_dialog = new ACProgressFlower.Builder(getActivity()).direction(ACProgressConstant.DIRECT_CLOCKWISE).themeColor(Color.WHITE).fadeColor(Color.GREEN).build();
        flower_dialog.show();
        RequestQueue queue = Volley.newRequestQueue(getActivity());
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
                                InfoDialog infoDialog = new InfoDialog(getActivity());
                                infoDialog.showDialogForPlayground(slotArray.toString(),playgroundId,date,true);
                            }
                            else {
                                // No times available
                                ExceptionHandling exceptionHandling = new ExceptionHandling(getActivity());
                                String dialogStringAr = "لا توجد اوقات متاحة في";
                                dialogStringAr += "\n"; dialogStringAr+= date;
                                exceptionHandling.showErrorDialog(dialogStringAr,"No times available in\n"+ date);
                             /*   if(language.equals("ar"))
                                    Toast.makeText(getActivity(), "لا توجد اوقات متاحة", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(getActivity(), "No times available", Toast.LENGTH_SHORT).show();*/
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
