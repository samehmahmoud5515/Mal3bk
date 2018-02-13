package com.logicdesigns.mohammed.mal3bklast;

import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import BusinessClasses.Fonts;

public class RefusePayFragment extends Fragment {
    Fonts fonts;
    TextView prompt1_fragment_refuse_pay,prompt2_fragment_refuse_pay,false_icon_fragment_refuse_pay,arrow_fragment_refuse_pay;
    Button btn_fragment_refuse_pay;
    Typeface font_awesome;
    Typeface dinNext;
    Typeface droidKufi ;

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
                    R.layout.fragment_refuse_pay_en, container, false);}
        else{
            TextView title_header_fragment= (TextView) getActivity().findViewById(R.id.title_header_fragment);
            title_header_fragment.setText("نتيجة الدفع");
             font_awesome =Typeface.createFromAsset(getActivity().getAssets(),  "fonts/fontawesome-webfont.ttf");
             dinNext =Typeface.createFromAsset(getActivity().getAssets(),  "fonts/DIN Next LT W23 Regular.ttf");
             droidKufi = Typeface.createFromAsset(getActivity().getAssets(),  "fonts/DroidKufi-Regular.ttf");
            return inflater.inflate(
                    R.layout.fragment_refuse_pay, container, false);
        }





    }

    @Override
    public void onStart() {
        super.onStart();
        fonts = new Fonts(getActivity());
        false_icon_fragment_refuse_pay =(TextView)getView().findViewById(R.id.false_icon_fragment_refuse_pay);
        prompt1_fragment_refuse_pay = (TextView)getView().findViewById(R.id.prompt1_fragment_refuse_pay);
        prompt2_fragment_refuse_pay = (TextView)getView().findViewById(R.id.prompt2_fragment_refuse_pay);

        btn_fragment_refuse_pay=(Button)getView().findViewById(R.id.btn_fragment_refuse_pay);
        arrow_fragment_refuse_pay=(TextView)getView().findViewById(R.id.arrow_fragment_refuse_pay);
        Typeface font_awesome =Typeface.createFromAsset(getActivity().getAssets(),  "fonts/fontawesome-webfont.ttf");
        Typeface dinNext =Typeface.createFromAsset(getActivity().getAssets(),  "fonts/DIN Next LT W23 Regular.ttf");
        Typeface droidKufi = Typeface.createFromAsset(getActivity().getAssets(),  "fonts/DroidKufi-Regular.ttf");
      //  Typeface openSans = Typeface.createFromAsset(getActivity().getAssets(),  "fonts/OpenSans-Light.ttf");
        false_icon_fragment_refuse_pay.setTypeface(font_awesome);
        prompt1_fragment_refuse_pay.setTypeface(dinNext);
        prompt2_fragment_refuse_pay.setTypeface(droidKufi);
        btn_fragment_refuse_pay.setTypeface(dinNext);
        arrow_fragment_refuse_pay.setTypeface(font_awesome);










    }

}

