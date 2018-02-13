package com.logicdesigns.mohammed.mal3bklast.pager;




import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by logicDesigns on 7/12/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    FragmentManager mFragmentManager;

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
        mFragmentManager = manager;
    }
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
    }
    public void replaceLeftFragment(Fragment fragment,int postion) {

            mFragmentManager.beginTransaction().remove(mFragmentList.get(postion));
            mFragmentManager.beginTransaction().add(postion,fragment).commit();



        notifyDataSetChanged();
    }
}
