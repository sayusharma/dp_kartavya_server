package com.e.dpkartavyaserver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.e.dpkartavyaserver.Preference.SaveSharedPreference;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class DashActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ImageView profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("admin");
        profile = findViewById(R.id.profile_pic);
        Picasso.get()
                .load(SaveSharedPreference.getPhoto(this))
                .into(profile);

    }
    public void onClickDailyIn(View view){
        Intent intent = new Intent(DashActivity.this,DailyInsightsActivity.class);
        startActivity(intent);
    }
    public void onClickSignUpRequests(View view){
        Intent intent = new Intent(DashActivity.this,SignUpRequestActivity.class);
        startActivity(intent);
    }
    public void onClickPoliceOfficer(View view){
        Intent intent = new Intent(DashActivity.this,PoliceOfficersActivity.class);
        startActivity(intent);
    }
}