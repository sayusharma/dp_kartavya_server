package com.e.dpkartavyaserver;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.e.dpkartavyaserver.Common.CurrentChildrenList;
import com.e.dpkartavyaserver.Common.CurrentSenior;
import com.e.dpkartavyaserver.Common.CurrentServiceProviderList;
import com.e.dpkartavyaserver.Common.CurrentUser;
import com.e.dpkartavyaserver.Model.AdditionalDetails;
import com.e.dpkartavyaserver.Model.BasicDetails;
import com.e.dpkartavyaserver.Model.Loc;
import com.e.dpkartavyaserver.Model.MoreDetails;
import com.e.dpkartavyaserver.Model.PersonalDetails;
import com.e.dpkartavyaserver.Model.RelativeDetails;
import com.e.dpkartavyaserver.Model.SecurityChecks;
import com.e.dpkartavyaserver.Model.SpouseDetails;
import com.e.dpkartavyaserver.Model.User;
import com.e.dpkartavyaserver.Model.VerifySnr;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class VerifyActivity extends AppCompatActivity implements LocationListener {
    private static final int LOCATION_REQ_CODE = 1021;
    Context context;
    private String currentDate;
    private PersonalDetails personalDetails;
    private SpouseDetails spouseDetails;
    private AdditionalDetails additionalDetails;
    private FusedLocationProviderClient fusedLocationClient;
    private RelativeDetails relativeDetails;
    private BasicDetails basicDetails;
    private boolean gps_enabled = false;
    private boolean network_enabled = false;
    private MoreDetails moreDetails;
    EditText name, dob, addr, mob, email, sname, sdob, wedding, from, year, field, children, residingWith, health, lpVisit, freeTime, rname, relation, rmob, raddr;
    private SecurityChecks securityChecks;
    private SwitchCompat Aaa, Aab, Aac, Aad, Aae, Aaf, Aag, Aba, Abb, Abc, Abd, Abe, Abf, Abg, Ba, Bb, Bc, Bd, Be, Bf;
    private String retired = "";
    private TabLayout tablayout;
    private Spinner dr, wa, ser, ten, swe, car, oth;
    private String currentTime;
    ViewPager viewPager;
    private Loc location;
    private String teVerStatus, drVerStatus, wVerStatus, serVerStatus, swVerStatus, carVerStatus, otVerStatus;
    private EditText drName, drAddr, drVerNo, wName, wAddr, wVerNo, serName, serAddr, serVerNo, teName, teAddr, teVerNo, swName, swAddr, swVerNo, carName, carAddr, carVerNo, otName, otAddr, otVerNo, otSerType;
    private String tenant_current_gender = "";
    private static final int REQUEST_LOCATION = 1;
    private String currentPhotoDownloadableUrl = "";
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private double latitude;
    private double longitude;
    private LocationManager locationManager;
    private Uri currentPhotoUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        context = this;
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("snr_czn");
        DatabaseReference databaseReference2 = firebaseDatabase.getReference("users");
        databaseReference2.child(CurrentSenior.currentSenior.getMoreDetails().getOff_mob()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                CurrentUser.currentUser = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        tenant_current_gender = CurrentSenior.currentSenior.getBasicDetails().getPersonalDetails().getGender();
        CurrentServiceProviderList.currentServiceList = CurrentSenior.currentSenior.getServiceProviders();
        CurrentChildrenList.currentChildrenList = CurrentSenior.currentSenior.getBasicDetails().getChildrenDetails();
        tablayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        tablayout.addTab(tablayout.newTab().setText("Basic Details"));
        tablayout.addTab(tablayout.newTab().setText("Service Providers"));
        tablayout.addTab(tablayout.newTab().setText("Security Checks"));
        tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final MyAdapter adapter = new MyAdapter(this, getSupportFragmentManager(),
                tablayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(tablayout.getApplicationWindowToken(), 0);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        personalDetails = CurrentSenior.currentSenior.getBasicDetails().getPersonalDetails();
        additionalDetails = CurrentSenior.currentSenior.getBasicDetails().getAdditionalDetails();
        relativeDetails = CurrentSenior.currentSenior.getBasicDetails().getRelativeDetails();
        spouseDetails = CurrentSenior.currentSenior.getBasicDetails().getSpouseDetails();
        basicDetails = CurrentSenior.currentSenior.getBasicDetails();

        Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        currentDate = year + "/" + (month + 1) + "/" + day;
        //Checking if gps is enabled

    }


    public void onClickBackVerify(View view) {
        Intent intent = new Intent(VerifyActivity.this, DashActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_REQ_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                }
                break;
        }
    }

    private void initialiseSwitchCompats() {
        Aaa = findViewById(R.id.Aaa);
        Aab = findViewById(R.id.Aab);
        Aac = findViewById(R.id.Aac);
        Aad = findViewById(R.id.Aad);
        Aae = findViewById(R.id.Aae);
        Aaf = findViewById(R.id.Aaf);
        Aag = findViewById(R.id.Aag);
        Aba = findViewById(R.id.Aba);
        Abb = findViewById(R.id.Abb);
        Abc = findViewById(R.id.Abc);
        Abd = findViewById(R.id.Abd);
        Abe = findViewById(R.id.Abe);
        Abf = findViewById(R.id.Abf);
        Abg = findViewById(R.id.Abg);
        Ba = findViewById(R.id.Ba);
        Bb = findViewById(R.id.Bb);
        Bc = findViewById(R.id.Bc);
        Bd = findViewById(R.id.Bd);
        Be = findViewById(R.id.Be);
        Bf = findViewById(R.id.Bf);
    }

    public void onClickAddPhoto(View view) {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .start(this);
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
        ImageView imageView = findViewById(R.id.seniorPhoto);
        imageView.setImageURI(currentPhotoUri);
    }

    private void setDownloadableUrl(Uri uri) {
        final StorageReference reference;
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading Photo....");
        progressDialog.show();
        reference = storageReference.child("SnrCznImages/" + UUID.randomUUID().toString());
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
                        Toast.makeText(getApplicationContext(), "ERROR UPLOADING PHOTO", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void onClickSaveBasicDetails(View view) {
        name = findViewById(R.id.seniorName);
        dob = findViewById(R.id.dob_senior);
        addr = findViewById(R.id.seniorAddress);
        mob = findViewById(R.id.seniorMob);
        email = findViewById(R.id.seniorEmail);
        sname = findViewById(R.id.spouseName);
        sdob = findViewById(R.id.dob_spouse);
        wedding = findViewById(R.id.wedding);
        from = findViewById(R.id.retiredFrom);
        year = findViewById(R.id.retiredYear);
        field = findViewById(R.id.field);
        residingWith = findViewById(R.id.residingWith);
        health = findViewById(R.id.health);
        freeTime = findViewById(R.id.freeTime);
        rname = findViewById(R.id.relativeName);
        relation = findViewById(R.id.relativeRelation);
        rmob = findViewById(R.id.relativeMob);
        raddr = findViewById(R.id.relativeAdd);
        if (validateBasicDetails()) {
            personalDetails = new PersonalDetails(currentPhotoDownloadableUrl, name.getText().toString(), tenant_current_gender, dob.getText().toString(), addr.getText().toString(), mob.getText().toString(), email.getText().toString());
            spouseDetails = new SpouseDetails(sname.getText().toString(), sdob.getText().toString(), wedding.getText().toString());
            additionalDetails = new AdditionalDetails(retired, from.getText().toString(), year.getText().toString(), field.getText().toString(), residingWith.getText().toString(), health.getText().toString(), freeTime.getText().toString());
            relativeDetails = new RelativeDetails(rname.getText().toString(), relation.getText().toString(), rmob.getText().toString(), raddr.getText().toString());
            basicDetails = new BasicDetails(personalDetails, spouseDetails, additionalDetails, relativeDetails, CurrentChildrenList.currentChildrenList);
            viewPager.setCurrentItem(tablayout.getSelectedTabPosition() + 1);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(tablayout.getApplicationWindowToken(), 0);
        }
    }

    private boolean validateBasicDetails() {
        if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(dob.getText()) || TextUtils.isEmpty(addr.getText()) || TextUtils.isEmpty(mob.getText())) {
            Toast.makeText(getApplicationContext(), "FIELDS CANNOT BE EMPTY", Toast.LENGTH_LONG).show();
            return false;
        } else if (mob.getText().length() != 10) {
            Toast.makeText(getApplicationContext(), "MOB NO CANNOT BE LESS THAN 10 DIGITS", Toast.LENGTH_LONG).show();
            return false;
        } else if (tenant_current_gender.equals("")) {
            Toast.makeText(getApplicationContext(), "PLEASE SELECT GENDER", Toast.LENGTH_LONG).show();
            return false;
        } else return true;
    }

    public String getChecked(SwitchCompat switchCompat) {
        if (switchCompat.isChecked())
            return String.valueOf(switchCompat.getTextOn());
        else return String.valueOf(switchCompat.getTextOff());
    }

    public int encodeSec(String s) {
        if (s.equals("Yes")) {
            return 1;
        }
        if (s.equals("No")) {
            return 0;
        }
        if (s.equals("Poor")) {
            return 2;
        }
        return 3;
    }

    public void onClickSubmit(View view) {
        initialiseSwitchCompats();
        try {
            securityChecks = new SecurityChecks(encodeSec(getChecked(Aaa)), encodeSec(getChecked(Aab)), encodeSec(getChecked(Aac)), encodeSec(getChecked(Aad)),
                    encodeSec(getChecked(Aae)), encodeSec(getChecked(Aaf)), encodeSec(getChecked(Aag)), encodeSec(getChecked(Aba)), encodeSec(getChecked(Abb)),
                    encodeSec(getChecked(Abc)), encodeSec(getChecked(Abd)), encodeSec(getChecked(Abe)), encodeSec(getChecked(Abf)), encodeSec(getChecked(Abg)),
                    encodeSec(getChecked(Ba)), encodeSec(getChecked(Bb)), encodeSec(getChecked(Bc)), encodeSec(getChecked(Bd)), encodeSec(getChecked(Be)),
                    encodeSec(getChecked(Bf)));
            currentTime = CurrentSenior.currentSenior.getMoreDetails().getTime();
            currentDate = CurrentSenior.currentSenior.getMoreDetails().getDate();
            location = CurrentSenior.currentSenior.getMoreDetails().getLoc();
            moreDetails = CurrentSenior.currentSenior.getMoreDetails();
            final VerifySnr verifySnr = new VerifySnr(basicDetails,CurrentServiceProviderList.currentServiceList,securityChecks,moreDetails);
            String id = basicDetails.getPersonalDetails().getMob();
            databaseReference.child(CurrentUser.currentUser.getPolice()).child(id).setValue(verifySnr).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "SUBMIT SUCCESSFUL", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(VerifyActivity.this, PreviewSnrCznActivity.class);
                        CurrentSenior.currentSenior = verifySnr;
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "ERROR WHILE SUBMITTING. TRY AGAIN", Toast.LENGTH_LONG).show();
                    }
                });


        }catch (NullPointerException n){
            n.printStackTrace();
            Toast.makeText(getApplicationContext(),"PLEASE SAVE DETAILS",Toast.LENGTH_LONG).show();
        }
    }

    public void onClickGenderTenant(View view){
        TextView textFemale = findViewById(R.id.tenant_female_text);
        TextView textMale =findViewById(R.id.tenant_male_text);
        TextView textOther = findViewById(R.id.tenant_other_text);
        CardView female = findViewById(R.id.tenant_female);
        CardView male = findViewById(R.id.tenant_male);
        CardView other = findViewById(R.id.tenant_other);
        switch (view.getId()){
            case R.id.tenant_male:
                tenant_current_gender = "male";
                male.setCardBackgroundColor(Color.parseColor("#1F2144"));
                textMale.setTextColor(Color.parseColor("#ffffff"));
                female.setCardBackgroundColor(Color.parseColor("#ffffff"));
                textFemale.setTextColor(Color.parseColor("#bdbdbd"));
                other.setCardBackgroundColor(Color.parseColor("#ffffff"));
                textOther.setTextColor(Color.parseColor("#bdbdbd"));
                break;
            case R.id.tenant_female:
                tenant_current_gender = "female";
                female.setCardBackgroundColor(Color.parseColor("#1F2144"));
                textFemale.setTextColor(Color.parseColor("#ffffff"));
                male.setCardBackgroundColor(Color.parseColor("#ffffff"));
                textMale.setTextColor(Color.parseColor("#bdbdbd"));
                other.setCardBackgroundColor(Color.parseColor("#ffffff"));
                textOther.setTextColor(Color.parseColor("#bdbdbd"));
                break;
            case R.id.tenant_other:
                tenant_current_gender = "other";
                other.setCardBackgroundColor(Color.parseColor("#1F2144"));
                textOther.setTextColor(Color.parseColor("#ffffff"));
                female.setCardBackgroundColor(Color.parseColor("#ffffff"));
                textFemale.setTextColor(Color.parseColor("#bdbdbd"));
                male.setCardBackgroundColor(Color.parseColor("#ffffff"));
                textMale.setTextColor(Color.parseColor("#bdbdbd"));
                break;
        }
    }
    public void onClickRetired(View view){
        TextView textFemale = findViewById(R.id.seniorNoText);
        TextView textMale =findViewById(R.id.seniorYesText);
        CardView female = findViewById(R.id.seniorNo);
        CardView male = findViewById(R.id.seniorYes);
        EditText from = findViewById(R.id.retiredFrom);
        EditText year = findViewById(R.id.retiredYear);
        switch (view.getId()){
            case R.id.seniorYes:
                retired = "YES";
                from.setEnabled(true);
                year.setEnabled(true);
                male.setCardBackgroundColor(Color.parseColor("#1F2144"));
                textMale.setTextColor(Color.parseColor("#ffffff"));
                female.setCardBackgroundColor(Color.parseColor("#ffffff"));
                textFemale.setTextColor(Color.parseColor("#bdbdbd"));
                break;
            case R.id.seniorNo:
                retired = "NO";
                from.setEnabled(false);
                from.setText("");
                year.setText("");
                year.setEnabled(false);
                female.setCardBackgroundColor(Color.parseColor("#1F2144"));
                textFemale.setTextColor(Color.parseColor("#ffffff"));
                male.setCardBackgroundColor(Color.parseColor("#ffffff"));
                textMale.setTextColor(Color.parseColor("#bdbdbd"));
                break;
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}