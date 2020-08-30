package com.e.dpkartavyaserver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.e.dpkartavyaserver.Common.CurrentSenior;
import com.e.dpkartavyaserver.Model.VerifySnr;
import com.squareup.picasso.Picasso;

public class PreviewSnrCznActivity extends AppCompatActivity {
    private TextView snrName,snrGender,snrDOB,snrAddress,snrMob,snrEmail,spouseName,spouseDOB,snrWeddingDate,snrRetired,
            snrRetiredFrom,snrRetiredYear,snrField,snrChildren,snrResidingWith,snrHealth,snrLPVisit,snrFreeTime,snrRelativeName,
            snrRelativeRelation,snrRelativeMob,snrRelativeAddr;
    private TextView driverName,driverAddr,driverVerStatus,driverVerNo;
    private TextView watchmanName,watchmanAddr,watchmanVerStatus,watchmanVerNo;
    private TextView servantName,servantAddr,servantVerStatus,servantVerNo;
    private TextView tenantName,tenantAddr,tenantVerStatus,tenantVerNo;
    private TextView sweeperName,sweeperAddr,sweeperVerStatus,sweeperVerNo;
    private TextView carName,carAddr,carVerStatus,carVerNo;
    private TextView otherName,otherAddr,otherVerStatus,otherVerNo,otherServType;
    private TextView Aaa,Aab,Aac,Aad,Aae,Aaf,Aag,Aba,Abb,Abc,Abd,Abe,Abf,Abg,Ba,Bb,Bc,Bd,Be,Bf;
    private VerifySnr snr;
    private ImageView snrPhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_snr_czn);
        snr = CurrentSenior.currentSenior;
        initialiseTextViews();
        Picasso.get()
                .load(snr.getBasicDetails().getPersonalDetails().getPhoto())
                .into(snrPhoto);
        setTextViews();
       // Toast.makeText(getApplicationContext(),""+snr.getSecurityChecks().getAaa(),Toast.LENGTH_LONG).show();
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
    public void onClickBackPreviewSnr(View view){
        Intent intent = new Intent(PreviewSnrCznActivity.this,PoliceOfficersActivity.class);
        startActivity(intent);
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
        snrChildren.setText(snr.getBasicDetails().getAdditionalDetails().getChildren());
        snrResidingWith.setText(snr.getBasicDetails().getAdditionalDetails().getResidingWith());
        snrHealth.setText(snr.getBasicDetails().getAdditionalDetails().getHealth());
        snrLPVisit.setText(snr.getBasicDetails().getAdditionalDetails().getLpVisit());
        snrFreeTime.setText(snr.getBasicDetails().getAdditionalDetails().getFreeTime());
        snrRelativeName.setText(snr.getBasicDetails().getRelativeDetails().getName());
        snrRelativeAddr.setText(snr.getBasicDetails().getRelativeDetails().getAddress());
        snrRelativeMob.setText(snr.getBasicDetails().getRelativeDetails().getMob());
        snrRelativeRelation.setText(snr.getBasicDetails().getRelativeDetails().getRelation());

        driverName.setText(snr.getServiceProviders().getDriver().getName());
        driverAddr.setText(snr.getServiceProviders().getDriver().getAddress());
        driverVerStatus.setText(snr.getServiceProviders().getDriver().getVerStatus());
        driverVerNo.setText(snr.getServiceProviders().getDriver().getVerNo());
        watchmanName.setText(snr.getServiceProviders().getWatchman().getName());
        watchmanAddr.setText(snr.getServiceProviders().getWatchman().getAddress());
        watchmanVerStatus.setText(snr.getServiceProviders().getWatchman().getVerStatus());
        watchmanVerNo.setText(snr.getServiceProviders().getWatchman().getVerNo());

        servantName.setText(snr.getServiceProviders().getServant().getName());
        servantAddr.setText(snr.getServiceProviders().getServant().getAddress());
        servantVerStatus.setText(snr.getServiceProviders().getServant().getVerStatus());
        servantVerNo.setText(snr.getServiceProviders().getServant().getVerNo());
        tenantName.setText(snr.getServiceProviders().getTenant().getName());
        tenantAddr.setText(snr.getServiceProviders().getTenant().getAddress());
        tenantVerStatus.setText(snr.getServiceProviders().getTenant().getVerStatus());
        tenantVerNo.setText(snr.getServiceProviders().getTenant().getVerNo());
        sweeperName.setText(snr.getServiceProviders().getSweeper().getName());
        sweeperAddr.setText(snr.getServiceProviders().getSweeper().getAddress());
        sweeperVerStatus.setText(snr.getServiceProviders().getSweeper().getVerStatus());
        sweeperVerNo.setText(snr.getServiceProviders().getSweeper().getVerNo());
        carName.setText(snr.getServiceProviders().getCarCleaner().getName());
        carAddr.setText(snr.getServiceProviders().getCarCleaner().getAddress());
        carVerStatus.setText(snr.getServiceProviders().getCarCleaner().getVerStatus());
        carVerNo.setText(snr.getServiceProviders().getCarCleaner().getVerNo());
        otherName.setText(snr.getServiceProviders().getOthers().getName());
        otherAddr.setText(snr.getServiceProviders().getOthers().getAddress());
        otherVerStatus.setText(snr.getServiceProviders().getOthers().getVerStatus());
        otherVerNo.setText(snr.getServiceProviders().getOthers().getVerNo());
        otherServType.setText(snr.getServiceProviders().getOthers().getSerType());

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
        snrField=findViewById(R.id.snrField);snrChildren=findViewById(R.id.snrChildren);snrResidingWith=findViewById(R.id.snrResidingWith);
        snrHealth=findViewById(R.id.snrHealth);snrLPVisit=findViewById(R.id.snrLPVisit);snrFreeTime=findViewById(R.id.snrFreeTime);
        snrRelativeName=findViewById(R.id.snrRelativeName);snrRelativeRelation=findViewById(R.id.snrRelativeRelation);
        snrRelativeMob=findViewById(R.id.snrRelativeMob);snrRelativeAddr=findViewById(R.id.snrRelativeAddress);


        driverName=findViewById(R.id.snrDriverName);driverAddr=findViewById(R.id.snrDriverAddr);driverVerStatus=findViewById(R.id.snrDriverVerStatus);driverVerNo=findViewById(R.id.snrDriverVerNo);
        watchmanName=findViewById(R.id.snrWatchmanName);watchmanAddr=findViewById(R.id.snrWatchmanAddr);watchmanVerStatus=findViewById(R.id.snrWatchmanVerStatus);watchmanVerNo=findViewById(R.id.snrWatchmanVerNo);
        servantName=findViewById(R.id.snrServantName);servantAddr=findViewById(R.id.snrServantAddr);servantVerStatus=findViewById(R.id.snrServantVerStatus);servantVerNo=findViewById(R.id.snrServantVerNo);
        tenantName=findViewById(R.id.snrTenantName);tenantAddr=findViewById(R.id.snrTenantAddr);tenantVerStatus=findViewById(R.id.snrTenantVerStatus);tenantVerNo=findViewById(R.id.snrTenantVerNo);
        sweeperName=findViewById(R.id.snrSweeperName);sweeperAddr=findViewById(R.id.snrSweeperAddr);sweeperVerStatus=findViewById(R.id.snrSweeperVerStatus);sweeperVerNo=findViewById(R.id.snrSweeperVerNo);
        carName=findViewById(R.id.snrCarCleanerName);carAddr=findViewById(R.id.snrCarCleanerAddr);carVerStatus=findViewById(R.id.snrCarCleanerVerStatus);carVerNo=findViewById(R.id.snrCarCleanerVerNo);
        otherServType=findViewById(R.id.snrOthersService);otherName=findViewById(R.id.snrOthersName);otherAddr=findViewById(R.id.snrOthersAddr);otherVerStatus=findViewById(R.id.snrOthersVerStatus);
        otherVerNo=findViewById(R.id.snrOthersVerNo);

        Aaa=findViewById(R.id.pAaa);Aab=findViewById(R.id.pAab);Aac=findViewById(R.id.pAac);Aad=findViewById(R.id.pAad);
        Aae=findViewById(R.id.pAae);Aaf=findViewById(R.id.pAaf);Aag=findViewById(R.id.pAag);Aba=findViewById(R.id.pAba);
        Abb=findViewById(R.id.pAbb);Abc=findViewById(R.id.pAbc);Abd=findViewById(R.id.pAbd);Abe=findViewById(R.id.pAbe);
        Abf=findViewById(R.id.pAbf);Abg=findViewById(R.id.pAbg);Ba=findViewById(R.id.pBa);Bb=findViewById(R.id.pBb);
        Bc=findViewById(R.id.pBc);Bd=findViewById(R.id.pBd);Be=findViewById(R.id.pBe);Bf=findViewById(R.id.pBf);

    }
}