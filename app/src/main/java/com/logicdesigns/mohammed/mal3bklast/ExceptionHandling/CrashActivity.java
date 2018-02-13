package com.logicdesigns.mohammed.mal3bklast.ExceptionHandling;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.logicdesigns.mohammed.mal3bklast.After_SP_Activity;
import com.logicdesigns.mohammed.mal3bklast.MainContainer;
import com.logicdesigns.mohammed.mal3bklast.R;


public class CrashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash);
        ExceptionHandling exceptionHandling = new ExceptionHandling(this);
        exceptionHandling.showErrorDialogForException();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(CrashActivity.this);

                if (mPrefs.getString("userObject", "").length() > 0) {
                    Intent intent = new Intent(CrashActivity.this, MainContainer.class);
                    startActivity(intent);
                     finish();

                } else {

                    Intent intent = new Intent(CrashActivity.this, After_SP_Activity.class);
                  finish();
                    startActivity(intent);

                }
            }
        }, 500);
    }
}
