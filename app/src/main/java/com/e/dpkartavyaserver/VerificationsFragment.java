package com.e.dpkartavyaserver;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.dpkartavyaserver.Adapter.TodayVerificationAdapter;
import com.e.dpkartavyaserver.Common.CurrentSenior;
import com.e.dpkartavyaserver.Common.CurrentUser;
import com.e.dpkartavyaserver.Model.VerifySnr;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VerificationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VerificationsFragment extends Fragment implements TodayVerificationAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private Button button;
    private ArrayList<VerifySnr> arrayList;
    private DatabaseReference databaseReference;
    private DatePickerDialog fromPicker,toPicker;
    private ImageView fromR,toR;
    private EditText from,to;
    private TextView totalVer;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VerificationsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VerificationsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VerificationsFragment newInstance(String param1, String param2) {
        VerificationsFragment fragment = new VerificationsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_verifications, container, false);
       // Toast.makeText(getContext(),"YOU THERE",Toast.LENGTH_LONG).show();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("snr_czn").child(CurrentUser.currentUser.getPolice());
        //Toast.makeText(getContext(), MyVerificationActivity.loc,Toast.LENGTH_LONG).show();
        recyclerView = view.findViewById(R.id.verificationRecycler);
        totalVer = view.findViewById(R.id.totalMyVerifications);
        from = view.findViewById(R.id.from_my_verificaition);
        fromR = view.findViewById(R.id.from_my_verificaition_relative);
        toR = view.findViewById(R.id.to_my_verificaition_relative);
        to = view.findViewById(R.id.to_my_verificaition);
        button = view.findViewById(R.id.myVerificationApply);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
                fromPicker = new DatePickerDialog(getContext(),
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
                    Toast.makeText(getContext(),"SELECT TO DATE",Toast.LENGTH_SHORT).show();
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
                        databaseReference = firebaseDatabase.getReference("snr_czn").child(CurrentUser.currentUser.getPolice());
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
        });

        toR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                toPicker = new DatePickerDialog(getContext(),
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
        return view;
    }
    public void setAdapter(){
        totalVer.setText(String.valueOf(arrayList.size()));
        TodayVerificationAdapter todayVerificationAdapter = new TodayVerificationAdapter(getContext(),arrayList,this);
        recyclerView.setAdapter(todayVerificationAdapter);
        // no.setText(arrayList.size());
    }
    @Override
    public void onClick(int position) {
        CurrentSenior.currentSenior = arrayList.get(position);
        Intent intent = new Intent(getActivity(),PreviewSnrCznActivity.class);
        startActivity(intent);
    }
}