//LoginActivity

package com.logicdesigns.mohammed.mal3bklast;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
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
import com.logicdesigns.mohammed.mal3bklast.SharedPref.PrefManager;
import com.logicdesigns.mohammed.mal3bklast.common.URLs;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import BusinessClasses.JSONParser;
import BusinessClasses.User;
import BusinessClasses.ValidatorInputs;
import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    String urlInsert = URLs.LOGIN_LINK;
    JSONParser jsonParser;
    JSONArray User = null;
    EditText emailEdit, passwordEdit;
    TextView prompt_login_first, prompt_remember_me, prompt_login, emailboxicon_login, password_icon_login, arrowicon;
    ImageView emailboxvalidation_login, password_validation_login;
    Button twitterBtn, facebook_button, loginBtn, newRegBtn;
    CheckBox rememberCheck;
    User user;
    ValidatorInputs validateInput;
    boolean emailCheck, passwordCheck, checkBoxCheck;
    ProgressBar loadiconreg;
    private static final int RC_SIGN_IN = 007;
    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;
    private Button btnSignIn_google;
    CallbackManager callbackManager;
    LoginButton loginButton;
    private ProfileTracker profileTracker;
    private AccessTokenTracker accessTokenTracker;
    private AccessToken accessToken;
    private Profile profile;
    String FaceID, FaceEmail, FaceName,FaceURL;
    SharedPreferences sharedPref;
    String language = null;

    String Google_ID, Google_name,Google_Image , Google_email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        newRegBtn = (Button) findViewById(R.id.new_Registration_btn);
        final Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/DIN Next LT W23 Regular.ttf");
        final Typeface custom_font_awesome = Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf");
        final Typeface custom_droid = Typeface.createFromAsset(getAssets(), "fonts/DroidKufi-Regular.ttf");
        final Typeface custom_din = Typeface.createFromAsset(getAssets(), "fonts/DIN Next LT W23 Regular.ttf");
        newRegBtn.setPaintFlags(newRegBtn.getPaintFlags());
        newRegBtn.setTypeface(custom_font);
        validateInput = new ValidatorInputs();
        user = new User();
        prompt_login_first = (TextView) findViewById(R.id.prompt_login_first);
        prompt_remember_me = (TextView) findViewById(R.id.prompt_remember_me);
        prompt_login = (TextView) findViewById(R.id.prompt_login);
        loginBtn = (Button) findViewById(R.id.login_db_btn);
        prompt_login_first.setTypeface(custom_droid);
        prompt_remember_me.setTypeface(custom_font);
        prompt_login.setTypeface(custom_font);
        loginBtn.setTypeface(custom_font);
        emailEdit = (EditText) findViewById(R.id.email_login_et);
        emailboxvalidation_login = (ImageView) findViewById(R.id.emailboxvalidation_login);
        password_validation_login = (ImageView) findViewById(R.id.password_validation_login);
        emailboxicon_login = (TextView) findViewById(R.id.emailboxicon_login);
        emailboxicon_login.setTypeface(custom_font_awesome);
        password_icon_login = (TextView) findViewById(R.id.password_icon_login);
        password_icon_login.setTypeface(custom_font_awesome);
        emailEdit.setTypeface(custom_din);
        passwordEdit = (EditText) findViewById(R.id.password_login_et);
        passwordEdit.setTypeface(custom_din);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.loginLayout);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        language = sharedPref.getString("language", "ar");
        if (language.equals("en")) {
            English();
            linearLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        emailEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {

                    if (validateInput.validateEmail(emailEdit.getText().toString())) {
                        emailboxvalidation_login.setImageResource(R.drawable.trueimage);
                        user.setEmail(emailEdit.getText().toString());
                        emailCheck = true;
                    } else {
                        emailboxvalidation_login.setImageResource(R.drawable.falseimage);
                        emailCheck = false;
                    }

                }
            }
        });
        passwordEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() != 0) {
                    if (validateInput.validatePasswordviaDB(passwordEdit.getText().toString())) {
                        password_validation_login.setImageResource(R.drawable.trueimage);
                        user.setPassword(passwordEdit.getText().toString());
                        passwordCheck = true;


                    } else {
                        password_validation_login.setImageResource(R.drawable.falseimage);
                        passwordCheck = false;

                    }
                }

            }
        });
        /*end passsowrd validation*/



        /* Accept checkbox*/
        rememberCheck = (CheckBox) findViewById(R.id.rememberMechecklog);
        rememberCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                                     @Override
                                                     public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                         if (isChecked) {
                                                             checkBoxCheck = true;

                                                         } else {
                                                             checkBoxCheck = false;

                                                         }

                                                     }
                                                 }
        );
        // google sigin in
        btnSignIn_google = (Button) findViewById(R.id.btn_sign_in_google);
        // Customizing G+ button
        //btnSignIn.setSize(SignInButton.SIZE_STANDARD);
        btnSignIn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mGoogleApiClient.isConnected())
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        /*Facebook Login*/

        /*Facebook Login*/
        facebook_button = (Button) findViewById(R.id.facebook_button1);
        loginButton = (LoginButton) findViewById(R.id.login_button1);

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
                Toast.makeText(LoginActivity.this, "onCancel", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, "onError", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void displayMessage(Profile profile, User user) {
        if (profile != null) {
            FaceID = profile.getId();
            FaceName = profile.getName();
            FaceName.replace(" ", "%20");
            FaceURL = profile.getProfilePictureUri(200,200).toString();
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
                        FaceName.replace(" ","%20");
                        FaceURL = "https://graph.facebook.com/"+FaceID+"/picture?type=large";
                        LoginWithFaceBook();
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

    public void facebookLogin(View view) {
        if (view == facebook_button) {
            loginButton.performClick();
        }
    }


    public void English() {
        prompt_login_first.setText(R.string.loginTitle2);
        emailEdit.setHint(R.string.loginEmail);
        passwordEdit.setHint(R.string.loginPassword);
        prompt_remember_me.setText(R.string.loginRemember);
        newRegBtn.setText(R.string.registerFromLogin);
        loginBtn.setText(R.string.mainLogin);
        prompt_login.setText(R.string.Ways);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER | Gravity.RIGHT;
        newRegBtn.setLayoutParams(lp);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e("", "display name: " + acct.getDisplayName());


            String personName = acct.getDisplayName();
            String personPhotoUrl = null;
            if (acct.getPhotoUrl() != null)
                personPhotoUrl = acct.getPhotoUrl().toString();


            if (personName != null)
                Google_name = personName;
            else Google_name = " ";
            if (acct.getId() != null)
                Google_ID = acct.getId();
            else Google_ID = " ";
            if (acct.getEmail() !=null)
                Google_email = acct.getEmail();
            else  Google_email = " ";
            if (acct.getPhotoUrl()!=null)
                Google_Image = acct.getPhotoUrl().toString();
            else Google_Image = " ";


            LoginWithGoogle();

        } else {
            // Signed out, show unauthenticated UI.
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
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
            // handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            // showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    //  hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d("", "onConnectionFailed:" + connectionResult);
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
    public void login(View v) {
        if (emailCheck) {

            loadiconreg = (ProgressBar) findViewById(R.id.loadiconlog);
            jsonParser = new JSONParser();
            // Toast.makeText(LoginActivity.this, user.getEmail()+"    "+user.getPassword(), Toast.LENGTH_LONG).show();
            loadiconreg.setVisibility(View.VISIBLE);
            new LoginActivity.CreateUser().execute();
//temprary

        } else {
            if (language.equals("en")) {
                Toast.makeText(this, R.string.fields, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "برجاء ادخال البيانات المطلوبه", Toast.LENGTH_LONG).show();
            }
        }

    }

    public void newRegistration(View v) {
        Toast toast = Toast.makeText(this, "new registration  login will be initialized", Toast.LENGTH_LONG);
        toast.show();
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }


    class CreateUser extends AsyncTask<String, String, Boolean> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

//            loadiconreg.setVisibility(View.VISIBLE);


        }

        @Override
        protected Boolean doInBackground(String... arg) {
            // TODO Auto-generated method stub
            DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("key", "logic123"));
            params.add(new BasicNameValuePair("email", user.getEmail()));
            params.add(new BasicNameValuePair("password", user.getPassword()));
            String jsonBack = null;
            jsonParser = new JSONParser();
            try {
                jsonBack = jsonParser.makeHttpRequest(urlInsert, "GET", user.getEmail(), user.getPassword());
            } catch (IOException e) {
                e.printStackTrace();
            }

            // check for success tag
            try {

                JSONObject json = new JSONObject(jsonBack);
                int success = json.getInt("success");

                if (success == 1) {

                    User = json.getJSONArray("user");
                    JSONObject c = User.getJSONObject(0);
                    user.setId(Integer.parseInt(c.getString("id")));
                    user.setName(c.getString("name"));
                    user.setPassword(c.getString("password"));
                    user.setEmail(c.getString("email"));
                    user.setCityId(Integer.parseInt(c.getString("cityId")));
                  //  DateFormat format = new SimpleDateFormat("yyyy-MMMM-d", Locale.ENGLISH);
//                    try {
//                        Date date = format.parse(c.getString("birthdate"));
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }

                    user.setMobile(c.getString("mobile"));
                    if (c.getString("teamId").length() > 0)
                        user.setTeamId(Integer.parseInt(c.getString("teamId")));

                    user.setImage(c.getString("image"));

                    user.setAddress(c.getString("address"));
                    if (c.getString("fId").length() > 0)
                        user.setFbId(c.getString("fId"));
                        if (c.getString("googleId").length() > 0)
                            user.setGoogleId(c.getString("googleId"));
                    user.setPosition(c.getString("positionId"));


                    //store data of user in  shared prefrence


                    SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    SharedPreferences.Editor prefsEditor = mPrefs.edit();
                    Gson gson = new Gson();
                    String jsonObject = gson.toJson(user);
                    prefsEditor.putString("userObject", jsonObject);
                    Log.d("preflogin ", jsonObject);
                    prefsEditor.commit();

                    /////////////
                    String json1 = mPrefs.getString("userObject", "");
                    User obj = gson.fromJson(json1, User.class);
                    Log.i("shared", obj.getEmail());


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


            //if true
            if (result) {

                //  Toast.makeText(LoginActivity.this, "Welcome", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(LoginActivity.this, MainContainer.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);


            } else {
                loadiconreg.setVisibility(View.INVISIBLE);
                if (language.equals("en"))
                Toast.makeText(LoginActivity.this, "email or password is wrong", Toast.LENGTH_SHORT).show();
               else
                    Toast.makeText(LoginActivity.this, "مستخدم غير موجود", Toast.LENGTH_SHORT).show();

            }
            //loadiconreg.setVisibility(View.INVISIBLE);
        }
    }


    public void LoginWithFaceBook()
    {
        final ACProgressFlower flower_dialog = new ACProgressFlower.Builder(LoginActivity.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.GREEN).build();
        flower_dialog.show();
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String name = FaceName.replace(" ", "%20");
        String url ="https://logic-host.com/work/kora/beta/phpFiles/loginWithFBAPI.php?key=logic123&fid="+FaceID+"&name="+name+"&email="+FaceEmail+"&image="+FaceURL+"";
        Log.d("dfdfdf",url);
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("response",response);
                        try {
                            flower_dialog.dismiss();
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
                                Intent intent = new Intent(LoginActivity.this, MainContainer.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                                startActivity(intent);
                            } else {
                                LoginManager.getInstance().logOut();
                                //sameh
                                RegisterWithFaceBook();
                               /* Toast.makeText(LoginActivity.this, "يجب انشاء حساب اولا", Toast.LENGTH_LONG).show();
                                if (language.equals("en")) {
                                    Toast.makeText(LoginActivity.this, "register first", Toast.LENGTH_LONG).show();

                                }*/
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

    public  void LoginWithGoogle()
    {
        mGoogleApiClient.stopAutoManage(this);

        mGoogleApiClient.disconnect();
        final ACProgressFlower flower_dialog = new ACProgressFlower.Builder(LoginActivity.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.GREEN).build();
        flower_dialog.show();
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String name = Google_name .replace(" ", "%20");
        //https://logic-host.com/work/kora/beta/phpFiles/loginWithGoogleAPI.php?key=logic123&gid=321&name=googleacc&email=ttt&image=https://logic-host.com/work/mal3bak/images/salah.jpg
        String url ="https://logic-host.com/work/kora/beta/phpFiles/loginWithGoogleAPI.php?key=logic123&gid="+Google_ID+
                "&name="+name+"&email="+Google_email+"&image="+Google_Image;
Log.d("urlLoginG",url);
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        flower_dialog.dismiss();
                        try {
                            JSONObject json = new JSONObject(response);
                            int success = json.getInt("success");
                           Log.d("GoogleLogin", response);
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
                                Intent intent = new Intent(LoginActivity.this, MainContainer.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                                startActivity(intent);
                            } else {
                                //kotb
                                RegisterWithGoogle();
                                /*Toast.makeText(LoginActivity.this, "المستخدم غير موجود من فضلك اذهب للتسجيل", Toast.LENGTH_LONG).show();
                                if (language.equals("en")) {
                                    Toast.makeText(LoginActivity.this, "user isn't found, lease register first", Toast.LENGTH_LONG).show();

                                }*/

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


    public void RegisterWithFaceBook()
    {
        final ACProgressFlower flower_dialog = new ACProgressFlower.Builder(LoginActivity.this)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.GREEN).build();
        flower_dialog.show();
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String name = FaceName.replace(" ", "%20");
        String url ="https://logic-host.com/work/kora/beta/phpFiles/signUpWithFBAPI.php?key=logic123&fid="+FaceID+"&name="+name+"&email="+FaceEmail+"&image="+FaceURL+"";
        Log.d("dfdfdf",url);
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        flower_dialog.dismiss();
                        // Display the first 500 characters of the response string.
                        Log.d("response",response);
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
                                Intent intent = new Intent(LoginActivity.this, MainContainer.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                                startActivity(intent);
                            } else {
                                LoginManager.getInstance().logOut();
                                Toast.makeText(LoginActivity.this, "البريد او الهاتف موجود بالفعل", Toast.LENGTH_LONG).show();
                                if (language.equals("en")) {
                                    Toast.makeText(LoginActivity.this, "user already exist", Toast.LENGTH_LONG).show();

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

    public void RegisterWithGoogle() {
        mGoogleApiClient.stopAutoManage(this);
        mGoogleApiClient.disconnect();
        final ACProgressFlower flower_dialog = new ACProgressFlower.Builder(LoginActivity.this)
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
                                Intent intent = new Intent(LoginActivity.this, MainContainer.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this, "البريد او الهاتف موجود بالفعل", Toast.LENGTH_LONG).show();
                                if (language.equals("en")) {
                                    Toast.makeText(LoginActivity.this, "user already exist", Toast.LENGTH_LONG).show();

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

}
