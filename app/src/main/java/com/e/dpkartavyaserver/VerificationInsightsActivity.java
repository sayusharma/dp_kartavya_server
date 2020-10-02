package com.e.dpkartavyaserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.e.dpkartavyaserver.Adapter.TodayVerificationAdapter;
import com.e.dpkartavyaserver.Common.CurrentSenior;
import com.e.dpkartavyaserver.Model.VerifySnr;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class VerificationInsightsActivity extends AppCompatActivity implements TodayVerificationAdapter.OnItemClickListener{
    private Spinner police;
    private DatePickerDialog fromPicker,toPicker;
    private ImageView fromR,toR;
    private EditText from,to;
    private RecyclerView recyclerView;
    private TextView totalVer;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private ArrayList<VerifySnr> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_insights);
        arrayList = new ArrayList<>();
        police = findViewById(R.id.policeStation);
        totalVer = findViewById(R.id.totalVerificationInsights);
        from = findViewById(R.id.from_verificaition);
        fromR = findViewById(R.id.from_verificaition_relative);
        toR = findViewById(R.id.to_verificaition_relative);
        to = findViewById(R.id.to_verificaition);
        recyclerView = findViewById(R.id.verificationInsightsRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        firebaseDatabase = FirebaseDatabase.getInstance();
        ArrayAdapter<CharSequence> adapters = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.police_station, R.layout.spinner_item_text);
        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        police.setAdapter(adapters);
        Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        String currentDate = year + "/" + (month+1) + "/" + day;
        from.setText(currentDate);
        to.setText(currentDate);
        fromR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                fromPicker = new DatePickerDialog(VerificationInsightsActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                from.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                            }
                        }, year, month, day);
                fromPicker.show();
                fromPicker.getDatePicker().setMaxDate(System.currentTimeMillis());
            }
        });
        toR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                toPicker = new DatePickerDialog(VerificationInsightsActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                to.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                            }
                        }, year, month, day);
                toPicker.show();
                toPicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                try {
                    toPicker.getDatePicker().setMinDate(simpleDateFormat.parse(from.getText().toString()).getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
    }
   public void onClickVerificationInsightsApply(View view){
        if(police.getSelectedItemPosition()==0){
            Toast.makeText(getApplicationContext(),"SELECT POLICE STATION",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(to.getText())){
            Toast.makeText(getApplicationContext(),"SELECT TO DATE",Toast.LENGTH_SHORT).show();
        }
        else {
            arrayList = new ArrayList<>();
            String in = from.getText().toString();
            long inTime=0;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            try {
                Date date = dateFormat.parse(in);
                inTime = date.getTime();
                //Toast.makeText(getApplicationContext(),String.valueOf(date.getTime()),Toast.LENGTH_LONG).show();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String out = to.getText().toString();
            long outTime=0;
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy/MM/dd");
            try {
                Date date = dateFormat1.parse(out);
                outTime = date.getTime();
                //Toast.makeText(getApplicationContext(),String.valueOf(date.getTime()),Toast.LENGTH_LONG).show();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (police.getSelectedItemPosition()==1){
                ArrayList<String> stations = new ArrayList<>();
                stations.add("SAGAR PUR");
                stations.add("PALAM VILLAGE");
                stations.add("DELHI CANTT");
                stations.add("VASANT VIHAR");
                stations.add("SOUTH CAMPUS");
                stations.add("R K PURAM");
                stations.add("VASANT KUNJ NORTH");
                stations.add("VASANT KUNJ SOUTH");
                stations.add("KAPASHERA");
                stations.add("KISHANGARH");
                stations.add("S J ENCLAVE");
                stations.add("SAROJANI NAGAR");
                arrayList = new ArrayList<>();
                for(String s:stations){
                    databaseReference = firebaseDatabase.getReference("snr_czn").child(s);
                    final ArrayList<VerifySnr> tmp=new ArrayList<>();
                    final long finalInTime = inTime;
                    final long finalOutTime = outTime;
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                VerifySnr verifySnr = dataSnapshot1.getValue(VerifySnr.class);
                                tmp.add(verifySnr);
                            }
                            // Log.d("count", String.valueOf(count));
                            if(tmp.size()!=0) {
                                for (VerifySnr verifySnr : tmp) {
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                                    try {
                                        long tmp = simpleDateFormat.parse(verifySnr.getMoreDetails().getDate()).getTime();
                                        if (tmp >= finalInTime && tmp <= finalOutTime) {
                                            arrayList.add(verifySnr);
                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            setAdapter();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
            }
            else {
                databaseReference = firebaseDatabase.getReference("snr_czn").child(police.getSelectedItem().toString());
                final ArrayList<VerifySnr> tmp=new ArrayList<>();
                final long finalInTime = inTime;
                final long finalOutTime = outTime;
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                          VerifySnr verifySnr = dataSnapshot1.getValue(VerifySnr.class);
                          tmp.add(verifySnr);
                        }
                        // Log.d("count", String.valueOf(count));
                        if(tmp.size()!=0) {
                            for (VerifySnr verifySnr : tmp) {
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                                try {
                                    long tmp = simpleDateFormat.parse(verifySnr.getMoreDetails().getDate()).getTime();
                                    if (tmp >= finalInTime && tmp <= finalOutTime) {
                                        arrayList.add(verifySnr);
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        setAdapter();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

        }
   }
    public void setAdapter(){
        totalVer.setText(String.valueOf(arrayList.size()));
        TodayVerificationAdapter todayVerificationAdapter = new TodayVerificationAdapter(this,arrayList,this);
        recyclerView.setAdapter(todayVerificationAdapter);
        // no.setText(arrayList.size());
    }

    @Override
    public void onClick(int position) {
        CurrentSenior.currentSenior = arrayList.get(position);
        Intent intent  = new Intent(VerificationInsightsActivity.this,PreviewSnrCznActivity.class);
        startActivity(intent);
    }
    public void onClickBackVerificationInsights(View view){
        Intent intent =new Intent(VerificationInsightsActivity.this,DashActivity.class);
        startActivity(intent);
    }
}