package com.logicdesigns.mohammed.mal3bklast.ExceptionHandling;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.logicdesigns.mohammed.mal3bklast.After_SP_Activity;
import com.logicdesigns.mohammed.mal3bklast.MainContainer;
import com.logicdesigns.mohammed.mal3bklast.R;
import com.logicdesigns.mohammed.mal3bklast.StartActivity;

/**
 * Created by logicDesigns on 9/24/2017.
 */

public class ExceptionHandling {

    Context _context;
    Typeface DINNext;
    String language = null;
    public ExceptionHandling(Context context)
    {
        _context = context;
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        language = sharedPref.getString("language", "");
    }
    public void showErrorDialog()
    {
        final Activity mActivity = (Activity) _context;
        DINNext = Typeface.createFromAsset(mActivity.getAssets(), "fonts/DIN Next LT W23 Regular.ttf");

        final Dialog dialog = new Dialog(mActivity, R.style.CustomDialogTheme);
        dialog.setContentView(R.layout.no_playgrounds_custom_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView no_message_text = (TextView) dialog.findViewById(R.id.no_message_text);
        no_message_text.setTypeface(DINNext);

        Button close_button = (Button) dialog.findViewById(R.id.btn_close_no_playground);
        close_button.setTypeface(DINNext);

        if (language.equals("en")) {
            close_button.setText(R.string.close);
            no_message_text.setText(R.string.error_happen_en);

        }
        else{
            close_button.setText(R.string.close_ar);
            no_message_text.setText(R.string.error_happen_ar);
        }
        close_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.cancel();
                mActivity.onBackPressed();


            }
        });
        dialog.show();
    }
    public void showErrorDialogForException()
    {
        final Activity mActivity = (Activity) _context;
        DINNext = Typeface.createFromAsset(mActivity.getAssets(), "fonts/DIN Next LT W23 Regular.ttf");

        final Dialog dialog = new Dialog(mActivity, R.style.CustomDialogTheme);
        dialog.setContentView(R.layout.no_playgrounds_custom_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView no_message_text = (TextView) dialog.findViewById(R.id.no_message_text);
        no_message_text.setTypeface(DINNext);

        Button close_button = (Button) dialog.findViewById(R.id.btn_close_no_playground);
        close_button.setTypeface(DINNext);

        if (language.equals("en")) {
            close_button.setText(R.string.close);
            no_message_text.setText(R.string.error_happen_en);

        }
        else{
            close_button.setText(R.string.close_ar);
            no_message_text.setText(R.string.error_happen_ar);
        }
        close_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.cancel();
                SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(mActivity);

                if (mPrefs.getString("userObject", "").length() > 0) {
                    Intent intent = new Intent(mActivity, MainContainer.class);
                    mActivity.startActivity(intent);
                    mActivity. finish();

                } else {

                    Intent intent = new Intent(mActivity, After_SP_Activity.class);
                    mActivity.finish();
                    mActivity.startActivity(intent);

                }

            }
        });
        dialog.show();
    }
      public void showErrorDialog(String arString,String enString)
      {
          final Activity mActivity = (Activity) _context;
          DINNext = Typeface.createFromAsset(mActivity.getAssets(), "fonts/DIN Next LT W23 Regular.ttf");

          final Dialog dialog = new Dialog(mActivity, R.style.CustomDialogTheme);
          dialog.setContentView(R.layout.no_playgrounds_custom_dialog);
          dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
          dialog.setCancelable(false);
          dialog.setCanceledOnTouchOutside(false);

          dialog.setCancelable(true);
          dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

          TextView no_message_text = (TextView) dialog.findViewById(R.id.no_message_text);
          no_message_text.setTypeface(DINNext);

          Button close_button = (Button) dialog.findViewById(R.id.btn_close_no_playground);
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
                //  mActivity.onBackPressed();


              }
          });
          dialog.show();
      }
    public void showErrorDialog(String arString, String enString, final int back)
    {
        final Activity mActivity = (Activity) _context;
        DINNext = Typeface.createFromAsset(mActivity.getAssets(), "fonts/DIN Next LT W23 Regular.ttf");

        final Dialog dialog = new Dialog(mActivity, R.style.CustomDialogTheme);
        dialog.setContentView(R.layout.no_playgrounds_custom_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView no_message_text = (TextView) dialog.findViewById(R.id.no_message_text);
        no_message_text.setTypeface(DINNext);

        Button close_button = (Button) dialog.findViewById(R.id.btn_close_no_playground);
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
                if (back==1)
                 mActivity.onBackPressed();


            }
        });
        dialog.show();
    }

}
