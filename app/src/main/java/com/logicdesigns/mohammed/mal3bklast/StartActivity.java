package com.logicdesigns.mohammed.mal3bklast;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.logicdesigns.mohammed.mal3bklast.ExceptionHandling.ExceptionHandler;
import com.logicdesigns.mohammed.mal3bklast.Network.CheckNetwork;
import com.logicdesigns.mohammed.mal3bklast.SharedPref.PrefManager;
import com.logicdesigns.mohammed.mal3bklast.tasks.GetCitiesTask;
import com.logicdesigns.mohammed.mal3bklast.tasks.GetPlayerPostions;
import com.logicdesigns.mohammed.mal3bklast.tasks.GetPlaygroundTypesTask;
import com.logicdesigns.mohammed.mal3bklast.tasks.PlaygroundFacilities_Task;
import com.logicdesigns.mohammed.mal3bklast.tasks.RefreshUserInforamtion;

import org.androidannotations.annotations.sharedpreferences.SharedPref;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import BusinessClasses.User;
import me.leolin.shortcutbadger.ShortcutBadger;


public class StartActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private TextView textView;
    private Handler handler = new Handler();
    Typeface custom_Din, custom_Droid;
    private SharedPreferences sharedPref;
    private String language = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        if (sharedPref.getString("userObject","").equals(""))
            ShortcutBadger.removeCount(this);

        PrefManager prefManager = new PrefManager(getApplicationContext());
        String Isfirst = prefManager.getFirstTime();
        if (Isfirst.equals("1"))
        {
           prefManager.setFirstTime("0");
            prefManager.setLanguage("ar");
        }


        language = sharedPref.getString("language", "ar");

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        custom_Din = Typeface.createFromAsset(getApplication().getAssets(), "fonts/DIN Next LT W23 Regular.ttf");
        int color = Color.parseColor("#567266");
        progressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        progressBar.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        final CheckNetwork cN = CheckNetwork.getInstance();
        cN.setContext(getApplicationContext());

        GetCitiesTask getCitiesTask = new GetCitiesTask(this);
        GetPlaygroundTypesTask getPlaygroundTypesTask = new GetPlaygroundTypesTask(this);
        PlaygroundFacilities_Task task = new PlaygroundFacilities_Task(this);
        GetPlayerPostions GetPlayerPostions_task = new GetPlayerPostions(this);

//        new Instabug.Builder(getApplication(), "910a73a7277720627d1bf4325c71f562")
//                .setInvocationEvent(InstabugInvocationEvent.SHAKE)
//                .build();

        //Long operation by thread
        new Thread(new Runnable() {
            public void run() {


                while (progressStatus < 100) {
                    progressStatus += 10;
                    //Update progress bar with completion of operation
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);
                            /*RefreshUserInforamtion refreshUserInforamtion = new
                                    RefreshUserInforamtion(StartActivity.this);*/


                        }
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        //Just to display the progress slowly
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

                final Activity mActivity = StartActivity.this;

                mActivity.runOnUiThread(new Runnable() {
                    public void run() {
                        if (!cN.isNetworkAvailable()) {
                            final Dialog dialog = new Dialog(mActivity, R.style.CustomDialogTheme);
                            dialog.setContentView(R.layout.offline_popup_dialog);


                            dialog.setCancelable(false);

                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                            TextView textView = (TextView) dialog.findViewById(R.id.offline_message_text);
                            Button goto_setting = (Button) dialog.findViewById(R.id.go_to_setting);
                            goto_setting.setText(R.string.tryAgain_ar);
                            Button close_button = (Button) dialog.findViewById(R.id.btn_close);

                            if(language.equals("en")){
                                textView.setText(R.string.internet);
                                goto_setting.setText(R.string.tryAgain_en);
                                close_button.setText(R.string.close);
                            }


                            textView.setTypeface(custom_Din);
                            goto_setting.setTypeface(custom_Din);
                            close_button.setTypeface(custom_Din);
                            //                        textView.setTypeface(custom_Din);
                            goto_setting.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                //    startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
                                   startActivity(new Intent(StartActivity.this,StartActivity.class));
                                    finish();
                                    dialog.cancel();

                                }
                            });

                            close_button.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    dialog.cancel();

                                    onBackPressed();

                                }
                            });
                            // now that the dialog is set up, it's time to show it
                            dialog.show();
                        }
                    }
                });


                //for 1.1.4+

                ////
                SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(StartActivity.this);
                /////////////
                if (cN.isNetworkAvailable()) {
                    if (mPrefs.getString("userObject", "").length() > 0) {
                        Intent intent = new Intent(StartActivity.this, MainContainer.class);
                        startActivity(intent);
                        finish();

                    } else {

                        Intent intent = new Intent(StartActivity.this, After_SP_Activity.class);
                        finish();
                        startActivity(intent);

                    }
                }

                PrefManager prefManager = new PrefManager(StartActivity.this);
               String lang= prefManager.getLanguage();
                if (lang.equals(""))
                {
                    prefManager.setLanguage("ar");
                }




            }
        }).start();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
