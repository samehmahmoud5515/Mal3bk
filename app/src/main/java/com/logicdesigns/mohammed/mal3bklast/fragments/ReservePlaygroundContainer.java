package com.logicdesigns.mohammed.mal3bklast.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.logicdesigns.mohammed.mal3bklast.HeaderFragment;
import com.logicdesigns.mohammed.mal3bklast.MainContainer;
import com.logicdesigns.mohammed.mal3bklast.R;
import com.logicdesigns.mohammed.mal3bklast.RegisterGroundFragment;
import com.logicdesigns.mohammed.mal3bklast.SharedPref.PrefManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReservePlaygroundContainer extends Fragment {


    public ReservePlaygroundContainer() {
        // Required empty public constructor
    }
    /*public static ReservePlaygroundContainer newInstance() {
        ReservePlaygroundContainer myFragment = new ReservePlaygroundContainer();



        return myFragment;
    }*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_reserve_playground_container, container, false);
        NonSwipeableViewPager viewPager = (NonSwipeableViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        // Set Tabs inside Toolbar
        TabLayout tabs = (TabLayout) view.findViewById(R.id.result_tabs);
        tabs.setupWithViewPager(viewPager);
        changeTabsFont(tabs);

        return  view;
    }





    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getChildFragmentManager());

        PrefManager prefManager = new PrefManager(getActivity());
        String lang = prefManager.getLanguage();
        if(lang.equals("ar"))
        {
            adapter.addFragment(new PlaygroundSearchByCityFragment(), getActivity().getResources().getString(R.string.reserve_ar));
            adapter.addFragment(new RegisterGroundFragment(), getActivity().getResources().getString(R.string.reserve_advanced_ar));

        }
        else {
            adapter.addFragment(new PlaygroundSearchByCityFragment(), getActivity().getResources().getString(R.string.reserve_en));
            adapter.addFragment(new RegisterGroundFragment(), getActivity().getResources().getString(R.string.reserve_advanced_en));
        }
        viewPager.setAdapter(adapter);
    }


    private void changeTabsFont(TabLayout tabLayout) {
        Typeface  dinNext = Typeface.createFromAsset(getActivity().getAssets(), "fonts/DIN Next LT W23 Regular.ttf");


        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(dinNext);
                }
            }
        }
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
