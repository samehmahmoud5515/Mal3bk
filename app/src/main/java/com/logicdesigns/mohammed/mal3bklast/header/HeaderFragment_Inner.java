package com.logicdesigns.mohammed.mal3bklast.header;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.logicdesigns.mohammed.mal3bklast.After_SP_Activity;
import com.logicdesigns.mohammed.mal3bklast.FilterSearchPlayerFragment;
import com.logicdesigns.mohammed.mal3bklast.Main2Activity;
import com.logicdesigns.mohammed.mal3bklast.MainContainer;
import com.logicdesigns.mohammed.mal3bklast.R;
import com.logicdesigns.mohammed.mal3bklast.SharedPref.PrefManager;
import com.logicdesigns.mohammed.mal3bklast.common.FragmentsTags;

public class HeaderFragment_Inner extends Fragment {
    TextView title_header_fragment, filter_header_fragment;
    Typeface custom_Din, custom_font_awesome;
    ImageButton notification_icon_header, backHeader,message_icon_header;
    SharedPreferences sharedPref;
    String language = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view ;
        if (language.equals("en")) {
            view = inflater.inflate(R.layout.header_fragment_en, container, false);
            custom_Din = Typeface.createFromAsset(getActivity().getAssets(), "fonts/OpenSans-Light.ttf");
        } else {
            view = inflater.inflate(R.layout.activity_header_fragment, container, false);
            custom_Din = Typeface.createFromAsset(getActivity().getAssets(), "fonts/DIN Next LT W23 Regular.ttf");
        }
        title_header_fragment = (TextView) view.findViewById(R.id.title_header_fragment);
        RelativeLayout relative_layout_message = view.findViewById(R.id.relative_layout_message);
        relative_layout_message.setVisibility(View.VISIBLE);
        RelativeLayout relative_layout_notification= view.findViewById(R.id.relative_layout_notification);
        relative_layout_notification.setVisibility(View.GONE);
        return view;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        language = sharedPref.getString("language", "ar");
    }

    @Override
    public void onStart() {
        super.onStart();
        title_header_fragment = (TextView) getView().findViewById(R.id.title_header_fragment);
        language = sharedPref.getString("language", "ar");
      if (language.equals("ar")) {
          if (MainContainer.pageNumber == 1.3)
          {
            //  title_header_fragment.setText("طرق الدفع");
              //return;
          }
      if (MainContainer.headerNum == 1) {
       //   title_header_fragment.setText("الملعب");
      } else if (MainContainer.headerNum == 2) {
          title_header_fragment.setText("الملاعب");
      } else if (MainContainer.headerNum == 3) {
          title_header_fragment.setText("الاعبين");
      } else if (MainContainer.headerNum == 4) {
          title_header_fragment.setText("تمرين");
      }
      else if (MainContainer.headerNum == 99){
          title_header_fragment.setText("طرق الدفع");
      }
      else  if (MainContainer.headerNum == 7)
      {
          title_header_fragment.setText("اضافة ملعب");
      }
  }

  else {
          if (MainContainer.pageNumber == 1.3)
          {

          }
      if (MainContainer.headerNum == 1) {
     //     title_header_fragment.setText("Playground");
      } else if (MainContainer.headerNum == 2) {
          title_header_fragment.setText("Playgrounds");
      } else if (MainContainer.headerNum == 3) {
          title_header_fragment.setText("Player(s)");
      } else if (MainContainer.headerNum == 4) {
          title_header_fragment.setText("Training");
      }
      else if (MainContainer.headerNum == 99){
          title_header_fragment.setText("Payment Methods");
      }
      else if (MainContainer.headerNum == 7)
      {
          title_header_fragment.setText("Add playground");
      }
  }
        custom_font_awesome = Typeface.createFromAsset(getActivity().getAssets(), "fonts/fontawesome-webfont.ttf");
        title_header_fragment = (TextView) getActivity().findViewById(R.id.title_header_fragment);
        filter_header_fragment = (TextView) getActivity().findViewById(R.id.filter_header_fragment);
        notification_icon_header = (ImageButton) getActivity().findViewById(R.id.notification_icon_header1);
        backHeader = (ImageButton) getActivity().findViewById(R.id.logoutHeader1);
        message_icon_header= (ImageButton) getActivity().findViewById(R.id.message_icon_header);
        backHeader.setImageResource(R.drawable.ic_back);

        if (language.equals("en")) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) notification_icon_header.getLayoutParams();
            params.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            notification_icon_header.setLayoutParams(params);

            RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) filter_header_fragment.getLayoutParams();
            params1.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            filter_header_fragment.setLayoutParams(params1);

            RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) backHeader.getLayoutParams();
            params2.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
            params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            backHeader.setLayoutParams(params2);
        }

        title_header_fragment.setTypeface(custom_Din);
        filter_header_fragment.setTypeface(custom_font_awesome);
        filter_header_fragment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment filterSearchPlayerFragment = new FilterSearchPlayerFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.search_about_player_fragment, filterSearchPlayerFragment).addToBackStack(null).commit();
                filter_header_fragment.setTextColor(Color.parseColor("#008542"));

            }
        });


        backHeader.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            getActivity().onBackPressed();

            }
        });
        notification_icon_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Main2Activity.class);
                intent.putExtra(FragmentsTags.FragemntTag, FragmentsTags.NotificationFragment);
                getActivity().startActivity(intent);
            }
        });
        message_icon_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), Main2Activity.class);
                intent.putExtra(FragmentsTags.FragemntTag, FragmentsTags.inboxFragment);
                getActivity().startActivity(intent);
            }
        });
    }
    public static void setPlaygroundName(String name)
    {

    }
}
