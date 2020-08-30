package com.e.dpkartavyaserver;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.dpkartavyaserver.Adapter.VerificationAdapter;
import com.e.dpkartavyaserver.Common.CurrentSenior;
import com.e.dpkartavyaserver.Common.CurrentUser;
import com.e.dpkartavyaserver.Model.VerifySnr;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VerificationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VerificationsFragment extends Fragment implements VerificationAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private VerificationAdapter verificationAdapter;
    private ArrayList<VerifySnr> arrayList;
    private DatabaseReference databaseReference;
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
        databaseReference = firebaseDatabase.getReference("snr_czn").child(MyVerificationActivity.loc);
        //Toast.makeText(getContext(), MyVerificationActivity.loc,Toast.LENGTH_LONG).show();
        recyclerView = view.findViewById(R.id.verificationRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        arrayList = new ArrayList<>();
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        //Query query = databaseReference.orderByKey();
        databaseReference.orderByChild("moreDetails/date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    if(dataSnapshot1.child("moreDetails/off_mob").getValue().equals(CurrentUser.currentUser.getMob())){
                        VerifySnr order = dataSnapshot1.getValue(VerifySnr.class);
                        // Toast.makeText(getContext(),order.getName(),Toast.LENGTH_SHORT).show();
                        arrayList.add(order);
                    }

                    //Toast.makeText(getContext(),"36",Toast.LENGTH_SHORT).show();
                }
                if (arrayList.isEmpty()){
                    progressDialog.dismiss();
                    // Toast.makeText(getContext(),"NO SENIOR CITIZENS FOUND",Toast.LENGTH_SHORT).show();
                }
                else {
                    Collections.reverse(arrayList);
                    setAdapter();
                    //Toast.makeText(getContext(),"IN2",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return view;
    }
    private void setAdapter() {
        verificationAdapter = new VerificationAdapter(getContext(), arrayList,this);
        //Toast.makeText(getContext(),"IN3",Toast.LENGTH_SHORT).show();
        recyclerView.setAdapter(verificationAdapter);

    }

    @Override
    public void onClick(int position) {
        CurrentSenior.currentSenior = arrayList.get(position);
        Intent intent = new Intent(getActivity(),PreviewSnrCznActivity.class);
        startActivity(intent);
        //Toast.makeText(getContext(),"CAN'T ACCESS",Toast.LENGTH_SHORT).show();
    }
}