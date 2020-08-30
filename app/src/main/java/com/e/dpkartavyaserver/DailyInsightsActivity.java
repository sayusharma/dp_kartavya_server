package com.e.dpkartavyaserver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.e.dpkartavyaserver.Adapter.*;
import com.google.android.material.tabs.TabLayout;

public class DailyInsightsActivity extends AppCompatActivity {
    private TabLayout tablayout;
    public static String loc;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_insights);
        viewPager = findViewById(R.id.viewMyPager);
        tablayout = findViewById(R.id.tabMyLayout);
        tablayout.addTab(tablayout.newTab().setText("Verifications"));
        tablayout.addTab(tablayout.newTab().setText("Visits"));
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final DailyInsightsAdapter adapter = new DailyInsightsAdapter(this,getSupportFragmentManager(),
                tablayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
    public void onClickBackDailyInsights(View view){
        Intent intent = new Intent(DailyInsightsActivity.this,DashActivity.class);
        startActivity(intent);
        finish();
    }
}