package com.e.dpkartavyaserver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.e.dpkartavyaserver.Adapter.VisitInsightsAdapter;
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

public class VisitCalenderActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private ArrayList<Visit> arrayList;
    private DatabaseReference databaseReference;
    private DatePickerDialog fromPicker,toPicker;
    private ImageView fromR,toR;
    private Button button;
    private EditText from,to;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_calender);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("last_visited");
        recyclerView = findViewById(R.id.visitCalenderRecycler);
        from = findViewById(R.id.from_visit_c);
        fromR = findViewById(R.id.from_visit_c_relative);
        toR = findViewById(R.id.to_visit_c_relative);
        to = findViewById(R.id.to_visit_c);
        button = findViewById(R.id.myVisitcApply);
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
                fromPicker = new DatePickerDialog(VisitCalenderActivity.this,
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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(to.getText())){
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
                    final ArrayList<Visit> tmp=new ArrayList<>();
                    final long finalInTime = inTime;
                    final long finalOutTime = outTime;
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                                Visit verifySnr = dataSnapshot1.getValue(Visit.class);
                                    // Toast.makeText(getApplicationContext(),""+verifySnr.getName(),Toast.LENGTH_SHORT).show();
                                tmp.add(verifySnr);

                            }
                            // Log.d("count", String.valueOf(count));
                            if(tmp.size()!=0) {
                                for (Visit verifySnr : tmp) {
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                                    try {
                                        long tmp = simpleDateFormat.parse(verifySnr.getDate()).getTime();
                                        if (tmp >= finalInTime && tmp <= finalOutTime) {
                                            arrayList.add(verifySnr);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                setAdapter();
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
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
                toPicker = new DatePickerDialog(VisitCalenderActivity.this,
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
    public void onClickBackVisitCalender(View view){
        Intent intent = new Intent(VisitCalenderActivity.this,DashActivity.class);
        startActivity(intent);
    }
    public void setAdapter(){
        VisitInsightsAdapter todayVerificationAdapter = new VisitInsightsAdapter(this,arrayList);
        recyclerView.setAdapter(todayVerificationAdapter);
        // no.setText(arrayList.size());
    }
}