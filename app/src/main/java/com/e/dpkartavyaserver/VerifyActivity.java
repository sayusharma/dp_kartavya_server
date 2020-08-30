package com.e.dpkartavyaserver;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.e.dpkartavyaserver.Common.CurrentSenior;
import com.e.dpkartavyaserver.Common.CurrentUser;
import com.e.dpkartavyaserver.Model.AdditionalDetails;
import com.e.dpkartavyaserver.Model.BasicDetails;
import com.e.dpkartavyaserver.Model.Loc;
import com.e.dpkartavyaserver.Model.MoreDetails;
import com.e.dpkartavyaserver.Model.OtherServiceProvider;
import com.e.dpkartavyaserver.Model.PersonalDetails;
import com.e.dpkartavyaserver.Model.RelativeDetails;
import com.e.dpkartavyaserver.Model.SecurityChecks;
import com.e.dpkartavyaserver.Model.ServiceProvider;
import com.e.dpkartavyaserver.Model.ServiceProviders;
import com.e.dpkartavyaserver.Model.SpouseDetails;
import com.e.dpkartavyaserver.Model.VerifySnr;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

public class VerifyActivity extends AppCompatActivity{
    private static final int LOCATION_REQ_CODE = 1021;
    Context context;
    private String currentDate;
    private PersonalDetails personalDetails;
    private SpouseDetails spouseDetails;
    private AdditionalDetails additionalDetails;
    //private FusedLocationProviderClient fusedLocationClient;
    private RelativeDetails relativeDetails;
    private BasicDetails basicDetails;
    private MoreDetails moreDetails;
    EditText name, dob, addr, mob, email, sname, sdob, wedding, from, year, field, children, residingWith, health, lpVisit, freeTime, rname, relation, rmob, raddr;
    private ServiceProviders serviceProviders;
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
    private String latitude;
    private String longitude;
    private LocationManager locationManager;
    private Uri currentPhotoUri;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        context = this;
        tenant_current_gender = CurrentSenior.currentSenior.getBasicDetails().getPersonalDetails().getGender();
        retired = CurrentSenior.currentSenior.getBasicDetails().getAdditionalDetails().getRetired();
        currentPhotoDownloadableUrl = CurrentSenior.currentSenior.getBasicDetails().getPersonalDetails().getPhoto();
        latitude = CurrentSenior.currentSenior.getMoreDetails().getLoc().getLatitude();
        longitude = CurrentSenior.currentSenior.getMoreDetails().getLoc().getLongitude();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("snr_czn");
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
        Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        currentDate = day + "/" + (month + 1) + "/" + year;
        //Checking if gps is enabled

    }


    private void initialiseSpinners() {
        dr = findViewById(R.id.driverVerStatus);
        wa = findViewById(R.id.WatchmanVerStatus);
        ser = findViewById(R.id.ServantVerStatus);
        ten = findViewById(R.id.TenantVerStatus);
        swe = findViewById(R.id.SweeperVerStatus);
        car = findViewById(R.id.CarCleanerVerStatus);
        oth = findViewById(R.id.OthersVerStatus);
    }

    private void initialiseEditTexts() {


        drName = findViewById(R.id.driverName);
        drAddr = findViewById(R.id.driverAdd);
        drVerNo = findViewById(R.id.driverVerNo);
        wName = findViewById(R.id.WatchmanName);
        wAddr = findViewById(R.id.WatchmanAdd);
        wVerNo = findViewById(R.id.WatchmanVerNo);
        serName = findViewById(R.id.ServantName);
        serAddr = findViewById(R.id.ServantAdd);
        serVerNo = findViewById(R.id.ServantVerNo);
        teName = findViewById(R.id.TenantName);
        teAddr = findViewById(R.id.TenantAdd);
        teVerNo = findViewById(R.id.TenantVerNo);
        swName = findViewById(R.id.SweeperName);
        swAddr = findViewById(R.id.SweeperAdd);
        swVerNo = findViewById(R.id.SweeperVerNo);
        carName = findViewById(R.id.CarCleanerName);
        carAddr = findViewById(R.id.CarCleanerAdd);
        carVerNo = findViewById(R.id.CarCleanerVerNo);
        otName = findViewById(R.id.OthersName);
        otAddr = findViewById(R.id.OthersAdd);
        otSerType = findViewById(R.id.OthersSerType);
        otVerNo = findViewById(R.id.OthersVerNo);

    }

    public void onClickBackVerify(View view) {
        Intent intent = new Intent(VerifyActivity.this, PreviewSnrCznActivity.class);
        startActivity(intent);
        finish();
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

    public void onClickAddPhoto(View view){
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
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
        reference = storageReference.child("SnrCznImages/"+ UUID.randomUUID().toString());
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

    public void onClickSaveBasicDetails(View view){
        name = findViewById(R.id.seniorName);
        dob= findViewById(R.id.dob_senior);
        addr = findViewById(R.id.seniorAddress);
        mob = findViewById(R.id.seniorMob);
        email = findViewById(R.id.seniorEmail);
        sname = findViewById(R.id.spouseName);
        sdob = findViewById(R.id.dob_spouse);
        wedding = findViewById(R.id.wedding);
        from = findViewById(R.id.retiredFrom);
        year = findViewById(R.id.retiredYear);
        field = findViewById(R.id.field);
        children = findViewById(R.id.childrenDetails);
        residingWith = findViewById(R.id.residingWith);
        health = findViewById(R.id.health);
        lpVisit = findViewById(R.id.lpVisit);
        freeTime = findViewById(R.id.freeTime);
        rname = findViewById(R.id.relativeName);
        relation = findViewById(R.id.relativeRelation);
        rmob = findViewById(R.id.relativeMob);
        raddr = findViewById(R.id.relativeAdd);
        if (validateBasicDetails())
        {
            personalDetails = new PersonalDetails(currentPhotoDownloadableUrl,name.getText().toString(),tenant_current_gender,dob.getText().toString(),addr.getText().toString(),mob.getText().toString(),email.getText().toString());
            spouseDetails = new SpouseDetails(sname.getText().toString(),sdob.getText().toString(),wedding.getText().toString());
            additionalDetails = new AdditionalDetails(retired,from.getText().toString(),year.getText().toString(),field.getText().toString(),children.getText().toString(),residingWith.getText().toString(),health.getText().toString(),lpVisit.getText().toString(),freeTime.getText().toString());
            relativeDetails = new RelativeDetails(rname.getText().toString(),relation.getText().toString(),rmob.getText().toString(),raddr.getText().toString());
            basicDetails = new BasicDetails(personalDetails,spouseDetails,additionalDetails,relativeDetails);
            viewPager.setCurrentItem(tablayout.getSelectedTabPosition()+1);
        }
    }

    private boolean validateBasicDetails() {
            if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(dob.getText()) || TextUtils.isEmpty(addr.getText()) || TextUtils.isEmpty(mob.getText()) ||
                TextUtils.isEmpty(email.getText()) || TextUtils.isEmpty(sname.getText()) || TextUtils.isEmpty(sdob.getText()) ||
                TextUtils.isEmpty(wedding.getText()) || TextUtils.isEmpty(field.getText()) || TextUtils.isEmpty(children.getText()) ||
                    TextUtils.isEmpty(residingWith.getText()) || TextUtils.isEmpty(health.getText()) || TextUtils.isEmpty(lpVisit.getText()) ||
                    TextUtils.isEmpty(freeTime.getText()) || TextUtils.isEmpty(rname.getText()) || TextUtils.isEmpty(relation.getText()) ||
                    TextUtils.isEmpty(rmob.getText()) || TextUtils.isEmpty(raddr.getText()))
            {
                Toast.makeText(getApplicationContext(),"FIELDS CANNOT BE EMPTY",Toast.LENGTH_LONG).show();
                return false;
            }
            else if (mob.getText().length()!=10 || rmob.getText().length()!=10){
                Toast.makeText(getApplicationContext(),"MOB NO CANNOT BE LESS THAN 10 DIGITS",Toast.LENGTH_LONG).show();
                return false;
            }
            else if (currentPhotoDownloadableUrl.equals("")){
                Toast.makeText(getApplicationContext(),"PLEASE UPLOAD PHOTO",Toast.LENGTH_LONG).show();
                return false;
            }else if (tenant_current_gender.equals("")){
                Toast.makeText(getApplicationContext(),"PLEASE SELECT GENDER",Toast.LENGTH_LONG).show();
                return false;
            }
            else return true;
    }
    public String getChecked(SwitchCompat switchCompat){
      if (switchCompat.isChecked())
          return String.valueOf(switchCompat.getTextOn());
      else return String.valueOf(switchCompat.getTextOff());
    }
    public void onClickSaveServiceProviders(View view){
        initialiseEditTexts();
        initialiseSpinners();
        if (dr.getSelectedItem().toString().equals("Select Verification Status")){
            drVerStatus = "";
        }
        else drVerStatus = dr.getSelectedItem().toString();
        if (wa.getSelectedItem().toString().equals("Select Verification Status")){
            wVerStatus = "";
        }
        else wVerStatus = wa.getSelectedItem().toString();
        if (ser.getSelectedItem().toString().equals("Select Verification Status")){
            serVerStatus = "";
        }
        else serVerStatus = ser.getSelectedItem().toString();
        if (ten.getSelectedItem().toString().equals("Select Verification Status")){
            teVerStatus = "";
        }
        else teVerStatus = ten.getSelectedItem().toString();
        if (swe.getSelectedItem().toString().equals("Select Verification Status")){
            swVerStatus = "";
        }
        else swVerStatus = swe.getSelectedItem().toString();
        if (car.getSelectedItem().toString().equals("Select Verification Status")){
            carVerStatus = "";
        }
        else carVerStatus = car.getSelectedItem().toString();
        if (oth.getSelectedItem().toString().equals("Select Verification Status")){
            otVerStatus = "";
        }
        else otVerStatus = oth.getSelectedItem().toString();
        ServiceProvider dr = new ServiceProvider(drName.getText().toString(),drAddr.getText().toString(),drVerStatus,drVerNo.getText().toString());
        ServiceProvider wa = new ServiceProvider(wName.getText().toString(),wAddr.getText().toString(),wVerStatus,wVerNo.getText().toString());
        ServiceProvider ser = new ServiceProvider(serName.getText().toString(),serAddr.getText().toString(),serVerStatus,serVerNo.getText().toString());
        ServiceProvider te = new ServiceProvider(teName.getText().toString(),teAddr.getText().toString(),teVerStatus,teVerNo.getText().toString());
        ServiceProvider sw = new ServiceProvider(swName.getText().toString(),swAddr.getText().toString(),swVerStatus,swVerNo.getText().toString());
        OtherServiceProvider oth = new OtherServiceProvider(otSerType.getText().toString(),otName.getText().toString(),otAddr.getText().toString(),otVerStatus,otVerNo.getText().toString());
        ServiceProvider car = new ServiceProvider(carName.getText().toString(),carAddr.getText().toString(),carVerStatus,carVerNo.getText().toString());
        serviceProviders = new ServiceProviders(dr,wa,ser,te,sw,car,oth);
        viewPager.setCurrentItem(tablayout.getSelectedTabPosition()+1);
    }
    public int encodeSec(String s){
        if(s.equals("Yes")){
            return 1;
        }
        if(s.equals("No")){
            return 0;
        }
        if(s.equals("Poor")){
            return 2;
        }
        return 3;
    }
    public void onClickSubmit(View view){
        initialiseSwitchCompats();
        try {
            securityChecks = new SecurityChecks(encodeSec(getChecked(Aaa)),encodeSec(getChecked(Aab)),encodeSec(getChecked(Aac)),encodeSec(getChecked(Aad)),
                    encodeSec(getChecked(Aae)),encodeSec(getChecked(Aaf)),encodeSec(getChecked(Aag)),encodeSec(getChecked(Aba)),encodeSec(getChecked(Abb)),
                    encodeSec(getChecked(Abc)),encodeSec(getChecked(Abd)),encodeSec(getChecked(Abe)),encodeSec(getChecked(Abf)),encodeSec(getChecked(Abg)),
                    encodeSec(getChecked(Ba)),encodeSec(getChecked(Bb)),encodeSec(getChecked(Bc)),encodeSec(getChecked(Bd)),encodeSec(getChecked(Be)),
                    encodeSec(getChecked(Bf)));
            currentTime = CurrentSenior.currentSenior.getMoreDetails().getTime();
            currentDate = CurrentSenior.currentSenior.getMoreDetails().getDate();
            location = new Loc(latitude,longitude);
            moreDetails = new MoreDetails(location, CurrentUser.currentUser.getMob(),CurrentUser.currentUser.getName(),currentDate,currentTime);
            final VerifySnr verifySnr = new VerifySnr(basicDetails,serviceProviders,securityChecks,moreDetails);
           // Toast.makeText(getApplicationContext(),""+verifySnr.getMoreDetails().getLoc().getLongitude(),Toast.LENGTH_LONG).show();
            String id = basicDetails.getPersonalDetails().getMob();
            databaseReference.child("Vasant Vihar").child(id).setValue(verifySnr).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "SUBMIT SUCCESSFUL", Toast.LENGTH_LONG).show();
                        CurrentSenior.currentSenior = verifySnr;
                        Intent intent = new Intent(VerifyActivity.this, PreviewSnrCznActivity.class);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "ERROR WHILE SUBMITTING. TRY AGAIN", Toast.LENGTH_LONG).show();
                    }
                });


        }catch (NullPointerException n){
            n.printStackTrace();
           // Toast.makeText(getApplicationContext(),"PLEASE SAVE DETAILS",Toast.LENGTH_LONG).show();
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

    public void onClickAddServant(View view){
        CardView add = findViewById(R.id.addServantBtn);
        if (add.getVisibility()==View.VISIBLE){
            LinearLayout linearLayout = findViewById(R.id.ServantLayout);
            linearLayout.setVisibility(View.VISIBLE);
            CardView save = findViewById(R.id.saveServantBtn);
            CardView cancel = findViewById(R.id.cancelServantBtn);
            CardView edit = findViewById(R.id.editServantBtn);
            save.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.VISIBLE);
            add.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.INVISIBLE);
        }
    }
    public void onClickSaveServant(View view){
        EditText name = findViewById(R.id.ServantName);
        EditText addr = findViewById(R.id.ServantAdd);
        EditText verNo = findViewById(R.id.ServantVerNo);
        Spinner spinner = findViewById(R.id.ServantVerStatus);
        if (spinner.getSelectedItem().toString().equals("Select Verification Status"))
            Toast.makeText(getApplicationContext(),"PLEASE SELECT VERIFICATION STATUS",Toast.LENGTH_LONG).show();
        else if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(addr.getText()) || TextUtils.isEmpty(verNo.getText()))
            Toast.makeText(getApplicationContext(),"FIELDS CANNOT BE EMPTY",Toast.LENGTH_LONG).show();
        else {
            LinearLayout linearLayout = findViewById(R.id.ServantLayout);
            linearLayout.setVisibility(View.GONE);
            CardView save = findViewById(R.id.saveServantBtn);
            CardView cancel = findViewById(R.id.cancelServantBtn);
            ImageView done = findViewById(R.id.doneServant);
            done.setVisibility(View.VISIBLE);
            CardView add = findViewById(R.id.addServantBtn);
            CardView edit = findViewById(R.id.editServantBtn);
            save.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.INVISIBLE);
            add.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(),"CHANGES SAVED!",Toast.LENGTH_LONG).show();
        }
    }
    public void onClickEditServant(View view){
        CardView edit = findViewById(R.id.editServantBtn);
        CardView add = findViewById(R.id.addServantBtn);
        if (edit.getVisibility()==View.VISIBLE){
            LinearLayout linearLayout = findViewById(R.id.ServantLayout);
            linearLayout.setVisibility(View.VISIBLE);
            CardView save = findViewById(R.id.saveServantBtn);
            CardView cancel = findViewById(R.id.cancelServantBtn);
            save.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.VISIBLE);
            add.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.INVISIBLE);
        }
    }
    public void onClickCancelServant(View view){
        CardView save = findViewById(R.id.saveServantBtn);
        CardView cancel = findViewById(R.id.cancelServantBtn);
        CardView add = findViewById(R.id.addServantBtn);
        CardView edit = findViewById(R.id.editServantBtn);
        ImageView done = findViewById(R.id.doneServant);
        if (done.getVisibility() == View.VISIBLE)
        {
            save.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.VISIBLE);
            add.setVisibility(View.INVISIBLE);
        }
        else{
            save.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.INVISIBLE);
            add.setVisibility(View.VISIBLE);
        }
        LinearLayout linearLayout = findViewById(R.id.ServantLayout);
        linearLayout.setVisibility(View.GONE);
    }
    //Watchman  FUNCTIONS
    public void onClickAddWatchman(View view){
        CardView add = findViewById(R.id.addWatchmanBtn);
        if (add.getVisibility()==View.VISIBLE){
            LinearLayout linearLayout = findViewById(R.id.WatchmanLayout);
            linearLayout.setVisibility(View.VISIBLE);
            CardView save = findViewById(R.id.saveWatchmanBtn);
            CardView cancel = findViewById(R.id.cancelWatchmanBtn);
            CardView edit = findViewById(R.id.editWatchmanBtn);
            save.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.VISIBLE);
            add.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.INVISIBLE);
        }
    }
    public void onClickSaveWatchman(View view){
        EditText name = findViewById(R.id.WatchmanName);
        EditText addr = findViewById(R.id.WatchmanAdd);
        EditText verNo = findViewById(R.id.WatchmanVerNo);
        Spinner spinner = findViewById(R.id.WatchmanVerStatus);
        if (spinner.getSelectedItem().toString().equals("Select Verification Status"))
            Toast.makeText(getApplicationContext(),"PLEASE SELECT VERIFICATION STATUS",Toast.LENGTH_LONG).show();
        else if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(addr.getText()) || TextUtils.isEmpty(verNo.getText()))
            Toast.makeText(getApplicationContext(),"FIELDS CANNOT BE EMPTY",Toast.LENGTH_LONG).show();
        else {
            LinearLayout linearLayout = findViewById(R.id.WatchmanLayout);
            linearLayout.setVisibility(View.GONE);
            CardView save = findViewById(R.id.saveWatchmanBtn);
            CardView cancel = findViewById(R.id.cancelWatchmanBtn);
            ImageView done = findViewById(R.id.doneWatchman);
            done.setVisibility(View.VISIBLE);
            CardView add = findViewById(R.id.addWatchmanBtn);
            CardView edit = findViewById(R.id.editWatchmanBtn);
            save.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.INVISIBLE);
            add.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(),"CHANGES SAVED!",Toast.LENGTH_LONG).show();
        }
    }
    public void onClickEditWatchman(View view){
        CardView edit = findViewById(R.id.editWatchmanBtn);
        CardView add = findViewById(R.id.addWatchmanBtn);
        if (edit.getVisibility()==View.VISIBLE){
            LinearLayout linearLayout = findViewById(R.id.WatchmanLayout);
            linearLayout.setVisibility(View.VISIBLE);
            CardView save = findViewById(R.id.saveWatchmanBtn);
            CardView cancel = findViewById(R.id.cancelWatchmanBtn);
            save.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.VISIBLE);
            add.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.INVISIBLE);
        }
    }
    public void onClickCancelWatchman(View view){
        CardView save = findViewById(R.id.saveWatchmanBtn);
        CardView cancel = findViewById(R.id.cancelWatchmanBtn);
        CardView add = findViewById(R.id.addWatchmanBtn);
        CardView edit = findViewById(R.id.editWatchmanBtn);
        ImageView done = findViewById(R.id.doneWatchman);
        if (done.getVisibility() == View.VISIBLE)
        {
            save.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.VISIBLE);
            add.setVisibility(View.INVISIBLE);
        }
        else{
            save.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.INVISIBLE);
            add.setVisibility(View.VISIBLE);
        }
        LinearLayout linearLayout = findViewById(R.id.WatchmanLayout);
        linearLayout.setVisibility(View.GONE);
    }
    public void onClickAddTenant(View view){
        CardView add = findViewById(R.id.addTenantBtn);
        if (add.getVisibility()==View.VISIBLE){
            LinearLayout linearLayout = findViewById(R.id.TenantLayout);
            linearLayout.setVisibility(View.VISIBLE);
            CardView save = findViewById(R.id.saveTenantBtn);
            CardView cancel = findViewById(R.id.cancelTenantBtn);
            CardView edit = findViewById(R.id.editTenantBtn);
            save.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.VISIBLE);
            add.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.INVISIBLE);
        }
    }
    public void onClickSaveTenant(View view){
        EditText name = findViewById(R.id.TenantName);
        EditText addr = findViewById(R.id.TenantAdd);
        EditText verNo = findViewById(R.id.TenantVerNo);
        Spinner spinner = findViewById(R.id.TenantVerStatus);
        if (spinner.getSelectedItem().toString().equals("Select Verification Status"))
            Toast.makeText(getApplicationContext(),"PLEASE SELECT VERIFICATION STATUS",Toast.LENGTH_LONG).show();
        else if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(addr.getText()) || TextUtils.isEmpty(verNo.getText()))
            Toast.makeText(getApplicationContext(),"FIELDS CANNOT BE EMPTY",Toast.LENGTH_LONG).show();
        else {
            LinearLayout linearLayout = findViewById(R.id.TenantLayout);
            linearLayout.setVisibility(View.GONE);
            CardView save = findViewById(R.id.saveTenantBtn);
            CardView cancel = findViewById(R.id.cancelTenantBtn);
            ImageView done = findViewById(R.id.doneTenant);
            done.setVisibility(View.VISIBLE);
            CardView add = findViewById(R.id.addTenantBtn);
            CardView edit = findViewById(R.id.editTenantBtn);
            save.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.INVISIBLE);
            add.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(),"CHANGES SAVED!",Toast.LENGTH_LONG).show();
        }
    }
    public void onClickEditTenant(View view){
        CardView edit = findViewById(R.id.editTenantBtn);
        CardView add = findViewById(R.id.addTenantBtn);
        if (edit.getVisibility()==View.VISIBLE){
            LinearLayout linearLayout = findViewById(R.id.TenantLayout);
            linearLayout.setVisibility(View.VISIBLE);
            CardView save = findViewById(R.id.saveTenantBtn);
            CardView cancel = findViewById(R.id.cancelTenantBtn);
            save.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.VISIBLE);
            add.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.INVISIBLE);
        }
    }
    public void onClickCancelTenant(View view){
        CardView save = findViewById(R.id.saveTenantBtn);
        CardView cancel = findViewById(R.id.cancelTenantBtn);
        CardView add = findViewById(R.id.addTenantBtn);
        CardView edit = findViewById(R.id.editTenantBtn);
        ImageView done = findViewById(R.id.doneTenant);
        if (done.getVisibility() == View.VISIBLE)
        {
            save.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.VISIBLE);
            add.setVisibility(View.INVISIBLE);
        }
        else{
            save.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.INVISIBLE);
            add.setVisibility(View.VISIBLE);
        }
        LinearLayout linearLayout = findViewById(R.id.TenantLayout);
        linearLayout.setVisibility(View.GONE);
    }
    public void onClickAddSweeper(View view){
        CardView add = findViewById(R.id.addSweeperBtn);
        if (add.getVisibility()==View.VISIBLE){
            LinearLayout linearLayout = findViewById(R.id.SweeperLayout);
            linearLayout.setVisibility(View.VISIBLE);
            CardView save = findViewById(R.id.saveSweeperBtn);
            CardView cancel = findViewById(R.id.cancelSweeperBtn);
            CardView edit = findViewById(R.id.editSweeperBtn);
            save.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.VISIBLE);
            add.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.INVISIBLE);
        }
    }
    public void onClickSaveSweeper(View view){
        EditText name = findViewById(R.id.SweeperName);
        EditText addr = findViewById(R.id.SweeperAdd);
        EditText verNo = findViewById(R.id.SweeperVerNo);
        Spinner spinner = findViewById(R.id.SweeperVerStatus);
        if (spinner.getSelectedItem().toString().equals("Select Verification Status"))
            Toast.makeText(getApplicationContext(),"PLEASE SELECT VERIFICATION STATUS",Toast.LENGTH_LONG).show();
        else if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(addr.getText()) || TextUtils.isEmpty(verNo.getText()))
            Toast.makeText(getApplicationContext(),"FIELDS CANNOT BE EMPTY",Toast.LENGTH_LONG).show();
        else {
            LinearLayout linearLayout = findViewById(R.id.SweeperLayout);
            linearLayout.setVisibility(View.GONE);
            CardView save = findViewById(R.id.saveSweeperBtn);
            CardView cancel = findViewById(R.id.cancelSweeperBtn);
            ImageView done = findViewById(R.id.doneSweeper);
            done.setVisibility(View.VISIBLE);
            CardView add = findViewById(R.id.addSweeperBtn);
            CardView edit = findViewById(R.id.editSweeperBtn);
            save.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.INVISIBLE);
            add.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(),"CHANGES SAVED!",Toast.LENGTH_LONG).show();
        }
    }
    public void onClickEditSweeper(View view){
        CardView edit = findViewById(R.id.editSweeperBtn);
        CardView add = findViewById(R.id.addSweeperBtn);
        if (edit.getVisibility()==View.VISIBLE){
            LinearLayout linearLayout = findViewById(R.id.SweeperLayout);
            linearLayout.setVisibility(View.VISIBLE);
            CardView save = findViewById(R.id.saveSweeperBtn);
            CardView cancel = findViewById(R.id.cancelSweeperBtn);
            save.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.VISIBLE);
            add.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.INVISIBLE);
        }
    }
    public void onClickCancelSweeper(View view){
        CardView save = findViewById(R.id.saveSweeperBtn);
        CardView cancel = findViewById(R.id.cancelSweeperBtn);
        CardView add = findViewById(R.id.addSweeperBtn);
        CardView edit = findViewById(R.id.editSweeperBtn);
        ImageView done = findViewById(R.id.doneSweeper);
        if (done.getVisibility() == View.VISIBLE)
        {
            save.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.VISIBLE);
            add.setVisibility(View.INVISIBLE);
        }
        else{
            save.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.INVISIBLE);
            add.setVisibility(View.VISIBLE);
        }
        LinearLayout linearLayout = findViewById(R.id.SweeperLayout);
        linearLayout.setVisibility(View.GONE);
    }
    public void onClickAddCarCleaner(View view){
        CardView add = findViewById(R.id.addCarCleanerBtn);
        if (add.getVisibility()==View.VISIBLE){
            LinearLayout linearLayout = findViewById(R.id.CarCleanerLayout);
            linearLayout.setVisibility(View.VISIBLE);
            CardView save = findViewById(R.id.saveCarCleanerBtn);
            CardView cancel = findViewById(R.id.cancelCarCleanerBtn);
            CardView edit = findViewById(R.id.editCarCleanerBtn);
            save.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.VISIBLE);
            add.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.INVISIBLE);
        }
    }
    public void onClickSaveCarCleaner(View view){
        EditText name = findViewById(R.id.CarCleanerName);
        EditText addr = findViewById(R.id.CarCleanerAdd);
        EditText verNo = findViewById(R.id.CarCleanerVerNo);
        Spinner spinner = findViewById(R.id.CarCleanerVerStatus);
        if (spinner.getSelectedItem().toString().equals("Select Verification Status"))
            Toast.makeText(getApplicationContext(),"PLEASE SELECT VERIFICATION STATUS",Toast.LENGTH_LONG).show();
        else if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(addr.getText()) || TextUtils.isEmpty(verNo.getText()))
            Toast.makeText(getApplicationContext(),"FIELDS CANNOT BE EMPTY",Toast.LENGTH_LONG).show();
        else {
            LinearLayout linearLayout = findViewById(R.id.CarCleanerLayout);
            linearLayout.setVisibility(View.GONE);
            CardView save = findViewById(R.id.saveCarCleanerBtn);
            CardView cancel = findViewById(R.id.cancelCarCleanerBtn);
            ImageView done = findViewById(R.id.doneCarCleaner);
            done.setVisibility(View.VISIBLE);
            CardView add = findViewById(R.id.addCarCleanerBtn);
            CardView edit = findViewById(R.id.editCarCleanerBtn);
            save.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.INVISIBLE);
            add.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(),"CHANGES SAVED!",Toast.LENGTH_LONG).show();
        }
    }
    public void onClickEditCarCleaner(View view){
        CardView edit = findViewById(R.id.editCarCleanerBtn);
        CardView add = findViewById(R.id.addCarCleanerBtn);
        if (edit.getVisibility()==View.VISIBLE){
            LinearLayout linearLayout = findViewById(R.id.CarCleanerLayout);
            linearLayout.setVisibility(View.VISIBLE);
            CardView save = findViewById(R.id.saveCarCleanerBtn);
            CardView cancel = findViewById(R.id.cancelCarCleanerBtn);
            save.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.VISIBLE);
            add.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.INVISIBLE);
        }
    }
    public void onClickCancelCarCleaner(View view){
        CardView save = findViewById(R.id.saveCarCleanerBtn);
        CardView cancel = findViewById(R.id.cancelCarCleanerBtn);
        CardView add = findViewById(R.id.addCarCleanerBtn);
        CardView edit = findViewById(R.id.editCarCleanerBtn);
        ImageView done = findViewById(R.id.doneCarCleaner);
        if (done.getVisibility() == View.VISIBLE)
        {
            save.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.VISIBLE);
            add.setVisibility(View.INVISIBLE);
        }
        else{
            save.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.INVISIBLE);
            add.setVisibility(View.VISIBLE);
        }
        LinearLayout linearLayout = findViewById(R.id.CarCleanerLayout);
        linearLayout.setVisibility(View.GONE);
    }
    public void onClickAddOthers(View view){
        CardView add = findViewById(R.id.addOthersBtn);
        if (add.getVisibility()==View.VISIBLE){
            LinearLayout linearLayout = findViewById(R.id.OthersLayout);
            linearLayout.setVisibility(View.VISIBLE);
            CardView save = findViewById(R.id.saveOthersBtn);
            CardView cancel = findViewById(R.id.cancelOthersBtn);
            CardView edit = findViewById(R.id.editOthersBtn);
            save.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.VISIBLE);
            add.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.INVISIBLE);
        }
    }
    public void onClickSaveOthers(View view){
        EditText name = findViewById(R.id.OthersName);
        EditText serType = findViewById(R.id.OthersSerType);
        EditText addr = findViewById(R.id.OthersAdd);
        EditText verNo = findViewById(R.id.OthersVerNo);
        Spinner spinner = findViewById(R.id.OthersVerStatus);
        if (spinner.getSelectedItem().toString().equals("Select Verification Status"))
            Toast.makeText(getApplicationContext(),"PLEASE SELECT VERIFICATION STATUS",Toast.LENGTH_LONG).show();
        else if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(addr.getText()) || TextUtils.isEmpty(verNo.getText()) || TextUtils.isEmpty(serType.getText()))
            Toast.makeText(getApplicationContext(),"FIELDS CANNOT BE EMPTY",Toast.LENGTH_LONG).show();
        else {
            LinearLayout linearLayout = findViewById(R.id.OthersLayout);
            linearLayout.setVisibility(View.GONE);
            CardView save = findViewById(R.id.saveOthersBtn);
            CardView cancel = findViewById(R.id.cancelOthersBtn);
            ImageView done = findViewById(R.id.doneOthers);
            done.setVisibility(View.VISIBLE);
            CardView add = findViewById(R.id.addOthersBtn);
            CardView edit = findViewById(R.id.editOthersBtn);
            save.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.INVISIBLE);
            add.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(),"CHANGES SAVED!",Toast.LENGTH_LONG).show();
        }
    }
    public void onClickEditOthers(View view){
        CardView edit = findViewById(R.id.editOthersBtn);
        CardView add = findViewById(R.id.addOthersBtn);
        if (edit.getVisibility()==View.VISIBLE){
            LinearLayout linearLayout = findViewById(R.id.OthersLayout);
            linearLayout.setVisibility(View.VISIBLE);
            CardView save = findViewById(R.id.saveOthersBtn);
            CardView cancel = findViewById(R.id.cancelOthersBtn);
            save.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.VISIBLE);
            add.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.INVISIBLE);
        }
    }
    public void onClickCancelOthers(View view){
        CardView save = findViewById(R.id.saveOthersBtn);
        CardView cancel = findViewById(R.id.cancelOthersBtn);
        CardView add = findViewById(R.id.addOthersBtn);
        CardView edit = findViewById(R.id.editOthersBtn);
        ImageView done = findViewById(R.id.doneOthers);
        if (done.getVisibility() == View.VISIBLE)
        {
            save.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.VISIBLE);
            add.setVisibility(View.INVISIBLE);
        }
        else{
            save.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.INVISIBLE);
            add.setVisibility(View.VISIBLE);
        }
        LinearLayout linearLayout = findViewById(R.id.OthersLayout);
        linearLayout.setVisibility(View.GONE);
    }
    public void onClickAddDriver(View view){
        CardView add = findViewById(R.id.addDriverBtn);
        if (add.getVisibility()==View.VISIBLE){
            LinearLayout linearLayout = findViewById(R.id.driverLayout);
            linearLayout.setVisibility(View.VISIBLE);
            CardView save = findViewById(R.id.saveDriverBtn);
            CardView cancel = findViewById(R.id.cancelDriverBtn);
            CardView edit = findViewById(R.id.editDriverBtn);
            save.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.VISIBLE);
            add.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.INVISIBLE);
        }
    }
    public void onClickSaveDriver(View view){
        EditText name = findViewById(R.id.driverName);
        EditText addr = findViewById(R.id.driverAdd);
        EditText verNo = findViewById(R.id.driverVerNo);
        Spinner spinner = findViewById(R.id.driverVerStatus);
        if (spinner.getSelectedItem().toString().equals("Select Verification Status"))
            Toast.makeText(getApplicationContext(),"PLEASE SELECT VERIFICATION STATUS",Toast.LENGTH_LONG).show();
        else if (TextUtils.isEmpty(name.getText()) || TextUtils.isEmpty(addr.getText()) || TextUtils.isEmpty(verNo.getText()))
            Toast.makeText(getApplicationContext(),"FIELDS CANNOT BE EMPTY",Toast.LENGTH_LONG).show();
        else {
            LinearLayout linearLayout = findViewById(R.id.driverLayout);
            linearLayout.setVisibility(View.GONE);
            CardView save = findViewById(R.id.saveDriverBtn);
            CardView cancel = findViewById(R.id.cancelDriverBtn);
            ImageView done = findViewById(R.id.doneDriver);
            done.setVisibility(View.VISIBLE);
            CardView add = findViewById(R.id.addDriverBtn);
            CardView edit = findViewById(R.id.editDriverBtn);
            save.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.INVISIBLE);
            add.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(),"CHANGES SAVED!",Toast.LENGTH_LONG).show();
        }
    }
    public void onClickEditDriver(View view){
        CardView edit = findViewById(R.id.editDriverBtn);
        CardView add = findViewById(R.id.addDriverBtn);
        if (edit.getVisibility()==View.VISIBLE){
            LinearLayout linearLayout = findViewById(R.id.driverLayout);
            linearLayout.setVisibility(View.VISIBLE);
            CardView save = findViewById(R.id.saveDriverBtn);
            CardView cancel = findViewById(R.id.cancelDriverBtn);
            save.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.VISIBLE);
            add.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.INVISIBLE);
        }
    }
    public void onClickCancelDriver(View view){
        CardView save = findViewById(R.id.saveDriverBtn);
        CardView cancel = findViewById(R.id.cancelDriverBtn);
        CardView add = findViewById(R.id.addDriverBtn);
        CardView edit = findViewById(R.id.editDriverBtn);
        ImageView done = findViewById(R.id.doneDriver);
        if (done.getVisibility() == View.VISIBLE)
        {
            save.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.VISIBLE);
            add.setVisibility(View.INVISIBLE);
        }
        else{
            save.setVisibility(View.INVISIBLE);
            cancel.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.INVISIBLE);
            add.setVisibility(View.VISIBLE);
        }
        LinearLayout linearLayout = findViewById(R.id.driverLayout);
        linearLayout.setVisibility(View.GONE);
    }
}