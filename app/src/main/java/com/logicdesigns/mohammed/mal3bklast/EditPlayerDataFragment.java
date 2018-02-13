package com.logicdesigns.mohammed.mal3bklast;

import android.*;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.plus.PlusOneButton;
import com.google.gson.Gson;
import com.logicdesigns.mohammed.mal3bklast.ExceptionHandling.ExceptionHandling;
import com.logicdesigns.mohammed.mal3bklast.Interface.AsyncResponse;
import com.logicdesigns.mohammed.mal3bklast.JSON_PARSER.Cities_Parser;
import com.logicdesigns.mohammed.mal3bklast.Models.City;
import com.logicdesigns.mohammed.mal3bklast.Models.PlayerPositions;
import com.logicdesigns.mohammed.mal3bklast.SharedPref.PrefManager;
import com.logicdesigns.mohammed.mal3bklast.common.URLs;
import com.logicdesigns.mohammed.mal3bklast.player.PlayerStoredData;
import com.logicdesigns.mohammed.mal3bklast.tasks.RefreshUserInforamtion;
import com.logicdesigns.mohammed.mal3bklast.tasks.loginTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import BusinessClasses.ServerWork;
import BusinessClasses.User;
import BusinessClasses.ValidatorInputs;
import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

import static com.facebook.FacebookSdk.getApplicationContext;

public class EditPlayerDataFragment extends Fragment implements AsyncResponse, TimePickerDialog.OnTimeSetListener, com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener, DialogInterface.OnDismissListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static int RESULT_LOAD_IMAGE = 1;
    TextView textView;
    String imagename = null;
    private Uri filePath;
    private Bitmap bitmap;
    Dialog dialog1 = null;
    ACProgressFlower flower_dialog1;
    String selected_date, yes_no_RorL;
    TextView username_textview, title_header_fragment, email_textview, mobile_textview, password_textview, address_textview, city_textview, rorl_textview, proImage_textview;
    RadioButton radioButton;
    TextView ic_name_tv, ic_email_textview, ic_mobile_textview, ic_password_textview, ic_address_tv, ic_city, ic_rol_tv, ic_proImage_tv, birthdate_ic, birthdate_tv, ic_rol_tv__, rorl_textview__, ic_change_lang, change_lang_tv;
    Typeface customFontAwesome, custom_DIN_Next;
    ArrayList<City> cityArrayList = new ArrayList<>();
    ArrayList<PlayerPositions> playerPositionsArrayList = new ArrayList<>();

    ACProgressFlower flower_dialog;
    SharedPreferences sharedPref;
    String language = null;
    private String mParam1;
    private String mParam2;
    String[] params;
    User user;
    Dialog UploadImagedialog;
    EditText nameEditText, MailEditText, MobileEditTExt, PasswordEditTExt, address_editText;
    Button btn_updateData, select_city_button, select_playerPostion_button,
            select_profile_image_button, select_birthDate_btn, select_RorL_button,
            select_lang_btn,cancel_btn;

    private OnFragmentInteractionListener mListener;
    private static final int REQUEST_WRITE_PERMISSION = 786;


    public EditPlayerDataFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public static EditPlayerDataFragment newInstance(String param1, String param2) {
        EditPlayerDataFragment fragment = new EditPlayerDataFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        try {
            PrefManager prefManager = new PrefManager(getActivity());
            String postions = prefManager.getPlayers_Postions();
            PlayerPositions playerPositions = new PlayerPositions();
            playerPositionsArrayList = playerPositions.parsePlayerPostions(postions,getActivity());


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
   int lang;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        customFontAwesome = Typeface.createFromAsset(getActivity().getAssets(), "fonts/fontawesome-webfont.ttf");
        custom_DIN_Next = Typeface.createFromAsset(getActivity().getAssets(), "fonts/DIN Next LT W23 Regular.ttf");
        title_header_fragment = (TextView) getActivity().findViewById(R.id.title_header_fragment);

        if (language.equals("en")) {
            lang = 1;
            view = inflater.inflate(R.layout.fragment_edit_player_data_en, container, false);
            title_header_fragment.setText(R.string.Modify);
            select_lang_btn = (Button) view.findViewById(R.id.select_lang_btn);

            select_lang_btn.setText("English");
        } else  {
            lang = 0;
            view = inflater.inflate(R.layout.fragment_edit_player_data, container, false);
            title_header_fragment.setText("تعديل البيانات");
            select_lang_btn = (Button) view.findViewById(R.id.select_lang_btn);

            select_lang_btn.setText("العربية");
        }



        // LinearLayout ll = (LinearLayout)getActivity().findViewById(R.id.profile_photo);
        // ll.setVisibility(View.GONE);
        ImageButton logoutHeader1 = (ImageButton) getActivity().findViewById(R.id.logoutHeader1);
        logoutHeader1.setVisibility(View.VISIBLE);
        user = new User();
        setIds_and_Fonts(view);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String json1 = mPrefs.getString("userObject", "");
        Gson gson = new Gson();
        final User USER_obj = gson.fromJson(json1, User.class);

        nameEditText.setText(USER_obj.getName());
        MailEditText.setText(USER_obj.getEmail());
        MobileEditTExt.setText(USER_obj.getMobile());
        PasswordEditTExt.setText(USER_obj.getPassword());
        address_editText.setText(USER_obj.getAddress());
        PlayerStoredData playerStoredData = new PlayerStoredData(getActivity());
        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        language = sharedPref.getString("language", "ar");
        String leg ="0";
        if (language.equals("ar")) {
            if (playerStoredData.getRorl().equals("no"))
                leg = "يسار";
            else if (playerStoredData.getRorl().equals("yes"))
                leg = "يمين";
        }
        else {
            if (playerStoredData.getRorl().equals("no"))
                leg = "left";
            else if (playerStoredData.getRorl().equals("yes"))
                leg = "right";
        }
        if (!leg.equals("0"))
            select_RorL_button.setText(leg);
        String bd = playerStoredData.getBirthdate();
        if (!bd.equals("0"))
         select_birthDate_btn.setText(bd);
        select_playerPostion_button = (Button) view.findViewById(R.id.select_playerPostion_button);
      if (!playerStoredData.getPosition().equals("0"))
     //   if (USER_obj.getPostionID().length()>0)
        {
            select_playerPostion_button.setText(playerStoredData.getPosition());
            Log.d("ppppppa",playerStoredData.getPosition());
        }

        PlayerPositions positions = new PlayerPositions();
        if (USER_obj.getPostionID()!=null) {
            Log.d("possa1",playerPositionsArrayList.get(0).getPostionName());
            Log.d("posssss",positions.getPostionNamebyId(playerPositionsArrayList, USER_obj.getPostionID()));
         //   select_playerPostion_button.setText(positions.getPostionNamebyId(playerPositionsArrayList, USER_obj.getPostionID()));
        }
        //city
        PrefManager prefManager = new PrefManager(getActivity());
        String cities = prefManager.getCITIES();
        Cities_Parser parser = new Cities_Parser(getActivity());
        cityArrayList = new ArrayList<City>();
        cityArrayList = parser.parse(cities);

        City city = new City();

        select_city_button.setText(city.getCityNameById(cityArrayList, String.valueOf(USER_obj.getCityId())));

        select_city_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] city_arr = new String[cityArrayList.size()];
                for (int i = 0; i < cityArrayList.size(); i++) {
                    city_arr[i] = cityArrayList.get(i).getCityName();
                }
                if (language.equals("en")) {
                    drawDialog("City", city_arr, 1);
                } else {
                    drawDialog("الموقع", city_arr, 1);

                }
            }
        });

        select_playerPostion_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String[] postions_arr = new String[playerPositionsArrayList.size()];
                for (int i = 0; i < playerPositionsArrayList.size(); i++) {
                    postions_arr[i] = playerPositionsArrayList.get(i).getPostionName();
                }
                if (language.equals("en")) {
                    drawDialog("Position", postions_arr, 2);
                } else {
                    drawDialog("المركز", postions_arr, 2);
                }

            }
        });

        select_birthDate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                        EditPlayerDataFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                if (language.equals("en")) {
                    dpd.setOkText("Choose");
                    dpd.setCancelText("Cancel");
                } else {
                    dpd.setOkText("اختار");
                    dpd.setCancelText("إلغاء");
                }
                dpd.show(getActivity().getFragmentManager(), "date");

            }
        });

        select_RorL_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (language.equals("en")) {
                    String[] rol = {"Left", "Right"};
                    drawDialog("Right OR Left", rol, 3);
                } else {
                    String[] rol = {"يسار", "يمين"};
                    drawDialog("يمين ام يسار", rol, 3);
                }
            }
        });

        ACProgressFlower    progressFlower = new ACProgressFlower.Builder(getActivity())
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.GREEN).build();

        RefreshUserInforamtion refreshUserInforamtion =
                new RefreshUserInforamtion(getActivity(),select_city_button,
                        select_playerPostion_button,select_birthDate_btn,select_RorL_button,progressFlower);


        btn_updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ValidatorInputs validateInput =new ValidatorInputs();;
                 if (!TextUtils.isEmpty(MailEditText.getText().toString()))
                 {
                     if (!validateInput.validateEmail(MailEditText.getText().toString()))
                     {
                         if (language.equals("ar"))
                             Toast.makeText(getActivity(), "البريد الالكتروني غير صحيح", Toast.LENGTH_SHORT).show();
                         else Toast.makeText(getActivity(), "Wrong Email Address", Toast.LENGTH_SHORT).show();
                         return;
                     }
                 }

                final ExceptionHandling exceptionHandling = new ExceptionHandling(getActivity());

                flower_dialog = new ACProgressFlower.Builder(getActivity())
                        .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                        .themeColor(Color.WHITE)
                        .fadeColor(Color.GREEN).build();


                if (!TextUtils.isEmpty(MailEditText.getText().toString()) &&
                        !TextUtils.isEmpty(PasswordEditTExt.getText().toString()) &&
                        !TextUtils.isEmpty(MobileEditTExt.getText().toString()) &&
                        !TextUtils.isEmpty(nameEditText.getText().toString()) &&
                        !TextUtils.isEmpty(address_editText.getText().toString())
                        ) {
                    flower_dialog.show();
                    String mail = MailEditText.getText().toString().replace(" ", "");
                    ;
                    String pass = PasswordEditTExt.getText().toString().trim();
                    String mobile = MobileEditTExt.getText().toString().trim();
                    String name = nameEditText.getText().toString().replace(" ", "");
                    ;
                    String address = address_editText.getText().toString().replace(" ", "");
                    ;
                    String cityName = select_city_button.getText().toString();
                   // String pos = select_playerPostion_button.getText().toString();
                    City city1 = new City();
                    String cityId = city1.getCityIdbyName(cityArrayList, cityName);

                    String rol = "";
                    if (yes_no_RorL != null) {
                        if (yes_no_RorL.equals("0"))
                            rol = "yes";
                        else
                            rol = "no";
                    }
                    PlayerPositions positions =new PlayerPositions();
                    String posName = select_playerPostion_button.getText().toString();
                    String posId = positions.getPostionIdbyName(playerPositionsArrayList,posName);
                      String dateOnButton = select_birthDate_btn.getText().toString();
                    // Instantiate the RequestQueue.
                    final RequestQueue queue = Volley.newRequestQueue(getActivity());
                    String url;
                    String chooseTextBtn = select_RorL_button.getText().toString();

                   if (chooseTextBtn.equals("Choose") || chooseTextBtn.equals("اختار"))
                       url = URLs.updateProfileAPI + "id=" + USER_obj.getId() + "&name=" +
                            name + "&birthdate=" + dateOnButton + "&email=" + mail + "&mobile=" + mobile
                            + "&password=" + pass + "&cityId=" + cityId + "&address=" + address
                            + "&position=" + posId + "&lang="+lang;
                   else {
                       if (yes_no_RorL==null)
                       {
                           if (chooseTextBtn.equals("يسار")||chooseTextBtn.equals("Left"))
                               rol = "yes";
                           else if (chooseTextBtn.equals("يمين")||chooseTextBtn.equals("Right"))
                               rol = "no";
                       }
                       url = URLs.updateProfileAPI + "id=" + USER_obj.getId() + "&name=" +
                               name + "&birthdate=" + dateOnButton + "&email=" + mail + "&mobile=" + mobile
                               + "&password=" + pass + "&cityId=" + cityId + "&rorl=" + rol + "&address=" + address
                               + "&position=" + posId + "&lang=" + lang;
                        }


                    Log.d("url_update ", url);

// Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        if (Integer.parseInt(jsonObject.getString("success")) == 1) {

                                           try {
                                               flower_dialog.dismiss();
                                           }catch (Exception e)
                                           {e.printStackTrace();}
                                         //   RefreshUserInforamtion refreshUserInforamtion = new RefreshUserInforamtion(getActivity());

                                            final Activity mActivity = getActivity();

                                            final Dialog dialog = new Dialog(mActivity, R.style.CustomDialogTheme);
                                            //  dialog.setCancelable(false);
                                            dialog.setContentView(R.layout.no_playgrounds_custom_dialog);
                                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                            dialog.setCancelable(false);
                                            dialog.setCancelable(true);
                                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                            TextView no_message_text = (TextView) dialog.findViewById(R.id.no_message_text);
                                            no_message_text.setTypeface(custom_DIN_Next);

                                            Button close_button = (Button) dialog.findViewById(R.id.btn_close_no_playground);
                                            close_button.setTypeface(custom_DIN_Next);


                                            close_button.setOnClickListener(new View.OnClickListener() {

                                                @Override
                                                public void onClick(View v) {
                                                    dialog.cancel();
                                                   /* Fragment editPlayerFragment = getActivity().getSupportFragmentManager().findFragmentByTag("editPlayerFragment");

                                                    if (editPlayerFragment == null) {
                                                        getActivity().getSupportFragmentManager()
                                                                .beginTransaction()
                                                                .replace(R.id.placeholder2, new EditPlayerDataFragment(), "editPlayerFragment")
                                                                .addToBackStack("editPlayerFragment")
                                                                .commit();
                                                    } else { // re-use the old fragment
                                                        getActivity().getSupportFragmentManager()
                                                                .beginTransaction()
                                                                .replace(R.id.placeholder2, editPlayerFragment, "editPlayerFragment")
                                                                .addToBackStack("editPlayerFragment")

                                                                .commit();
                                                    }*/
                                                    dialog.dismiss();
//                                                    dialog1.dismiss();
                                                    Intent intent = new Intent(getActivity(),MainContainer.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                                    startActivity(intent);
                                                    getActivity().finish();

                                                }
                                            });

                                            if (language.equals("en")) {
                                                no_message_text.setText("Saved");
                                                close_button.setText(R.string.close);
                                            } else {
                                                no_message_text.setText("تم الحفظ");
                                                close_button.setText("إغلاق");
                                            }

                                            dialog.show();


                                        }
                                        else {
                                            exceptionHandling.showErrorDialog();

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        exceptionHandling.showErrorDialog();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            exceptionHandling.showErrorDialog();

                        }
                    });
// Add the request to the RequestQueue.
                    queue.add(stringRequest);


                } else {
                    if (language.equals("en")) {
                        Toast.makeText(getActivity(), "Please enter all data", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "ادخل البيانات المطلوبة", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        select_profile_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestPermission();


            }
        });

        select_lang_btn.setTypeface(custom_DIN_Next);
        select_lang_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog lang_dialog = new Dialog(getActivity(), R.style.CustomDialogTheme);
                lang_dialog.setContentView(R.layout.select_lang_dialog);
                //  dialog.setTitle("This is my custom dialog box");
                lang_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                lang_dialog.setCancelable(false);
                lang_dialog.setCanceledOnTouchOutside(false);

                final RadioButton arabic_radio_btn = (RadioButton) lang_dialog.findViewById(R.id.arabic_radio_btn);
                final RadioButton english_radio_btn = (RadioButton) lang_dialog.findViewById(R.id.english_radio_btn);
                final RadioGroup lang_radio_group = (RadioGroup) lang_dialog.findViewById(R.id.lang_radio_group);
                Button save_lang_btn = (Button) lang_dialog.findViewById(R.id.save_lang_btn);
                TextView lang_tv_mesg = (TextView) lang_dialog.findViewById(R.id.lang_tv_mesg);
                ImageView en_pic = (ImageView) lang_dialog.findViewById(R.id.en_pic);
                ImageView ar_pic = (ImageView) lang_dialog.findViewById(R.id.ar_pic);
                Button cancel_lan_btn = (Button) lang_dialog.findViewById(R.id.cancel_lan_btn);
                cancel_lan_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lang_dialog.dismiss();
                    }
                });
                cancel_lan_btn.setTypeface(custom_DIN_Next);
                if (language.equals("en"))
                    cancel_lan_btn.setText("Cancel");
                ar_pic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        arabic_radio_btn.performClick();
                    }
                });
                en_pic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        english_radio_btn.performClick();
                    }
                });



                lang_tv_mesg.setTypeface(custom_DIN_Next);
                arabic_radio_btn.setTypeface(custom_DIN_Next);
                save_lang_btn.setTypeface(custom_DIN_Next);
                english_radio_btn.setTypeface(custom_DIN_Next);

                if (language.equals("en")) {
                    lang_tv_mesg.setText(R.string.chooseApp);
                    save_lang_btn.setText(R.string.save);
                }

                save_lang_btn.setOnClickListener(new View.OnClickListener() {

                    SharedPreferences sharedPreferences1 = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPreferences1.edit();

                    @Override
                    public void onClick(View v) {
                        lang_dialog.dismiss();
                        //get radio button id
                        int selectedId = lang_radio_group.getCheckedRadioButtonId();


                        radioButton = (RadioButton) lang_dialog.findViewById(selectedId);
                        try {
                            if (radioButton.getId() == R.id.arabic_radio_btn) {
                                // save user option in shared prefrences
                                lang_dialog.dismiss();
                                final Activity mActivity = getActivity();
                                editor.putString("language", "ar");
                                PrefManager prefManager = new PrefManager(getActivity());
                                prefManager.setLanguage("ar");
                                editor.apply();
                                final Dialog DoneDialog = new Dialog(mActivity, R.style.CustomDialogTheme);
                                //  dialog.setCancelable(false);
                                DoneDialog.setContentView(R.layout.no_playgrounds_custom_dialog);
                                DoneDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                DoneDialog.setCancelable(false);
                                DoneDialog.setCanceledOnTouchOutside(false);
                                DoneDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                TextView no_message_text = (TextView) DoneDialog.findViewById(R.id.no_message_text);
                                no_message_text.setTypeface(custom_DIN_Next);


                                Button close_button = (Button) DoneDialog.findViewById(R.id.btn_close_no_playground);
                                close_button.setTypeface(custom_DIN_Next);
                                close_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        DoneDialog.dismiss();
                                        lang_dialog.dismiss();
                                    }
                                });
                                DoneDialog.show();

                            /*if (language.equals("en")) {
                                no_message_text.setText("Saved");
                                close_button.setText(R.string.close);
                            } else {
                                no_message_text.setText("تم الحفظ");
                                close_button.setText("إغلاق");
                            }*/
                                no_message_text.setText("تم الحفظ");
                                close_button.setText("إغلاق");
                                close_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        DoneDialog.dismiss();
                                        Intent intent = new Intent(getActivity(), MainContainer.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


                                        startActivity(intent);
                                        getActivity().finish();
                                    }
                                });


                            } else if (radioButton.getId() == R.id.english_radio_btn) {
                                // save user option in shared prefrences
                                lang_dialog.dismiss();
                                final Activity mActivity = getActivity();
                                SharedPreferences.Editor editor = sharedPreferences1.edit();
                                editor.putString("language", "en");
                                PrefManager prefManager = new PrefManager(getActivity());
                                prefManager.setLanguage("en");
                                editor.apply();
                                final Dialog DoneDialog = new Dialog(mActivity, R.style.CustomDialogTheme);
                                DoneDialog.setContentView(R.layout.no_playgrounds_custom_dialog);
                                DoneDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                DoneDialog.setCancelable(false);
                                DoneDialog.setCanceledOnTouchOutside(false);
                                DoneDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                TextView no_message_text = (TextView) DoneDialog.findViewById(R.id.no_message_text);
                                no_message_text.setTypeface(custom_DIN_Next);


                                Button close_button = (Button) DoneDialog.findViewById(R.id.btn_close_no_playground);
                                close_button.setTypeface(custom_DIN_Next);

                                DoneDialog.show();
                            /*if (language.equals("en")) {
                                no_message_text.setText("Saved");
                                close_button.setText(R.string.close);
                            } else {
                                no_message_text.setText("تم الحفظ");
                                close_button.setText("إغلاق");
                            }*/
                                no_message_text.setText("Saved");
                                close_button.setText(R.string.close);
                                close_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        DoneDialog.dismiss();


                                        Intent intent = new Intent(getActivity(), MainContainer.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                        startActivity(intent);
                                        getActivity().finish();
                                    }
                                });

                            }
                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                });

                lang_dialog.show();

            }
        });
        cancel_btn = (Button) view.findViewById(R.id.cancel_btn);

        cancel_btn.setTypeface(custom_DIN_Next);
        select_lang_btn.setTypeface(custom_DIN_Next);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        return view;
    }

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


    public void makePostRequest(String image, final int id, String ImageName) throws IOException {
        if (flower_dialog != null && flower_dialog.isShowing()) {
            flower_dialog.dismiss();
        }

        try {
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    flower_dialog1 = new ACProgressFlower.Builder(getActivity())
                            .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                            .themeColor(Color.WHITE)
                            .fadeColor(Color.GREEN).build();
                    flower_dialog1.show();

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        ServerWork serverWork = new ServerWork();
        serverWork.post(URLs.uploadProfileImageApi, image, id, ImageName, new okhttp3.Callback() {

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
                        close_button.setTypeface(custom_DIN_Next);
                        textView.setTypeface(custom_DIN_Next);
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
                      //  RefreshUserInforamtion refreshUserInforamtion = new RefreshUserInforamtion(getActivity());


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
                                    close_button.setTypeface(custom_DIN_Next);
                                    textView.setTypeface(custom_DIN_Next);
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
                                        flower_dialog1.dismiss();
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
                                    flower_dialog1.dismiss();
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
                            flower_dialog1.dismiss();
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

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Refresh the state of the +1 button each time the activity receives focus.
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        language = sharedPref.getString("language", "");
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void processFinish(Boolean output) {

        if (output) {

        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date;
        if (monthOfYear+1 < 10)
          date = year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth;
       else date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        select_birthDate_btn.setText(date);
        selected_date = date;
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        selected_date = "";
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

   /* public void English() {
        username_textview.setText(R.string.player);
        email_textview.setText(R.string.loginEmail);
        mobile_textview.setText(R.string.mobile);
        password_textview.setText(R.string.loginPassword);
        address_textview.setText(R.string.Address);
        city_textview.setText(R.string.city);
        rorl_textview.setText(R.string.Position);
        rorl_textview__.setText(R.string.RightorLeft);
        birthdate_tv.setText(R.string.DateofBirth);
        proImage_textview.setText(R.string.profileImage);
        change_lang_tv.setText(R.string.applanguage);
        btn_updateData.setText(R.string.save);
        select_city_button.setText(R.string.chooseEn);
        select_playerPostion_button.setText(R.string.chooseEn);
        select_RorL_button.setText(R.string.chooseEn);
        select_birthDate_btn.setText(R.string.chooseEn);
        select_profile_image_button.setText(R.string.chooseEn);
        select_lang_btn.setText(R.string.chooseEn);
        LinearLayout.LayoutParams para = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        para.gravity = Gravity.LEFT | Gravity.CENTER;
        ic_name_tv.setLayoutParams(para);
        ic_email_textview.setLayoutParams(para);
        ic_mobile_textview.setLayoutParams(para);
        ic_password_textview.setLayoutParams(para);
        ic_address_tv.setLayoutParams(para);
        ic_city.setLayoutParams(para);
        ic_rol_tv.setLayoutParams(para);
        ic_rol_tv__.setLayoutParams(para);
        birthdate_ic.setLayoutParams(para);
        ic_proImage_tv.setLayoutParams(para);
        ic_change_lang.setLayoutParams(para);
        username_textview.setLayoutParams(para);
        email_textview.setLayoutParams(para);
        mobile_textview.setLayoutParams(para);
        password_textview.setLayoutParams(para);
        address_textview.setLayoutParams(para);
        city_textview.setLayoutParams(para);
        rorl_textview.setLayoutParams(para);
        rorl_textview__.setLayoutParams(para);
        birthdate_tv.setLayoutParams(para);
        proImage_textview.setLayoutParams(para);
        change_lang_tv.setLayoutParams(para);
//        LinearLayout.LayoutParams para1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        para1.gravity = Gravity.RIGHT | Gravity.CENTER;
//        nameEditText.setLayoutParams(para1);
//        MailEditText.setLayoutParams(para1);
//        MobileEditTExt.setLayoutParams(para1);

    }*/

    void setIds_and_Fonts(View view) {
        username_textview = (TextView) view.findViewById(R.id.username_textview);
        username_textview.setTypeface(custom_DIN_Next);
        email_textview = (TextView) view.findViewById(R.id.email_textview);
        email_textview.setTypeface(custom_DIN_Next);
        mobile_textview = (TextView) view.findViewById(R.id.mobile_textview);
        mobile_textview.setTypeface(custom_DIN_Next);
        password_textview = (TextView) view.findViewById(R.id.password_textview);
        password_textview.setTypeface(custom_DIN_Next);
        address_textview = (TextView) view.findViewById(R.id.address_textview);
        address_textview.setTypeface(custom_DIN_Next);
        city_textview = (TextView) view.findViewById(R.id.city_textview);
        city_textview.setTypeface(custom_DIN_Next);
        rorl_textview = (TextView) view.findViewById(R.id.rorl_textview);
        rorl_textview.setTypeface(custom_DIN_Next);
        proImage_textview = (TextView) view.findViewById(R.id.proImage_textview);
        proImage_textview.setTypeface(custom_DIN_Next);

        ic_change_lang = (TextView) view.findViewById(R.id.ic_change_lang);
        ic_change_lang.setTypeface(customFontAwesome);
        change_lang_tv = (TextView) view.findViewById(R.id.change_lang_tv);
        change_lang_tv.setTypeface(custom_DIN_Next);
        select_lang_btn = (Button) view.findViewById(R.id.select_lang_btn);

        ic_name_tv = (TextView) view.findViewById(R.id.ic_name_tv);
        ic_email_textview = (TextView) view.findViewById(R.id.ic_email_textview);
        ic_mobile_textview = (TextView) view.findViewById(R.id.ic_mobile_textview);
        ic_password_textview = (TextView) view.findViewById(R.id.ic_password_textview);
        ic_address_tv = (TextView) view.findViewById(R.id.ic_address_tv);
        ic_city = (TextView) view.findViewById(R.id.ic_city);
        select_birthDate_btn = (Button) view.findViewById(R.id.select_birthDate_btn);
        ic_rol_tv = (TextView) view.findViewById(R.id.ic_rol_tv);
        ic_proImage_tv = (TextView) view.findViewById(R.id.ic_proImage_tv);
        birthdate_ic = (TextView) view.findViewById(R.id.birthdate_ic);
        ic_rol_tv__ = (TextView) view.findViewById(R.id.ic_rol_tv__);
        birthdate_ic.setTypeface(customFontAwesome);
        ic_rol_tv__.setTypeface(customFontAwesome);
        ic_address_tv.setTypeface(customFontAwesome);
        ic_name_tv.setTypeface(customFontAwesome);
        ic_email_textview.setTypeface(customFontAwesome);
        ic_mobile_textview.setTypeface(customFontAwesome);
        ic_password_textview.setTypeface(customFontAwesome);
        ic_address_tv.setTypeface(customFontAwesome);
        ic_city.setTypeface(customFontAwesome);
        ic_rol_tv.setTypeface(customFontAwesome);
        ic_proImage_tv.setTypeface(customFontAwesome);
        nameEditText = (EditText) view.findViewById(R.id.username_editText);
        MailEditText = (EditText) view.findViewById(R.id.email_editText);
        MobileEditTExt = (EditText) view.findViewById(R.id.mobile_editText);
        PasswordEditTExt = (EditText) view.findViewById(R.id.password_editText);
        btn_updateData = (Button) view.findViewById(R.id.btn_updateData);
        address_editText = (EditText) view.findViewById(R.id.address_editText);
        select_city_button = (Button) view.findViewById(R.id.select_city_button);
        select_playerPostion_button = (Button) view.findViewById(R.id.select_playerPostion_button);
        select_profile_image_button = (Button) view.findViewById(R.id.select_profile_image_button);
        select_RorL_button = (Button) view.findViewById(R.id.select_RorL_button);
        birthdate_tv = (TextView) view.findViewById(R.id.birthdate_tv);
        rorl_textview__ = (TextView) view.findViewById(R.id.rorl_textview__);
        nameEditText.setTypeface(custom_DIN_Next);
        MailEditText.setTypeface(custom_DIN_Next);
        MobileEditTExt.setTypeface(custom_DIN_Next);
        PasswordEditTExt.setTypeface(custom_DIN_Next);
        address_editText.setTypeface(custom_DIN_Next);
        birthdate_tv.setTypeface(custom_DIN_Next);
        rorl_textview__.setTypeface(custom_DIN_Next);
        btn_updateData.setTypeface(custom_DIN_Next);
        select_city_button.setTypeface(custom_DIN_Next);
        select_playerPostion_button.setTypeface(custom_DIN_Next);
        select_profile_image_button.setTypeface(custom_DIN_Next);
        select_birthDate_btn.setTypeface(custom_DIN_Next);
        select_RorL_button.setTypeface(custom_DIN_Next);
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
        title.setTypeface(custom_DIN_Next);

        dialog.setCustomTitle(title);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.choice_item, R.id.text_view_choice, s) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(R.id.text_view_choice);
                text.setTypeface(custom_DIN_Next);
                return view;
            }
        };
        dialog.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (button_num == 1)
                    select_city_button.setText(s[which]);
                if (button_num == 2) {
                    select_playerPostion_button.setText(s[which]);

                }
                if (button_num == 3) {
                    select_RorL_button.setText(s[which]);
                    yes_no_RorL = String.valueOf(which);
                }

            }
        });


        AlertDialog alert = dialog.create();

        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openFilePicker();
        }
    }



    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {
            openFilePicker();
        }
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

    private Bitmap decodeFile(File f) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE=70;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {}
        return null;
    }

}
