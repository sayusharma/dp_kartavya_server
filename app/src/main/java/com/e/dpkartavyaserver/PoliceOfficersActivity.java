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
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.e.dpkartavyaserver.Adapter.PoliceAdapter;
import com.e.dpkartavyaserver.Common.CurrentUser;
import com.e.dpkartavyaserver.Model.User;
import com.e.dpkartavyaserver.Model.VerifySnr;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PoliceOfficersActivity extends AppCompatActivity implements PoliceAdapter.OnItemClickListener {
    private FirebaseDatabase firebaseDatabase;
    private EditText mobEditText;
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private EditText editText;
    private ImageView cancelName,cancelMob;
    private Context context;
    private Spinner spinner;
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
        spinner = findViewById(R.id.policePoliceOfficers);
        editText = findViewById(R.id.searchByOffNameEditText);
        mobEditText = findViewById(R.id.searchByOffMobEditText);
        recyclerView.setHasFixedSize(true);
        ArrayAdapter<CharSequence> adapters = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.police_station, R.layout.spinner_item_text);
        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapters);
        spinner.setSelection(1);
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
                    progressDialog.dismiss();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    public void onClickPoliceOfficersApply(View view){
        if(spinner.getSelectedItemPosition()==0){
            Toast.makeText(getApplicationContext(),"SELECT POLICE STATION",Toast.LENGTH_SHORT).show();
        }
        else{
            final ArrayList<User> arrayList1;
            if(spinner.getSelectedItemPosition()==1){
                arrayList1 = (ArrayList<User>) arrayList.clone();
                if (TextUtils.isEmpty(editText.getText()) && TextUtils.isEmpty(mobEditText.getText())) {
                    filterResults("",arrayList1);
                } else if (TextUtils.isEmpty(editText.getText())) {
                    String str2 = mobEditText.getText().toString();
                    filterResultsForMob(str2,arrayList1);
                } else if (TextUtils.isEmpty(mobEditText.getText())) {
                    String str = editText.getText().toString();
                    filterResults(str,arrayList1);
                } else {
                    String str = editText.getText().toString();
                    filterResults(str,arrayList1);
                    String str2 = mobEditText.getText().toString();
                    filterResultsForMob(str2,arrayList1);
                }
            }
            else {
                final String s = spinner.getSelectedItem().toString();
                arrayList1 = new ArrayList<>();
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            User user = dataSnapshot1.getValue(User.class);
                            if (user.getPolice().equals(s)) {
                              //  Toast.makeText(getApplicationContext(),""+user.getPolice(),Toast.LENGTH_SHORT).show();
                                arrayList1.add(user);
                                //Toast.makeText(getApplicationContext(),""+arrayList1.size(),Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (TextUtils.isEmpty(editText.getText()) && TextUtils.isEmpty(mobEditText.getText())) {
                            filterResults("",arrayList1);
                        } else if (TextUtils.isEmpty(editText.getText())) {
                            String str2 = mobEditText.getText().toString();
                            filterResultsForMob(str2,arrayList1);
                        } else if (TextUtils.isEmpty(mobEditText.getText())) {
                            String str = editText.getText().toString();
                            filterResults(str,arrayList1);
                        } else {
                            String str = editText.getText().toString();
                            filterResults(str,arrayList1);
                            String str2 = mobEditText.getText().toString();
                            filterResultsForMob(str2,arrayList1);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }

    }
    private void setAdapter() {
        policeAdapter = new PoliceAdapter(getApplicationContext(), arrayList,this);
        cuurentArrayList = arrayList;
        //Toast.makeText(getContext(),"IN3",Toast.LENGTH_SHORT).show();
        recyclerView.setAdapter(policeAdapter);

    }

    private void filterResultsForMob(String s,ArrayList<User> arrayList) {
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

    private void filterResults(String s,ArrayList<User> arrayList) {
        ArrayList<User> filterList = new ArrayList<>();
        for(User v:arrayList){
            if (v.getName().toLowerCase().contains(s)){
                filterList.add(v);
            }
        }
        if (filterList.isEmpty()){
            cuurentArrayList = (ArrayList<User>) arrayList.clone();
        }
        else{
            cuurentArrayList = (ArrayList<User>) filterList.clone();
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