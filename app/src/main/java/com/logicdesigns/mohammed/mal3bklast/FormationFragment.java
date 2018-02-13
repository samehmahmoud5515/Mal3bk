package com.logicdesigns.mohammed.mal3bklast;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import BusinessClasses.FormationData;
import BusinessClasses.ListViewAdapterForSearchTraining;
import BusinessClasses.ListViewAsapterForFormation;
import BusinessClasses.ListViewAsapterForGroundRegister;
import BusinessClasses.RegisterPlaygroundData;

public class FormationFragment extends Fragment {
    ListView userList;
    ListViewAsapterForFormation userAdapter;
    ArrayList<FormationData> FormationDatas = new ArrayList<FormationData>();
    TextView formation_add_new_player,formation_btn_add_new_player,formation_icon_main_page,formation_icon_share,title_header_fragment;
    Button formation_btn_main_page,formation_btn_share;
    Typeface custom_font_Awesome ;
    Typeface custom_Din;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {




        if (PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("langugae", "").equals("en")){
            custom_font_Awesome= Typeface.createFromAsset(getActivity().getAssets(),  "fonts/fontawesome-webfont.ttf");
            custom_Din=Typeface.createFromAsset(getActivity().getAssets(),  "fonts/OpenSans-Light.ttf");
            return inflater.inflate(
                    R.layout.fragment_formation_en, container, false);
        }
        else{
            custom_font_Awesome= Typeface.createFromAsset(getActivity().getAssets(),  "fonts/fontawesome-webfont.ttf");
            custom_Din=Typeface.createFromAsset(getActivity().getAssets(),  "fonts/DIN Next LT W23 Regular.ttf");
            return inflater.inflate(
                    R.layout.fragment_formation, container, false);
        }

    }

    @Override
    public void onStart() {
        super.onStart();


        title_header_fragment= (TextView) getActivity().findViewById(R.id.title_header_fragment);
        title_header_fragment.setTypeface(custom_Din);
        FormationData formationData = new FormationData();
        if (PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("langugae", "").equals("en")){
            title_header_fragment.setText("Formation");
            formationData.setPlayerName("Ramdan Sobhy");
        }
        else{

            title_header_fragment.setText("التشكيلة");
            formationData.setPlayerName("رمضان صبحي");
        }



       // formationData.setRatingBar("4.5");
        formationData.setRatingText("4.5");
        Bitmap image;


        FormationDatas.add(formationData);
        FormationDatas.add(formationData);
        FormationDatas.add(formationData);
        FormationDatas.add(formationData);
        FormationDatas.add(formationData);
        FormationDatas.add(formationData);
        FormationDatas.add(formationData);
        if (PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("langugae", "").equals("en")){
            userAdapter = new ListViewAsapterForFormation(getActivity(), R.layout.formation_child_en,
                    FormationDatas);
        }
        else{
            userAdapter = new ListViewAsapterForFormation(getActivity(), R.layout.formation_child,
                    FormationDatas);
        }

        userList = (ListView) getView().findViewById(R.id.formation_list);
        userList.setItemsCanFocus(false);
        userList.setAdapter(userAdapter);
        formation_add_new_player=(TextView)getView().findViewById(R.id.formation_add_new_player);
        formation_add_new_player.setTypeface(custom_Din);
        formation_btn_add_new_player=(TextView)getView().findViewById(R.id.formation_btn_add_new_player);
        formation_btn_add_new_player.setTypeface(custom_font_Awesome);
        formation_icon_main_page=(TextView)getView().findViewById(R.id.formation_icon_main_page);
        formation_icon_main_page.setTypeface(custom_font_Awesome);
        formation_icon_share=(TextView)getView().findViewById(R.id.formation_icon_share);
        formation_icon_share.setTypeface(custom_font_Awesome);
        formation_btn_main_page=(Button)getView().findViewById(R.id.formation_btn_main_page);
        formation_btn_main_page.setTypeface(custom_Din);
        formation_btn_share=(Button)getView().findViewById(R.id.formation_btn_share);
        formation_btn_share.setTypeface(custom_Din);

        formation_add_new_player.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment getPlayerFragment = new GetPlayerFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.placeholder2, getPlayerFragment).commit();
            }});


    }
}
