package com.logicdesigns.mohammed.mal3bklast;

import android.app.Dialog;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.logicdesigns.mohammed.mal3bklast.ExceptionHandling.ExceptionHandling;
import com.logicdesigns.mohammed.mal3bklast.JSON_PARSER.Playground_Search_reservation;
import com.logicdesigns.mohammed.mal3bklast.adapters.Playground_ListView_Adapter;
import com.logicdesigns.mohammed.mal3bklast.common.URLs;
import com.logicdesigns.mohammed.mal3bklast.fragments.ReservePlaygroundContainer;
import com.logicdesigns.mohammed.mal3bklast.header.HeaderFragment_Inner;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import BusinessClasses.JSONParser;
import BusinessClasses.ListViewAsapterForGroundRegister;
import BusinessClasses.RegisterPlaygroundData;

public class ViewResultPlaygroundFragment extends Fragment {
    ListView userList;
    String date;
    String townId;
    String fromTime;
    String toTime,playgroundTypeId;

    String selected_date,selected_from_time,selected_to_time;


    JSONParser jsonParser;
    JSONArray User=null;


    Playground_ListView_Adapter userAdapter;
    ArrayList<RegisterPlaygroundData> registerPlaygroundDatas = new ArrayList<RegisterPlaygroundData>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_result, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        registerPlaygroundDatas = new ArrayList<RegisterPlaygroundData>();
        userList = (ListView) getView().findViewById(R.id.listViewSearchPlayground);
        userList.setItemsCanFocus(false);



        selected_date= getArguments().getString("date");
        selected_from_time=getArguments().getString("start_time");
        selected_to_time=getArguments().getString("end_time");

        jsonParser = new JSONParser();

         String jsonString = getArguments().getString("playgroundJsonResults");
        ExceptionHandling exceptionHandling = new ExceptionHandling(getActivity());

        try {
            JSONObject json =new JSONObject(jsonString);
            int success = json.getInt("success");

            if (success == 1) {

                Playground_Search_reservation playgroundSearchReservation =new Playground_Search_reservation(getActivity(),selected_from_time,selected_to_time);

                ArrayList<Playground_Search_reservation> playgroundsList=
                        playgroundSearchReservation.Parse(jsonString);
                if (playgroundsList==null)
                {


                }
                else {
                    userAdapter = new Playground_ListView_Adapter(getActivity(), R.layout.result_playground_content_list, playgroundsList, selected_date);
                    userList.setAdapter(userAdapter);
                }



            } else {
                Log.i("test view","false");
                 exceptionHandling.showErrorDialog();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            exceptionHandling.showErrorDialog();

        }

    }


    @Override
    public void onDestroyView() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        int count =manager.getBackStackEntryCount();
        if (count ==0) {
            MainContainer.headerNum = 2;
            FragmentTransaction ft1 = getActivity().getSupportFragmentManager().beginTransaction();
            ft1.replace(R.id.placeholder1, new HeaderFragment())
                    .commit();
        }
        super.onDestroyView();

    }
}

