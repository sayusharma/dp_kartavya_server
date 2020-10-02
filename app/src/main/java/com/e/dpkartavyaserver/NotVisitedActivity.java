package com.e.dpkartavyaserver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.e.dpkartavyaserver.Adapter.NotVisitedAdapter;
import com.e.dpkartavyaserver.Common.CurrentSenior;
import com.e.dpkartavyaserver.Common.CurrentUser;
import com.e.dpkartavyaserver.Model.LastVisit;
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

public class NotVisitedActivity extends AppCompatActivity implements NotVisitedAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private long oneMonth;
    private ArrayList<LastVisit> lastVisitArrayList,mainArrayList;
    private DatabaseReference databaseReference;
    private Spinner policeSpinner,notVisitedSpinner;
    private Context context;
    private Button button;
    private TextView totalVer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_visited);
        firebaseDatabase = FirebaseDatabase.getInstance();
        context = this;
        databaseReference = firebaseDatabase.getReference("last_visited");
        policeSpinner = findViewById(R.id.policeNotVisited);
        notVisitedSpinner = findViewById(R.id.policeLastVisit);
        ArrayAdapter<CharSequence> adapters = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.police_station, R.layout.spinner_item_text);
        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        policeSpinner.setAdapter(adapters);
        ArrayAdapter<CharSequence> adapters2 = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.not_visited_type, R.layout.spinner_item_text);
        adapters2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        notVisitedSpinner.setAdapter(adapters2);
        recyclerView = findViewById(R.id.notVisitedRecycler);
        lastVisitArrayList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                    LastVisit lastVisit = dataSnapshot1.getValue(LastVisit.class);
                    lastVisitArrayList.add(lastVisit);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void onClickNotVisitedApply(View view){
        mainArrayList = new ArrayList<>();
        if(policeSpinner.getSelectedItemPosition()==0){
            Toast.makeText(getApplicationContext(),"SELECT POLICE STATION",Toast.LENGTH_SHORT).show();
        }
        else if(notVisitedSpinner.getSelectedItemPosition()==0){
            Toast.makeText(getApplicationContext(),"SELECT NOT VISITED IN",Toast.LENGTH_SHORT).show();
        }
        else {
            if(policeSpinner.getSelectedItemPosition()==1){
                if(notVisitedSpinner.getSelectedItemPosition()==5){
                    for(LastVisit lastVisit:lastVisitArrayList){
                        if(lastVisit.getDate().equals("NONE"))
                            mainArrayList.add(lastVisit);
                    }
                    NotVisitedAdapter notVisitedAdapter = new NotVisitedAdapter(getApplicationContext(),mainArrayList,this);
                    recyclerView.setAdapter(notVisitedAdapter);
                    TextView textView = findViewById(R.id.totalNotVisited);
                    textView.setText(String.valueOf(mainArrayList.size()));

                }
                else {
                    int month = 0;
                    switch (notVisitedSpinner.getSelectedItemPosition()) {
                        case 1:
                            month = -1;
                            break;
                        case 2:
                            month = -3;
                            break;
                        case 3:
                            month = -6;
                            break;
                        case 4:
                            month = -12;
                            break;
                        default:
                            break;
                    }
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.MONTH, month);
                    Date date = calendar.getTime();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                    String dateOutput = format.format(date);
                    Toast.makeText(getApplicationContext(), dateOutput, Toast.LENGTH_SHORT).show();
                    try {
                        long tmp = format.parse(dateOutput).getTime();
                        for (LastVisit lastVisit : lastVisitArrayList) {
                            String d = lastVisit.getDate();
                            if(d.equals("NONE"))
                                mainArrayList.add(lastVisit);
                            else {
                                long curr = format.parse(d).getTime();
                                if (curr < tmp)
                                    mainArrayList.add(lastVisit);
                            }
                        }
                        NotVisitedAdapter notVisitedAdapter = new NotVisitedAdapter(getApplicationContext(), mainArrayList, this);
                        recyclerView.setAdapter(notVisitedAdapter);
                        TextView textView = findViewById(R.id.totalNotVisited);
                        textView.setText(String.valueOf(mainArrayList.size()));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            }
            else{
                String s = policeSpinner.getSelectedItem().toString();
                if(notVisitedSpinner.getSelectedItemPosition()==5){
                    for(LastVisit lastVisit:lastVisitArrayList){
                        if(lastVisit.getDate().equals("NONE") && lastVisit.getPolice().equals(s))
                            mainArrayList.add(lastVisit);
                    }
                    NotVisitedAdapter notVisitedAdapter = new NotVisitedAdapter(getApplicationContext(),mainArrayList,this);
                    recyclerView.setAdapter(notVisitedAdapter);
                    TextView textView = findViewById(R.id.totalNotVisited);
                    textView.setText(String.valueOf(mainArrayList.size()));
                }
                else {
                    int month=0;
                    switch (notVisitedSpinner.getSelectedItemPosition()){
                        case 1:
                            month = -1;
                            break;
                        case 2:
                            month = -3;
                            break;
                        case 3:
                            month = -6;
                            break;
                        case 4:
                            month = -12;
                            break;
                        default:
                            break;
                    }
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.MONTH, month);
                    Date date = calendar.getTime();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                    String dateOutput = format.format(date);
                    Toast.makeText(getApplicationContext(),dateOutput,Toast.LENGTH_SHORT).show();
                    try {
                        long tmp = format.parse(dateOutput).getTime();
                        for(LastVisit lastVisit:lastVisitArrayList){
                            String d = lastVisit.getDate();
                            if(d.equals("NONE") && lastVisit.getPolice().equals(s))
                                mainArrayList.add(lastVisit);
                            else {
                                long curr = format.parse(d).getTime();
                                if (curr < tmp && lastVisit.getPolice().equals(s))
                                    mainArrayList.add(lastVisit);
                            }

                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    NotVisitedAdapter notVisitedAdapter = new NotVisitedAdapter(getApplicationContext(),mainArrayList,this);
                    recyclerView.setAdapter(notVisitedAdapter);
                    TextView textView = findViewById(R.id.totalNotVisited);
                    textView.setText(String.valueOf(mainArrayList.size()));

                }

            }
        }
    }
    public void onClickBackNotVisited(View view){
        Intent intent = new Intent(NotVisitedActivity.this,DashActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(int position) {

    }
}