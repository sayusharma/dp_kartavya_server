package com.e.dpkartavyaserver;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.dpkartavyaserver.Adapter.VisitAdapter;
import com.e.dpkartavyaserver.Common.CurrentUser;
import com.e.dpkartavyaserver.Model.Visit;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VisitsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VisitsFragment extends Fragment implements VisitAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private VisitAdapter orderAdapter;
    private ArrayList<Visit> currentList;
    private ArrayList<Visit> arrayList;
    private DatabaseReference databaseReference;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VisitsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VisitsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VisitsFragment newInstance(String param1, String param2) {
        VisitsFragment fragment = new VisitsFragment();
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
        View view = inflater.inflate(R.layout.fragment_visits, container, false);
        // Toast.makeText(getContext(),"YOU THERE",Toast.LENGTH_LONG).show();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("visits");
        arrayList = new ArrayList<>();
        currentList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.visitRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        //Query query = databaseReference.orderByKey();
        databaseReference.orderByChild("date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    if(dataSnapshot1.child("off_mob").getValue().equals(CurrentUser.currentUser.getMob())){
                        Visit order = dataSnapshot1.getValue(Visit.class);
                       // Toast.makeText(getContext(),order.getName(),Toast.LENGTH_SHORT).show();
                        currentList.add(order);
                    }

                    //Toast.makeText(getContext(),"36",Toast.LENGTH_SHORT).show();
                }
                if (currentList.isEmpty()){
                    progressDialog.dismiss();
                   // Toast.makeText(getContext(),"NO SENIOR CITIZENS FOUND",Toast.LENGTH_SHORT).show();
                }
                else {
                    Collections.reverse(currentList);
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
        orderAdapter = new VisitAdapter(getContext(), currentList,this);
        //Toast.makeText(getContext(),"IN3",Toast.LENGTH_SHORT).show();
        recyclerView.setAdapter(orderAdapter);

    }

    @Override
    public void onClick(int position) {
        Toast.makeText(getContext(),"CAN'T ACCESS",Toast.LENGTH_SHORT).show();
    }
}