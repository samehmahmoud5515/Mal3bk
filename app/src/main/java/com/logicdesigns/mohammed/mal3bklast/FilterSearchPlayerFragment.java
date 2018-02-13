
package com.logicdesigns.mohammed.mal3bklast;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.logicdesigns.mohammed.mal3bklast.JSON_PARSER.Player_Properties;
import com.logicdesigns.mohammed.mal3bklast.SharedPref.PrefManager;
import com.logicdesigns.mohammed.mal3bklast.adapters.DialogRatingAdapter;

import org.json.JSONException;

import static com.facebook.FacebookSdk.getApplicationContext;


public class FilterSearchPlayerFragment extends Fragment {
    TextView speed_icon_fragment_filter_search_players_box, speed_header_fragment_filter_search_players_box, skill_icon_fragment_filter_search_players_box, skill_heasder_fragment_filter_search_players_box, shoot_icon_fragment_filter_search_players_box, shoot_header_fragment_filter_search_players_box;
    Button btn_fragment_filter_search_players_box;
    Typeface customFontAwesome, custom2;
    // Spinner speed_spinner_fragment_filter_search_players_box,skill_spinner_fragment_filter_search_players_box,shoot_spinner_fragment_filter_search_players_box;
    Button speed_filter_btn, skill_filter_btn, shooting_filter_btn;
    String text, choose;
    SharedPreferences sharedPref;
    String language = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        language = sharedPref.getString("language", "");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter_search_players_box, container, false);
        TextView title_header_fragment = (TextView) getActivity().findViewById(R.id.title_header_fragment);
        if (language.equals("en")) {
            title_header_fragment.setText(R.string.filtering);
        } else {
            title_header_fragment.setText("فلترة البحث");
        }
        customFontAwesome = Typeface.createFromAsset(getActivity().getAssets(), "fonts/fontawesome-webfont.ttf");
        custom2 = Typeface.createFromAsset(getActivity().getAssets(), "fonts/DIN Next LT W23 Regular.ttf");
        final String[] filer_values = new String[]{"1", "2", "3", "4", "5"};
        speed_filter_btn = (Button) view.findViewById(R.id.speed_filter_btn);
        skill_filter_btn = (Button) view.findViewById(R.id.skill_filter_btn);
        shooting_filter_btn = (Button) view.findViewById(R.id.shooting_filter_btn);
        speed_filter_btn.setTypeface(custom2);
        skill_filter_btn.setTypeface(custom2);
        shooting_filter_btn.setTypeface(custom2);

        speed_filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (language.equals("en")) {
                    text = getResources().getString(R.string.speed);
                    drawDialogRating(text, filer_values, 1);
                } else {
                    drawDialogRating("السرعة", filer_values, 1);
                }
            }
        });
        skill_filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (language.equals("en")) {
                    text = getResources().getString(R.string.Skill);
                    drawDialogRating(text, filer_values, 2);
                } else {
                    drawDialogRating("المهارة", filer_values, 2);
                }
            }
        });
        shooting_filter_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (language.equals("en")) {
                    text = getResources().getString(R.string.shot);
                    drawDialogRating(text, filer_values, 3);
                } else {
                    drawDialogRating("التسديد", filer_values, 3);
                }
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        setIDs();
        setFonts();
        LinearLayout regitration_ground_content = (LinearLayout) getView().findViewById(R.id.regitration_ground_content);
        if(language.equals("en"))
        {
            regitration_ground_content.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            English();
        }
        PrefManager prefManager = new PrefManager(getActivity());
        String json = prefManager.getFilered_Players_json();
        Player_Properties player_properties = new Player_Properties(getActivity());

        if (language.equals("en")) {
            choose = "Choose";
        } else {
            choose = "اختار";
        }
        btn_fragment_filter_search_players_box.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!speed_filter_btn.getText().toString().equals(choose) || !skill_filter_btn.getText().toString().equals(choose) || !shooting_filter_btn.getText().toString().equals(choose)) {
                    Fragment getPlayerFragment = new GetPlayerFragment();
                    Bundle bundle = new Bundle();
                    if (!shooting_filter_btn.getText().toString().equals(choose))
                        bundle.putString("filterShooting", shooting_filter_btn.getText().toString());
                    if (!skill_filter_btn.getText().toString().equals(choose))
                        bundle.putString("filterSkill", skill_filter_btn.getText().toString());
                    if (!speed_filter_btn.getText().toString().equals(choose))
                        bundle.putString("filterSpeed", speed_filter_btn.getText().toString());


                    getPlayerFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.placeholder2, getPlayerFragment, null)
                            .commit();
                } else {
                    if (language.equals("en")) {
                        Toast.makeText(getActivity(), R.string.filter , Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "اختار فلتر واحد علي الاقل", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


    }

    public void setIDs() {


        speed_icon_fragment_filter_search_players_box = (TextView) getView().findViewById(R.id.speed_icon_fragment_filter_search_players_box);
        speed_header_fragment_filter_search_players_box = (TextView) getView().findViewById(R.id.speed_header_fragment_filter_search_players_box);
        skill_icon_fragment_filter_search_players_box = (TextView) getView().findViewById(R.id.skill_icon_fragment_filter_search_players_box);
        skill_heasder_fragment_filter_search_players_box = (TextView) getView().findViewById(R.id.skill_heasder_fragment_filter_search_players_box);
        shoot_icon_fragment_filter_search_players_box = (TextView) getView().findViewById(R.id.shoot_icon_fragment_filter_search_players_box);
        shoot_header_fragment_filter_search_players_box = (TextView) getView().findViewById(R.id.shoot_header_fragment_filter_search_players_box);
        btn_fragment_filter_search_players_box = (Button) getView().findViewById(R.id.btn_fragment_filter_search_players_box);
        //  speed_spinner_fragment_filter_search_players_box=(Spinner) getView().findViewById(R.id.speed_spinner_fragment_filter_search_players_box);
        //  skill_spinner_fragment_filter_search_players_box=(Spinner)getView().findViewById(R.id.skill_spinner_fragment_filter_search_players_box);
        //  shoot_spinner_fragment_filter_search_players_box=(Spinner)getView().findViewById(R.id.shoot_spinner_fragment_filter_search_players_box);


    }

    public void setFonts() {

        speed_icon_fragment_filter_search_players_box.setTypeface(customFontAwesome);
        speed_header_fragment_filter_search_players_box.setTypeface(custom2);
        skill_icon_fragment_filter_search_players_box.setTypeface(customFontAwesome);
        skill_heasder_fragment_filter_search_players_box.setTypeface(custom2);
        shoot_icon_fragment_filter_search_players_box.setTypeface(customFontAwesome);
        shoot_header_fragment_filter_search_players_box.setTypeface(custom2);
        btn_fragment_filter_search_players_box.setTypeface(custom2);

    }
public void English(){
    speed_header_fragment_filter_search_players_box.setText(R.string.speed);
    skill_heasder_fragment_filter_search_players_box.setText(R.string.Skill);
    shoot_header_fragment_filter_search_players_box.setText(R.string.shot);
    speed_filter_btn.setText(R.string.chooseEn);
    skill_filter_btn.setText(R.string.chooseEn);
    shooting_filter_btn.setText(R.string.chooseEn);
    btn_fragment_filter_search_players_box.setText(R.string.search);
}

    void drawDialog(String Tiltle, final String s[], final int button_num) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.CustomDialogTheme));
        TextView title = new TextView(getActivity());

        title.setText(Tiltle);
        title.setBackgroundColor(Color.parseColor("#008542"));
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(20);
        title.setTypeface(custom2);

        dialog.setCustomTitle(title);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.choice_item, R.id.text_view_choice, s) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(R.id.text_view_choice);
                text.setTypeface(custom2);
                return view;
            }
        };
        dialog.setAdapter(adapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (button_num == 1)
                    speed_filter_btn.setText(s[which]);
                if (button_num == 2)
                    skill_filter_btn.setText(s[which]);
                if (button_num == 3)
                    shooting_filter_btn.setText(s[which]);
            }
        });


        AlertDialog alert = dialog.create();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        alert.show();
    }

    public void drawDialogRating(String Tiltle, final String s[], final int button_num) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.CustomDialogTheme));
        TextView title = new TextView(getActivity());

        title.setText(Tiltle);
        title.setBackgroundColor(Color.parseColor("#008542"));
        title.setPadding(10, 10, 10, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(20);
        title.setTypeface(custom2);

        dialog.setCustomTitle(title);

        DialogRatingAdapter dialogRatingAdapter = new DialogRatingAdapter(getActivity(), s,language);
        dialog.setAdapter(dialogRatingAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (button_num == 1)
                    speed_filter_btn.setText(s[which]);
                if (button_num == 2)
                    skill_filter_btn.setText(s[which]);
                if (button_num == 3)
                    shooting_filter_btn.setText(s[which]);
            }
        });

        AlertDialog alert = dialog.create();
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        alert.show();
    }

}
