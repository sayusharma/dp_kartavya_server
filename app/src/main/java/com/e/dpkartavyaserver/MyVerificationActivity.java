package com.e.dpkartavyaserver;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.e.dpkartavyaserver.Common.CurrentUser;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

public class MyVerificationActivity extends AppCompatActivity {
    private TabLayout tablayout;
    private TextView toolbar;
    private ImageView imageView;
    private ViewPager viewPager;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_verification);
        tablayout = findViewById(R.id.tabMyLayout);
        toolbar = findViewById(R.id.currentPolice);
        imageView = findViewById(R.id.currentPolicePhoto);
        viewPager = findViewById(R.id.viewMyPager);
        tablayout.addTab(tablayout.newTab().setText("Verifications"));
        tablayout.addTab(tablayout.newTab().setText("Visits"));
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        toolbar.setText(CurrentUser.currentUser.getName());
        try{
            Picasso.get()
                    .load(CurrentUser.currentUser.getPhoto())
                    .into(imageView);
        }catch (Exception e) {e.printStackTrace();}
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