package com.logicdesigns.mohammed.mal3bklast.reciever;

/**
 * Created by logicDesigns on 10/30/2017.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.logicdesigns.mohammed.mal3bklast.Network.CheckNetwork;
import com.logicdesigns.mohammed.mal3bklast.R;
import com.logicdesigns.mohammed.mal3bklast.StartActivity;

import static com.facebook.FacebookSdk.getApplicationContext;


public class NetworkReceiver extends BroadcastReceiver {
    SharedPreferences sharedPref;
    String language;
    Typeface custom_Din;
 //   Dialog dialog;
    public NetworkReceiver() {
       // dialog = new Dialog(getApplicationContext(), R.style.CustomDialogTheme);

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
          try {
           //   sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
           //   language = sharedPref.getString("language", "ar");
          //    custom_Din = Typeface.createFromAsset(context.getAssets(), "fonts/DIN Next LT W23 Regular.ttf");

              CheckNetwork checkNetwork = CheckNetwork.getInstance();
              checkNetwork.setContext(context);
              boolean network = checkNetwork.isNetworkAvailable();
              if (!network) {

               /*   dialog.setContentView(R.layout.offline_popup_dialog);
                  dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

                  dialog.setCancelable(false);

                  dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                  TextView textView = (TextView) dialog.findViewById(R.id.offline_message_text);
                  Button goto_setting = (Button) dialog.findViewById(R.id.go_to_setting);
                  goto_setting.setText(R.string.tryAgain_ar);
                  Button close_button = (Button) dialog.findViewById(R.id.btn_close);

                  if (language.equals("en")) {
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
                          // startActivity(new Intent(StartActivity.this,StartActivity.class));
                          //  finish();
                          dialog.cancel();

                      }
                  });

                  close_button.setOnClickListener(new View.OnClickListener() {

                      @Override
                      public void onClick(View v) {
                          dialog.cancel();

                          // onBackPressed();

                      }
                  });
                  // now that the dialog is set up, it's time to show it
                  dialog.show();*/


                //  Toast toast = Toast.makeText(context, "No Internet Connection",
                     //     Toast.LENGTH_LONG);
               //   toast.show();
              } else {
                 // if (dialog != null)
                     // if (dialog.isShowing())
                        //  dialog.dismiss();
              }
          }catch (Exception e)
          {
              e.printStackTrace();
          }

    }
}