package com.e.dpkartavyaserver;
import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class MyAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;
    public MyAdapter(Context c, FragmentManager fm, int totalTabs) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new BasicDetailsFragment();
            case 1:
                return new ServiceProviderFragment();
            case 2:
                return new SecurityChecksFragment();
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return 3;
    }
}