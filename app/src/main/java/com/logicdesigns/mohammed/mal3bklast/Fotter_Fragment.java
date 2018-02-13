package com.logicdesigns.mohammed.mal3bklast;

        import android.app.Activity;
        import android.content.Context;
        import android.graphics.Color;
        import android.graphics.Typeface;
        import android.preference.PreferenceManager;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentActivity;
        import android.support.v4.app.FragmentTransaction;
        import android.support.v4.view.ViewPager;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.logicdesigns.mohammed.mal3bklast.common.FragmentsTags;
        import com.logicdesigns.mohammed.mal3bklast.fragments.ReservePlaygroundContainer;

public class Fotter_Fragment extends Fragment {
TextView get_player_footer,search_training_footer,home_footer,profile_footer;
    ImageView register_playground_footer;
    LinearLayout ll_get_player_footer,ll_search_training_footer
            ,ll_home_footer,ll_profile_footer,ll_register_playground_footer;

    MainContainer mainContainer = new MainContainer();
   // boolean checkonHomeIC=mainContainer.checkonHomeIC;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {




        View view;
        if (PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("langugae", "").equals("en")){
            return inflater.inflate(
                    R.layout.fragment_fotter, container, false);
        }

        else{
             view= inflater.inflate(
                    R.layout.fragment_fotter, container, false);


        }

     return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Typeface  custom_fontawesome= Typeface.createFromAsset(getActivity().getAssets(),  "fontawesome-webfont.ttf");
        register_playground_footer=(ImageView) getActivity().findViewById(R.id.register_playground_footer);


        ll_get_player_footer=(LinearLayout) getActivity().findViewById(R.id.ll_get_player_footer);
        ll_search_training_footer=(LinearLayout) getActivity().findViewById(R.id.ll_search_training_footer);
        ll_home_footer=(LinearLayout) getActivity().findViewById(R.id.ll_home_footer);
                ll_profile_footer=(LinearLayout) getActivity().findViewById(R.id.ll_profile_footer);
        ll_register_playground_footer=(LinearLayout) getActivity().findViewById(R.id.ll_register_playground_footer);

        home_footer=(TextView) getActivity().findViewById(R.id.home_footer);
        home_footer.setTypeface(custom_fontawesome);

      //  if(mainContainer.checkonHomeIC==false)
        //home_footer.setTextColor(Color.parseColor("#008542"));

        profile_footer=(TextView) getActivity().findViewById(R.id.profile_footer);
        profile_footer.setTypeface(custom_fontawesome);

        get_player_footer=(TextView) getActivity().findViewById(R.id.get_player_footer);
        get_player_footer.setTypeface(custom_fontawesome);


        search_training_footer=(TextView) getActivity().findViewById(R.id.search_training_footer);
        search_training_footer.setTypeface(custom_fontawesome);



///////////handle clickable
        ll_register_playground_footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  Fragment payFragment = new RegisterGroundFragment();
              /*  FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.placeholder2, payFragment).commit();*/
                Fragment payFragment = getActivity().getSupportFragmentManager().findFragmentByTag(FragmentsTags.RegisterPlayground_Tag);

                if (payFragment == null) {
                    getActivity().  getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.placeholder2, new ReservePlaygroundContainer(), FragmentsTags.RegisterPlayground_Tag)

                            .commit();        }
                else { // re-use the old fragment
                    getActivity().  getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.placeholder2,payFragment, FragmentsTags.RegisterPlayground_Tag)

                            .commit();
                }

                register_playground_footer.setBackgroundResource(R.drawable.goal2);
                get_player_footer.setTextColor(Color.parseColor("#dddddd"));
                search_training_footer.setTextColor(Color.parseColor("#dddddd"));
                home_footer.setTextColor(Color.parseColor("#dddddd"));
                profile_footer.setTextColor(Color.parseColor("#dddddd"));

            }
        });

        ll_get_player_footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Fragment searchAboutPlayerFragment = new SearchAboutPlayerFragment();
//                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.placeholder2, searchAboutPlayerFragment).commit();

                Fragment searchFragment = getActivity().getSupportFragmentManager().findFragmentByTag(FragmentsTags.SearchPlayer_Tag);

                if (searchFragment == null) {
                    getActivity().  getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.placeholder2, new SearchAboutPlayerFragment(), FragmentsTags.SearchPlayer_Tag)

                            .commit();        }
                else { // re-use the old fragment
                    getActivity().  getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.placeholder2,searchFragment, FragmentsTags.SearchPlayer_Tag)

                            .commit();
                }

                register_playground_footer.setBackgroundResource(R.drawable.shabka);
                get_player_footer.setTextColor(Color.parseColor("#008542"));
                search_training_footer.setTextColor(Color.parseColor("#dddddd"));
                home_footer.setTextColor(Color.parseColor("#dddddd"));
                profile_footer.setTextColor(Color.parseColor("#dddddd"));

            }
        });

        ll_search_training_footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                Fragment searchTrainingFragment = new SearchTrainingBoxFragment();
//                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.placeholder2, searchTrainingFragment).commit();

                Fragment TrainingFragment = getActivity().getSupportFragmentManager().findFragmentByTag(FragmentsTags.SearchTraining_Tag);

                if (TrainingFragment == null) {
                    getActivity().  getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.placeholder2, new SearchTrainingBoxFragment(), FragmentsTags.SearchTraining_Tag)

                            .commit();        }
                else { // re-use the old fragment
                    getActivity().  getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.placeholder2,TrainingFragment, FragmentsTags.SearchTraining_Tag)

                            .commit();
                }

                register_playground_footer.setBackgroundResource(R.drawable.shabka);
                get_player_footer.setTextColor(Color.parseColor("#dddddd"));
                search_training_footer.setTextColor(Color.parseColor("#008542"));
                home_footer.setTextColor(Color.parseColor("#dddddd"));
                profile_footer.setTextColor(Color.parseColor("#dddddd"));
            }
        });



        ///////////
        ////////////////////////////
        ll_home_footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Fragment mainPageFragment = new MainPageFragment();
//                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.placeholder2, mainPageFragment).commit();

                Fragment main_fragment = getActivity().getSupportFragmentManager().findFragmentByTag( FragmentsTags.Home_Tag);

                if (main_fragment == null) {
                    getActivity().  getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.placeholder2, new MainPageFragment(),  FragmentsTags.Home_Tag)

                            .commit();        }
                else { // re-use the old fragment
                    getActivity().  getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.placeholder2,main_fragment,  FragmentsTags.Home_Tag)

                            .commit();
                }

                register_playground_footer.setBackgroundResource(R.drawable.shabka);
                get_player_footer.setTextColor(Color.parseColor("#dddddd"));
                search_training_footer.setTextColor(Color.parseColor("#dddddd"));
                home_footer.setTextColor(Color.parseColor("#008542"));
                profile_footer.setTextColor(Color.parseColor("#dddddd"));
            }
        });

        ll_profile_footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Fragment profileFragment = new ProfileFragment();
//                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.placeholder2, profileFragment).commit();

                Fragment profileFragment = getActivity().getSupportFragmentManager().findFragmentByTag(FragmentsTags.Profile_Tag);

                if (profileFragment == null) {
                    getActivity().  getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.placeholder2, new ProfileFragment(), FragmentsTags.Profile_Tag)

                            .commit();        }
                else { // re-use the old fragment
                    getActivity().  getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.placeholder2,profileFragment, FragmentsTags.Profile_Tag)

                            .commit();
                }

                register_playground_footer.setBackgroundResource(R.drawable.shabka);
                get_player_footer.setTextColor(Color.parseColor("#dddddd"));
                search_training_footer.setTextColor(Color.parseColor("#dddddd"));
                home_footer.setTextColor(Color.parseColor("#dddddd"));
                profile_footer.setTextColor(Color.parseColor("#008542"));
            }
        });




    }

    public  void updateFooterIcons(int i, Context context)
    {
        ImageView register_playground_footer;
        TextView get_player_footer,search_training_footer,home_footer,profile_footer;
        Typeface  custom_fontawesome= Typeface.createFromAsset(context.getAssets(),  "fontawesome-webfont.ttf");

        register_playground_footer=(ImageView)((Activity) context).findViewById(R.id.register_playground_footer);

        home_footer=(TextView) ((Activity) context).findViewById(R.id.home_footer);
        home_footer.setTypeface(custom_fontawesome);


        profile_footer=(TextView) ((Activity) context).findViewById(R.id.profile_footer);
        profile_footer.setTypeface(custom_fontawesome);

        get_player_footer=(TextView) ((Activity) context).findViewById(R.id.get_player_footer);
        get_player_footer.setTypeface(custom_fontawesome);


        search_training_footer=(TextView) ((Activity) context).findViewById(R.id.search_training_footer);
        search_training_footer.setTypeface(custom_fontawesome);

        if (i==1)
        {        //    mainContainer.checkonHomeIC=false;

            register_playground_footer.setBackgroundResource(R.drawable.shabka);
            get_player_footer.setTextColor(Color.parseColor("#dddddd"));
            search_training_footer.setTextColor(Color.parseColor("#dddddd"));
            home_footer.setTextColor(Color.parseColor("#008542"));
            profile_footer.setTextColor(Color.parseColor("#dddddd"));
        }
        else if (i==2)
        {         //   mainContainer.checkonHomeIC=true;

            register_playground_footer.setBackgroundResource(R.drawable.goal2);
            get_player_footer.setTextColor(Color.parseColor("#dddddd"));
            search_training_footer.setTextColor(Color.parseColor("#dddddd"));
            home_footer.setTextColor(Color.parseColor("#dddddd"));
            profile_footer.setTextColor(Color.parseColor("#dddddd"));
        }
        else if (i==3)
        {          //  mainContainer.checkonHomeIC=true;

            register_playground_footer.setBackgroundResource(R.drawable.shabka);
            get_player_footer.setTextColor(Color.parseColor("#008542"));
            search_training_footer.setTextColor(Color.parseColor("#dddddd"));
            home_footer.setTextColor(Color.parseColor("#dddddd"));
            profile_footer.setTextColor(Color.parseColor("#dddddd"));
        }
        else if (i==4)
        {           // mainContainer.checkonHomeIC=true;

            register_playground_footer.setBackgroundResource(R.drawable.shabka);
            get_player_footer.setTextColor(Color.parseColor("#dddddd"));
            search_training_footer.setTextColor(Color.parseColor("#008542"));
            home_footer.setTextColor(Color.parseColor("#dddddd"));
            profile_footer.setTextColor(Color.parseColor("#dddddd"));
        }
        else  if (i==5)
        {
          //  mainContainer.checkonHomeIC=true;
            register_playground_footer.setBackgroundResource(R.drawable.shabka);
            get_player_footer.setTextColor(Color.parseColor("#dddddd"));
            search_training_footer.setTextColor(Color.parseColor("#dddddd"));
            home_footer.setTextColor(Color.parseColor("#dddddd"));
            profile_footer.setTextColor(Color.parseColor("#008542"));
        }
    }
}
