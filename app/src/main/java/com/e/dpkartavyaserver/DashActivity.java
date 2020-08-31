package com.e.dpkartavyaserver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.e.dpkartavyaserver.Common.CurrentAdmin;
import com.e.dpkartavyaserver.Model.Admin;
import com.e.dpkartavyaserver.Preference.SaveSharedPreference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class DashActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ImageView profile;
    private Admin user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("admin");
        profile = findViewById(R.id.profile_pic);
        final ProgressDialog p = new ProgressDialog(this);
        p.setMessage("Please Wait...");
        p.show();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.child(SaveSharedPreference.getUserName(getApplicationContext())).getValue(Admin.class);
                CurrentAdmin.currentAdmin = user;
                Picasso.get()
                        .load(CurrentAdmin.currentAdmin.getPhoto())
                        .into(profile);
                p.dismiss();
                //Toast.makeText(getApplicationContext(),""+CurrentUser.currentUser.getName(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                p.dismiss();
                Toast.makeText(getApplicationContext(),"BAD DATABASE REQUEST",Toast.LENGTH_LONG).show();
            }
        });

    }
    public void onClickSeniorCitizens(View view){
        Intent intent = new Intent(DashActivity.this,SeniorCitizensActivity.class);
        startActivity(intent);
    }
    public void onClickMyProfile(View view){
        Intent intent = new Intent(DashActivity.this,MyProfile.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
        finish();
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