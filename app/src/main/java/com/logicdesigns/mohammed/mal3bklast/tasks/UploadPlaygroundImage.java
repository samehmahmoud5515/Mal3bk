package com.logicdesigns.mohammed.mal3bklast.tasks;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.logicdesigns.mohammed.mal3bklast.R;
import com.logicdesigns.mohammed.mal3bklast.SharedPref.PrefManager;

/**
 * Created by logicDesigns on 1/24/2018.
 */

public class UploadPlaygroundImage {
    Context context;
    Typeface DINNext;
    String language;
    Dialog UploadImagedialog;
    private static final int REQUEST_WRITE_PERMISSION = 786;

    public UploadPlaygroundImage(Context context)
    {this.context = context;
        PrefManager prefManager = new PrefManager(context);
        language = prefManager.getLanguage();
    }

    public void showDialog(String arString,String enString)
    {
        final Activity mActivity = (Activity) context;
        DINNext = Typeface.createFromAsset(mActivity.getAssets(), "fonts/DIN Next LT W23 Regular.ttf");

        final Dialog dialog = new Dialog(mActivity, R.style.CustomDialogTheme);
        dialog.setContentView(R.layout.no_playgrounds_custom_dialog);
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
                //  mActivity.onBackPressed();
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

            }
        });

        dialog.show();
    }


}
