package com.logicdesigns.mohammed.mal3bklast;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.logicdesigns.mohammed.mal3bklast.header.HeaderFragment_Inner;

import BusinessClasses.Fonts;


public class PayFragment extends Fragment {


    SharedPreferences sharedPref;
    String language = null;

    TextView title_pay,cc_tv ,end_tv,csv_tv;
    EditText cc_editText,endMonth_editText,endYear_editText,csv_editText;
    Button btn_fragment_pay;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        language = sharedPref.getString("language", "ar");

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pay, container, false);
        title_pay = (TextView) view.findViewById(R.id.title_pay);
        cc_tv = (TextView) view.findViewById(R.id.cc_tv);
        end_tv = (TextView) view.findViewById(R.id.end_tv);
        csv_tv = (TextView) view.findViewById(R.id.csv_tv);
        cc_editText = (EditText) view.findViewById(R.id.cc_editText);
        endMonth_editText = (EditText) view.findViewById(R.id.endMonth_editText);
        endYear_editText = (EditText) view.findViewById(R.id.endYear_editText);
        csv_editText = (EditText) view.findViewById(R.id.csv_editText);
        btn_fragment_pay = (Button) view.findViewById(R.id.btn_fragment_pay);
        Fonts fonts = new Fonts(getActivity());
        if (language.equals("ar"))
        {
            title_pay.setTypeface(fonts.getDinNext());
            cc_tv.setTypeface(fonts.getDinNext());
            end_tv.setTypeface(fonts.getDinNext());
            csv_tv.setTypeface(fonts.getDinNext());
            cc_editText.setTypeface(fonts.getDinNext());
            endMonth_editText .setTypeface(fonts.getDinNext());
            btn_fragment_pay .setTypeface(fonts.getDinNext());
                    csv_editText.setTypeface(fonts.getDinNext());
            endYear_editText.setTypeface(fonts.getDinNext());
        }
        else{
            title_pay.setTypeface(fonts.getOpenSans());
            cc_tv.setTypeface(fonts.getOpenSans());
            end_tv.setTypeface(fonts.getOpenSans());
            csv_tv.setTypeface(fonts.getOpenSans());
            cc_editText.setTypeface(fonts.getOpenSans());
            endMonth_editText .setTypeface(fonts.getOpenSans());
            btn_fragment_pay .setTypeface(fonts.getOpenSans());
            csv_editText.setTypeface(fonts.getOpenSans());
            endYear_editText.setTypeface(fonts.getOpenSans());
            title_pay.setText(R.string.payMethods);
            cc_tv.setText(R.string.cardNumber);
            cc_editText.setText(R.string.cardNumber);
            end_tv.setText("Validation Date");
            endMonth_editText.setText("M");
            endYear_editText.setText("Y");
            csv_editText.setText("CSV");
            btn_fragment_pay.setText("Pay");


        }

        return view;
    }


}

