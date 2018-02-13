package com.logicdesigns.mohammed.mal3bklast;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import BusinessClasses.Fonts;

public class ApprovalPayFragment extends Fragment {
    Fonts fonts;
    Typeface font_awesome,dinNext,droidKufi;
    TextView prompt1_fragment_accept_approval,prompt2_fragment_accept_approval,true_icon_fragment_accept_approval;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        if (PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("langugae", "").equals("en")){
            TextView title_header_fragment= (TextView) getActivity().findViewById(R.id.title_header_fragment);
            title_header_fragment.setText("Pay Result");
            font_awesome =Typeface.createFromAsset(getActivity().getAssets(),  "fonts/fontawesome-webfont.ttf");
            dinNext =Typeface.createFromAsset(getActivity().getAssets(),  "fonts/OpenSans-Light.ttf");
            droidKufi = Typeface.createFromAsset(getActivity().getAssets(),  "fonts/OpenSans-Light.ttf");
            return inflater.inflate(
                    R.layout.fragment_accept_approval_en, container, false);




        }
        else{

            ////////////////////////alert dialog for explain
           /* AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
            builder1.setMessage("");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            builder1.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();*/
            /////////////////

            TextView title_header_fragment= (TextView) getActivity().findViewById(R.id.title_header_fragment);
            title_header_fragment.setText("نتيجة الدفع");
            font_awesome =Typeface.createFromAsset(getActivity().getAssets(),  "fonts/fontawesome-webfont.ttf");
            dinNext =Typeface.createFromAsset(getActivity().getAssets(),  "fonts/DIN Next LT W23 Regular.ttf");
            droidKufi = Typeface.createFromAsset(getActivity().getAssets(),  "fonts/DroidKufi-Regular.ttf");
            return inflater.inflate(
                    R.layout.fragment_accept_approval, container, false);


        }




    }

    @Override
    public void onStart() {
        super.onStart();
        Button go_get_player_btn;
        fonts = new Fonts(getActivity());
        go_get_player_btn= (Button)getView().findViewById(R.id.go_get_player_btn);
        prompt1_fragment_accept_approval = (TextView)getView().findViewById(R.id.prompt1_fragment_accept_approval);
        prompt2_fragment_accept_approval = (TextView)getView().findViewById(R.id.prompt2_fragment_accept_approval);
        true_icon_fragment_accept_approval = (TextView)getView().findViewById(R.id.true_icon_fragment_accept_approval);
       //  Typeface openSans = Typeface.createFromAsset(getActivity().getAssets(),  "fonts/OpenSans-Light.ttf");
        true_icon_fragment_accept_approval.setTypeface(font_awesome);
        prompt1_fragment_accept_approval.setTypeface(dinNext);
        prompt2_fragment_accept_approval.setTypeface(droidKufi);
        /*FragmentManager fm = getActivity().getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount()-1; ++i) {
            fm.popBackStack();
        }*/


        go_get_player_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment searchAboutPlayerFragment = new SearchAboutPlayerFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.placeholder2, searchAboutPlayerFragment).commit();


            }





        });







    }

}

