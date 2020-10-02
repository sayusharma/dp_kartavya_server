package com.e.dpkartavyaserver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import com.e.dpkartavyaserver.Common.CurrentAdmin;
import com.e.dpkartavyaserver.Preference.SaveSharedPreference;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MyProfile extends Activity {
    private Button logout,edit,change,cancel;
    private TextView name,desn,police;
    private ImageView imageView;
    private String string;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_profile);
       // firebaseDatabase = FirebaseDatabase.getInstance();
       // databaseReference = firebaseDatabase.getReference("users");
        logout = findViewById(R.id.btnLogout);
        name = findViewById(R.id.profile_name);
        desn = findViewById(R.id.designation);
        police = findViewById(R.id.police);
        desn.setText(CurrentAdmin.currentAdmin.getRank());
        police.setText(CurrentAdmin.currentAdmin.getPolice());
        imageView = findViewById(R.id.profilePhoto);
        //Toast.makeText(getApplicationContext(),""+CurrentUser.currentUser.getName()+CurrentUser.currentUser.getPhoto(),Toast.LENGTH_LONG).show();
        try {
            Picasso.get()
                    .load(CurrentAdmin.currentAdmin.getPhoto())
                    .into(imageView);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"FAILED TO LOAD PROFILE PHOTO",Toast.LENGTH_LONG).show();
        }
        name.setText(CurrentAdmin.currentAdmin.getName());

    }
    public void onClickLogout(View view){
        //SaveSharedPreference.setUserName(getApplicationContext(),"null");
        SaveSharedPreference.clearPreference(this);
        CurrentAdmin.currentAdmin = null;
        Intent intent = new Intent(MyProfile.this,LoginActivity.class);
        startActivity(intent);
    }
    public void onClickCancelProfile(View view){
        Intent intent = new Intent(MyProfile.this,DashActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
        finish();
    }
}
