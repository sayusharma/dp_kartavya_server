package com.e.dpkartavyaserver;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.e.dpkartavyaserver.Adapter.VisitAdapter;
import com.e.dpkartavyaserver.Adapter.VisitInsightsAdapter;
import com.e.dpkartavyaserver.Common.CurrentUser;
import com.e.dpkartavyaserver.Model.Visit;
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

public class VisitInsightsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private ArrayList<Visit> arrayList;
    private DatabaseReference databaseReference;
    private Spinner spinner;
    private DatePickerDialog fromPicker,toPicker;
    private Context context;
    private ImageView fromR,toR;
    private Button button;
    private EditText from,to;
    private TextView totalVer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_insights);
        context = this;
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("visits");
        recyclerView = findViewById(R.id.visitInsightsRecycler);
        totalVer = findViewById(R.id.totalVisitInsights);
        from = findViewById(R.id.from_visit_insights);
        fromR = findViewById(R.id.from_visit_insighs_relative);
        toR = findViewById(R.id.to_visit_insights_relative);
        to = findViewById(R.id.to_visit_insights);
        spinner = findViewById(R.id.policeVisitInsights);
        ArrayAdapter<CharSequence> adapters = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.police_station, R.layout.spinner_item_text);
        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapters);
        button = findViewById(R.id.myVisitInsightsApply);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        arrayList = new ArrayList<>();
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
                fromPicker = new DatePickerDialog(VisitInsightsActivity.this,
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
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading visits...");
        progressDialog.show();
        progressDialog.setCancelable(false);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    for(DataSnapshot dataSnapshot11:dataSnapshot1.getChildren()) {
                        Visit verifySnr = dataSnapshot11.getValue(Visit.class);
                        arrayList.add(verifySnr);
                       // Toast.makeText(getApplicationContext(),""+arrayList.size(),Toast.LENGTH_SHORT).show();
                    }
                }
                progressDialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Visit> filterList;
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
                final long finalInTime = inTime;
                final long finalOutTime = outTime;
                if (spinner.getSelectedItemPosition()==0){
                    Toast.makeText(getApplicationContext(),"SELECT POLICE STATION",Toast.LENGTH_SHORT).show();
                }
                else if(spinner.getSelectedItemPosition()==1){
                    filterList = new ArrayList<>();
                    if(arrayList.size()!=0) {
                        for (Visit verifySnr : arrayList) {
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                            try {
                                long tmp = simpleDateFormat.parse(verifySnr.getDate()).getTime();
                                if (tmp >= finalInTime && tmp <= finalOutTime) {
                                    filterList.add(verifySnr);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        totalVer.setText(String.valueOf(filterList.size()));
                        VisitInsightsAdapter todayVerificationAdapter = new VisitInsightsAdapter(context,filterList);
                        recyclerView.setAdapter(todayVerificationAdapter);
                    }
                }
                else{
                    filterList = new ArrayList<>();
                    for (Visit verifySnr : arrayList) {
                        try {
                            if(verifySnr.getPolice().equals(spinner.getSelectedItem().toString())){
                                filterList.add(verifySnr);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    ArrayList<Visit> fil = new ArrayList<>();
                    for (Visit verifySnr : filterList) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                        try {
                            long tmp = simpleDateFormat.parse(verifySnr.getDate()).getTime();
                            if (tmp >= finalInTime && tmp <= finalOutTime) {
                                fil.add(verifySnr);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    totalVer.setText(String.valueOf(fil.size()));
                    VisitInsightsAdapter todayVerificationAdapter = new VisitInsightsAdapter(context,fil);
                    recyclerView.setAdapter(todayVerificationAdapter);
                }
            }
        });
        toR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                toPicker = new DatePickerDialog(VisitInsightsActivity.this,
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
    public void onClickBackVisitInsights(View view){
        Intent intent = new Intent(VisitInsightsActivity.this,DashActivity.class);
        startActivity(intent);
    }
    public void setAdapter(){
        totalVer.setText(String.valueOf(arrayList.size()));
        VisitInsightsAdapter todayVerificationAdapter = new VisitInsightsAdapter(this,arrayList);
        recyclerView.setAdapter(todayVerificationAdapter);
        // no.setText(arrayList.size());
    }
}