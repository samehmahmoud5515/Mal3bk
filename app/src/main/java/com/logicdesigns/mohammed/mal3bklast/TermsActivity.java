package com.logicdesigns.mohammed.mal3bklast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TermsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
    }

    public void BackToRegister(View view) {
        Intent intent = new Intent(TermsActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }
}
