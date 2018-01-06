package com.example.mikeb.calldetails;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mikeb on 10/27/2017.
 */

public class SectionPageAdapter extends FragmentPagerAdapter {


    public final List<Fragment>mFragList = new ArrayList<Fragment>();
    public final List<String> mFragTitleList = new ArrayList<String>();
    public SectionPageAdapter(FragmentManager fragmentManager){
        super(fragmentManager);
    }
    public void addFragment(Fragment fragment,String title){
        mFragList.add(fragment);
        mFragTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragTitleList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragList.get(position);
    }

    @Override
    public int getCount() {
        return mFragList.size();
    }
}
