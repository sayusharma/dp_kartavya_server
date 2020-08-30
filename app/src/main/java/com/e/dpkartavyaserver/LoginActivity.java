package com.e.dpkartavyaserver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.e.dpkartavyaserver.Common.CurrentAdmin;
import com.e.dpkartavyaserver.Model.Admin;
import com.e.dpkartavyaserver.Preference.SaveSharedPreference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText login_email,login_password;
    private Button login_click;
    private Admin user;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);
        login_click = findViewById(R.id.login_click);
        /* To make Box At time of Typing */
        login_password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                login_email.setBackgroundResource(R.drawable.edit_text_design_off);
                login_password.setBackgroundResource(R.drawable.edit_text_design_on);
                return false;
            }
        });

        login_email.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                login_password.setBackgroundResource(R.drawable.edit_text_design_off);
                login_email.setBackgroundResource(R.drawable.edit_text_design_on);
                return false;
            }
        });
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("admin");
        login_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    final String email = login_email.getText().toString();
                    final String pass = login_password.getText().toString();
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child(email).exists()){
                                user = dataSnapshot.child(email).getValue(Admin.class);
                                CurrentAdmin.currentAdmin = user;
                                if (user.getPass().equals(pass)){
                                    SaveSharedPreference.setUserName(getApplicationContext(),user.getMob());
                                    SaveSharedPreference.setPhoto(getApplicationContext(),user.getPhoto());
                                    Intent intent = new Intent(LoginActivity.this, DashActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    Toast.makeText(getApplicationContext(),"WRONG PASSWORD",Toast.LENGTH_LONG).show();
                                }
                            }
                            else Toast.makeText(getApplicationContext(),"USER DOESN'T EXISTS",Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getApplicationContext(),"BAD DATABASE REQUEST",Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else
                    Toast.makeText(getApplicationContext(),"INVALID CREDENTIALS",Toast.LENGTH_LONG).show();
            }
        });
    }
    private boolean validate() {
        if (TextUtils.isEmpty(login_email.getText()) || TextUtils.isEmpty(login_password.getText())){
            return false;
        }
        else return true;
    }
}