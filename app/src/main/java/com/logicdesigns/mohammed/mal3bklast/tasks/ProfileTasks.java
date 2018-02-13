package com.logicdesigns.mohammed.mal3bklast.tasks;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.logicdesigns.mohammed.mal3bklast.ExceptionHandling.ExceptionHandling;
import com.logicdesigns.mohammed.mal3bklast.R;

import org.json.JSONException;
import org.json.JSONObject;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

/**
 * Created by logicDesigns on 10/30/2017.
 */

public class ProfileTasks {

    Context mContext;
    String language;
    Typeface dinNext;
    AlertDialog alert_createTeam;
    String userId;
    public ProfileTasks(Context context, String userId, String lang, Typeface din)
    {
        mContext = context;
        language = lang;
        dinNext =din;
        this.userId =userId;
    }

    public void openDialogcreateTeam()
    {
        AlertDialog.Builder builder_createTeam = new AlertDialog.Builder(new ContextThemeWrapper(mContext, R.style.CustomDialog));

        TextView title = new TextView(mContext);

        if (language.equals("ar"))
            title.setText("إنشاء فريق ");
        else title.setText("Create Team");
        title.setBackgroundColor(Color.parseColor("#008542"));


        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);
        title.setTypeface(dinNext);

        builder_createTeam.setCustomTitle(title);

        final EditText input = new EditText(mContext);

        input.setGravity(Gravity.CENTER);
        input.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        input.setTextColor(Color.parseColor("#2E8B57"));
        input.setHintTextColor(Color.parseColor("#2E8B57"));
        input.setTypeface(dinNext);
        String okString,cancelString;
        if (language.equals("ar"))
        {  input.setHint("اسم الفريق");
            okString = "إنشاء فريق";
            cancelString = "إلغاء";
        }
        else
        {input.setHint("Team Name");
            okString = "Create Team";
            cancelString = "Cancel";
        }

        builder_createTeam.setView(input);


        builder_createTeam.setPositiveButton(okString, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (input.getText().toString().length()>0)
                    createNewTeamAPI(input.getText().toString());
                else {
                    if (language.equals("ar"))
                    {
                        Toast.makeText(mContext, "ادخل اسم من فضلك", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(mContext, "Please enter a name", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });

        builder_createTeam.setNegativeButton(cancelString,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alert_createTeam = builder_createTeam.create();
        alert_createTeam.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button btnPositive = alert_createTeam.getButton(Dialog.BUTTON_POSITIVE);
                btnPositive.setTypeface(dinNext);

                Button btnNegative = alert_createTeam.getButton(Dialog.BUTTON_NEGATIVE);
                btnNegative.setTypeface(dinNext);
            }
        });
        alert_createTeam.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alert_createTeam.getWindow().setWindowAnimations(R.style.CustomDialog);
        alert_createTeam.getWindow().getAttributes().windowAnimations = R.style.CustomDialog;
        alert_createTeam.show();
    }
    public void createNewTeamAPI(String teamName)
    {
        teamName = teamName.replace(" ", "%20");
        final ACProgressFlower flower_dialog = new ACProgressFlower.Builder(mContext)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.GREEN).build();
        flower_dialog.show();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(mContext);
        String url ="https://logic-host.com/work/kora/beta/phpFiles/createTeamAPI.php?key=logic123&id="+userId+"&teamName="+teamName;

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            flower_dialog.dismiss();
                            JSONObject jsonObject = new JSONObject(response);
                            if (! jsonObject.isNull("success")&& jsonObject.has("success"))
                            {
                                int success = Integer.parseInt(jsonObject.getString("success"));
                                if (success==1)
                                {
                                    if(language.equals("ar"))
                                        Toast.makeText(mContext, "تم اضافة فريق ", Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(mContext, "team created", Toast.LENGTH_SHORT).show();
                                    alert_createTeam.dismiss();

                                }
                                else if (success==0)
                                {
                                    if(language.equals("ar"))
                                        Toast.makeText(mContext, "لقد إنشأت فريق من قبل ", Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(mContext, "You've created team before", Toast.LENGTH_SHORT).show();
                                }
                                else if (success==-1)
                                {
                                    if(language.equals("ar"))
                                        Toast.makeText(mContext, "اسم الفريق متواجد ", Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(mContext, "team name already taken", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                ExceptionHandling exceptionHandling = new ExceptionHandling(mContext);
                                exceptionHandling.showErrorDialog();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ExceptionHandling exceptionHandling = new ExceptionHandling(mContext);
                            exceptionHandling.showErrorDialog();
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

    public void openDialogEditTeamName(final String teamId, String oldTeamName)
    {
        AlertDialog.Builder builder_createTeam = new AlertDialog.Builder(new ContextThemeWrapper(mContext, R.style.CustomDialog));

        TextView title = new TextView(mContext);

        if (language.equals("ar"))
            title.setText("تعديل اسم الفريق");
        else title.setText("Create Team");
        title.setBackgroundColor(Color.parseColor("#008542"));


        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);
        title.setTypeface(dinNext);

        builder_createTeam.setCustomTitle(title);

        final EditText input = new EditText(mContext);

        input.setGravity(Gravity.CENTER);
        input.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        input.setTextColor(Color.parseColor("#2E8B57"));
        input.setHintTextColor(Color.parseColor("#2E8B57"));
        input.setTypeface(dinNext);
        String okString,cancelString;
        if (language.equals("ar"))
        {  input.setHint("تعديل اسم الفريق");
            okString = "تعديل اسم فريق";
            cancelString = "إلغاء";
        }
        else
        {input.setHint("Edit Team Name");
            okString = "Edit Team Name";
            cancelString = "Cancel";
        }
        input.setText(oldTeamName);

        builder_createTeam.setView(input);


        builder_createTeam.setPositiveButton(okString, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (input.getText().toString().length()>0)
                    editTeamNameAPI(input.getText().toString(),teamId);
                else {
                    if (language.equals("ar"))
                    {
                        Toast.makeText(mContext, "ادخل اسم من فضلك", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(mContext, "Please enter a new name", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });

        builder_createTeam.setNegativeButton(cancelString,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alert_createTeam = builder_createTeam.create();
        alert_createTeam.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button btnPositive = alert_createTeam.getButton(Dialog.BUTTON_POSITIVE);
                btnPositive.setTypeface(dinNext);

                Button btnNegative = alert_createTeam.getButton(Dialog.BUTTON_NEGATIVE);
                btnNegative.setTypeface(dinNext);
            }
        });
        alert_createTeam.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alert_createTeam.getWindow().setWindowAnimations(R.style.CustomDialog);
        alert_createTeam.getWindow().getAttributes().windowAnimations = R.style.CustomDialog;
        alert_createTeam.show();
    }


    public void editTeamNameAPI(String teamName, String teamId)
    {
        teamName = teamName.replace(" ", "%20");
        final ACProgressFlower flower_dialog = new ACProgressFlower.Builder(mContext)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .fadeColor(Color.GREEN).build();
        flower_dialog.show();

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(mContext);
        String url ="https://logic-host.com/work/kora/beta/phpFiles/editTeamAPI.php?key=logic123&id="+teamId+"&newname="+teamName+"&pid="+userId;

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            flower_dialog.dismiss();
                            JSONObject jsonObject = new JSONObject(response);
                            if (! jsonObject.isNull("success")&& jsonObject.has("success"))
                            {
                                int success = Integer.parseInt(jsonObject.getString("success"));
                                if (success==1)
                                {
                                    if(language.equals("ar"))
                                        Toast.makeText(mContext, "تم تعديل اسم الفريق ", Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(mContext, "team name edited", Toast.LENGTH_SHORT).show();
                                    alert_createTeam.dismiss();

                                }
                                else {
                                    ExceptionHandling exceptionHandling = new ExceptionHandling(mContext);
                                    exceptionHandling.showErrorDialog();
                                }
                            }
                            else {
                                ExceptionHandling exceptionHandling = new ExceptionHandling(mContext);
                                exceptionHandling.showErrorDialog();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            ExceptionHandling exceptionHandling = new ExceptionHandling(mContext);
                            exceptionHandling.showErrorDialog();
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
