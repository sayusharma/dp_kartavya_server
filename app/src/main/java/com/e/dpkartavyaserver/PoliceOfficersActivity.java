package com.e.dpkartavyaserver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.e.dpkartavyaserver.Adapter.PoliceAdapter;
import com.e.dpkartavyaserver.Common.CurrentUser;
import com.e.dpkartavyaserver.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class PoliceOfficersActivity extends AppCompatActivity implements PoliceAdapter.OnItemClickListener {
    private FirebaseDatabase firebaseDatabase;
    private EditText mobEditText;
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private EditText editText;
    private ImageView cancelName,cancelMob;
    private Context context;
    private ArrayList<User> cuurentArrayList;
    private PoliceAdapter policeAdapter;
    private ArrayList<User> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_officers);
        context = this;
        firebaseDatabase = FirebaseDatabase.getInstance();
        recyclerView = findViewById(R.id.policeOffRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        editText = findViewById(R.id.searchByOffNameEditText);
        mobEditText = findViewById(R.id.searchByOffMobEditText);
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
                filterResultsForMob(s);
            }
        });
        recyclerView.setHasFixedSize(true);
        arrayList = new ArrayList<>();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        databaseReference = firebaseDatabase.getReference("users");
        //Query query = databaseReference.orderByKey();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    User order = dataSnapshot1.getValue(User.class);
                    arrayList.add(order);
                    //Toast.makeText(getContext(),"36",Toast.LENGTH_SHORT).show();
                }
                if (arrayList.isEmpty()){
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"NO POLICE OFFICERS FOUND",Toast.LENGTH_SHORT).show();
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
    public void onClickCancelName(View view){
        cancelName.setVisibility(View.INVISIBLE);
        editText.setText("");
    }
    public void onClickCancelMob(View view){
        cancelMob.setVisibility(View.INVISIBLE);
        mobEditText.setText("");
    }
    private void setAdapter() {
        policeAdapter = new PoliceAdapter(getApplicationContext(), arrayList,this);
        cuurentArrayList = arrayList;
        //Toast.makeText(getContext(),"IN3",Toast.LENGTH_SHORT).show();
        recyclerView.setAdapter(policeAdapter);

    }

    private void filterResultsForMob(CharSequence s) {
        ArrayList<User> filterList = new ArrayList<>();
        for(User v:arrayList){
            if (v.getMob().contains(s)){
                filterList.add(v);
            }
        }
        if (filterList.isEmpty()){
            cuurentArrayList = arrayList;
            // Toast.makeText(getApplicationContext(),"NO SENIOR CITIZENS FOUND",Toast.LENGTH_SHORT).show();
        }
        else{
            cuurentArrayList = filterList;
        }
        PoliceAdapter seniorAdapter = new PoliceAdapter(getApplicationContext(),filterList,this);
        recyclerView.setAdapter(seniorAdapter);
    }

    private void filterResults(CharSequence s) {
        ArrayList<User> filterList = new ArrayList<>();
        for(User v:arrayList){
            if (v.getName().toLowerCase().contains(s)){
                filterList.add(v);
            }
        }
        if (filterList.isEmpty()){
            cuurentArrayList = arrayList;
            //Toast.makeText(getApplicationContext(),"NO SENIOR CITIZENS FOUND",Toast.LENGTH_SHORT).show();
        }
        else{
            cuurentArrayList = filterList;
        }
        PoliceAdapter seniorAdapter = new PoliceAdapter(getApplicationContext(),filterList,this);
        recyclerView.setAdapter(seniorAdapter);
    }
    public void onClickBackPoliceOfficers(View view){
        Intent intent = new Intent(PoliceOfficersActivity.this,DashActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onClick(int position) {
        CurrentUser.currentUser = cuurentArrayList.get(position);
        Intent intent = new Intent(PoliceOfficersActivity.this,MyVerificationActivity.class);
        startActivity(intent);
    }
}