package com.logicdesigns.mohammed.mal3bklast;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.gson.Gson;
import com.logicdesigns.mohammed.mal3bklast.SharedPref.PrefManager;
import com.logicdesigns.mohammed.mal3bklast.common.FragmentsTags;
import com.logicdesigns.mohammed.mal3bklast.tasks.NotificationBadgeTask;

import BusinessClasses.User;
import me.leolin.shortcutbadger.ShortcutBadger;

public class HeaderFragment extends Fragment {
    TextView title_header_fragment, filter_header_fragment;
    Typeface custom_Din, custom_font_awesome;
    ImageButton notification_icon_header, logoutHeader,message_icon_header;
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
        relative_layout_message.setVisibility(View.GONE);
        RelativeLayout relative_layout_notification= view.findViewById(R.id.relative_layout_notification);
        relative_layout_notification.setVisibility(View.VISIBLE);


        return view;
    }

    TextView badge_notifiaction;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        language = sharedPref.getString("language", "ar");
    }

    @Override
    public void onStart() {
        super.onStart();

        badge_notifiaction = (TextView) getView().findViewById(R.id.badge_notification);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String json1 = mPrefs.getString("userObject", "");
        Gson gson = new Gson();
        User user_obj = gson.fromJson(json1, User.class);
        String user_id = String.valueOf(user_obj.getId());
        NotificationBadgeTask notificationBadgeTask =
                new NotificationBadgeTask(getActivity(),user_id,badge_notifiaction,5);

        NotificationBadgeTask notificationBadgeTaska = new NotificationBadgeTask(getActivity(),user_id,
                new TextView(getActivity()),2);

         if (language.equals("ar")) {
             if (MainContainer.headerNum == 1) {
                 title_header_fragment.setText("اخر العروض");
                 MainContainer.headerNum = -1;
             } else if (MainContainer.headerNum == 2) {
                 title_header_fragment.setText("حجز ملعب");
                 MainContainer.headerNum = -1;
             } else if (MainContainer.headerNum == 3) {
                 title_header_fragment.setText("البحث عن لاعب");
                 MainContainer.headerNum = -1;
             } else if (MainContainer.headerNum == 4) {
                 title_header_fragment.setText("البحث عن تمرين");
                 MainContainer.headerNum = -1;
             }
         }
        else {
             if (MainContainer.headerNum == 1) {
                 title_header_fragment.setText("Latest Offers");
                 MainContainer.headerNum = -1;
             } else if (MainContainer.headerNum == 2) {
                 title_header_fragment.setText("Reserve Playground");
                 MainContainer.headerNum = -1;
             } else if (MainContainer.headerNum == 3) {
                 title_header_fragment.setText("Search For Player");
                 MainContainer.headerNum = -1;
             } else if (MainContainer.headerNum == 4) {
                 title_header_fragment.setText("Search For Training");
                 MainContainer.headerNum = -1;
             }
         }
        custom_font_awesome = Typeface.createFromAsset(getActivity().getAssets(), "fonts/fontawesome-webfont.ttf");
        title_header_fragment = (TextView) getActivity().findViewById(R.id.title_header_fragment);
        filter_header_fragment = (TextView) getActivity().findViewById(R.id.filter_header_fragment);
        notification_icon_header = (ImageButton) getActivity().findViewById(R.id.notification_icon_header1);
        logoutHeader = (ImageButton) getActivity().findViewById(R.id.logoutHeader1);
        message_icon_header= (ImageButton) getActivity().findViewById(R.id.message_icon_header);

        if (language.equals("en")) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) notification_icon_header.getLayoutParams();
            params.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            notification_icon_header.setLayoutParams(params);

            RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) filter_header_fragment.getLayoutParams();
            params1.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            filter_header_fragment.setLayoutParams(params1);

            RelativeLayout.LayoutParams params2 = (RelativeLayout.LayoutParams) logoutHeader.getLayoutParams();
            params2.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
            params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            logoutHeader.setLayoutParams(params2);
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


        logoutHeader.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ShortcutBadger.removeCount(getActivity());
                SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                prefsEditor.putString("userObject", "");
                prefsEditor.commit();
                PrefManager prefManager = new PrefManager(getActivity());
                // prefManager.setUserImage("");
                prefManager.setUserName("");
                prefManager.setUSERMAIL("");

                Intent intent = new Intent(getActivity(), After_SP_Activity.class);
                startActivity(intent);
                getActivity().finish();
                LoginManager.getInstance().logOut();

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

                TextView badge_messages = (TextView) getActivity().findViewById(R.id.badge_messages);
                badge_messages.setVisibility(View.GONE);
                Intent intent = new Intent(getActivity(), Main2Activity.class);
                intent.putExtra(FragmentsTags.FragemntTag, FragmentsTags.inboxFragment);
                getActivity().startActivity(intent);
            }
        });
    }
}
