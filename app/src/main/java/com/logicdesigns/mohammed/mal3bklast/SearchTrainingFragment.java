package com.logicdesigns.mohammed.mal3bklast;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;


import BusinessClasses.ListViewAdapterForSearchTraining;

import BusinessClasses.SearchTrainingData;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SearchTrainingFragment extends Fragment {

    ListView userList;
    ListViewAdapterForSearchTraining userAdapter;
    ArrayList<SearchTrainingData> searchTrainingDatas_List = new ArrayList<SearchTrainingData>();
    TextView title_header_fragment;
    Typeface custom_font_Awesome;
    Typeface custom_Din;
    SharedPreferences sharedPref;
    String language = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        language = sharedPref.getString("language", "ar");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_training, container, false);
        if (language.equals("en")) {
            TextView title_header_fragment = (TextView) getActivity().findViewById(R.id.title_header_fragment);
            title_header_fragment.setText("Search Result");
            custom_Din = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Light.ttf");
        } else {
            TextView title_header_fragment = (TextView) getActivity().findViewById(R.id.title_header_fragment);
            title_header_fragment.setText("نتائج البحث");
            custom_Din = Typeface.createFromAsset(getActivity().getAssets(), "fonts/DIN Next LT W23 Regular.ttf");
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        custom_font_Awesome = Typeface.createFromAsset(getActivity().getAssets(), "fonts/fontawesome-webfont.ttf");
        searchTrainingDatas_List = new ArrayList<SearchTrainingData>();
        String trainingJson = getArguments().getString("trainingJson");
        String selected_date = getArguments().getString("selected_date");
        String selected_start_time = getArguments().getString("selected_start_time");
        String selected_end_time = getArguments().getString("selected_end_time");

        SearchTrainingData obj = new SearchTrainingData(Integer.parseInt(selected_start_time), Integer.parseInt(selected_end_time));
        try {
            searchTrainingDatas_List = obj.ParseJson(trainingJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        title_header_fragment = (TextView) getActivity().findViewById(R.id.title_header_fragment);
        title_header_fragment.setTypeface(custom_Din);
        SearchTrainingData searchTrainingData = new SearchTrainingData();
        if (PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("langugae", "").equals("en")) {
            title_header_fragment.setText("Search About Training");
            searchTrainingData.setTeamName("Shabab Team");
            searchTrainingData.setRatingBar("4.5");
            searchTrainingData.setRating("4.5");
            searchTrainingData.setPlaygroundName("Nour Playground");
            searchTrainingData.setTownName("Gada");

        } else {


        }


        Bitmap image;



        if (language.equals("ar"))
        userAdapter = new ListViewAdapterForSearchTraining(getActivity(), R.layout.search_training_child, searchTrainingDatas_List, selected_date);
     else   userAdapter = new ListViewAdapterForSearchTraining(getActivity(), R.layout.search_training_child_en, searchTrainingDatas_List, selected_date);

        userList = (ListView) getView().findViewById(R.id.search_training_list);
        userList.setItemsCanFocus(false);
        userList.setAdapter(userAdapter);


    }

    @Override
    public void onDestroyView() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        int count =manager.getBackStackEntryCount();
        if (count ==0) {
            MainContainer.headerNum = 4;
            FragmentTransaction ft1 = getActivity().getSupportFragmentManager().beginTransaction();
            ft1.replace(R.id.placeholder1, new HeaderFragment())
                    .commit();
        }
        super.onDestroyView();

    }
}
