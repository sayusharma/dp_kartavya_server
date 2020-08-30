package com.e.dpkartavyaserver.Adapter;
import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.e.dpkartavyaserver.VerificationFragment;
import com.e.dpkartavyaserver.VisitFragment;

public class DailyInsightsAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;
    public DailyInsightsAdapter(Context c, FragmentManager fm, int totalTabs) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new VerificationFragment();
            case 1:
                return new VisitFragment();
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return 2;
    }
}