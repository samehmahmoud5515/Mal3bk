package com.logicdesigns.mohammed.mal3bklast.tasks;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.logicdesigns.mohammed.mal3bklast.Interface.AsyncResponse;
import com.logicdesigns.mohammed.mal3bklast.LoginActivity;
import com.logicdesigns.mohammed.mal3bklast.MainContainer;
import com.logicdesigns.mohammed.mal3bklast.common.URLs;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import BusinessClasses.JSONParser;
import BusinessClasses.User;

/**
 * Created by logicDesigns on 6/13/2017.
 */

public class loginTask {
    String Pass;
    String Mail;
    String JSON_USER;

    JSONArray User=null;
    User user;
    public   AsyncResponse delegate =null;

    Context mContext;

    public loginTask(Context context,String [] params)
    {
        mContext=context;

        new loginTask.EditUser().execute(params);


    }


   public class EditUser extends AsyncTask<String, String, Boolean>
    {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            // loadiconreg.setVisibility(View.VISIBLE);


        }

        @Override
        protected Boolean doInBackground(String... paramters) {




            String pass=paramters[1];
            String mail=paramters[0];
            String mobile=paramters[2];
            String name =paramters[3];


            // TODO Auto-generated method stub
            DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("key", "logic123"));

            params.add(new BasicNameValuePair("email",mail));

            params.add(new BasicNameValuePair("password",pass));
            params.add(new BasicNameValuePair("mobile",mobile));
            params.add(new BasicNameValuePair("name",name));

            JSONParser jsonParser=new JSONParser();

            JSONObject json = jsonParser.makeHttpRequest(URLs.urlUpdate,
                    "GET", params);





            // check for success tag
            try {
                int success = json.getInt("success");
                      Log.d("444444444444444",json.toString());
                if (success == 1) {

                    User =json.getJSONArray("user");
                    JSONObject c = User.getJSONObject(0);
                    Log.d("55555555555555555",c.toString());
                    Log.d("6666666666666666666",c.getString("id"));

                    JSON_USER =c.toString();




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
            if(result)
            {

//
                JSONObject c=null;
                if (JSON_USER!=null)
                    try {
                        c = new JSONObject(JSON_USER);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                Log.d("777777777777777777778",JSON_USER);
                SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
                SharedPreferences.Editor prefsEditor = mPrefs.edit();

                if (JSON_USER!=null)
                {prefsEditor.putString("userObject", JSON_USER);
                prefsEditor.apply();

                    delegate = new AsyncResponse() {
                        @Override
                        public void processFinish(Boolean output) {
                            delegate.processFinish(true);
                        }
                    };
                    delegate.processFinish(true);

                }
                else delegate.processFinish(false);



            }
            else
            {
//                Toast.makeText(LoginActivity.this, "email or password false", Toast.LENGTH_LONG).show();
            }
            //  loadiconreg.setVisibility(View.INVISIBLE);
        }
    }

}
