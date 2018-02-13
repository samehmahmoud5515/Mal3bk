package com.logicdesigns.mohammed.mal3bklast;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.logicdesigns.mohammed.mal3bklast.ExceptionHandling.ExceptionHandling;
import com.logicdesigns.mohammed.mal3bklast.ImageSlider.ChildAnimationExample;
import com.logicdesigns.mohammed.mal3bklast.common.FragmentsTags;
import com.logicdesigns.mohammed.mal3bklast.header.HeaderFragment_Inner;
import com.logicdesigns.mohammed.mal3bklast.tasks.ReservationPlaygroundTask;
import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import BusinessClasses.JSONParser;
import BusinessClasses.MembersChildData;
import BusinessClasses.User;
import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.facebook.FacebookSdk.getApplicationContext;


public class ClubFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    Button btnearth_type_icon_fragment_club;
    MapView mMapView;
    private GoogleMap googleMap;
    JSONParser jsonParser;
    JSONArray jsonArray = null;
    Typeface DroidKufi, fontAwesome, DINNext;
    String[] properties = new String[7];
    String playgroundName="";
    ACProgressFlower flower_dialog;
    String selected_date, selected_from_time, selected_to_time;
    TextView button_change_date, button_change_time;
    ArrayList<String> imagesUrls;
    TextView calender_icon_fragment_club, time_icon_fragment_club, shower_icon_fragment_club, bath_icon_fragment_club, playground_icon_fragment_club, earth_type_icon_fragment_club;
    TextView shower_title_fragment_club, bath_title_fragment_club, playground_title_fragment_club, earth_type_title_fragment_club;
    private SliderLayout mDemoSlider;
    LinearLayout clubLayout;
    SharedPreferences sharedPref;
    String language = null;
    int enteredVolley=0;
    HashMap<String, String> url_maps = new HashMap<String, String>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        language = sharedPref.getString("language", "ar");
        MainPageFragment.cond_is_met = 0;
        MainPageFragment.noAvailableTime=1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_club, container, false);

        if (language.equals("en")) {
            DroidKufi = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Light.ttf");
            fontAwesome = Typeface.createFromAsset(getActivity().getAssets(), "fonts/fontawesome-webfont.ttf");
            DINNext = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Light.ttf");
        } else {
            DroidKufi = Typeface.createFromAsset(getActivity().getAssets(), "fonts/DroidKufi-Regular.ttf");
            fontAwesome = Typeface.createFromAsset(getActivity().getAssets(), "fonts/fontawesome-webfont.ttf");
            DINNext = Typeface.createFromAsset(getActivity().getAssets(), "fonts/DIN Next LT W23 Regular.ttf");
        }
        initializeItemsWithData();
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Gson gson = new Gson();
        String json1 = mPrefs.getString("userObject", "");
         user_obj = gson.fromJson(json1, User.class);
        return view;
    }
    User user_obj;
    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        try {
            mDemoSlider.stopAutoCycle();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        super.onStop();
    }
    int start_timeReceived_int,end_timeReceived_int;
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = (MapView) getView().findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately
        try {


            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    String receivedStartTime,receivedEndTime;


    @Override
    public void onStart() {
        super.onStart();
        setIDObjects();
        setFonts();

        selected_date = getArguments().getString("date");
        selected_from_time = getArguments().getString("start_time");
        selected_to_time = getArguments().getString("end_time");

        receivedStartTime = getArguments().getString("start_time");
        receivedEndTime =getArguments().getString("end_time");
        receivedStartTime = receivedStartTime.replace("AM","");
        receivedEndTime = receivedEndTime.replace("AM","");

        if (receivedStartTime.contains("PM")) {
            receivedStartTime = receivedStartTime.replace("PM", "");
            start_timeReceived_int = Integer.parseInt(receivedStartTime);
            start_timeReceived_int+=12;
            receivedStartTime = String.valueOf(start_timeReceived_int);
        }

        if (receivedEndTime.contains("PM")) {
            receivedEndTime = receivedEndTime.replace("PM", "");
            end_timeReceived_int = Integer.parseInt(receivedEndTime);
            end_timeReceived_int +=12;
            receivedEndTime = String.valueOf(end_timeReceived_int);
        }

        Log.d("DATE->>",getArguments().getString("date"));
        Log.d("from_time->>",receivedStartTime);
        Log.d("to_time->>",receivedEndTime);

        btnearth_type_icon_fragment_club.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReservationPlaygroundTask task = new ReservationPlaygroundTask(getActivity());
                task.reserveGround(getArguments().getString("playgroundId"),
                        String.valueOf(user_obj.getId()),selected_date,receivedStartTime,
                        receivedEndTime,String.valueOf(user_obj.getTeamId()));
            }
        });

        // button_change_date.setTypeface();button_change_time.setTypeface(DroidKufi);
        button_change_date.setText(selected_date);
        button_change_time.setText(selected_from_time + "-" + selected_to_time);
        button_change_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mDemoSlider = null;
        //mDemoSlider = (SliderLayout) getView().findViewById(R.id.slider);
        clubLayout = (LinearLayout) getView().findViewById(R.id.clubLayout);
        if (language.equals("en")) {
            clubLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            btnearth_type_icon_fragment_club.setText(R.string.Reservation);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        TextView  title_header_fragment = (TextView) getActivity().findViewById(R.id.title_header_fragment);
        title_header_fragment.setText(playgroundName);
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    public void setIDObjects() {

        button_change_time = (TextView) getView().findViewById(R.id.button_change_time);
        button_change_date = (TextView) getView().findViewById(R.id.button_change_date);

        calender_icon_fragment_club = (TextView) getView().findViewById(R.id.calender_icon_fragment_club);
        time_icon_fragment_club = (TextView) getView().findViewById(R.id.time_icon_fragment_club);
        shower_icon_fragment_club = (TextView) getView().findViewById(R.id.shower_icon_fragment_club);
        bath_icon_fragment_club = (TextView) getView().findViewById(R.id.bath_icon_fragment_club);
        playground_icon_fragment_club = (TextView) getView().findViewById(R.id.playground_icon_fragment_club);
        earth_type_icon_fragment_club = (TextView) getView().findViewById(R.id.earth_type_icon_fragment_club);

//    calender_data_fragment_club=(Spinner) getView().findViewById(R.id.calender_data_fragment_club);
//            time_data_fragment_club=(Spinner) getView().findViewById(R.id.time_data_fragment_club);
        shower_title_fragment_club = (TextView) getView().findViewById(R.id.shower_title_fragment_club);
        bath_title_fragment_club = (TextView) getView().findViewById(R.id.bath_title_fragment_club);
        playground_title_fragment_club = (TextView) getView().findViewById(R.id.playground_title_fragment_club);
        earth_type_title_fragment_club = (TextView) getView().findViewById(R.id.earth_type_title_fragment_club);
        btnearth_type_icon_fragment_club = (Button) getView().findViewById(R.id.btnearth_type_icon_fragment_club);


    }

    public void setFonts() {

        calender_icon_fragment_club.setTypeface(fontAwesome);
        time_icon_fragment_club.setTypeface(fontAwesome);
        shower_icon_fragment_club.setTypeface(fontAwesome);
        bath_icon_fragment_club.setTypeface(fontAwesome);
        playground_icon_fragment_club.setTypeface(fontAwesome);
        earth_type_icon_fragment_club.setTypeface(fontAwesome);
        btnearth_type_icon_fragment_club.setTypeface(fontAwesome);


        /////

        shower_title_fragment_club.setTypeface(DroidKufi);
        bath_title_fragment_club.setTypeface(DroidKufi);
        playground_title_fragment_club.setTypeface(DroidKufi);
        earth_type_title_fragment_club.setTypeface(DroidKufi);
        btnearth_type_icon_fragment_club.setTypeface(DINNext);


    }

    public void initializeItemsWithData() {
//    String[] times = new String[]{"1AM-3AM","5AM-8AM","10AM-12AM","1PM-12PM"};
////    ArrayAdapter<String> arrayAdapterForTime = new ArrayAdapter<String>(getActivity().getBaseContext(), R.layout.spinner_item, times);
////    arrayAdapterForTime.setDropDownViewResource(R.layout.spinner_item);
//  //  time_data_fragment_club.setAdapter(arrayAdapterForTime);
//    String[] dates = new String[]{"11-5-2017","12-6-2017"};
//  //  ArrayAdapter<String> arrayAdapterForDates = new ArrayAdapter<String>(getActivity().getBaseContext(), R.layout.spinner_item, dates);
//  //  arrayAdapterForDates.setDropDownViewResource(R.layout.spinner_item);
//   // calender_data_fragment_club.setAdapter(arrayAdapterForDates);
        flower_dialog = new ACProgressFlower.Builder(getActivity()).direction(ACProgressConstant.DIRECT_CLOCKWISE).themeColor(Color.WHITE).fadeColor(Color.GREEN).build();

        flower_dialog.show();
        jsonParser = new JSONParser();
       // new GetProperties().execute();
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url ="https://logic-host.com/work/kora/beta/phpFiles/getPlaygroundPropertiesAPI.php?key=logic123&playgroundId="+getArguments().getString("playgroundId");
        final ExceptionHandling exceptionHandling = new ExceptionHandling(getActivity());
          Log.d("clubURL",url);
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json = new JSONObject(response);
                            int success = json.getInt("success");

                            if (success == 1) {
                                enteredVolley=1;
                                jsonArray = json.getJSONArray("proprties");
                                JSONObject c = jsonArray.getJSONObject(0);
                                if (! c.isNull("dosh")&& c.has("dosh"))
                                   properties[0] = c.getString("dosh");
                                else properties[0] = "";
                                if (! c.isNull("panio")&& c.has("panio"))
                                   properties[1] = c.getString("panio");
                                else properties[1] = "";
                                if (language.equals("ar")) {
                                    if (!c.isNull("groundType") && c.has("groundType"))
                                        properties[2] = c.getString("groundType");
                                    else properties[2] = "";
                                }
                                else {
                                    if (!c.isNull("groundTypeEn") && c.has("groundTypeEn"))
                                        properties[2] = c.getString("groundTypeEn");
                                    else properties[2] = "";
                                }

                                    if (!c.isNull("playgroundType") && c.has("playgroundType"))
                                        properties[3] = c.getString("playgroundType");
                                    else properties[3] = "";

                                if (!c.isNull("name") && c.has("name")) {
                                    playgroundName = c.getString("name");
                                    TextView  title_header_fragment = (TextView) getActivity().findViewById(R.id.title_header_fragment);
                                    title_header_fragment.setText(playgroundName);
                                }
                                else playgroundName = "";

                                if (! c.isNull("lat")&& c.has("lat"))
                                   properties[4] = c.getString("lat");
                                else properties[4] = "";
                                if (! c.isNull("lng")&& c.has("lng"))
                                    properties[5] = c.getString("lng");
                                else properties[5] = "";
                                if (language.equals("ar")) {
                                    if (!c.isNull("cityName") && c.has("cityName"))
                                        properties[6] = c.getString("cityName");
                                    else properties[6] = "";
                                }
                                else {
                                    if (!c.isNull("cityNameEn") && c.has("cityNameEn"))
                                        properties[6] = c.getString("cityNameEn");
                                    else properties[6] = "";
                                }

                                imagesUrls = new ArrayList<>();
                                url_maps = new HashMap<>();
                                mDemoSlider = (SliderLayout) getView().findViewById(R.id.slider);

                                JSONArray imagesArray = json.getJSONArray("images");
                                for (int i = 0; i < imagesArray.length(); i++) {
                                    JSONObject imageJsonObj = imagesArray.getJSONObject(i);
                                    String url = imageJsonObj.getString("URL");
                                    imagesUrls.add(url);
                                }


                                if (properties[0].equals("1")) {
                                    if (language.equals("en")) {
                                        shower_title_fragment_club.setText(R.string.available);
                                    } else if (language.equals("ar")) {
                                        shower_title_fragment_club.setText("متوفر");
                                    }
                                }
                                else {
                                    if (language.equals("en")) {
                                        shower_title_fragment_club.setText("No");
                                    } else if (language.equals("ar")) {
                                        shower_title_fragment_club.setText("غير متوفر");
                                    }
                                }

                                if (properties[1].equals("1")) {
                                    if (language.equals("en")) {
                                        bath_title_fragment_club.setText(R.string.available);
                                    } else if (language.equals("en")) {
                                        bath_title_fragment_club.setText("متوفر");
                                    }

                                }
                                else {
                                    if (language.equals("en")) {
                                        bath_title_fragment_club.setText("No");
                                    } else if (language.equals("ar")) {
                                        bath_title_fragment_club.setText("غير متوفر");
                                    }
                                }
                                playground_title_fragment_club.setText(properties[3] + "*" + properties[3]);
                                earth_type_title_fragment_club.setText(properties[2]);


                                mMapView.getMapAsync(new OnMapReadyCallback() {
                                    @Override
                                    public void onMapReady(GoogleMap mMap) {
                                        googleMap = mMap;

                                        // For showing a move to my location button
                                        // googleMap.setMyLocationEnabled(true);

                                        // For dropping a marker at a point on the Map
                                        LatLng town = new LatLng(Float.parseFloat(properties[4]), Float.parseFloat(properties[5]));
                                        googleMap.addMarker(new MarkerOptions().position(town).title(properties[6])).showInfoWindow();

                                        // For zooming automatically to the location of the marker
                                        CameraPosition cameraPosition = new CameraPosition.Builder().target(town).zoom(12).build();
                                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                                        Circle circle = googleMap.addCircle(new CircleOptions()
                                                .center(town)
                                                .radius(1000)
                                                .strokeWidth(2)
                                                .strokeColor(Color.BLUE)
                                                .fillColor(Color.parseColor("#500084d3")));
                                    }
                                });
                                if (imagesUrls!=null)
                                    for (int i = 0; i < imagesUrls.size(); i++) {
                                        url_maps.put("" , imagesUrls.get(i));
                                        //   url_maps.put("new","https://logic-host.com/work/alkan5/beta/panel/categories/upload/1957891758sss1.jpg");
                                        // url_maps.put("o", "https://logic-host.com/work/kora/beta/images/play2.jpg");
                                        // url_maps.put("s", "https://logic-host.com/work/kora/images/sliderbg.jpg");
                                        // url_maps.put("g", "https://logic-host.com/work/kora/beta/images/testphone2.jpg");
                                    }
//        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
//        file_maps.put("Hannibal",R.drawable.hannibal);
//        file_maps.put("Big Bang Theory",R.drawable.bigbang);
//        file_maps.put("House of Cards",R.drawable.house);
                                //file_maps.put("Game of Thrones", R.drawable.game_of_thrones);

                                for (String name : url_maps.keySet()) {
                                    TextSliderView textSliderView = new TextSliderView(getActivity());
                                    // initialize a SliderLayout
                                    textSliderView
                                            .description(name)
                                            .image(url_maps.get(name))
                                            //  .setScaleType(BaseSliderView.ScaleType.Fit)
                                            .setOnSliderClickListener(ClubFragment.this);
                                    // String s = url_maps.get(name);


                                    //add your extra information
                                    textSliderView.bundle(new Bundle());
                                    textSliderView.getBundle()
                                            .putString("extra", name);
                                    textSliderView.getBundle()
                                            .putString("url", url_maps.get(name));


                                    mDemoSlider.addSlider(textSliderView);
                                }
                                mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
                                mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                                mDemoSlider.setCustomAnimation(new DescriptionAnimation());
                                mDemoSlider.setDuration(4000);
                                mDemoSlider.addOnPageChangeListener(ClubFragment.this);
                                flower_dialog.dismiss();

                            } else {



                                flower_dialog.dismiss();
                                exceptionHandling.showErrorDialog();
                               // showErrorDialog();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            flower_dialog.dismiss();
                            exceptionHandling.showErrorDialog();

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        //Toast.makeText(getActivity(), slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
        Typeface custom_Din = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Light.ttf");
        final Dialog UploadImagedialog = new Dialog(getActivity(), R.style.CustomDialogTheme);
        UploadImagedialog.setContentView(R.layout.dialog_upload_image);

        UploadImagedialog.setCancelable(true);

        UploadImagedialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Button close_btn = (Button) UploadImagedialog.findViewById(R.id.close_btn);
        Button ok_btn = (Button) UploadImagedialog.findViewById(R.id.ok_btn);
        close_btn.setTypeface(custom_Din);
        ok_btn.setTypeface(custom_Din);
        if (language.equals("en")) {
            close_btn.setText(R.string.close);
        }
        close_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                UploadImagedialog.cancel();

            }
        });

        ok_btn.setVisibility(View.GONE);
        // now that the dialog is set up, it's time to show it
        ImageView imageView = (ImageView) UploadImagedialog.findViewById(R.id.profilePic_imageView);
        Picasso.with(getActivity()).load(slider.getBundle().get("url").toString())
                .into(imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                        Button ok_btn = (Button) UploadImagedialog.findViewById(R.id.ok_btn);
                        if (language.equals("en")) {
                            ok_btn.setText(R.string.agree1);
                        } else {
                            ok_btn.setText("موافق");
                        }
                        ok_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getActivity(), "uploading", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onError() {

                    }
                });

        UploadImagedialog.show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.slider_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_custom_indicator:
                mDemoSlider.setCustomIndicator((PagerIndicator) getActivity().findViewById(R.id.custom_indicator));
                break;
            case R.id.action_custom_child_animation:
                mDemoSlider.setCustomAnimation(new ChildAnimationExample());
                break;
            case R.id.action_restore_default:
                mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                mDemoSlider.setCustomAnimation(new DescriptionAnimation());
                break;
            case R.id.action_github:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/daimajia/AndroidImageSlider"));
                startActivity(browserIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDestroyView() {


        super.onDestroyView();
        if (MainContainer.pageNumber == 1.3)
        {
            MainContainer.headerNum=1;
            FragmentTransaction ft1 = getActivity().getSupportFragmentManager().beginTransaction();
            ft1.replace(R.id.placeholder1, new HeaderFragment())
                    .commit();
            super.onDestroyView();
        }
        else if (MainContainer.pageNumber == 2.3) {
            MainContainer.headerNum=2;
            FragmentTransaction ft1 = getActivity().getSupportFragmentManager().beginTransaction();
            ft1.replace(R.id.placeholder1, new HeaderFragment_Inner())
                    .commit();
            super.onDestroyView();
        }

    }



}

