//RegistrationActivity
package com.logicdesigns.mohammed.mal3bklast;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.gson.Gson;
import com.logicdesigns.mohammed.mal3bklast.JSON_PARSER.Cities_Parser;
import com.logicdesigns.mohammed.mal3bklast.Models.City;
import com.logicdesigns.mohammed.mal3bklast.SharedPref.PrefManager;
import com.logicdesigns.mohammed.mal3bklast.common.URLs;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import BusinessClasses.JSONParser;
import BusinessClasses.User;
import BusinessClasses.ValidatorInputs;
import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class RegistrationActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    String urlInsert = URLs.REgistration_link;
    JSONParser jsonParser;
    Typeface custom_font;
    Typeface custom_font_awesome, custom_font_droid;
    String[] towns;
    EditText name, email, phone, password1, password2;
    Button town;

    TextView first_prompt, second_prompt, name_icon_registration, email_icon_registration, phone_icon_registration, password_icon_registration, conpassword_icon_registration, town_icon_registration;
    CheckBox checkBoxAccept;
    boolean nameCheck = false, emailCheck = false, phoneCheck = false, password1Check = false, passowrd2Check = false, townCheck = false, acceptCheck = true;
    ImageView nameIcon, emailIcon, phoneIcon, password1Icon, passowrd2Icon, townIcon;
    ValidatorInputs validateInput = new ValidatorInputs();
    Button rulesbtn, registerbtn;
    User user = new User();
    JSONArray User = null;
    ProgressBar loadiconreg;
    ArrayList<String> cityIds;
    ArrayList<City> cityArrayList;

    ArrayList citiesNames;
    Button button_reg_GOOGLE, facebook_button;
    private static final int RC_SIGN_IN = 007;
    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;
    private Button btnSignIn_google;
    ProgressDialog progressDialog;
    CallbackManager callbackManager;
    LoginButton loginButton;
    String FaceID, FaceEmail, FaceName, FaceURL;
    String Global = null;
    String language;
    SharedPreferences sharedPref;
    String cityId;


    String Google_ID, Google_name, Google_Image, Google_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        custom_font = Typeface.createFromAsset(getAssets(), "fonts/DIN Next LT W23 Regular.ttf");
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.logicdesigns.mohammed.mal3bklast",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash: ", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        jsonParser = new JSONParser();
        // new GetCities().execute();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("جاري التحميل");
        progressDialog.setCancelable(false);
        setContentView(R.layout.activity_registration);
        custom_font_awesome = Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf");
        name_icon_registration = (TextView) findViewById(R.id.name_icon_registration);
        name_icon_registration.setTypeface(custom_font_awesome);
        email_icon_registration = (TextView) findViewById(R.id.email_icon_registration);
        email_icon_registration.setTypeface(custom_font_awesome);
        phone_icon_registration = (TextView) findViewById(R.id.phone_icon_registration);
        phone_icon_registration.setTypeface(custom_font_awesome);
        password_icon_registration = (TextView) findViewById(R.id.password_icon_registration);
        password_icon_registration.setTypeface(custom_font_awesome);
        conpassword_icon_registration = (TextView) findViewById(R.id.conpassword_icon_registration);
        conpassword_icon_registration.setTypeface(custom_font_awesome);
        town_icon_registration = (TextView) findViewById(R.id.town_icon_registration);
        town_icon_registration.setTypeface(custom_font_awesome);
        first_prompt = (TextView) findViewById(R.id.first_prompt);
        second_prompt = (TextView) findViewById(R.id.second_prompt);
        rulesbtn = (Button) findViewById(R.id.rulesbtn);
        registerbtn = (Button) findViewById(R.id.registerbtn);
        first_prompt.setTypeface(custom_font);
        second_prompt.setTypeface(custom_font);
        rulesbtn.setTypeface(custom_font);
        rulesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, TermsActivity.class);
                startActivity(intent);
            }
        });
        registerbtn.setTypeface(custom_font);
        name = (EditText) findViewById(R.id.nameedittextreg);
        name.setTypeface(custom_font);
        nameIcon = (ImageView) findViewById(R.id.namevalidicon);
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    if (validateInput.validateName(name.getText().toString())) {
                        nameIcon.setImageResource(R.drawable.trueimage);
                        nameCheck = true;
                        user.setName(name.getText().toString());
                    } else {
                        nameIcon.setImageResource(R.drawable.falseimage);
                        nameCheck = false;
                    }
                }
            }
        });
        email = (EditText) findViewById(R.id.emailedittextreg);
        email.setTypeface(custom_font);
        emailIcon = (ImageView) findViewById(R.id.emailvalidicon);
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    if (validateInput.validateEmail(email.getText().toString())) {
                        emailIcon.setImageResource(R.drawable.trueimage);
                        user.setEmail(email.getText().toString());
                        emailCheck = true;
                    } else {
                        emailIcon.setImageResource(R.drawable.falseimage);
                        emailCheck = false;
                    }
                }
            }
        });
        phone = (EditText) findViewById(R.id.phoneedittextreg);
        phone.setTypeface(custom_font);
        phoneIcon = (ImageView) findViewById(R.id.phonevalidicon);
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    if (validateInput.validatePhone(phone.getText().toString())) {
                        phoneIcon.setImageResource(R.drawable.trueimage);
                        user.setMobile(phone.getText().toString());
                        phoneCheck = true;
                    } else {
                        phoneIcon.setImageResource(R.drawable.falseimage);
                        phoneCheck = false;
                    }
                }
            }
        });
        password1 = (EditText) findViewById(R.id.passwordedittextreg);
        password1.setTypeface(custom_font);
        password1Icon = (ImageView) findViewById(R.id.password1validicon);
        password1.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    if (validateInput.validatePassword(password1.getText().toString())) {
                        password1Icon.setImageResource(R.drawable.trueimage);
                        user.setPassword(password1.getText().toString());
                        password1Check = true;
                    } else {
                        password1Icon.setImageResource(R.drawable.trueimage);
                        password1Check = true;
                    }
                }
            }
        });
        password2 = (EditText) findViewById(R.id.confirmedittextreg);
        password2.setTypeface(custom_font);
        passowrd2Icon = (ImageView) findViewById(R.id.password2validicon);
        password2.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    if (validateInput.matchPassword(password2.getText().toString(), password1.getText().toString())) {
                        passowrd2Icon.setImageResource(R.drawable.trueimage);
                        passowrd2Check = true;
                    } else {
                        passowrd2Icon.setImageResource(R.drawable.falseimage);
                        passowrd2Check = false;
                    }
                }
            }
        });
        town = (Button) findViewById(R.id.towneditSpinnerreg);
        town.setTypeface(custom_font);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        language = sharedPref.getString("language", "ar");
        if (language.equals("en"))
            town.setText("Choose");
        townIcon = (ImageView) findViewById(R.id.townvalidicon);
      /*  town.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                townIcon.setImageResource(R.drawable.trueimage);
                user.setTown(cityIds.get(position));
                townCheck = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                townIcon.setImageResource(R.drawable.falseimage);
                townCheck = false;
            }

        });*/
        town.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("InsideTown", "InsideTown");

                PrefManager prefManager = new PrefManager(RegistrationActivity.this);
                String cities = prefManager.getCITIES();
                Cities_Parser parser = new Cities_Parser(RegistrationActivity.this);
                cityArrayList = new ArrayList<City>();
                cityArrayList = parser.parse(cities);


                String[] city_arr = new String[cityArrayList.size()];
                for (int i = 0; i < cityArrayList.size(); i++) {
                    city_arr[i] = cityArrayList.get(i).getCityName();
                }
                sharedPref = PreferenceManager.getDefaultSharedPreferences(RegistrationActivity.this);
                language = sharedPref.getString("language", "");
                if (language.equals("en")) {

                    drawDialog("City", city_arr, 1);
                } else {

                    drawDialog("المدينة", city_arr, 1);
                }

            }

        });
        checkBoxAccept = (CheckBox) findViewById(R.id.acceptcheckboxreg);
        checkBoxAccept.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                      @Override
                                                      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                          if (isChecked) {
                                                              acceptCheck = true;
                                                          } else {
                                                              acceptCheck = false;
                                                          }
                                                      }
                                                  }
        );
        user.setAccountDate(new Date());
        btnSignIn_google = (Button) findViewById(R.id.btn_sign_in_google);
        btnSignIn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mGoogleApiClient.isConnected())
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.registerLayout);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        language = sharedPref.getString("language", "");
        if (language.equals("en")) {
            English();
            linearLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }

        /*Facebook Login*/
        facebook_button = (Button) findViewById(R.id.facebook_button);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_friends"));
        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                User user = new User();
                if (loginResult != null) {
                    Profile profile = Profile.getCurrentProfile();
                    if (profile != null) {
                        displayMessage(profile, user);
                    }
                }
                if (AccessToken.getCurrentAccessToken() != null) {
                    RequestData(user);
                }
            }

            @Override
            public void onCancel() {
                Toast.makeText(RegistrationActivity.this, "onCancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(RegistrationActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void displayMessage(Profile profile, User user) {
        if (profile != null) {
            FaceID = profile.getId();
            FaceName = profile.getName();
            FaceName.replace(" ", "%20");
            FaceURL = profile.getProfilePictureUri(200, 200).toString();
        }
    }

    private void RequestData(final User user) {

        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                final JSONObject json = response.getJSONObject();
                try {
                    if (json != null) {
                        FaceID = json.getString("id");
                        FaceEmail = json.getString("email");
                        FaceName = json.getString("name");
                        FaceName.replace(" ", "%20");
                        FaceURL = "https://graph.facebook.com/" + FaceID + "/picture?type=large";
                        RegisterWithFaceBook();
                        //new RegistrationActivity.CreateUser().execute();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }

    public void English() {
        first_prompt.setText(R.string.createAccount);
        name.setHint(R.string.player);
        email.setHint(R.string.loginEmail);
        phone.setHint(R.string.mobile);
        password1.setHint(R.string.loginPassword);
        password2.setHint(R.string.confirmPassword);
        second_prompt.setText(R.string.agree);
        rulesbtn.setText(R.string.terms);
        registerbtn.setText(R.string.create);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER | Gravity.RIGHT;
        rulesbtn.setLayoutParams(lp);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            //   progressDialog.dismiss();
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e("", "display name: " + acct.getDisplayName());


            String personName = acct.getDisplayName();
            String personPhotoUrl = null;
            if (acct.getPhotoUrl() != null)
                personPhotoUrl = acct.getPhotoUrl().toString();
            String email = acct.getEmail();

            if (personName != null)
                Google_name = personName;
            else Google_name = " ";
            if (acct.getId() != null)
                Google_ID = acct.getId();
            else Google_ID = " ";
            if (acct.getEmail() != null)
                Google_email = acct.getEmail();
            else Google_email = " ";
            if (acct.getPhotoUrl() != null)
                Google_Image = acct.getPhotoUrl().toString();
            else Google_Image = " ";


            LoginWithGoogle();

        } else {
            // Signed out, show unauthenticated UI.
            //Toast.makeText(this, "Error Happened", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayMessage(Profile.getCurrentProfile(), user);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @Override
    public void onStart() {
        super.onStart();


        if (mGoogleApiClient.isConnected())
            Auth.GoogleSignInApi.signOut(mGoogleApiClient);

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d("", "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            //  handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            // showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    // hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
        ///set fonts for icoons

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d("", "onConnectionFailed:" + connectionResult);
        // progressDialog.dismiss();
    }

//    private void showProgressDialog() {
//        if (mProgressDialog == null) {
//            mProgressDialog = new ProgressDialog(this);
//            mProgressDialog.setMessage("Loading");
//            mProgressDialog.setIndeterminate(true);
//        }
//
//        mProgressDialog.show();
//    }

//    private void hideProgressDialog() {
//        if (mProgressDialog != null && mProgressDialog.isShowing()) {
//            mProgressDialog.hide();
//        }
//    }


    /*@Override
    protected void onStart() {
        super.onStart();

        ///set fonts for icoons
        name_icon_registration=(TextView) findViewById(R.id.name_icon_registration);
        name_icon_registration.setTypeface(custom_font_awesome);
        email_icon_registration=(TextView) findViewById(R.id.email_icon_registration);
        email_icon_registration.setTypeface(custom_font_awesome);
        phone_icon_registration=(TextView) findViewById(R.id.phone_icon_registration);
        phone_icon_registration.setTypeface(custom_font_awesome);
        password_icon_registration=(TextView) findViewById(R.id.password_icon_registration);
        password_icon_registration.setTypeface(custom_font_awesome);
        conpassword_icon_registration=(TextView) findViewById(R.id.conpassword_icon_registration);
        conpassword_icon_registration.setTypeface(custom_font_awesome);
        town_icon_registration=(TextView) findViewById(R.id.town_icon_registration);
        town_icon_registration.setTypeface(custom_font_awesome);



    }*/

    public void addUser(View v) {
        if (nameCheck && emailCheck && phoneCheck && password1Check && passowrd2Check & acceptCheck & townCheck) {
            loadiconreg = (ProgressBar) findViewById(R.id.loadiconreg);
            user.setAccountDate(new Date());
            loadiconreg.setVisibility(View.VISIBLE);
            City city = new City();
            cityId = city.getCityIdbyName(cityArrayList,
                    town.getText().toString());
            new RegistrationActivity.CreateUser().execute();
        } else {

          /*  if (!validateInput.validatePassword(password1.getText().toString())) {
                Toast.makeText(this, "كلمه المرور يجب ان تحتوي علي 5 ارقام علي الاقل و حرف واحد علي الاقل", Toast.LENGTH_LONG).show();
            }
            if (!validateInput.matchPassword(password1.getText().toString(), password2.getText().toString())) {
                Toast.makeText(this, "كلمه المرور يجب ان تكون متطابقه", Toast.LENGTH_LONG).show();
            }*/
            if (language.equals("en")) {
                Toast.makeText(this, R.string.fields, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "برجاء ادخال البيانات المطلوبه", Toast.LENGTH_LONG).show();
            }

        }

    }

    public void facebookRegister(View view) {
        if (view == facebook_button) {
            loginButton.performClick();
        }
    }


    //////Async class to get data

    class CreateUser extends AsyncTask<String, String, Boolean> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            // loadiconreg.setVisibility(View.VISIBLE);


        }

        @Override
        protected Boolean doInBackground(String... arg) {
            // TODO Auto-generated method stub
            DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("key", "logic123"));
            params.add(new BasicNameValuePair("name", user.getName()));
            params.add(new BasicNameValuePair("email", user.getEmail()));
            params.add(new BasicNameValuePair("phone", user.getMobile()));
            params.add(new BasicNameValuePair("password", user.getPassword()));



            params.add(new BasicNameValuePair("town", cityId));
            params.add(new BasicNameValuePair("accountDate", df.format(user.getAccountDate())));


            JSONObject json = jsonParser.makeHttpRequest(urlInsert,
                    "GET", params);

             Log.d("registerURL",urlInsert);
            // check for success tag
            try {
                int success = json.getInt("success");

                if (success == 1) {
                    Log.d("registerURL",json.toString());
                    User = json.getJSONArray("user");
                    JSONObject c = User.getJSONObject(0);
                    user.setId(Integer.parseInt(c.getString("id")));
                    user.setName(c.getString("name"));
                    user.setPassword(c.getString("password"));
                    user.setEmail(c.getString("email"));
                    user.setCityId(Integer.parseInt(c.getString("cityId")));
                    DateFormat format = new SimpleDateFormat("yyyy-MMMM-d", Locale.ENGLISH);
                /*    try {

                        Date date = format.parse(c.getString("birthdate"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }*/

                    user.setMobile(c.getString("mobile"));
                    if (c.getString("teamId").length() > 0)
                        user.setTeamId(Integer.parseInt(c.getString("teamId")));

                    user.setImage(c.getString("image"));

                    user.setAddress(c.getString("address"));
                    if (c.getString("fId").length() > 0)
                        user.setFbId(c.getString("fId"));
                    if (c.getString("googleId").length() > 0)
                        user.setGoogleId(c.getString("googleId"));
                    user.setPosition(c.getString("position"));


                    //store data of user in  shared prefrence
                    SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    SharedPreferences.Editor prefsEditor = mPrefs.edit();
                    Gson gson = new Gson();

                    String jsonObject = gson.toJson(user);
                    prefsEditor.putString("userObject", jsonObject);

                    prefsEditor.apply();
                    return true;

                } else {

                    return false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return false;


        }

        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            if (result) {
                Intent intent = new Intent(RegistrationActivity.this, MainContainer.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
            } else {
                if (language.equals("ar"))
                    Toast.makeText(RegistrationActivity.this, "البريد الالكتروني او الهاتف موجود بالفعل", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(RegistrationActivity.this, "Either the e-mail or the phone is repeated", Toast.LENGTH_LONG).show();

            }
            loadiconreg.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.stopAutoManage(this);
        mGoogleApiClient.disconnect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGoogleApiClient.stopAutoManage(this);
        mGoogleApiClient.disconnect();
    }


    public void RegisterWithFaceBook() {
        final ACProgressFlower flower_dialog = new ACProgressFlower.Builder(RegistrationActivity.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.GREEN).build();
        flower_dialog.show();
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String name = FaceName.replace(" ", "%20");
        String url = "https://logic-host.com/work/kora/beta/phpFiles/signUpWithFBAPI.php?key=logic123&fid=" + FaceID + "&name=" + name + "&email=" + FaceEmail + "&image=" + FaceURL + "";
        Log.d("dfdfdf", url);
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        flower_dialog.dismiss();
                        // Display the first 500 characters of the response string.
                        Log.d("response", response);
                        try {
                            JSONObject json = new JSONObject(response);
                            int success = json.getInt("success");

                            if (success == 1) {
                                User = json.getJSONArray("user");
                                JSONObject c = User.getJSONObject(0);
                                user.setId(Integer.parseInt(c.getString("id")));
                                user.setName(c.getString("name"));
                                user.setEmail(c.getString("email"));
                                user.setImage(c.getString("image"));
                                //store data of user in  shared prefrence
                                SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                                Gson gson = new Gson();
                                String jsonObject = gson.toJson(user);
                                prefsEditor.putString("userObject", jsonObject);
                                prefsEditor.apply();
                                Intent intent = new Intent(RegistrationActivity.this, MainContainer.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                                startActivity(intent);
                            } else {
                                LoginManager.getInstance().logOut();
                                Toast.makeText(RegistrationActivity.this, "البريد او الهاتف موجود بالفعل", Toast.LENGTH_LONG).show();
                                if (language.equals("en")) {
                                    Toast.makeText(RegistrationActivity.this, "user already exist", Toast.LENGTH_LONG).show();

                                }
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
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    //

    public void LoginWithGoogle() {
        mGoogleApiClient.stopAutoManage(this);
        mGoogleApiClient.disconnect();
        final ACProgressFlower flower_dialog = new ACProgressFlower.Builder(RegistrationActivity.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.GREEN).build();
        flower_dialog.show();
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String name = Google_name.replace(" ", "%20");
        String url = "https://logic-host.com/work/kora/beta/phpFiles/signUpWithGoogleAPI.php?key=logic123&gid=" + Google_ID +
                "&name=" + name + "&email=" + Google_email + "&image=" + Google_Image;

        Log.d("urlRegG", url);
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        flower_dialog.dismiss();
                        try {
                            JSONObject json = new JSONObject(response);
                            int success = json.getInt("success");
                            Log.d("responsRegistration", response);

                            if (success == 1) {
                                User = json.getJSONArray("user");
                                JSONObject c = User.getJSONObject(0);
                                user.setId(Integer.parseInt(c.getString("id")));
                                user.setName(c.getString("name"));
                                user.setEmail(c.getString("email"));
                                user.setImage(c.getString("image"));
                                //store data of user in  shared prefrence
                                SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                                Gson gson = new Gson();
                                String jsonObject = gson.toJson(user);
                                prefsEditor.putString("userObject", jsonObject);
                                prefsEditor.apply();
                                Intent intent = new Intent(RegistrationActivity.this, MainContainer.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                                startActivity(intent);
                            } else {
                                Toast.makeText(RegistrationActivity.this, "البريد او الهاتف موجود بالفعل", Toast.LENGTH_LONG).show();
                                if (language.equals("en")) {
                                    Toast.makeText(RegistrationActivity.this, "user already exist", Toast.LENGTH_LONG).show();

                                }
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
// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    void drawDialog(String Tiltle, final String s[], final int button_num) {
        final Typeface custom_DIN_Next = Typeface.createFromAsset(this.getAssets(), "fonts/DIN Next LT W23 Regular.ttf");

        @SuppressLint("RestrictedApi") AlertDialog.Builder dialog = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.CustomDialogTheme));
        TextView title = new TextView(this);

        title.setText(Tiltle);
        title.setBackgroundColor(Color.parseColor("#008542"));


        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);
        title.setTypeface(custom_DIN_Next);

        dialog.setCustomTitle(title);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
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
                    town.setText(s[which]);
                townIcon.setImageResource(R.drawable.trueimage);
                townCheck = true;

            }
        });


        AlertDialog alert = dialog.create();

        alert.show();
    }

}
