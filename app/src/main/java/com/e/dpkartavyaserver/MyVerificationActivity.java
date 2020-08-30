package com.e.dpkartavyaserver;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.e.dpkartavyaserver.Common.CurrentUser;
import com.google.android.material.tabs.TabLayout;

public class MyVerificationActivity extends AppCompatActivity {
    private TabLayout tablayout;
    public static String loc;
    private TextView textView;
    private ViewPager viewPager;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_verification);
        tablayout = findViewById(R.id.tabMyLayout);
        viewPager = findViewById(R.id.viewMyPager);
        tablayout.addTab(tablayout.newTab().setText("Verifications"));
        tablayout.addTab(tablayout.newTab().setText("Visits"));
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        textView = findViewById(R.id.currentPolice);
        textView.setText(CurrentUser.currentUser.getName());
        loc="Vasant Vihar";
        final MyVerificationAdapter adapter = new MyVerificationAdapter(this,getSupportFragmentManager(),
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
    public void onClickBackMyVerification(View view){
        Intent intent = new Intent(MyVerificationActivity.this,PoliceOfficersActivity.class);
        startActivity(intent);
        finish();
    }
}