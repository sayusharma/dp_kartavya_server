package com.e.dpkartavyaserver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.e.dpkartavyaserver.Common.CurrentAdmin;
import com.e.dpkartavyaserver.Model.Admin;
import com.e.dpkartavyaserver.Preference.SaveSharedPreference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.UUID;

public class EditProfileActivity extends AppCompatActivity {
    private EditText name;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private Uri currentPhotoUri;
    private ImageView imageView;
    private String currentPhotoDownloadableUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("admin");
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        imageView = findViewById(R.id.euserPhoto);
        currentPhotoDownloadableUrl = CurrentAdmin.currentAdmin.getPhoto();
        name = findViewById(R.id.euserName);
        Picasso.get().load(CurrentAdmin.currentAdmin.getPhoto()).into(imageView);
        name.setText(CurrentAdmin.currentAdmin.getName());
    }
    public void eonClickUploadProfilePhoto(View view){
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .start(this);
    }
    public void eonClickRegisterNowDetails(View view){
        if(validate()){
            Admin user = new Admin(name.getText().toString(),currentPhotoDownloadableUrl, SaveSharedPreference.getUserName(this),CurrentAdmin.currentAdmin.getPass());
            databaseReference.child(CurrentAdmin.currentAdmin.getMob()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getApplicationContext(),"CHANGES SAVED!",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(EditProfileActivity.this,DashActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(),"BAD DATABASE REQUEST. TRY AGAIN",Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    private boolean validate() {
        if (TextUtils.isEmpty(name.getText())){
            Toast.makeText(getApplicationContext(),"FIELDS CANNOT BE EMPTY!",Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                currentPhotoUri = result.getUri();
                setImageView(currentPhotoUri);
                setDownloadableUrl(currentPhotoUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void setImageView(Uri currentPhotoUri) {
        ImageView imageView = findViewById(R.id.euserPhoto);
        imageView.setImageURI(currentPhotoUri);
    }
    public void onClickBackEditProfile(View view){
        Intent intent = new Intent(EditProfileActivity.this,MyProfile.class);
        startActivity(intent);
        finish();
    }
    private void setDownloadableUrl(Uri uri) {
        final StorageReference reference;
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading Photo....");
        progressDialog.show();
        reference = storageReference.child("UserImages/"+ UUID.randomUUID().toString());
        reference.putFile(uri)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        progressDialog.dismiss();
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                currentPhotoDownloadableUrl = uri.toString();

                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),"ERROR UPLOADING PHOTO",Toast.LENGTH_LONG).show();
                    }
                });
    }
}