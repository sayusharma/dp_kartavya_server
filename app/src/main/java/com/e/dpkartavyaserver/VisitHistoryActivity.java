package com.e.dpkartavyaserver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.e.dpkartavyaserver.Adapter.VisitAdapter;
import com.e.dpkartavyaserver.Common.CurrentSenior;
import com.e.dpkartavyaserver.Model.Visit;
import com.e.dpkartavyaserver.ViewHolder.VisitViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VisitHistoryActivity extends AppCompatActivity {
    private TextView name,mob;
    private ImageView imageView;
    private RecyclerView recyclerView;
    private ArrayList<Visit> arrayList;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_history);
        name = findViewById(R.id.visitHistoryName);
        mob = findViewById(R.id.visitHistoryMob);
        arrayList = new ArrayList<>();
        imageView = findViewById(R.id.visitHistoryPhoto);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("visits").child("+91"+CurrentSenior.currentSenior.getBasicDetails().getPersonalDetails().getMob());
        try {
            Picasso.get()
                    .load(CurrentSenior.currentSenior.getBasicDetails().getPersonalDetails().getPhoto())
                    .into(imageView);
        }catch (Exception e){
            e.printStackTrace();
        }
        name.setText(CurrentSenior.currentSenior.getBasicDetails().getPersonalDetails().getName());
        mob.setText(CurrentSenior.currentSenior.getBasicDetails().getPersonalDetails().getMob());
        recyclerView = findViewById(R.id.visitHistoryRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        FirebaseRecyclerAdapter<Visit, VisitViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Visit, VisitViewHolder>(Visit.class,
                R.layout.item_visit,VisitViewHolder.class,databaseReference.orderByKey()) {
            @Override
            protected void populateViewHolder(VisitViewHolder visitViewHolder, final Visit visit, int i) {
                visitViewHolder.complaint.setText(visit.getComplaint());
                visitViewHolder.date.setText(visit.getDate());
                visitViewHolder.mob.setText(visit.getMob());
                visitViewHolder.name.setText(visit.getName());
                visitViewHolder.notes.setText(visit.getNotes());
                visitViewHolder.time.setText(visit.getTime());
                visitViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Visit snr = visit;
                        String ss ="geo:0,0?q="+snr.getLoc().getLatitude()+","+snr.getLoc().getLongitude()+",(Location)";
                        // String u = "geo:"+snr.getMoreDetails().getLoc().getLatitude()+","+snr.getMoreDetails().getLoc().getLongitude()+"?q=";
                        Uri gmmIntentUri = Uri.parse(ss);
// Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
// Make the Intent explicit by setting the Google Maps package
                        mapIntent.setPackage("com.google.android.apps.maps");

// Attempt to star an activity that can handle the Intent
                        startActivity(mapIntent);
                    }
                });
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    public void onClickBackVisitHistory(View view){
        Intent intent = new Intent(VisitHistoryActivity.this,PreviewSnrCznActivity.class);
        startActivity(intent);
        finish();
    }
}