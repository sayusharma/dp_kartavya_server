package com.e.dpkartavyaserver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.tv.TvInputInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.e.dpkartavyaserver.Common.CurrentSenior;
import com.e.dpkartavyaserver.Model.VerifySnr;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class SeniorCitizensActivity extends AppCompatActivity implements SeniorAdapter.OnItemClickListener {
    private FirebaseDatabase firebaseDatabase;
    private EditText mobEditText;
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private EditText editText;
    private ArrayList<Integer> fil;
    private ImageView cancelName,cancelMob;
    private Context context;
    private ArrayList<VerifySnr> cuurentArrayList;
    private SeniorAdapter orderAdapter;
    private ArrayList<VerifySnr> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senior_citizens);
        context = this;
        fil = new ArrayList<>();
        for(int i=0;i<5;i++){
            fil.add(0);
        }
        firebaseDatabase = FirebaseDatabase.getInstance();
        recyclerView = findViewById(R.id.snrRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        editText = findViewById(R.id.searchByNameEditText);
        mobEditText = findViewById(R.id.searchByMobEditText);
        cancelMob = findViewById(R.id.cancelMob);
        cancelName = findViewById(R.id.cancelName);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")){
                    mobEditText.setEnabled(true);
                    cancelName.setVisibility(View.INVISIBLE);
                }
                else{
                    mobEditText.setEnabled(false);
                    cancelName.setVisibility(View.VISIBLE);
                }
                if(!arrayList.isEmpty())
                    filterResults(s);

            }
        });
        mobEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")){
                    editText.setEnabled(true);
                    cancelMob.setVisibility(View.INVISIBLE);
                }
                else {
                    cancelMob.setVisibility(View.VISIBLE);
                    editText.setEnabled(false);
                }
                if(!arrayList.isEmpty())
                    filterResultsForMob(s);
            }
        });
        recyclerView.setHasFixedSize(true);
        arrayList = new ArrayList<>();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        databaseReference = firebaseDatabase.getReference("snr_czn").child("Vasant Vihar");
        //Query query = databaseReference.orderByKey();
        databaseReference.orderByChild("basicDetails/personalDetails/name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    VerifySnr order = dataSnapshot1.getValue(VerifySnr.class);
                    arrayList.add(order);
                    //Toast.makeText(getContext(),"36",Toast.LENGTH_SHORT).show();
                }
                //parentList = arrayList;
                if (arrayList.isEmpty()){
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"NO SENIOR CITIZENS FOUND",Toast.LENGTH_SHORT).show();
                }
                else {
                    setAdapter();
                    //Toast.makeText(getContext(),"IN2",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    private void filterResults(CharSequence s) {
       // Toast.makeText(getApplicationContext(),arrayList.size(),Toast.LENGTH_LONG).show();
        ArrayList<VerifySnr> filterList = new ArrayList<>();
        if(s.equals("")){
            filterList = (ArrayList<VerifySnr>) arrayList.clone();
           // Toast.makeText(getApplicationContext(),"TRUE"+filterList.size(),Toast.LENGTH_LONG).show();
        }
        else {
            for (VerifySnr v : arrayList) {
                if (v.getBasicDetails().getPersonalDetails().getName().toLowerCase().contains(s)) {
                    filterList.add(v);
                }
            }
        }
        if(fil.get(0)==1){
            try{
                for (VerifySnr v : filterList) {
                    if (v.getSecurityChecks().getAae() == 0) {
                        filterList.remove(v);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(fil.get(1)==1){
            try{
                for (VerifySnr v : filterList) {
                    if (v.getSecurityChecks().getAad() == 0) {
                        filterList.remove(v);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(fil.get(2)==1){
            try{
                for (VerifySnr v : filterList) {
                    if (v.getSecurityChecks().getAaf() == 0) {
                        filterList.remove(v);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(fil.get(3)==1){
            try {
                for (VerifySnr v : filterList) {
                    if (v.getSecurityChecks().getAac() == 0) {
                        filterList.remove(v);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(fil.get(4)==1){
            try{
                for (VerifySnr v : filterList) {
                    if (v.getSecurityChecks().getAaa() == 0) {
                        filterList.remove(v);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        cuurentArrayList = filterList;
        //Toast.makeText(getApplicationContext(),arrayList.size(),Toast.LENGTH_LONG).show();
        SeniorAdapter seniorAdapter = new SeniorAdapter(getApplicationContext(),filterList,this);
        recyclerView.setAdapter(seniorAdapter);
    }
    private void filterResultsForMob(CharSequence s) {
        ArrayList<VerifySnr> filterList = new ArrayList<>();
        if(s.equals("")){
            filterList = (ArrayList<VerifySnr>) arrayList.clone();
            // Toast.makeText(getApplicationContext(),"TRUE"+filterList.size(),Toast.LENGTH_LONG).show();
        }
        else {
            for (VerifySnr v : arrayList) {
                if (v.getBasicDetails().getPersonalDetails().getMob().contains(s)) {
                    filterList.add(v);
                }
            }
        }
        if(fil.get(0)==1){
            try{
                for (VerifySnr v : filterList) {
                    if (v.getSecurityChecks().getAae() == 0) {
                        filterList.remove(v);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(fil.get(1)==1){
            try{
                for (VerifySnr v : filterList) {
                    if (v.getSecurityChecks().getAad() == 0) {
                        filterList.remove(v);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(fil.get(2)==1){
            try{
                for (VerifySnr v : filterList) {
                    if (v.getSecurityChecks().getAaf() == 0) {
                        filterList.remove(v);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(fil.get(3)==1){
            try {
                for (VerifySnr v : filterList) {
                    if (v.getSecurityChecks().getAac() == 0) {
                        filterList.remove(v);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(fil.get(4)==1){
            try{
                for (VerifySnr v : filterList) {
                    if (v.getSecurityChecks().getAaa() == 0) {
                        filterList.remove(v);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        cuurentArrayList = filterList;
        //Toast.makeText(getApplicationContext(),arrayList.size(),Toast.LENGTH_LONG).show();
        SeniorAdapter seniorAdapter = new SeniorAdapter(getApplicationContext(),filterList,this);
        recyclerView.setAdapter(seniorAdapter);

    }

    public void onClickFilters(View view){
        TextView houseText = findViewById(R.id.houseText);
        TextView cctvText =findViewById(R.id.cctvText);
        TextView doorText = findViewById(R.id.doorText);
        TextView intruderText = findViewById(R.id.intruderText);
        TextView elecText = findViewById(R.id.electronicText);
        CardView houseCard = findViewById(R.id.houseCard);
        CardView cctvCard =findViewById(R.id.cctvCard);
        CardView doorCard = findViewById(R.id.doorCard);
        CardView intruderCard = findViewById(R.id.intruderCard);
        CardView elecCard = findViewById(R.id.electronicCard);
        //primary color is #1F2144
        //normal back color is #F8F5F5
        switch (view.getId()){
            case R.id.cctvCard:
                if(cctvCard.getAlpha()==(float) 0.99){
                    fil.set(0,1);
                    cctvCard.setCardBackgroundColor(Color.parseColor("#1F2144"));
                    cctvText.setTextColor(Color.parseColor("#FFFFFF"));
                    cctvCard.setAlpha((float) 1);
                }
                else {
                    cctvCard.setAlpha((float) 0.99);
                }
                break;
            case R.id.houseCard:
                if(houseCard.getAlpha()==(float) 0.99){
                    fil.set(1,1);
                    houseCard.setCardBackgroundColor(Color.parseColor("#1F2144"));
                    houseText.setTextColor(Color.parseColor("#FFFFFF"));
                    houseCard.setAlpha((float) 1);
                }
                else {
                    houseCard.setAlpha((float) 0.99);
                }
                break;
            case R.id.intruderCard:
                if(intruderCard.getAlpha()==(float) 0.99){
                    fil.set(2,1);
                    intruderCard.setCardBackgroundColor(Color.parseColor("#1F2144"));
                    intruderText.setTextColor(Color.parseColor("#FFFFFF"));
                    intruderCard.setAlpha((float) 1);

                }
                else {
                    elecCard.setAlpha((float) 0.99);
                }
                break;
            case R.id.electronicCard:
                if(elecCard.getAlpha()==(float) 0.99){
                    fil.set(3,1);
                    elecCard.setCardBackgroundColor(Color.parseColor("#1F2144"));
                    elecText.setTextColor(Color.parseColor("#FFFFFF"));
                    elecCard.setAlpha((float) 1);

                }
                else {
                    elecCard.setAlpha((float) 0.99);
                }
                break;
            case R.id.doorCard:
                if(doorCard.getAlpha()==(float) 0.99){
                    fil.set(4,1);
                    doorCard.setCardBackgroundColor(Color.parseColor("#1F2144"));
                    doorText.setTextColor(Color.parseColor("#FFFFFF"));
                    doorCard.setAlpha((float) 1);
                }
                else {
                    doorCard.setAlpha((float) 0.99);
                }
                break;
            default: break;
        }
        if(editText.getText().length()==0 && mobEditText.getText().length()==0){
            filterResults("");
        }
        else if(mobEditText.getText().length()>1){
            filterResults(mobEditText.getText());
        }
        else
            filterResults(editText.getText());
    }
    public void onClickResetFilters(View view){
        TextView houseText = findViewById(R.id.houseText);
        TextView cctvText =findViewById(R.id.cctvText);
        TextView doorText = findViewById(R.id.doorText);
        TextView intruderText = findViewById(R.id.intruderText);
        TextView elecText = findViewById(R.id.electronicText);
        CardView houseCard = findViewById(R.id.houseCard);
        CardView cctvCard =findViewById(R.id.cctvCard);
        CardView doorCard = findViewById(R.id.doorCard);
        CardView intruderCard = findViewById(R.id.intruderCard);
        CardView elecCard = findViewById(R.id.electronicCard);
        for(int i=0;i<5;i++){
            fil.set(0,0);
        }
        cctvCard.setCardBackgroundColor(Color.parseColor("#F8F5F5"));
        cctvText.setTextColor(Color.parseColor("#1F2144"));
        houseCard.setCardBackgroundColor(Color.parseColor("#F8F5F5"));
        houseText.setTextColor(Color.parseColor("#1F2144"));
        intruderCard.setCardBackgroundColor(Color.parseColor("#F8F5F5"));
        intruderText.setTextColor(Color.parseColor("#1F2144"));
        doorCard.setCardBackgroundColor(Color.parseColor("#F8F5F5"));
        doorText.setTextColor(Color.parseColor("#1F2144"));
        elecCard.setCardBackgroundColor(Color.parseColor("#F8F5F5"));
        elecText.setTextColor(Color.parseColor("#1F2144"));
        editText.setText("");
        mobEditText.setText("");
        final ArrayList<VerifySnr> parentList = new ArrayList<>();
        databaseReference.orderByChild("basicDetails/personalDetails/name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    VerifySnr order = dataSnapshot1.getValue(VerifySnr.class);
                    parentList.add(order);
                    //Toast.makeText(getContext(),"36",Toast.LENGTH_SHORT).show();
                }
                if (parentList.isEmpty()){
                   // progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"NO SENIOR CITIZENS FOUND",Toast.LENGTH_SHORT).show();
                }
                else {
                   setInsideAdapter(parentList);
                    //Toast.makeText(getContext(),"IN2",Toast.LENGTH_SHORT).show();
                   // progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void setInsideAdapter(ArrayList<VerifySnr> a){
        orderAdapter = new SeniorAdapter(getApplicationContext(), a,this);
        cuurentArrayList = a;
        recyclerView.setAdapter(orderAdapter);
    }
    public void onClickCancelName(View view){
        cancelName.setVisibility(View.INVISIBLE);
        editText.setText("");
    }
    public void onClickCancelMob(View view){
        cancelMob.setVisibility(View.INVISIBLE);
        mobEditText.setText("");
    }
    private void setAdapter() {
       // Toast.makeText(getApplicationContext(),String.valueOf(arrayList.size()),Toast.LENGTH_LONG).show();
        orderAdapter = new SeniorAdapter(getApplicationContext(), arrayList,this);
        cuurentArrayList = arrayList;
        recyclerView.setAdapter(orderAdapter);

    }
    public void onClickBackSeniorCitizens(View view){
        Intent intent = new Intent(SeniorCitizensActivity.this,DashActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onClick(int position) {
        Intent intent = new Intent(SeniorCitizensActivity.this,PreviewSnrCznActivity.class);
        CurrentSenior.currentSenior = cuurentArrayList.get(position);
       // intent.putExtra("police",getIntent().getStringExtra("police"));
        startActivity(intent);
    }
}