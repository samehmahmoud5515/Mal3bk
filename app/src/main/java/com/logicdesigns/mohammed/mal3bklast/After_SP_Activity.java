package com.logicdesigns.mohammed.mal3bklast;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

public class After_SP_Activity extends AppCompatActivity {

    Button login_btn;
    Button registeration_btn;
    Typeface custom_font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after__sp);
        login_btn = (Button) findViewById(R.id.login_btn_After_sp);
        registeration_btn = (Button) findViewById(R.id.registeration_btn_After_sp);
        login_btn.setTypeface(custom_font);
        registeration_btn.setTypeface(custom_font);
        SharedPreferences  sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String language = sharedPref.getString("language","");
        if (language.equals("en")) {
            custom_font = Typeface.createFromAsset(getAssets(), "fonts/OpenSans-Light.ttf");
            English();
        } else {
            custom_font = Typeface.createFromAsset(getAssets(), "fonts/DIN Next LT W23 Regular.ttf");
            login_btn.setTypeface(custom_font);
            registeration_btn.setTypeface(custom_font);
        }
    }

    public void English(){
        login_btn.setText(R.string.mainLogin);
        registeration_btn.setText(R.string.mainRegister);
    }

    public void loginSPBtn(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }

    public void registerSPBtn(View v) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }
}
