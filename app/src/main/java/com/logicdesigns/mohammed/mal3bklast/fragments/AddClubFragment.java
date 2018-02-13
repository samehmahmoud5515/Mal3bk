package com.logicdesigns.mohammed.mal3bklast.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.logicdesigns.mohammed.mal3bklast.ExceptionHandling.ExceptionHandling;
import com.logicdesigns.mohammed.mal3bklast.HeaderFragment;
import com.logicdesigns.mohammed.mal3bklast.JSON_PARSER.Cities_Parser;
import com.logicdesigns.mohammed.mal3bklast.JSON_PARSER.PlaygroundFacilities_Parser;
import com.logicdesigns.mohammed.mal3bklast.JSON_PARSER.PlaygroundTypeParser;
import com.logicdesigns.mohammed.mal3bklast.MainContainer;
import com.logicdesigns.mohammed.mal3bklast.Models.City;
import com.logicdesigns.mohammed.mal3bklast.Models.PlayGroundType;
import com.logicdesigns.mohammed.mal3bklast.Models.PlaygroundFacilities;
import com.logicdesigns.mohammed.mal3bklast.R;
import com.logicdesigns.mohammed.mal3bklast.SharedPref.PrefManager;
import com.logicdesigns.mohammed.mal3bklast.appFonts.AppFonts;
import com.logicdesigns.mohammed.mal3bklast.common.URLs;
import com.logicdesigns.mohammed.mal3bklast.header.HeaderFragment_Inner;
import com.logicdesigns.mohammed.mal3bklast.tasks.UploadPlaygroundImage;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

import BusinessClasses.ServerWork;
import BusinessClasses.User;
import butterknife.BindView;
import butterknife.ButterKnife;
import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import fr.ganfra.materialspinner.MaterialSpinner;
import okhttp3.Call;

import static com.facebook.FacebookSdk.getApplicationContext;


public class AddClubFragment extends Fragment implements OnMapReadyCallback {

    public AddClubFragment() {
        // Required empty public constructor
    }
    private static final String TAG ="AddClubFragment" ;
    String language="";
    ArrayList<City> cityArrayList;
    ArrayList<PlayGroundType> PlayGroundTypeList;
    ArrayList<PlaygroundFacilities> playgroundFacilities;
    @BindView(R.id.scrollViewClub)
    NestedScrollView scrollView;
    @BindView(R.id.playName_et)
    EditText playNameEditText;
    @BindView(R.id.price_et)
    EditText price_et;
    @BindView(R.id.address_et)
    EditText address_et;
    @BindView(R.id.selectType)
    Button selectType;
    @BindView(R.id.selectDoshPanio)
    Button selectDoshPanio;
    @BindView(R.id.selectground)
    Button selectground;
    @BindView(R.id.select_city)
    Button select_city;
    @BindView(R.id.addPlayground_btn)
    Button addPlayground_btn;
    @BindView(R.id.back_btn)
    Button back_btn;
    @BindView(R.id.details_et)
    EditText details_et;
    String _5admat[] = {"الكل", "حمام", "دش"};
    String _5admatEn[] = {"All", "Bathroom", "Shower"};
    Dialog UploadImagedialog;
    private static int RESULT_LOAD_IMAGE = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_add_club, container, false);

        ButterKnife.bind(this, rootView);
        MainContainer.headerNum = 7;
        FragmentTransaction ft1 = getActivity().getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.placeholder1, new HeaderFragment_Inner())
                .commit();
        PrefManager prefManager = new PrefManager(getActivity());
        language = prefManager.getLanguage();
        setButtonsDate();
        handleEnglish();

        return rootView;
    }

    void handleEnglish()
    {
        if(language.equals("en"))
        {
            playNameEditText.setHint("Playground name");
            address_et.setHint("Playground address");
            price_et.setHint("Playground price");
            details_et.setHint("Playground details");
            selectDoshPanio.setText("Choose playground services");
            selectground.setText("Choose playground floor");
            selectType.setText("Choose playground type");
            select_city.setText("Choose playground city");
            addPlayground_btn.setText("Submit playground");
            back_btn.setText("Cancel");
        }
    }

    GoogleMap googleMap;
    Marker marker;
    LatLng selectedLatLng;
    @Override
    public void onMapReady( GoogleMap googleMap) {
        this. googleMap = googleMap;
        this. googleMap.getUiSettings().setZoomControlsEnabled(true);

        this.googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                selectedLatLng = point;
                if (marker!= null)
                    marker.remove();
                marker =  AddClubFragment.this.googleMap.addMarker(new MarkerOptions().position(point));
            }
        });

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        WorkaroundMapFragment mapFragment = (WorkaroundMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ((WorkaroundMapFragment) getChildFragmentManager().findFragmentById(R.id.map))
                .setListener(new WorkaroundMapFragment.OnTouchListener() {
                    @Override
                    public void onTouch() {
                        scrollView.requestDisallowInterceptTouchEvent(true);
                    }
                });
        SupportPlaceAutocompleteFragment autocompleteFragment = (SupportPlaceAutocompleteFragment )
                getChildFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

                Log.i(TAG, "PlaceA: " + place.getName());


                if(googleMap!=null) {
                    if (marker!=null)
                        marker.remove();
                    LatLng sydney = new LatLng(place.getLatLng().latitude, place.getLatLng().longitude);
                    marker=  googleMap.addMarker(new MarkerOptions().position(sydney)
                            .title(place.getName().toString()));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

                }
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });
        flower_dialog = new ACProgressFlower.Builder(getActivity())
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.GREEN).build();
    }
    void setButtonsDate()
    {
        addPlayground_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareDataForAPI();
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        selectDoshPanio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (language.equals("en")) {
                    String services = getResources().getString(R.string.services);
                    drawDialog(services, _5admatEn, 4);
                } else {
                    drawDialog("الخدمات", _5admat, 4);
                }
            }
        });
        select_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //el mawka3
                PrefManager prefManager = new PrefManager(getActivity());
                String cities = prefManager.getCITIES();
                Cities_Parser parser = new Cities_Parser(getActivity());
                cityArrayList = new ArrayList<City>();
                cityArrayList = parser.parse(cities);

                String[] city_arr = new String[cityArrayList.size() ];
                for (int i = 0; i < cityArrayList.size(); i++) {
                    city_arr[i] = cityArrayList.get(i).getCityName();
                }
                if (language.equals("en")) {
                  //  city_arr[0] = "All";
                    drawDialog("City", city_arr, 1);
                } else {
                   // city_arr[0] = "الكل";
                    drawDialog("الموقع", city_arr, 1);
                }

            }
        });
        selectType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrefManager prefManager = new PrefManager(getActivity());
                String types = prefManager.getPlayground_Types();
                PlaygroundTypeParser parser = new PlaygroundTypeParser();
                PlayGroundTypeList = parser.parse(types);

                String[] types_arr = new String[PlayGroundTypeList.size()];
                for (int i = 0; i < PlayGroundTypeList.size(); i++) {
                    types_arr[i] = PlayGroundTypeList.get(i).getPlayground_Type_Number() + " * "
                            + PlayGroundTypeList.get(i).getPlayground_Type_Number();
                }
                if (language.equals("en")) {
                    //types_arr[0] = "All";

                    drawDialog("Playground Type", types_arr, 2);
                } else {
                   // types_arr[0] = "الكل";

                    drawDialog("نوع الملعب", types_arr, 2);
                }
            }
        });
        selectground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String s []= {"نجيلة طبيعية","نجيلة صناعية","اسفلت"};
                PrefManager prefManager = new PrefManager(getActivity());
                String facilities_json_string = prefManager.getPlayground_Facilities();
                PlaygroundFacilities_Parser parser = new PlaygroundFacilities_Parser();
                playgroundFacilities = parser.parse(facilities_json_string,getActivity());
                String[] namesArr = new String[playgroundFacilities.size()];
                for (int i = 0; i < playgroundFacilities.size(); i++) {
                    namesArr[i] = playgroundFacilities.get(i).getPlayground_Facility_String();
                }
                if (language.equals("en")) {
                    String type = getResources().getString(R.string.playgroundtype);
                 //   namesArr[0] = "All";
                    drawDialog(type, namesArr, 3);
                } else {
                   // namesArr[0] = "الكل";
                    drawDialog("نوع الملعب", namesArr, 3);
                }
            }
        });
    }

    void drawDialog(String Tiltle, final String s[], final int button_num) {
        @SuppressLint("RestrictedApi") AlertDialog.Builder dialog = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.CustomDialogTheme));
        TextView title = new TextView(getActivity());
        title.setText(Tiltle);
        title.setBackgroundColor(Color.parseColor("#008542"));
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(20);
        title.setTypeface(AppFonts.getInstance(getActivity()).getDIN_NEXT());
        dialog.setCustomTitle(title);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.choice_item, R.id.text_view_choice, s) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(R.id.text_view_choice);
              //  text.setTypeface(font);
                return view;
            }
        };
        dialog.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (button_num == 1)
                    select_city.setText(s[which]);
                if (button_num == 2)
                    selectType.setText(s[which]);
                if (button_num == 3)
                    selectground.setText(s[which]);
                if (button_num == 4)
                    selectDoshPanio.setText(s[which]);
            }
        });
        AlertDialog alert = dialog.create();
        alert.show();
    }
    String  playgroundName,price,details,address,allText,cityId,chooseText,
            Playground_String_Number,typeId,ground, dosh,panio;
    int choos_5aedmat_btnInt;
    void prepareDataForAPI()
    {
        if (language.equals("ar")) {
            allText = "الكل";
            chooseText = "اختار";
        }
        else {
            allText = "All";
            chooseText = "Choose";
        }
        if (playNameEditText.getText().length()==0) {
            if (language.equals("ar"))
            playNameEditText.setError("ادخل اسم الملعب");
            else playNameEditText.setError("Enter Name");
            return;
        }
        if (price_et.getText().length()==0) {
            if (language.equals("ar"))
                price_et.setError("ادخل سعر الملعب");
            else price_et.setError("Enter price");
            return;
        }
        if (address_et.getText().length()==0) {
            if (language.equals("ar"))
                address_et.setError("ادخل عنوان الملعب");
            else address_et.setError("Enter address");
            return;
        }
        if (select_city.getText().toString().equals(chooseText))
        {
            if (language.equals("ar"))
                Toast.makeText(getActivity(), "اختار المدينة", Toast.LENGTH_SHORT).show();
            else  Toast.makeText(getActivity(), "Select City", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectType.getText().toString().equals(chooseText))
        {
            if (language.equals("ar"))
                Toast.makeText(getActivity(), "اختار نوع الملعب", Toast.LENGTH_SHORT).show();
            else  Toast.makeText(getActivity(), "Select Playground Type", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectground.getText().toString().equals(chooseText))
        {
            if (language.equals("ar"))
                Toast.makeText(getActivity(), "اختار ارضية الملعب", Toast.LENGTH_SHORT).show();
            else  Toast.makeText(getActivity(), "Select ground", Toast.LENGTH_SHORT).show();
            return;
        }
        if (marker== null)
        {
            if (language.equals("ar"))
                Toast.makeText(getActivity(), "اختار المكان من علي الخريطة", Toast.LENGTH_SHORT).show();
            else  Toast.makeText(getActivity(), "Select place from map", Toast.LENGTH_SHORT).show();
            return;
        }
        if (selectDoshPanio.equals(allText))
        {
            if (language.equals("ar"))
                Toast.makeText(getActivity(), "اختار خدمات الملعب", Toast.LENGTH_SHORT).show();
            else  Toast.makeText(getActivity(), "Select services", Toast.LENGTH_SHORT).show();
            return;
        }
        playgroundName = playNameEditText.getText().toString();
        price= price_et.getText().toString();
        if (details_et.getText().length()>0)
          details= details_et.getText().toString();
        else details = "";
        address= address_et.getText().toString();
        playgroundName.replace(" ","%20");
        price.replace(" ","%20");
        details.replace(" ","%20");
        address.replace(" ","%20");

        City city = new City();
        if (!select_city.getText().toString().equals(allText))
            cityId = city.getCityIdbyName(cityArrayList,
                    select_city.getText().toString());
        else cityId = "-1";


        String CurrentString = selectType.getText().toString();

        StringTokenizer tokens = new StringTokenizer(CurrentString, " *");
        Playground_String_Number = tokens.nextToken();

        PlayGroundType playGroundType = new PlayGroundType();
        if (CurrentString.equals(allText))
            typeId="-1";
        else
            typeId = playGroundType.getPlaygroundIdbyNumber(PlayGroundTypeList
                    , Playground_String_Number);

         choos_5aedmat_btnInt = Arrays.asList(_5admat).indexOf(selectDoshPanio.getText().toString());
        if (choos_5aedmat_btnInt==0)
            choos_5aedmat_btnInt = -1;
        else choos_5aedmat_btnInt =Arrays.asList(_5admat).indexOf(choos_5aedmat_btnInt);
        PlaygroundFacilities facilities = new PlaygroundFacilities();

        if (selectground.getText().toString().equals(allText))
            ground ="-1";
        else
            ground = facilities.getPlaygroundIdbyString(playgroundFacilities, selectground.getText().toString());

        if (choos_5aedmat_btnInt == 2)
            dosh = "1";
        if (choos_5aedmat_btnInt == 1 )
              panio = "1";
        if (choos_5aedmat_btnInt == -1 )
        {
            dosh = "1";
            panio = "1";
        }

        addPlayground();
    }

    void addPlayground()
    {
        final ACProgressFlower flower_dialog;
        flower_dialog = new ACProgressFlower.Builder(getActivity()).direction(ACProgressConstant.DIRECT_CLOCKWISE).themeColor(Color.WHITE).fadeColor(Color.GREEN).build();
        flower_dialog.show();
        final ExceptionHandling exceptionHandling = new ExceptionHandling(getActivity());

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        /*
        https://logic-host.com/work/kora/beta/phpFiles/setPlaygroundAPI.php?key=logic123
        &playgroundname=Momo&cityID=2&typeID=1
        &lat=33.4556565&lng=35.54848548&address=momommmomo&description=dfndnjfdnjdfnjdf&price=500
      &dosh=1&panio=1   */
        String url = URLs.ADDPLAYGROUND + "&playgroundname=" +playgroundName+
                "&cityID="+ cityId + "&typeID="+typeId + "&address="+
                address+"&description=" +details + "&price=" +price + "&lat="+
                marker.getPosition().latitude
                +"&lng="+ marker.getPosition().longitude+ "&dosh="+dosh+
                "&panio=" +panio + "&floor=" +ground;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            flower_dialog.dismiss();
                            Log.d(TAG,response);
                            if (jsonObject.getString("success").equals("1"))
                            {
                               // UploadPlaygroundImage uploadPlaygroundImag= new UploadPlaygroundImage(getActivity());
                              //  uploadPlaygroundImag .showDialog("تمت الاضافة بنجاح","playground Added successfully");
                                   showDialog("تمت الاضافة بنجاح","playground Added successfully");
                                JSONArray jsonArray = jsonObject.getJSONArray("playground");
                                JSONObject obj = jsonArray.getJSONObject(0);
                                playgroundID = obj.getString("id");
                            }
                            else {
                                exceptionHandling .showErrorDialog("لم تتم اضافة الملعب حاول مرة اخري","playground not added, try again");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                flower_dialog.dismiss();
                exceptionHandling .showErrorDialog("لم تتم اضافة الملعب حاول مرة اخري","playground not added, try again");

            }
        });
        queue.add(stringRequest);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openFilePicker();
        }
    }
    String playgroundID= "";
    private static final int REQUEST_WRITE_PERMISSION = 786;


    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {
            openFilePicker();
        }
    }


    public void showDialog(String arString,String enString)
    {
        Typeface DINNext;
        DINNext = Typeface.createFromAsset(getActivity().getAssets(), "fonts/DIN Next LT W23 Regular.ttf");

        final Dialog dialog = new Dialog(getActivity(), R.style.CustomDialogTheme);
        dialog.setContentView(R.layout.upload_image_playground_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView no_message_text = (TextView) dialog.findViewById(R.id.message_text);
        no_message_text.setTypeface(DINNext);

        Button close_button = (Button) dialog.findViewById(R.id.btn_close);
        close_button.setTypeface(DINNext);

        if (language.equals("en")) {
            close_button.setText(R.string.close);
            no_message_text.setText(enString);

        }
        else{
            close_button.setText(R.string.close_ar);
            no_message_text.setText(arString);
        }
        close_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        Button btn_addImage = (Button) dialog.findViewById(R.id.btn_addImage);
        btn_addImage.setTypeface(DINNext);
        if (language.equals("en")) {
            btn_addImage.setText("Add Image");
        }
        btn_addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
            }
        });

        dialog.show();
    }

    public void openFilePicker()
    {
        Typeface custom_Din = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Light.ttf");
        UploadImagedialog = new Dialog(getActivity(), R.style.CustomDialogTheme);
        UploadImagedialog.setContentView(R.layout.dialog_upload_image);
        UploadImagedialog.setCancelable(true);
        UploadImagedialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Button close_btn = (Button) UploadImagedialog.findViewById(R.id.close_btn);
        Button ok_btn = (Button) UploadImagedialog.findViewById(R.id.ok_btn);
        if (language.equals("en")) {
            ok_btn.setText(R.string.Gallery);
            close_btn.setText(R.string.close);
        }

        close_btn.setTypeface(custom_Din);
        ok_btn.setTypeface(custom_Din);
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImagedialog.cancel();
            }
        });
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });
        UploadImagedialog.show();
    }
    private Uri filePath;
    private Bitmap bitmap;
    String imagename = null;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RESULT_LOAD_IMAGE && resultCode == getActivity().RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            //  String[] filePathColumn = { MediaStore.Images.Media.DATA };

            filePath = data.getData();

            if (!filePath.getScheme().equals("content")) {
                imagename = filePath.toString().substring(filePath.toString().lastIndexOf("/") + 1);
                Log.d("ImageName", imagename);
            } else {
                try (Cursor cursor = getActivity().getContentResolver().query(filePath, null, null, null, null)) {
                    if (cursor != null && cursor.moveToFirst()) {
                        imagename = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        Log.d("ImageName", imagename);
                    }

                }

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);

                } catch (IOException e) {
                    e.printStackTrace();
                }


                ImageView imageView = (ImageView) UploadImagedialog.findViewById(R.id.profilePic_imageView);
                Picasso.with(getActivity()).load(selectedImage)
                        .into(imageView, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                Button ok_btn = (Button) UploadImagedialog.findViewById(R.id.ok_btn);
                                if (language.equals("en")) {
                                    ok_btn.setText("Agree");
                                } else {
                                    ok_btn.setText("موافق");
                                }

                                ok_btn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //Toast.makeText(getActivity(),"uploading",Toast.LENGTH_SHORT).show();


                                        try {
                                            String uploadImage = getStringImage(bitmap);
                                            SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                            String json1 = mPrefs.getString("userObject", "");
                                            Gson gson = new Gson();
                                            User obj = gson.fromJson(json1, User.class);
                                            if (imagename != null)
                                                makePostRequest(uploadImage, obj.getId(), imagename);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });
                            }

                            @Override
                            public void onError() {

                            }
                        });

            }


        }
    }
    Dialog dialog1 = null;
    TextView textView;
    ACProgressFlower flower_dialog;
    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public void makePostRequest(String image, final int id, String ImageName) throws IOException {
        if (flower_dialog != null && flower_dialog.isShowing()) {
            flower_dialog.dismiss();
        }

        try {
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    flower_dialog = new ACProgressFlower.Builder(getActivity())
                            .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                            .themeColor(Color.WHITE)
                            .fadeColor(Color.GREEN).build();
                    flower_dialog.show();

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        ServerWork serverWork = new ServerWork();
        serverWork.post(URLs.Upload_Playground_Image, image, Integer.parseInt(playgroundID), ImageName, new okhttp3.Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        // RefreshUserInforamtion refreshUserInforamtion = new RefreshUserInforamtion(getActivity());


                        dialog1 = new Dialog(getActivity(), R.style.CustomDialogTheme);
                        dialog1.setContentView(R.layout.no_playgrounds_custom_dialog);

                        dialog1.setCancelable(true);

                        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                        textView = (TextView) dialog1.findViewById(R.id.no_message_text);

                        Button close_button = (Button) dialog1.findViewById(R.id.btn_close_no_playground);
                        close_button.setTypeface(AppFonts.getInstance(getActivity()).getDIN_NEXT());
                        textView.setTypeface(AppFonts.getInstance(getActivity()).getDIN_NEXT());
                        close_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog1.dismiss();
                            }
                        });
                        if (language.equals("en")) {
                            textView.setText("Photo Uploaded Successfully");
                            close_button.setText(R.string.close);
                        } else {
                            textView.setText("تم رفع الصورة بنجاح");

                        }

                        dialog1.show();
                    }
                });
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseStr = response.body().string();
                    // Do what you want to do with the response.
                    Log.d("imageAPI", responseStr);
                    UploadImagedialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(responseStr);


                        try {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dialog1 = new Dialog(getActivity(), R.style.CustomDialogTheme);
                                    dialog1.setContentView(R.layout.no_playgrounds_custom_dialog);

                                    dialog1.setCancelable(true);

                                    dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                    textView = (TextView) dialog1.findViewById(R.id.no_message_text);

                                    Button close_button = (Button) dialog1.findViewById(R.id.btn_close_no_playground);
                                    close_button.setTypeface(AppFonts.getInstance(getActivity()).getDIN_NEXT());
                                    textView.setTypeface(AppFonts.getInstance(getActivity()).getDIN_NEXT());
                                    close_button.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog1.dismiss();
                                        }
                                    });
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            Handler h = new Handler(Looper.getMainLooper());
                            h.post(new Runnable() {
                                public void run() {
                                    if (language.equals("en")) {
                                        Toast.makeText(getApplicationContext(), "Uploading Photo ...", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "جاري رفع الصورة ...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }


                        if (Integer.parseInt(jsonObject.getString("success")) == 1) {
                            try {
                                getActivity().runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        flower_dialog.dismiss();
                                        if (language.equals("en")) {
                                            textView.setText("Photo Uploaded Successfully");
                                        } else {
                                            textView.setText("تم رفع الصورة بنجاح");
                                        }
                                        dialog1.show();
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();

                                Handler h = new Handler(Looper.getMainLooper());
                                h.post(new Runnable() {
                                    public void run() {
                                        if (language.equals("en")) {
                                            Toast.makeText(getApplicationContext(), "Photo Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "تم رفع الصورة بنجاح", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                            }


                        } else {


                            Handler h = new Handler(Looper.getMainLooper());
                            h.post(new Runnable() {
                                public void run() {
                                    flower_dialog.dismiss();
                                    if (language.equals("en")) {
                                        textView.setText("An Error Occurred");
                                    } else {
                                        textView.setText("حدث خطأ");
                                    }
                                    dialog1.show();
                                }
                            });
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            flower_dialog.dismiss();
                            if (language.equals("en")) {
                                textView.setText("An Error Occurred");
                            } else {
                                textView.setText("حدث خطأ");
                            }
                            dialog1.show();
                        }
                    });
                    // Request not successful
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        MainContainer.headerNum = 0;
        super.onDestroyView();
    }
}
