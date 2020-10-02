package com.e.dpkartavyaserver;
import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class MyVerificationAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;
    public MyVerificationAdapter(Context c, FragmentManager fm, int totalTabs) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new VerificationsFragment();
            case 1:
                return new VisitsFragment();
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return 2;
    }
}