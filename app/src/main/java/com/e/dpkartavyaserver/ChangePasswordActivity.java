package com.e.dpkartavyaserver;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.e.dpkartavyaserver.Common.CurrentAdmin;
import com.e.dpkartavyaserver.Model.Admin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText old,newP,conNew;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("admin");
        old = findViewById(R.id.oldPassword);
        newP = findViewById(R.id.newPassword);
        conNew = findViewById(R.id.conNewPassword);
    }
    public void onClickResetPassword(View view){
        if(validate()){
            Admin user = new Admin(CurrentAdmin.currentAdmin.getName(),CurrentAdmin.currentAdmin.getPhoto(),
                    CurrentAdmin.currentAdmin.getMob(),newP.getText().toString());
            databaseReference.child(CurrentAdmin.currentAdmin.getMob()).setValue(user).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),"BAD DATABASE REQUEST. TRY AGAIN",Toast.LENGTH_LONG).show();
                }
            }).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getApplicationContext(),"PASSWORD CHANGED!",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ChangePasswordActivity.this,DashActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

        }
    }

    private boolean validate() {
        if (TextUtils.isEmpty(old.getText()) || TextUtils.isEmpty(newP.getText()) || TextUtils.isEmpty(conNew.getText())){
            Toast.makeText(getApplicationContext(),"FIELDS CANNOT BE EMPTY!",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!old.getText().toString().equals(CurrentAdmin.currentAdmin.getPass())){
            Toast.makeText(getApplicationContext(),"OLD PASSWORD IS INCORRECT",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!newP.getText().toString().equals(conNew.getText().toString())){
            Toast.makeText(getApplicationContext(),"PASSWORD DOESN'T MATCH",Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }
    public void onClickBackChangePassword(View view){
        Intent intent = new Intent(ChangePasswordActivity.this,MyProfile.class);
        startActivity(intent);
        finish();
    }
}