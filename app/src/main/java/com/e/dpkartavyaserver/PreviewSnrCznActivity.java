package com.e.dpkartavyaserver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.e.dpkartavyaserver.Adapter.PreviewChildrenAdapter;
import com.e.dpkartavyaserver.Adapter.PreviewServiceAdapter;
import com.e.dpkartavyaserver.Common.CurrentSenior;
import com.e.dpkartavyaserver.Model.VerifySnr;
import com.squareup.picasso.Picasso;

public class PreviewSnrCznActivity extends AppCompatActivity {
    private TextView snrName,snrGender,snrDOB,snrAddress,snrMob,snrEmail,spouseName,spouseDOB,snrWeddingDate,snrRetired,
            snrRetiredFrom,snrRetiredYear,snrField,snrResidingWith,snrHealth,snrFreeTime,snrRelativeName,
            snrRelativeRelation,snrRelativeMob,snrRelativeAddr;
    private RecyclerView childrenRecycler,serviceRecycler;
    private TextView Aaa,Aab,Aac,Aad,Aae,Aaf,Aag,Aba,Abb,Abc,Abd,Abe,Abf,Abg,Ba,Bb,Bc,Bd,Be,Bf;
    private VerifySnr snr;
    private ImageView snrPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_snr_czn);
        snr = CurrentSenior.currentSenior;
        initialiseTextViews();
        try {
            Picasso.get()
                    .load(snr.getBasicDetails().getPersonalDetails().getPhoto())
                    .into(snrPhoto);
        }catch (Exception e){
            e.printStackTrace();
        }
        //Toast.makeText(getApplicationContext(),""+snr.getBasicDetails().getPersonalDetails().getMob(),Toast.LENGTH_SHORT).show();
        childrenRecycler = findViewById(R.id.previewChildrenRecycler);
        childrenRecycler.setLayoutManager(new LinearLayoutManager(this));
        if(CurrentSenior.currentSenior.getBasicDetails().getChildrenDetails()!=null){
            PreviewChildrenAdapter previewChildrenAdapter = new PreviewChildrenAdapter(this,CurrentSenior.currentSenior.getBasicDetails().getChildrenDetails());
            childrenRecycler.setAdapter(previewChildrenAdapter);
        }
        serviceRecycler = findViewById(R.id.previewServiceRecycler);
        serviceRecycler.setLayoutManager(new LinearLayoutManager(this));
        if(CurrentSenior.currentSenior.getServiceProviders()!=null) {
            PreviewServiceAdapter previewServiceAdapter = new PreviewServiceAdapter(this, CurrentSenior.currentSenior.getServiceProviders());
            serviceRecycler.setAdapter(previewServiceAdapter);
        }
        setTextViews();
    }
    public void onClickVisitHistory(View view){
        Intent intent = new Intent(PreviewSnrCznActivity.this,VisitHistoryActivity.class);
        startActivity(intent);
    }
    public void onClickEditVerify(View view){
        Intent intent = new Intent(PreviewSnrCznActivity.this,VerifyActivity.class);
        startActivity(intent);
    }
    public void onClickGeoLocation(View view){
        String ss ="geo:0,0?q="+snr.getMoreDetails().getLoc().getLatitude()+","+snr.getMoreDetails().getLoc().getLongitude()+",(Location)";
       // String u = "geo:"+snr.getMoreDetails().getLoc().getLatitude()+","+snr.getMoreDetails().getLoc().getLongitude()+"?q=";
        Uri gmmIntentUri = Uri.parse(ss);
// Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
// Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps");

// Attempt to star an activity that can handle the Intent
        startActivity(mapIntent);
    }
    private void setTextViews() {
        snrName.setText(snr.getBasicDetails().getPersonalDetails().getName());
        snrAddress.setText(snr.getBasicDetails().getPersonalDetails().getAddress());
        snrDOB.setText(snr.getBasicDetails().getPersonalDetails().getDob());
        snrGender.setText(snr.getBasicDetails().getPersonalDetails().getGender());
        snrMob.setText(snr.getBasicDetails().getPersonalDetails().getMob());
        snrEmail.setText(snr.getBasicDetails().getPersonalDetails().getEmail());

        spouseName.setText(snr.getBasicDetails().getSpouseDetails().getName());
        spouseDOB.setText(snr.getBasicDetails().getSpouseDetails().getDob());
        snrWeddingDate.setText(snr.getBasicDetails().getSpouseDetails().getWedding());

        snrRetired.setText(snr.getBasicDetails().getAdditionalDetails().getRetired());
        snrRetiredFrom.setText(snr.getBasicDetails().getAdditionalDetails().getFrom());
        snrRetiredYear.setText(snr.getBasicDetails().getAdditionalDetails().getYear());
        snrField.setText(snr.getBasicDetails().getAdditionalDetails().getField());
        snrResidingWith.setText(snr.getBasicDetails().getAdditionalDetails().getResidingWith());
        snrHealth.setText(snr.getBasicDetails().getAdditionalDetails().getHealth());
        snrFreeTime.setText(snr.getBasicDetails().getAdditionalDetails().getAvailable_hours());
        snrRelativeName.setText(snr.getBasicDetails().getRelativeDetails().getName());
        snrRelativeAddr.setText(snr.getBasicDetails().getRelativeDetails().getAddress());
        snrRelativeMob.setText(snr.getBasicDetails().getRelativeDetails().getMob());
        snrRelativeRelation.setText(snr.getBasicDetails().getRelativeDetails().getRelation());


        Aaa.setText(decodeSecurityCheck(snr.getSecurityChecks().getAaa()));
        Aab.setText(decodeSecurityCheck(snr.getSecurityChecks().getAab()));
        Aac.setText(decodeSecurityCheck(snr.getSecurityChecks().getAac()));
        Aad.setText(decodeSecurityCheck(snr.getSecurityChecks().getAad()));
        Aae.setText(decodeSecurityCheck(snr.getSecurityChecks().getAae()));
        Aaf.setText(decodeSecurityCheck(snr.getSecurityChecks().getAaf()));
        Aag.setText(decodeSecurityCheck(snr.getSecurityChecks().getAag()));
        Aba.setText(decodeSecurityCheck(snr.getSecurityChecks().getAba()));
        Abb.setText(decodeSecurityCheck(snr.getSecurityChecks().getAbb()));
        Abc.setText(decodeSecurityCheck(snr.getSecurityChecks().getAbc()));
        Abd.setText(decodeSecurityCheck(snr.getSecurityChecks().getAbd()));
        Abe.setText(decodeSecurityCheck(snr.getSecurityChecks().getAbe()));
        Abf.setText(decodeSecurityCheck(snr.getSecurityChecks().getAbf()));
        Abg.setText(decodeSecurityCheck(snr.getSecurityChecks().getAbg()));
        Ba.setText(decodeSecurityCheck(snr.getSecurityChecks().getBa()));
        Bb.setText(decodeSecurityCheck(snr.getSecurityChecks().getBb()));
        Bc.setText(decodeSecurityCheck(snr.getSecurityChecks().getBc()));
        Bd.setText(decodeSecurityCheck(snr.getSecurityChecks().getBd()));
        Be.setText(decodeSecurityCheck(snr.getSecurityChecks().getBe()));
        Bf.setText(decodeSecurityCheck(snr.getSecurityChecks().getBf()));

    }
    public String decodeSecurityCheck(int s){
        if(s==0){
            return "NO";
        }
        if(s==1){
            return "YES";
        }
        if(s==2){
            return "POOR";
        }
        return "GOOD";
    }
    private void initialiseTextViews() {
        //currSnr=findViewById(R.id.currentSenior);
        snrPhoto=findViewById(R.id.snrPhoto);
        snrName=findViewById(R.id.snrName);snrGender=findViewById(R.id.snrGender);
        snrDOB=findViewById(R.id.snrDOB);snrAddress=findViewById(R.id.snrAddress);snrMob=findViewById(R.id.snrMob);
        snrEmail=findViewById(R.id.snrEmail);spouseName=findViewById(R.id.snrSpouseName);spouseDOB=findViewById(R.id.snrSpouseDOB);
        snrWeddingDate=findViewById(R.id.snrWeddingDate);snrRetired=findViewById(R.id.snrRetired);
        snrRetiredFrom=findViewById(R.id.snrRetiredFrom);snrRetiredYear=findViewById(R.id.snrRetiredYear);
        snrField=findViewById(R.id.snrField);snrResidingWith=findViewById(R.id.snrResidingWith);
        snrHealth=findViewById(R.id.snrHealth);snrFreeTime=findViewById(R.id.snrFreeTime);
        snrRelativeName=findViewById(R.id.snrRelativeName);snrRelativeRelation=findViewById(R.id.snrRelativeRelation);
        snrRelativeMob=findViewById(R.id.snrRelativeMob);snrRelativeAddr=findViewById(R.id.snrRelativeAddress);

        Aaa=findViewById(R.id.pAaa);Aab=findViewById(R.id.pAab);Aac=findViewById(R.id.pAac);Aad=findViewById(R.id.pAad);
        Aae=findViewById(R.id.pAae);Aaf=findViewById(R.id.pAaf);Aag=findViewById(R.id.pAag);Aba=findViewById(R.id.pAba);
        Abb=findViewById(R.id.pAbb);Abc=findViewById(R.id.pAbc);Abd=findViewById(R.id.pAbd);Abe=findViewById(R.id.pAbe);
        Abf=findViewById(R.id.pAbf);Abg=findViewById(R.id.pAbg);Ba=findViewById(R.id.pBa);Bb=findViewById(R.id.pBb);
        Bc=findViewById(R.id.pBc);Bd=findViewById(R.id.pBd);Be=findViewById(R.id.pBe);Bf=findViewById(R.id.pBf);

    }
}